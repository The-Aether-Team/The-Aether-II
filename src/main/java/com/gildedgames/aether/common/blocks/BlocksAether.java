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
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummyHalf;
import com.gildedgames.aether.common.blocks.natural.*;
import com.gildedgames.aether.common.blocks.natural.ores.*;
import com.gildedgames.aether.common.blocks.natural.plants.*;
import com.gildedgames.aether.common.blocks.util.*;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemBlockMultiName;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber()
public class BlocksAether
{
	public static final Block therastone_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockTherastoneDecorative therastone_brick_decorative = new BlockTherastoneDecorative();

	public static final Block therastone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockAetherLog therawood_log = new BlockAetherLog();

	public static final BlockAetherLeaves therawood_leaves = new BlockAetherLeaves();

	public static final BlockTherawoodPlanks therawood_planks = new BlockTherawoodPlanks();

	public static final Block therawood_beam = new BlockCustomPillar(Material.WOOD)
			.setSoundType(SoundType.WOOD).setHardness(2.0f);

	public static final Block aether_dirt = new BlockAetherDirt();

	public static final BlockAetherGrass aether_grass = new BlockAetherGrass();

	public static final BlockTheraGrass thera_grass = new BlockTheraGrass();

	public static final BlockTheraDirt thera_dirt = new BlockTheraDirt();

	public static final BlockHolystone holystone = new BlockHolystone();

	public static final BlockFerrosite ferrosite = new BlockFerrosite();

	public static final BlockRustedFerrosite rusted_ferrosite = new BlockRustedFerrosite();

	public static final Block holystone_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockHolystoneDecorative holystone_brick_decorative = new BlockHolystoneDecorative();

	public static final Block holystone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block faded_holystone_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockFadedHolystoneDecorative faded_holystone_brick_decorative = new BlockFadedHolystoneDecorative();

	public static final Block faded_holystone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block sentrystone_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block sentrystone_brick_decorative = new BlockSentrystoneDecorative();

	public static final Block sentrystone_brick_decorative_lit = new BlockSentrystoneDecorativeLit()
			.setLightLevel(0.5f);

