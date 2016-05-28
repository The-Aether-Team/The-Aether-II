package com.gildedgames.aether.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.blocks.construction.BlockAltar;
import com.gildedgames.aether.common.blocks.construction.BlockAmbrosiumTorch;
import com.gildedgames.aether.common.blocks.construction.BlockHolystoneButton;
import com.gildedgames.aether.common.blocks.construction.BlockHolystonePressurePlate;
import com.gildedgames.aether.common.blocks.construction.BlockQuicksoilGlass;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootButton;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootChest;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootDoor;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootLadder;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootPressurePlate;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootTrapDoor;
import com.gildedgames.aether.common.blocks.construction.aether_walls.BlockAerogelWall;
import com.gildedgames.aether.common.blocks.construction.aether_walls.BlockAetherWall;
import com.gildedgames.aether.common.blocks.construction.skyroot_fence.BlockSkyrootFence;
import com.gildedgames.aether.common.blocks.construction.skyroot_fence.BlockSkyrootFenceGate;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.skyroot_sign.BlockWallSkyrootSign;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import com.gildedgames.aether.common.blocks.containers.BlockSkyrootWorkbench;
import com.gildedgames.aether.common.blocks.dungeon.BlockDivine;
import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinth;
import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinthEye;
import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinthPillar;
import com.gildedgames.aether.common.blocks.dungeon.BlockLinkedSchematicBoundary;
import com.gildedgames.aether.common.blocks.dungeon.BlockSchematicBoundary;
import com.gildedgames.aether.common.blocks.dungeon.BlockTeleporter;
import com.gildedgames.aether.common.blocks.dungeon.BlockWildcard;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAerogel;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockGoldenOakLog;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.BlockIcestoneBricks;
import com.gildedgames.aether.common.blocks.natural.BlockQuicksoil;
import com.gildedgames.aether.common.blocks.natural.ores.BlockAmbrosiumOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockContinuumOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockGravititeOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockIcestoneOre;
import com.gildedgames.aether.common.blocks.natural.ores.BlockZaniteOre;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.items.itemblocks.ItemBlockAetherVariants;

public class BlocksAether
{
	public static Block aether_dirt;

	public static BlockAetherGrass aether_grass;

	public static BlockHolystone holystone;

    public static Block holystone_brick;

	public static BlockAercloud aercloud;

	public static BlockAmbrosiumOre ambrosium_ore;

	public static BlockZaniteOre zanite_ore;

	public static BlockGravititeOre gravitite_ore;

	public static BlockContinuumOre continuum_ore;

	public static Block skyroot_planks;

	public static BlockAetherLog skyroot_log, golden_oak_log;

	public static BlockAetherLeaves blue_skyroot_leaves, green_skyroot_leaves, dark_blue_skyroot_leaves,
			golden_oak_leaves, purple_crystal_leaves, purple_fruit_leaves;

	public static BlockAetherPortal aether_portal;

	public static BlockTallAetherGrass tall_aether_grass;

	public static BlockQuicksoil quicksoil;

	public static BlockSkyrootWorkbench skyroot_crafting_table;

	public static BlockBlueberryBush blueberry_bush;

	public static BlockOrangeTree orange_tree;

	public static BlockAetherFlower aether_flower;

	public static BlockAltar altar;

	public static BlockIcestoneOre icestone_ore;

	public static BlockAerogel aerogel;

	public static Block zanite_block;

	public static Block enchanted_gravitite;

	public static BlockAetherSapling aether_sapling;

	public static BlockLabyrinth carved_stone, sentry_stone;

	public static BlockHolystoneFurnace holystone_furnace;

	public static BlockQuicksoilGlass quicksoil_glass;

	public static BlockSkyrootDoor skyroot_door;

	public static BlockSkyrootChest skyroot_chest;

	public static BlockAmbrosiumTorch ambrosium_torch;

	public static BlockIcestoneBricks icestone_bricks;

	public static BlockSkyrootFence skyroot_fence;

	public static BlockSkyrootFenceGate skyroot_fence_gate;

	public static BlockStandingSkyrootSign standing_skyroot_sign;

	public static BlockWallSkyrootSign wall_skyroot_sign;

	public static BlockSkyrootTrapDoor skyroot_trapdoor;

	public static BlockSkyrootPressurePlate skyroot_pressure_plate;

	public static BlockSkyrootButton skyroot_button;

	public static BlockHolystonePressurePlate holystone_pressure_plate;

	public static BlockHolystoneButton holystone_button;

