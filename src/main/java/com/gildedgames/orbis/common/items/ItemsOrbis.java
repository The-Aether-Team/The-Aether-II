package com.gildedgames.orbis.common.items;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemsOrbis
{
	public static final ItemBlueprint blueprint = new ItemBlueprint();

	public static final ItemBlockDataContainer blockdata = new ItemBlockDataContainer();

	public static void preInit()
	{
		registerItem("blueprint", blueprint.setCreativeTab(null));
		registerItem("blockdata", blockdata.setCreativeTab(null));
	}


	public static <T extends Item> T registerItem(final String name, final T item)
	{
		item.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		item.setRegistryName(name);

		GameRegistry.register(item);

		return item;
	}

}
