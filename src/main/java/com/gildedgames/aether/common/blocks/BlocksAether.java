package com.gildedgames.aether.common.blocks;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.containers.*;
import com.gildedgames.aether.common.blocks.dungeon.*;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.blocks.construction.*;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystoneButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystonePressurePlate;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootPressurePlate;
import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.walls.BlockScatterglassWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockCustomWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockDivineWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
import com.gildedgames.aether.common.blocks.misc.BlockMoaEgg;
import com.gildedgames.aether.common.blocks.natural.*;
import com.gildedgames.aether.common.blocks.natural.ores.*;
import com.gildedgames.aether.common.blocks.natural.plants.*;
import com.gildedgames.aether.common.blocks.util.*;
import com.gildedgames.aether.common.blocks.util.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemAetherSlab;
import com.gildedgames.aether.common.items.blocks.ItemBlockVariants;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class BlocksAether
{

	private static final List<Runnable> postRegistration = Lists.newArrayList();

	public static final Block aether_dirt = new BlockAetherDirt();

	public static final BlockAetherGrass aether_grass = new BlockAetherGrass();

	public static final BlockHolystone holystone = new BlockHolystone();

	public static final Block holystone_brick = new BlockCustom(Material.ROCK).setSoundType(SoundType.STONE).setHardness(2f);

	public static final BlockAercloud aercloud = new BlockAercloud();

	public static final BlockAmbrosiumOre ambrosium_ore = new BlockAmbrosiumOre();

	public static final BlockZaniteOre zanite_ore = new BlockZaniteOre();

	public static final BlockGravititeOre gravitite_ore = new BlockGravititeOre();

	public static final BlockArkeniumOre arkenium_ore = new BlockArkeniumOre();

	public static final Block aether_planks = new BlockAetherPlanks();

	public static final BlockAetherLog skyroot_log = new BlockAetherLog(),
			golden_oak_log = new BlockGoldenOakLog(),
			frostpine_log = new BlockAetherLog(),
			blightwillow_log = new BlockAetherLog(),
			earthshifter_log = new BlockAetherLog();

	public static final BlockAetherLeaves blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.BLUE_SKYROOT.getMeta()),
			green_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.GREEN_SKYROOT.getMeta()),
			dark_blue_skyroot_leaves = new BlockAetherLeaves(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta()),
			golden_oak_leaves = new BlockAetherLeaves(BlockAetherSapling.GOLDEN_OAK.getMeta()),
			blighted_leaves = new BlockAetherLeaves(BlockAetherSapling.BLIGHTED.getMeta()),
			frostpine_leaves = new BlockAetherLeaves(BlockAetherSapling.FROSTPINE.getMeta()),
			blightwillow_leaves = new BlockAetherLeaves(BlockAetherSapling.BLIGHTWILLOW.getMeta()),
			earthshifter_leaves = new BlockAetherLeaves(BlockAetherSapling.EARTHSHIFTER.getMeta()),
			vined_earthshifter_leaves = new BlockAetherLeaves(BlockAetherSapling.EARTHSHIFTER.getMeta());

	public static final BlockAetherPortal aether_portal = new BlockAetherPortal();

	public static final BlockTallAetherGrass tall_aether_grass = new BlockTallAetherGrass();

	public static final BlockQuicksoil quicksoil = new BlockQuicksoil();

	public static final BlockAetherCraftingTable aether_crafting_table = new BlockAetherCraftingTable();

	public static final BlockBlueberryBush blueberry_bush = new BlockBlueberryBush();

	public static final BlockBlueberryBush enchanted_blueberry_bush = new BlockBlueberryBush();

	public static final BlockOrangeTree orange_tree = new BlockOrangeTree();

	public static final BlockAetherFlower aether_flower = new BlockAetherFlower();

	public static final BlockAltar altar = new BlockAltar();

	public static final BlockIcestoneOre icestone_ore = new BlockIcestoneOre();

	public static final BlockCrudeScatterglass crude_scatterglass = new BlockCrudeScatterglass();

	public static final BlockScatterglass scatterglass = new BlockScatterglass();

	public static final Block zanite_block = new BlockCustom(Material.IRON).setSoundType(SoundType.METAL).setHardness(5f);

	public static final Block enchanted_gravitite = new BlockCustom(Material.ROCK).setSoundType(SoundType.STONE).setHardness(5f);

	public static final BlockAetherSapling aether_sapling = new BlockAetherSapling();

	public static final BlockLabyrinth
			carved_stone = new BlockDivine(),
			sentry_stone = new BlockDivine().setLightLevel(0.50f);

	public static final BlockHolystoneFurnace holystone_furnace = new BlockHolystoneFurnace();

	public static final BlockQuicksoilGlass quicksoil_glass = new BlockQuicksoilGlass();

	public static final BlockCustomDoor skyroot_door = new BlockCustomDoor(Material.WOOD, new Supplier<Item>()
	{
		@Override
		public Item get()
		{
			return ItemsAether.skyroot_door;
		}
	}, SoundType.WOOD);

	public static final BlockCustomDoor arkenium_door = new BlockCustomDoor(Material.IRON, new Supplier<Item>()
	{
		@Override
		public Item get()
		{
			return ItemsAether.arkenium_door;
		}
	}, SoundType.METAL);

	public static final Block blightwillow_door = new BlockCustomDoor(Material.WOOD, new Supplier<Item>()
	{
		@Override
		public Item get()
		{
			return ItemsAether.blightwillow_door;
		}
	}, SoundType.WOOD).setLightLevel(0.6F);

	public static final BlockCustomDoor earthshifter_door = new BlockCustomDoor(Material.WOOD, new Supplier<Item>()
	{
		@Override
		public Item get()
		{
			return ItemsAether.earthshifter_door;
		}
	}, SoundType.WOOD);

	public static final BlockCustomDoor frostpine_door = new BlockCustomDoor(Material.WOOD, new Supplier<Item>()
	{
		@Override
		public Item get()
		{
			return ItemsAether.frostpine_door;
		}
	}, SoundType.WOOD);

	public static final BlockSkyrootChest skyroot_chest = new BlockSkyrootChest();

	public static final BlockAmbrosiumTorch ambrosium_torch = new BlockAmbrosiumTorch();

	public static final BlockIcestoneBricks icestone_bricks = new BlockIcestoneBricks();

	public static final BlockSkyrootFence skyroot_fence = new BlockSkyrootFence();

	public static final BlockSkyrootFenceGate skyroot_fence_gate = new BlockSkyrootFenceGate();

	public static final BlockStandingSkyrootSign standing_skyroot_sign = new BlockStandingSkyrootSign();

	public static final BlockWallSkyrootSign wall_skyroot_sign = new BlockWallSkyrootSign();

	public static final BlockSkyrootTrapDoor skyroot_trapdoor = new BlockSkyrootTrapDoor();

	public static final BlockSkyrootPressurePlate skyroot_pressure_plate = new BlockSkyrootPressurePlate();

	public static final BlockHolystonePressurePlate holystone_pressure_plate = new BlockHolystonePressurePlate();

	public static final BlockSkyrootButton skyroot_button = new BlockSkyrootButton();

	public static final BlockButtonStone holystone_button = new BlockHolystoneButton();

	public static final BlockCustomLadder skyroot_ladder = new BlockCustomLadder(),
										blightwillow_ladder = new BlockCustomLadder(),
										earthshifter_ladder = new BlockCustomLadder(),
										frostpine_ladder = new BlockCustomLadder();

	public static final BlockLabyrinthTotem labyrinth_totem = new BlockLabyrinthTotem(Material.IRON);

	public static final Block multiblock_dummy = new BlockMultiDummy().setBlockUnbreakable();

	public static final BlockLabyrinth labyrinth_capstone = new BlockLabyrinth(),
			unstable_labyrinth_capstone = new BlockLabyrinth();

	public static final BlockLabyrinthBridge labyrinth_bridge = new BlockLabyrinthBridge();

	public static final BlockBed skyroot_bed = new BlockCustomBed(new Supplier<Item>()
	{
		@Override public Item get()
		{
			return ItemsAether.skyroot_bed;
		}
	}, SoundType.WOOD);

	public static final Block
			labyrinth_glowing_pillar = new BlockLabyrinthPillar(),
			labyrinth_pillar = new BlockLabyrinthPillar();

	public static final BlockLabyrinthChest labyrinth_chest = new BlockLabyrinthChest();

	public static final BlockLabyrinth
			labyrinth_wall = new BlockLabyrinth(),
			labyrinth_lightstone = new BlockLabyrinth(),
			labyrinth_base = new BlockLabyrinth(),
			labyrinth_headstone =  new BlockLabyrinth();

	public static final BlockCustomWall
			holystone_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			mossy_holystone_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			holystone_brick_wall = new BlockCustomWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			carved_stone_wall = new BlockCustomWall(BlocksAether.carved_stone.getDefaultState(), 1.0f, 10.0f),
			icestone_wall = new BlockCustomWall(BlocksAether.icestone_bricks.getDefaultState(), 3.0f, 10.0f),
			scatterglass_wall = new BlockScatterglassWall(BlocksAether.scatterglass.getDefaultState(), 1.0f, 10.0f),
			skyroot_log_wall = new BlockSkyrootWall(BlocksAether.skyroot_log.getDefaultState(), 2.0f, 10.0f),
			divine_stone_wall = new BlockDivineWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f),
			sentry_stone_wall = new BlockCustomWall(BlocksAether.labyrinth_lightstone.getDefaultState(), 1.0f, 10.0f),
			divine_sentry_wall = new BlockDivineWall(BlocksAether.holystone.getDefaultState(), 1.0f, 10.0f);

	public static final BlockCustomSlab
			skyroot_slab = new BlockCustomSlab(BlocksAether.aether_planks.getDefaultState()),
			holystone_slab = new BlockCustomSlab(BlocksAether.holystone.getDefaultState()),
			holystone_brick_slab =  new BlockCustomSlab(BlocksAether.holystone_brick.getDefaultState()),
			carved_stone_slab = new BlockCustomSlab(BlocksAether.carved_stone.getDefaultState()),
			divine_carved_stone_slab = new BlockCustomSlab(BlocksAether.carved_stone.getDefaultState().withProperty(BlockDivine.PROPERTY_IS_DIVINE, true)),
			sentry_stone_slab = new BlockCustomSlab(BlocksAether.sentry_stone.getDefaultState()),
			divine_sentry_stone_slab = new BlockCustomSlab(BlocksAether.sentry_stone.getDefaultState().withProperty(BlockDivine.PROPERTY_IS_DIVINE, true)),
			icestone_slab = new BlockCustomSlab(BlocksAether.icestone_bricks.getDefaultState()),
			labyrinth_capstone_slab = new BlockCustomSlab(BlocksAether.labyrinth_capstone.getDefaultState()),
			labyrinth_wall_slab = new BlockCustomSlab(BlocksAether.labyrinth_wall.getDefaultState());

	public static final Block labyrinth_strongblock = new BlockLabyrinth().setBlockUnbreakable().setResistance(6000000.0F);

	public static final Block skyroot_stairs = new BlockCustomStairs(BlocksAether.aether_planks.getDefaultState()),
			holystone_stairs = new BlockCustomStairs(BlocksAether.holystone.getDefaultState()),
			mossy_holystone_stairs = new BlockCustomStairs(BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE)),
			holystone_brick_stairs = new BlockCustomStairs(BlocksAether.holystone_brick.getDefaultState()),
			sentry_stone_stairs = new BlockCustomStairs(BlocksAether.sentry_stone.getDefaultState()),
			divine_sentry_stone_stairs = new BlockCustomStairs(BlocksAether.sentry_stone.getDefaultState().withProperty(BlockDivine.PROPERTY_IS_DIVINE, true)),
			carved_stone_stairs = new BlockCustomStairs(BlocksAether.carved_stone.getDefaultState().withProperty(BlockDivine.PROPERTY_IS_DIVINE, true)),
			divine_carved_stone_stairs = new BlockCustomStairs(BlocksAether.carved_stone.getDefaultState().withProperty(BlockDivine.PROPERTY_IS_DIVINE, true)),
			icestone_brick_stairs = new BlockCustomStairs(BlocksAether.icestone_bricks.getDefaultState()),
			scatterglass_stairs = new BlockScatterglassStairs(BlocksAether.scatterglass.getDefaultState());

	public static final Block woven_sticks = new BlockWovenSticks();

	public static final BlockMoaEgg moa_egg = new BlockMoaEgg();

	public static final Block structure_extended = new BlockStructureExtended().setBlockUnbreakable().setResistance(6000000.0F);

	public static final BlockVine skyroot_vines = new BlockVineNoGrowth(), enchanted_skyroot_vines = new BlockVineNoGrowth();

	public static final Block frostpine_cooler = new BlockFrostpineCooler();

	public static final Block incubator = new BlockIncubator();

	public static final Block present = new BlockPresent();

	public static final BlockLabyrinthContainer labyrinth_container = new BlockLabyrinthContainer();

	public static void preInit()
	{
		registerBlock("aether_dirt", BlocksAether.aether_dirt.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_dirt));
		registerBlock("aether_grass", BlocksAether.aether_grass.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_grass));
		registerBlock("holystone", BlocksAether.holystone.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.holystone));

		registerBlock("skyroot_bed", BlocksAether.skyroot_bed);

		registerBlock("aercloud", BlocksAether.aercloud.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aercloud));

		registerBlock("aether_planks", BlocksAether.aether_planks.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_planks));

		registerBlock("ambrosium_ore", BlocksAether.ambrosium_ore.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("zanite_ore", BlocksAether.zanite_ore.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("gravitite_ore", BlocksAether.gravitite_ore.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("arkenium_ore", BlocksAether.arkenium_ore.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("icestone_ore", BlocksAether.icestone_ore.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_log", BlocksAether.skyroot_log.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("golden_oak_log", BlocksAether.golden_oak_log.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("frostpine_log", BlocksAether.frostpine_log.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("blightwillow_log", BlocksAether.blightwillow_log.setCreativeTab(CreativeTabsAether.BLOCKS).setLightLevel(0.6F));
		//registerBlock("earthshifter_log", BlocksAether.earthshifter_log.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("blue_skyroot_leaves", BlocksAether.blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("green_skyroot_leaves", BlocksAether.green_skyroot_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("dark_blue_skyroot_leaves", BlocksAether.dark_blue_skyroot_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("golden_oak_leaves", BlocksAether.golden_oak_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("blighted_leaves", BlocksAether.blighted_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("frostpine_leaves", BlocksAether.frostpine_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("blightwillow_leaves", BlocksAether.blightwillow_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("earthshifter_leaves", BlocksAether.earthshifter_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("vined_earthshifter_leaves", BlocksAether.vined_earthshifter_leaves.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("aether_sapling", BlocksAether.aether_sapling.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_sapling));

		registerBlock("aether_portal", BlocksAether.aether_portal);

		registerBlock("tall_aether_grass", BlocksAether.tall_aether_grass.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("quicksoil", BlocksAether.quicksoil.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("aether_crafting_table", BlocksAether.aether_crafting_table.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_crafting_table));

		registerBlock("blueberry_bush", BlocksAether.blueberry_bush.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.blueberry_bush));
		//registerBlock("enchanted_blueberry_bush", BlocksAether.enchanted_blueberry_bush.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.enchanted_blueberry_bush));

		registerBlock("orange_tree", BlocksAether.orange_tree.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("aether_flower", BlocksAether.aether_flower.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.aether_flower));

		registerBlock("altar", BlocksAether.altar.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("crude_scatterglass", BlocksAether.crude_scatterglass.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("scatterglass", BlocksAether.scatterglass.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("zanite_block", BlocksAether.zanite_block.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("enchanted_gravitite", BlocksAether.enchanted_gravitite.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("carved_stone", BlocksAether.carved_stone.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.carved_stone));

		registerBlock("sentry_stone", BlocksAether.sentry_stone.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.sentry_stone));

		registerBlock("holystone_brick", BlocksAether.holystone_brick.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("holystone_furnace", BlocksAether.holystone_furnace.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("quicksoil_glass", BlocksAether.quicksoil_glass.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_door", BlocksAether.skyroot_door);
		registerBlock("arkenium_door", BlocksAether.arkenium_door);
		//registerBlock("blightwillow_door", BlocksAether.blightwillow_door);
		//registerBlock("earthshifter_door", BlocksAether.earthshifter_door);
		//registerBlock("frostpine_door", BlocksAether.frostpine_door);

		registerBlock("skyroot_chest", BlocksAether.skyroot_chest.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("ambrosium_torch", BlocksAether.ambrosium_torch.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("icestone_bricks", BlocksAether.icestone_bricks.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_fence", BlocksAether.skyroot_fence.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_fence_gate", BlocksAether.skyroot_fence_gate.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("standing_skyroot_sign", BlocksAether.standing_skyroot_sign);

		registerBlock("skyroot_trapdoor", BlocksAether.skyroot_trapdoor.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_ladder", BlocksAether.skyroot_ladder.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("blightwillow_ladder", BlocksAether.blightwillow_ladder.setCreativeTab(CreativeTabsAether.BLOCKS).setLightLevel(0.6F));
		//registerBlock("earthshifter_ladder", BlocksAether.earthshifter_ladder.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("frostpine_ladder", BlocksAether.frostpine_ladder.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_button", BlocksAether.skyroot_button.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("holystone_button", BlocksAether.holystone_button.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_pressure_plate", BlocksAether.skyroot_pressure_plate.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("holystone_pressure_plate", BlocksAether.holystone_pressure_plate.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("wall_skyroot_sign", BlocksAether.wall_skyroot_sign);

		registerBlock("labyrinth_chest", BlocksAether.labyrinth_chest.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("holystone_wall", BlocksAether.holystone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("mossy_holystone_wall", BlocksAether.mossy_holystone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("holystone_brick_wall", BlocksAether.holystone_brick_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("carved_stone_wall", BlocksAether.carved_stone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("icestone_wall", BlocksAether.icestone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("skyroot_log_wall", BlocksAether.skyroot_log_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("scatterglass_wall", BlocksAether.scatterglass_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("sentry_stone_wall", BlocksAether.sentry_stone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("divine_sentry_wall", BlocksAether.divine_sentry_wall.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("divine_stone_wall", BlocksAether.divine_stone_wall.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("labyrinth_totem", BlocksAether.labyrinth_totem.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("multiblock_dummy", BlocksAether.multiblock_dummy);

		registerBlock("labyrinth_capstone", BlocksAether.labyrinth_capstone.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_glowing_pillar", BlocksAether.labyrinth_glowing_pillar.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_pillar", BlocksAether.labyrinth_pillar.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_wall", BlocksAether.labyrinth_wall.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_lightstone", BlocksAether.labyrinth_lightstone.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_base", BlocksAether.labyrinth_base.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("labyrinth_headstone", BlocksAether.labyrinth_headstone.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("unstable_labyrinth_capstone", BlocksAether.unstable_labyrinth_capstone.setBlockUnbreakable().setResistance(6000000.0F));

		registerBlock("skyroot_slab", BlocksAether.skyroot_slab.setCreativeTab(CreativeTabsAether.BLOCKS),new ItemAetherSlab(BlocksAether.skyroot_slab));
		registerBlock("holystone_slab", BlocksAether.holystone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.holystone_slab));
		registerBlock("holystone_brick_slab", BlocksAether.holystone_brick_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.holystone_brick_slab));
		registerBlock("carved_stone_slab", BlocksAether.carved_stone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.carved_stone_slab));
		registerBlock("divine_carved_stone_slab", BlocksAether.divine_carved_stone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.divine_carved_stone_slab));
		registerBlock("sentry_stone_slab", BlocksAether.sentry_stone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.sentry_stone_slab));
		registerBlock("divine_sentry_stone_slab", BlocksAether.divine_sentry_stone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.divine_sentry_stone_slab));
		registerBlock("icestone_slab", BlocksAether.icestone_slab.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.icestone_slab));
		registerBlock("labyrinth_capstone_slab", BlocksAether.labyrinth_capstone_slab.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.labyrinth_capstone_slab));
		registerBlock("labyrinth_wall_slab", BlocksAether.labyrinth_wall_slab.setBlockUnbreakable().setResistance(6000000.0F).setCreativeTab(CreativeTabsAether.BLOCKS), new ItemAetherSlab(BlocksAether.labyrinth_wall_slab));

		registerBlock("labyrinth_strongblock", BlocksAether.labyrinth_strongblock.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("skyroot_stairs", BlocksAether.skyroot_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("holystone_stairs", BlocksAether.holystone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("mossy_holystone_stairs", BlocksAether.mossy_holystone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("holystone_brick_stairs", BlocksAether.holystone_brick_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("sentry_stone_stairs", BlocksAether.sentry_stone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("divine_sentry_stone_stairs", BlocksAether.divine_sentry_stone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("carved_stone_stairs", BlocksAether.carved_stone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("divine_carved_stone_stairs", BlocksAether.divine_carved_stone_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("icestone_brick_stairs", BlocksAether.icestone_brick_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));
		registerBlock("scatterglass_stairs", BlocksAether.scatterglass_stairs.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("woven_sticks", BlocksAether.woven_sticks.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.woven_sticks));
		registerBlock("moa_egg", BlocksAether.moa_egg);

		registerBlock("structure_extended", BlocksAether.structure_extended);

		//registerBlock("skyroot_vines", BlocksAether.skyroot_vines.setCreativeTab(CreativeTabsAether.BLOCKS));
		//registerBlock("enchanted_skyroot_vines", BlocksAether.enchanted_skyroot_vines.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("frostpine_cooler", BlocksAether.frostpine_cooler.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("incubator", BlocksAether.incubator.setCreativeTab(CreativeTabsAether.BLOCKS));

		registerBlock("present", BlocksAether.present.setCreativeTab(CreativeTabsAether.MISCELLANEOUS), new ItemBlockPresent(BlocksAether.present));
		registerBlock("labyrinth_container", BlocksAether.labyrinth_container.setCreativeTab(CreativeTabsAether.BLOCKS), new ItemBlockVariants(BlocksAether.labyrinth_container));

		registerBlock("labyrinth_bridge", BlocksAether.labyrinth_bridge.setBlockUnbreakable().setResistance(6000000.0F));

		Blocks.FIRE.setFireInfo(BlocksAether.aether_flower, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.orange_tree, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.blueberry_bush, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.enchanted_blueberry_bush, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_log_wall, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.aether_planks, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_chest, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.aether_crafting_table, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_door, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.blightwillow_door, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.earthshifter_door, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.frostpine_door, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence_gate, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_ladder, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.blightwillow_ladder, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.earthshifter_ladder, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.frostpine_ladder, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_pressure_plate, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_bed, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_slab, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_trapdoor, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_stairs, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_button, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.woven_sticks, 5, 5);

		Blocks.FIRE.setFireInfo(BlocksAether.frostpine_cooler, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.incubator, 5, 5);

		registerHarvestLevels();

		for (Runnable runnable : BlocksAether.postRegistration)
		{
			runnable.run();
		}

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

		skyroot_log.setHarvestLevel("axe", 0);
		golden_oak_log.setHarvestLevel("axe", 0);
		aether_planks.setHarvestLevel("axe", 0);
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
		frostpine_cooler.setHarvestLevel("axe", 0);
		incubator.setHarvestLevel("axe", 0);

		zanite_block.setHarvestLevel("pickaxe", 1);
		enchanted_gravitite.setHarvestLevel("pickaxe", 2);

		carved_stone.setHarvestLevel("pickaxe", 0);
		labyrinth_lightstone.setHarvestLevel("pickaxe", 0);

		holystone_brick.setHarvestLevel("pickaxe", 0);

		skyroot_door.setHarvestLevel("axe", 0);
		skyroot_trapdoor.setHarvestLevel("axe", 0);

		holystone_wall.setHarvestLevel("pickaxe", 0);
		mossy_holystone_wall.setHarvestLevel("pickaxe", 0);
		carved_stone_wall.setHarvestLevel("pickaxe", 0);
		skyroot_log_wall.setHarvestLevel("axe", 0);
		icestone_wall.setHarvestLevel("pickaxe", 1);
		scatterglass_wall.setHarvestLevel("pickaxe", 1);

		skyroot_slab.setHarvestLevel("axe", 0);
		holystone_slab.setHarvestLevel("pickaxe", 0);
		holystone_brick_slab.setHarvestLevel("pickaxe", 0);
		carved_stone_slab.setHarvestLevel("pickaxe", 0);
		divine_carved_stone_slab.setHarvestLevel("pickaxe", 0);
		sentry_stone_slab.setHarvestLevel("pickaxe", 0);
		divine_sentry_stone_slab.setHarvestLevel("pickaxe", 0);
		icestone_slab.setHarvestLevel("pickaxe", 0);
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