	public static BlockSkyrootLadder skyroot_ladder;

	public static BlockAetherWall holystone_wall, holystone_wall_mossy, holystone_brick_wall, carved_stone_wall, icestone_wall, aerogel_wall, skyroot_log_wall,
									divine_stone_wall, sentry_stone_wall, divine_sentry_stone_wall;

	public static BlockTeleporter labyrinth_totem;

	public static Block multiblock_dummy;

	public static BlockLabyrinth carved_capstone;

	public static Block labyrinth_pillar;

	public static BlockLabyrinth labyrinth_wall, labyrinth_lightstone, labyrinth_base, labyrinth_headstone;
	
	public static Block labyrinth_eye, wildcard, schematicBoundary, linkedSchematicBoundary;

	public static void preInit()
	{
		aether_dirt = registerBlock("aether_dirt", new Block(Material.ground).setStepSound(Block.soundTypeGravel).setHardness(0.5f), AetherCreativeTabs.tabBlocks);

		aether_grass = registerBlockWithItem("aether_grass", new BlockAetherGrass(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		holystone = registerBlockWithItem("holystone", new BlockHolystone(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		aercloud = registerBlockWithItem("aercloud", new BlockAercloud(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		skyroot_planks = registerBlock("skyroot_planks", new Block(Material.wood)
				.setStepSound(Block.soundTypeWood).setHardness(2.0f).setResistance(5.0f), AetherCreativeTabs.tabBlocks);

		ambrosium_ore = registerBlock("ambrosium_ore", new BlockAmbrosiumOre(), AetherCreativeTabs.tabBlocks);

		zanite_ore = registerBlock("zanite_ore", new BlockZaniteOre(), AetherCreativeTabs.tabBlocks);

		gravitite_ore = registerBlock("gravitite_ore", new BlockGravititeOre(), AetherCreativeTabs.tabBlocks);

		continuum_ore = registerBlock("continuum_ore", new BlockContinuumOre(), AetherCreativeTabs.tabBlocks);

		skyroot_log = registerBlock("skyroot_log", new BlockAetherLog(), AetherCreativeTabs.tabBlocks);

		golden_oak_log = registerBlock("golden_oak_log", new BlockGoldenOakLog(), AetherCreativeTabs.tabBlocks);

		blue_skyroot_leaves = registerBlock("blue_skyroot_leaves", new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		green_skyroot_leaves = registerBlock("green_skyroot_leaves", new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		dark_blue_skyroot_leaves = registerBlock("dark_blue_skyroot_leaves", new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		golden_oak_leaves = registerBlock("golden_oak_leaves", new BlockAetherLeaves(BlockAetherSapling.GOLDEN_OAK_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		purple_crystal_leaves = registerBlock("purple_crystal_leaves", new BlockAetherLeaves(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		purple_fruit_leaves = registerBlock("purple_fruit_leaves", new BlockAetherLeaves(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta()), AetherCreativeTabs.tabBlocks);

		aether_portal = registerBlock("aether_portal", new BlockAetherPortal());

		tall_aether_grass = registerBlock("tall_aether_grass", new BlockTallAetherGrass(), AetherCreativeTabs.tabBlocks);

		quicksoil = registerBlock("quicksoil", new BlockQuicksoil(), AetherCreativeTabs.tabBlocks);

		skyroot_crafting_table = registerBlock("skyroot_crafting_table", new BlockSkyrootWorkbench(), AetherCreativeTabs.tabBlocks);

		blueberry_bush = registerBlockWithItem("blueberry_bush", new BlockBlueberryBush(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		orange_tree = registerBlock("orange_tree", new BlockOrangeTree(), AetherCreativeTabs.tabBlocks);

		aether_flower = registerBlockWithItem("aether_flower", new BlockAetherFlower(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		altar = registerBlock("altar", new BlockAltar(), AetherCreativeTabs.tabBlocks);

		icestone_ore = registerBlock("icestone_ore", new BlockIcestoneOre(), AetherCreativeTabs.tabBlocks);

		aerogel = registerBlock("aerogel", new BlockAerogel(), AetherCreativeTabs.tabBlocks);

		zanite_block = registerBlock("zanite_block", new Block(Material.iron).setHardness(5f).setStepSound(Block.soundTypeMetal), AetherCreativeTabs.tabBlocks);

		enchanted_gravitite = registerBlock("enchanted_gravitite", new Block(Material.rock).setHardness(5f).setStepSound(Block.soundTypeStone), AetherCreativeTabs.tabBlocks);

		aether_sapling = registerBlockWithItem("aether_sapling", new BlockAetherSapling(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		carved_stone = registerBlockWithItem("carved_stone", new BlockDivine(), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		sentry_stone = registerBlockWithItem("sentry_stone", new BlockDivine().setGlows(true), ItemBlockAetherVariants.class, AetherCreativeTabs.tabBlocks);

		holystone_brick = registerBlock("holystone_brick", new Block(Material.rock).setHardness(2f).setStepSound(Block.soundTypeStone), AetherCreativeTabs.tabBlocks);

		holystone_furnace = registerBlock("holystone_furnace", new BlockHolystoneFurnace(), AetherCreativeTabs.tabBlocks);

		quicksoil_glass = registerBlock("quicksoil_glass", new BlockQuicksoilGlass(), AetherCreativeTabs.tabBlocks);

		skyroot_door = registerBlock("skyroot_door", new BlockSkyrootDoor());

		skyroot_chest = registerBlock("skyroot_chest", new BlockSkyrootChest(), AetherCreativeTabs.tabBlocks);

		ambrosium_torch = registerBlock("ambrosium_torch", new BlockAmbrosiumTorch(), AetherCreativeTabs.tabBlocks);

		icestone_bricks = registerBlock("icestone_bricks", new BlockIcestoneBricks(), AetherCreativeTabs.tabBlocks);

		skyroot_fence = registerBlock("skyroot_fence", new BlockSkyrootFence(Material.wood), AetherCreativeTabs.tabBlocks);

		skyroot_fence_gate = registerBlock("skyroot_fence_gate", new BlockSkyrootFenceGate(), AetherCreativeTabs.tabBlocks);

		standing_skyroot_sign = registerBlock("standing_skyroot_sign", new BlockStandingSkyrootSign());

		skyroot_trapdoor = registerBlock("skyroot_trapdoor", new BlockSkyrootTrapDoor(), AetherCreativeTabs.tabBlocks);

		skyroot_button = registerBlock("skyroot_button", new BlockSkyrootButton(), AetherCreativeTabs.tabBlocks);

		skyroot_pressure_plate = registerBlock("skyroot_pressure_plate", new BlockSkyrootPressurePlate(), AetherCreativeTabs.tabBlocks);

		holystone_pressure_plate = registerBlock("holystone_pressure_plate", new BlockHolystonePressurePlate(), AetherCreativeTabs.tabBlocks);

		holystone_button = registerBlock("holystone_button", new BlockHolystoneButton(), AetherCreativeTabs.tabBlocks);

		skyroot_ladder = registerBlock("skyroot_ladder", new BlockSkyrootLadder(Material.wood), AetherCreativeTabs.tabBlocks);

		wall_skyroot_sign = registerBlock("wall_skyroot_sign", new BlockWallSkyrootSign());

		holystone_wall = registerBlock("holystone_wall", new BlockAetherWall(BlocksAether.holystone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		holystone_wall_mossy = registerBlock("holystone_wall_mossy", new BlockAetherWall(BlocksAether.holystone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		holystone_brick_wall = registerBlock("holystone_brick_wall", new BlockAetherWall(BlocksAether.holystone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		carved_stone_wall = registerBlock("carved_stone_wall", new BlockAetherWall(BlocksAether.carved_stone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		icestone_wall = registerBlock("icestone_wall", new BlockAetherWall(BlocksAether.icestone_bricks, 3.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		skyroot_log_wall = registerBlock("skyroot_log_wall", new BlockAetherWall(BlocksAether.skyroot_log, 2.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		aerogel_wall = registerBlock("aerogel_wall", new BlockAerogelWall(BlocksAether.aerogel, 1.0f, 10.f), AetherCreativeTabs.tabBlocks);

		labyrinth_totem = registerBlock("labyrinth_totem", new BlockTeleporter(Material.iron), AetherCreativeTabs.tabBlocks);

		multiblock_dummy = registerBlock("multiblock_dummy", new BlockMultiDummy().setBlockUnbreakable());

		carved_capstone = registerBlock("carved_capstone", new BlockLabyrinth(), AetherCreativeTabs.tabBlocks);
		labyrinth_pillar = registerBlock("labyrinth_pillar", new BlockLabyrinthPillar().setGlows(true), AetherCreativeTabs.tabBlocks);
		labyrinth_wall = registerBlock("labyrinth_wall", new BlockLabyrinth(), AetherCreativeTabs.tabBlocks);
		labyrinth_lightstone = registerBlock("labyrinth_lightstone", new BlockLabyrinth().setGlows(true), AetherCreativeTabs.tabBlocks);
		labyrinth_base = registerBlock("labyrinth_base", new BlockLabyrinth().setGlows(true), AetherCreativeTabs.tabBlocks);
		labyrinth_headstone = registerBlock("labyrinth_headstone", new BlockLabyrinth(), AetherCreativeTabs.tabBlocks);

		sentry_stone_wall = registerBlock("sentry_stone_wall", new BlockAetherWall(BlocksAether.labyrinth_lightstone, 1.0f, 10.0f).setGlows(true), AetherCreativeTabs.tabBlocks);
		divine_sentry_stone_wall = registerBlock("divine_sentry_stone_wall", new BlockAetherWall(BlocksAether.holystone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);
		divine_stone_wall = registerBlock("divine_stone_wall", new BlockAetherWall(BlocksAether.holystone, 1.0f, 10.0f), AetherCreativeTabs.tabBlocks);

		labyrinth_eye = registerBlock("labyrinth_eye", new BlockLabyrinthEye(), AetherCreativeTabs.tabBlocks);

		wildcard = registerBlock("wildcard", new BlockWildcard(), AetherCreativeTabs.tabBlocks);
		
		schematicBoundary = registerBlock("schematicBoundary", new BlockSchematicBoundary(), AetherCreativeTabs.tabBlocks);
		linkedSchematicBoundary = registerBlock("linkedSchematicBoundary", new BlockLinkedSchematicBoundary());
		
		registerHarvestLevels();
	}

	private static void registerHarvestLevels()
	{
		aether_dirt.setHarvestLevel("shovel", 0);
		aether_grass.setHarvestLevel("shovel", 0);
		holystone.setHarvestLevel("pickaxe", 0);
		holystone_brick.setHarvestLevel("pickaxe", 0);
		quicksoil.setHarvestLevel("shovel", 0);
		aercloud.setHarvestLevel("shovel", 0);

		skyroot_log.setHarvestLevel("axe", 0);
		golden_oak_log.setHarvestLevel("axe", 0);
		skyroot_planks.setHarvestLevel("axe", 0);

		ambrosium_ore.setHarvestLevel("pickaxe", 0);
		zanite_ore.setHarvestLevel("pickaxe", 1);
		gravitite_ore.setHarvestLevel("pickaxe", 2);
		continuum_ore.setHarvestLevel("pickaxe", 3);

		icestone_ore.setHarvestLevel("pickaxe", 1);
		icestone_bricks.setHarvestLevel("pickaxe", 1);
		aerogel.setHarvestLevel("pickaxe", 1);

		altar.setHarvestLevel("pickaxe", 0);
		holystone_furnace.setHarvestLevel("pickaxe", 0);
		skyroot_crafting_table.setHarvestLevel("axe", 0);

		zanite_block.setHarvestLevel("pickaxe", 1);
		enchanted_gravitite.setHarvestLevel("pickaxe", 2);

		carved_stone.setHarvestLevel("pickaxe", 0);
		labyrinth_lightstone.setHarvestLevel("pickaxe", 0);

		holystone_brick.setHarvestLevel("pickaxe", 0);

		skyroot_door.setHarvestLevel("axe", 0);
		skyroot_trapdoor.setHarvestLevel("axe", 0);

		holystone_wall.setHarvestLevel("pickaxe", 0);
		holystone_wall_mossy.setHarvestLevel("pickaxe", 0);
		carved_stone_wall.setHarvestLevel("pickaxe", 0);
		skyroot_log_wall.setHarvestLevel("axe", 0);
		icestone_wall.setHarvestLevel("pickaxe", 1);
		aerogel_wall.setHarvestLevel("pickaxe", 1);
	}

	private static <T extends Block> T registerBlock(String name, T block, CreativeTabs tab)
	{
		registerBlock(name, block.setCreativeTab(tab));

		return block;
	}

	private static <T extends Block> T registerBlock(String name, T block)
	{
		registerBlockWithItem(name, block, ItemBlock.class);

		return block;
	}

	private static <T extends Block> T registerBlockWithItem(String name, T block, Class<? extends ItemBlock> item, CreativeTabs tab)
	{
		registerBlockWithItem(name, block.setCreativeTab(tab), item);

		return block;
	}

	private static <T extends Block> T registerBlockWithItem(String name, T block, Class<? extends ItemBlock> item)
	{
		block.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		block.setRegistryName(name);

		GameRegistry.registerBlock(block, item);

		return block;
	}
}
