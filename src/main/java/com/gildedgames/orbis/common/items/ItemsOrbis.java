package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

@Mod.EventBusSubscriber()
public class ItemsOrbis
{
	public static final ItemBlueprint blueprint = new ItemBlueprint();

	public static final ItemBlockDataContainer block_chunk = new ItemBlockDataContainer();

	public static final ItemBlockPalette block_palette = new ItemBlockPalette();

	public static final ItemBlueprintPalette blueprint_palette = new ItemBlueprintPalette();

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event)
	{
		final ItemRegistryHelper items = new ItemRegistryHelper(event.getRegistry());

		items.register("blueprint", blueprint.setCreativeTab(null));
		items.register("block_chunk", block_chunk.setCreativeTab(null));
		items.register("block_palette", block_palette.setCreativeTab(null));
		items.register("blueprint_palette", blueprint_palette.setCreativeTab(null));
	}

	private static class ItemRegistryHelper
	{
		private final IForgeRegistry<Item> registry;

		ItemRegistryHelper(final IForgeRegistry<Item> registry)
		{
			this.registry = registry;
		}

		private void register(final String registryName, final Item item)
		{
			item.setUnlocalizedName(AetherCore.MOD_ID + "." + registryName);

			item.setRegistryName(AetherCore.MOD_ID, registryName);
			registry.register(item);
		}

		private void registerBlock(final Block block)
		{
			final ItemBlock metaItemBlock = new ItemBlock(block);
			register(metaItemBlock);
		}

		private void register(final ItemBlock item)
		{
			item.setRegistryName(item.getBlock().getRegistryName());
			item.setUnlocalizedName(item.getBlock().getUnlocalizedName());
			registry.register(item);
		}
	}

}
