package com.gildedgames.aether.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.blocks.construction.BlockAltar;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneKiln;
import com.gildedgames.aether.common.blocks.containers.BlockSkyrootWorkbench;
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
import com.gildedgames.aether.common.items.itemblocks.ItemBlockAetherSubtypes;

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

	public static Block holystone_kiln;

	public static void preInit()
	{
		aether_dirt = registerBlock("aether_dirt", new BlockAether(Material.ground).setStepSound(Block.soundTypeGravel).setHardness(0.5f), AetherCore.PROXY.TabBlocks);

		aether_grass = registerBlockWithItem("aether_grass", new BlockAetherGrass(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		holystone = registerBlockWithItem("holystone", new BlockHolystone(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		aercloud = registerBlockWithItem("aercloud", new BlockAercloud(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		aether_log = registerBlockWithItem("aether_log", new BlockAetherLog(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		skyroot_planks = registerBlock("skyroot_planks", new BlockAether(Material.wood)
				.setStepSound(Block.soundTypeWood).setHardness(2.0f).setResistance(5.0f), AetherCore.PROXY.TabBlocks);

		ambrosium_ore = registerBlock("ambrosium_ore", new BlockAmbrosiumOre(), AetherCore.PROXY.TabBlocks);

		zanite_ore = registerBlock("zanite_ore", new BlockZaniteOre(), AetherCore.PROXY.TabBlocks);

		gravitite_ore = registerBlock("gravitite_ore", new BlockGravititeOre(), AetherCore.PROXY.TabBlocks);

		continuum_ore = registerBlock("continuum_ore", new BlockContinuumOre(), AetherCore.PROXY.TabBlocks);

		aether_leaves = registerBlockWithItem("aether_leaves", new BlockAetherLeaves(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		aether_portal = registerBlock("aether_portal", new BlockAetherPortal());

		tall_aether_grass = registerBlock("tall_aether_grass", new BlockTallAetherGrass(), AetherCore.PROXY.TabBlocks);

		quicksoil = registerBlock("quicksoil", new BlockQuicksoil(), AetherCore.PROXY.TabBlocks);

		skyroot_crafting_table = registerBlock("skyroot_crafting_table", new BlockSkyrootWorkbench(), AetherCore.PROXY.TabBlocks);

		blueberry_bush = registerBlockWithItem("blueberry_bush", new BlockBlueberryBush(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		orange_tree = registerBlock("orange_tree", new BlockOrangeTree(), AetherCore.PROXY.TabBlocks);

		aether_flower = registerBlockWithItem("aether_flower", new BlockAetherFlower(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		altar = registerBlock("altar", new BlockAltar(), AetherCore.PROXY.TabBlocks);

		icestone = registerBlock("icestone", new BlockIcestone(), AetherCore.PROXY.TabBlocks);

		aerogel = registerBlock("aerogel", new BlockAerogel(), AetherCore.PROXY.TabBlocks);

		zanite_block = registerBlock("zanite_block", new BlockAether(Material.rock).setHardness(5f).setStepSound(Block.soundTypeStone), AetherCore.PROXY.TabBlocks);

		enchanted_gravitite = registerBlock("enchanted_gravitite", new BlockAether(Material.rock).setHardness(5f).setStepSound(Block.soundTypeStone), AetherCore.PROXY.TabBlocks);

		aether_sapling = registerBlockWithItem("aether_sapling", new BlockAetherSapling(), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		carved_stone = registerBlockWithItem("carved_stone", new BlockDungeon(Material.rock), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);

		sentry_stone = registerBlockWithItem("sentry_stone", new BlockDungeon(Material.rock).setLightLevel(0.75f), ItemBlockAetherSubtypes.class, AetherCore.PROXY.TabBlocks);
		
		holystone_brick = registerBlock("holystone_brick", new BlockAether(Material.rock), AetherCore.PROXY.TabBlocks);

		holystone_kiln = registerBlock("holystone_kiln", new BlockHolystoneKiln());

		registerHarvestLevels();
	}

	private static void registerHarvestLevels()
	{
		// Do not register harvest levels to blocks that can be mined without a tool!

		aether_dirt.setHarvestLevel("shovel", 0);
		aether_grass.setHarvestLevel("shovel", 0);
		holystone.setHarvestLevel("pickaxe", 0);

		ambrosium_ore.setHarvestLevel("pickaxe", 0);
		zanite_ore.setHarvestLevel("pickaxe", 1);
		gravitite_ore.setHarvestLevel("pickaxe", 2);
		continuum_ore.setHarvestLevel("pickaxe", 3);

		icestone.setHarvestLevel("pickaxe", 1);
		aerogel.setHarvestLevel("pickaxe", 1);

		altar.setHarvestLevel("pickaxe", 0);

		zanite_block.setHarvestLevel("pickaxe", 1);
		enchanted_gravitite.setHarvestLevel("pickaxe", 2);

		carved_stone.setHarvestLevel("pickaxe", 0);
		sentry_stone.setHarvestLevel("pickaxe", 0);

		holystone_brick.setHarvestLevel("pickaxe", 0);
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
