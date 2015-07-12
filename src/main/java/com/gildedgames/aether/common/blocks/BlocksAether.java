package com.gildedgames.aether.common.blocks;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.blocks.construction.BlockAltar;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootWorkbench;
import com.gildedgames.aether.common.blocks.dungeon.BlockDungeon;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAerogel;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.BlockIcestone;
import com.gildedgames.aether.common.blocks.natural.BlockQuicksoil;
import com.gildedgames.aether.common.blocks.natural.ores.BlockAmbrosiumOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockContinuumOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockGravititeOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockZaniteOre;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.util.BlockAether;
import com.gildedgames.aether.common.blocks.util.BlockFloating;
import com.gildedgames.aether.common.items.itemblocks.ItemBlockAetherSubtypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAether
{
	public static Block aether_dirt;

	public static Block aether_grass;

	public static Block holystone;

	public static Block aercloud;

	public static Block aether_log;

	public static Block ambrosium_ore;

	public static Block zanite_ore;

	public static Block gravitite_ore;

	public static Block continuum_ore;

	public static Block skyroot_planks;

	public static Block aether_leaves;

	public static Block aether_portal;

	public static Block tall_aether_grass;

	public static Block quicksoil;

	public static Block skyroot_crafting_table;

	public static Block blueberry_bush;

	public static Block orange_tree;

	public static Block aether_flower;

	public static Block altar;

	public static Block icestone;

	public static Block aerogel;

	public static Block zanite_block;

	public static Block enchanted_gravitite;

	public static Block aether_sapling;

	public static Block carved_stone;

	public static Block sentry_stone;
	
	public static Block holystone_brick;

	public static void preInit()
	{
		aether_dirt = registerBlock("aether_dirt", new BlockAether(Material.ground).setStepSound(Block.soundTypeGravel).setHardness(0.5f), AetherCreativeTabs.tabBlocks);

		aether_grass = registerBlockWithItem("aether_grass", new BlockAetherGrass(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		holystone = registerBlockWithItem("holystone", new BlockHolystone(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		aercloud = registerBlockWithItem("aercloud", new BlockAercloud(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		aether_log = registerBlockWithItem("aether_log", new BlockAetherLog(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		skyroot_planks = registerBlock("skyroot_planks", new BlockAether(Material.wood).setStepSound(Block.soundTypeWood).setHardness(2.0f)
				.setResistance(5.0f), AetherCreativeTabs.tabBlocks);

		ambrosium_ore = registerBlock("ambrosium_ore", new BlockAmbrosiumOre(), AetherCreativeTabs.tabBlocks);

		zanite_ore = registerBlock("zanite_ore", new BlockZaniteOre(), AetherCreativeTabs.tabBlocks);

		gravitite_ore = registerBlock("gravitite_ore", new BlockGravititeOre(), AetherCreativeTabs.tabBlocks);

		continuum_ore = registerBlock("continuum_ore", new BlockContinuumOre(), AetherCreativeTabs.tabBlocks);

		aether_leaves = registerBlockWithItem("aether_leaves", new BlockAetherLeaves(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		aether_portal = registerBlock("aether_portal", new BlockAetherPortal());

		tall_aether_grass = registerBlock("tall_aether_grass", new BlockTallAetherGrass(), AetherCreativeTabs.tabBlocks);

		quicksoil = registerBlock("quicksoil", new BlockQuicksoil(), AetherCreativeTabs.tabBlocks);

		skyroot_crafting_table = registerBlock("skyroot_crafting_table", new BlockSkyrootWorkbench(), AetherCreativeTabs.tabBlocks);

		blueberry_bush = registerBlockWithItem("blueberry_bush", new BlockBlueberryBush(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		orange_tree = registerBlock("orange_tree", new BlockOrangeTree(), AetherCreativeTabs.tabBlocks);

		aether_flower = registerBlockWithItem("aether_flower", new BlockAetherFlower(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		altar = registerBlock("altar", new BlockAltar(), AetherCreativeTabs.tabBlocks);

		icestone = registerBlock("icestone", new BlockIcestone(), AetherCreativeTabs.tabBlocks);

		aerogel = registerBlock("aerogel", new BlockAerogel(), AetherCreativeTabs.tabBlocks);

		zanite_block = registerBlock("zanite_block", new BlockAether(Material.rock).setHardness(5f).setStepSound(Block.soundTypeStone), AetherCreativeTabs.tabBlocks);

		enchanted_gravitite = registerBlock("enchanted_gravitite", new BlockAether(Material.rock).setHardness(5f).setStepSound(Block.soundTypeStone), AetherCreativeTabs.tabBlocks);

		aether_sapling = registerBlockWithItem("aether_sapling", new BlockAetherSapling(), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		carved_stone = registerBlockWithItem("carved_stone", new BlockDungeon(Material.rock), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);

		sentry_stone = registerBlockWithItem("sentry_stone", new BlockDungeon(Material.rock).setLightLevel(0.75f), ItemBlockAetherSubtypes.class, AetherCreativeTabs.tabBlocks);
		
		holystone_brick = registerBlock("holystone_brick", new BlockAether(Material.rock), AetherCreativeTabs.tabBlocks);
	}

	private static Block registerBlock(String name, Block block, CreativeTabs tab)
	{
		return registerBlock(name, block.setCreativeTab(tab));
	}

	private static Block registerBlock(String name, Block block)
	{
		return registerBlockWithItem(name, block, ItemBlock.class);
	}

	private static Block registerBlockWithItem(String name, Block block, Class<? extends ItemBlock> item, CreativeTabs tab)
	{
		return registerBlockWithItem(name, block.setCreativeTab(tab), item);
	}

	private static Block registerBlockWithItem(String name, Block block, Class<? extends ItemBlock> item)
	{
		return GameRegistry.registerBlock(block.setUnlocalizedName(name), item, name);
	}
}
