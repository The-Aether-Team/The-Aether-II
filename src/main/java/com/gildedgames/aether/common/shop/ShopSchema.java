package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.shop.IShopBuyDefinition;
import com.gildedgames.aether.api.shop.IShopDefinition;
import com.gildedgames.aether.common.AetherCore;
import com.google.gson.annotations.SerializedName;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShopSchema implements IShopDefinition
{
	@SerializedName("buy_definitions")
	private final List<ShopBuyDefinitionSchema> buyDefinitions = null;

	@SerializedName("greetings")
	private final Collection<String> greetings = null;

	@SerializedName("max_stock")
	private int maxStock;

	@SerializedName("min_stock")
	private int minStock;

	@SerializedName("currency_type")
	private String currencyType = null;

	@Override
	public List<IShopBuyDefinition> getBuyDefinitions()
	{
		return this.buyDefinitions == null ? Collections.emptyList() : Collections.unmodifiableList(this.buyDefinitions);
	}

	@Override
	public Collection<String> getUnlocalizedGreetings()
	{
		return this.greetings == null ? Collections.emptyList() : Collections.unmodifiableCollection(this.greetings);
	}

	@Override
	public int getMaxStock()
	{
		return this.maxStock;
	}

	@Override
	public int getMinStock()
	{
		return this.minStock;
	}

	@Override
	public String getCurrencyType()
	{
		return this.currencyType == null ? "" : this.currencyType;
	}

	private class ItemStackSchema
	{
		private transient ItemStack stack;

		@SerializedName("item")
		private String item = null;

		@SerializedName("data")
		private int data;

		public ItemStack getStack()
		{
			if (this.stack == null && this.item != null)
			{
				ResourceLocation resource = new ResourceLocation(this.item);

				Block block = Block.REGISTRY.getObject(resource);

				Item item;

				if (block != Blocks.AIR)
				{
					item = Item.getItemFromBlock(block);
				}
				else
				{
					item = Item.REGISTRY.getObject(resource);
				}

				if (item != null)
				{
					this.stack = new ItemStack(item, 1, this.data);
				}
				else
				{
					AetherCore.LOGGER.error("Failed to load Item: {}", this.item);
				}
			}

			return this.stack;
		}
	}

	private class ShopBuyDefinitionSchema implements IShopBuyDefinition
	{
		@SerializedName("ticks_until_restock")
		public int ticksUntilRestock;

		@SerializedName("rarity_weight")
		public float rarityWeight;

		@SerializedName("min_price")
		public int min_price;

		@SerializedName("max_price")
		public int max_price;

		@SerializedName("min_stock")
		public int min_stock;

		@SerializedName("max_stock")
		public int max_stock;

		@SerializedName("min_sell_price")
		public double min_sell_price;

		@SerializedName("max_sell_price")
		public double max_sell_price;

		@SerializedName("stack")
		private ItemStackSchema stack = null;

		@SerializedName("descriptions")
		private Collection<String> descriptions = null;

		@Override
		public Optional<ItemStack> getItemStack()
		{
			if (this.stack == null)
			{
				return Optional.empty();
			}

			return this.stack.getStack() == null ? Optional.empty() : Optional.of(this.stack.getStack());
		}

		@Override
		public Collection<String> getUnlocalizedDescriptions()
		{
			return this.descriptions == null ? Collections.emptyList() : Collections.unmodifiableCollection(this.descriptions);
		}

		@Override
		public int getTicksUntilRestock()
		{
			return this.ticksUntilRestock;
		}

		@Override
		public float getRarityWeight()
		{
			return this.rarityWeight;
		}

		@Override
		public double getMinSellPrice()
		{
			return this.min_sell_price;
		}

		@Override
		public double getMaxSellPrice()
		{
			return this.max_sell_price;
		}

		@Override
		public int getMinPrice()
		{
			return this.min_price;
		}

		@Override
		public int getMaxPrice()
		{
			return this.max_price;
		}

		@Override
		public int getMaxStock()
		{
			return this.max_stock;
		}

		@Override
		public int getMinStock()
		{
			return this.min_stock;
		}
	}
}
