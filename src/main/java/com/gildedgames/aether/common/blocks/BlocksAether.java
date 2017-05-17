package com.gildedgames.aether.common.blocks;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.construction.*;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystoneButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystonePressurePlate;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootPressurePlate;
import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.walls.BlockCustomWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockScatterglassWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
import com.gildedgames.aether.common.blocks.containers.*;
import com.gildedgames.aether.common.blocks.decorative.*;
import com.gildedgames.aether.common.blocks.misc.BlockMoaEgg;
import com.gildedgames.aether.common.blocks.misc.BlockOutpostCampfire;
import com.gildedgames.aether.common.blocks.natural.*;
import com.gildedgames.aether.common.blocks.natural.ores.*;
import com.gildedgames.aether.common.blocks.natural.plants.*;
import com.gildedgames.aether.common.blocks.util.*;
import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiDummyHalf;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemAetherSlab;
import com.gildedgames.aether.common.items.blocks.ItemBlockDecorative;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.items.blocks.ItemBlockVariants;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class BlocksAether
{

	private static final List<Runnable> postRegistration = Lists.newArrayList();

	public static final Block aether_dirt = new BlockAetherDirt();

	public static final BlockAetherGrass aether_grass = new BlockAetherGrass();

	public static final BlockHolystone holystone = new BlockHolystone();

	public static final Block holystone_brick = new BlockCustom(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockHolystoneDecorative holystone_brick_decorative = new BlockHolystoneDecorative();

	public static final Block holystone_pillar = new BlockParentPillar(Material.ROCK, BlocksAether.holystone_brick)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block faded_holystone_brick = new BlockCustom(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockFadedHolystoneDecorative faded_holystone_brick_decorative = new BlockFadedHolystoneDecorative();

	public static final Block faded_holystone_pillar = new BlockParentPillar(Material.ROCK, BlocksAether.faded_holystone_brick)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block agiosite = new BlockCustom(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block agiosite_brick = new BlockCustom(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockAgiositeDecorative agiosite_brick_decorative = new BlockAgiositeDecorative();

	public static final Block agiosite_pillar = new BlockParentPillar(Material.ROCK, BlocksAether.agiosite_brick)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockSkyrootPlanks skyroot_planks = new BlockSkyrootPlanks();

	public static final Block skyroot_beam = new BlockParentPillar(Material.WOOD, BlocksAether.skyroot_planks)
			.setSoundType(SoundType.WOOD).setHardness(2.0f);

	public static final BlockAercloud aercloud = new BlockAercloud();

	public static final BlockCloudwoolBlock cloudwool_block = new BlockCloudwoolBlock();

	public static final BlockAmbrosiumOre ambrosium_ore = new BlockAmbrosiumOre();

	public static final BlockZaniteOre zanite_ore = new BlockZaniteOre();

	public static final BlockGravititeOre gravitite_ore = new BlockGravititeOre();

	public static final BlockArkeniumOre arkenium_ore = new BlockArkeniumOre();

	public static final BlockAetherLog skyroot_log = new BlockAetherLog();

	public static final BlockAetherLog golden_oak_log = new BlockGoldenOakLog();

	public static final BlockAetherLeaves blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT.getMeta());

	public static final BlockAetherLeaves green_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT.getMeta());

	public static final BlockAetherLeaves dark_blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta());

	public static final BlockAetherLeaves golden_oak_leaves = new BlockAetherLeaves(BlockAetherSapling.GOLDEN_OAK.getMeta());

	public static final BlockAetherPortal aether_portal = new BlockAetherPortal();

	public static final BlockTallAetherGrass tall_aether_grass = new BlockTallAetherGrass();

	public static final BlockQuicksoil quicksoil = new BlockQuicksoil();

	public static final BlockAetherCraftingTable aether_crafting_table = new BlockAetherCraftingTable();

	public static final BlockBlueberryBush blueberry_bush = new BlockBlueberryBush();

	public static final BlockOrangeTree orange_tree = new BlockOrangeTree();

	public static final BlockAetherFlower aether_flower = new BlockAetherFlower();

	public static final BlockAltar altar = new BlockAltar();

	public static final BlockIcestoneOre icestone_ore = new BlockIcestoneOre();

	public static final Block quicksoil_glass = new BlockRockGlass().setLightLevel(1f),
			crude_scatterglass = new BlockRockGlass(),
			scatterglass = new BlockRockGlass();

	public static final Block quicksoil_glass_decorative = new BlockRockGlassDecorative(BlocksAether.quicksoil_glass).setLightLevel(1f),
			crude_scatterglass_decorative = new BlockRockGlassDecorative(BlocksAether.crude_scatterglass),
			scatterglass_decorative = new BlockRockGlassDecorative(BlocksAether.scatterglass);

	public static final Block zanite_block = new BlockCustom(Material.IRON).setSoundType(SoundType.METAL).setHardness(5f);

	public static final Block gravitite_block = new BlockCustom(Material.ROCK).setSoundType(SoundType.STONE).setHardness(5f);

	public static final BlockAetherSapling aether_sapling = new BlockAetherSapling();

	public static final BlockHolystoneFurnace holystone_furnace = new BlockHolystoneFurnace();

	public static final BlockCustomDoor skyroot_door = new BlockCustomDoor(Material.WOOD, () -> ItemsAether.skyroot_door, SoundType.WOOD);

	public static final BlockCustomDoor secret_skyroot_door = new BlockCustomDoor(Material.WOOD, () -> ItemsAether.secret_skyroot_door, SoundType.WOOD);

	public static final BlockCustomDoor arkenium_door = new BlockCustomDoor(Material.IRON, () -> ItemsAether.arkenium_door, SoundType.METAL);

	public static final BlockSkyrootChest skyroot_chest = new BlockSkyrootChest();

	public static final BlockAmbrosiumTorch ambrosium_torch = new BlockAmbrosiumTorch();

	public static final BlockIcestoneBricks icestone_bricks = new BlockIcestoneBricks();

	public static final BlockSkyrootFence skyroot_fence = new BlockSkyrootFence();

	public static final BlockSkyrootFenceGate skyroot_fence_gate = new BlockSkyrootFenceGate();

	public static final BlockStandingSkyrootSign standing_skyroot_sign = new BlockStandingSkyrootSign();

	public static final BlockWallSkyrootSign wall_skyroot_sign = new BlockWallSkyrootSign();

	public static final BlockSkyrootTrapDoor skyroot_trapdoor = new BlockSkyrootTrapDoor();

	public static final BlockSkyrootTrapDoor secret_skyroot_trapdoor = new BlockSkyrootTrapDoor();

	public static final BlockSkyrootPressurePlate skyroot_pressure_plate = new BlockSkyrootPressurePlate();

	public static final BlockHolystonePressurePlate holystone_pressure_plate = new BlockHolystonePressurePlate();

	public static final BlockSkyrootButton skyroot_button = new BlockSkyrootButton();

	public static final BlockButtonStone holystone_button = new BlockHolystoneButton();

	public static final BlockCustomLadder skyroot_ladder = new BlockCustomLadder();

	public static final Block multiblock_dummy = new BlockMultiDummy().setBlockUnbreakable();

	public static final Block multiblock_dummy_half = new BlockMultiDummyHalf().setBlockUnbreakable();

	public static final BlockBed skyroot_bed = new BlockCustomBed(() -> ItemsAether.skyroot_bed, SoundType.CLOTH);

	public static final BlockCustomWall
			holystone_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			mossy_holystone_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			holystone_brick_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			icestone_wall = new BlockCustomWall(BlocksAether.icestone_bricks.getDefaultState(), 3.0f, 10.0f),
			scatterglass_wall = new BlockScatterglassWall(BlocksAether.scatterglass.getDefaultState(), 1.0f, 10.0f),
			skyroot_log_wall = new BlockSkyrootWall(BlocksAether.skyroot_log.getDefaultState(), 2.0f, 10.0f);

	public static final BlockCustomSlab
			skyroot_slab = new BlockCustomSlab(BlocksAether.skyroot_planks.getDefaultState()),
			holystone_slab = new BlockCustomSlab(BlocksAether.holystone.getDefaultState()),
			holystone_brick_slab = new BlockCustomSlab(BlocksAether.holystone_brick.getDefaultState()),
			icestone_slab = new BlockCustomSlab(BlocksAether.icestone_bricks.getDefaultState());

	public static final Block skyroot_stairs = new BlockCustomStairs(BlocksAether.skyroot_planks.getDefaultState()),
			holystone_stairs = new BlockCustomStairs(BlocksAether.holystone.getDefaultState()),
			mossy_holystone_stairs = new BlockCustomStairs(BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE)),
			holystone_brick_stairs = new BlockCustomStairs(BlocksAether.holystone_brick.getDefaultState()),
			icestone_brick_stairs = new BlockCustomStairs(BlocksAether.icestone_bricks.getDefaultState()),
			scatterglass_stairs = new BlockScatterglassStairs(BlocksAether.scatterglass.getDefaultState());

	public static final Block woven_sticks = new BlockWovenSticks();

	public static final BlockMoaEgg moa_egg = new BlockMoaEgg();

	public static final Block icestone_cooler = new BlockIcestoneCooler();

	public static final Block incubator = new BlockIncubator();

	public static final Block present = new BlockPresent();

	public static final Block wildcard = new BlockWildcard();

	public static final BlockMasonryBench masonry_bench = new BlockMasonryBench();

	public static final Block quicksoil_glass_pane = new BlockRockGlassPane(BlocksAether.quicksoil_glass).setLightLevel(1f),
			scatterglass_pane = new BlockRockGlassPane(BlocksAether.scatterglass),
			crude_scatterglass_pane = new BlockRockGlassPane(BlocksAether.crude_scatterglass);


	public static final Block quicksoil_glass_pane_decorative = new BlockRockGlassPaneDecorative(BlocksAether.quicksoil_glass).setLightLevel(1f),
			scatterglass_pane_decorative = new BlockRockGlassPaneDecorative(BlocksAether.scatterglass),
			crude_scatterglass_pane_decorative = new BlockRockGlassPaneDecorative(BlocksAether.crude_scatterglass);

	public static final Block skyroot_twigs = new BlockFloorObject(Material.PLANTS);

	public static final Block holystone_rock = new BlockFloorObject(Material.ROCK);

	public static BlockCustomCarpet cloudwool_carpet = new BlockCustomCarpet(BlocksAether.cloudwool_block.getDefaultState());

	public static Block skyroot_bookshelf = new BlockCustomBookshelf(Material.WOOD);

	public static Block holystone_bookshelf = new BlockCustomBookshelf(Material.ROCK);

	public static BlockOutpostCampfire outpost_campfire = new BlockOutpostCampfire(Material.IRON);

	public static Block skyroot_decorative = new BlockSkyrootDecorative();

	public static void preInit()
	{
		registerBlock("aether_dirt", BlocksAether.aether_dirt.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.aether_dirt));
		registerBlock("aether_grass", BlocksAether.aether_grass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.aether_grass));
		registerBlock("holystone", BlocksAether.holystone.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.holystone));

		registerBlock("skyroot_bed", BlocksAether.skyroot_bed);

		registerBlock("aercloud", BlocksAether.aercloud.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.aercloud));

		registerBlock("cloudwool_block", BlocksAether.cloudwool_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("ambrosium_ore", BlocksAether.ambrosium_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("zanite_ore", BlocksAether.zanite_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("gravitite_ore", BlocksAether.gravitite_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("arkenium_ore", BlocksAether.arkenium_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("icestone_ore", BlocksAether.icestone_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("skyroot_log", BlocksAether.skyroot_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("golden_oak_log", BlocksAether.golden_oak_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("blue_skyroot_leaves", BlocksAether.blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("green_skyroot_leaves", BlocksAether.green_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("dark_blue_skyroot_leaves", BlocksAether.dark_blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("golden_oak_leaves", BlocksAether.golden_oak_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("quicksoil", BlocksAether.quicksoil.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("aether_crafting_table", BlocksAether.aether_crafting_table.setCreativeTab(CreativeTabsAether.UTILITY), new ItemBlockVariants(BlocksAether.aether_crafting_table));

		registerBlock("holystone_furnace", BlocksAether.holystone_furnace.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("altar", BlocksAether.altar.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("incubator", BlocksAether.incubator.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("masonry_bench", BlocksAether.masonry_bench.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("icestone_cooler", BlocksAether.icestone_cooler.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("skyroot_chest", BlocksAether.skyroot_chest.setCreativeTab(CreativeTabsAether.UTILITY));

		registerBlock("ambrosium_torch", BlocksAether.ambrosium_torch.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("aether_sapling", BlocksAether.aether_sapling.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.aether_sapling));

		registerBlock("aether_portal", BlocksAether.aether_portal);

		registerBlock("tall_aether_grass", BlocksAether.tall_aether_grass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.tall_aether_grass));

		registerBlock("blueberry_bush", BlocksAether.blueberry_bush.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.blueberry_bush));

		registerBlock("orange_tree", BlocksAether.orange_tree.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("aether_flower", BlocksAether.aether_flower.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.aether_flower));

		registerBlock("quicksoil_glass", BlocksAether.quicksoil_glass.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("crude_scatterglass", BlocksAether.crude_scatterglass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("scatterglass", BlocksAether.scatterglass.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("quicksoil_glass_decorative", BlocksAether.quicksoil_glass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.quicksoil_glass_decorative));
		registerBlock("crude_scatterglass_decorative", BlocksAether.crude_scatterglass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.crude_scatterglass_decorative));
		registerBlock("scatterglass_decorative", BlocksAether.scatterglass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.scatterglass_decorative));

		registerBlock("zanite_block", BlocksAether.zanite_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("gravitite_block", BlocksAether.gravitite_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("holystone_brick", BlocksAether.holystone_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_brick_decorative", BlocksAether.holystone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.holystone_brick_decorative));

		registerBlock("holystone_pillar", BlocksAether.holystone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		registerBlock("faded_holystone_brick", BlocksAether.faded_holystone_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("faded_holystone_brick_decorative", BlocksAether.faded_holystone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.faded_holystone_brick_decorative));

		registerBlock("faded_holystone_pillar", BlocksAether.faded_holystone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		registerBlock("agiosite", BlocksAether.agiosite.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("agiosite_brick", BlocksAether.agiosite_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("agiosite_brick_decorative", BlocksAether.agiosite_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.agiosite_brick_decorative));

		registerBlock("agiosite_pillar", BlocksAether.agiosite_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		registerBlock("skyroot_planks", BlocksAether.skyroot_planks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("skyroot_decorative", BlocksAether.skyroot_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.skyroot_decorative));

		registerBlock("skyroot_beam", BlocksAether.skyroot_beam.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		registerBlock("skyroot_bookshelf", BlocksAether.skyroot_bookshelf.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("holystone_bookshelf", BlocksAether.holystone_bookshelf.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("skyroot_door", BlocksAether.skyroot_door);
		registerBlock("secret_skyroot_door", BlocksAether.secret_skyroot_door);
		registerBlock("arkenium_door", BlocksAether.arkenium_door);

		registerBlock("icestone_bricks", BlocksAether.icestone_bricks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("skyroot_fence", BlocksAether.skyroot_fence.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("skyroot_fence_gate", BlocksAether.skyroot_fence_gate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("standing_skyroot_sign", BlocksAether.standing_skyroot_sign);

		registerBlock("skyroot_trapdoor", BlocksAether.skyroot_trapdoor.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("secret_skyroot_trapdoor", BlocksAether.secret_skyroot_trapdoor.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		registerBlock("skyroot_ladder", BlocksAether.skyroot_ladder.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("skyroot_button", BlocksAether.skyroot_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_button", BlocksAether.holystone_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("skyroot_pressure_plate", BlocksAether.skyroot_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_pressure_plate", BlocksAether.holystone_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("wall_skyroot_sign", BlocksAether.wall_skyroot_sign);


		registerBlock("holystone_wall", BlocksAether.holystone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("mossy_holystone_wall", BlocksAether.mossy_holystone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_brick_wall", BlocksAether.holystone_brick_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("icestone_wall", BlocksAether.icestone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("skyroot_log_wall", BlocksAether.skyroot_log_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("scatterglass_wall", BlocksAether.scatterglass_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("multiblock_dummy", BlocksAether.multiblock_dummy);
		registerBlock("multiblock_dummy_half", BlocksAether.multiblock_dummy_half);

		registerBlock("skyroot_slab", BlocksAether.skyroot_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION), new ItemAetherSlab(BlocksAether.skyroot_slab));
		registerBlock("holystone_slab", BlocksAether.holystone_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION), new ItemAetherSlab(BlocksAether.holystone_slab));
		registerBlock("holystone_brick_slab", BlocksAether.holystone_brick_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION), new ItemAetherSlab(BlocksAether.holystone_brick_slab));
		registerBlock("icestone_slab", BlocksAether.icestone_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION), new ItemAetherSlab(BlocksAether.icestone_slab));

		registerBlock("skyroot_stairs", BlocksAether.skyroot_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_stairs", BlocksAether.holystone_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("mossy_holystone_stairs", BlocksAether.mossy_holystone_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("holystone_brick_stairs", BlocksAether.holystone_brick_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("icestone_brick_stairs", BlocksAether.icestone_brick_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("scatterglass_stairs", BlocksAether.scatterglass_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("woven_sticks", BlocksAether.woven_sticks.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS), new ItemBlockVariants(BlocksAether.woven_sticks));
		registerBlock("moa_egg", BlocksAether.moa_egg);

		registerBlock("present", BlocksAether.present.setCreativeTab(CreativeTabsAether.MISCELLANEOUS), new ItemBlockPresent(BlocksAether.present));

		registerBlock("wildcard", BlocksAether.wildcard);

		registerBlock("quicksoil_glass_pane", BlocksAether.quicksoil_glass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("scatterglass_pane", BlocksAether.scatterglass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		registerBlock("crude_scatterglass_pane", BlocksAether.crude_scatterglass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("quicksoil_glass_pane_decorative", BlocksAether.quicksoil_glass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.quicksoil_glass_pane_decorative));
		registerBlock("scatterglass_pane_decorative", BlocksAether.scatterglass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.scatterglass_pane_decorative));
		registerBlock("crude_scatterglass_pane_decorative", BlocksAether.crude_scatterglass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS), new ItemBlockDecorative(BlocksAether.crude_scatterglass_pane_decorative));

		registerBlock("skyroot_twigs", BlocksAether.skyroot_twigs.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		registerBlock("holystone_rock", BlocksAether.holystone_rock.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		registerBlock("cloudwool_carpet", BlocksAether.cloudwool_carpet.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		registerBlock("outpost_campfire", BlocksAether.outpost_campfire.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_twigs, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.tall_aether_grass, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.aether_flower, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.orange_tree, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.blueberry_bush, 60, 100);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.golden_oak_log, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_log_wall, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_bookshelf, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_beam, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_stairs, 5, 20);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_door, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.secret_skyroot_door, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence_gate, 5, 20);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_ladder, 5, 20);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_pressure_plate, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_slab, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_trapdoor, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.secret_skyroot_trapdoor, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_button, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.woven_sticks, 30, 60);

		Blocks.FIRE.setFireInfo(BlocksAether.blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.golden_oak_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.green_skyroot_leaves, 30, 60);

		Blocks.FIRE.setFireInfo(BlocksAether.cloudwool_block, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.cloudwool_carpet, 30, 60);

		registerHarvestLevels();

		BlocksAether.postRegistration.forEach(Runnable::run);
		BlocksAether.postRegistration.clear();
	}

	private static void registerHarvestLevels()
	{
		aether_dirt.setHarvestLevel("shovel", 0);
		aether_grass.setHarvestLevel("shovel", 0);
		holystone.setHarvestLevel("pickaxe", 0);
		holystone_brick.setHarvestLevel("pickaxe", 0);
		quicksoil.setHarvestLevel("shovel", 0);
		aercloud.setHarvestLevel("shovel", 0);

		skyroot_decorative.setHarvestLevel("axe", 0);
		skyroot_chest.setHarvestLevel("axe", 0);
		skyroot_log.setHarvestLevel("axe", 0);
		golden_oak_log.setHarvestLevel("axe", 0);
		skyroot_planks.setHarvestLevel("axe", 0);
		woven_sticks.setHarvestLevel("axe", 0);

		ambrosium_ore.setHarvestLevel("pickaxe", 0);
		zanite_ore.setHarvestLevel("pickaxe", 1);
		gravitite_ore.setHarvestLevel("pickaxe", 2);
		arkenium_ore.setHarvestLevel("pickaxe", 2);

		icestone_ore.setHarvestLevel("pickaxe", 1);
		icestone_bricks.setHarvestLevel("pickaxe", 1);
		crude_scatterglass.setHarvestLevel("pickaxe", 0);
		scatterglass.setHarvestLevel("pickaxe", 0);

		altar.setHarvestLevel("pickaxe", 0);
		holystone_furnace.setHarvestLevel("pickaxe", 0);
		aether_crafting_table.setHarvestLevel("axe", 0);
		icestone_cooler.setHarvestLevel("axe", 0);
		incubator.setHarvestLevel("axe", 0);
		masonry_bench.setHarvestLevel("axe", 0);

		zanite_block.setHarvestLevel("pickaxe", 1);
		gravitite_block.setHarvestLevel("pickaxe", 2);

		skyroot_door.setHarvestLevel("axe", 0);
		skyroot_trapdoor.setHarvestLevel("axe", 0);
		secret_skyroot_door.setHarvestLevel("axe", 0);
		secret_skyroot_trapdoor.setHarvestLevel("axe", 0);

		holystone_wall.setHarvestLevel("pickaxe", 0);
		mossy_holystone_wall.setHarvestLevel("pickaxe", 0);
		skyroot_log_wall.setHarvestLevel("axe", 0);
		icestone_wall.setHarvestLevel("pickaxe", 1);
		scatterglass_wall.setHarvestLevel("pickaxe", 1);

		skyroot_slab.setHarvestLevel("axe", 0);

		holystone_slab.setHarvestLevel("pickaxe", 0);
		holystone_brick_slab.setHarvestLevel("pickaxe", 0);
		icestone_slab.setHarvestLevel("pickaxe", 0);

		holystone_bookshelf.setHarvestLevel("pickaxe", 0);
		skyroot_bookshelf.setHarvestLevel("axe", 0);
	}

	private static void registerBlock(String name, Block block, ItemBlock item)
	{
		block.setUnlocalizedName(AetherCore.MOD_ID + "." + name);
		block.setRegistryName(name);

		GameRegistry.register(block);

		if (item != null)
		{
			registerItemBlock(item);
		}
	}

	private static void registerBlock(String name, Block block)
	{
		registerBlock(name, block, new ItemBlock(block));
	}

	private static void registerItemBlock(ItemBlock item)
	{
		item.setRegistryName(item.getBlock().getRegistryName());

		GameRegistry.register(item);
	}

	public static void applyPostRegistration(Runnable runnable)
	{
		BlocksAether.postRegistration.add(runnable);
	}

}
