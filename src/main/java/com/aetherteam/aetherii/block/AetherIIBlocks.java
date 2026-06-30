package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.construction.*;
import com.aetherteam.aetherii.block.dungeon.*;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.furniture.VaseBlock;
import com.aetherteam.aetherii.block.miscellaneous.*;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.portal.AetherPortalBlock;
import com.aetherteam.aetherii.block.utility.*;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundTypes;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.materials.RockItem;
import com.aetherteam.aetherii.item.miscellaneous.AetherIIBedItem;
import com.aetherteam.aetherii.item.miscellaneous.AetherIIRenderedBlockItem;
import com.aetherteam.aetherii.item.miscellaneous.CopyBlockItem;
import com.aetherteam.aetherii.world.tree.AetherIITreeGrowers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AetherIIBlocks extends AetherIIBlockBuilders {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AetherII.MODID);

    // Portal
    public static final RegistryObject<AetherPortalBlock> AETHER_PORTAL = registerWithoutItem("aether_portal", AetherPortalBlock::new, () -> Block.Properties.of().noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(AetherIIBlocks::lightLevel11).pushReaction(PushReaction.BLOCK).forceSolidOn().noLootTable());

    // Surface
    public static final RegistryObject<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", AetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    public static final RegistryObject<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", EnchantedAetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.GOLD).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    public static final RegistryObject<Block> AETHER_DIRT_PATH = register("aether_dirt_path", AetherDirtPathBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));
    public static final RegistryObject<Block> AETHER_DIRT = register("aether_dirt", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL));
    public static final RegistryObject<Block> COARSE_AETHER_DIRT = register("coarse_aether_dirt", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL));
    public static final RegistryObject<Block> MYCELIAL_AETHER_DIRT = register("mycelial_aether_dirt", () -> Block.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(0.5F).sound(SoundType.GRAVEL));
    public static final RegistryObject<Block> AETHER_FARMLAND = register("aether_farmland", AetherFarmlandBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));
    public static final RegistryObject<Block> SHIMMERING_SILT = register("shimmering_silt", (properties) -> new SandBlock(8360341, properties), () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.5F).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always));

    // Underground
    public static final RegistryObject<Block> HOLYSTONE = register("holystone", () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> UNSTABLE_HOLYSTONE = register("unstable_holystone", UnstableBlock::new, () -> Block.Properties.copy(HOLYSTONE.get()));
    public static final RegistryObject<Block> UNDERSHALE = register("undershale", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> UNSTABLE_UNDERSHALE = register("unstable_undershale", UnstableBlock::new, () -> Block.Properties.copy(UNDERSHALE.get()));
    public static final RegistryObject<Block> AGIOSITE = register("agiosite", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> ICHORITE = register("ichorite", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final RegistryObject<HalfTransparentBlock> CRUDE_SCATTERGLASS = register("crude_scatterglass", CrudeScatterglassBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never));
    public static final RegistryObject<Block> SKY_ROOTS = register("sky_roots", AetherHangingRootsBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).replaceable().noCollission().instabreak().sound(SoundType.HANGING_ROOTS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<LiquidBlock> ALKAHEST = registerWithoutItem("alkahest", (properties) -> new AlkahestLiquidBlock(AetherIIFluids.ALKAHEST.get(), properties), () -> Block.Properties.of().mapColor(MapColor.FIRE).replaceable().noCollission().randomTicks().strength(100.0F).lightLevel(AetherIIBlocks::lightLevel8).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));
    public static final RegistryObject<Block> HESTVEIL = registerWithoutItem("hestveil", HestveilBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(-1.0F, 0.0F).replaceable().noCollission().noOcclusion().noParticlesOnBreak().isValidSpawn(AetherIIBlockBuilders::never).isRedstoneConductor(AetherIIBlockBuilders::never).isSuffocating(AetherIIBlockBuilders::never).isViewBlocking(AetherIIBlockBuilders::never).noLootTable());
    public static final RegistryObject<AbstractPointedStoneBlock> POINTED_HOLYSTONE = register("pointed_holystone", PointedHolystoneBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).isRedstoneConductor(AetherIIBlocks::never));
    public static final RegistryObject<AbstractPointedStoneBlock> POINTED_ICHORITE = register("pointed_ichorite", PointedIchoriteBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(4.0F, 3.0F).dynamicShape().offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).isRedstoneConductor(AetherIIBlocks::never));

    // Highfields
    public static final RegistryObject<Block> QUICKSOIL = register("quicksoil", QuicksoilBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(1.1F).sound(SoundType.SAND));
    public static final RegistryObject<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> Block.Properties.copy(HOLYSTONE.get()));
    public static final RegistryObject<Block> BRYALINN_MOSS_BLOCK = register("bryalinn_moss_block", (properties) -> new AetherMossBlock(HolyIslesConfiguredFeatures.BRYALINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> BRYALINN_MOSS_CARPET = register("bryalinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> BRYALINN_MOSS_VINES = register("bryalinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> BRYALINN_MOSS_FLOWERS = register("bryalinn_moss_flowers", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> TANGLED_BRANCHES = register("tangled_branches", TangledBranchBlock::new, () -> Block.Properties.of().noOcclusion().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.WOOD));

    // Magnetic
    public static final RegistryObject<Block> FERROSITE_SAND = register("ferrosite_sand", () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final RegistryObject<Block> FERROSITE_MUD = register("ferrosite_mud", MudBlock::new, () -> Block.Properties.copy(Blocks.MUD).mapColor(MapColor.COLOR_PURPLE));
    public static final RegistryObject<Block> FERROSITE = register("ferrosite", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(AetherIISoundTypes.FERROSITE).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> RUSTED_FERROSITE = register("rusted_ferrosite", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(AetherIISoundTypes.FERROSITE).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> MAGNETIC_SHROOM = register("magnetic_shroom", (properties) -> new MushroomBlock(properties, HolyIslesConfiguredFeatures.HUGE_MAGNETIC_SHROOM_GROWN), () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel(light -> 5).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> MAGNETIC_SHROOM_BLOCK = register("magnetic_shroom_block", HugeMushroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD).ignitedByLava());
    public static final RegistryObject<Block> SPOTTED_MAGNETIC_SHROOM_BLOCK = register("spotted_magnetic_shroom_block", HugeMushroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD).ignitedByLava().lightLevel((state) -> 6));
    public static final RegistryObject<Block> MAGNETIC_SHROOM_STEM = register("magnetic_shroom_stem", HugeMushroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD).ignitedByLava());

    // Arctic
    public static final RegistryObject<Block> ARCTIC_SNOW_BLOCK = register("arctic_snow_block", () -> Block.Properties.of().mapColor(MapColor.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW));
    @SuppressWarnings("deprecation")
    public static final RegistryObject<Block> ARCTIC_SNOW = register("arctic_snow", SnowLayerBlock::new, () -> Block.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).sound(SoundType.SNOW).requiresCorrectToolForDrops().isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final RegistryObject<Block> ARCTIC_ICE = register("arctic_ice", IceBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType.builtInRegistryHolder().is(AetherIITags.EntityTypes.SPAWNING_ICE)).isRedstoneConductor(AetherIIBlocks::never));
    public static final RegistryObject<Block> FRAGILE_ARCTIC_ICE = register("fragile_arctic_ice", FragileIceBlock::new, () -> Block.Properties.copy(ARCTIC_ICE.get()));
    public static final RegistryObject<Block> ARCTIC_PACKED_ICE = register("arctic_packed_ice", () -> Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).friction(0.98F).strength(0.5F).sound(SoundType.GLASS));
    public static final RegistryObject<Block> ICESTONE = register("icestone", IcestoneBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> LARGE_ARCTIC_ICE_CRYSTAL = register("large_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.of().mapColor(MapColor.ICE).forceSolidOn().noOcclusion().sound(SoundType.GLASS).strength(0.5F).pushReaction(PushReaction.DESTROY).randomTicks());
    @SuppressWarnings("deprecation")
    public static final RegistryObject<Block> MEDIUM_ARCTIC_ICE_CRYSTAL = register("medium_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.copy(LARGE_ARCTIC_ICE_CRYSTAL.get()));
    @SuppressWarnings("deprecation")
    public static final RegistryObject<Block> SMALL_ARCTIC_ICE_CRYSTAL = register("small_arctic_ice_crystal", (properties) -> new IceCrystalBlock(8.0F, 2.0F, properties), () -> Block.Properties.copy(LARGE_ARCTIC_ICE_CRYSTAL.get()));
    public static final RegistryObject<Block> SHAYELINN_MOSS_BLOCK = register("shayelinn_moss_block", (properties) -> new AetherMossBlock(HolyIslesConfiguredFeatures.SHAYELINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> SHAYELINN_MOSS_CARPET = register("shayelinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> SHAYELINN_MOSS_VINES = register("shayelinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));

    // Irradiated
    public static final RegistryObject<Block> IRRADIATED_HOLYSTONE = register("irradiated_holystone", IrradiatedBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE.get()));
    public static final RegistryObject<Block> IRRADIATED_DUST_BLOCK = register("irradiated_dust_block", IrradiatedBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F, 30.0F).lightLevel((state) -> 5).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> AMBRELINN_MOSS_BLOCK = register("ambrelinn_moss_block", (properties) -> new AetherMossBlock(HolyIslesConfiguredFeatures.AMBRELINN_MOSS_FLOOR, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> AMBRELINN_MOSS_CARPET = register("ambrelinn_moss_carpet", CarpetBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> AMBRELINN_MOSS_VINES = register("ambrelinn_moss_vines", BottomedVineBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> TARAHESP_FLOWERS = register("tarahesp_flowers", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));

    // Ores
    public static final RegistryObject<Block> HOLYSTONE_QUARTZ_ORE = register("holystone_quartz_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(2, 5)), () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> AMBROSIUM_ORE = register("ambrosium_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(0, 2)), () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops().lightLevel(AetherIIBlocks::lightLevel8));
    public static final RegistryObject<Block> ZANITE_ORE = register("zanite_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(3, 5)), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> ARKENIUM_ORE = register("arkenium_ore", (properties) -> new DropExperienceBlock(properties, ConstantInt.of(0)), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> GRAVITITE_ORE = register("gravitite_ore", (properties) -> new DropExperienceBlock(properties, ConstantInt.of(0)), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> GLINT_ORE = register("glint_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(3, 5)), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> UNDERSHALE_AMBROSIUM_ORE = register("undershale_ambrosium_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(0, 2)), () -> Block.Properties.copy(AMBROSIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE).lightLevel(AetherIIBlocks::lightLevel8));
    public static final RegistryObject<Block> UNDERSHALE_ZANITE_ORE = register("undershale_zanite_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(3, 5)), () -> Block.Properties.copy(ZANITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE));
    public static final RegistryObject<Block> UNDERSHALE_ARKENIUM_ORE = register("undershale_arkenium_ore", (properties) -> new DropExperienceBlock(properties, ConstantInt.of(0)), () -> Block.Properties.copy(ARKENIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE));
    public static final RegistryObject<Block> UNDERSHALE_GRAVITITE_ORE = register("undershale_gravitite_ore", (properties) -> new DropExperienceBlock(properties, ConstantInt.of(0)), () -> Block.Properties.copy(GRAVITITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE));
    public static final RegistryObject<Block> UNDERSHALE_GLINT_ORE = register("undershale_glint_ore", (properties) -> new DropExperienceBlock(properties, UniformInt.of(3, 5)), () -> Block.Properties.copy(GLINT_ORE.get()).mapColor(MapColor.WOOL).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE));
    public static final RegistryObject<Block> CORROBONITE_ORE = register("corrobonite_ore", (properties) -> new CorroboniteOreBlock(ConstantInt.of(0), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(4.5F, 3.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> CORROBONITE_CLUSTER = register("corrobonite_cluster", CorroboniteClusterBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).strength(3.0F, 3.0F).replaceable().noOcclusion().noCollission().instabreak());

    // Aerclouds
    public static final RegistryObject<Block> COLD_AERCLOUD = register("cold_aercloud", AercloudBlock::new, coldAercloudProperties(MapColor.SNOW));
    public static final RegistryObject<Block> GOLDEN_AERCLOUD = register("golden_aercloud", AercloudBlock::new, specialAercloudProperties(MapColor.COLOR_YELLOW));
    public static final RegistryObject<Block> BLUE_AERCLOUD = register("blue_aercloud", BlueAercloudBlock::new, specialAercloudProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final RegistryObject<Block> GREEN_AERCLOUD = register("green_aercloud", GreenAercloudBlock::new, specialAercloudProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryObject<Block> PURPLE_AERCLOUD = register("purple_aercloud", PurpleAercloudBlock::new, specialAercloudProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> STORM_AERCLOUD = register("storm_aercloud", AercloudBlock::new, specialAercloudProperties(MapColor.DEEPSLATE));

    // Nest Blocks
    public static final RegistryObject<Block> WOVEN_SKYROOT_STICKS = register("woven_skyroot_sticks", WovenSticksBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.75F).sound(SoundType.GRASS));
    public static final RegistryObject<Block> ANIMAL_STASH = register("animal_stash", AnimalStashBlock::new, () -> Block.Properties.copy(WOVEN_SKYROOT_STICKS.get()));
    public static final RegistryObject<Block> MOA_EGG = registerWithoutItem("moa_egg", MoaEggBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.5F).sound(SoundType.METAL).noOcclusion());

    // Logs
    public static final RegistryObject<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<RotatedPillarBlock> GREATROOT_LOG = register("greatroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final RegistryObject<RotatedPillarBlock> GREATROOT_WOOD = register("greatroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GREATROOT_LOG = register("stripped_greatroot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GREATROOT_WOOD = register("stripped_greatroot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final RegistryObject<RotatedPillarBlock> WISPROOT_LOG = register("wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<RotatedPillarBlock> MOSSY_WISPROOT_LOG = register("mossy_wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<FacingPillarBlock> MOSSY_WISPROOT_LOG_BASE = register("mossy_wisproot_log_base", FacingPillarBlock::new, (() -> Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<RotatedPillarBlock> WISPROOT_WOOD = register("wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<RotatedPillarBlock> MOSSY_WISPROOT_WOOD = register("mossy_wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WISPROOT_LOG = register("stripped_wisproot_log", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WISPROOT_WOOD = register("stripped_wisproot_wood", RotatedPillarBlock::new, logProperties(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final RegistryObject<RotatedPillarBlock> AMBEROOT_LOG = register("amberoot_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> AMBEROOT_DEPOSIT = register("amberoot_deposit", RotatedPillarBlock::new, logProperties(MapColor.COLOR_ORANGE, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> AMBEROOT_WOOD = register("amberoot_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BROWN));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_AMBEROOT_LOG = register("stripped_amberoot_log", RotatedPillarBlock::new, logProperties(MapColor.WOOD, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_AMBEROOT_WOOD = register("stripped_amberoot_wood", RotatedPillarBlock::new, logProperties(MapColor.WOOD, MapColor.WOOD));

    // Trunks
    public static final RegistryObject<TrunkBlock> SKYROOT_TRUNK = register("skyroot_trunk", TrunkBlock::new, trunkProperties(MapColor.COLOR_BROWN));
    public static final RegistryObject<TrunkBlock> STRIPPED_SKYROOT_TRUNK = register("stripped_skyroot_trunk", TrunkBlock::new, trunkProperties(MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<TrunkBlock> GREATROOT_TRUNK = register("greatroot_trunk", TrunkBlock::new, trunkProperties(MapColor.TERRACOTTA_BROWN));
    public static final RegistryObject<TrunkBlock> STRIPPED_GREATROOT_TRUNK = register("stripped_greatroot_trunk", TrunkBlock::new, trunkProperties(MapColor.COLOR_BROWN));
    public static final RegistryObject<TrunkBlock> WISPROOT_TRUNK = register("wisproot_trunk", TrunkBlock::new, trunkProperties(MapColor.QUARTZ));
    public static final RegistryObject<TrunkBlock> MOSSY_WISPROOT_TRUNK = register("mossy_wisproot_trunk", TrunkBlock::new, trunkProperties(MapColor.QUARTZ));
    public static final RegistryObject<TrunkBlock> STRIPPED_WISPROOT_TRUNK = register("stripped_wisproot_trunk", TrunkBlock::new, trunkProperties(MapColor.QUARTZ));
    public static final RegistryObject<TrunkBlock> AMBEROOT_TRUNK = register("amberoot_trunk", TrunkBlock::new, trunkProperties(MapColor.COLOR_BROWN));
    public static final RegistryObject<TrunkBlock> STRIPPED_AMBEROOT_TRUNK = register("stripped_amberoot_trunk", TrunkBlock::new, trunkProperties(MapColor.COLOR_BROWN));

    // Leaf Pile
    public static final RegistryObject<Block> SKYROOT_LEAF_PILE = register("skyroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.GRASS));
    public static final RegistryObject<Block> SKYPLANE_LEAF_PILE = register("skyplane_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_BLUE));
    public static final RegistryObject<Block> SKYBIRCH_LEAF_PILE = register("skybirch_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final RegistryObject<Block> SKYPINE_LEAF_PILE = register("skypine_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> WISPROOT_LEAF_PILE = register("wisproot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.DIAMOND));
    public static final RegistryObject<Block> WISPTOP_LEAF_PILE = register("wisptop_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> GREATROOT_LEAF_PILE = register("greatroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final RegistryObject<Block> GREATOAK_LEAF_PILE = register("greatoak_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> GREATBOA_LEAF_PILE = register("greatboa_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.COLOR_BLUE));
    public static final RegistryObject<Block> AMBEROOT_LEAF_PILE = register("amberoot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.GOLD));
    public static final RegistryObject<Block> IRRADIATED_SKYROOT_LEAF_PILE = register("irradiated_skyroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYPLANE_LEAF_PILE = register("irradiated_skyplane_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYBIRCH_LEAF_PILE = register("irradiated_skybirch_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYPINE_LEAF_PILE = register("irradiated_skypine_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_WISPROOT_LEAF_PILE = register("irradiated_wisproot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_WISPTOP_LEAF_PILE = register("irradiated_wisptop_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATROOT_LEAF_PILE = register("irradiated_greatroot_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATOAK_LEAF_PILE = register("irradiated_greatoak_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATBOA_LEAF_PILE = register("irradiated_greatboa_leaf_pile", AetherLeafPileBlock::new, leafPileProperties(MapColor.TERRACOTTA_YELLOW));

    // Leaves
    public static final RegistryObject<Block> SKYROOT_LEAVES = register("skyroot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYROOT_LEAVES, AetherIIBlocks.SKYROOT_LEAF_PILE), leavesProperties(MapColor.GRASS));
    public static final RegistryObject<Block> SKYPLANE_LEAVES = register("skyplane_leaves", (properties) -> new BlocksLightLeavesBlock(properties, AetherIIParticleTypes.SKYPLANE_LEAVES, AetherIIBlocks.SKYPLANE_LEAF_PILE), leavesProperties(MapColor.COLOR_BLUE));
    public static final RegistryObject<Block> SKYBIRCH_LEAVES = register("skybirch_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYBIRCH_LEAVES, AetherIIBlocks.SKYBIRCH_LEAF_PILE), leavesProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final RegistryObject<Block> SKYPINE_LEAVES = register("skypine_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.SKYPINE_LEAVES, AetherIIBlocks.SKYPINE_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> WISPROOT_LEAVES = register("wisproot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.WISPROOT_LEAVES, AetherIIBlocks.WISPROOT_LEAF_PILE), leavesProperties(MapColor.DIAMOND));
    public static final RegistryObject<Block> WISPTOP_LEAVES = register("wisptop_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.WISPTOP_LEAVES, AetherIIBlocks.WISPTOP_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> GREATROOT_LEAVES = register("greatroot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.GREATROOT_LEAVES, AetherIIBlocks.GREATROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final RegistryObject<Block> GREATOAK_LEAVES = register("greatoak_leaves", (properties) -> new AllowsLightLeavesBlock(properties, AetherIIParticleTypes.GREATOAK_LEAVES, AetherIIBlocks.GREATOAK_LEAF_PILE), leavesProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> GREATBOA_LEAVES = register("greatboa_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.GREATBOA_LEAVES, AetherIIBlocks.GREATBOA_LEAF_PILE), leavesProperties(MapColor.COLOR_BLUE));
    public static final RegistryObject<Block> AMBEROOT_LEAVES = register("amberoot_leaves", (properties) -> new AetherLeavesBlock(properties, AetherIIParticleTypes.AMBEROOT_LEAVES, AetherIIBlocks.AMBEROOT_LEAF_PILE), leavesProperties(MapColor.GOLD));
    public static final RegistryObject<Block> IRRADIATED_SKYROOT_LEAVES = register("irradiated_skyroot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYPLANE_LEAVES = register("irradiated_skyplane_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYBIRCH_LEAVES = register("irradiated_skybirch_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_SKYPINE_LEAVES = register("irradiated_skypine_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_WISPROOT_LEAVES = register("irradiated_wisproot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_WISPTOP_LEAVES = register("irradiated_wisptop_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATROOT_LEAVES = register("irradiated_greatroot_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATOAK_LEAVES = register("irradiated_greatoak_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));
    public static final RegistryObject<Block> IRRADIATED_GREATBOA_LEAVES = register("irradiated_greatboa_leaves", (properties) -> new IrradiatedLeavesBlock(properties, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE), leavesProperties(MapColor.TERRACOTTA_YELLOW));

    // Saplings
    public static final RegistryObject<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYROOT, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> SKYPLANE_SAPLING = register("skyplane_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYPLANE, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> SKYBIRCH_SAPLING = register("skybirch_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYBIRCH, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> SKYPINE_SAPLING = register("skypine_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.SKYPINE, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> WISPROOT_SAPLING = register("wisproot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.WISPROOT, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> WISPTOP_SAPLING = register("wisptop_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.WISPTOP, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> GREATROOT_SAPLING = register("greatroot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATROOT, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> GREATOAK_SAPLING = register("greatoak_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATOAK, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> GREATBOA_SAPLING = register("greatboa_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.GREATBOA, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));
    public static final RegistryObject<SaplingBlock> AMBEROOT_SAPLING = register("amberoot_sapling", (properties) -> new SaplingBlock(AetherIITreeGrowers.AMBEROOT, properties), () -> Block.Properties.copy(Blocks.OAK_SAPLING));

    // Potted Saplings
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYROOT_SAPLING = registerWithoutItem("potted_skyroot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYPLANE_SAPLING = registerWithoutItem("potted_skyplane_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPLANE_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYBIRCH_SAPLING = registerWithoutItem("potted_skybirch_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYBIRCH_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYPINE_SAPLING = registerWithoutItem("potted_skypine_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPINE_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_WISPROOT_SAPLING = registerWithoutItem("potted_wisproot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPROOT_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_WISPTOP_SAPLING = registerWithoutItem("potted_wisptop_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPTOP_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_GREATROOT_SAPLING = registerWithoutItem("potted_greatroot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATROOT_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_GREATOAK_SAPLING = registerWithoutItem("potted_greatoak_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATOAK_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_GREATBOA_SAPLING = registerWithoutItem("potted_greatboa_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATBOA_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_AMBEROOT_SAPLING = registerWithoutItem("potted_amberoot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AMBEROOT_SAPLING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));

    // Grasses
    public static final RegistryObject<Block> SHORT_AETHER_GRASS = register("short_aether_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final RegistryObject<Block> MEDIUM_AETHER_GRASS = register("medium_aether_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final RegistryObject<Block> TALL_AETHER_GRASS = register("tall_aether_grass", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final RegistryObject<Block> AETHER_FERN = register("aether_fern", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));
    public static final RegistryObject<Block> SHIELD_FERN = register("shield_fern", AetherTallGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always));

    // Flowers
    public static final RegistryObject<Block> HESPEROSE = register("hesperose", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> TARABLOOM = register("tarabloom", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> POASPROUT = register("poasprout", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> LILICHIME = register("lilichime", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> PLURACIAN = register("pluracian", (properties) -> new FacingFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> SATIVAL_SHOOT = register("satival_shoot", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> HOLPUPEA = register("holpupea", MossFlowersBlock::new, () -> Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> BLADE_POA = register("blade_poa", (properties) -> new AetherFlowerBlock(properties), () -> Block.Properties.copy(Blocks.DANDELION));
    public static final RegistryObject<Block> AECHOR_CUTTING = register("aechor_cutting", (properties) -> new PlantMobCuttingBlock(AetherIIEntityTypes.AECHOR_PLANT::get, properties), () -> Block.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.65F).sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> CARRION_CUTTING = register("carrion_cutting", (properties) -> new PlantMobCuttingBlock(AetherIIEntityTypes.CARRION_SPROUT::get, properties), () -> Block.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.65F).sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops());

    // Potted Flowers
    public static final RegistryObject<FlowerPotBlock> POTTED_MAGNETIC_SHROOM = registerWithoutItem("potted_magnetic_shroom", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MAGNETIC_SHROOM, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_AETHER_FERN = registerWithoutItem("potted_aether_fern", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AETHER_FERN, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_SHIELD_FERN = registerWithoutItem("potted_shield_fern", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHIELD_FERN, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_HESPEROSE = registerWithoutItem("potted_hesperose", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HESPEROSE, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_TARABLOOM = registerWithoutItem("potted_tarabloom", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TARABLOOM, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_POASPROUT = registerWithoutItem("potted_poasprout", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, POASPROUT, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_PLURACIAN = registerWithoutItem("potted_pluracian", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PLURACIAN, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_SATIVAL_SHOOT = registerWithoutItem("potted_satival_shoot", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SATIVAL_SHOOT, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_LILICHIME = registerWithoutItem("potted_lilichime", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LILICHIME, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLADE_POA = registerWithoutItem("potted_blade_poa", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLADE_POA, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_AECHOR_CUTTING = registerWithoutItem("potted_aechor_cutting", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AECHOR_CUTTING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_CARRION_CUTTING = registerWithoutItem("potted_carrion_cutting", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CARRION_CUTTING, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));

    // Bushes
    public static final RegistryObject<Block> AETHER_BUSH = register("aether_bush", AetherFullBushBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.65F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> BLUEBERRY_BUSH = register("blueberry_bush", BlueberryBushBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.65F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> BLUEBERRY_BUSH_STEM = register("blueberry_bush_stem", BlueberryBushStemBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.65F).sound(SoundType.GRASS).noCollission().requiresCorrectToolForDrops());

    // Potted Bushes
    public static final RegistryObject<FlowerPotBlock> POTTED_AETHER_BUSH = registerWithoutItem("potted_aether_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AETHER_BUSH, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLUEBERRY_BUSH = registerWithoutItem("potted_blueberry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLUEBERRY_BUSH_STEM = registerWithoutItem("potted_blueberry_bush_stem", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH_STEM, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));

    // Orange Tree
    public static final RegistryObject<Block> ORANGE_TREE = register("orange_tree", OrangeTreeBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).noCollission().strength(0.65F).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops());

    // Potted Orange Tree
    public static final RegistryObject<FlowerPotBlock> POTTED_ORANGE_TREE = registerWithoutItem("potted_orange_tree", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_TREE, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));

    // Valkyrie Sprout
    public static final RegistryObject<Block> VALKYRIE_SPROUT = register("valkyrie_sprout", ValkyrieSproutBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).noCollission().strength(0.65F).offsetType(Block.OffsetType.XZ).requiresCorrectToolForDrops());

    // Brettl
    public static final RegistryObject<Block> BRETTL_PLANT = registerWithoutItem("brettl_plant", BrettlPlantBlock::new, () -> Block.Properties.of().noCollission().strength(0.65F).randomTicks().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> BRETTL_PLANT_TIP = registerWithoutItem("brettl_plant_tip", BrettlPlantTipBlock::new, () -> Block.Properties.of().noCollission().strength(0.65F).randomTicks().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> BRETTL_FLOWER = register("brettl_flower", BrettlFlowerBlock::new, () -> Block.Properties.copy(Blocks.CACTUS).mapColor(MapColor.DIAMOND));

    // Lake
    public static final RegistryObject<Block> ARILUM_SHOOT = registerWithoutItem("arilum_shoot", ArilumShootBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> ARILUM = register("arilum", ArilumBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> ARILUM_PLANT = register("arilum_plant", ArilumPlantBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> BLOOMING_ARILUM = register("blooming_arilum", BloomingArilumBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).lightLevel((block) -> 5));
    public static final RegistryObject<Block> BLOOMING_ARILUM_PLANT = register("blooming_arilum_plant", BloomingArilumPlantBlock::new, () -> Block.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).lightLevel((block) -> 5));

    // Ground Decoration
    public static final RegistryObject<Block> SKYROOT_TWIG = register("skyroot_twig", TwigBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> HOLYSTONE_ROCK = register("holystone_rock", RockBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).noOcclusion().noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY), RockItem::new);

    // Skyroot Planks
    public static final RegistryObject<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<StairBlock> SKYROOT_STAIRS = register("skyroot_stairs", (properties) -> new StairBlock(SKYROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.SKYROOT_PLANKS.get()));
    public static final RegistryObject<SlabBlock> SKYROOT_SLAB = register("skyroot_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final RegistryObject<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", FenceBlock::new, () -> Block.Properties.copy(Blocks.OAK_FENCE));
    public static final RegistryObject<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", (properties) -> new FenceGateBlock(properties, AetherIIWoodTypes.SKYROOT), () -> Block.Properties.copy(Blocks.OAK_FENCE_GATE));
    public static final RegistryObject<DoorBlock> SKYROOT_DOOR = register("skyroot_door", (properties) -> new DoorBlock(properties, AetherIIWoodTypes.SKYROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_DOOR));
    public static final RegistryObject<TrapDoorBlock> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", (properties) -> new TrapDoorBlock(properties, AetherIIWoodTypes.SKYROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));
    public static final RegistryObject<ButtonBlock> SKYROOT_BUTTON = register("skyroot_button", (properties) -> new ButtonBlock(properties, AetherIIWoodTypes.SKYROOT_BLOCK_SET, 30, true), () -> Block.Properties.copy(Blocks.OAK_BUTTON));
    public static final RegistryObject<PressurePlateBlock> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", (properties) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, AetherIIWoodTypes.SKYROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE));
    public static final RegistryObject<ShelfBlock> SKYROOT_SHELF = register("skyroot_shelf", ShelfBlock::new, () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY));

    // Skyroot Decorative Blocks
    public static final RegistryObject<Block> SKYROOT_FLOORBOARDS = register("skyroot_floorboards", () -> Block.Properties.copy(SKYROOT_PLANKS.get()).mapColor(MapColor.COLOR_BROWN));
    public static final RegistryObject<Block> SKYROOT_HIGHLIGHT = register("skyroot_highlight", () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<Block> SKYROOT_SHINGLES = register("skyroot_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<Block> SKYROOT_SMALL_SHINGLES = register("skyroot_small_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<Block> SKYROOT_BASE_PLANKS = register("skyroot_base_planks", () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<Block> SKYROOT_TOP_PLANKS = register("skyroot_top_planks", () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> SKYROOT_BASE_BEAM = register("skyroot_base_beam", FacingPillarBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> SKYROOT_TOP_BEAM = register("skyroot_top_beam", FacingPillarBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> SKYROOT_BEAM = register("skyroot_beam", FacingPillarBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<DoorBlock> SECRET_SKYROOT_DOOR = register("secret_skyroot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(SKYROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());
    public static final RegistryObject<TrapDoorBlock> SECRET_SKYROOT_TRAPDOOR = register("secret_skyroot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));

    // Greatroot Planks
    public static final RegistryObject<Block> GREATROOT_PLANKS = register("greatroot_planks", () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN));
    public static final RegistryObject<StairBlock> GREATROOT_STAIRS = register("greatroot_stairs", (properties) -> new StairBlock(GREATROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.GREATROOT_PLANKS.get()));
    public static final RegistryObject<SlabBlock> GREATROOT_SLAB = register("greatroot_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.GREATROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final RegistryObject<FenceBlock> GREATROOT_FENCE = register("greatroot_fence", FenceBlock::new, () -> Block.Properties.copy(Blocks.OAK_FENCE));
    public static final RegistryObject<FenceGateBlock> GREATROOT_FENCE_GATE = register("greatroot_fence_gate", (properties) -> new FenceGateBlock(properties, AetherIIWoodTypes.GREATROOT), () -> Block.Properties.copy(Blocks.OAK_FENCE_GATE));
    public static final RegistryObject<DoorBlock> GREATROOT_DOOR = register("greatroot_door", (properties) -> new DoorBlock(properties, AetherIIWoodTypes.GREATROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_DOOR));
    public static final RegistryObject<TrapDoorBlock> GREATROOT_TRAPDOOR = register("greatroot_trapdoor", (properties) -> new TrapDoorBlock(properties, AetherIIWoodTypes.GREATROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));
    public static final RegistryObject<ButtonBlock> GREATROOT_BUTTON = register("greatroot_button", (properties) -> new ButtonBlock(properties, AetherIIWoodTypes.GREATROOT_BLOCK_SET, 30, true), () -> Block.Properties.copy(Blocks.OAK_BUTTON));
    public static final RegistryObject<PressurePlateBlock> GREATROOT_PRESSURE_PLATE = register("greatroot_pressure_plate", (properties) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, AetherIIWoodTypes.GREATROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE));
    public static final RegistryObject<ShelfBlock> GREATROOT_SHELF = register("greatroot_shelf", ShelfBlock::new, () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BROWN));

    // Greatroot Decorative Blocks
    public static final RegistryObject<Block> GREATROOT_FLOORBOARDS = register("greatroot_floorboards", () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_HIGHLIGHT = register("greatroot_highlight", () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_SHINGLES = register("greatroot_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_SMALL_SHINGLES = register("greatroot_small_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_BASE_PLANKS = register("greatroot_base_planks", () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_TOP_PLANKS = register("greatroot_top_planks", () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> GREATROOT_BASE_BEAM = register("greatroot_base_beam", FacingPillarBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> GREATROOT_TOP_BEAM = register("greatroot_top_beam", FacingPillarBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> GREATROOT_BEAM = register("greatroot_beam", FacingPillarBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<DoorBlock> SECRET_GREATROOT_DOOR = register("secret_greatroot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<TrapDoorBlock> SECRET_GREATROOT_TRAPDOOR = register("secret_greatroot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());

    // Wisproot Planks
    public static final RegistryObject<Block> WISPROOT_PLANKS = register("wisproot_planks", () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.QUARTZ));
    public static final RegistryObject<StairBlock> WISPROOT_STAIRS = register("wisproot_stairs", (properties) -> new StairBlock(WISPROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.WISPROOT_PLANKS.get()));
    public static final RegistryObject<SlabBlock> WISPROOT_SLAB = register("wisproot_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.WISPROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final RegistryObject<FenceBlock> WISPROOT_FENCE = register("wisproot_fence", FenceBlock::new, () -> Block.Properties.copy(Blocks.OAK_FENCE));
    public static final RegistryObject<FenceGateBlock> WISPROOT_FENCE_GATE = register("wisproot_fence_gate", (properties) -> new FenceGateBlock(properties, AetherIIWoodTypes.WISPROOT), () -> Block.Properties.copy(Blocks.OAK_FENCE_GATE));
    public static final RegistryObject<DoorBlock> WISPROOT_DOOR = register("wisproot_door", (properties) -> new DoorBlock(properties, AetherIIWoodTypes.WISPROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_DOOR));
    public static final RegistryObject<TrapDoorBlock> WISPROOT_TRAPDOOR = register("wisproot_trapdoor", (properties) -> new TrapDoorBlock(properties, AetherIIWoodTypes.WISPROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));
    public static final RegistryObject<ButtonBlock> WISPROOT_BUTTON = register("wisproot_button", (properties) -> new ButtonBlock(properties, AetherIIWoodTypes.WISPROOT_BLOCK_SET, 30, true), () -> Block.Properties.copy(Blocks.OAK_BUTTON));
    public static final RegistryObject<PressurePlateBlock> WISPROOT_PRESSURE_PLATE = register("wisproot_pressure_plate", (properties) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, AetherIIWoodTypes.WISPROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE));
    public static final RegistryObject<ShelfBlock> WISPROOT_SHELF = register("wisproot_shelf", ShelfBlock::new, () -> Block.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.QUARTZ));

    // Wisproot Decorative Blocks
    public static final RegistryObject<Block> WISPROOT_FLOORBOARDS = register("wisproot_floorboards", () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_HIGHLIGHT = register("wisproot_highlight", () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_SHINGLES = register("wisproot_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_SMALL_SHINGLES = register("wisproot_small_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_BASE_PLANKS = register("wisproot_base_planks", () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_TOP_PLANKS = register("wisproot_top_planks", () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> WISPROOT_BASE_BEAM = register("wisproot_base_beam", FacingPillarBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> WISPROOT_TOP_BEAM = register("wisproot_top_beam", FacingPillarBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> WISPROOT_BEAM = register("wisproot_beam", FacingPillarBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<DoorBlock> SECRET_WISPROOT_DOOR = register("secret_wisproot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<TrapDoorBlock> SECRET_WISPROOT_TRAPDOOR = register("secret_wisproot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());

    // Amberoot Planks
    public static final RegistryObject<Block> AMBEROOT_PLANKS = register("amberoot_planks", () -> Block.Properties.copy(Blocks.OAK_PLANKS));
    public static final RegistryObject<StairBlock> AMBEROOT_STAIRS = register("amberoot_stairs", (properties) -> new StairBlock(AMBEROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.AMBEROOT_PLANKS.get()));
    public static final RegistryObject<SlabBlock> AMBEROOT_SLAB = register("amberoot_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.AMBEROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final RegistryObject<FenceBlock> AMBEROOT_FENCE = register("amberoot_fence", FenceBlock::new, () -> Block.Properties.copy(Blocks.OAK_FENCE));
    public static final RegistryObject<FenceGateBlock> AMBEROOT_FENCE_GATE = register("amberoot_fence_gate", (properties) -> new FenceGateBlock(properties, AetherIIWoodTypes.AMBEROOT), () -> Block.Properties.copy(Blocks.OAK_FENCE_GATE));
    public static final RegistryObject<DoorBlock> AMBEROOT_DOOR = register("amberoot_door", (properties) -> new DoorBlock(properties, AetherIIWoodTypes.AMBEROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_DOOR));
    public static final RegistryObject<TrapDoorBlock> AMBEROOT_TRAPDOOR = register("amberoot_trapdoor", (properties) -> new TrapDoorBlock(properties, AetherIIWoodTypes.AMBEROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));
    public static final RegistryObject<ButtonBlock> AMBEROOT_BUTTON = register("amberoot_button", (properties) -> new ButtonBlock(properties, AetherIIWoodTypes.AMBEROOT_BLOCK_SET, 30, true), () -> Block.Properties.copy(Blocks.OAK_BUTTON));
    public static final RegistryObject<PressurePlateBlock> AMBEROOT_PRESSURE_PLATE = register("amberoot_pressure_plate", (properties) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties, AetherIIWoodTypes.AMBEROOT_BLOCK_SET), () -> Block.Properties.copy(Blocks.OAK_PRESSURE_PLATE));
    public static final RegistryObject<ShelfBlock> AMBEROOT_SHELF = register("amberoot_shelf", ShelfBlock::new, () -> Block.Properties.copy(Blocks.OAK_PLANKS));

    // Amberoot Decorative Blocks
    public static final RegistryObject<Block> AMBEROOT_FLOORBOARDS = register("amberoot_floorboards", () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_HIGHLIGHT = register("amberoot_highlight", () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_SHINGLES = register("amberoot_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_SMALL_SHINGLES = register("amberoot_small_shingles", HorizontalFacingBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_BASE_PLANKS = register("amberoot_base_planks", () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_TOP_PLANKS = register("amberoot_top_planks", () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> AMBEROOT_BASE_BEAM = register("amberoot_base_beam", FacingPillarBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> AMBEROOT_TOP_BEAM = register("amberoot_top_beam", FacingPillarBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<FacingPillarBlock> AMBEROOT_BEAM = register("amberoot_beam", FacingPillarBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<DoorBlock> SECRET_AMBEROOT_DOOR = register("secret_amberoot_door", (properties) -> new SecretDoorBlock(AetherIIWoodTypes.AMBEROOT_BLOCK_SET, properties), () -> Block.Properties.of().mapColor(AMBEROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava());
    public static final RegistryObject<TrapDoorBlock> SECRET_AMBEROOT_TRAPDOOR = register("secret_amberoot_trapdoor", (properties) -> new SecretTrapDoorBlock(AetherIIWoodTypes.AMBEROOT_BLOCK_SET, properties), () -> Block.Properties.copy(Blocks.OAK_TRAPDOOR));

    // Holystone
    public static final RegistryObject<StairBlock> HOLYSTONE_STAIRS = register("holystone_stairs", (properties) -> new StairBlock(HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE.get()));
    public static final RegistryObject<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> HOLYSTONE_WALL = register("holystone_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE.get()).forceSolidOn());
    public static final RegistryObject<ButtonBlock> HOLYSTONE_BUTTON = register("holystone_button", (properties) -> new ButtonBlock(properties, BlockSetType.STONE, 20, false), () -> Block.Properties.copy(Blocks.STONE_BUTTON));
    public static final RegistryObject<PressurePlateBlock> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", (properties) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, properties, BlockSetType.STONE), () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F));

    // Mossy Holystone
    public static final RegistryObject<StairBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs", (properties) -> new StairBlock(MOSSY_HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.MOSSY_HOLYSTONE.get()));
    public static final RegistryObject<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).forceSolidOn());

    // Irradiated Holystone
    public static final RegistryObject<StairBlock> IRRADIATED_HOLYSTONE_STAIRS = register("irradiated_holystone_stairs", (properties) -> new StairBlock(IRRADIATED_HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()));
    public static final RegistryObject<SlabBlock> IRRADIATED_HOLYSTONE_SLAB = register("irradiated_holystone_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> IRRADIATED_HOLYSTONE_WALL = register("irradiated_holystone_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).forceSolidOn());

    // Holystone Bricks
    public static final RegistryObject<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<StairBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs", (properties) -> new StairBlock(HOLYSTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).forceSolidOn());

    // Holystone Decorative Blocks
    public static final RegistryObject<Block> HOLYSTONE_FLAGSTONES = register("holystone_flagstones", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> HOLYSTONE_HEADSTONE = register("holystone_headstone", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> HOLYSTONE_KEYSTONE = register("holystone_keystone", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> HOLYSTONE_BASE_BRICKS = register("holystone_base_bricks", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> HOLYSTONE_CAPSTONE_BRICKS = register("holystone_capstone_bricks", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> HOLYSTONE_BASE_PILLAR = register("holystone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> HOLYSTONE_CAPSTONE_PILLAR = register("holystone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> HOLYSTONE_PILLAR = register("holystone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));

    // Faded Holystone Bricks
    public static final RegistryObject<Block> FADED_HOLYSTONE_BRICKS = register("faded_holystone_bricks", () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<StairBlock> FADED_HOLYSTONE_BRICK_STAIRS = register("faded_holystone_brick_stairs", (properties) -> new StairBlock(FADED_HOLYSTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<SlabBlock> FADED_HOLYSTONE_BRICK_SLAB = register("faded_holystone_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> FADED_HOLYSTONE_BRICK_WALL = register("faded_holystone_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).forceSolidOn());

    // Faded Holystone Decorative Blocks
    public static final RegistryObject<Block> FADED_HOLYSTONE_FLAGSTONES = register("faded_holystone_flagstones", () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> FADED_HOLYSTONE_HEADSTONE = register("faded_holystone_headstone", () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> FADED_HOLYSTONE_KEYSTONE = register("faded_holystone_keystone", () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> FADED_HOLYSTONE_BASE_BRICKS = register("faded_holystone_base_bricks", () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<Block> FADED_HOLYSTONE_CAPSTONE_BRICKS = register("faded_holystone_capstone_bricks", () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> FADED_HOLYSTONE_BASE_PILLAR = register("faded_holystone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> FADED_HOLYSTONE_CAPSTONE_PILLAR = register("faded_holystone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> FADED_HOLYSTONE_PILLAR = register("faded_holystone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(FADED_HOLYSTONE_BRICKS.get()));

    // Undershale
    public static final RegistryObject<StairBlock> UNDERSHALE_STAIRS = register("undershale_stairs", (properties) -> new StairBlock(UNDERSHALE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE.get()));
    public static final RegistryObject<SlabBlock> UNDERSHALE_SLAB = register("undershale_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE.get()));
    public static final RegistryObject<WallBlock> UNDERSHALE_WALL = register("undershale_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE.get()).forceSolidOn());

    // Undershale Bricks
    public static final RegistryObject<Block> UNDERSHALE_BRICKS = register("undershale_bricks", () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops());
    public static final RegistryObject<StairBlock> UNDERSHALE_BRICK_STAIRS = register("undershale_brick_stairs", (properties) -> new StairBlock(UNDERSHALE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<SlabBlock> UNDERSHALE_BRICK_SLAB = register("undershale_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<WallBlock> UNDERSHALE_BRICK_WALL = register("undershale_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.UNDERSHALE_BRICKS.get()).forceSolidOn());
    public static final RegistryObject<ButtonBlock> UNDERSHALE_BRICK_BUTTON = register("undershale_brick_button", (properties) -> new ButtonBlock(properties, BlockSetType.STONE, 20, false), () -> Block.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.DEEPSLATE));
    public static final RegistryObject<PlayerPressurePlateBlock> UNDERSHALE_BRICK_PRESSURE_PLATE = register("undershale_brick_pressure_plate", (properties) -> new PlayerPressurePlateBlock(BlockSetType.STONE, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.DEEPSLATE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F));

    // Undershale Decorative Blocks
    public static final RegistryObject<Block> UNDERSHALE_FLAGSTONES = register("undershale_flagstones", () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<Block> UNDERSHALE_TILE = register("undershale_tile", () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<Block> UNDERSHALE_BASE_BRICKS = register("undershale_base_bricks", () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<Block> UNDERSHALE_CAPSTONE_BRICKS = register("undershale_capstone_bricks", () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> UNDERSHALE_BASE_PILLAR = register("undershale_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> UNDERSHALE_CAPSTONE_PILLAR = register("undershale_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> UNDERSHALE_PILLAR = register("undershale_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(UNDERSHALE_BRICKS.get()));

    // Sentry Bricks
    public static final RegistryObject<Block> SENTRY_BRICKS = register("sentry_bricks", SentryBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().isRedstoneConductor(AetherIIBlockBuilders::never).lightLevel(AetherIIBlockBuilders::lightLevel6));
    public static final RegistryObject<StairBlock> SENTRY_BRICK_STAIRS = register("sentry_brick_stairs", (properties) -> new SentryStairBlock(SENTRY_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.SENTRY_BRICKS.get()));
    public static final RegistryObject<SlabBlock> SENTRY_BRICK_SLAB = register("sentry_brick_slab", SentrySlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SENTRY_BRICKS.get()));
    public static final RegistryObject<WallBlock> SENTRY_BRICK_WALL = register("sentry_brick_wall", SentryWallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SENTRY_BRICKS.get()).forceSolidOn());
    public static final RegistryObject<ButtonBlock> SENTRY_BUTTON = register("sentry_button", (properties) -> new ButtonBlock(properties, BlockSetType.STONE, 20, false), () -> Block.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.DEEPSLATE));

    // Sentry Decorative Blocks
    public static final RegistryObject<Block> SENTRY_LIGHTSTONE = register("sentry_lightstone", SentryBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()).lightLevel(AetherIIBlockBuilders::lightLevel11));
    public static final RegistryObject<Block> SENTRY_FLAGSTONES = register("sentry_flagstones", SentryBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<Block> SENTRY_TILE = register("sentry_tile", SentryBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<Block> SENTRY_BASE_BRICKS = register("sentry_base_bricks", SentryBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<Block> SENTRY_CAPSTONE_BRICKS = register("sentry_capstone_bricks", SentryBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> SENTRY_BASE_PILLAR = register("sentry_base_pillar", SentryFacingPillarBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> SENTRY_CAPSTONE_PILLAR = register("sentry_capstone_pillar", SentryFacingPillarBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> SENTRY_PILLAR = register("sentry_pillar", SentryFacingPillarBlock::new, () -> Block.Properties.copy(SENTRY_BRICKS.get()));

    // Ichorite
    public static final RegistryObject<StairBlock> ICHORITE_STAIRS = register("ichorite_stairs", (properties) -> new StairBlock(ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.ICHORITE.get()));
    public static final RegistryObject<SlabBlock> ICHORITE_SLAB = register("ichorite_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICHORITE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> ICHORITE_WALL = register("ichorite_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICHORITE.get()).forceSolidOn());

    // Smooth Ichorite
    public static final RegistryObject<Block> SMOOTH_ICHORITE = register("smooth_ichorite", () -> Block.Properties.copy(ICHORITE.get()));
    public static final RegistryObject<StairBlock> SMOOTH_ICHORITE_STAIRS = register("smooth_ichorite_stairs", (properties) -> new StairBlock(SMOOTH_ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.SMOOTH_ICHORITE.get()));
    public static final RegistryObject<SlabBlock> SMOOTH_ICHORITE_SLAB = register("smooth_ichorite_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SMOOTH_ICHORITE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> SMOOTH_ICHORITE_WALL = register("smooth_ichorite_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SMOOTH_ICHORITE.get()).forceSolidOn());

    // Ichorite Bricks
    public static final RegistryObject<Block> ICHORITE_BRICKS = register("ichorite_bricks", () -> Block.Properties.copy(SMOOTH_ICHORITE.get()));
    public static final RegistryObject<StairBlock> ICHORITE_BRICK_STAIRS = register("ichorite_brick_stairs", (properties) -> new StairBlock(ICHORITE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.SMOOTH_ICHORITE.get()));
    public static final RegistryObject<SlabBlock> ICHORITE_BRICK_SLAB = register("ichorite_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICHORITE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> ICHORITE_BRICK_WALL = register("ichorite_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICHORITE_BRICKS.get()).forceSolidOn());

    // Marbled Ichorite Decorative Blocks
    public static final RegistryObject<Block> ICHORITE_FLAGSTONES = register("ichorite_flagstones", () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<Block> ICHORITE_RUNESTONE = register("ichorite_runestone", () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<Block> ICHORITE_KEYSTONE = register("ichorite_keystone", () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<Block> ICHORITE_BASE_BRICKS = register("ichorite_base_bricks", () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<Block> ICHORITE_CAPSTONE_BRICKS = register("ichorite_capstone_bricks", () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICHORITE_BASE_PILLAR = register("ichorite_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICHORITE_CAPSTONE_PILLAR = register("ichorite_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICHORITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICHORITE_PILLAR = register("ichorite_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICHORITE_BRICKS.get()));

    // Marbled Ichorite
    public static final RegistryObject<Block> MARBLED_ICHORITE = register("marbled_ichorite", () -> Block.Properties.copy(ICHORITE.get()));
    public static final RegistryObject<StairBlock> MARBLED_ICHORITE_STAIRS = register("marbled_ichorite_stairs", (properties) -> new StairBlock(MARBLED_ICHORITE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.MARBLED_ICHORITE.get()));
    public static final RegistryObject<SlabBlock> MARBLED_ICHORITE_SLAB = register("marbled_ichorite_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MARBLED_ICHORITE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> MARBLED_ICHORITE_WALL = register("marbled_ichorite_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MARBLED_ICHORITE.get()).forceSolidOn());

    // Marbled Bricks
    public static final RegistryObject<Block> MARBLED_BRICKS = register("marbled_bricks", () -> Block.Properties.copy(MARBLED_ICHORITE.get()));
    public static final RegistryObject<StairBlock> MARBLED_BRICK_STAIRS = register("marbled_brick_stairs", (properties) -> new StairBlock(MARBLED_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.MARBLED_BRICKS.get()));
    public static final RegistryObject<SlabBlock> MARBLED_BRICK_SLAB = register("marbled_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MARBLED_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> MARBLED_BRICK_WALL = register("marbled_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.MARBLED_BRICKS.get()).forceSolidOn());

    // Marbled Ichorite Decorative Blocks
    public static final RegistryObject<Block> MARBLED_FLAGSTONES = register("marbled_flagstones", () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<Block> MARBLED_KEYSTONE = register("marbled_keystone", () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<Block> MARBLED_BASE_BRICKS = register("marbled_base_bricks", () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<Block> MARBLED_CAPSTONE_BRICKS = register("marbled_capstone_bricks", () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> MARBLED_BASE_PILLAR = register("marbled_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> MARBLED_CAPSTONE_PILLAR = register("marbled_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(MARBLED_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> MARBLED_PILLAR = register("marbled_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(MARBLED_BRICKS.get()));

    // Agiosite
    public static final RegistryObject<StairBlock> AGIOSITE_STAIRS = register("agiosite_stairs", (properties) -> new StairBlock(AGIOSITE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE.get()));
    public static final RegistryObject<SlabBlock> AGIOSITE_SLAB = register("agiosite_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> AGIOSITE_WALL = register("agiosite_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE.get()).forceSolidOn());

    // Agiosite Bricks
    public static final RegistryObject<Block> AGIOSITE_BRICKS = register("agiosite_bricks", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<StairBlock> AGIOSITE_BRICK_STAIRS = register("agiosite_brick_stairs", (properties) -> new StairBlock(AGIOSITE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE_BRICKS.get()));
    public static final RegistryObject<SlabBlock> AGIOSITE_BRICK_SLAB = register("agiosite_brick_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> AGIOSITE_BRICK_WALL = register("agiosite_brick_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.AGIOSITE_BRICKS.get()).forceSolidOn());

    // Agiosite Decorative Blocks
    public static final RegistryObject<Block> AGIOSITE_FLAGSTONES = register("agiosite_flagstones", () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<Block> AGIOSITE_KEYSTONE = register("agiosite_keystone", () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<Block> AGIOSITE_BASE_BRICKS = register("agiosite_base_bricks", () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<Block> AGIOSITE_CAPSTONE_BRICKS = register("agiosite_capstone_bricks", () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> AGIOSITE_BASE_PILLAR = register("agiosite_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> AGIOSITE_CAPSTONE_PILLAR = register("agiosite_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> AGIOSITE_PILLAR = register("agiosite_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(AGIOSITE_BRICKS.get()));

    // Icestone
    public static final RegistryObject<StairBlock> ICESTONE_STAIRS = register("icestone_stairs", (properties) -> new IcestoneStairsBlock(ICESTONE.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.ICESTONE.get()));
    public static final RegistryObject<SlabBlock> ICESTONE_SLAB = register("icestone_slab", IcestoneSlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICESTONE.get()).strength(0.5F, 6.0F));
    public static final RegistryObject<WallBlock> ICESTONE_WALL = register("icestone_wall", IcestoneWallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICESTONE.get()).forceSolidOn());

    // Icestone Bricks
    public static final RegistryObject<Block> ICESTONE_BRICKS = register("icestone_bricks", () -> Block.Properties.of().mapColor(MapColor.ICE).sound(SoundType.GLASS).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<StairBlock> ICESTONE_BRICK_STAIRS = register("icestone_bricks_stairs", (properties) -> new StairBlock(ICESTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.copy(AetherIIBlocks.ICESTONE_BRICKS.get()));
    public static final RegistryObject<SlabBlock> ICESTONE_BRICK_SLAB = register("icestone_bricks_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICESTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final RegistryObject<WallBlock> ICESTONE_BRICK_WALL = register("icestone_bricks_wall", WallBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ICESTONE_BRICKS.get()).forceSolidOn());

    // Icestone Decorative Blocks
    public static final RegistryObject<Block> ICESTONE_FLAGSTONES = register("icestone_flagstones", () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<Block> ICESTONE_KEYSTONE = register("icestone_keystone", () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<Block> ICESTONE_BASE_BRICKS = register("icestone_base_bricks", () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<Block> ICESTONE_CAPSTONE_BRICKS = register("icestone_capstone_bricks", () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICESTONE_BASE_PILLAR = register("icestone_base_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICESTONE_CAPSTONE_PILLAR = register("icestone_capstone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICESTONE_BRICKS.get()));
    public static final RegistryObject<FacingPillarBlock> ICESTONE_PILLAR = register("icestone_pillar", FacingPillarBlock::new, () -> Block.Properties.copy(ICESTONE_BRICKS.get()));

    // Glass
    public static final RegistryObject<HalfTransparentBlock> QUICKSOIL_GLASS = register("quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final RegistryObject<HalfTransparentBlock> TILED_QUICKSOIL_GLASS = register("tiled_quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.copy(QUICKSOIL_GLASS.get()));
    public static final RegistryObject<HalfTransparentBlock> GRIDDED_QUICKSOIL_GLASS = register("gridded_quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.copy(QUICKSOIL_GLASS.get()));
    public static final RegistryObject<HalfTransparentBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS = register("skyroot_framed_crude_scatterglass", CrudeScatterglassBlock::new, () -> Block.Properties.copy(CRUDE_SCATTERGLASS.get()));
    public static final RegistryObject<HalfTransparentBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS = register("arkenium_framed_crude_scatterglass", CrudeScatterglassBlock::new, () -> Block.Properties.copy(CRUDE_SCATTERGLASS.get()));
    public static final RegistryObject<HalfTransparentBlock> SCATTERGLASS = register("scatterglass", ScatterglassBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never));
    public static final RegistryObject<HalfTransparentBlock> SKYROOT_FRAMED_SCATTERGLASS = register("skyroot_framed_scatterglass", ScatterglassBlock::new, () -> Block.Properties.copy(SCATTERGLASS.get()));
    public static final RegistryObject<HalfTransparentBlock> ARKENIUM_FRAMED_SCATTERGLASS = register("arkenium_framed_scatterglass", ScatterglassBlock::new, () -> Block.Properties.copy(SCATTERGLASS.get()));

    // Glass Panes
    public static final RegistryObject<IronBarsBlock> QUICKSOIL_GLASS_PANE = register("quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion());
    public static final RegistryObject<IronBarsBlock> TILED_QUICKSOIL_GLASS_PANE = register("tiled_quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.copy(QUICKSOIL_GLASS_PANE.get()));
    public static final RegistryObject<IronBarsBlock> GRIDDED_QUICKSOIL_GLASS_PANE = register("gridded_quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.copy(QUICKSOIL_GLASS_PANE.get()));
    public static final RegistryObject<IronBarsBlock> CRUDE_SCATTERGLASS_PANE = register("crude_scatterglass_pane", CrudeScatterglassPaneBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never));
    public static final RegistryObject<IronBarsBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE = register("skyroot_framed_crude_scatterglass_pane", CrudeScatterglassPaneBlock::new, () -> Block.Properties.copy(CRUDE_SCATTERGLASS_PANE.get()));
    public static final RegistryObject<IronBarsBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE = register("arkenium_framed_crude_scatterglass_pane", CrudeScatterglassPaneBlock::new, () -> Block.Properties.copy(CRUDE_SCATTERGLASS_PANE.get()));
    public static final RegistryObject<IronBarsBlock> SCATTERGLASS_PANE = register("scatterglass_pane", ScatterglassPaneBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops());
    public static final RegistryObject<IronBarsBlock> SKYROOT_FRAMED_SCATTERGLASS_PANE = register("skyroot_framed_scatterglass_pane", ScatterglassPaneBlock::new, () -> Block.Properties.copy(SCATTERGLASS_PANE.get()));
    public static final RegistryObject<IronBarsBlock> ARKENIUM_FRAMED_SCATTERGLASS_PANE = register("arkenium_framed_scatterglass_pane", ScatterglassPaneBlock::new, () -> Block.Properties.copy(SCATTERGLASS_PANE.get()));

    // Wool
    public static final RegistryObject<Block> CLOUDWOOL = register("cloudwool", () -> Block.Properties.copy(Blocks.WHITE_WOOL));
    public static final RegistryObject<Block> WHITE_CLOUDWOOL = register("white_cloudwool", () -> Block.Properties.copy(Blocks.WHITE_WOOL));
    public static final RegistryObject<Block> ORANGE_CLOUDWOOL = register("orange_cloudwool", () -> Block.Properties.copy(Blocks.ORANGE_WOOL));
    public static final RegistryObject<Block> MAGENTA_CLOUDWOOL = register("magenta_cloudwool", () -> Block.Properties.copy(Blocks.MAGENTA_WOOL));
    public static final RegistryObject<Block> LIGHT_BLUE_CLOUDWOOL = register("light_blue_cloudwool", () -> Block.Properties.copy(Blocks.LIGHT_BLUE_WOOL));
    public static final RegistryObject<Block> YELLOW_CLOUDWOOL = register("yellow_cloudwool", () -> Block.Properties.copy(Blocks.YELLOW_WOOL));
    public static final RegistryObject<Block> LIME_CLOUDWOOL = register("lime_cloudwool", () -> Block.Properties.copy(Blocks.LIME_WOOL));
    public static final RegistryObject<Block> PINK_CLOUDWOOL = register("pink_cloudwool", () -> Block.Properties.copy(Blocks.PINK_WOOL));
    public static final RegistryObject<Block> GRAY_CLOUDWOOL = register("gray_cloudwool", () -> Block.Properties.copy(Blocks.GRAY_WOOL));
    public static final RegistryObject<Block> LIGHT_GRAY_CLOUDWOOL = register("light_gray_cloudwool", () -> Block.Properties.copy(Blocks.LIGHT_GRAY_WOOL));
    public static final RegistryObject<Block> CYAN_CLOUDWOOL = register("cyan_cloudwool", () -> Block.Properties.copy(Blocks.CYAN_WOOL));
    public static final RegistryObject<Block> PURPLE_CLOUDWOOL = register("purple_cloudwool", () -> Block.Properties.copy(Blocks.PURPLE_WOOL));
    public static final RegistryObject<Block> BLUE_CLOUDWOOL = register("blue_cloudwool", () -> Block.Properties.copy(Blocks.BLUE_WOOL));
    public static final RegistryObject<Block> BROWN_CLOUDWOOL = register("brown_cloudwool", () -> Block.Properties.copy(Blocks.BROWN_WOOL));
    public static final RegistryObject<Block> GREEN_CLOUDWOOL = register("green_cloudwool", () -> Block.Properties.copy(Blocks.GREEN_WOOL));
    public static final RegistryObject<Block> RED_CLOUDWOOL = register("red_cloudwool", () -> Block.Properties.copy(Blocks.RED_WOOL));
    public static final RegistryObject<Block> BLACK_CLOUDWOOL = register("black_cloudwool", () -> Block.Properties.copy(Blocks.BLACK_WOOL));

    // Carpet
    public static final RegistryObject<CarpetBlock> CLOUDWOOL_CARPET = register("cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.WHITE_CARPET));
    public static final RegistryObject<CarpetBlock> WHITE_CLOUDWOOL_CARPET = register("white_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.WHITE_CARPET));
    public static final RegistryObject<CarpetBlock> ORANGE_CLOUDWOOL_CARPET = register("orange_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.ORANGE_CARPET));
    public static final RegistryObject<CarpetBlock> MAGENTA_CLOUDWOOL_CARPET = register("magenta_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.MAGENTA_CARPET));
    public static final RegistryObject<CarpetBlock> LIGHT_BLUE_CLOUDWOOL_CARPET = register("light_blue_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.LIGHT_BLUE_CARPET));
    public static final RegistryObject<CarpetBlock> YELLOW_CLOUDWOOL_CARPET = register("yellow_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.YELLOW_CARPET));
    public static final RegistryObject<CarpetBlock> LIME_CLOUDWOOL_CARPET = register("lime_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.LIME_CARPET));
    public static final RegistryObject<CarpetBlock> PINK_CLOUDWOOL_CARPET = register("pink_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.PINK_CARPET));
    public static final RegistryObject<CarpetBlock> GRAY_CLOUDWOOL_CARPET = register("gray_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.GRAY_CARPET));
    public static final RegistryObject<CarpetBlock> LIGHT_GRAY_CLOUDWOOL_CARPET = register("light_gray_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.LIGHT_GRAY_CARPET));
    public static final RegistryObject<CarpetBlock> CYAN_CLOUDWOOL_CARPET = register("cyan_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.CYAN_CARPET));
    public static final RegistryObject<CarpetBlock> PURPLE_CLOUDWOOL_CARPET = register("purple_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.PURPLE_CARPET));
    public static final RegistryObject<CarpetBlock> BLUE_CLOUDWOOL_CARPET = register("blue_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.BLUE_CARPET));
    public static final RegistryObject<CarpetBlock> BROWN_CLOUDWOOL_CARPET = register("brown_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.BROWN_CARPET));
    public static final RegistryObject<CarpetBlock> GREEN_CLOUDWOOL_CARPET = register("green_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.GREEN_CARPET));
    public static final RegistryObject<CarpetBlock> RED_CLOUDWOOL_CARPET = register("red_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.RED_CARPET));
    public static final RegistryObject<CarpetBlock> BLACK_CLOUDWOOL_CARPET = register("black_cloudwool_carpet", CarpetBlock::new, () -> Block.Properties.copy(Blocks.BLACK_CARPET));

    // Roofing
    public static final RegistryObject<Block> CLOUDWOOL_ROOFING = register("cloudwool_roofing", () -> BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).strength(1.5F).sound(SoundType.WOOL).ignitedByLava());

    // Arkenium Blocks
    public static final RegistryObject<DoorBlock> ARKENIUM_DOOR = register("arkenium_door", (properties) -> new DoorBlock(properties, BlockSetType.IRON), () -> Block.Properties.copy(Blocks.IRON_DOOR));
    public static final RegistryObject<TrapDoorBlock> ARKENIUM_TRAPDOOR = register("arkenium_trapdoor", (properties) -> new TrapDoorBlock(properties, BlockSetType.IRON), () -> Block.Properties.copy(Blocks.IRON_TRAPDOOR));
    public static final RegistryObject<IronBarsBlock> ARKENIUM_BARS = register("arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> FLORAL_ARKENIUM_BARS = register("floral_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> PATTERNED_ARKENIUM_BARS = register("patterned_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> CURVED_ARKENIUM_BARS = register("curved_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));

    // Rustic Arkenium Blocks
    public static final RegistryObject<IronBarsBlock> RUSTIC_ARKENIUM_BARS = register("rustic_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> RUSTIC_FLORAL_ARKENIUM_BARS = register("rustic_floral_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> RUSTIC_PATTERNED_ARKENIUM_BARS = register("rustic_patterned_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));
    public static final RegistryObject<IronBarsBlock> RUSTIC_CURVED_ARKENIUM_BARS = register("rustic_curved_arkenium_bars", IronBarsBlock::new, () -> Block.Properties.copy(Blocks.IRON_BARS));

    // Inert Mineral Blocks
    public static final RegistryObject<Block> INERT_ARKENIUM_BLOCK = register("inert_arkenium_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0F, 6.0F).requiresCorrectToolForDrops());
    public static final RegistryObject<Block> INERT_GRAVITITE_BLOCK = register("inert_gravitite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0F, 6.0F).requiresCorrectToolForDrops());

    // Mineral Blocks
    public static final RegistryObject<Block> AMBROSIUM_BLOCK = register("ambrosium_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel(AetherIIBlocks::lightLevel11));
    public static final RegistryObject<Block> ZANITE_BLOCK = register("zanite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final RegistryObject<Block> ARKENIUM_BLOCK = register("arkenium_block", () -> Block.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final RegistryObject<Block> GRAVITITE_BLOCK = register("gravitite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final RegistryObject<Block> GLINT_BLOCK = register("glint_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final RegistryObject<Block> CORROBONITE_BLOCK = register("corrobonite_block", () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final RegistryObject<Block> GOLDEN_AMBER_BLOCK = register("golden_amber_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));

    // Storage Blocks
    public static final RegistryObject<Block> BRETTL_GRASS_BUNDLE = register("brettl_grass_bundle", HayBlock::new, () -> Block.Properties.copy(Blocks.HAY_BLOCK).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final RegistryObject<Block> GEL_BLOCK = register("gel_block", GelBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_CYAN).speedFactor(0.4F).jumpFactor(0.5F).noOcclusion().sound(SoundType.HONEY_BLOCK));

    // Arilum Lantern
    public static final RegistryObject<Block> WHITE_ARILUM_LANTERN = register("white_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.SNOW));
    public static final RegistryObject<Block> ORANGE_ARILUM_LANTERN = register("orange_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_ORANGE));
    public static final RegistryObject<Block> MAGENTA_ARILUM_LANTERN = register("magenta_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ARILUM_LANTERN = register("light_blue_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_LIGHT_BLUE));

    public static final RegistryObject<Block> YELLOW_ARILUM_LANTERN = register("yellow_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_YELLOW));
    public static final RegistryObject<Block> LIME_ARILUM_LANTERN = register("lime_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryObject<Block> PINK_ARILUM_LANTERN = register("pink_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_PINK));
    public static final RegistryObject<Block> GRAY_ARILUM_LANTERN = register("gray_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_GRAY));
    public static final RegistryObject<Block> LIGHT_GRAY_ARILUM_LANTERN = register("light_gray_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_LIGHT_GRAY));
    public static final RegistryObject<Block> CYAN_ARILUM_LANTERN = register("cyan_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_CYAN));
    public static final RegistryObject<Block> PURPLE_ARILUM_LANTERN = register("purple_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_PURPLE));
    public static final RegistryObject<Block> BLUE_ARILUM_LANTERN = register("blue_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_BLUE));
    public static final RegistryObject<Block> BROWN_ARILUM_LANTERN = register("brown_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_BROWN));
    public static final RegistryObject<Block> GREEN_ARILUM_LANTERN = register("green_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> RED_ARILUM_LANTERN = register("red_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_RED));
    public static final RegistryObject<Block> BLACK_ARILUM_LANTERN = register("black_arilum_lantern", HalfTransparentBlock::new, arilumLanternProperties(MapColor.COLOR_BLACK));


    // Utility
    public static final RegistryObject<Block> AMBROSIUM_TORCH = register("ambrosium_torch", AmbrosiumTorchBlock::new, () -> Block.Properties.copy(Blocks.TORCH), torchItem(() -> AetherIIBlocks.AMBROSIUM_WALL_TORCH));
    public static final RegistryObject<Block> AMBROSIUM_WALL_TORCH = registerWithoutItem("ambrosium_wall_torch", AmbrosiumWallTorchBlock::new, () -> Block.Properties.copy(Blocks.WALL_TORCH));
    public static final RegistryObject<Block> ARKENIUM_LANTERN = register("arkenium_lantern", ArkeniumLanternBlock::new, () -> Block.Properties.copy(Blocks.LANTERN));
    public static final RegistryObject<Block> RUSTIC_ARKENIUM_LANTERN = register("rustic_arkenium_lantern", RusticArkeniumLanternBlock::new, () -> Block.Properties.copy(Blocks.LANTERN));
    public static final RegistryObject<Block> ARKENIUM_CHAIN = register("arkenium_chain", ChainBlock::new, () -> Block.Properties.copy(Blocks.CHAIN));
    public static final RegistryObject<Block> SKYROOT_CRAFTING_TABLE = register("skyroot_crafting_table", SkyrootCraftingTableBlock::new, () -> Block.Properties.copy(Blocks.CRAFTING_TABLE));
    public static final RegistryObject<Block> HOLYSTONE_FURNACE = register("holystone_furnace", HolystoneFurnaceBlock::new, () -> Block.Properties.copy(Blocks.FURNACE));
    public static final RegistryObject<Block> HOLYSTONE_SMOKER = register("holystone_smoker", HolystoneSmokerBlock::new, () -> Block.Properties.copy(Blocks.SMOKER));
    public static final RegistryObject<Block> AMBER_HOURGLASS = register("amber_hourglass", AmberHourglassBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SKYROOT_PLANKS.get()).noOcclusion());
    public static final RegistryObject<Block> ALTAR = register("altar", AltarBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HOLYSTONE.get()).noOcclusion());
    public static final RegistryObject<Block> ARTISANS_BENCH = register("artisans_bench", ArtisansBenchBlock::new, () -> Block.Properties.copy(Blocks.STONECUTTER).noOcclusion());
    public static final RegistryObject<Block> ARKENIUM_FORGE = register("arkenium_forge", ArkeniumForgeBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ARKENIUM_BLOCK.get()).noOcclusion());
    public static final RegistryObject<Block> ALKAHEST_PURIFIER = register("alkahest_purifier", AlkahestPurifierBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ARKENIUM_BLOCK.get()).noOcclusion(), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> MUSIC_BLOCK = register("music_block", MusicBlock::new, () -> Block.Properties.copy(AetherIIBlocks.SKYROOT_PLANKS.get()));
    public static final RegistryObject<CampfireBlock> AMBROSIUM_CAMPFIRE = register("ambrosium_campfire", (properties) -> new AmbrosiumCampfireBlock(false, 1, properties), () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).noOcclusion().ignitedByLava());
    public static final RegistryObject<Block> SKYROOT_CHEST = register("skyroot_chest", (properties) -> new SkyrootChestBlock(properties, AetherIIBlockEntityTypes.SKYROOT_CHEST::get), () -> Block.Properties.copy(Blocks.CHEST), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> SKYROOT_BARREL = register("skyroot_barrel", BarrelBlock::new, () -> Block.Properties.copy(Blocks.BARREL));
    public static final RegistryObject<LadderBlock> SKYROOT_LADDER = register("skyroot_ladder", LadderBlock::new, () -> Block.Properties.copy(Blocks.LADDER).strength(0.4F).sound(SoundType.LADDER).noOcclusion());
    public static final RegistryObject<BedrollBlock> CLOUDWOOL_BEDROLL = register("cloudwool_bedroll", BedrollBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).sound(SoundType.WOOL).strength(0.2F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), AetherIIBlocks.bedrollBlockItem());

    public static final RegistryObject<BedBlock> SKYROOT_BED = register("skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.WHITE, properties), () -> Block.Properties.copy(Blocks.WHITE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> WHITE_SKYROOT_BED = register("white_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.WHITE, properties), () -> Block.Properties.copy(Blocks.WHITE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> ORANGE_SKYROOT_BED = register("orange_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.ORANGE, properties), () -> Block.Properties.copy(Blocks.ORANGE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> MAGENTA_SKYROOT_BED = register("magenta_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.MAGENTA, properties), () -> Block.Properties.copy(Blocks.MAGENTA_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> LIGHT_BLUE_SKYROOT_BED = register("light_blue_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.LIGHT_BLUE, properties), () -> Block.Properties.copy(Blocks.LIGHT_BLUE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> YELLOW_SKYROOT_BED = register("yellow_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.YELLOW, properties), () -> Block.Properties.copy(Blocks.YELLOW_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> LIME_SKYROOT_BED = register("lime_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.LIME, properties), () -> Block.Properties.copy(Blocks.LIME_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> PINK_SKYROOT_BED = register("pink_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.PINK, properties), () -> Block.Properties.copy(Blocks.PINK_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> GRAY_SKYROOT_BED = register("gray_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.GRAY, properties), () -> Block.Properties.copy(Blocks.GRAY_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> LIGHT_GRAY_SKYROOT_BED = register("light_gray_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.LIGHT_GRAY, properties), () -> Block.Properties.copy(Blocks.LIGHT_GRAY_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> CYAN_SKYROOT_BED = register("cyan_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.CYAN, properties), () -> Block.Properties.copy(Blocks.CYAN_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> PURPLE_SKYROOT_BED = register("purple_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.PURPLE, properties), () -> Block.Properties.copy(Blocks.PURPLE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> BLUE_SKYROOT_BED = register("blue_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.BLUE, properties), () -> Block.Properties.copy(Blocks.BLUE_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> BROWN_SKYROOT_BED = register("brown_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.BROWN, properties), () -> Block.Properties.copy(Blocks.BROWN_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> GREEN_SKYROOT_BED = register("green_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.GREEN, properties), () -> Block.Properties.copy(Blocks.GREEN_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> RED_SKYROOT_BED = register("red_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.RED, properties), () -> Block.Properties.copy(Blocks.RED_BED), AetherIIBlocks.bedBlockItem());
    public static final RegistryObject<BedBlock> BLACK_SKYROOT_BED = register("black_skyroot_bed", (properties) -> new SkyrootBedBlock(DyeColor.BLACK, properties), () -> Block.Properties.copy(Blocks.BLACK_BED), AetherIIBlocks.bedBlockItem());

    private static Block.Properties skyrootSignProperties() {
        return Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD);
    }
    public static final RegistryObject<StandingSignBlock> SKYROOT_SIGN = register("skyroot_sign", (properties) -> new StandingSignBlock(properties, AetherIIWoodTypes.SKYROOT), AetherIIBlocks::skyrootSignProperties, signItem(() -> AetherIIBlocks.SKYROOT_WALL_SIGN));
    public static final RegistryObject<WallSignBlock> SKYROOT_WALL_SIGN = registerWithoutItem("skyroot_wall_sign", (properties) -> new WallSignBlock(properties, AetherIIWoodTypes.SKYROOT), AetherIIBlocks::skyrootSignProperties);

    private static Block.Properties skyrootHangingSignProperties() {
        return Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava();
    }
    public static final RegistryObject<CeilingHangingSignBlock> SKYROOT_HANGING_SIGN = register("skyroot_hanging_sign", (properties) -> new CeilingHangingSignBlock(properties, AetherIIWoodTypes.SKYROOT), AetherIIBlocks::skyrootHangingSignProperties, hangingSignItem(() -> AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN));
    public static final RegistryObject<WallHangingSignBlock> SKYROOT_WALL_HANGING_SIGN = registerWithoutItem("skyroot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(properties, AetherIIWoodTypes.SKYROOT), AetherIIBlocks::skyrootHangingSignProperties);

    private static Block.Properties greatrootSignProperties() { return skyrootSignProperties(); }
    public static final RegistryObject<StandingSignBlock> GREATROOT_SIGN = register("greatroot_sign", (properties) -> new StandingSignBlock(properties, AetherIIWoodTypes.GREATROOT), AetherIIBlocks::greatrootSignProperties, signItem(() -> AetherIIBlocks.GREATROOT_WALL_SIGN));
    public static final RegistryObject<WallSignBlock> GREATROOT_WALL_SIGN = registerWithoutItem("greatroot_wall_sign", (properties) -> new WallSignBlock(properties, AetherIIWoodTypes.GREATROOT), AetherIIBlocks::greatrootSignProperties);

    private static Block.Properties greatrootHangingSignProperties() { return skyrootHangingSignProperties(); }
    public static final RegistryObject<CeilingHangingSignBlock> GREATROOT_HANGING_SIGN = register("greatroot_hanging_sign", (properties) -> new CeilingHangingSignBlock(properties, AetherIIWoodTypes.GREATROOT), AetherIIBlocks::greatrootHangingSignProperties, hangingSignItem(() -> AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN));
    public static final RegistryObject<WallHangingSignBlock> GREATROOT_WALL_HANGING_SIGN = registerWithoutItem("greatroot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(properties, AetherIIWoodTypes.GREATROOT), AetherIIBlocks::greatrootHangingSignProperties);

    private static Block.Properties wisprootSignProperties() { return skyrootSignProperties(); }
    public static final RegistryObject<StandingSignBlock> WISPROOT_SIGN = register("wisproot_sign", (properties) -> new StandingSignBlock(properties, AetherIIWoodTypes.WISPROOT), AetherIIBlocks::wisprootSignProperties, signItem(() -> AetherIIBlocks.WISPROOT_WALL_SIGN));
    public static final RegistryObject<WallSignBlock> WISPROOT_WALL_SIGN = registerWithoutItem("wisproot_wall_sign", (properties) -> new WallSignBlock(properties, AetherIIWoodTypes.WISPROOT), AetherIIBlocks::wisprootSignProperties);

    private static Block.Properties wisprootHangingSignProperties() { return skyrootHangingSignProperties(); }
    public static final RegistryObject<CeilingHangingSignBlock> WISPROOT_HANGING_SIGN = register("wisproot_hanging_sign", (properties) -> new CeilingHangingSignBlock(properties, AetherIIWoodTypes.WISPROOT), AetherIIBlocks::wisprootHangingSignProperties, hangingSignItem(() -> AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN));
    public static final RegistryObject<WallHangingSignBlock> WISPROOT_WALL_HANGING_SIGN = registerWithoutItem("wisproot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(properties, AetherIIWoodTypes.WISPROOT), AetherIIBlocks::wisprootHangingSignProperties);

    private static Block.Properties amberootSignProperties() { return skyrootSignProperties(); }
    public static final RegistryObject<StandingSignBlock> AMBEROOT_SIGN = register("amberoot_sign", (properties) -> new StandingSignBlock(properties, AetherIIWoodTypes.AMBEROOT), AetherIIBlocks::wisprootSignProperties, signItem(() -> AetherIIBlocks.AMBEROOT_WALL_SIGN));
    public static final RegistryObject<WallSignBlock> AMBEROOT_WALL_SIGN = registerWithoutItem("amberoot_wall_sign", (properties) -> new WallSignBlock(properties, AetherIIWoodTypes.AMBEROOT), AetherIIBlocks::wisprootSignProperties);

    private static Block.Properties amberootHangingSignProperties() { return skyrootHangingSignProperties(); }
    public static final RegistryObject<CeilingHangingSignBlock> AMBEROOT_HANGING_SIGN = register("amberoot_hanging_sign", (properties) -> new CeilingHangingSignBlock(properties, AetherIIWoodTypes.AMBEROOT), AetherIIBlocks::wisprootHangingSignProperties, hangingSignItem(() -> AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN));
    public static final RegistryObject<WallHangingSignBlock> AMBEROOT_WALL_HANGING_SIGN = registerWithoutItem("amberoot_wall_hanging_sign", (properties) -> new WallHangingSignBlock(properties, AetherIIWoodTypes.AMBEROOT), AetherIIBlocks::wisprootHangingSignProperties);

    public static final RegistryObject<Block> HOLYSTONE_LEVER = register("holystone_lever", LeverBlock::new, () -> Block.Properties.copy(Blocks.LEVER));

    public static final RegistryObject<Block> HOLYSTONE_VASE = register("holystone_vase", VaseBlock::new, () -> Block.Properties.copy(Blocks.DECORATED_POT).mapColor(MapColor.WOOL), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> VERADEXIAN_VASE = register("veradexian_vase", VaseBlock::new, () -> Block.Properties.copy(Blocks.DECORATED_POT).mapColor(MapColor.QUARTZ), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> BREXALLEN_VASE = register("brexallen_vase", VaseBlock::new, () -> Block.Properties.copy(Blocks.DECORATED_POT).mapColor(MapColor.COLOR_PURPLE), AetherIIRenderedBlockItem::new);

    public static final RegistryObject<Block> SENTRY_CRATE = register("sentry_crate", SentryCrateBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).sound(SoundType.STONE).lightLevel((state) -> 4).requiresCorrectToolForDrops(), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> SENTRY_SPAWNER = register("sentry_spawner", SentrySpawnerBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0F).sound(SoundType.STONE).lightLevel(AetherIIBlockBuilders::lightLevel6), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> SENTRY_TRAP = register("sentry_trap", SentryTrapBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0F).sound(SoundType.STONE).lightLevel(AetherIIBlockBuilders::lightLevel6));

    public static final RegistryObject<Block> LOCKED_BLOCK = register("locked_block", LockedBlock::new, () -> BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noLootTable().isValidSpawn(AetherIIBlocks::always).pushReaction(PushReaction.BLOCK), CopyBlockItem::new);
    public static final RegistryObject<Block> BOSS_DOORWAY_BLOCK = register("boss_doorway_block", BossDoorwayBlock::new, () -> BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noLootTable().isValidSpawn(AetherIIBlocks::always).pushReaction(PushReaction.BLOCK), CopyBlockItem::new);
    public static final RegistryObject<Block> TREASURE_DOORWAY_BLOCK = register("treasure_doorway_block", TreasureDoorwayBlock::new, () -> BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noLootTable().isValidSpawn(AetherIIBlocks::always).pushReaction(PushReaction.BLOCK), CopyBlockItem::new);

    // Bookshelves
    public static final RegistryObject<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", BookshelfBlock::new, () -> Block.Properties.copy(SKYROOT_PLANKS.get()));
    public static final RegistryObject<Block> GREATROOT_BOOKSHELF = register("greatroot_bookshelf", BookshelfBlock::new, () -> Block.Properties.copy(GREATROOT_PLANKS.get()));
    public static final RegistryObject<Block> WISPROOT_BOOKSHELF = register("wisproot_bookshelf", BookshelfBlock::new, () -> Block.Properties.copy(WISPROOT_PLANKS.get()));
    public static final RegistryObject<Block> AMBEROOT_BOOKSHELF = register("amberoot_bookshelf", BookshelfBlock::new, () -> Block.Properties.copy(AMBEROOT_PLANKS.get()));
    public static final RegistryObject<Block> HOLYSTONE_BOOKSHELF = register("holystone_bookshelf", BookshelfBlock::new, () -> Block.Properties.copy(HOLYSTONE_BRICKS.get()));

    // Furniture
    public static final RegistryObject<OutpostCampfireBlock> OUTPOST_CAMPFIRE = register("outpost_campfire", OutpostCampfireBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(15.0F, 1200.0F).noOcclusion().pushReaction(PushReaction.BLOCK));
    public static final RegistryObject<Block> MURAL = register("mural", MuralBlock::new, () -> Block.Properties.copy(Blocks.STONE), (block, properties) -> new BlockItem(block, properties) {
        @Override
        public ItemStack getDefaultInstance() {
            ItemStack stack = super.getDefaultInstance();
            AetherIIDataComponents.set(stack, AetherIIDataComponents.MURAL_SECTION, MuralSection.defaultSection());
            return stack;
        }
    });

    // Melting Blocks
    public static final RegistryObject<Block> FROSTED_ICE = registerWithoutItem("frosted_ice", AetherFrostedIceBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType.builtInRegistryHolder().is(AetherIITags.EntityTypes.SPAWNING_ICE)).isRedstoneConductor(AetherIIBlockBuilders::never).noLootTable());
    public static final RegistryObject<Block> FROSTED_ARCTIC_ICE = registerWithoutItem("frosted_arctic_ice", AetherFrostedIceBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType.builtInRegistryHolder().is(AetherIITags.EntityTypes.SPAWNING_ICE)).isRedstoneConductor(AetherIIBlockBuilders::never).noLootTable());
    public static final RegistryObject<Block> UNSTABLE_OBSIDIAN = registerWithoutItem("unstable_obsidian", UnstableObsidianBlock::new, () ->  BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).randomTicks().requiresCorrectToolForDrops().strength(50.0F, 1200.0F).noLootTable());

    // Infected Guardian Tree
    // Guardian Wood
    public static final RegistryObject<RotatedPillarBlock> GUARDIAN_LOG = register("guardian_log", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> GUARDIAN_WOOD = register("guardian_wood", RotatedPillarBlock::new, logProperties(MapColor.COLOR_BROWN, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GUARDIAN_LOG = register("stripped_guardian_log", RotatedPillarBlock::new, logProperties(MapColor.WOOD, MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GUARDIAN_WOOD = register("stripped_guardian_wood", RotatedPillarBlock::new, logProperties(MapColor.WOOD, MapColor.WOOD));

    // Infected Wood
    public static final RegistryObject<RotatedPillarBlock> INFECTED_LOG = register("infected_log", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.SAND));
    public static final RegistryObject<RotatedPillarBlock> INFECTED_WOOD = register("infected_wood", RotatedPillarBlock::new, logProperties(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.SAND));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_INFECTED_LOG = register("stripped_infected_log", RotatedPillarBlock::new, logProperties(MapColor.SAND, MapColor.SAND));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_INFECTED_WOOD = register("stripped_infected_wood", RotatedPillarBlock::new, logProperties(MapColor.SAND, MapColor.SAND));

    // Guardian Slabs
    public static final RegistryObject<SlabBlock> GUARDIAN_LOG_SLAB = register("guardian_log_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.GUARDIAN_LOG.get()).mapColor(MapColor.WOOD));
    public static final RegistryObject<SlabBlock> GUARDIAN_WOOD_SLAB = register("guardian_wood_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.GUARDIAN_WOOD.get()).mapColor(MapColor.COLOR_BROWN));
    public static final RegistryObject<SlabBlock> STRIPPED_GUARDIAN_LOG_SLAB = register("stripped_guardian_log_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get()).mapColor(MapColor.WOOD));
    public static final RegistryObject<SlabBlock> STRIPPED_GUARDIAN_WOOD_SLAB = register("stripped_guardian_wood_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get()).mapColor(MapColor.WOOD));
    public static final RegistryObject<SlabBlock> INFECTED_LOG_SLAB = register("infected_log_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.GUARDIAN_LOG.get()).mapColor(MapColor.SAND));
    public static final RegistryObject<SlabBlock> INFECTED_WOOD_SLAB = register("infected_wood_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.GUARDIAN_WOOD.get()).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<SlabBlock> STRIPPED_INFECTED_LOG_SLAB = register("stripped_infected_log_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get()).mapColor(MapColor.SAND));
    public static final RegistryObject<SlabBlock> STRIPPED_INFECTED_WOOD_SLAB = register("stripped_infected_wood_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get()).mapColor(MapColor.SAND));

    // Guardian Trunks
    public static final RegistryObject<TrunkBlock> GUARDIAN_TRUNK = register("guardian_trunk", TrunkBlock::new, trunkProperties(MapColor.COLOR_BROWN));
    public static final RegistryObject<TrunkBlock> STRIPPED_GUARDIAN_TRUNK = register("stripped_guardian_trunk", TrunkBlock::new, trunkProperties(MapColor.WOOD));
    public static final RegistryObject<TrunkBlock> INFECTED_TRUNK = register("infected_trunk", TrunkBlock::new, trunkProperties(MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final RegistryObject<TrunkBlock> STRIPPED_INFECTED_TRUNK = register("stripped_infected_trunk", TrunkBlock::new, trunkProperties(MapColor.SAND));

    // Guardian Root Blocks
    public static final RegistryObject<Block> GUARDIAN_ROOTS = register("guardian_roots", Block::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.ROOTS));
    public static final RegistryObject<Block> UNSTABLE_GUARDIAN_ROOTS = register("unstable_guardian_roots", UnstableBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.ROOTS).ignitedByLava());
    public static final RegistryObject<Block> LUCENT_GUARDIAN_ROOTS = register("lucent_guardian_roots", Block::new, () -> Block.Properties.copy(GUARDIAN_ROOTS.get()).lightLevel((state) -> 7));
    public static final RegistryObject<Block> GUARDIAN_LAMP = register("guardian_lamp", Block::new, () -> Block.Properties.copy(GUARDIAN_ROOTS.get()).lightLevel((state) -> 10));

    // Undergrowth Blocks
    public static final RegistryObject<Block> UNDERGROWTH_LEAVES = register("undergrowth_leaves", () -> Block.Properties.of().strength(0.2F).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating(AetherIIBlockBuilders::never).isViewBlocking(AetherIIBlockBuilders::never).isRedstoneConductor(AetherIIBlockBuilders::never).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> UNDERGROWTH_VINES = register("undergrowth_vines", BottomedVineBlock::new, () -> Block.Properties.copy(Blocks.VINE).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final RegistryObject<Block> HANGING_UNDERGROWTH = register("hanging_undergrowth", HangingUndergrowthBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> HANGING_UNDERGROWTH_PLANT = registerWithoutItem("hanging_undergrowth_plant", HangingUndergrowthPlantBlock::new, () -> Block.Properties.copy(AetherIIBlocks.HANGING_UNDERGROWTH.get()));

    // Rotshroom Blocks
    public static final RegistryObject<Block> ROTSHROOM_BLOCK = register("rotshroom_block", Block::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD));
    public static final RegistryObject<SlabBlock> ROTSHROOM_SLAB = register("rotshroom_slab", SlabBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ROTSHROOM_BLOCK.get()).mapColor(MapColor.WOOD));
    public static final RegistryObject<RotatedPillarBlock> ROTSHROOM_STEM = register("rotshroom_stem", RotatedPillarBlock::new, () -> Block.Properties.copy(AetherIIBlocks.ROTSHROOM_BLOCK.get()).mapColor(MapColor.WOOL));
    public static final RegistryObject<Block> SHELF_ROTSHROOM_SLAB = register("shelf_rotshroom_slab", LargeShelfRotshroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.WOOD).ignitedByLava());
    public static final RegistryObject<Block> ROTSHROOM = register("rotshroom", RotshroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).noCollission().noOcclusion().randomTicks().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape().sound(SoundType.FUNGUS).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<FlowerPotBlock> POTTED_ROTSHROOM = registerWithoutItem("potted_rotshroom", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ROTSHROOM, properties), () -> Block.Properties.copy(Blocks.FLOWER_POT));
    public static final RegistryObject<Block> ROTSHROOM_CLUSTER = register("rotshroom_cluster", RotshroomClusterBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).strength(0.1F).noOcclusion().randomTicks().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape().sound(SoundType.FUNGUS).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> ROTSHROOM_TOADSTOOL = register("rotshroom_toadstool", RotshroomToadstoolBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).strength(0.1F).offsetType(BlockBehaviour.OffsetType.XYZ).dynamicShape().noOcclusion().randomTicks().sound(SoundType.FUNGUS).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> SHELF_ROTSHROOM = register("shelf_rotshroom", ShelfRotshroomBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIRT).noCollission().randomTicks().instabreak().sound(SoundType.FUNGUS).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY));
    public static final RegistryObject<Block> ROTGROWTH_VINES = register("rotgrowth_vines", BottomedVineBlock::new, () -> Block.Properties.copy(Blocks.VINE).sound(SoundType.HANGING_ROOTS).mapColor(MapColor.DIRT).strength(-1.0F, 3600000.0F).noLootTable());

    // Dungeon Furniture
    public static final RegistryObject<Block> PRAYER_CANDLE = register("prayer_candle", PrayerCandleBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD).lightLevel((state) -> state.getValue(PrayerCandleBlock.LIT) ? 12 : 0));
    public static final RegistryObject<Block> GUARDIAN_PEW = register("guardian_pew", GuardianPewBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD));
    public static final RegistryObject<Block> GUARDIAN_DONATION_BOX = register("guardian_donation_box", GuardianDonationBoxBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD));
    public static final RegistryObject<Block> ABANDONED_BAG = register("abandoned_bag", AbandonedBagBlock::new, () -> Block.Properties.copy(LIGHT_BLUE_CLOUDWOOL.get()), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> FUNGAL_CACHE = register("fungal_cache", FungalCacheBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD), AetherIIRenderedBlockItem::new);
    public static final RegistryObject<Block> SAGE_CHEST = register("sage_chest", SageChestBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD), AetherIIRenderedBlockItem::new);

    private static RegistryObject<Block> registerWithoutItem(String name, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, Block::new, properties);
    }

    private static <T extends Block> RegistryObject<T> registerWithoutItem(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return BLOCKS.register(name, () -> builder.apply(properties.get()));
    }

    private static RegistryObject<Block> register(String name, Supplier<Block.Properties> properties) {
        return register(name, Block::new, properties);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return register(name, builder, properties, BlockItem::new);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties, BiFunction<? super T, Item.Properties, ? extends Item> itemCreator) {
        RegistryObject<T> block = BLOCKS.register(name, () -> builder.apply(properties.get()));
        AetherIIItems.ITEMS.register(name, () -> itemCreator.apply(
            block.get(),
            new Item.Properties()
        ));
        return block;
    }

    private static BiFunction<Block, Item.Properties, StandingAndWallBlockItem> torchItem(Supplier<? extends RegistryObject<?>> wallTorch) {
        return standingAndWallBlockItem(wallTorch, Direction.DOWN);
    }

    private static BiFunction<Block, Item.Properties, StandingAndWallBlockItem> standingAndWallBlockItem(Supplier<? extends RegistryObject<?>> wallBlock, Direction attachmentDirection) {
        Objects.requireNonNull(wallBlock);
        return (standingBlock, properties) -> new StandingAndWallBlockItem(standingBlock, (Block) Objects.requireNonNull(wallBlock.get().get()), properties, attachmentDirection);
    }

    private static BiFunction<BedrollBlock, Item.Properties, BedItem> bedrollBlockItem() {
        return (bedrollBlock, properties) -> new BedItem(bedrollBlock, properties.stacksTo(4));
    }

    private static BiFunction<BedBlock, Item.Properties, BedItem> bedBlockItem() {
        return (bedBlock, properties) -> new AetherIIBedItem(bedBlock, properties.stacksTo(1));
    }

    private static BiFunction<StandingSignBlock, Item.Properties, SignItem> signItem(Supplier<? extends RegistryObject<? extends WallSignBlock>> wallSignBlock) {
        Objects.requireNonNull(wallSignBlock);
        return (standingSignBlock, properties) -> new SignItem(properties.stacksTo(16), standingSignBlock, Objects.requireNonNull(wallSignBlock.get().get()));
    }

    private static BiFunction<CeilingHangingSignBlock, Item.Properties, HangingSignItem> hangingSignItem(Supplier<? extends RegistryObject<? extends WallHangingSignBlock>> wallHangingSignBlock) {
        Objects.requireNonNull(wallHangingSignBlock);
        return (ceilingHangingSignBlock, properties) -> new HangingSignItem(ceilingHangingSignBlock, Objects.requireNonNull(wallHangingSignBlock.get().get()), properties.stacksTo(16));
    }

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, new ResourceLocation(AetherII.MODID, name));
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
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.AETHER_FERN.get()), AetherIIBlocks.POTTED_AETHER_FERN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SHIELD_FERN.get()), AetherIIBlocks.POTTED_SHIELD_FERN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.HESPEROSE.get()), AetherIIBlocks.POTTED_HESPEROSE);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.TARABLOOM.get()), AetherIIBlocks.POTTED_TARABLOOM);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.POASPROUT.get()), AetherIIBlocks.POTTED_POASPROUT);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.PLURACIAN.get()), AetherIIBlocks.POTTED_PLURACIAN);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.SATIVAL_SHOOT.get()), AetherIIBlocks.POTTED_SATIVAL_SHOOT);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.LILICHIME.get()), AetherIIBlocks.POTTED_LILICHIME);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLADE_POA.get()), AetherIIBlocks.POTTED_BLADE_POA);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.AECHOR_CUTTING.get()), AetherIIBlocks.POTTED_AECHOR_CUTTING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.CARRION_CUTTING.get()), AetherIIBlocks.POTTED_CARRION_CUTTING);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.AETHER_BUSH.get()), AetherIIBlocks.POTTED_AETHER_BUSH);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLUEBERRY_BUSH.get()), AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get()), AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.ORANGE_TREE.get()), AetherIIBlocks.POTTED_ORANGE_TREE);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(AetherIIBlocks.ROTSHROOM.get()), AetherIIBlocks.POTTED_ROTSHROOM);
    }

    public static void registerFlammability() {
        FlammabilityRegistrar fireBlockAccessor = ((FireBlock) Blocks.FIRE)::setFlammable;
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.MOSSY_WISPROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_DEPOSIT.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_TRUNK.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get(), 5, 5);
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
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SHORT_AETHER_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.MEDIUM_AETHER_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.TALL_AETHER_GRASS.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AETHER_FERN.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AETHER_BUSH.get(), 30, 60);
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
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_FLOORBOARDS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_HIGHLIGHT.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_BASE_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_TOP_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_BASE_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_TOP_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_BEAM.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.CLOUDWOOL.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.CLOUDWOOL_CARPET.get(), 60, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_BOOKSHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_BOOKSHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_BOOKSHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.SKYROOT_SHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.GREATROOT_SHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.WISPROOT_SHELF.get(), 30, 20);
        fireBlockAccessor.callSetFlammable(AetherIIBlocks.AMBEROOT_SHELF.get(), 30, 20);
    }

    @FunctionalInterface
    private interface FlammabilityRegistrar {
        void callSetFlammable(Block block, int encouragement, int flammability);
    }

    public static void registerWoodTypes() {
        WoodType.register(AetherIIWoodTypes.SKYROOT);
        WoodType.register(AetherIIWoodTypes.GREATROOT);
        WoodType.register(AetherIIWoodTypes.WISPROOT);
        WoodType.register(AetherIIWoodTypes.AMBEROOT);
    }

    public static void registerFluidInteractions() {
        FluidInteractionRegistry.FluidInteraction interaction = (level, currentPos, relativePos, currentState) -> {
            level.setBlockAndUpdate(currentPos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, currentPos, currentPos, AetherIIBlocks.GEL_BLOCK.get().defaultBlockState()));
            level.playSound(null, currentPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.8F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        currentPos.getX() + level.getRandom().nextDouble(),
                        currentPos.getY() + 1.2,
                        currentPos.getZ() + level.getRandom().nextDouble(),
                        8, 0.0, 0.0, 0.0, 0.0
                );
            }
        };

        FluidInteractionRegistry.addInteraction(AetherIIFluidTypes.ALKAHEST_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getFluidState(relativePos).getFluidType() == ForgeMod.WATER_TYPE.get(),
                interaction
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getFluidState(relativePos).getFluidType() == AetherIIFluidTypes.ALKAHEST_TYPE.get(),
                interaction
        ));
    }

    /**
     * Blocks able to be flattened with {@link ItemAbilities#AXE_STRIP}, and the equivalent result block.
     */
    public static final Map<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>> STRIPPABLES = new ImmutableMap.Builder<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>>()
            .put(() -> AetherIIBlocks.SKYROOT_LOG, () -> AetherIIBlocks.STRIPPED_SKYROOT_LOG)
            .put(() -> AetherIIBlocks.SKYROOT_WOOD, () -> AetherIIBlocks.STRIPPED_SKYROOT_WOOD)
            .put(() -> AetherIIBlocks.SKYROOT_TRUNK, () -> AetherIIBlocks.STRIPPED_SKYROOT_TRUNK)
            .put(() -> AetherIIBlocks.GREATROOT_LOG, () -> AetherIIBlocks.STRIPPED_GREATROOT_LOG)
            .put(() -> AetherIIBlocks.GREATROOT_WOOD, () -> AetherIIBlocks.STRIPPED_GREATROOT_WOOD)
            .put(() -> AetherIIBlocks.GREATROOT_TRUNK, () -> AetherIIBlocks.STRIPPED_GREATROOT_TRUNK)
            .put(() -> AetherIIBlocks.WISPROOT_LOG, () -> AetherIIBlocks.STRIPPED_WISPROOT_LOG)
            .put(() -> AetherIIBlocks.WISPROOT_WOOD, () -> AetherIIBlocks.STRIPPED_WISPROOT_WOOD)
            .put(() -> AetherIIBlocks.WISPROOT_TRUNK, () -> AetherIIBlocks.STRIPPED_WISPROOT_TRUNK)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_LOG, () -> AetherIIBlocks.WISPROOT_LOG)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_WOOD, () -> AetherIIBlocks.WISPROOT_WOOD)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_TRUNK, () -> AetherIIBlocks.WISPROOT_TRUNK)
            .put(() -> AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE, () -> AetherIIBlocks.WISPROOT_LOG)
            .put(() -> AetherIIBlocks.AMBEROOT_LOG, () -> AetherIIBlocks.STRIPPED_AMBEROOT_LOG)
            .put(() -> AetherIIBlocks.AMBEROOT_DEPOSIT, () -> AetherIIBlocks.STRIPPED_AMBEROOT_LOG)
            .put(() -> AetherIIBlocks.AMBEROOT_WOOD, () -> AetherIIBlocks.STRIPPED_AMBEROOT_WOOD)
            .put(() -> AetherIIBlocks.AMBEROOT_TRUNK, () -> AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK)
            .put(() -> AetherIIBlocks.GUARDIAN_LOG, () -> AetherIIBlocks.STRIPPED_GUARDIAN_LOG)
            .put(() -> AetherIIBlocks.GUARDIAN_LOG_SLAB, () -> AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB)
            .put(() -> AetherIIBlocks.GUARDIAN_WOOD, () -> AetherIIBlocks.STRIPPED_GUARDIAN_WOOD)
            .put(() -> AetherIIBlocks.GUARDIAN_WOOD_SLAB, () -> AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB)
            .put(() -> AetherIIBlocks.GUARDIAN_TRUNK, () -> AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK)
            .put(() -> AetherIIBlocks.INFECTED_LOG, () -> AetherIIBlocks.STRIPPED_INFECTED_LOG)
            .put(() -> AetherIIBlocks.INFECTED_LOG_SLAB, () -> AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB)
            .put(() -> AetherIIBlocks.INFECTED_WOOD, () -> AetherIIBlocks.STRIPPED_INFECTED_WOOD)
            .put(() -> AetherIIBlocks.INFECTED_WOOD_SLAB, () -> AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB)
            .put(() -> AetherIIBlocks.INFECTED_TRUNK, () -> AetherIIBlocks.STRIPPED_INFECTED_TRUNK)
            .build();

    /**
     * Blocks able to be flattened with {@link ItemAbilities#SHOVEL_FLATTEN}, and the equivalent result block.
     */
    public static final Map<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>> FLATTENABLES = new ImmutableMap.Builder<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>>()
            .put(() -> AetherIIBlocks.AETHER_GRASS_BLOCK, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .put(() -> AetherIIBlocks.AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .put(() -> AetherIIBlocks.COARSE_AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT_PATH)
            .build();

    /**
     * Blocks able to be tilled with {@link ItemAbilities#HOE_TILL}, and the equivalent result block.
     */
    public static final Map<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>> TILLABLES = new ImmutableMap.Builder<Supplier<RegistryObject<? extends Block>>, Supplier<RegistryObject<? extends Block>>>()
            .put(() -> AetherIIBlocks.AETHER_DIRT, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.AETHER_GRASS_BLOCK, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.AETHER_DIRT_PATH, () -> AetherIIBlocks.AETHER_FARMLAND)
            .put(() -> AetherIIBlocks.COARSE_AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT)
            .put(() -> AetherIIBlocks.MYCELIAL_AETHER_DIRT, () -> AetherIIBlocks.AETHER_DIRT)
            .build();

    public static BlockState registerBlockModifications(LevelAccessor levelAccessor, ToolAction toolAction, BlockPos blockPos, BlockState oldState, BlockState newState) {
        Map<Block, Block> strippables = STRIPPABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));
        Map<Block, Block> flattenables = FLATTENABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));
        Map<Block, Block> tillables = TILLABLES.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().get().get(), (e) -> e.getValue().get().get()));

        Block oldBlock = oldState.getBlock();
        if (toolAction == ToolActions.AXE_STRIP) {
            if (strippables.containsKey(oldBlock)) {
                newState = strippables.get(oldBlock).withPropertiesOf(oldState);
            }
        } else if (toolAction == ToolActions.SHOVEL_FLATTEN) {
            if (flattenables.containsKey(oldBlock)) {
                newState = flattenables.get(oldBlock).withPropertiesOf(oldState);
            }
        } else if (toolAction == ToolActions.HOE_TILL) {
            if (levelAccessor.getBlockState(blockPos.above()).isAir()) {
                if (tillables.containsKey(oldBlock)) {
                    newState = tillables.get(oldBlock).withPropertiesOf(oldState);
                }
            }
        }
        return newState;
    }

}
