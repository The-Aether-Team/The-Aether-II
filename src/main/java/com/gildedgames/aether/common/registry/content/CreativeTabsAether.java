package com.gildedgames.aether.common.registry.content;

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
	public static final CreativeTab BLOCKS = new CreativeTab("aether.blocks");

    public static final CreativeTab TOOLS = new CreativeTab("aether.tools");

	public static final CreativeTab WEAPONS = new CreativeTab("aether.weapons");

	public static final CreativeTab ARMOR = new CreativeTab("aether.armor");

    public static final CreativeTab COMPANIONS = new CreativeTab("aether.companions");

    public static final CreativeTab CONSUMABLES = new CreativeTab("aether.consumables");

    public static final CreativeTab MISCELLANEOUS = new CreativeTab("aether.miscellaneous");

    public static final CreativeTab MATERIALS = new CreativeTab("aether.materials");

	public static final CreativeTab RINGS = new CreativeTab("aether.rings");

	public static final CreativeTab NECKWEAR = new CreativeTab("aether.neckwear");

	public static final CreativeTab RELICS = new CreativeTab("aether.relics");

	public static final CreativeTab CHARMS = new CreativeTab("aether.charms");

	public static final CreativeTab ARTIFACTS = new CreativeTab("aether.artifacts");

    @SideOnly(Side.CLIENT)
	public static void registerTabIcons()
	{
		BLOCKS.setDisplayStack(new ItemStack(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta()));
		MATERIALS.setDisplayStack(new ItemStack(ItemsAether.ambrosium_shard));
		TOOLS.setDisplayStack(new ItemStack(ItemsAether.zanite_pickaxe));
		WEAPONS.setDisplayStack(new ItemStack(ItemsAether.gravitite_sword));
		ARMOR.setDisplayStack(new ItemStack(ItemsAether.zanite_helmet));
		CONSUMABLES.setDisplayStack(new ItemStack(ItemsAether.orange));
        COMPANIONS.setDisplayStack(new ItemStack(ItemsAether.pink_baby_swet));
        MISCELLANEOUS.setDisplayStack(new ItemStack(ItemsAether.skyroot_poison_bucket));

		RINGS.setDisplayStack(new ItemStack(ItemsAether.zanite_ring));
		NECKWEAR.setDisplayStack(new ItemStack(ItemsAether.amulet_of_growth));
		RELICS.setDisplayStack(new ItemStack(ItemsAether.regeneration_stone));
		CHARMS.setDisplayStack(new ItemStack(ItemsAether.blight_ward));
		ARTIFACTS.setDisplayStack(new ItemStack(ItemsAether.valkyrie_wings));
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
