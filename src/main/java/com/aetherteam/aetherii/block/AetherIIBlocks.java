package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.construction.*;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.portal.AetherPortalBlock;
import com.aetherteam.aetherii.block.utility.*;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsConfiguredFeatures;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.materials.RockItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.FireBlockAccessor;
import com.aetherteam.aetherii.world.tree.AetherIITreeGrowers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AetherIIBlocks extends AetherIIBlockBuilders {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AetherII.MODID);

    // Portal
    public static final DeferredBlock<AetherPortalBlock> AETHER_PORTAL = registerWithoutItem("aether_portal", AetherPortalBlock::new, () -> Block.Properties.of().noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(AetherIIBlocks::lightLevel11).pushReaction(PushReaction.BLOCK).forceSolidOn().noLootTable());

    // Surface
    public static final DeferredBlock<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", AetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", EnchantedAetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.GOLD).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> AETHER_DIRT_PATH = register("aether_dirt_path", AetherDirtPathBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));
    public static final DeferredBlock<Block> AETHER_DIRT = register("aether_dirt", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL));
    public static final DeferredBlock<Block> COARSE_AETHER_DIRT = register("coarse_aether_dirt", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL));
    public static final DeferredBlock<Block> AETHER_FARMLAND = register("aether_farmland", AetherFarmBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));
    public static final DeferredBlock<Block> SHIMMERING_SILT = register("shimmering_silt", (properties) -> new ColoredFallingBlock(new ColorRGBA(8360341), properties), () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.5F).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));

    // Underground
    public static final DeferredBlock<Block> HOLYSTONE = register("holystone", () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> UNSTABLE_HOLYSTONE = register("unstable_holystone", UnstableBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> UNDERSHALE = register("undershale", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> UNSTABLE_UNDERSHALE = register("unstable_undershale", UnstableBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> AGIOSITE = register("agiosite", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> ICHORITE = register("ichorite", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final DeferredBlock<HalfTransparentBlock> CRUDE_SCATTERGLASS = register("crude_scatterglass", HalfTransparentBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<Block> SKY_ROOTS = register("sky_roots", AetherHangingRootsBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).replaceable().noCollission().instabreak().sound(SoundType.HANGING_ROOTS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<LiquidBlock> ACID = registerWithoutItem("acid", (properties) -> new AcidLiquidBlock(AetherIIFluids.ACID.get(), properties), () -> Block.Properties.of().mapColor(MapColor.FIRE).replaceable().noCollission().randomTicks().strength(100.0F).lightLevel(AetherIIBlocks::lightLevel8).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));
    public static final DeferredBlock<Block> GAS = registerWithoutItem("gas", GasBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(-1.0F, 0.0F).replaceable().noCollission().noOcclusion().noTerrainParticles().isValidSpawn(AetherIIBlockBuilders::never).isRedstoneConductor(AetherIIBlockBuilders::never).isSuffocating(AetherIIBlockBuilders::never).isViewBlocking(AetherIIBlockBuilders::never).noLootTable());
    public static final DeferredBlock<AbstractPointedStoneBlock> POINTED_HOLYSTONE = register("pointed_holystone", PointedHolystoneBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).isRedstoneConductor(AetherIIBlocks::never));
    public static final DeferredBlock<AbstractPointedStoneBlock> POINTED_ICHORITE = register("pointed_ichorite", PointedIchoriteBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(4.0F, 3.0F).dynamicShape().offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).isRedstoneConductor(AetherIIBlocks::never));

    // Highfields
    public static final DeferredBlock<Block> QUICKSOIL = register("quicksoil", QuicksoilBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(1.1F).sound(SoundType.SAND));
    public static final DeferredBlock<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> Block.Properties.ofFullCopy(HOLYSTONE.get()));
    public static final DeferredBlock<Block> BRYALINN_MOSS_BLOCK = register("bryalinn_moss_block", (properties) -> new AetherMossBlock(HighlandsConfiguredFeatures.BRYALINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BRYALINN_MOSS_CARPET = register("bryalinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BRYALINN_MOSS_VINES = register("bryalinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BRYALINN_MOSS_FLOWERS = register("bryalinn_moss_flowers", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> TANGLED_BRANCHES = register("tangled_branches", () -> Block.Properties.of().noOcclusion().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.WOOD));

    // Magnetic
    public static final DeferredBlock<Block> FERROSITE_SAND = register("ferrosite_sand", () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final DeferredBlock<Block> FERROSITE_MUD = register("ferrosite_mud", MudBlock::new, () -> Block.Properties.ofFullCopy(Blocks.MUD).mapColor(MapColor.COLOR_PURPLE));
    public static final DeferredBlock<Block> FERROSITE = register("ferrosite", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> RUSTED_FERROSITE = register("rusted_ferrosite", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> MAGNETIC_SHROOM = register("magnetic_shroom", (properties) -> new MushroomBlock(TreeFeatures.HUGE_BROWN_MUSHROOM, properties), () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel(light -> 5).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));

    // Arctic
    public static final DeferredBlock<Block> ARCTIC_SNOW_BLOCK = register("arctic_snow_block", () -> Block.Properties.of().mapColor(MapColor.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW));
    public static final DeferredBlock<Block> ARCTIC_SNOW = register("arctic_snow", SnowLayerBlock::new, () -> Block.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).sound(SoundType.SNOW).requiresCorrectToolForDrops().isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final DeferredBlock<Block> ARCTIC_ICE = register("arctic_ice", IceBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType.is(AetherIITags.Entities.SPAWNING_ICE)).isRedstoneConductor(AetherIIBlocks::never));
    public static final DeferredBlock<Block> ARCTIC_PACKED_ICE = register("arctic_packed_ice", () -> Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).friction(0.98F).strength(0.5F).sound(SoundType.GLASS));
    public static final DeferredBlock<Block> ICESTONE = register("icestone", IcestoneBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> LARGE_ARCTIC_ICE_CRYSTAL = register("large_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.of().mapColor(MapColor.ICE).forceSolidOn().noOcclusion().sound(SoundType.GLASS).strength(0.5F).pushReaction(PushReaction.DESTROY).randomTicks());
    public static final DeferredBlock<Block> MEDIUM_ARCTIC_ICE_CRYSTAL = register("medium_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.ofLegacyCopy(LARGE_ARCTIC_ICE_CRYSTAL.get()));
    public static final DeferredBlock<Block> SMALL_ARCTIC_ICE_CRYSTAL = register("small_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.ofLegacyCopy(LARGE_ARCTIC_ICE_CRYSTAL.get()));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_BLOCK = register("shayelinn_moss_block", (properties) -> new AetherMossBlock(HighlandsConfiguredFeatures.SHAYELINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_CARPET = register("shayelinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_VINES = register("shayelinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));

    // Irradiated
    public static final DeferredBlock<Block> IRRADIATED_HOLYSTONE = register("irradiated_holystone", IrradiatedBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()));
    public static final DeferredBlock<Block> IRRADIATED_DUST_BLOCK = register("irradiated_dust_block", IrradiatedBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F, 30.0F).lightLevel((state) -> 5).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> AMBRELINN_MOSS_BLOCK = register("ambrelinn_moss_block", (properties) -> new AetherMossBlock(HighlandsConfiguredFeatures.AMBRELINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> AMBRELINN_MOSS_CARPET = register("ambrelinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> AMBRELINN_MOSS_VINES = register("ambrelinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> TARAHESP_FLOWERS = register("tarahesp_flowers", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));

    // Ores
    public static final DeferredBlock<Block> HOLYSTONE_QUARTZ_ORE = register("holystone_quartz_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 5), properties), () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> AMBROSIUM_ORE = register("ambrosium_ore", (properties) -> new DropExperienceBlock(UniformInt.of(0, 2), properties), () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops().lightLevel(AetherIIBlocks::lightLevel8));
    public static final DeferredBlock<Block> ZANITE_ORE = register("zanite_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 5), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> GLINT_ORE = register("glint_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 5), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> ARKENIUM_ORE = register("arkenium_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> GRAVITITE_ORE = register("gravitite_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> UNDERSHALE_AMBROSIUM_ORE = register("undershale_ambrosium_ore", (properties) -> new DropExperienceBlock(UniformInt.of(0, 2), properties), () -> Block.Properties.ofFullCopy(AMBROSIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).lightLevel(AetherIIBlocks::lightLevel8));
    public static final DeferredBlock<Block> UNDERSHALE_ZANITE_ORE = register("undershale_zanite_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 5), properties), () -> Block.Properties.ofFullCopy(ZANITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> UNDERSHALE_GLINT_ORE = register("undershale_glint_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 5), properties), () -> Block.Properties.ofFullCopy(GLINT_ORE.get()).mapColor(MapColor.WOOL).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> UNDERSHALE_ARKENIUM_ORE = register("undershale_arkenium_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties), () -> Block.Properties.ofFullCopy(ARKENIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> UNDERSHALE_GRAVITITE_ORE = register("undershale_gravitite_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties), () -> Block.Properties.ofFullCopy(GRAVITITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> CORROBONITE_ORE = register("corrobonite_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> CORROBONITE_CLUSTER = register("corrobonite_cluster", CorroboniteClusterBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).strength(3.0F, 3.0F).noOcclusion().noCollission());

    // Aerclouds
    public static final DeferredBlock<Block> COLD_AERCLOUD = register("cold_aercloud", AercloudBlock::new, aercloudProperties(MapColor.SNOW));
    public static final DeferredBlock<Block> GOLDEN_AERCLOUD = register("golden_aercloud", AercloudBlock::new, aercloudProperties(MapColor.COLOR_YELLOW));
    public static final DeferredBlock<Block> BLUE_AERCLOUD = register("blue_aercloud", BlueAercloudBlock::new, aercloudProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> GREEN_AERCLOUD = register("green_aercloud", GreenAercloudBlock::new, aercloudProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final DeferredBlock<Block> PURPLE_AERCLOUD = register("purple_aercloud", PurpleAercloudBlock::new, aercloudProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> STORM_AERCLOUD = register("storm_aercloud", AercloudBlock::new, aercloudProperties(MapColor.DEEPSLATE));

    // Moa Nest
    public static final DeferredBlock<Block> WOVEN_SKYROOT_STICKS = register("woven_skyroot_sticks", () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.75F).sound(SoundType.GRASS));

    // Logs
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> GREATROOT_LOG = register("greatroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> GREATROOT_WOOD = register("greatroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GREATROOT_LOG = register("stripped_greatroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GREATROOT_WOOD = register("stripped_greatroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> WISPROOT_LOG = register("wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> MOSSY_WISPROOT_LOG = register("mossy_wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<FacingPillarBlock> MOSSY_WISPROOT_LOG_END = register("mossy_wisproot_log_end", FacingPillarBlock::new, (() -> Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<RotatedPillarBlock> WISPROOT_WOOD = register("wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> MOSSY_WISPROOT_WOOD = register("mossy_wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WISPROOT_LOG = register("stripped_wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WISPROOT_WOOD = register("stripped_wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> AMBEROOT_LOG = register("amberoot_log", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> AMBEROOT_WOOD = register("amberoot_wood", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));

    // Leaf Pile
    public static final DeferredBlock<Block> SKYROOT_LEAF_PILE = register("skyroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.GRASS));
    public static final DeferredBlock<Block> SKYPLANE_LEAF_PILE = register("skyplane_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> SKYBIRCH_LEAF_PILE = register("skybirch_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> SKYPINE_LEAF_PILE = register("skypine_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> WISPROOT_LEAF_PILE = register("wisproot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.DIAMOND));
    public static final DeferredBlock<Block> WISPTOP_LEAF_PILE = register("wisptop_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATROOT_LEAF_PILE = register("greatroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final DeferredBlock<Block> GREATOAK_LEAF_PILE = register("greatoak_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATBOA_LEAF_PILE = register("greatboa_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> AMBEROOT_LEAF_PILE = register("amberoot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.GOLD));
    public static final DeferredBlock<Block> IRRADIATED_SKYROOT_LEAF_PILE = register("irradiated_skyroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPLANE_LEAF_PILE = register("irradiated_skyplane_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYBIRCH_LEAF_PILE = register("irradiated_skybirch_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPINE_LEAF_PILE = register("irradiated_skypine_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPROOT_LEAF_PILE = register("irradiated_wisproot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPTOP_LEAF_PILE = register("irradiated_wisptop_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATROOT_LEAF_PILE = register("irradiated_greatroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATOAK_LEAF_PILE = register("irradiated_greatoak_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATBOA_LEAF_PILE = register("irradiated_greatboa_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));

    // Leaves
    public static final DeferredBlock<Block> SKYROOT_LEAVES = register("skyroot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYROOT_LEAVES, AetherIIBlocks.SKYROOT_LEAF_PILE), leavesProperties(MapColor.GRASS));
    public static final DeferredBlock<Block> SKYPLANE_LEAVES = register("skyplane_leaves", (properties) -> new BlocksLightLeavesBlock(properties, AetherIIParticleTypes.SKYPLANE_LEAVES, AetherIIBlocks.SKYPLANE_LEAF_PILE), leavesProperties(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> SKYBIRCH_LEAVES = register("skybirch_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYBIRCH_LEAVES, AetherIIBlocks.SKYBIRCH_LEAF_PILE), leavesProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> SKYPINE_LEAVES = register("skypine_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYPINE_LEAVES, AetherIIBlocks.SKYPINE_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> WISPROOT_LEAVES = register("wisproot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.WISPROOT_LEAVES, AetherIIBlocks.WISPROOT_LEAF_PILE), leavesProperties(MapColor.DIAMOND));
    public static final DeferredBlock<Block> WISPTOP_LEAVES = register("wisptop_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.WISPTOP_LEAVES, AetherIIBlocks.WISPTOP_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATROOT_LEAVES = register("greatroot_leaves", (properties) -> new BlocksLightLeavesBlock(properties, AetherIIParticleTypes.GREATROOT_LEAVES, AetherIIBlocks.GREATROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final DeferredBlock<Block> GREATOAK_LEAVES = register("greatoak_leaves", (properties) -> new BlocksLightLeavesBlock(properties, AetherIIParticleTypes.GREATOAK_LEAVES, AetherIIBlocks.GREATOAK_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATBOA_LEAVES = register("greatboa_leaves", (properties) -> new BlocksLightLeavesBlock(properties, AetherIIParticleTypes.GREATBOA_LEAVES, AetherIIBlocks.GREATBOA_LEAF_PILE), leavesProperties(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> AMBEROOT_LEAVES = register("amberoot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.AMBEROOT_LEAVES, AetherIIBlocks.AMBEROOT_LEAF_PILE), leavesProperties(MapColor.GOLD));
    public static final DeferredBlock<Block> IRRADIATED_SKYROOT_LEAVES = register("irradiated_skyroot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPLANE_LEAVES = register("irradiated_skyplane_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYBIRCH_LEAVES = register("irradiated_skybirch_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPINE_LEAVES = register("irradiated_skypine_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPROOT_LEAVES = register("irradiated_wisproot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPTOP_LEAVES = register("irradiated_wisptop_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATROOT_LEAVES = register("irradiated_greatroot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATOAK_LEAVES = register("irradiated_greatoak_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATBOA_LEAVES = register("irradiated_greatboa_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));

    // Saplings
    public static final DeferredBlock<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> SKYPLANE_SAPLING = register("skyplane_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYPLANE, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> SKYBIRCH_SAPLING = register("skybirch_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYBIRCH, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> SKYPINE_SAPLING = register("skypine_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYPINE, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> WISPROOT_SAPLING = register("wisproot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.WISPROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> WISPTOP_SAPLING = register("wisptop_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.WISPTOP, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> GREATROOT_SAPLING = register("greatroot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> GREATOAK_SAPLING = register("greatoak_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATOAK, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> GREATBOA_SAPLING = register("greatboa_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATBOA, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> AMBEROOT_SAPLING = register("amberoot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.AMBEROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));

    // Potted Saplings
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYROOT_SAPLING = registerWithoutItem("potted_skyroot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYPLANE_SAPLING = registerWithoutItem("potted_skyplane_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPLANE_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYBIRCH_SAPLING = registerWithoutItem("potted_skybirch_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYBIRCH_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYPINE_SAPLING = registerWithoutItem("potted_skypine_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPINE_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WISPROOT_SAPLING = registerWithoutItem("potted_wisproot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPROOT_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WISPTOP_SAPLING = registerWithoutItem("potted_wisptop_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPTOP_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATROOT_SAPLING = registerWithoutItem("potted_greatroot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATROOT_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATOAK_SAPLING = registerWithoutItem("potted_greatoak_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATOAK_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATBOA_SAPLING = registerWithoutItem("potted_greatboa_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATBOA_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AMBEROOT_SAPLING = registerWithoutItem("potted_amberoot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AMBEROOT_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    // Grasses
    public static final DeferredBlock<Block> AETHER_SHORT_GRASS = register("aether_short_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final DeferredBlock<Block> AETHER_MEDIUM_GRASS = register("aether_medium_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final DeferredBlock<Block> AETHER_LONG_GRASS = register("aether_long_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final DeferredBlock<Block> HIGHLAND_FERN = register("highland_fern", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final DeferredBlock<Block> SHIELD_FERN = register("shield_fern", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));

    // Flowers
    public static final DeferredBlock<Block> HESPEROSE = register("hesperose", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 4, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION));
    public static final DeferredBlock<Block> TARABLOOM = register("tarabloom", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> POASPROUT = register("poasprout", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> LILICHIME = register("lilichime", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> PLURACIAN = register("pluracian", (properties) -> new FacingFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> SATIVAL_SHOOT = register("satival_shoot", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> HOLPUPEA = register("holpupea", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BLADE_POA = register("blade_poa", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION)); //todo mob effects
    public static final DeferredBlock<Block> AECHOR_CUTTING = register("aechor_cutting", AechorCuttingBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY));

    // Potted Flowers
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAGNETIC_SHROOM = registerWithoutItem("potted_magnetic_shroom", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MAGNETIC_SHROOM, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_HIGHLAND_FERN = registerWithoutItem("potted_highland_fern", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HIGHLAND_FERN, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SHIELD_FERN = registerWithoutItem("potted_shield_fern", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHIELD_FERN, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_HESPEROSE = registerWithoutItem("potted_hesperose", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HESPEROSE, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_TARABLOOM = registerWithoutItem("potted_tarabloom", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TARABLOOM, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_POASPROUT = registerWithoutItem("potted_poasprout", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, POASPROUT, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PLURACIAN = registerWithoutItem("potted_pluracian", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PLURACIAN, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SATIVAL_SHOOT = registerWithoutItem("potted_satival_shoot", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SATIVAL_SHOOT, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_LILICHIME = registerWithoutItem("potted_lilichime", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LILICHIME, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLADE_POA = registerWithoutItem("potted_blade_poa", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLADE_POA, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AECHOR_CUTTING = registerWithoutItem("potted_aechor_cutting", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AECHOR_CUTTING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    // Bushes
    public static final DeferredBlock<Block> HIGHLANDS_BUSH = register("highlands_bush", HighlandsBushBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH = register("blueberry_bush", BlueberryBushBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH_STEM = register("blueberry_bush_stem", BlueberryBushStemBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noCollission());

    // Potted Bushes
    public static final DeferredBlock<FlowerPotBlock> POTTED_HIGHLANDS_BUSH = registerWithoutItem("potted_highlands_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HIGHLANDS_BUSH, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUEBERRY_BUSH = registerWithoutItem("potted_blueberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUEBERRY_BUSH_STEM = registerWithoutItem("potted_blueberry_bush_stem", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH_STEM, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    // Orange Tree
    public static final DeferredBlock<Block> ORANGE_TREE = register("orange_tree", OrangeTreeBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).noCollission().strength(0.2F).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));

    // Potted Orange Tree
    public static final DeferredBlock<FlowerPotBlock> POTTED_ORANGE_TREE = registerWithoutItem("potted_orange_tree", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_TREE, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    // Valkyrie Sprout
    public static final DeferredBlock<Block> VALKYRIE_SPROUT = register("valkyrie_sprout", ValkyrieSproutBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).instabreak().noCollission().offsetType(Block.OffsetType.XZ));

    // Brettl
    public static final DeferredBlock<Block> BRETTL_PLANT = registerWithoutItem("brettl_plant", BrettlPlantBlock::new, () -> Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BRETTL_PLANT_TIP = registerWithoutItem("brettl_plant_tip", BrettlPlantTipBlock::new, () -> Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));

    // Lake
    public static final DeferredBlock<Block> ARILUM_SHOOT = registerWithoutItem("arilum_shoot", ArilumShootBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> ARILUM = register("arilum", ArilumBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> ARILUM_PLANT = register("arilum_plant", ArilumPlantBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> BLOOMING_ARILUM = register("blooming_arilum", BloomingArilumBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).lightLevel((block) -> 5));
    public static final DeferredBlock<Block> BLOOMING_ARILUM_PLANT = register("blooming_arilum_plant", BloomingArilumPlantBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).lightLevel((block) -> 5));

    // Ground Decoration
    public static final DeferredBlock<Block> SKYROOT_TWIG = register("skyroot_twig", TwigBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> HOLYSTONE_ROCK = register("holystone_rock", RockBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).noOcclusion().noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY));

    // Skyroot Planks
    public static final DeferredBlock<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> Block.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<StairBlock> SKYROOT_STAIRS = register("skyroot_stairs", (properties) -> new StairBlock(SKYROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.SKYROOT_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> SKYROOT_SLAB = register("skyroot_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final DeferredBlock<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", FenceBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", (properties) -> new FenceGateBlock(AetherIIWoodTypes.SKYROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final DeferredBlock<DoorBlock> SKYROOT_DOOR = register("skyroot_door", (properties) -> new DoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", (properties) -> new TrapDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final DeferredBlock<ButtonBlock> SKYROOT_BUTTON = register("skyroot_button", (properties) -> new ButtonBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, 30, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", (properties) -> new PressurePlateBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));

    // Skyroot Decorative Blocks
    public static final DeferredBlock<Block> SKYROOT_FLOORBOARDS = register("skyroot_floorboards", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<Block> SKYROOT_HIGHLIGHT = register("skyroot_highlight", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<Block> SKYROOT_SHINGLES = register("skyroot_shingles", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<Block> SKYROOT_SMALL_SHINGLES = register("skyroot_small_shingles", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<Block> SKYROOT_BASE_PLANKS = register("skyroot_base_planks", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<Block> SKYROOT_TOP_PLANKS = register("skyroot_top_planks", () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_BASE_BEAM = register("skyroot_base_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_TOP_BEAM = register("skyroot_top_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_BEAM = register("skyroot_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(SKYROOT_PLANKS.get()));
    public static final DeferredBlock<DoorBlock> SECRET_SKYROOT_DOOR = register("secret_skyroot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(SKYROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());
    public static final DeferredBlock<TrapDoorBlock> SECRET_SKYROOT_TRAPDOOR = register("secret_skyroot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));

    // Greatroot Planks
    public static final DeferredBlock<Block> GREATROOT_PLANKS = register("greatroot_planks", () -> Block.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<StairBlock> GREATROOT_STAIRS = register("greatroot_stairs", (properties) -> new StairBlock(GREATROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.GREATROOT_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> GREATROOT_SLAB = register("greatroot_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.GREATROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final DeferredBlock<FenceBlock> GREATROOT_FENCE = register("greatroot_fence", FenceBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> GREATROOT_FENCE_GATE = register("greatroot_fence_gate", (properties) -> new FenceGateBlock(AetherIIWoodTypes.GREATROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final DeferredBlock<DoorBlock> GREATROOT_DOOR = register("greatroot_door", (properties) -> new DoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> GREATROOT_TRAPDOOR = register("greatroot_trapdoor", (properties) -> new TrapDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final DeferredBlock<ButtonBlock> GREATROOT_BUTTON = register("greatroot_button", (properties) -> new ButtonBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, 30, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> GREATROOT_PRESSURE_PLATE = register("greatroot_pressure_plate", (properties) -> new PressurePlateBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));

    // Greatroot Decorative Blocks
    public static final DeferredBlock<Block> GREATROOT_FLOORBOARDS = register("greatroot_floorboards", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<Block> GREATROOT_HIGHLIGHT = register("greatroot_highlight", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<Block> GREATROOT_SHINGLES = register("greatroot_shingles", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<Block> GREATROOT_SMALL_SHINGLES = register("greatroot_small_shingles", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<Block> GREATROOT_BASE_PLANKS = register("greatroot_base_planks", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<Block> GREATROOT_TOP_PLANKS = register("greatroot_top_planks", () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_BASE_BEAM = register("greatroot_base_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_TOP_BEAM = register("greatroot_top_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_BEAM = register("greatroot_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(GREATROOT_PLANKS.get()));
    public static final DeferredBlock<DoorBlock> SECRET_GREATROOT_DOOR = register("secret_greatroot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<TrapDoorBlock> SECRET_GREATROOT_TRAPDOOR = register("secret_greatroot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());

    // Wisproot Planks
    public static final DeferredBlock<Block> WISPROOT_PLANKS = register("wisproot_planks", () -> Block.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<StairBlock> WISPROOT_STAIRS = register("wisproot_stairs", (properties) -> new StairBlock(WISPROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.WISPROOT_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> WISPROOT_SLAB = register("wisproot_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.WISPROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final DeferredBlock<FenceBlock> WISPROOT_FENCE = register("wisproot_fence", FenceBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> WISPROOT_FENCE_GATE = register("wisproot_fence_gate", (properties) -> new FenceGateBlock(AetherIIWoodTypes.WISPROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final DeferredBlock<DoorBlock> WISPROOT_DOOR = register("wisproot_door", (properties) -> new DoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> WISPROOT_TRAPDOOR = register("wisproot_trapdoor", (properties) -> new TrapDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final DeferredBlock<ButtonBlock> WISPROOT_BUTTON = register("wisproot_button", (properties) -> new ButtonBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, 30, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> WISPROOT_PRESSURE_PLATE = register("wisproot_pressure_plate", (properties) -> new PressurePlateBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));

    // Wisproot Decorative Blocks
    public static final DeferredBlock<Block> WISPROOT_FLOORBOARDS = register("wisproot_floorboards", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<Block> WISPROOT_HIGHLIGHT = register("wisproot_highlight", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<Block> WISPROOT_SHINGLES = register("wisproot_shingles", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<Block> WISPROOT_SMALL_SHINGLES = register("wisproot_small_shingles", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<Block> WISPROOT_BASE_PLANKS = register("wisproot_base_planks", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<Block> WISPROOT_TOP_PLANKS = register("wisproot_top_planks", () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_BASE_BEAM = register("wisproot_base_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_TOP_BEAM = register("wisproot_top_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_BEAM = register("wisproot_beam", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(WISPROOT_PLANKS.get()));
    public static final DeferredBlock<DoorBlock> SECRET_WISPROOT_DOOR = register("secret_wisproot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<TrapDoorBlock> SECRET_WISPROOT_TRAPDOOR = register("secret_wisproot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());

    // Holystone
    public static final DeferredBlock<StairBlock> HOLYSTONE_STAIRS = register("holystone_stairs", (properties) -> new StairBlock(HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> HOLYSTONE_WALL = register("holystone_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()).forceSolidOn());
    public static final DeferredBlock<ButtonBlock> HOLYSTONE_BUTTON = register("holystone_button", (properties) -> new ButtonBlock(BlockSetType.STONE, 20, properties), () -> Block.Properties.ofFullCopy(Blocks.STONE_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetType.STONE, properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F));

    // Mossy Holystone
    public static final DeferredBlock<StairBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs", (properties) -> new StairBlock(MOSSY_HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get()));
    public static final DeferredBlock<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).forceSolidOn());

    // Irradiated Holystone
    public static final DeferredBlock<StairBlock> IRRADIATED_HOLYSTONE_STAIRS = register("irradiated_holystone_stairs", (properties) -> new StairBlock(IRRADIATED_HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()));
    public static final DeferredBlock<SlabBlock> IRRADIATED_HOLYSTONE_SLAB = register("irradiated_holystone_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> IRRADIATED_HOLYSTONE_WALL = register("irradiated_holystone_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).forceSolidOn());

    // Holystone Bricks
    public static final DeferredBlock<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<StairBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs", (properties) -> new StairBlock(HOLYSTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).forceSolidOn());

    // Holystone Decorative Blocks
    public static final DeferredBlock<Block> HOLYSTONE_FLAGSTONES = register("holystone_flagstones", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> HOLYSTONE_HEADSTONE = register("holystone_headstone", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> HOLYSTONE_KEYSTONE = register("holystone_keystone", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> HOLYSTONE_BASE_BRICKS = register("holystone_base_bricks", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> HOLYSTONE_CAPSTONE_BRICKS = register("holystone_capstone_bricks", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_BASE_PILLAR = register("holystone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_CAPSTONE_PILLAR = register("holystone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_PILLAR = register("holystone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));

    // Faded Holystone Bricks
    public static final DeferredBlock<Block> FADED_HOLYSTONE_BRICKS = register("faded_holystone_bricks", () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<StairBlock> FADED_HOLYSTONE_BRICK_STAIRS = register("faded_holystone_brick_stairs", (properties) -> new StairBlock(FADED_HOLYSTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> FADED_HOLYSTONE_BRICK_SLAB = register("faded_holystone_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> FADED_HOLYSTONE_BRICK_WALL = register("faded_holystone_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).forceSolidOn());

    // Faded Holystone Decorative Blocks
    public static final DeferredBlock<Block> FADED_HOLYSTONE_FLAGSTONES = register("faded_holystone_flagstones", () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_HEADSTONE = register("faded_holystone_headstone", () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_KEYSTONE = register("faded_holystone_keystone", () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_BASE_BRICKS = register("faded_holystone_base_bricks", () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_CAPSTONE_BRICKS = register("faded_holystone_capstone_bricks", () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_BASE_PILLAR = register("faded_holystone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_CAPSTONE_PILLAR = register("faded_holystone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_PILLAR = register("faded_holystone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get()));

    // Undershale
    public static final DeferredBlock<StairBlock> UNDERSHALE_STAIRS = register("undershale_stairs", (properties) -> new StairBlock(UNDERSHALE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get()));
    public static final DeferredBlock<SlabBlock> UNDERSHALE_SLAB = register("undershale_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get()));
    public static final DeferredBlock<WallBlock> UNDERSHALE_WALL = register("undershale_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get()).forceSolidOn());

    // Undershale Bricks
    public static final DeferredBlock<Block> UNDERSHALE_BRICKS = register("undershale_bricks", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final DeferredBlock<StairBlock> UNDERSHALE_BRICK_STAIRS = register("undershale_brick_stairs", (properties) -> new StairBlock(UNDERSHALE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> UNDERSHALE_BRICK_SLAB = register("undershale_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE_BRICKS.get()));
    public static final DeferredBlock<WallBlock> UNDERSHALE_BRICK_WALL = register("undershale_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE_BRICKS.get()).forceSolidOn());

    //Ichorite
    public static final DeferredBlock<StairBlock> ICHORITE_STAIRS = register("ichorite_stairs", (properties) -> new StairBlock(ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICHORITE.get()));
    public static final DeferredBlock<SlabBlock> ICHORITE_SLAB = register("ichorite_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICHORITE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> ICHORITE_WALL = register("ichorite_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICHORITE.get()).forceSolidOn());

    // Smooth Ichorite
    public static final DeferredBlock<Block> SMOOTH_ICHORITE = register("smooth_ichorite", () -> Block.Properties.ofFullCopy(ICHORITE.get()));
    public static final DeferredBlock<StairBlock> SMOOTH_ICHORITE_STAIRS = register("smooth_ichorite_stairs", (properties) -> new StairBlock(SMOOTH_ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.SMOOTH_ICHORITE.get()));
    public static final DeferredBlock<SlabBlock> SMOOTH_ICHORITE_SLAB = register("smooth_ichorite_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.SMOOTH_ICHORITE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> SMOOTH_ICHORITE_WALL = register("smooth_ichorite_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.SMOOTH_ICHORITE.get()).forceSolidOn());

    // Ichorite Bricks
    public static final DeferredBlock<Block> ICHORITE_BRICKS = register("ichorite_bricks", () -> Block.Properties.ofFullCopy(SMOOTH_ICHORITE.get()));
    public static final DeferredBlock<StairBlock> ICHORITE_BRICK_STAIRS = register("ichorite_brick_stairs", (properties) -> new StairBlock(ICHORITE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.SMOOTH_ICHORITE.get()));
    public static final DeferredBlock<SlabBlock> ICHORITE_BRICK_SLAB = register("ichorite_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICHORITE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> ICHORITE_BRICK_WALL = register("ichorite_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICHORITE_BRICKS.get()).forceSolidOn());

    // Marbled Ichorite Decorative Blocks
    public static final DeferredBlock<Block> ICHORITE_FLAGSTONES = register("ichorite_flagstones", () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<Block> ICHORITE_RUNESTONE = register("ichorite_runestone", () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<Block> ICHORITE_KEYSTONE = register("ichorite_keystone", () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<Block> ICHORITE_BASE_BRICKS = register("ichorite_base_bricks", () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<Block> ICHORITE_CAPSTONE_BRICKS = register("ichorite_capstone_bricks", () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICHORITE_BASE_PILLAR = register("ichorite_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICHORITE_CAPSTONE_PILLAR = register("ichorite_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICHORITE_PILLAR = register("ichorite_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICHORITE_BRICKS.get()));

    // Marbled Ichorite
    public static final DeferredBlock<Block> MARBLED_ICHORITE = register("marbled_ichorite", () -> Block.Properties.ofFullCopy(ICHORITE.get()));
    public static final DeferredBlock<StairBlock> MARBLED_ICHORITE_STAIRS = register("marbled_ichorite_stairs", (properties) -> new StairBlock(MARBLED_ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_ICHORITE.get()));
    public static final DeferredBlock<SlabBlock> MARBLED_ICHORITE_SLAB = register("marbled_ichorite_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_ICHORITE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> MARBLED_ICHORITE_WALL = register("marbled_ichorite_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_ICHORITE.get()).forceSolidOn());

    // Marbled Bricks
    public static final DeferredBlock<Block> MARBLED_BRICKS = register("marbled_bricks", () -> Block.Properties.ofFullCopy(MARBLED_ICHORITE.get()));
    public static final DeferredBlock<StairBlock> MARBLED_BRICK_STAIRS = register("marbled_brick_stairs", (properties) -> new StairBlock(MARBLED_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> MARBLED_BRICK_SLAB = register("marbled_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> MARBLED_BRICK_WALL = register("marbled_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.MARBLED_BRICKS.get()).forceSolidOn());

    // Marbled Ichorite Decorative Blocks
    public static final DeferredBlock<Block> MARBLED_FLAGSTONES = register("marbled_flagstones", () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<Block> MARBLED_KEYSTONE = register("marbled_keystone", () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<Block> MARBLED_BASE_BRICKS = register("marbled_base_bricks", () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<Block> MARBLED_CAPSTONE_BRICKS = register("marbled_capstone_bricks", () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> MARBLED_BASE_PILLAR = register("marbled_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> MARBLED_CAPSTONE_PILLAR = register("marbled_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> MARBLED_PILLAR = register("marbled_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(MARBLED_BRICKS.get()));

    // Agiosite
    public static final DeferredBlock<StairBlock> AGIOSITE_STAIRS = register("agiosite_stairs", (properties) -> new StairBlock(AGIOSITE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get()));
    public static final DeferredBlock<SlabBlock> AGIOSITE_SLAB = register("agiosite_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> AGIOSITE_WALL = register("agiosite_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get()).forceSolidOn());

    // Agiosite Bricks
    public static final DeferredBlock<Block> AGIOSITE_BRICKS = register("agiosite_bricks", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<StairBlock> AGIOSITE_BRICK_STAIRS = register("agiosite_brick_stairs", (properties) -> new StairBlock(AGIOSITE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> AGIOSITE_BRICK_SLAB = register("agiosite_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> AGIOSITE_BRICK_WALL = register("agiosite_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get()).forceSolidOn());

    // Agiosite Decorative Blocks
    public static final DeferredBlock<Block> AGIOSITE_FLAGSTONES = register("agiosite_flagstones", () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<Block> AGIOSITE_KEYSTONE = register("agiosite_keystone", () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<Block> AGIOSITE_BASE_BRICKS = register("agiosite_base_bricks", () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<Block> AGIOSITE_CAPSTONE_BRICKS = register("agiosite_capstone_bricks", () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_BASE_PILLAR = register("agiosite_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_CAPSTONE_PILLAR = register("agiosite_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_PILLAR = register("agiosite_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get()));

    // Icestone
    public static final DeferredBlock<StairBlock> ICESTONE_STAIRS = register("icestone_stairs", (properties) -> new IcestoneStairsBlock(ICESTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get()));
    public static final DeferredBlock<SlabBlock> ICESTONE_SLAB = register("icestone_slab", IcestoneSlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<WallBlock> ICESTONE_WALL = register("icestone_wall", IcestoneWallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get()).forceSolidOn());

    // Icestone Bricks
    public static final DeferredBlock<Block> ICESTONE_BRICKS = register("icestone_bricks", () -> Block.Properties.of().mapColor(MapColor.ICE).sound(SoundType.GLASS).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<StairBlock> ICESTONE_BRICK_STAIRS = register("icestone_bricks_stairs", (properties) -> new StairBlock(ICESTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get()));
    public static final DeferredBlock<SlabBlock> ICESTONE_BRICK_SLAB = register("icestone_bricks_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<WallBlock> ICESTONE_BRICK_WALL = register("icestone_bricks_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get()).forceSolidOn());

    // Icestone Decorative Blocks
    public static final DeferredBlock<Block> ICESTONE_FLAGSTONES = register("icestone_flagstones", () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<Block> ICESTONE_KEYSTONE = register("icestone_keystone", () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<Block> ICESTONE_BASE_BRICKS = register("icestone_base_bricks", () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<Block> ICESTONE_CAPSTONE_BRICKS = register("icestone_capstone_bricks", () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_BASE_PILLAR = register("icestone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_CAPSTONE_PILLAR = register("icestone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_PILLAR = register("icestone_pillar", FacingPillarBlock::new, () -> Block.Properties.ofFullCopy(ICESTONE_BRICKS.get()));

    // Glass
    public static final DeferredBlock<TransparentBlock> QUICKSOIL_GLASS = register("quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<TransparentBlock> TILED_QUICKSOIL_GLASS = register("tiled_quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.ofFullCopy(QUICKSOIL_GLASS.get()));
    public static final DeferredBlock<TransparentBlock> GRIDDED_QUICKSOIL_GLASS = register("gridded_quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.ofFullCopy(QUICKSOIL_GLASS.get()));
    public static final DeferredBlock<HalfTransparentBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS = register("skyroot_framed_crude_scatterglass", HalfTransparentBlock::new, () -> Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS.get()));
    public static final DeferredBlock<HalfTransparentBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS = register("arkenium_framed_crude_scatterglass", HalfTransparentBlock::new, () -> Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS.get()));
    public static final DeferredBlock<TransparentBlock> SCATTERGLASS = register("scatterglass", ScatterglassBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<TransparentBlock> SKYROOT_FRAMED_SCATTERGLASS = register("skyroot_framed_scatterglass", ScatterglassBlock::new, () -> Block.Properties.ofFullCopy(SCATTERGLASS.get()));
    public static final DeferredBlock<TransparentBlock> ARKENIUM_FRAMED_SCATTERGLASS = register("arkenium_framed_scatterglass", ScatterglassBlock::new, () -> Block.Properties.ofFullCopy(SCATTERGLASS.get()));

    // Glass Panes
    public static final DeferredBlock<IronBarsBlock> QUICKSOIL_GLASS_PANE = register("quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<IronBarsBlock> TILED_QUICKSOIL_GLASS_PANE = register("tiled_quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.ofFullCopy(QUICKSOIL_GLASS_PANE.get()));
    public static final DeferredBlock<IronBarsBlock> GRIDDED_QUICKSOIL_GLASS_PANE = register("gridded_quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.ofFullCopy(QUICKSOIL_GLASS_PANE.get()));
    public static final DeferredBlock<IronBarsBlock> CRUDE_SCATTERGLASS_PANE = register("crude_scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never));
    public static final DeferredBlock<IronBarsBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE = register("skyroot_framed_crude_scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS_PANE.get()));
    public static final DeferredBlock<IronBarsBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE = register("arkenium_framed_crude_scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS_PANE.get()));
    public static final DeferredBlock<IronBarsBlock> SCATTERGLASS_PANE = register("scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops());
    public static final DeferredBlock<IronBarsBlock> SKYROOT_FRAMED_SCATTERGLASS_PANE = register("skyroot_framed_scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.ofFullCopy(SCATTERGLASS_PANE.get()));
    public static final DeferredBlock<IronBarsBlock> ARKENIUM_FRAMED_SCATTERGLASS_PANE = register("arkenium_framed_scatterglass_pane", IronBarsBlock::new, () -> Block.Properties.ofFullCopy(SCATTERGLASS_PANE.get()));

    // Wool
    public static final DeferredBlock<Block> CLOUDWOOL = register("cloudwool", () -> Block.Properties.ofFullCopy(Blocks.WHITE_WOOL));
    public static final DeferredBlock<Block> WHITE_CLOUDWOOL = register("white_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.WHITE_WOOL));
    public static final DeferredBlock<Block> ORANGE_CLOUDWOOL = register("orange_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.ORANGE_WOOL));
    public static final DeferredBlock<Block> MAGENTA_CLOUDWOOL = register("magenta_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.MAGENTA_WOOL));
    public static final DeferredBlock<Block> LIGHT_BLUE_CLOUDWOOL = register("light_blue_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL));
    public static final DeferredBlock<Block> YELLOW_CLOUDWOOL = register("yellow_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.YELLOW_WOOL));
    public static final DeferredBlock<Block> LIME_CLOUDWOOL = register("lime_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.LIME_WOOL));
    public static final DeferredBlock<Block> PINK_CLOUDWOOL = register("pink_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.PINK_WOOL));
    public static final DeferredBlock<Block> GRAY_CLOUDWOOL = register("gray_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.GRAY_WOOL));
    public static final DeferredBlock<Block> LIGHT_GRAY_CLOUDWOOL = register("light_gray_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL));
    public static final DeferredBlock<Block> CYAN_CLOUDWOOL = register("cyan_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.CYAN_WOOL));
    public static final DeferredBlock<Block> PURPLE_CLOUDWOOL = register("purple_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.PURPLE_WOOL));
    public static final DeferredBlock<Block> BLUE_CLOUDWOOL = register("blue_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.BLUE_WOOL));
    public static final DeferredBlock<Block> BROWN_CLOUDWOOL = register("brown_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.BROWN_WOOL));
    public static final DeferredBlock<Block> GREEN_CLOUDWOOL = register("green_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.GREEN_WOOL));
    public static final DeferredBlock<Block> RED_CLOUDWOOL = register("red_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.RED_WOOL));
    public static final DeferredBlock<Block> BLACK_CLOUDWOOL = register("black_cloudwool", () -> Block.Properties.ofFullCopy(Blocks.BLACK_WOOL));

    // Carpet
    public static final DeferredBlock<CarpetBlock> CLOUDWOOL_CARPET = register("cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.WHITE_CARPET));
    public static final DeferredBlock<CarpetBlock> WHITE_CLOUDWOOL_CARPET = register("white_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.WHITE_CARPET));
    public static final DeferredBlock<CarpetBlock> ORANGE_CLOUDWOOL_CARPET = register("orange_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.ORANGE_CARPET));
    public static final DeferredBlock<CarpetBlock> MAGENTA_CLOUDWOOL_CARPET = register("magenta_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.MAGENTA_CARPET));
    public static final DeferredBlock<CarpetBlock> LIGHT_BLUE_CLOUDWOOL_CARPET = register("light_blue_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.LIGHT_BLUE_CARPET));
    public static final DeferredBlock<CarpetBlock> YELLOW_CLOUDWOOL_CARPET = register("yellow_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.YELLOW_CARPET));
    public static final DeferredBlock<CarpetBlock> LIME_CLOUDWOOL_CARPET = register("lime_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.LIME_CARPET));
    public static final DeferredBlock<CarpetBlock> PINK_CLOUDWOOL_CARPET = register("pink_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.PINK_CARPET));
    public static final DeferredBlock<CarpetBlock> GRAY_CLOUDWOOL_CARPET = register("gray_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.GRAY_CARPET));
    public static final DeferredBlock<CarpetBlock> LIGHT_GRAY_CLOUDWOOL_CARPET = register("light_gray_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CARPET));
    public static final DeferredBlock<CarpetBlock> CYAN_CLOUDWOOL_CARPET = register("cyan_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.CYAN_CARPET));
    public static final DeferredBlock<CarpetBlock> PURPLE_CLOUDWOOL_CARPET = register("purple_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.PURPLE_CARPET));
    public static final DeferredBlock<CarpetBlock> BLUE_CLOUDWOOL_CARPET = register("blue_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BLUE_CARPET));
    public static final DeferredBlock<CarpetBlock> BROWN_CLOUDWOOL_CARPET = register("brown_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BROWN_CARPET));
    public static final DeferredBlock<CarpetBlock> GREEN_CLOUDWOOL_CARPET = register("green_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.GREEN_CARPET));
    public static final DeferredBlock<CarpetBlock> RED_CLOUDWOOL_CARPET = register("red_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.RED_CARPET));
    public static final DeferredBlock<CarpetBlock> BLACK_CLOUDWOOL_CARPET = register("black_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BLACK_CARPET));

    // Arkenium Blocks
    public static final DeferredBlock<DoorBlock> ARKENIUM_DOOR = register("arkenium_door", (properties) -> new DoorBlock(BlockSetType.IRON, properties), () -> Block.Properties.ofFullCopy(Blocks.IRON_DOOR));
    public static final DeferredBlock<TrapDoorBlock> ARKENIUM_TRAPDOOR = register("arkenium_trapdoor", (properties) -> new TrapDoorBlock(BlockSetType.IRON, properties), () -> Block.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR));

    // Mineral Blocks
    public static final DeferredBlock<Block> AMBROSIUM_BLOCK = register("ambrosium_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> ZANITE_BLOCK = register("zanite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> ARKENIUM_BLOCK = register("arkenium_block", () -> Block.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> GRAVITITE_BLOCK = register("gravitite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));

    // Arilum Lantern
    public static final DeferredBlock<Block> GREEN_ARILUM_LANTERN = register("green_arilum_lantern", arilumLanternProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final DeferredBlock<Block> BLUE_ARILUM_LANTERN = register("blue_arilum_lantern", arilumLanternProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> PURPLE_ARILUM_LANTERN = register("purple_arilum_lantern", arilumLanternProperties(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GOLDEN_ARILUM_LANTERN = register("golden_arilum_lantern", arilumLanternProperties(MapColor.COLOR_YELLOW));
    public static final DeferredBlock<Block> WHITE_ARILUM_LANTERN = register("white_arilum_lantern", arilumLanternProperties(MapColor.COLOR_LIGHT_GRAY));


    // Utility
    public static final DeferredBlock<Block> AMBROSIUM_TORCH = register("ambrosium_torch", AmbrosiumTorchBlock::new, () -> Block.Properties.ofFullCopy(Blocks.TORCH));
    public static final DeferredBlock<Block> AMBROSIUM_WALL_TORCH = registerWithoutItem("ambrosium_wall_torch", AmbrosiumWallTorchBlock::new, () -> Block.Properties.ofFullCopy(Blocks.WALL_TORCH));
    public static final DeferredBlock<Block> SKYROOT_CRAFTING_TABLE = register("skyroot_crafting_table", SkyrootCraftingTableBlock::new, () -> Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE));
    public static final DeferredBlock<Block> HOLYSTONE_FURNACE = register("holystone_furnace", HolystoneFurnaceBlock::new, () -> Block.Properties.ofFullCopy(Blocks.FURNACE));
    public static final DeferredBlock<Block> ALTAR = register("altar", AltarBlock::new, () -> Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion());
    public static final DeferredBlock<Block> ARTISANS_BENCH = register("artisans_bench", ArtisansBenchBlock::new, () -> Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion());
    public static final DeferredBlock<Block> ARKENIUM_FORGE = register("arkenium_forge", ArkeniumForgeBlock::new, () -> Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion());
    public static final DeferredBlock<Block> SKYROOT_CHEST = register("skyroot_chest", (properties) -> new SkyrootChestBlock(properties, AetherIIBlockEntityTypes.SKYROOT_CHEST::get), () -> Block.Properties.ofFullCopy(Blocks.CHEST));
    public static final DeferredBlock<LadderBlock> SKYROOT_LADDER = register("skyroot_ladder", LadderBlock::new, () -> Block.Properties.ofFullCopy(Blocks.LADDER).strength(0.4F).sound(SoundType.LADDER).noOcclusion());
    public static final DeferredBlock<BedBlock> SKYROOT_BED = register("skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.WHITE, properties), () -> Block.Properties.ofFullCopy(Blocks.WHITE_BED));

    public static final DeferredBlock<StandingSignBlock> SKYROOT_SIGN = register("skyroot_sign", (properties) -> new StandingSignBlock(AetherIIWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<WallSignBlock> SKYROOT_WALL_SIGN = registerWithoutItem("skyroot_wall_sign", (properties) -> new WallSignBlock(AetherIIWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<CeilingHangingSignBlock> SKYROOT_HANGING_SIGN = register("skyroot_hanging_sign", (properties) -> new CeilingHangingSignBlock(AetherIIWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());
    public static final DeferredBlock<WallHangingSignBlock> SKYROOT_WALL_HANGING_SIGN = registerWithoutItem("skyroot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(AetherIIWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());

    public static final DeferredBlock<StandingSignBlock> GREATROOT_SIGN = register("greatroot_sign", (properties) -> new StandingSignBlock(AetherIIWoodTypes.GREATROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<WallSignBlock> GREATROOT_WALL_SIGN = registerWithoutItem("greatroot_wall_sign", (properties) -> new WallSignBlock(AetherIIWoodTypes.GREATROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<CeilingHangingSignBlock> GREATROOT_HANGING_SIGN = register("greatroot_hanging_sign", (properties) -> new CeilingHangingSignBlock(AetherIIWoodTypes.GREATROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());
    public static final DeferredBlock<WallHangingSignBlock> GREATROOT_WALL_HANGING_SIGN = registerWithoutItem("greatroot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(AetherIIWoodTypes.GREATROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());

    public static final DeferredBlock<StandingSignBlock> WISPROOT_SIGN = register("wisproot_sign", (properties) -> new StandingSignBlock(AetherIIWoodTypes.WISPROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<WallSignBlock> WISPROOT_WALL_SIGN = registerWithoutItem("wisproot_wall_sign", (properties) -> new WallSignBlock(AetherIIWoodTypes.WISPROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<CeilingHangingSignBlock> WISPROOT_HANGING_SIGN = register("wisproot_hanging_sign", (properties) -> new CeilingHangingSignBlock(AetherIIWoodTypes.WISPROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());
    public static final DeferredBlock<WallHangingSignBlock> WISPROOT_WALL_HANGING_SIGN = registerWithoutItem("wisproot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(AetherIIWoodTypes.WISPROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava());

    // Moa Egg
    public static final DeferredBlock<Block> MOA_EGG = registerWithoutItem("moa_egg", MoaEggBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.5F).sound(SoundType.METAL).noOcclusion());

    // Bookshelves
    public static final DeferredBlock<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", BookshelfBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredBlock<Block> HOLYSTONE_BOOKSHELF = register("holystone_bookshelf", BookshelfBlock::new, () -> Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get()));

    // Furniture
    public static final DeferredBlock<OutpostCampfireBlock> OUTPOST_CAMPFIRE = register("outpost_campfire", OutpostCampfireBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(15.0F, 1200.0F).noOcclusion().pushReaction(PushReaction.BLOCK));

    private static <T extends Block> DeferredBlock<Block> registerWithoutItem(String name, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, Block::new, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, createKey(name), builder, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return BLOCKS.register(name, () -> builder.apply(properties.get().setId(key)));
    }

    private static <T extends Block> DeferredBlock<Block> register(String name, Supplier<Block.Properties> properties) {
        return register(name, Block::new, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return register(name, createKey(name), builder, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return baseRegister(name, key, builder, properties, (deferredBlock) -> registerBlockItem(deferredBlock, name));
    }

    private static <T extends Block> DeferredBlock<T> baseRegister(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> registered = BLOCKS.register(name, () -> builder.apply(properties.get().setId(key)));
        AetherIIItems.ITEMS.register(name, item.apply(registered));
        return registered;
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final DeferredBlock<T> deferredBlock, String name) {
        return () -> {
            DeferredBlock<T> block = Objects.requireNonNull(deferredBlock);
            Item.Properties properties = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name))).useBlockDescriptionPrefix();
            if (block == HOLYSTONE_ROCK) {
                return new RockItem(HOLYSTONE_ROCK.get(), properties);
            } else if (block == AMBROSIUM_TORCH) {
                return new StandingAndWallBlockItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), Direction.DOWN, properties);
            } else if (block == SKYROOT_SIGN) {
                return new SignItem(SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get(), properties.stacksTo(16));
            } else if (block == SKYROOT_HANGING_SIGN) {
                return new HangingSignItem(SKYROOT_HANGING_SIGN.get(), SKYROOT_WALL_HANGING_SIGN.get(), properties.stacksTo(16));
            } else if (block == GREATROOT_SIGN) {
                return new SignItem(GREATROOT_SIGN.get(), GREATROOT_WALL_SIGN.get(), properties.stacksTo(16));
            } else if (block == GREATROOT_HANGING_SIGN) {
                return new HangingSignItem(GREATROOT_HANGING_SIGN.get(), GREATROOT_WALL_HANGING_SIGN.get(), properties.stacksTo(16));
            } else if (block == WISPROOT_SIGN) {
                return new SignItem(WISPROOT_SIGN.get(), WISPROOT_WALL_SIGN.get(), properties.stacksTo(16));
            } else if (block == WISPROOT_HANGING_SIGN) {
                return new HangingSignItem(WISPROOT_HANGING_SIGN.get(), WISPROOT_WALL_HANGING_SIGN.get(), properties.stacksTo(16));
            } else {
                return new BlockItem(block.get(), properties);
            }
        };
    }

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SKYROOT_SAPLING.get()), AetherIIBlocks.POTTED_SKYROOT_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SKYPLANE_SAPLING.get()), AetherIIBlocks.POTTED_SKYPLANE_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SKYBIRCH_SAPLING.get()), AetherIIBlocks.POTTED_SKYBIRCH_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SKYPINE_SAPLING.get()), AetherIIBlocks.POTTED_SKYPINE_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.WISPROOT_SAPLING.get()), AetherIIBlocks.POTTED_WISPROOT_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.WISPTOP_SAPLING.get()), AetherIIBlocks.POTTED_WISPTOP_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.GREATROOT_SAPLING.get()), AetherIIBlocks.POTTED_GREATROOT_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.GREATOAK_SAPLING.get()), AetherIIBlocks.POTTED_GREATOAK_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.GREATBOA_SAPLING.get()), AetherIIBlocks.POTTED_GREATBOA_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.AMBEROOT_SAPLING.get()), AetherIIBlocks.POTTED_AMBEROOT_SAPLING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.MAGNETIC_SHROOM.get()), AetherIIBlocks.POTTED_MAGNETIC_SHROOM);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.HIGHLAND_FERN.get()), AetherIIBlocks.POTTED_HIGHLAND_FERN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SHIELD_FERN.get()), AetherIIBlocks.POTTED_SHIELD_FERN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.HESPEROSE.get()), AetherIIBlocks.POTTED_HESPEROSE);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.TARABLOOM.get()), AetherIIBlocks.POTTED_TARABLOOM);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.POASPROUT.get()), AetherIIBlocks.POTTED_POASPROUT);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.PLURACIAN.get()), AetherIIBlocks.POTTED_PLURACIAN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SATIVAL_SHOOT.get()), AetherIIBlocks.POTTED_SATIVAL_SHOOT);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.LILICHIME.get()), AetherIIBlocks.POTTED_LILICHIME);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLADE_POA.get()), AetherIIBlocks.POTTED_BLADE_POA);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.AECHOR_CUTTING.get()), AetherIIBlocks.POTTED_AECHOR_CUTTING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.HIGHLANDS_BUSH.get()), AetherIIBlocks.POTTED_HIGHLANDS_BUSH);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLUEBERRY_BUSH.get()), AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get()), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.ORANGE_TREE.get()), AetherIIBlocks.POTTED_ORANGE_TREE);
    }

    public static void registerFlammability() {
        FireBlockAccessor fireBlockAccessor = (FireBlockAccessor) Blocks.FIRE;
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYPLANE_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYPINE_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPTOP_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATOAK_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATBOA_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_LEAF_PILE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYPLANE_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYBIRCH_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYPINE_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPTOP_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATOAK_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATBOA_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AETHER_SHORT_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AETHER_LONG_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.HIGHLAND_FERN.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.HIGHLANDS_BUSH.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.BLUEBERRY_BUSH.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.ORANGE_TREE.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_FLOORBOARDS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_HIGHLIGHT.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_BASE_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_TOP_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_FLOORBOARDS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_HIGHLIGHT.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_BASE_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_TOP_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_FLOORBOARDS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_HIGHLIGHT.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_TOP_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_BASE_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_TOP_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.CLOUDWOOL.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.CLOUDWOOL_CARPET.get(), 60, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
    }

    public static void registerWoodTypes() {
        WoodType.register(AetherIIWoodTypes.SKYROOT);
        WoodType.register(AetherIIWoodTypes.GREATROOT);
        WoodType.register(AetherIIWoodTypes.WISPROOT);
    }

    /**
     * Blocks able to be flattened with {@link ItemAbilities#AXE_STRIP}, and the equivalent result block.
     */
    public static final Map<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>> STRIPPABLES = new ImmutableMap.Builder<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>>()
            .put(() -> AetherIIBlocks.SKYROOT_LOG, () -> AetherIIBlocks.STRIPPED_SKYROOT_LOG)
            .put(() -> AetherIIBlocks.SKYROOT_WOOD, () -> AetherIIBlocks.STRIPPED_SKYROOT_WOOD)
            .put(() -> AetherIIBlocks.GREATROOT_LOG, () -> AetherIIBlocks.STRIPPED_GREATROOT_LOG)
            .put(() -> AetherIIBlocks.GREATROOT_WOOD, () -> AetherIIBlocks.STRIPPED_GREATROOT_WOOD)
            .put(() -> AetherIIBlocks.WISPROOT_LOG, () -> AetherIIBlocks.STRIPPED_WISPROOT_LOG)
            .put(() -> AetherIIBlocks.WISPROOT_WOOD, () -> AetherIIBlocks.STRIPPED_WISPROOT_WOOD)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_LOG, () -> AetherIIBlocks.WISPROOT_LOG)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_WOOD, () -> AetherIIBlocks.WISPROOT_WOOD)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_LOG_END, () -> AetherIIBlocks.WISPROOT_LOG)
            .put(() -> AetherIIBlocks.AMBEROOT_LOG, () -> AetherIIBlocks.STRIPPED_SKYROOT_LOG)
            .put(() -> AetherIIBlocks.AMBEROOT_WOOD, () -> AetherIIBlocks.STRIPPED_SKYROOT_WOOD)
            .build();

    /**
     * Blocks able to be flattened with {@link ItemAbilities#SHOVEL_FLATTEN}, and the equivalent result block.
     */
    public static final Map<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>> FLATTENABLES = new ImmutableMap.Builder<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>>()
            .put(() -> AetherIIBlocks.AETHER_GRASS_BLOCK, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .put(() -> AetherIIBlocks.AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .put(() -> AetherIIBlocks.COARSE_AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .build();

    /**
     * Blocks able to be tilled with {@link ItemAbilities#HOE_TILL}, and the equivalent result block.
     */
    public static final Map<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>> TILLABLES = new ImmutableMap.Builder<Supplier<DeferredBlock<? extends Block>>, Supplier<DeferredBlock<? extends Block>>>()
            .put(() -> AetherIIBlocks.AETHER_DIRT, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.AETHER_GRASS_BLOCK, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.AETHER_DIRT_PATH, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.COARSE_AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT)
            .build();

    public static BlockState registerBlockModifications(LevelAccessor levelAccessor, ItemAbility toolAction, BlockPos blockPos, BlockState oldState, BlockState newState) {
        Map<Block, Block> strippables = STRIPPABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));
        Map<Block, Block> flattenables = FLATTENABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));
        Map<Block, Block> tillables = TILLABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));

        Block oldBlock = oldState.getBlock();
        if (toolAction == ItemAbilities.AXE_STRIP) {
            if (strippables.containsKey(oldBlock)) {
                newState = strippables.get(oldBlock).withPropertiesOf(oldState);
            }
        } else if (toolAction == ItemAbilities.SHOVEL_FLATTEN) {
            if (flattenables.containsKey(oldBlock)) {
                newState = flattenables.get(oldBlock).withPropertiesOf(oldState);
            }
        } else if (toolAction == ItemAbilities.HOE_TILL) {
            if (levelAccessor.getBlockState(blockPos.above()).isAir()) {
                if (tillables.containsKey(oldBlock)) {
                    newState = tillables.get(oldBlock).withPropertiesOf(oldState);
                }
            }
        }
        return newState;
    }

}