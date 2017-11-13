package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.client.util.ItemModelBuilder;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Map;

public class ItemModelsOrbis
{

	public static void preInit()
	{
		registerModels();
	}

	private static void registerModels()
	{
		registerItemModels(ItemsOrbis.blueprint, "orbis/blueprint");
		registerItemModels(ItemsOrbis.block_chunk, "orbis/block_chunk");
		registerItemModels(ItemsOrbis.forged_block, "orbis/forged_block");
	}

	private static void registerItemModels(final Block block, final String path)
	{
		registerItemModels(getItem(block), path);
	}

	private static void registerItemModels(final Item item, final String path)
	{
		final ModelResourceLocation resource = new ModelResourceLocation(AetherCore.getResourcePath(path), "inventory");

		ModelLoader.setCustomModelResourceLocation(item, 0, resource);
	}

	private static void registerItemModels(final Block block, final ItemModelBuilder builder)
	{
		registerItemModels(getItem(block), builder);
	}

	private static void registerItemModels(final Item item, final ItemModelBuilder builder)
	{
		for (final Map.Entry<Integer, ModelResourceLocation> entry : builder.getRegistrations().entrySet())
		{
			ModelLoader.setCustomModelResourceLocation(item, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Shorthand utility method for Item.getItemFromBlock(block).
	 */
	private static Item getItem(final Block block)
	{
		return Item.getItemFromBlock(block);
	}
}
