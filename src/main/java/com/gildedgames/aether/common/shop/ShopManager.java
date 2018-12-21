package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.shop.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Used to load and cache shop definitions for cleaner and quicker use.
 */
public class ShopManager implements IShopManager
{

	private final HashMap<ResourceLocation, IShopDefinition> cachedDefinitions = new HashMap<>();

	private final Gson gson;

	private final boolean allowCaching;

	public ShopManager()
	{
		this(true);
	}

	/**
	 * Creates an instance of this dialog manager with caching enabled or disabled
	 * @param allowCaching Controls whether or not caching is enabled. Useful for debugging.
	 */
	public ShopManager(final boolean allowCaching)
	{
		this.allowCaching = allowCaching;

		this.gson = this.buildDeserializer().create();
	}

	/**
	 * Creates the JSON deserializer that converts a shop file into a {@link IShopDefinition}.
	 *
	 * @return A {@link GsonBuilder} that will construct an appropriate {@link Gson} object.
	 */
	protected GsonBuilder buildDeserializer()
	{
		final GsonBuilder builder = new GsonBuilder();

		return builder;
	}

	@Override
	public Optional<IShopDefinition> getShopDefinition(final ResourceLocation resource)
	{
		if (this.allowCaching && this.cachedDefinitions.containsKey(resource))
		{
			return Optional.of(this.cachedDefinitions.get(resource));
		}

		final IShopDefinition speaker;

		try
		{
			speaker = this.loadShopDefinition(resource);

			if (this.allowCaching)
			{
				this.cachedDefinitions.put(resource, speaker);
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to load shop definition: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(speaker);
	}

	@Override
	public IShopInstance createInstance(IShopDefinition definition, Random rand)
	{
		int stock = MathHelper.getInt(rand, definition.getMinStock(), definition.getMaxStock());

		if (stock > definition.getBuyDefinitions().size())
		{
			throw new IllegalArgumentException("One of your shop definitions has a higher stock possibility than the amount of stock options");
		}

		List<IShopBuyDefinition> chosenStockDefs = Lists.newArrayList();
		IShopBuyDefinition it = null;

		for (int i = 0; i < stock; i++)
		{
			while (it == null || chosenStockDefs.contains(it))
			{
				it = definition.getBuyDefinitions().get(rand.nextInt(definition.getBuyDefinitions().size()));
			}

			chosenStockDefs.add(it);
		}

		LinkedList<IShopBuy> chosenStock = Lists.newLinkedList();

		for (IShopBuyDefinition def : chosenStockDefs)
		{
			if (def.getItemStack().isPresent())
			{
				//TODO: Factor in the definition's rarity weight
				int price = MathHelper.getInt(rand, def.getMinPrice(), def.getMaxPrice());
				double sellingPrice = MathUtil.getDoubleRange(rand, def.getMinSellPrice(), def.getMaxSellPrice());
				int maxStock = MathHelper.getInt(rand, def.getMinStock(), def.getMaxStock());
				int ticksUntilRestock = def.getTicksUntilRestock();

				ItemStack stack = def.getItemStack().get().copy();

				chosenStock
						.add(new ShopBuy(stack, Lists.newArrayList(def.getUnlocalizedDescriptions()), price, sellingPrice, ticksUntilRestock,
								maxStock));
			}
		}

		IShopCurrency currency = new ShopCurrencyGilt();

		switch (definition.getCurrencyType())
		{
			case "plumproots":
				currency = new ShopCurrencyPlumproot();
				break;
		}

		return new ShopInstance(chosenStock, Lists.newArrayList(definition.getUnlocalizedGreetings()), currency);
	}

	private IShopDefinition loadShopDefinition(final ResourceLocation resource) throws IOException
	{
		final String path = "/assets/" + resource.getNamespace() + "/shop/" + resource.getPath() + ".json";

		AetherCore.LOGGER.info("Loading shop definition from file {}", path);

		try (InputStream stream = MinecraftServer.class.getResourceAsStream(path))
		{
			try (InputStreamReader reader = new InputStreamReader(stream))
			{
				return this.gson.fromJson(reader, ShopSchema.class);
			}
		}
	}

}
