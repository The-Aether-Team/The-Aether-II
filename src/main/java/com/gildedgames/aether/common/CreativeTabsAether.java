package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabsAether
{
	public static final CreativeTab tabBlocks = new CreativeTab("aether.blocks");

    public static final CreativeTab tabTools = new CreativeTab("aether.tools");

	public static final CreativeTab tabWeapons = new CreativeTab("aether.weapons");

	public static final CreativeTab tabArmor = new CreativeTab("aether.armor");

    public static final CreativeTab tabCompanions = new CreativeTab("aether.companions");

	public static final CreativeTab tabAccessories = new CreativeTab("aether.accessories");

    public static final CreativeTab tabConsumables = new CreativeTab("aether.consumables");

    public static final CreativeTab tabMiscellaneous = new CreativeTab("aether.miscellaneous");

    public static final CreativeTab tabMaterials = new CreativeTab("aether.materials");

    @SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		tabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		tabMaterials.setDisplayStack(new ItemStack(ItemsAether.ambrosium_shard));
		tabTools.setDisplayStack(new ItemStack(ItemsAether.zanite_pickaxe));
		tabWeapons.setDisplayStack(new ItemStack(ItemsAether.zanite_sword));
		tabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_helmet));
		tabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
		tabAccessories.setDisplayStack(new ItemStack(ItemsAether.zanite_ring));
        tabCompanions.setDisplayStack(new ItemStack(ItemsAether.pink_baby_swet));
        tabMiscellaneous.setDisplayStack(new ItemStack(ItemsAether.aechor_petal));
	}

	public static class CreativeTab extends CreativeTabs
	{
		private ItemStack stack;

		public CreativeTab(String unlocalizedName)
		{
			super(unlocalizedName);
		}

		@SideOnly(Side.CLIENT)
		public void setDisplayStack(ItemStack stack)
		{
			this.stack = stack;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return this.stack.getItem();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconItemDamage()
		{
			return this.stack.getItemDamage();
		}
	}
}