	public static final Block sentrystone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block sentrystone_pillar_lit = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f).setLightLevel(0.5f);

	public static final Block hellfirestone_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block hellfirestone_brick_decorative = new BlockHellfirestoneDecorative();

    public static final Block hellfirestone_lantern = new BlockBuilder(Material.ROCK)
            .setSoundType(SoundType.STONE).setHardness(2.0f).setLightLevel(1.0f);

	public static final Block hellfirestone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block agiosite = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final Block agiosite_brick = new BlockBuilder(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockAgiositeDecorative agiosite_brick_decorative = new BlockAgiositeDecorative();

	public static final Block agiosite_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.STONE).setHardness(2.0f);

	public static final BlockSkyrootPlanks skyroot_planks = new BlockSkyrootPlanks();

	public static final Block skyroot_beam = new BlockCustomPillar(Material.WOOD)
			.setSoundType(SoundType.WOOD).setHardness(2.0f);

	public static final BlockSkyrootPlanks dark_skyroot_planks = new BlockSkyrootPlanks();

	public static final Block dark_skyroot_beam = new BlockCustomPillar(Material.WOOD)
			.setSoundType(SoundType.WOOD).setHardness(2.0f);

	public static final BlockSkyrootPlanks light_skyroot_planks = new BlockSkyrootPlanks();

	public static final Block light_skyroot_beam = new BlockCustomPillar(Material.WOOD)
			.setSoundType(SoundType.WOOD).setHardness(2.0f);

	public static final BlockAercloud aercloud = new BlockAercloud();

	public static final BlockCloudwoolBlock cloudwool_block = new BlockCloudwoolBlock();

	public static final BlockAmbrosiumOre ambrosium_ore = new BlockAmbrosiumOre();

	public static final BlockZaniteOre zanite_ore = new BlockZaniteOre();

	public static final BlockGravititeOre gravitite_ore = new BlockGravititeOre();

	public static final BlockArkeniumOre arkenium_ore = new BlockArkeniumOre();

	public static final BlockAetherLog skyroot_log = new BlockAetherLog();

	public static final BlockAetherLeaves blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT.getMeta());

	public static final BlockAetherLeaves green_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT.getMeta());

	public static final BlockAetherLeaves dark_blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta());

	public static final BlockAetherLog dark_skyroot_log = new BlockAetherLog();

	public static final BlockAetherLeaves blue_dark_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT.getMeta());

	public static final BlockAetherLeaves green_dark_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT.getMeta());

	public static final BlockAetherLeaves dark_blue_dark_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta());

	public static final BlockAetherLog light_skyroot_log = new BlockAetherLog();

	public static final BlockAetherLeaves blue_light_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT.getMeta());

	public static final BlockAetherLeaves green_light_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT.getMeta());

	public static final BlockAetherLeaves dark_blue_light_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta());

	public static final BlockAetherLog golden_oak_log = new BlockGoldenOakLog();

	public static final BlockAetherLeaves golden_oak_leaves = new BlockAetherLeaves(BlockAetherSapling.GOLDEN_OAK.getMeta());

	public static final BlockAetherPortal aether_portal = new BlockAetherPortal();

	public static final BlockTallAetherGrass tall_aether_grass = new BlockTallAetherGrass();

	public static final BlockQuicksoil quicksoil = new BlockQuicksoil();

	public static final BlockFerrositeSand ferrosite_sand = new BlockFerrositeSand();

	public static final BlockAetherCraftingTable aether_crafting_table = new BlockAetherCraftingTable();

	public static final BlockBlueberryBush blueberry_bush = new BlockBlueberryBush();

	public static final BlockOrangeTree orange_tree = new BlockOrangeTree();

	public static final BlockAetherFlower aether_flower = new BlockAetherFlower();

	public static final BlockValkyrieGrass valkyrie_grass = new BlockValkyrieGrass();

	public static final BlockBrettlPlant brettl_plant = new BlockBrettlPlant();

	public static final BlockAltar altar = new BlockAltar();

	public static final BlockIcestoneOre icestone_ore = new BlockIcestoneOre();

	public static final Block quicksoil_glass = new BlockRockGlassTranslucent().setLightLevel(1f),
			crude_scatterglass = new BlockRockGlass(),
			scatterglass = new BlockRockGlassTranslucent();

	public static final Block quicksoil_glass_decorative = new BlockRockGlassDecorative().setLightLevel(1f),
			crude_scatterglass_decorative = new BlockRockGlassDecorative(),
			scatterglass_decorative = new BlockRockGlassDecorative();

	public static final Block zanite_block = new BlockBuilder(Material.IRON).setSoundType(SoundType.METAL).setHardness(5f);

	public static final Block gravitite_block = new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(5f);

	public static final BlockAetherSapling aether_sapling = new BlockAetherSapling();

	public static final BlockHolystoneFurnace holystone_furnace = new BlockHolystoneFurnace();

	public static final BlockCustomDoor skyroot_door = new BlockCustomDoor(Material.WOOD, () -> ItemsAether.skyroot_door, SoundType.WOOD);

	public static final BlockCustomDoor secret_skyroot_door = new BlockCustomDoor(Material.WOOD, () -> ItemsAether.secret_skyroot_door, SoundType.WOOD);

	public static final BlockCustomDoor arkenium_door = new BlockCustomDoor(Material.IRON, () -> ItemsAether.arkenium_door, SoundType.METAL);

	public static final BlockSkyrootChest skyroot_chest = new BlockSkyrootChest();

	public static final BlockAmbrosiumTorch ambrosium_torch = new BlockAmbrosiumTorch();

	public static final BlockIcestoneBricks icestone_bricks = new BlockIcestoneBricks();

	public static final BlockIcestoneBricksDecorative icestone_bricks_decorative = new BlockIcestoneBricksDecorative();

	public static final Block icestone_pillar = new BlockCustomPillar(Material.ROCK)
			.setSoundType(SoundType.GLASS).setHardness(2.0f);

	public static final BlockSkyrootFence skyroot_fence = new BlockSkyrootFence();

	public static final BlockSkyrootFenceGate skyroot_fence_gate = new BlockSkyrootFenceGate();

	public static final BlockSkyrootFence wisproot_fence = new BlockSkyrootFence();

	public static final BlockSkyrootFenceGate wisproot_fence_gate = new BlockSkyrootFenceGate();

	public static final BlockSkyrootFence greatroot_fence = new BlockSkyrootFence();

	public static final BlockSkyrootFenceGate greatroot_fence_gate = new BlockSkyrootFenceGate();

	public static final BlockStandingSkyrootSign standing_skyroot_sign = new BlockStandingSkyrootSign();

	public static final BlockWallSkyrootSign wall_skyroot_sign = new BlockWallSkyrootSign();

	public static final BlockSkyrootTrapDoor skyroot_trapdoor = new BlockSkyrootTrapDoor();

	public static final BlockSkyrootTrapDoor secret_skyroot_trapdoor = new BlockSkyrootTrapDoor();

	public static final BlockSkyrootPressurePlate skyroot_pressure_plate = new BlockSkyrootPressurePlate();

	public static final BlockSkyrootPressurePlate wisproot_pressure_plate = new BlockSkyrootPressurePlate();

	public static final BlockSkyrootPressurePlate greatroot_pressure_plate = new BlockSkyrootPressurePlate();

	public static final BlockHolystonePressurePlate holystone_pressure_plate = new BlockHolystonePressurePlate();

	public static final BlockSkyrootButton skyroot_button = new BlockSkyrootButton();

	public static final BlockSkyrootButton wisproot_button = new BlockSkyrootButton();

	public static final BlockSkyrootButton greatroot_button = new BlockSkyrootButton();

	public static final BlockButtonStone holystone_button = new BlockHolystoneButton();

	public static final BlockCustomLadder skyroot_ladder = new BlockCustomLadder();

	public static final Block multiblock_dummy = new BlockMultiDummy();

	public static final Block multiblock_dummy_half = new BlockMultiDummyHalf();

	public static final BlockBed skyroot_bed = new BlockCustomBed(() -> ItemsAether.skyroot_bed, SoundType.CLOTH);

	public static final Block plumproot = new BlockPlumproot();

	public static final BlockCustomWall
			holystone_wall = new BlockCustomWall(holystone.getDefaultState(), 1.0f, 10.0f),
			mossy_holystone_wall = new BlockCustomWall(holystone.getDefaultState(), 1.0f, 10.0f),
			holystone_brick_wall = new BlockCustomWall(holystone.getDefaultState(), 1.0f, 10.0f),
			icestone_wall = new BlockCustomWall(icestone_bricks.getDefaultState(), 3.0f, 10.0f),
			scatterglass_wall = new BlockScatterglassWall(scatterglass.getDefaultState(), 1.0f, 10.0f),
			skyroot_log_wall = new BlockSkyrootWall(skyroot_log.getDefaultState(), 2.0f, 10.0f);

	public static final Block
			skyroot_slab = new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f),
			wisproot_slab = new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f),
			greatroot_slab = new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f),
			therawood_slab = new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f),
			holystone_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			therastone_brick_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			mossy_holystone_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			holystone_brick_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			faded_holystone_brick_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			agiosite_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			agiosite_brick_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2.0f),
			icestone_slab = new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.GLASS).setHardness(3.0f),
			scatterglass_slab = new BlockScatterglassSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.0f);

	public static final Block skyroot_stairs = new BlockCustomStairs(skyroot_planks.getDefaultState()),
			wisproot_stairs = new BlockCustomStairs(light_skyroot_planks.getDefaultState()),
			greatroot_stairs = new BlockCustomStairs(dark_skyroot_planks.getDefaultState()),
			therawood_stairs = new BlockCustomStairs(therawood_planks.getDefaultState()),
			holystone_stairs = new BlockCustomStairs(holystone.getDefaultState()),
			therastone_brick_stairs = new BlockCustomStairs(therastone_brick.getDefaultState()),
			mossy_holystone_stairs = new BlockCustomStairs(
					holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE)),
			holystone_brick_stairs = new BlockCustomStairs(holystone_brick.getDefaultState()),
			faded_holystone_brick_stairs = new BlockCustomStairs(faded_holystone_brick.getDefaultState()),
			agiosite_stairs = new BlockCustomStairs(agiosite.getDefaultState()),
			agiosite_brick_stairs = new BlockCustomStairs(agiosite_brick.getDefaultState()),
			icestone_brick_stairs = new BlockCustomStairs(icestone_bricks.getDefaultState()),
			scatterglass_stairs = new BlockScatterglassStairs(scatterglass.getDefaultState());

	public static final Block woven_sticks = new BlockWovenSticks();

	public static final BlockMoaEgg moa_egg = new BlockMoaEgg();

	public static final Block icestone_cooler = new BlockIcestoneCooler();

	public static final Block incubator = new BlockIncubator();

	public static final Block present = new BlockPresent();

	public static final Block wildcard = new BlockWildcard();

	public static final BlockMasonryBench masonry_bench = new BlockMasonryBench();

	public static final Block quicksoil_glass_pane = new BlockRockGlassPane(quicksoil_glass).setLightLevel(1f),
			scatterglass_pane = new BlockRockGlassPane(scatterglass),
			crude_scatterglass_pane = new BlockRockGlassPane(crude_scatterglass);

	public static final Block quicksoil_glass_pane_decorative = new BlockRockGlassPaneDecorative().setLightLevel(1f),
			scatterglass_pane_decorative = new BlockRockGlassPaneDecorative(),
			crude_scatterglass_pane_decorative = new BlockRockGlassPaneDecorative();

	public static final Block skyroot_twigs = new BlockFloorObject(Material.PLANTS, SoundType.WOOD);

	public static final Block holystone_rock = new BlockFloorObject(Material.ROCK, SoundType.STONE);

	private static final Set<Block> registeredBlocks = new HashSet<>();

	public static BlockCustomSnowBlock highlands_snow = new BlockCustomSnowBlock();

	public static BlockCustomSnow highlands_snow_layer = new BlockCustomSnow();

	public static Block highlands_ice = new BlockCustomIce().setHardness(0.5F).setLightOpacity(3);

	public static Block highlands_packed_ice = new BlockCustomPackedIce().setHardness(0.5F);

	public static Block therawood_decorative = new BlockTherawoodDecorative();

	public static BlockCustomCarpet cloudwool_carpet = new BlockCustomCarpet();

	public static Block skyroot_bookshelf = new BlockCustomBookshelf(Material.WOOD, SoundType.WOOD);

	public static Block holystone_bookshelf = new BlockCustomBookshelf(Material.ROCK, SoundType.STONE);

	public static BlockOutpostCampfire outpost_campfire = new BlockOutpostCampfire(Material.IRON);

	public static Block skyroot_decorative = new BlockSkyrootDecorative();

	public static Block light_skyroot_decorative = new BlockLightSkyrootDecorative();

	public static Block dark_skyroot_decorative = new BlockDarkSkyrootDecorative();

	public static BlockAetherTeleporter aether_teleporter = new BlockAetherTeleporter();

	public static BlockIceCrystal ice_crystal = new BlockIceCrystal();

	public static Block candy_cane_block = new BlockCandyCane();

	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
	{
		final BlockRegistryHelper r = new BlockRegistryHelper(event.getRegistry());

		r.register("therastone_brick", therastone_brick.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therastone_brick_decorative", therastone_brick_decorative.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therastone_pillar", therastone_pillar.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therawood_log", therawood_log.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therawood_leaves", therawood_leaves.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therawood_planks", therawood_planks.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therawood_decorative", therawood_decorative.setCreativeTab(CreativeTabsAether.THERA));
		r.register("therawood_beam", therawood_beam.setCreativeTab(CreativeTabsAether.THERA));
		r.register("thera_grass", thera_grass.setCreativeTab(CreativeTabsAether.THERA));
		r.register("thera_dirt", thera_dirt.setCreativeTab(CreativeTabsAether.THERA));

		r.register("aether_dirt", aether_dirt.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("aether_grass", aether_grass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("holystone", holystone.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("ferrosite", ferrosite.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("rusted_ferrosite", rusted_ferrosite.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("highlands_snow", highlands_snow.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("highlands_snow_layer", highlands_snow_layer.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("highlands_ice", highlands_ice.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("highlands_packed_ice", highlands_packed_ice.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("skyroot_bed", skyroot_bed);

		r.register("aercloud", aercloud.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("cloudwool_block", cloudwool_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("ambrosium_ore", ambrosium_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("zanite_ore", zanite_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("gravitite_ore", gravitite_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("arkenium_ore", arkenium_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("icestone_ore", icestone_ore.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("quicksoil", quicksoil.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("ferrosite_sand", ferrosite_sand.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("aether_crafting_table", aether_crafting_table.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("holystone_furnace", holystone_furnace.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("altar", altar.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("incubator", incubator.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("masonry_bench", masonry_bench.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("icestone_cooler", icestone_cooler.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("skyroot_chest", skyroot_chest.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("ambrosium_torch", ambrosium_torch.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("quicksoil_glass", quicksoil_glass.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("crude_scatterglass", crude_scatterglass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("scatterglass", scatterglass.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("quicksoil_glass_decorative", quicksoil_glass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("crude_scatterglass_decorative", crude_scatterglass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("scatterglass_decorative", scatterglass_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("skyroot_log", skyroot_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("blue_skyroot_leaves", blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("green_skyroot_leaves", green_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("dark_blue_skyroot_leaves", dark_blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("dark_skyroot_log", dark_skyroot_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("blue_dark_skyroot_leaves", blue_dark_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("green_dark_skyroot_leaves", green_dark_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("dark_blue_dark_skyroot_leaves", dark_blue_dark_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("light_skyroot_log", light_skyroot_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("blue_light_skyroot_leaves", blue_light_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("green_light_skyroot_leaves", green_light_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("dark_blue_light_skyroot_leaves", dark_blue_light_skyroot_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("golden_oak_log", golden_oak_log.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("golden_oak_leaves", golden_oak_leaves.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("woven_sticks", woven_sticks.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("aether_sapling", aether_sapling.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("aether_portal", aether_portal);

		r.register("tall_aether_grass", tall_aether_grass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("blueberry_bush", blueberry_bush.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("orange_tree", orange_tree.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("valkyrie_grass", valkyrie_grass.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("brettl_plant", brettl_plant);

		r.register("aether_flower", aether_flower.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("zanite_block", zanite_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("gravitite_block", gravitite_block.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("holystone_brick", holystone_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_brick_decorative", holystone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("holystone_pillar", holystone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("faded_holystone_brick", faded_holystone_brick.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("faded_holystone_brick_decorative", faded_holystone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("faded_holystone_pillar", faded_holystone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("agiosite", agiosite.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("agiosite_brick", agiosite_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("agiosite_brick_decorative", agiosite_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("agiosite_pillar", agiosite_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("skyroot_planks", skyroot_planks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("skyroot_decorative", skyroot_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("skyroot_beam", skyroot_beam.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("dark_skyroot_planks", dark_skyroot_planks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("dark_skyroot_decorative", dark_skyroot_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("dark_skyroot_beam", dark_skyroot_beam.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("light_skyroot_planks", light_skyroot_planks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("light_skyroot_decorative", light_skyroot_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("light_skyroot_beam", light_skyroot_beam.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("skyroot_bookshelf", skyroot_bookshelf.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("holystone_bookshelf", holystone_bookshelf.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_door", skyroot_door);
		r.register("secret_skyroot_door", secret_skyroot_door);
		r.register("arkenium_door", arkenium_door);

		r.register("icestone_bricks", icestone_bricks.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("icestone_bricks_decorative", icestone_bricks_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("icestone_pillar", icestone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("skyroot_fence", skyroot_fence.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("skyroot_fence_gate", skyroot_fence_gate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("wisproot_fence", wisproot_fence.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("wisproot_fence_gate", wisproot_fence_gate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("greatroot_fence", greatroot_fence.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("greatroot_fence_gate", greatroot_fence_gate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("standing_skyroot_sign", standing_skyroot_sign);

		r.register("skyroot_trapdoor", skyroot_trapdoor.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_ladder", skyroot_ladder.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_button", skyroot_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("wisproot_button", wisproot_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("greatroot_button", greatroot_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_button", holystone_button.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_pressure_plate", skyroot_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("wisproot_pressure_plate", wisproot_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("greatroot_pressure_plate", greatroot_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_pressure_plate", holystone_pressure_plate.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("wall_skyroot_sign", wall_skyroot_sign);

		r.register("holystone_wall", holystone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("mossy_holystone_wall", mossy_holystone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_brick_wall", holystone_brick_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("icestone_wall", icestone_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("skyroot_log_wall", skyroot_log_wall.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("scatterglass_wall", scatterglass_wall.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("multiblock_dummy", multiblock_dummy);
		r.register("multiblock_dummy_half", multiblock_dummy_half);

		r.register("skyroot_slab", skyroot_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("wisproot_slab", wisproot_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("greatroot_slab", greatroot_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("therawood_slab", therawood_slab.setCreativeTab(CreativeTabsAether.THERA));
		r.register("holystone_slab", holystone_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("therastone_brick_slab", therastone_brick_slab.setCreativeTab(CreativeTabsAether.THERA));
		r.register("mossy_holystone_slab", mossy_holystone_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_brick_slab", holystone_brick_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("faded_holystone_brick_slab", faded_holystone_brick_slab.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("agiosite_slab", agiosite_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("agiosite_brick_slab", agiosite_brick_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("icestone_slab", icestone_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("scatterglass_slab", scatterglass_slab.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("skyroot_stairs", skyroot_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("wisproot_stairs", wisproot_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("greatroot_stairs", greatroot_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("therawood_stairs", therawood_stairs.setCreativeTab(CreativeTabsAether.THERA));
		r.register("holystone_stairs", holystone_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("therastone_brick_stairs", therastone_brick_stairs.setCreativeTab(CreativeTabsAether.THERA));
		r.register("mossy_holystone_stairs", mossy_holystone_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("holystone_brick_stairs", holystone_brick_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("faded_holystone_brick_stairs", faded_holystone_brick_stairs.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("agiosite_stairs", agiosite_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("agiosite_brick_stairs", agiosite_brick_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("icestone_brick_stairs", icestone_brick_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("scatterglass_stairs", scatterglass_stairs.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("moa_egg", moa_egg);

		r.register("present", present.setCreativeTab(CreativeTabsAether.MISCELLANEOUS));

		r.register("wildcard", wildcard);

		r.register("quicksoil_glass_pane", quicksoil_glass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("scatterglass_pane", scatterglass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("crude_scatterglass_pane", crude_scatterglass_pane.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("quicksoil_glass_pane_decorative", quicksoil_glass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("scatterglass_pane_decorative", scatterglass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("crude_scatterglass_pane_decorative", crude_scatterglass_pane_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("secret_skyroot_trapdoor", secret_skyroot_trapdoor.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("skyroot_twigs", skyroot_twigs.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));
		r.register("holystone_rock", holystone_rock.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("cloudwool_carpet", cloudwool_carpet.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("outpost_campfire", outpost_campfire.setCreativeTab(CreativeTabsAether.CONSTRUCTION));

		r.register("aether_teleporter", aether_teleporter.setCreativeTab(CreativeTabsAether.UTILITY));

		r.register("sentrystone_brick", sentrystone_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
		r.register("sentrystone_brick_decorative", sentrystone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("sentrystone_brick_decorative_lit", sentrystone_brick_decorative_lit.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("sentrystone_pillar", sentrystone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("sentrystone_pillar_lit", sentrystone_pillar_lit.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

        r.register("hellfirestone_brick", hellfirestone_brick.setCreativeTab(CreativeTabsAether.CONSTRUCTION));
        r.register("hellfirestone_brick_decorative", hellfirestone_brick_decorative.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
        r.register("hellfirestone_lantern", hellfirestone_lantern.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
        r.register("hellfirestone_pillar", hellfirestone_pillar.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));
		r.register("candy_cane_block", candy_cane_block.setCreativeTab(CreativeTabsAether.DECORATIVE_BLOCKS));

		r.register("plumproot", plumproot.setCreativeTab(CreativeTabsAether.NATURAL_BLOCKS));

		r.register("highlands_ice_crystal", ice_crystal);

		Blocks.FIRE.setFireInfo(plumproot, 60, 100);
		Blocks.FIRE.setFireInfo(skyroot_twigs, 60, 100);
		Blocks.FIRE.setFireInfo(tall_aether_grass, 60, 100);
		Blocks.FIRE.setFireInfo(aether_flower, 60, 100);
		Blocks.FIRE.setFireInfo(orange_tree, 60, 100);
		Blocks.FIRE.setFireInfo(blueberry_bush, 60, 100);
		Blocks.FIRE.setFireInfo(valkyrie_grass, 60, 100);
		Blocks.FIRE.setFireInfo(brettl_plant, 60, 100);

		Blocks.FIRE.setFireInfo(skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(light_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(dark_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(golden_oak_log, 5, 5);

		Blocks.FIRE.setFireInfo(skyroot_log_wall, 5, 5);
		Blocks.FIRE.setFireInfo(skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_bookshelf, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_beam, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_decorative, 5, 20);

		Blocks.FIRE.setFireInfo(light_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(light_skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(light_skyroot_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(light_skyroot_beam, 5, 20);

		Blocks.FIRE.setFireInfo(therawood_log, 5, 5);
		Blocks.FIRE.setFireInfo(therawood_planks, 5, 20);
		Blocks.FIRE.setFireInfo(therawood_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(therawood_beam, 5, 20);

		Blocks.FIRE.setFireInfo(dark_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(dark_skyroot_planks, 5, 20);

		Blocks.FIRE.setFireInfo(skyroot_door, 5, 20);
		Blocks.FIRE.setFireInfo(secret_skyroot_door, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_fence, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_fence_gate, 5, 20);

		Blocks.FIRE.setFireInfo(skyroot_ladder, 5, 20);

		Blocks.FIRE.setFireInfo(skyroot_pressure_plate, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_slab, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_trapdoor, 5, 20);
		Blocks.FIRE.setFireInfo(secret_skyroot_trapdoor, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(skyroot_button, 5, 20);
		Blocks.FIRE.setFireInfo(woven_sticks, 30, 60);

		Blocks.FIRE.setFireInfo(green_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(dark_blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(golden_oak_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(blue_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(dark_blue_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(green_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(blue_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(dark_blue_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(green_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(therawood_leaves, 30, 60);

		Blocks.FIRE.setFireInfo(cloudwool_block, 30, 60);
		Blocks.FIRE.setFireInfo(cloudwool_carpet, 30, 60);

		registerHarvestLevels();
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event)
	{
		for (final Block block : registeredBlocks)
		{
			final ItemBlock item;

			if (block instanceof IInternalBlock)
			{
				continue;
			}
			else if (block instanceof IBlockWithItem)
			{
				item = ((IBlockWithItem) block).createItemBlock();
			}
			else if (block instanceof IBlockMultiName)
			{
				item = new ItemBlockMultiName(block);
			}
			else
			{
				item = new ItemBlock(block);
			}

			if (block.getRegistryName() == null)
			{
				throw new RuntimeException("Registry name of block cannot be null");
			}

			String registryName = block.getRegistryName().getPath();

			item.setTranslationKey(AetherCore.MOD_ID + "." + registryName);
			item.setRegistryName(AetherCore.MOD_ID, registryName);

			event.getRegistry().register(item);
		}
	}

	private static void registerHarvestLevels()
	{
		aether_dirt.setHarvestLevel("shovel", 0);
		aether_grass.setHarvestLevel("shovel", 0);
		holystone.setHarvestLevel("pickaxe", 0);
		holystone_brick.setHarvestLevel("pickaxe", 0);
		quicksoil.setHarvestLevel("shovel", 0);
		aercloud.setHarvestLevel("shovel", 0);
		highlands_snow.setHarvestLevel("shovel", 0);
		highlands_snow_layer.setHarvestLevel("shovel", 0);

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
		highlands_ice.setHarvestLevel("pickaxe", 0);
		highlands_packed_ice.setHarvestLevel("pickaxe", 0);

		altar.setHarvestLevel("pickaxe", 0);
		holystone_furnace.setHarvestLevel("pickaxe", 0);
		aether_crafting_table.setHarvestLevel("axe", 0);
		icestone_cooler.setHarvestLevel("pickaxe", 0);
		incubator.setHarvestLevel("pickaxe", 0);
		masonry_bench.setHarvestLevel("pickaxe", 0);

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
		candy_cane_block.setHarvestLevel("pickaxe", 0);
	}

	public static Collection<Block> getRegisteredBlocks()
	{
		return Collections.unmodifiableCollection(registeredBlocks);
	}

	private static class BlockRegistryHelper
	{
		private final IForgeRegistry<Block> registry;

		BlockRegistryHelper(final IForgeRegistry<Block> registry)
		{
			this.registry = registry;
		}

		private void register(final String registryName, final Block block)
		{
			block.setTranslationKey(AetherCore.MOD_ID + "." + registryName);
			block.setRegistryName(AetherCore.MOD_ID, registryName);

			this.registry.register(block);

			registeredBlocks.add(block);
		}
	}
}
