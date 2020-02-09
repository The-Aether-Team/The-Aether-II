package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.IInternalBlock;
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
import com.gildedgames.aether.common.blocks.natural.wood.BlockAmberLog;
import com.gildedgames.aether.common.blocks.util.*;
import com.gildedgames.aether.common.items.blocks.ItemBlockMultiName;
import net.minecraft.block.Block;
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

import static com.gildedgames.aether.common.init.CreativeTabsAether.*;

@Mod.EventBusSubscriber
public class BlocksAetherInit
{
	private static final Set<Block> registeredBlocks = new HashSet<>();

	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
	{
		final BlockRegistryHelper r = new BlockRegistryHelper(event.getRegistry());

		Block holystone = new BlockHolystone();
		Block skyroot_planks = new BlockSkyrootPlanks();
		Block therawood_planks = new BlockTherawoodPlanks();
		Block icestone_bricks = new BlockIcestoneBricks();
		Block quicksoil_glass = new BlockQuicksoilGlass();
		Block scatterglass = new BlockRockGlassTranslucent();
		Block crude_scatterglass = new BlockRockGlass();
		Block candy_cane = new BlockCandyCane();

		r.register("aercloud", new BlockAercloud().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("aether_crafting_table", new BlockAetherCraftingTable().setCreativeTab(TAB_UTILITY));
		r.register("aether_dirt", new BlockAetherDirt().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("aether_flower", new BlockAetherFlower().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("aether_grass", new BlockAetherGrass().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("aether_portal", new BlockAetherPortal());
		r.register("aether_teleporter", new BlockAetherTeleporter().setCreativeTab(TAB_UTILITY));
		r.register("agiosite", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("agiosite_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("agiosite_brick_decorative", new BlockAgiositeDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("agiosite_brick_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("agiosite_brick_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("agiosite_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("agiosite_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("agiosite_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("altar", new BlockAltar().setCreativeTab(TAB_UTILITY));
		r.register("amberoot_leaves", new BlockUniqueLeaves(AetherWoodType.AMBERROOT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("ambrosium_ore", new BlockAmbrosiumOre().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("ambrosium_torch", new BlockAmbrosiumTorch().setCreativeTab(TAB_CONSTRUCTION));
		r.register("arctic_spikespring", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("arkenium_door", new BlockCustomDoor(Material.IRON, () -> ItemsAether.arkenium_door_item, SoundType.METAL));
		r.register("arkenium_ore", new BlockArkeniumOre().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("barkshroom", new BlockAetherMushroom().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("blue_dark_skyroot_leaves", new BlockGreatrootLeaves(BlockColoredLeaves.Color.BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("blue_light_skyroot_leaves", new BlockWisprootLeaves(BlockColoredLeaves.Color.BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("blue_skyroot_leaves", new BlockSkyrootLeaves(BlockColoredLeaves.Color.BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("blue_swingtip", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("blueberry_bush", new BlockBlueberryBush().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("brettl_plant", new BlockBrettlPlant());
		r.register("candy_cane_block", candy_cane.setCreativeTab(TAB_CONSTRUCTION));
		r.register("candy_cane_wall", new BlockCandyCaneWall(candy_cane.getDefaultState(), 0.5f, 2.5f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("cloudwool_block", new BlockCloudwoolBlock().setCreativeTab(TAB_CONSTRUCTION));
		r.register("cloudwool_carpet", new BlockCustomCarpet().setCreativeTab(TAB_CONSTRUCTION));
		r.register("crude_scatterglass", crude_scatterglass.setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("crude_scatterglass_decorative", new BlockRockGlassDecorative(Material.ROCK).setHardness(1.0f).setLightOpacity(3).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("crude_scatterglass_pane", new BlockRockGlassPane(crude_scatterglass, Material.ROCK).setHardness(1.0f).setLightOpacity(3).setCreativeTab(TAB_CONSTRUCTION));
		r.register("crude_scatterglass_pane_decorative", new BlockRockGlassPaneDecorative(Material.ROCK).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("dark_blue_dark_skyroot_leaves", new BlockGreatrootLeaves(BlockColoredLeaves.Color.DARK_BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("dark_blue_light_skyroot_leaves", new BlockWisprootLeaves(BlockColoredLeaves.Color.DARK_BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("dark_blue_skyroot_leaves", new BlockSkyrootLeaves(BlockColoredLeaves.Color.DARK_BLUE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("dark_skyroot_beam", new BlockCustomPillar(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("dark_skyroot_decorative", new BlockDarkSkyrootDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("dark_skyroot_log", new BlockAetherLog(AetherWoodType.GREATROOT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("dark_skyroot_planks", new BlockSkyrootPlanks().setCreativeTab(TAB_CONSTRUCTION));
		r.register("faded_holystone_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("faded_holystone_brick_decorative", new BlockFadedHolystoneDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("faded_holystone_brick_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("faded_holystone_brick_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("faded_holystone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("ferrosite", new BlockFerrosite().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("ferrosite_sand", new BlockFerrositeSand().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("forgotten_rose", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("golden_oak_log", new BlockAmberLog(AetherWoodType.AMBERROOT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("gravitite_block", new BlockOreBlock(Material.IRON).setSoundType(SoundType.METAL).setHardness(5f).setResistance(10f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("gravitite_ore", new BlockGravititeOre().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("greatroot_button", new BlockSkyrootButton().setCreativeTab(TAB_CONSTRUCTION));
		r.register("greatroot_fence", new BlockSkyrootFence().setCreativeTab(TAB_CONSTRUCTION));
		r.register("greatroot_fence_gate", new BlockSkyrootFenceGate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("greatroot_pressure_plate", new BlockSkyrootPressurePlate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("greatroot_sapling", new BlockAetherGreatrootSapling().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("greatroot_slab", new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("greatroot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("green_dark_skyroot_leaves", new BlockGreatrootLeaves(BlockColoredLeaves.Color.GREEN).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("green_light_skyroot_leaves", new BlockWisprootLeaves(BlockColoredLeaves.Color.GREEN).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("green_skyroot_leaves", new BlockSkyrootLeaves(BlockColoredLeaves.Color.GREEN).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("green_swingtip", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("hellfirestone_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("hellfirestone_brick_decorative", new BlockHellfirestoneDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("hellfirestone_lantern", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setLightLevel(1.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("hellfirestone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("highlands_bush", new BlockAetherLeaves().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("highlands_ice", new BlockCustomIce().setHardness(0.5F).setLightOpacity(3).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("highlands_ice_crystal", new BlockIceCrystal());
		r.register("highlands_packed_ice", new BlockCustomPackedIce().setHardness(0.5F).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("highlands_snow", new BlockCustomSnowBlock().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("highlands_snow_layer", new BlockCustomSnow().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("highlands_tulips", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("holystone", holystone.setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("holystone_bookshelf", new BlockCustomBookshelf(Material.ROCK, SoundType.STONE).setHardness(1f).setResistance(5.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_brick_decorative", new BlockHolystoneDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("holystone_brick_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_brick_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_brick_wall", new BlockCustomWall(holystone.getDefaultState(), 1.5f, 10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_button", new BlockHolystoneButton().setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_furnace", new BlockHolystoneFurnace().setCreativeTab(TAB_UTILITY));
		r.register("holystone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("holystone_pressure_plate", new BlockHolystonePressurePlate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_rock", new BlockFloorObject(Material.ROCK, SoundType.STONE).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("holystone_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("holystone_wall", new BlockCustomWall(holystone.getDefaultState(), 1.5f, 10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("icestone_brick_stairs", new BlockCustomStairs(icestone_bricks.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("icestone_bricks", icestone_bricks.setCreativeTab(TAB_CONSTRUCTION));
		r.register("icestone_bricks_decorative", new BlockIcestoneBricksDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("icestone_cooler", new BlockIcestoneCooler().setCreativeTab(TAB_UTILITY));
		r.register("icestone_ore", new BlockIcestoneOre().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("icestone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.GLASS).setHardness(2.0f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("icestone_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.GLASS).setHardness(2.0f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("icestone_wall", new BlockCustomWall(icestone_bricks.getDefaultState(), 2.0f, 10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("incubator", new BlockIncubator().setCreativeTab(TAB_UTILITY));
		r.register("irradiated_flower", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("light_skyroot_beam", new BlockCustomPillar(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("light_skyroot_decorative", new BlockLightSkyrootDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("light_skyroot_log", new BlockAetherLog(AetherWoodType.WISPROOT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("light_skyroot_planks", new BlockSkyrootPlanks().setCreativeTab(TAB_CONSTRUCTION));
		r.register("magnetic_shroom", new BlockAetherMushroom().setLightLevel(0.5F).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("masonry_bench", new BlockMasonryBench().setCreativeTab(TAB_UTILITY));
		r.register("moa_egg", new BlockMoaEgg());
		r.register("mossy_holystone_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("mossy_holystone_stairs", new BlockCustomStairs(holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE)).setCreativeTab(TAB_CONSTRUCTION));
		r.register("mossy_holystone_wall", new BlockCustomWall(holystone.getDefaultState(), 1.5f, 10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("multiblock_dummy", new BlockMultiDummy());
		r.register("multiblock_dummy_half", new BlockMultiDummyHalf());
		r.register("mutant_leaves", new BlockUniqueLeaves(AetherWoodType.MUTANT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("mutant_leaves_decorated", new BlockUniqueLeaves(AetherWoodType.MUTANT).setLightLevel(1f).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("neverbloom", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("orange_tree", new BlockOrangeTree().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("outpost_campfire", new BlockOutpostCampfire(Material.ROCK).setCreativeTab(TAB_CONSTRUCTION));
		r.register("pink_swingtip", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("plumproot", new BlockPlumproot().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("present", new BlockPresent().setCreativeTab(TAB_MISCELLANEOUS));
		r.register("quickshoot", new BlockAetherFlowerBase().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("quicksoil", new BlockQuicksoil().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("quicksoil_glass", quicksoil_glass.setCreativeTab(TAB_CONSTRUCTION));
		r.register("quicksoil_glass_decorative", new BlockQuicksoilGlassDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("quicksoil_glass_pane", new BlockQuicksoilGlassPane().setCreativeTab(TAB_CONSTRUCTION));
		r.register("quicksoil_glass_pane_decorative", new BlockQuicksoilGlassPaneDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("rusted_ferrosite", new BlockRustedFerrosite().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("scatterglass", scatterglass.setCreativeTab(TAB_CONSTRUCTION));
		r.register("scatterglass_decorative", new BlockRockGlassDecorative(Material.GLASS).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("scatterglass_pane", new BlockRockGlassPane(scatterglass, Material.GLASS).setHardness(0.3f).setLightOpacity(0).setCreativeTab(TAB_CONSTRUCTION));
		r.register("scatterglass_pane_decorative", new BlockRockGlassPaneDecorative(Material.GLASS).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("scatterglass_slab", new BlockScatterglassSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(0.3f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("scatterglass_stairs", new BlockScatterglassStairs(scatterglass.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("scatterglass_wall", new BlockScatterglassWall(scatterglass.getDefaultState(), 0.3f, 2000.0f).setLightOpacity(0).setCreativeTab(TAB_CONSTRUCTION));
		r.register("secret_skyroot_door", new BlockCustomDoor(Material.WOOD, () -> ItemsAether.secret_skyroot_door_item, SoundType.WOOD).setHardness(2.0f).setResistance(5.0f));
		r.register("secret_skyroot_trapdoor", new BlockSkyrootTrapDoor().setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("sentrystone_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("sentrystone_brick_decorative", new BlockSentrystoneDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("sentrystone_brick_decorative_lit", new BlockSentrystoneDecorativeLit().setLightLevel(0.5f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("sentrystone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("sentrystone_pillar_lit", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setLightLevel(0.5f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("skyroot_beam", new BlockCustomPillar(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("skyroot_bed", new BlockCustomBed(() -> ItemsAether.skyroot_bed_item, SoundType.WOOD));
		r.register("skyroot_bookshelf", new BlockCustomBookshelf(Material.WOOD, SoundType.WOOD).setHardness(1.5f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_button", new BlockSkyrootButton().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_chest", new BlockSkyrootChest().setCreativeTab(TAB_UTILITY));
		r.register("skyroot_decorative", new BlockSkyrootDecorative().setCreativeTab(TAB_DECORATIVE_BLOCKS));
		r.register("skyroot_door", new BlockCustomDoor(Material.WOOD, () -> ItemsAether.skyroot_door_item, SoundType.WOOD));
		r.register("skyroot_fence", new BlockSkyrootFence().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_fence_gate", new BlockSkyrootFenceGate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_ladder", new BlockCustomLadder().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_log", new BlockAetherLog(AetherWoodType.SKYROOT).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("skyroot_log_wall", new BlockSkyrootWall(new BlockAetherLog(AetherWoodType.SKYROOT).getDefaultState(), 2.0f, 10.0f).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("skyroot_planks", skyroot_planks.setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_pressure_plate", new BlockSkyrootPressurePlate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_sapling", new BlockAetherSkyrootSapling().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("skyroot_slab", new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_trapdoor", new BlockSkyrootTrapDoor().setCreativeTab(TAB_CONSTRUCTION));
		r.register("skyroot_twigs", new BlockFloorObject(Material.PLANTS, SoundType.WOOD).setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("standing_skyroot_sign", new BlockStandingSkyrootSign());
		r.register("stoneshroom", new BlockAetherMushroom().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("tall_aether_grass", new BlockTallAetherGrass().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("thera_dirt", new BlockTheraDirt().setCreativeTab(TAB_THERA));
		r.register("thera_grass", new BlockTheraGrass().setCreativeTab(TAB_THERA));
		r.register("therastone_brick", new BlockBuilder(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_THERA));
		r.register("therastone_brick_decorative", new BlockTherastoneDecorative().setCreativeTab(TAB_THERA));
		r.register("therastone_brick_slab", new BlockCustomSlab(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_THERA));
		r.register("therastone_brick_stairs", new BlockCustomStairs(holystone.getDefaultState()).setCreativeTab(TAB_THERA));
		r.register("therastone_pillar", new BlockCustomPillar(Material.ROCK).setSoundType(SoundType.STONE).setHardness(1.5f).setResistance(10.0f).setCreativeTab(TAB_THERA));
		r.register("therawood_beam", new BlockCustomPillar(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_THERA));
		r.register("therawood_decorative", new BlockTherawoodDecorative().setCreativeTab(TAB_THERA));
		r.register("therawood_leaves", new BlockAetherLeaves().setCreativeTab(TAB_THERA));
		r.register("therawood_log", new BlockAetherLog(AetherWoodType.THERA).setCreativeTab(TAB_THERA));
		r.register("therawood_planks", therawood_planks.setCreativeTab(TAB_THERA));
		r.register("therawood_slab", new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setCreativeTab(TAB_THERA));
		r.register("therawood_stairs", new BlockCustomStairs(therawood_planks.getDefaultState()).setCreativeTab(TAB_THERA));
		r.register("unique_sapling", new BlockAetherUniqueSapling().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("valkyrie_grass", new BlockValkyrieGrass().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("wall_skyroot_sign", new BlockWallSkyrootSign());
		r.register("wildcard", new BlockWildcard());
		r.register("wisproot_button", new BlockSkyrootButton().setCreativeTab(TAB_CONSTRUCTION));
		r.register("wisproot_fence", new BlockSkyrootFence().setCreativeTab(TAB_CONSTRUCTION));
		r.register("wisproot_fence_gate", new BlockSkyrootFenceGate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("wisproot_pressure_plate", new BlockSkyrootPressurePlate().setCreativeTab(TAB_CONSTRUCTION));
		r.register("wisproot_sapling", new BlockAetherWisprootSapling().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("wisproot_slab", new BlockCustomSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f).setResistance(5.0f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("wisproot_stairs", new BlockCustomStairs(skyroot_planks.getDefaultState()).setCreativeTab(TAB_CONSTRUCTION));
		r.register("woven_sticks", new BlockWovenSticks().setCreativeTab(TAB_NATURAL_BLOCKS));
		r.register("zanite_block", new BlockOreBlock(Material.IRON).setSoundType(SoundType.METAL).setHardness(5f).setResistance(10f).setCreativeTab(TAB_CONSTRUCTION));
		r.register("zanite_ore", new BlockZaniteOre().setCreativeTab(TAB_NATURAL_BLOCKS));

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

	public static void init()
	{
		registerFireInfo();
		registerHarvestLevels();
	}

	private static void registerFireInfo()
	{
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_twigs, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.tall_aether_grass, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.aether_flower, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.arctic_spikespring, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.blue_swingtip, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.forgotten_rose, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.green_swingtip, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.highlands_tulips, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.irradiated_flower, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.neverbloom, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.pink_swingtip, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.quickshoot, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.orange_tree, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.blueberry_bush, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.valkyrie_grass, 60, 100);
		Blocks.FIRE.setFireInfo(BlocksAether.brettl_plant, 60, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.light_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.golden_oak_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_log_wall, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_bookshelf, 30, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_beam, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.light_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.light_skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.light_skyroot_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.light_skyroot_beam, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.therawood_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.therawood_planks, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.therawood_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.therawood_beam, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_skyroot_log, 5, 5);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_skyroot_planks, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_skyroot_decorative, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_fence_gate, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_slab, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.wisproot_slab, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.greatroot_slab, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.skyroot_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(BlocksAether.woven_sticks, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.green_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_blue_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.amberoot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.blue_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_blue_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.green_dark_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.blue_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.dark_blue_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.green_light_skyroot_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.therawood_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.mutant_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.mutant_leaves_decorated, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.highlands_bush, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.cloudwool_block, 30, 60);
		Blocks.FIRE.setFireInfo(BlocksAether.cloudwool_carpet, 30, 60);
	}

	private static void registerHarvestLevels()
	{
		BlocksAether.aether_dirt.setHarvestLevel("shovel", 0);
		BlocksAether.aether_grass.setHarvestLevel("shovel", 0);
		BlocksAether.holystone.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick.setHarvestLevel("pickaxe", 0);
		BlocksAether.quicksoil.setHarvestLevel("shovel", 0);
		BlocksAether.ferrosite_sand.setHarvestLevel("shovel", 0);
		BlocksAether.aercloud.setHarvestLevel("shovel", 0);
		BlocksAether.highlands_snow.setHarvestLevel("shovel", 0);
		BlocksAether.highlands_snow_layer.setHarvestLevel("shovel", 0);
		BlocksAether.skyroot_decorative.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_chest.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_log.setHarvestLevel("axe", 0);
		BlocksAether.golden_oak_log.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_planks.setHarvestLevel("axe", 0);
		BlocksAether.woven_sticks.setHarvestLevel("axe", 0);
		BlocksAether.blueberry_bush.setHarvestLevel("axe", 0);
		BlocksAether.plumproot.setHarvestLevel("axe", 0);
		BlocksAether.ambrosium_ore.setHarvestLevel("pickaxe", 0);
		BlocksAether.zanite_ore.setHarvestLevel("pickaxe", 1);
		BlocksAether.gravitite_ore.setHarvestLevel("pickaxe", 2);
		BlocksAether.arkenium_ore.setHarvestLevel("pickaxe", 2);
		BlocksAether.icestone_ore.setHarvestLevel("pickaxe", 1);
		BlocksAether.icestone_bricks.setHarvestLevel("pickaxe", 1);
		BlocksAether.crude_scatterglass.setHarvestLevel("pickaxe", 0);
		BlocksAether.scatterglass.setHarvestLevel("pickaxe", 0);
		BlocksAether.highlands_ice.setHarvestLevel("pickaxe", 0);
		BlocksAether.highlands_packed_ice.setHarvestLevel("pickaxe", 0);
		BlocksAether.altar.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_furnace.setHarvestLevel("pickaxe", 0);
		BlocksAether.aether_crafting_table.setHarvestLevel("axe", 0);
		BlocksAether.icestone_cooler.setHarvestLevel("pickaxe", 0);
		BlocksAether.incubator.setHarvestLevel("pickaxe", 0);
		BlocksAether.masonry_bench.setHarvestLevel("pickaxe", 0);
		BlocksAether.zanite_block.setHarvestLevel("pickaxe", 1);
		BlocksAether.gravitite_block.setHarvestLevel("pickaxe", 2);
		BlocksAether.skyroot_door.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_trapdoor.setHarvestLevel("axe", 0);
		BlocksAether.secret_skyroot_door.setHarvestLevel("axe", 0);
		BlocksAether.secret_skyroot_trapdoor.setHarvestLevel("axe", 0);
		BlocksAether.holystone_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.mossy_holystone_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_log_wall.setHarvestLevel("axe", 0);
		BlocksAether.icestone_wall.setHarvestLevel("pickaxe", 1);
		BlocksAether.scatterglass_wall.setHarvestLevel("pickaxe", 1);
		BlocksAether.skyroot_slab.setHarvestLevel("axe", 0);
		BlocksAether.holystone_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.icestone_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_bookshelf.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_bookshelf.setHarvestLevel("axe", 0);
		BlocksAether.candy_cane_block.setHarvestLevel("pickaxe", 0);
		BlocksAether.candy_cane_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_ladder.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_button.setHarvestLevel("axe", 0);
		BlocksAether.wisproot_button.setHarvestLevel("axe", 0);
		BlocksAether.greatroot_button.setHarvestLevel("axe", 0);
		BlocksAether.holystone_button.setHarvestLevel("pickaxe", 0);
		BlocksAether.thera_grass.setHarvestLevel("shovel", 0);
		BlocksAether.thera_dirt.setHarvestLevel("shovel", 0);
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
