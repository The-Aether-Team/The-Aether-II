package com.gildedgames.aether.common.registry.minecraft;

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

    public static final CreativeTab tabConsumables = new CreativeTab("aether.consumables");

    public static final CreativeTab tabMiscellaneous = new CreativeTab("aether.miscellaneous");

    public static final CreativeTab tabMaterials = new CreativeTab("aether.materials");

	public static final CreativeTab tabRings = new CreativeTab("aether.rings");

	public static final CreativeTab tabNeckwear = new CreativeTab("aether.neckwear");

	public static final CreativeTab tabRelics = new CreativeTab("aether.relics");

	public static final CreativeTab tabCharms = new CreativeTab("aether.charms");

	public static final CreativeTab tabArtifacts = new CreativeTab("aether.artifacts");

    @SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		tabBlocks.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		tabMaterials.setDisplayStack(new ItemStack(ItemsAether.ambrosium_shard));
		tabTools.setDisplayStack(new ItemStack(ItemsAether.zanite_pickaxe));
		tabWeapons.setDisplayStack(new ItemStack(ItemsAether.zanite_sword));
		tabArmor.setDisplayStack(new ItemStack(ItemsAether.zanite_helmet));
		tabConsumables.setDisplayStack(new ItemStack(ItemsAether.orange));
        tabCompanions.setDisplayStack(new ItemStack(ItemsAether.pink_baby_swet));
        tabMiscellaneous.setDisplayStack(new ItemStack(ItemsAether.aechor_petal));

		tabRings.setDisplayStack(new ItemStack(ItemsAether.zanite_ring));
		tabNeckwear.setDisplayStack(new ItemStack(ItemsAether.amulet_of_growth));
		tabRelics.setDisplayStack(new ItemStack(ItemsAether.regeneration_stone));
		tabCharms.setDisplayStack(new ItemStack(ItemsAether.glamoured_bone_shard));
		tabArtifacts.setDisplayStack(new ItemStack(ItemsAether.gravitite_core));
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
