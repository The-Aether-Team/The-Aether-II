package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.construction.*;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystoneButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockHolystonePressurePlate;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootButton;
import com.gildedgames.aether.common.blocks.construction.redstone.BlockSkyrootPressurePlate;
import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.walls.BlockCandyCaneWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockCustomWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockScatterglassWall;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
import com.gildedgames.aether.common.blocks.containers.*;
import com.gildedgames.aether.common.blocks.decorative.*;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummy;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiDummyHalf;
import com.gildedgames.aether.common.blocks.natural.*;
import com.gildedgames.aether.common.blocks.natural.leaves.*;
import com.gildedgames.aether.common.blocks.natural.ores.*;
import com.gildedgames.aether.common.blocks.natural.plants.*;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherGreatrootSapling;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherSkyrootSapling;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherUniqueSapling;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherWisprootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.AetherWoodType;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import com.gildedgames.aether.common.blocks.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class BlocksAetherInit
{
	private static final Set<Block> registeredBlocks = new HashSet<>();

	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
	{
		final BlockRegistryHelper r = new BlockRegistryHelper(event.getRegistry());

		Block holystone = new BlockHolystone(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 10.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE));
		Block skyroot_planks = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0));
		Block therawood_planks = new Block(Block.Properties.from(skyroot_planks));
		Block icestone_bricks = new BlockIcestoneBricks(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(1));
		Block quicksoil_glass = new BlockRockGlassTranslucent(Block.Properties.create(Material.GLASS).hardnessAndResistance(1.0f, 2000.0f).sound(SoundType.GLASS).lightValue(15));
		Block crude_scatterglass = new Block(Block.Properties.create(Material.GLASS).hardnessAndResistance(1.0f, 2000.0f).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0));
		Block scatterglass = new BlockRockGlassTranslucent(Block.Properties.from(crude_scatterglass));
		Block candy_cane = new BlockCandyCane(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.5F).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.GLASS));
		Block agiosite = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0f, 10.0f));
		Block sentrystone = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0f, 10.0f));
		Block therastone_brick = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0f, 10.0f));
		Block aether_dirt = new BlockAetherDirt(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5f).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0));
		Block aether_grass = new BlockAetherGrass(Block.Properties.from(aether_dirt).tickRandomly());
		Block aether_flower = new BlockAetherFlower(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement());
		Block skyroot_log = new BlockAetherLog(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).harvestLevel(0), AetherWoodType.SKYROOT);
		Block skyroot_leaves = new BlockAetherLeaves(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
		Block highlands_ice = new Block(Block.Properties.create(Material.ICE).slipperiness(0.98F).tickRandomly().hardnessAndResistance(0.5F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0));
		Block highlands_snow = new Block(Block.Properties.create(Material.SNOW).tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.SNOW).harvestTool(ToolType.SHOVEL).harvestLevel(0));

		r.register("aercloud", new BlockAercloud(Block.Properties.create(Material.ICE).hardnessAndResistance(0.2f).harvestTool(ToolType.SHOVEL).harvestLevel(0).sound(SoundType.CLOTH)));
		r.register("aether_crafting_table", new BlockAetherCraftingTable(Block.Properties.from(skyroot_planks).hardnessAndResistance(2.5f)));
		r.register("aether_dirt", aether_dirt);
		r.register("aether_flower", aether_flower);
		r.register("aether_grass", aether_grass);
		r.register("aether_portal", new BlockAetherPortal(Block.Properties.create(Material.PORTAL).sound(SoundType.GLASS).hardnessAndResistance(-1.0f).lightValue(12).tickRandomly().doesNotBlockMovement()));
		r.register("aether_teleporter", new BlockAetherTeleporter(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f).sound(SoundType.STONE)));
		r.register("agiosite", agiosite);
		r.register("agiosite_brick", new Block(Block.Properties.from(agiosite)));
		r.register("agiosite_brick_decorative", new BlockAgiositeDecorative(Block.Properties.from(agiosite)));
		r.register("agiosite_brick_slab", new BlockCustomSlab(Block.Properties.from(agiosite)));
		r.register("agiosite_brick_stairs", new BlockCustomStairs(agiosite.getDefaultState(), Block.Properties.from(agiosite)));
		r.register("agiosite_pillar", new BlockRotatable(Block.Properties.from(agiosite)));
		r.register("agiosite_slab", new BlockCustomSlab(Block.Properties.from(agiosite)));
		r.register("agiosite_stairs", new BlockCustomStairs(agiosite.getDefaultState(), Block.Properties.from(agiosite)));
		r.register("altar", new BlockAltar(Block.Properties.from(holystone)));
		r.register("amberoot_leaves", new BlockUniqueLeaves(Block.Properties.from(skyroot_leaves), AetherWoodType.AMBERROOT));
		r.register("ambrosium_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).lightValue(6).sound(SoundType.STONE)));
		r.register("ambrosium_torch", new BlockAmbrosiumTorch(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.0f).lightValue(14).sound(SoundType.WOOD)));
		r.register("arctic_spikespring", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("arkenium_door", new BlockCustomDoor(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f).sound(SoundType.METAL), () -> ItemsAether.arkenium_door_item));
		r.register("arkenium_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)));
		r.register("barkshroom", new BlockAetherMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		r.register("blue_dark_skyroot_leaves", new BlockGreatrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.BLUE));
		r.register("blue_light_skyroot_leaves", new BlockWisprootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.BLUE));
		r.register("blue_skyroot_leaves", new BlockSkyrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.BLUE));
		r.register("blue_swingtip", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("blueberry_bush", new BlockBlueberryBush(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0f).sound(SoundType.PLANT).tickRandomly()));
		r.register("brettl_plant", new BlockBrettlPlant(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.0f).sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement()));
		r.register("candy_cane_block", candy_cane);
		r.register("candy_cane_wall", new BlockCandyCaneWall(Block.Properties.from(candy_cane)));
		r.register("cloudwool_block", new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
		r.register("cloudwool_carpet", new BlockCustomCarpet(Block.Properties.create(Material.CARPET).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
		r.register("crude_scatterglass", crude_scatterglass);
		r.register("crude_scatterglass_decorative", new BlockRockGlassDecorative(Block.Properties.from(crude_scatterglass)));
		r.register("crude_scatterglass_pane", new BlockRockGlassPane(Block.Properties.from(crude_scatterglass), crude_scatterglass));
		r.register("crude_scatterglass_pane_decorative", new BlockRockGlassPaneDecorative(Block.Properties.from(crude_scatterglass)));
		r.register("dark_blue_dark_skyroot_leaves", new BlockGreatrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.DARK_BLUE));
		r.register("dark_blue_light_skyroot_leaves", new BlockWisprootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.DARK_BLUE));
		r.register("dark_blue_skyroot_leaves", new BlockSkyrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.DARK_BLUE));
		r.register("dark_skyroot_beam", new BlockRotatable(Block.Properties.from(skyroot_planks)));
		r.register("dark_skyroot_decorative", new BlockDarkSkyrootDecorative(Block.Properties.from(skyroot_planks)));
		r.register("dark_skyroot_log", new BlockAetherLog(Block.Properties.from(skyroot_log), AetherWoodType.GREATROOT));
		r.register("dark_skyroot_planks", new Block(Block.Properties.from(skyroot_planks)));
		r.register("faded_holystone_brick", new Block(Block.Properties.from(holystone)));
		r.register("faded_holystone_brick_decorative", new BlockFadedHolystoneDecorative(Block.Properties.from(holystone)));
		r.register("faded_holystone_brick_slab", new BlockCustomSlab(Block.Properties.from(holystone)));
		r.register("faded_holystone_brick_stairs", new BlockCustomStairs(holystone.getDefaultState(), Block.Properties.from(holystone)));
		r.register("faded_holystone_pillar", new BlockRotatable(Block.Properties.from(holystone)));
		r.register("ferrosite", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f).sound(SoundType.STONE)));
		r.register("ferrosite_sand", new Block(Block.Properties.create(Material.SAND).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
		r.register("forgotten_rose", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("golden_oak_log", new BlockAetherLog(Block.Properties.from(skyroot_log), AetherWoodType.AMBERROOT));
		r.register("gravitite_block", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2)));
		r.register("gravitite_ore", new BlockFloating(Block.Properties.from(holystone).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2)));
		r.register("greatroot_button", new BlockSkyrootButton(Block.Properties.from(skyroot_planks)));
		r.register("greatroot_fence", new FenceBlock(Block.Properties.from(skyroot_planks).hardnessAndResistance(2.0f, 5.0f)));
		r.register("greatroot_fence_gate", new FenceGateBlock(Block.Properties.from(skyroot_planks)));
		r.register("greatroot_pressure_plate", new BlockSkyrootPressurePlate(Block.Properties.from(skyroot_planks)));
		r.register("greatroot_sapling", new BlockAetherGreatrootSapling());
		r.register("greatroot_slab", new BlockCustomSlab(Block.Properties.from(skyroot_planks)));
		r.register("greatroot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState(), Block.Properties.from(skyroot_planks)));
		r.register("green_dark_skyroot_leaves", new BlockGreatrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.GREEN));
		r.register("green_light_skyroot_leaves", new BlockWisprootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.GREEN));
		r.register("green_skyroot_leaves", new BlockSkyrootLeaves(Block.Properties.from(skyroot_leaves), BlockColoredLeaves.Color.GREEN));
		r.register("green_swingtip", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("hellfirestone_brick", new Block(Block.Properties.from(holystone)));
		r.register("hellfirestone_brick_decorative", new BlockHellfirestoneDecorative(Block.Properties.from(holystone)));
		r.register("hellfirestone_lantern", new Block(Block.Properties.from(holystone).lightValue(15)));
		r.register("hellfirestone_pillar", new BlockRotatable(Block.Properties.from(holystone)));
		r.register("highlands_bush", new BlockAetherLeaves(Block.Properties.from(skyroot_leaves)));
		r.register("highlands_ice", highlands_ice.setLightOpacity(3));
		r.register("highlands_ice_crystal", new BlockIceCrystal(Block.Properties.from(highlands_ice).doesNotBlockMovement()));
		r.register("highlands_packed_ice", new Block(Block.Properties.from(highlands_ice)));
		r.register("highlands_snow", highlands_snow);
		r.register("highlands_snow_layer", new BlockCustomSnow(Block.Properties.from(highlands_snow)));
		r.register("highlands_tulips", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("holystone", holystone);
		r.register("holystone_bookshelf", new BlockCustomBookshelf(Block.Properties.from(holystone)));
		r.register("holystone_brick", new Block(Block.Properties.from(holystone)));
		r.register("holystone_brick_decorative", new BlockHolystoneDecorative(Block.Properties.from(holystone)));
		r.register("holystone_brick_slab", new BlockCustomSlab(Block.Properties.from(holystone)));
		r.register("holystone_brick_stairs", new BlockCustomStairs(holystone.getDefaultState(), Block.Properties.from(holystone)));
		r.register("holystone_brick_wall", new BlockCustomWall(Block.Properties.from(holystone)));
		r.register("holystone_button", new BlockHolystoneButton(Block.Properties.from(holystone)));
		r.register("holystone_furnace", new BlockHolystoneFurnace(Block.Properties.from(holystone)));
		r.register("holystone_pillar", new BlockRotatable(Block.Properties.from(holystone)));
		r.register("holystone_pressure_plate", new BlockHolystonePressurePlate(Block.Properties.from(holystone)));
		r.register("holystone_rock", new BlockFloorObject(Block.Properties.from(holystone)));
		r.register("holystone_slab", new BlockCustomSlab(Block.Properties.from(holystone)));
		r.register("holystone_stairs", new BlockCustomStairs(holystone.getDefaultState(), Block.Properties.from(holystone)));
		r.register("holystone_wall", new BlockCustomWall(Block.Properties.from(holystone)));
		r.register("icestone_brick_stairs", new BlockCustomStairs(icestone_bricks.getDefaultState(), Block.Properties.from(icestone_bricks)));
		r.register("icestone_bricks", icestone_bricks);
		r.register("icestone_bricks_decorative", new BlockIcestoneBricksDecorative(Block.Properties.from(icestone_bricks)));
		r.register("icestone_cooler", new BlockIcestoneCooler(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
		r.register("icestone_ore", new Block(Block.Properties.from(holystone).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
		r.register("icestone_pillar", new BlockRotatable(Block.Properties.from(icestone_bricks)));
		r.register("icestone_slab", new BlockCustomSlab(Block.Properties.from(icestone_bricks)));
		r.register("icestone_wall", new BlockCustomWall(Block.Properties.from(icestone_bricks)));
		r.register("incubator", new BlockIncubator(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
		r.register("irradiated_flower", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("light_skyroot_beam", new BlockRotatable(Block.Properties.from(skyroot_planks)));
		r.register("light_skyroot_decorative", new BlockLightSkyrootDecorative(Block.Properties.from(skyroot_planks)));
		r.register("light_skyroot_log", new BlockAetherLog(Block.Properties.from(skyroot_log), AetherWoodType.WISPROOT));
		r.register("light_skyroot_planks", new Block(Block.Properties.from(skyroot_planks)));
		r.register("magnetic_shroom", new BlockAetherMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		r.register("masonry_bench", new BlockMasonryBench(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
		r.register("moa_egg", new BlockMoaEgg(Block.Properties.create(Material.ORGANIC).sound(SoundType.STONE).hardnessAndResistance(0.1f)));
		r.register("mossy_holystone_slab", new BlockCustomSlab(Block.Properties.from(holystone)));
		r.register("mossy_holystone_stairs", new BlockCustomStairs(holystone.getDefaultState().with(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), Block.Properties.from(holystone)));
		r.register("mossy_holystone_wall", new BlockCustomWall(Block.Properties.from(holystone)));
		r.register("multiblock_dummy", new BlockMultiDummy());
		r.register("multiblock_dummy_half", new BlockMultiDummyHalf());
		r.register("mutant_leaves", new BlockUniqueLeaves(Block.Properties.from(skyroot_leaves), AetherWoodType.MUTANT));
		r.register("mutant_leaves_decorated", new BlockUniqueLeaves(Block.Properties.from(skyroot_leaves).lightValue(15), AetherWoodType.MUTANT));
		r.register("neverbloom", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("orange_tree", new BlockOrangeTree(Block.Properties.create(Material.PLANTS).hardnessAndResistance(1.0f).tickRandomly().sound(SoundType.PLANT)));
		r.register("outpost_campfire", new BlockOutpostCampfire(Block.Properties.create(Material.IRON).hardnessAndResistance(-1.0F)));
		r.register("pink_swingtip", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("plumproot", new Block(Block.Properties.create(Material.GOURD).sound(SoundType.WOOD).hardnessAndResistance(1.0f)));
		r.register("present", new BlockPresent(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.4f).sound(SoundType.CLOTH)));
		r.register("quickshoot", new BlockAetherFlowerBase(Block.Properties.from(aether_flower)));
		r.register("quicksoil", new BlockQuicksoil(Block.Properties.create(Material.SAND).hardnessAndResistance(1.5f).harvestTool(ToolType.SHOVEL).harvestLevel(0).sound(SoundType.SAND)));
		r.register("quicksoil_glass", quicksoil_glass);
		r.register("quicksoil_glass_decorative", new BlockRockGlassDecorative(Block.Properties.from(quicksoil_glass)));
		r.register("quicksoil_glass_pane", new BlockRockGlassPane(Block.Properties.from(quicksoil_glass), quicksoil_glass));
		r.register("quicksoil_glass_pane_decorative", new BlockRockGlassPaneDecorative(Block.Properties.from(quicksoil_glass)));
		r.register("rusted_ferrosite", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f).sound(SoundType.STONE)));
		r.register("scatterglass", scatterglass);
		r.register("scatterglass_decorative", new BlockRockGlassDecorative(Block.Properties.from(scatterglass)));
		r.register("scatterglass_pane", new BlockRockGlassPane(Block.Properties.from(scatterglass), scatterglass));
		r.register("scatterglass_pane_decorative", new BlockRockGlassPaneDecorative(Block.Properties.from(scatterglass)));
		r.register("scatterglass_slab", new BlockScatterglassSlab(Block.Properties.from(scatterglass)));
		r.register("scatterglass_stairs", new BlockScatterglassStairs(scatterglass.getDefaultState(), Block.Properties.from(scatterglass)));
		r.register("scatterglass_wall", new BlockScatterglassWall(Block.Properties.from(scatterglass)));
		r.register("secret_skyroot_door", new BlockCustomDoor(Block.Properties.from(skyroot_planks), () -> ItemsAether.secret_skyroot_door_item));
		r.register("secret_skyroot_trapdoor", new BlockSkyrootTrapDoor(Block.Properties.from(skyroot_planks)));
		r.register("sentrystone_brick", sentrystone);
		r.register("sentrystone_brick_decorative", new BlockSentrystoneDecorative(Block.Properties.from(sentrystone)));
		r.register("sentrystone_brick_decorative_lit", new BlockSentrystoneDecorativeLit(Block.Properties.from(sentrystone).lightValue(7)));
		r.register("sentrystone_pillar", new BlockRotatable(Block.Properties.from(sentrystone)));
		r.register("sentrystone_pillar_lit", new BlockRotatable(Block.Properties.from(sentrystone).lightValue(7)));
		r.register("skyroot_beam", new BlockRotatable(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_bed", new BlockCustomBed(Block.Properties.from(skyroot_planks), () -> ItemsAether.skyroot_bed_item));
		r.register("skyroot_bookshelf", new BlockCustomBookshelf(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_button", new BlockSkyrootButton(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_chest", new BlockSkyrootChest(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_decorative", new BlockSkyrootDecorative(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_door", new BlockCustomDoor(Block.Properties.from(skyroot_planks), () -> ItemsAether.skyroot_door_item));
		r.register("skyroot_fence", new FenceBlock(Block.Properties.from(skyroot_planks).hardnessAndResistance(2.0f, 5.0f)));
		r.register("skyroot_fence_gate", new FenceGateBlock(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_ladder", new Block(Block.Properties.from(skyroot_planks).hardnessAndResistance(0.4f)));
		r.register("skyroot_log", skyroot_log);
		r.register("skyroot_log_wall", new BlockSkyrootWall(Block.Properties.from(skyroot_log)));
		r.register("skyroot_planks", skyroot_planks);
		r.register("skyroot_pressure_plate", new BlockSkyrootPressurePlate(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_sapling", new BlockAetherSkyrootSapling());
		r.register("skyroot_slab", new BlockCustomSlab(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState(), Block.Properties.from(skyroot_planks)));
		r.register("skyroot_trapdoor", new BlockSkyrootTrapDoor(Block.Properties.from(skyroot_planks)));
		r.register("skyroot_twigs", new BlockFloorObject(Block.Properties.from(skyroot_planks)));
		r.register("standing_skyroot_sign", new BlockStandingSkyrootSign(Block.Properties.from(skyroot_planks)));
		r.register("stoneshroom", new BlockAetherMushroom(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		r.register("tall_aether_grass", new BlockTallAetherGrass(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		r.register("thera_dirt", new BlockTheraDirt(Block.Properties.from(aether_dirt)));
		r.register("thera_grass", new BlockTheraGrass(Block.Properties.from(aether_grass)));
		r.register("therastone_brick", therastone_brick);
		r.register("therastone_brick_decorative", new BlockTherastoneDecorative(Block.Properties.from(therastone_brick)));
		r.register("therastone_brick_slab", new BlockCustomSlab(Block.Properties.from(therastone_brick)));
		r.register("therastone_brick_stairs", new BlockCustomStairs(therastone_brick.getDefaultState(), Block.Properties.from(therastone_brick)));
		r.register("therastone_pillar", new BlockRotatable(Block.Properties.from(therastone_brick)));
		r.register("therawood_beam", new BlockRotatable(Block.Properties.from(skyroot_planks)));
		r.register("therawood_decorative", new BlockTherawoodDecorative(Block.Properties.from(skyroot_planks)));
		r.register("therawood_leaves", new BlockAetherLeaves(Block.Properties.from(skyroot_leaves)));
		r.register("therawood_log", new BlockAetherLog(Block.Properties.from(skyroot_log), AetherWoodType.THERA));
		r.register("therawood_planks", therawood_planks);
		r.register("therawood_slab", new BlockCustomSlab(Block.Properties.from(skyroot_planks)));
		r.register("therawood_stairs", new BlockCustomStairs(therawood_planks.getDefaultState(), Block.Properties.from(skyroot_planks)));
		r.register("unique_sapling", new BlockAetherUniqueSapling());
		r.register("valkyrie_grass", new BlockValkyrieGrass(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).sound(SoundType.PLANT).tickRandomly()));
		r.register("wall_skyroot_sign", new BlockWallSkyrootSign(Block.Properties.from(skyroot_planks)));
		r.register("wildcard", new BlockWildcard(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f).sound(SoundType.STONE)));
		r.register("wisproot_button", new BlockSkyrootButton(Block.Properties.from(skyroot_planks)));
		r.register("wisproot_fence", new FenceBlock(Block.Properties.from(skyroot_planks).hardnessAndResistance(2.0f, 5.0f)));
		r.register("wisproot_fence_gate", new FenceGateBlock(Block.Properties.from(skyroot_planks)));
		r.register("wisproot_pressure_plate", new BlockSkyrootPressurePlate(Block.Properties.from(skyroot_planks)));
		r.register("wisproot_sapling", new BlockAetherWisprootSapling());
		r.register("wisproot_slab", new BlockCustomSlab(Block.Properties.from(skyroot_planks)));
		r.register("wisproot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState(), Block.Properties.from(skyroot_planks)));
		r.register("woven_sticks", new BlockWovenSticks(Block.Properties.create(Material.WOOD).sound(SoundType.GROUND).hardnessAndResistance(0.5F).harvestTool(ToolType.AXE).harvestLevel(0)));
		r.register("zanite_block", new Block(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
		r.register("zanite_ore", new Block(Block.Properties.from(holystone).hardnessAndResistance(3.0f, 5.0f).harvestLevel(1)));

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
			block.setRegistryName(AetherCore.MOD_ID, registryName);

			this.registry.register(block);

			registeredBlocks.add(block);
		}
	}
}
