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
import com.aetherteam.aetherii.blockentity.OutpostCampfireBlockEntity;
import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import com.aetherteam.aetherii.blockentity.SkyrootChestBlockEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsConfiguredFeatures;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.materials.RockItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.FireBlockAccessor;
import com.aetherteam.aetherii.world.tree.AetherIITreeGrowers;
import com.aetherteam.nitrogen.item.block.EntityBlockItem;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    // Portal
    public static final DeferredBlock<AetherPortalBlock> AETHER_PORTAL = BLOCKS.register("aether_portal", () -> new AetherPortalBlock(Block.Properties.of().noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(AetherIIBlocks::lightLevel11).pushReaction(PushReaction.BLOCK).forceSolidOn().noLootTable()));

    // Dirt
    public static final DeferredBlock<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> ENCHANTED_AETHER_GRASS_BLOCK = register("enchanted_aether_grass_block", () -> new EnchantedAetherGrassBlock(Block.Properties.of().mapColor(MapColor.GOLD).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> AETHER_DIRT_PATH = register("aether_dirt_path", () -> new AetherDirtPathBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.65F).sound(SoundType.GRASS).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> AETHER_DIRT = register("aether_dirt", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_AETHER_DIRT = register("coarse_aether_dirt", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> AETHER_FARMLAND = register("aether_farmland", () -> new AetherFarmBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> SHIMMERING_SILT = register("shimmering_silt", () -> new ColoredFallingBlock(new ColorRGBA(8360341), Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.5F).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND).isViewBlocking(AetherIIBlocks::always).isSuffocating(AetherIIBlocks::always)));

    // Underground
    public static final DeferredBlock<Block> HOLYSTONE = register("holystone", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UNDERSHALE = register("undershale", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> AGIOSITE = register("agiosite", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<HalfTransparentBlock> CRUDE_SCATTERGLASS = register("crude_scatterglass", () -> new HalfTransparentBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> SKY_ROOTS = register("sky_roots", () -> new AetherHangingRootsBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).replaceable().noCollission().instabreak().sound(SoundType.HANGING_ROOTS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    // Highfields
    public static final DeferredBlock<Block> QUICKSOIL = register("quicksoil", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(1.1F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> MOSSY_HOLYSTONE = register("mossy_holystone", () -> new Block(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get())));
    public static final DeferredBlock<Block> BRYALINN_MOSS_BLOCK = register("bryalinn_moss_block", () -> new AetherMossBlock(HighlandsConfiguredFeatures.BRYALINN_MOSS_FLOOR, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BRYALINN_MOSS_CARPET = register("bryalinn_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BRYALINN_MOSS_VINES = register("bryalinn_moss_vines", () -> new BottomedVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BRYALINN_MOSS_FLOWERS = register("bryalinn_moss_flowers", () -> new MossFlowersBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TANGLED_BRANCHES = register("tangled_branches", () -> new Block(Block.Properties.of().noOcclusion().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.WOOD)));

    // Magnetic
    public static final DeferredBlock<Block> FERROSITE_SAND = register("ferrosite_sand", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> FERROSITE = register("ferrosite", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> RUSTED_FERROSITE = register("rusted_ferrosite", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MAGNETIC_SHROOM = register("magnetic_shroom", () -> new MushroomBlock(TreeFeatures.HUGE_BROWN_MUSHROOM, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel(light -> 5).hasPostProcess(AetherIIBlocks::always).pushReaction(PushReaction.DESTROY)));

    // Arctic
    public static final DeferredBlock<Block> ARCTIC_SNOW_BLOCK = register("arctic_snow_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));
    public static final DeferredBlock<Block> ARCTIC_SNOW = register("arctic_snow", () -> new SnowLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).sound(SoundType.SNOW).requiresCorrectToolForDrops().isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> ARCTIC_ICE = register("arctic_ice", () -> new IceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType.is(AetherIITags.Entities.SPAWNING_ICE)).isRedstoneConductor(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> ARCTIC_PACKED_ICE = register("arctic_packed_ice", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).friction(0.98F).strength(0.5F).sound(SoundType.GLASS)));
    public static final DeferredBlock<Block> ICESTONE = register("icestone", () -> new IcestoneBlock(Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LARGE_ARCTIC_ICE_CRYSTAL = register("large_arctic_ice_crystal", () -> new IceCrystalBlock(8.0F, 2.0F, Block.Properties.of().mapColor(MapColor.ICE).forceSolidOn().noOcclusion().sound(SoundType.GLASS).strength(0.5F).pushReaction(PushReaction.DESTROY).randomTicks()));
    public static final DeferredBlock<Block> MEDIUM_ARCTIC_ICE_CRYSTAL = register("medium_arctic_ice_crystal", () -> new IceCrystalBlock(5.0F, 3.0F, Block.Properties.ofLegacyCopy(LARGE_ARCTIC_ICE_CRYSTAL.get())));
    public static final DeferredBlock<Block> SMALL_ARCTIC_ICE_CRYSTAL = register("small_arctic_ice_crystal", () -> new IceCrystalBlock(3.0F, 5.0F, Block.Properties.ofLegacyCopy(LARGE_ARCTIC_ICE_CRYSTAL.get())));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_BLOCK = register("shayelinn_moss_block", () -> new AetherMossBlock(HighlandsConfiguredFeatures.SHAYELINN_MOSS_FLOOR, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_CARPET = register("shayelinn_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SHAYELINN_MOSS_VINES = register("shayelinn_moss_vines", () -> new BottomedVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    // Irradiated
    public static final DeferredBlock<Block> IRRADIATED_HOLYSTONE = register("irradiated_holystone", () -> new Block(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get())));
    public static final DeferredBlock<Block> TARAHESP_AMBRELINN_MOSS_BLOCK = register("tarahesp_ambrelinn_moss_block", () -> new AetherMossBlock(HighlandsConfiguredFeatures.SHAYELINN_MOSS_FLOOR, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TARAHESP_AMBRELINN_MOSS_CARPET = register("tarahesp_ambrelinn_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TARAHESP_AMBRELINN_MOSS_VINES = register("tarahesp_ambrelinn_moss_vines", () -> new BottomedVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).replaceable().noCollission().randomTicks().strength(0.1F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TARAHESP_AMBRELINN_MOSS_FLOWERS = register("tarahesp_ambrelinn_moss_flowers", () -> new MossFlowersBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    // Ores
    public static final DeferredBlock<Block> HOLYSTONE_QUARTZ_ORE = register("holystone_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> AMBROSIUM_ORE = register("ambrosium_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops().lightLevel(AetherIIBlocks::lightLevel8)));
    public static final DeferredBlock<Block> ZANITE_ORE = register("zanite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GLINT_ORE = register("glint_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ARKENIUM_ORE = register("arkenium_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GRAVITITE_ORE = register("gravitite_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UNDERSHALE_AMBROSIUM_ORE = register("undershale_ambrosium_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Block.Properties.ofFullCopy(AMBROSIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).lightLevel(AetherIIBlocks::lightLevel8)));
    public static final DeferredBlock<Block> UNDERSHALE_ZANITE_ORE = register("undershale_zanite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.ofFullCopy(ZANITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> UNDERSHALE_GLINT_ORE = register("undershale_glint_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.ofFullCopy(GLINT_ORE.get()).mapColor(MapColor.WOOL).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> UNDERSHALE_ARKENIUM_ORE = register("undershale_arkenium_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.ofFullCopy(ARKENIUM_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> UNDERSHALE_GRAVITITE_ORE = register("undershale_gravitite_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.ofFullCopy(GRAVITITE_ORE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> CORROBONITE_ORE = register("corrobonite_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CORROBONITE_CLUSTER = register("corrobonite_cluster", () -> new CorroboniteClusterBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(3.0F, 3.0F).noOcclusion().noCollission()));

    // Aerclouds
    public static final DeferredBlock<Block> COLD_AERCLOUD = register("cold_aercloud", () -> new AercloudBlock(aercloudProperties(MapColor.SNOW)));
    public static final DeferredBlock<Block> BLUE_AERCLOUD = register("blue_aercloud", () -> new BlueAercloudBlock(aercloudProperties(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> GOLDEN_AERCLOUD = register("golden_aercloud", () -> new AercloudBlock(aercloudProperties(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> GREEN_AERCLOUD = register("green_aercloud", () -> new GreenAercloudBlock(aercloudProperties(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<Block> PURPLE_AERCLOUD = register("purple_aercloud", () -> new PurpleAercloudBlock(aercloudProperties(MapColor.COLOR_MAGENTA)));
    public static final DeferredBlock<Block> STORM_AERCLOUD = register("storm_aercloud", () -> new AercloudBlock(aercloudProperties(MapColor.DEEPSLATE)));

    // Moa Nest
    public static final DeferredBlock<Block> WOVEN_SKYROOT_STICKS = register("woven_skyroot_sticks", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.75F).sound(SoundType.GRASS)));

    // Logs
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> log(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_WOOD = register("skyroot_wood", () -> log(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = register("stripped_skyroot_log", () -> log(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = register("stripped_skyroot_wood", () -> log(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> GREATROOT_LOG = register("greatroot_log", () -> log(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> GREATROOT_WOOD = register("greatroot_wood", () -> log(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GREATROOT_LOG = register("stripped_greatroot_log", () -> log(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GREATROOT_WOOD = register("stripped_greatroot_wood", () -> log(MapColor.TERRACOTTA_BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> WISPROOT_LOG = register("wisproot_log", () -> log(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<FacingPillarBlock> MOSSY_WISPROOT_LOG = register("mossy_wisproot_log", () -> new FacingPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<RotatedPillarBlock> WISPROOT_WOOD = register("wisproot_wood", () -> log(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WISPROOT_LOG = register("stripped_wisproot_log", () -> log(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WISPROOT_WOOD = register("stripped_wisproot_wood", () -> log(MapColor.QUARTZ, MapColor.QUARTZ));
    public static final DeferredBlock<RotatedPillarBlock> AMBEROOT_LOG = register("amberoot_log", () -> log(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> AMBEROOT_WOOD = register("amberoot_wood", () -> log(MapColor.COLOR_BROWN, MapColor.TERRACOTTA_LIGHT_GRAY));

    // Leaf Pile
    public static final DeferredBlock<Block> SKYROOT_LEAF_PILE = register("skyroot_leaf_pile" , () -> leafPile(MapColor.GRASS));
    public static final DeferredBlock<Block> SKYPLANE_LEAF_PILE = register("skyplane_leaf_pile" , () -> leafPile(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> SKYBIRCH_LEAF_PILE = register("skybirch_leaf_pile" , () -> leafPile(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> SKYPINE_LEAF_PILE = register("skypine_leaf_pile" , () -> leafPile(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> WISPROOT_LEAF_PILE = register("wisproot_leaf_pile" , () -> leafPile(MapColor.DIAMOND));
    public static final DeferredBlock<Block> WISPTOP_LEAF_PILE = register("wisptop_leaf_pile" , () -> leafPile(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATROOT_LEAF_PILE = register("greatroot_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_LIGHT_GREEN));
    public static final DeferredBlock<Block> GREATOAK_LEAF_PILE = register("greatoak_leaf_pile" , () -> leafPile(MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<Block> GREATBOA_LEAF_PILE = register("greatboa_leaf_pile" , () -> leafPile(MapColor.COLOR_BLUE));
    public static final DeferredBlock<Block> AMBEROOT_LEAF_PILE = register("amberoot_leaf_pile" , () -> leafPile(MapColor.GOLD));
    public static final DeferredBlock<Block> IRRADIATED_SKYROOT_LEAF_PILE = register("irradiated_skyroot_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPLANE_LEAF_PILE = register("irradiated_skyplane_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYBIRCH_LEAF_PILE = register("irradiated_skybirch_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_SKYPINE_LEAF_PILE = register("irradiated_skypine_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPROOT_LEAF_PILE = register("irradiated_wisproot_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_WISPTOP_LEAF_PILE = register("irradiated_wisptop_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATROOT_LEAF_PILE = register("irradiated_greatroot_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATOAK_LEAF_PILE = register("irradiated_greatoak_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredBlock<Block> IRRADIATED_GREATBOA_LEAF_PILE = register("irradiated_greatboa_leaf_pile" , () -> leafPile(MapColor.TERRACOTTA_YELLOW));

    // Leaves
    public static final DeferredBlock<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> leaves(MapColor.GRASS, AetherIIParticleTypes.SKYROOT_LEAVES, AetherIIBlocks.SKYROOT_LEAF_PILE));
    public static final DeferredBlock<Block> SKYPLANE_LEAVES = register("skyplane_leaves", () -> blocksLightleaves(MapColor.COLOR_BLUE, AetherIIParticleTypes.SKYPLANE_LEAVES, AetherIIBlocks.SKYPLANE_LEAF_PILE));
    public static final DeferredBlock<Block> SKYBIRCH_LEAVES = register("skybirch_leaves", () -> leaves(MapColor.COLOR_LIGHT_BLUE, AetherIIParticleTypes.SKYBIRCH_LEAVES, AetherIIBlocks.SKYBIRCH_LEAF_PILE));
    public static final DeferredBlock<Block> SKYPINE_LEAVES = register("skypine_leaves", () -> leaves(MapColor.COLOR_MAGENTA, AetherIIParticleTypes.SKYPINE_LEAVES, AetherIIBlocks.SKYPINE_LEAF_PILE));
    public static final DeferredBlock<Block> WISPROOT_LEAVES = register("wisproot_leaves", () -> leaves(MapColor.DIAMOND, AetherIIParticleTypes.WISPROOT_LEAVES, AetherIIBlocks.WISPROOT_LEAF_PILE));
    public static final DeferredBlock<Block> WISPTOP_LEAVES = register("wisptop_leaves", () -> leaves(MapColor.COLOR_MAGENTA, AetherIIParticleTypes.WISPTOP_LEAVES, AetherIIBlocks.WISPTOP_LEAF_PILE));
    public static final DeferredBlock<Block> GREATROOT_LEAVES = register("greatroot_leaves", () -> blocksLightleaves(MapColor.TERRACOTTA_LIGHT_GREEN, AetherIIParticleTypes.GREATROOT_LEAVES, AetherIIBlocks.GREATROOT_LEAF_PILE));
    public static final DeferredBlock<Block> GREATOAK_LEAVES = register("greatoak_leaves", () -> blocksLightleaves(MapColor.COLOR_MAGENTA, AetherIIParticleTypes.GREATOAK_LEAVES, AetherIIBlocks.GREATOAK_LEAF_PILE));
    public static final DeferredBlock<Block> GREATBOA_LEAVES = register("greatboa_leaves", () -> blocksLightleaves(MapColor.COLOR_BLUE, AetherIIParticleTypes.GREATBOA_LEAVES, AetherIIBlocks.GREATBOA_LEAF_PILE));
    public static final DeferredBlock<Block> AMBEROOT_LEAVES = register("amberoot_leaves", () -> leaves(MapColor.GOLD, AetherIIParticleTypes.AMBEROOT_LEAVES, AetherIIBlocks.AMBEROOT_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_SKYROOT_LEAVES = register("irradiated_skyroot_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_SKYPLANE_LEAVES = register("irradiated_skyplane_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_SKYBIRCH_LEAVES = register("irradiated_skybirch_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_SKYPINE_LEAVES = register("irradiated_skypine_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_WISPROOT_LEAVES = register("irradiated_wisproot_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_WISPTOP_LEAVES = register("irradiated_wisptop_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_GREATROOT_LEAVES = register("irradiated_greatroot_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_GREATOAK_LEAVES = register("irradiated_greatoak_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE));
    public static final DeferredBlock<Block> IRRADIATED_GREATBOA_LEAVES = register("irradiated_greatboa_leaves", () -> irradiatedLeaves(MapColor.TERRACOTTA_YELLOW, AetherIIParticleTypes.IRRADIATED_LEAVES, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE));

    // Saplings
    public static final DeferredBlock<SaplingBlock> SKYROOT_SAPLING = register("skyroot_sapling", () -> new SaplingBlock(AetherIITreeGrowers.SKYROOT, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> SKYPLANE_SAPLING = register("skyplane_sapling", () -> new SaplingBlock(AetherIITreeGrowers.SKYPLANE, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> SKYBIRCH_SAPLING = register("skybirch_sapling", () -> new SaplingBlock(AetherIITreeGrowers.SKYBIRCH, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> SKYPINE_SAPLING = register("skypine_sapling", () -> new SaplingBlock(AetherIITreeGrowers.SKYPINE, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> WISPROOT_SAPLING = register("wisproot_sapling", () -> new SaplingBlock(AetherIITreeGrowers.WISPROOT, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> WISPTOP_SAPLING = register("wisptop_sapling", () -> new SaplingBlock(AetherIITreeGrowers.WISPTOP, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> GREATROOT_SAPLING = register("greatroot_sapling", () -> new SaplingBlock(AetherIITreeGrowers.GREATROOT, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> GREATOAK_SAPLING = register("greatoak_sapling", () -> new SaplingBlock(AetherIITreeGrowers.GREATOAK, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> GREATBOA_SAPLING = register("greatboa_sapling", () -> new SaplingBlock(AetherIITreeGrowers.GREATBOA, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> AMBEROOT_SAPLING = register("amberoot_sapling", () -> new SaplingBlock(AetherIITreeGrowers.AMBEROOT, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    // Potted Saplings
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYROOT_SAPLING = BLOCKS.register("potted_skyroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYPLANE_SAPLING = BLOCKS.register("potted_skyplane_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPLANE_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYBIRCH_SAPLING = BLOCKS.register("potted_skybirch_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYBIRCH_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYPINE_SAPLING = BLOCKS.register("potted_skypine_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYPINE_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WISPROOT_SAPLING = BLOCKS.register("potted_wisproot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPTOP_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WISPTOP_SAPLING = BLOCKS.register("potted_wisptop_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WISPTOP_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATROOT_SAPLING = BLOCKS.register("potted_greatroot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATROOT_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATOAK_SAPLING = BLOCKS.register("potted_greatoak_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATOAK_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREATBOA_SAPLING = BLOCKS.register("potted_greatboa_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREATBOA_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AMBEROOT_SAPLING = BLOCKS.register("potted_amberoot_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AMBEROOT_SAPLING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    // Grasses
    public static final DeferredBlock<Block> AETHER_SHORT_GRASS = register("aether_short_grass", () -> new AetherTallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> AETHER_MEDIUM_GRASS = register("aether_medium_grass", () -> new AetherTallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> AETHER_LONG_GRASS = register("aether_long_grass", () -> new AetherTallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> HIGHLAND_FERN = register("highland_fern", () -> new AetherTallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));
    public static final DeferredBlock<Block> SHIELD_FERN = register("shield_fern", () -> new AetherTallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).hasPostProcess(AetherIIBlocks::always)));

    // Flowers
    public static final DeferredBlock<Block> HESPEROSE = register("hesperose", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 4, Block.Properties.ofFullCopy(Blocks.DANDELION)));
    public static final DeferredBlock<Block> TARABLOOM = register("tarabloom", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> POASPROUT = register("poasprout", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> LILICHIME = register("lilichime", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> PLURACIAN = register("pluracian", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> SATIVAL_SHOOT = register("satival_shoot", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> BLADE_POA = register("blade_poa", () -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 12, Block.Properties.ofFullCopy(Blocks.DANDELION))); //todo mob effects
    public static final DeferredBlock<Block> AECHOR_CUTTING = register("aechor_cutting", () -> new AechorCuttingBlock(Block.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    // Potted Flowers
    public static final DeferredBlock<FlowerPotBlock> POTTED_HIGHLAND_FERN = BLOCKS.register("potted_highland_fern", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HIGHLAND_FERN, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SHIELD_FERN = BLOCKS.register("potted_shield_fern", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHIELD_FERN, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_HESPEROSE = BLOCKS.register("potted_hesperose", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HESPEROSE, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_TARABLOOM = BLOCKS.register("potted_tarabloom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TARABLOOM, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_POASPROUT = BLOCKS.register("potted_poasprout", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, POASPROUT, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PLURACIAN = BLOCKS.register("potted_pluracian", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PLURACIAN, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SATIVAL_SHOOT = BLOCKS.register("potted_satival_shoot", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SATIVAL_SHOOT, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_LILICHIME = BLOCKS.register("potted_lilichime", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LILICHIME, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLADE_POA = BLOCKS.register("potted_blade_poa", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLADE_POA, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AECHOR_CUTTING = BLOCKS.register("potted_aechor_cutting", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AECHOR_CUTTING, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    // Bushes
    public static final DeferredBlock<Block> HIGHLANDS_BUSH = register("highlands_bush", () -> new HighlandsBushBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH = register("blueberry_bush", () -> new BlueberryBushBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::spawnOnLeaves).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH_STEM = register("blueberry_bush_stem", () -> new BlueberryBushStemBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).strength(0.2F).sound(SoundType.GRASS).noCollission()));

    // Potted Bushes
    public static final DeferredBlock<FlowerPotBlock> POTTED_HIGHLANDS_BUSH = BLOCKS.register("potted_highlands_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HIGHLANDS_BUSH, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUEBERRY_BUSH = BLOCKS.register("potted_blueberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUEBERRY_BUSH_STEM = BLOCKS.register("potted_blueberry_bush_stem", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUEBERRY_BUSH_STEM, Block.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    // Orange Tree
    public static final DeferredBlock<Block> ORANGE_TREE = register("orange_tree", () -> new OrangeTreeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().strength(0.2F).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    // Potted Orange Tree
    public static final DeferredBlock<FlowerPotBlock> POTTED_ORANGE_TREE = BLOCKS.register("potted_orange_tree", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    // Valkyrie Sprout
    public static final DeferredBlock<Block> VALKYRIE_SPROUT = register("valkyrie_sprout", () -> new ValkyrieSproutBlock(Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).instabreak().noCollission().offsetType(BlockBehaviour.OffsetType.XZ)));

    // Brettl
    public static final DeferredBlock<Block> BRETTL_PLANT = BLOCKS.register("brettl_plant", () -> new BrettlPlantBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BRETTL_PLANT_TIP = BLOCKS.register("brettl_plant_tip", () -> new BrettlPlantTipBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    // Ground Decoration
    public static final DeferredBlock<Block> SKYROOT_TWIG = register("skyroot_twig", () -> new TwigBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> HOLYSTONE_ROCK = register("holystone_rock", () -> new RockBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).noOcclusion().noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY)));

    // Skyroot Planks
    public static final DeferredBlock<Block> SKYROOT_PLANKS = register("skyroot_planks", () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> SKYROOT_STAIRS = register("skyroot_stairs", () -> new StairBlock(SKYROOT_PLANKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.SKYROOT_PLANKS.get())));
    public static final DeferredBlock<SlabBlock> SKYROOT_SLAB = register("skyroot_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F)));
    public static final DeferredBlock<FenceBlock> SKYROOT_FENCE = register("skyroot_fence", () -> new FenceBlock(Block.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> SKYROOT_FENCE_GATE = register("skyroot_fence_gate", () -> new FenceGateBlock(AetherIIWoodTypes.SKYROOT, Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<DoorBlock> SKYROOT_DOOR = register("skyroot_door", () -> new DoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> SKYROOT_TRAPDOOR = register("skyroot_trapdoor", () -> new TrapDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<ButtonBlock> SKYROOT_BUTTON = register("skyroot_button", () -> new ButtonBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, 30, Block.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> SKYROOT_PRESSURE_PLATE = register("skyroot_pressure_plate", () -> new PressurePlateBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    // Skyroot Decorative Blocks
    public static final DeferredBlock<Block> SKYROOT_FLOORBOARDS = register("skyroot_floorboards", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SKYROOT_HIGHLIGHT = register("skyroot_highlight", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SKYROOT_SHINGLES = register("skyroot_shingles", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SKYROOT_SMALL_SHINGLES = register("skyroot_small_shingles", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SKYROOT_BASE_PLANKS = register("skyroot_base_planks", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SKYROOT_TOP_PLANKS = register("skyroot_top_planks", () -> new Block(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_BASE_BEAM = register("skyroot_base_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_TOP_BEAM = register("skyroot_top_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> SKYROOT_BEAM = register("skyroot_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(SKYROOT_PLANKS.get())));
    public static final DeferredBlock<DoorBlock> SECRET_SKYROOT_DOOR = register("secret_skyroot_door", () -> new SecretDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, Block.Properties.of().mapColor(SKYROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava()));
    public static final DeferredBlock<TrapDoorBlock> SECRET_SKYROOT_TRAPDOOR = register("secret_skyroot_trapdoor", () -> new SecretTrapDoorBlock(AetherIIWoodTypes.SKYROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

    // Greatroot Planks
    public static final DeferredBlock<Block> GREATROOT_PLANKS = register("greatroot_planks", () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> GREATROOT_STAIRS = register("greatroot_stairs", () -> new StairBlock(GREATROOT_PLANKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.GREATROOT_PLANKS.get())));
    public static final DeferredBlock<SlabBlock> GREATROOT_SLAB = register("greatroot_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.GREATROOT_PLANKS.get()).strength(2.0F, 3.0F)));
    public static final DeferredBlock<FenceBlock> GREATROOT_FENCE = register("greatroot_fence", () -> new FenceBlock(Block.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> GREATROOT_FENCE_GATE = register("greatroot_fence_gate", () -> new FenceGateBlock(AetherIIWoodTypes.GREATROOT, Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<DoorBlock> GREATROOT_DOOR = register("greatroot_door", () -> new DoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> GREATROOT_TRAPDOOR = register("greatroot_trapdoor", () -> new TrapDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<ButtonBlock> GREATROOT_BUTTON = register("greatroot_button", () -> new ButtonBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, 30, Block.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> GREATROOT_PRESSURE_PLATE = register("greatroot_pressure_plate", () -> new PressurePlateBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    // Greatroot Decorative Blocks
    public static final DeferredBlock<Block> GREATROOT_FLOORBOARDS = register("greatroot_floorboards", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<Block> GREATROOT_HIGHLIGHT = register("greatroot_highlight", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<Block> GREATROOT_SHINGLES = register("greatroot_shingles", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<Block> GREATROOT_SMALL_SHINGLES = register("greatroot_small_shingles", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<Block> GREATROOT_BASE_PLANKS = register("greatroot_base_planks", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<Block> GREATROOT_TOP_PLANKS = register("greatroot_top_planks", () -> new Block(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_BASE_BEAM = register("greatroot_base_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_TOP_BEAM = register("greatroot_top_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> GREATROOT_BEAM = register("greatroot_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(GREATROOT_PLANKS.get())));
    public static final DeferredBlock<DoorBlock> SECRET_GREATROOT_DOOR = register("secret_greatroot_door", () -> new SecretDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<TrapDoorBlock> SECRET_GREATROOT_TRAPDOOR = register("secret_greatroot_trapdoor", () -> new SecretTrapDoorBlock(AetherIIWoodTypes.GREATROOT_BLOCK_SET, Block.Properties.of().mapColor(GREATROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava()));

    // Wisproot Planks
    public static final DeferredBlock<Block> WISPROOT_PLANKS = register("wisproot_planks", () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<StairBlock> WISPROOT_STAIRS = register("wisproot_stairs", () -> new StairBlock(WISPROOT_PLANKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.WISPROOT_PLANKS.get())));
    public static final DeferredBlock<SlabBlock> WISPROOT_SLAB = register("wisproot_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.WISPROOT_PLANKS.get()).strength(2.0F, 3.0F)));
    public static final DeferredBlock<FenceBlock> WISPROOT_FENCE = register("wisproot_fence", () -> new FenceBlock(Block.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final DeferredBlock<FenceGateBlock> WISPROOT_FENCE_GATE = register("wisproot_fence_gate", () -> new FenceGateBlock(AetherIIWoodTypes.WISPROOT, Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final DeferredBlock<DoorBlock> WISPROOT_DOOR = register("wisproot_door", () -> new DoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> WISPROOT_TRAPDOOR = register("wisproot_trapdoor", () -> new TrapDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final DeferredBlock<ButtonBlock> WISPROOT_BUTTON = register("wisproot_button", () -> new ButtonBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, 30, Block.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> WISPROOT_PRESSURE_PLATE = register("wisproot_pressure_plate", () -> new PressurePlateBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    // Wisproot Decorative Blocks
    public static final DeferredBlock<Block> WISPROOT_FLOORBOARDS = register("wisproot_floorboards", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<Block> WISPROOT_HIGHLIGHT = register("wisproot_highlight", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<Block> WISPROOT_SHINGLES = register("wisproot_shingles", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<Block> WISPROOT_SMALL_SHINGLES = register("wisproot_small_shingles", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<Block> WISPROOT_BASE_PLANKS = register("wisproot_base_planks", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<Block> WISPROOT_TOP_PLANKS = register("wisproot_top_planks", () -> new Block(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_BASE_BEAM = register("wisproot_base_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_TOP_BEAM = register("wisproot_top_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<FacingPillarBlock> WISPROOT_BEAM = register("wisproot_beam", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(WISPROOT_PLANKS.get())));
    public static final DeferredBlock<DoorBlock> SECRET_WISPROOT_DOOR = register("secret_wisproot_door", () -> new SecretDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<TrapDoorBlock> SECRET_WISPROOT_TRAPDOOR = register("secret_wisproot_trapdoor", () -> new SecretTrapDoorBlock(AetherIIWoodTypes.WISPROOT_BLOCK_SET, Block.Properties.of().mapColor(WISPROOT_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).isValidSpawn(AetherIIBlocks::never).ignitedByLava()));

    // Holystone
    public static final DeferredBlock<StairBlock> HOLYSTONE_STAIRS = register("holystone_stairs", () -> new StairBlock(HOLYSTONE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get())));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_SLAB = register("holystone_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> HOLYSTONE_WALL = register("holystone_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE.get()).forceSolidOn()));
    public static final DeferredBlock<ButtonBlock> HOLYSTONE_BUTTON = register("holystone_button", () -> new ButtonBlock(BlockSetType.STONE, 20, Block.Properties.ofFullCopy(Blocks.STONE_BUTTON)));
    public static final DeferredBlock<PressurePlateBlock> HOLYSTONE_PRESSURE_PLATE = register("holystone_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F)));

    // Mossy Holystone
    public static final DeferredBlock<StairBlock> MOSSY_HOLYSTONE_STAIRS = register("mossy_holystone_stairs", () -> new StairBlock(MOSSY_HOLYSTONE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get())));
    public static final DeferredBlock<SlabBlock> MOSSY_HOLYSTONE_SLAB = register("mossy_holystone_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> MOSSY_HOLYSTONE_WALL = register("mossy_holystone_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.MOSSY_HOLYSTONE.get()).forceSolidOn()));

    // Irradiated Holystone
    public static final DeferredBlock<StairBlock> IRRADIATED_HOLYSTONE_STAIRS = register("irradiated_holystone_stairs", () -> new StairBlock(IRRADIATED_HOLYSTONE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get())));
    public static final DeferredBlock<SlabBlock> IRRADIATED_HOLYSTONE_SLAB = register("irradiated_holystone_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> IRRADIATED_HOLYSTONE_WALL = register("irradiated_holystone_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.IRRADIATED_HOLYSTONE.get()).forceSolidOn()));

    // Holystone Bricks
    public static final DeferredBlock<Block> HOLYSTONE_BRICKS = register("holystone_bricks", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<StairBlock> HOLYSTONE_BRICK_STAIRS = register("holystone_brick_stairs", () -> new StairBlock(HOLYSTONE_BRICKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_BRICK_SLAB = register("holystone_brick_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> HOLYSTONE_BRICK_WALL = register("holystone_brick_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.HOLYSTONE_BRICKS.get()).forceSolidOn()));

    // Holystone Decorative Blocks
    public static final DeferredBlock<Block> HOLYSTONE_FLAGSTONES = register("holystone_flagstones", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> HOLYSTONE_HEADSTONE = register("holystone_headstone", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> HOLYSTONE_KEYSTONE = register("holystone_keystone", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> HOLYSTONE_BASE_BRICKS = register("holystone_base_bricks", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> HOLYSTONE_CAPSTONE_BRICKS = register("holystone_capstone_bricks", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_BASE_PILLAR = register("holystone_base_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_CAPSTONE_PILLAR = register("holystone_capstone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> HOLYSTONE_PILLAR = register("holystone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));

    // Faded Holystone Bricks
    public static final DeferredBlock<Block> FADED_HOLYSTONE_BRICKS = register("faded_holystone_bricks", () -> new Block(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<StairBlock> FADED_HOLYSTONE_BRICK_STAIRS = register("faded_holystone_brick_stairs", () -> new StairBlock(FADED_HOLYSTONE_BRICKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> FADED_HOLYSTONE_BRICK_SLAB = register("faded_holystone_brick_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> FADED_HOLYSTONE_BRICK_WALL = register("faded_holystone_brick_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get()).forceSolidOn()));

    // Faded Holystone Decorative Blocks
    public static final DeferredBlock<Block> FADED_HOLYSTONE_FLAGSTONES = register("faded_holystone_flagstones", () -> new Block(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_HEADSTONE = register("faded_holystone_headstone", () -> new Block(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_KEYSTONE = register("faded_holystone_keystone", () -> new Block(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_BASE_BRICKS = register("faded_holystone_base_bricks", () -> new Block(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<Block> FADED_HOLYSTONE_CAPSTONE_BRICKS = register("faded_holystone_capstone_bricks", () -> new Block(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_BASE_PILLAR = register("faded_holystone_base_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_CAPSTONE_PILLAR = register("faded_holystone_capstone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> FADED_HOLYSTONE_PILLAR = register("faded_holystone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(FADED_HOLYSTONE_BRICKS.get())));

    // Undershale
    public static final DeferredBlock<StairBlock> UNDERSHALE_STAIRS = register("undershale_stairs", () -> new StairBlock(UNDERSHALE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get())));
    public static final DeferredBlock<SlabBlock> UNDERSHALE_SLAB = register("undershale_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get())));
    public static final DeferredBlock<WallBlock> UNDERSHALE_WALL = register("undershale_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.UNDERSHALE.get()).forceSolidOn()));

    // Agiosite
    public static final DeferredBlock<StairBlock> AGIOSITE_STAIRS = register("agiosite_stairs", () -> new StairBlock(AGIOSITE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get())));
    public static final DeferredBlock<SlabBlock> AGIOSITE_SLAB = register("agiosite_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> AGIOSITE_WALL = register("agiosite_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE.get()).forceSolidOn()));

    // Agiosite Bricks
    public static final DeferredBlock<Block> AGIOSITE_BRICKS = register("agiosite_bricks", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<StairBlock> AGIOSITE_BRICK_STAIRS = register("agiosite_brick_stairs", () -> new StairBlock(AGIOSITE_BRICKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> AGIOSITE_BRICK_SLAB = register("agiosite_brick_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> AGIOSITE_BRICK_WALL = register("agiosite_brick_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.AGIOSITE_BRICKS.get()).forceSolidOn()));

    // Agiosite Decorative Blocks
    public static final DeferredBlock<Block> AGIOSITE_FLAGSTONES = register("agiosite_flagstones", () -> new Block(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<Block> AGIOSITE_KEYSTONE = register("agiosite_keystone", () -> new Block(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<Block> AGIOSITE_BASE_BRICKS = register("agiosite_base_bricks", () -> new Block(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<Block> AGIOSITE_CAPSTONE_BRICKS = register("agiosite_capstone_bricks", () -> new Block(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_BASE_PILLAR = register("agiosite_base_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_CAPSTONE_PILLAR = register("agiosite_capstone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> AGIOSITE_PILLAR = register("agiosite_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(AGIOSITE_BRICKS.get())));

    // Icestone
    public static final DeferredBlock<StairBlock> ICESTONE_STAIRS = register("icestone_stairs", () -> new IcestoneStairsBlock(ICESTONE.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get())));
    public static final DeferredBlock<SlabBlock> ICESTONE_SLAB = register("icestone_slab", () -> new IcestoneSlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get()).strength(0.5F, 6.0F)));
    public static final DeferredBlock<WallBlock> ICESTONE_WALL = register("icestone_wall", () -> new IcestoneWallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE.get()).forceSolidOn()));

    // Icestone Bricks
    public static final DeferredBlock<Block> ICESTONE_BRICKS = register("icestone_bricks", () -> new Block(Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<StairBlock> ICESTONE_BRICK_STAIRS = register("icestone_bricks_stairs", () -> new StairBlock(ICESTONE_BRICKS.get().defaultBlockState(), Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get())));
    public static final DeferredBlock<SlabBlock> ICESTONE_BRICK_SLAB = register("icestone_bricks_slab", () -> new SlabBlock(Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final DeferredBlock<WallBlock> ICESTONE_BRICK_WALL = register("icestone_bricks_wall", () -> new WallBlock(Block.Properties.ofFullCopy(AetherIIBlocks.ICESTONE_BRICKS.get()).forceSolidOn()));

    // Icestone Decorative Blocks
    public static final DeferredBlock<Block> ICESTONE_FLAGSTONES = register("icestone_flagstones", () -> new Block(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<Block> ICESTONE_KEYSTONE = register("icestone_keystone", () -> new Block(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<Block> ICESTONE_BASE_BRICKS = register("icestone_base_bricks", () -> new Block(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<Block> ICESTONE_CAPSTONE_BRICKS = register("icestone_capstone_bricks", () -> new Block(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_BASE_PILLAR = register("icestone_base_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_CAPSTONE_PILLAR = register("icestone_capstone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));
    public static final DeferredBlock<FacingPillarBlock> ICESTONE_PILLAR = register("icestone_pillar", () -> new FacingPillarBlock(Block.Properties.ofFullCopy(ICESTONE_BRICKS.get())));

    // Glass
    public static final DeferredBlock<TransparentBlock> QUICKSOIL_GLASS = register("quicksoil_glass", () -> new QuicksoilGlassBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<TransparentBlock> SKYROOT_FRAMED_QUICKSOIL_GLASS = register("skyroot_framed_quicksoil_glass", () -> new QuicksoilGlassBlock(Block.Properties.ofFullCopy(QUICKSOIL_GLASS.get())));
    public static final DeferredBlock<TransparentBlock> ARKENIUM_FRAMED_QUICKSOIL_GLASS = register("arkenium_framed_quicksoil_glass", () -> new QuicksoilGlassBlock(Block.Properties.ofFullCopy(QUICKSOIL_GLASS.get())));
    public static final DeferredBlock<HalfTransparentBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS = register("skyroot_framed_crude_scatterglass", () -> new HalfTransparentBlock(Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS.get())));
    public static final DeferredBlock<HalfTransparentBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS = register("arkenium_framed_crude_scatterglass", () -> new HalfTransparentBlock(Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS.get())));
    public static final DeferredBlock<TransparentBlock> SCATTERGLASS = register("scatterglass", () -> new ScatterglassBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops().isValidSpawn(AetherIIBlocks::never).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<TransparentBlock> SKYROOT_FRAMED_SCATTERGLASS = register("skyroot_framed_scatterglass", () -> new ScatterglassBlock(Block.Properties.ofFullCopy(SCATTERGLASS.get())));
    public static final DeferredBlock<TransparentBlock> ARKENIUM_FRAMED_SCATTERGLASS = register("arkenium_framed_scatterglass", () -> new ScatterglassBlock(Block.Properties.ofFullCopy(SCATTERGLASS.get())));

    // Glass Panes
    public static final DeferredBlock<IronBarsBlock> QUICKSOIL_GLASS_PANE = register("quicksoil_glass_pane", () -> new IronBarsBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherIIBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<IronBarsBlock> SKYROOT_FRAMED_QUICKSOIL_GLASS_PANE = register("skyroot_framed_quicksoil_glass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(QUICKSOIL_GLASS_PANE.get())));
    public static final DeferredBlock<IronBarsBlock> ARKENIUM_FRAMED_QUICKSOIL_GLASS_PANE = register("arkenium_framed_quicksoil_glass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(QUICKSOIL_GLASS_PANE.get())));
    public static final DeferredBlock<IronBarsBlock> CRUDE_SCATTERGLASS_PANE = register("crude_scatterglass_pane", () -> new CrudeScatterglassPane(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<IronBarsBlock> SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE = register("skyroot_framed_crude_scatterglass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS_PANE.get())));
    public static final DeferredBlock<IronBarsBlock> ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE = register("arkenium_framed_crude_scatterglass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(CRUDE_SCATTERGLASS_PANE.get())));
    public static final DeferredBlock<IronBarsBlock> SCATTERGLASS_PANE = register("scatterglass_pane", () -> new IronBarsBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(0.2F).sound(SoundType.GLASS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<IronBarsBlock> SKYROOT_FRAMED_SCATTERGLASS_PANE = register("skyroot_framed_scatterglass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(SCATTERGLASS_PANE.get())));
    public static final DeferredBlock<IronBarsBlock> ARKENIUM_FRAMED_SCATTERGLASS_PANE = register("arkenium_framed_scatterglass_pane", () -> new IronBarsBlock(Block.Properties.ofFullCopy(SCATTERGLASS_PANE.get())));

    // Wool
    public static final DeferredBlock<Block> CLOUDWOOL = register("cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final DeferredBlock<Block> WHITE_CLOUDWOOL = register("white_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final DeferredBlock<Block> ORANGE_CLOUDWOOL = register("orange_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.ORANGE_WOOL)));
    public static final DeferredBlock<Block> MAGENTA_CLOUDWOOL = register("magenta_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.MAGENTA_WOOL)));
    public static final DeferredBlock<Block> LIGHT_BLUE_CLOUDWOOL = register("light_blue_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL)));
    public static final DeferredBlock<Block> YELLOW_CLOUDWOOL = register("yellow_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.YELLOW_WOOL)));
    public static final DeferredBlock<Block> LIME_CLOUDWOOL = register("lime_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.LIME_WOOL)));
    public static final DeferredBlock<Block> PINK_CLOUDWOOL = register("pink_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.PINK_WOOL)));
    public static final DeferredBlock<Block> GRAY_CLOUDWOOL = register("gray_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.GRAY_WOOL)));
    public static final DeferredBlock<Block> LIGHT_GRAY_CLOUDWOOL = register("light_gray_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL)));
    public static final DeferredBlock<Block> CYAN_CLOUDWOOL = register("cyan_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.CYAN_WOOL)));
    public static final DeferredBlock<Block> PURPLE_CLOUDWOOL = register("purple_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.PURPLE_WOOL)));
    public static final DeferredBlock<Block> BLUE_CLOUDWOOL = register("blue_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.BLUE_WOOL)));
    public static final DeferredBlock<Block> BROWN_CLOUDWOOL = register("brown_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.BROWN_WOOL)));
    public static final DeferredBlock<Block> GREEN_CLOUDWOOL = register("green_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.GREEN_WOOL)));
    public static final DeferredBlock<Block> RED_CLOUDWOOL = register("red_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.RED_WOOL)));
    public static final DeferredBlock<Block> BLACK_CLOUDWOOL = register("black_cloudwool", () -> new Block(Block.Properties.ofFullCopy(Blocks.BLACK_WOOL)));

    // Carpet
    public static final DeferredBlock<CarpetBlock> CLOUDWOOL_CARPET = register("cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.WHITE_CARPET)));
    public static final DeferredBlock<CarpetBlock> WHITE_CLOUDWOOL_CARPET = register("white_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.WHITE_CARPET)));
    public static final DeferredBlock<CarpetBlock> ORANGE_CLOUDWOOL_CARPET = register("orange_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.ORANGE_CARPET)));
    public static final DeferredBlock<CarpetBlock> MAGENTA_CLOUDWOOL_CARPET = register("magenta_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.MAGENTA_CARPET)));
    public static final DeferredBlock<CarpetBlock> LIGHT_BLUE_CLOUDWOOL_CARPET = register("light_blue_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.LIGHT_BLUE_CARPET)));
    public static final DeferredBlock<CarpetBlock> YELLOW_CLOUDWOOL_CARPET = register("yellow_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.YELLOW_CARPET)));
    public static final DeferredBlock<CarpetBlock> LIME_CLOUDWOOL_CARPET = register("lime_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.LIME_CARPET)));
    public static final DeferredBlock<CarpetBlock> PINK_CLOUDWOOL_CARPET = register("pink_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.PINK_CARPET)));
    public static final DeferredBlock<CarpetBlock> GRAY_CLOUDWOOL_CARPET = register("gray_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.GRAY_CARPET)));
    public static final DeferredBlock<CarpetBlock> LIGHT_GRAY_CLOUDWOOL_CARPET = register("light_gray_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CARPET)));
    public static final DeferredBlock<CarpetBlock> CYAN_CLOUDWOOL_CARPET = register("cyan_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.CYAN_CARPET)));
    public static final DeferredBlock<CarpetBlock> PURPLE_CLOUDWOOL_CARPET = register("purple_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.PURPLE_CARPET)));
    public static final DeferredBlock<CarpetBlock> BLUE_CLOUDWOOL_CARPET = register("blue_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.BLUE_CARPET)));
    public static final DeferredBlock<CarpetBlock> BROWN_CLOUDWOOL_CARPET = register("brown_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.BROWN_CARPET)));
    public static final DeferredBlock<CarpetBlock> GREEN_CLOUDWOOL_CARPET = register("green_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.GREEN_CARPET)));
    public static final DeferredBlock<CarpetBlock> RED_CLOUDWOOL_CARPET = register("red_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.RED_CARPET)));
    public static final DeferredBlock<CarpetBlock> BLACK_CLOUDWOOL_CARPET = register("black_cloudwool_carpet", () -> new CarpetBlock(Block.Properties.ofFullCopy(Blocks.BLACK_CARPET)));

    // Arkenium Blocks
    public static final DeferredBlock<DoorBlock> ARKENIUM_DOOR = register("arkenium_door", () -> new DoorBlock(BlockSetType.IRON, Block.Properties.ofFullCopy(Blocks.IRON_DOOR)));
    public static final DeferredBlock<TrapDoorBlock> ARKENIUM_TRAPDOOR = register("arkenium_trapdoor", () -> new TrapDoorBlock(BlockSetType.IRON, Block.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR)));

    // Mineral Blocks
    public static final DeferredBlock<Block> AMBROSIUM_BLOCK = register("ambrosium_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ZANITE_BLOCK = register("zanite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ARKENIUM_BLOCK = register("arkenium_block", () -> new Block(Block.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> GRAVITITE_BLOCK = register("gravitite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    // Utility
    public static final DeferredBlock<Block> AMBROSIUM_TORCH = register("ambrosium_torch", () -> new AmbrosiumTorchBlock(Block.Properties.ofFullCopy(Blocks.TORCH)));
    public static final DeferredBlock<Block> AMBROSIUM_WALL_TORCH = BLOCKS.register("ambrosium_wall_torch", () -> new AmbrosiumWallTorchBlock(Block.Properties.ofFullCopy(Blocks.WALL_TORCH)));
    public static final DeferredBlock<Block> SKYROOT_CRAFTING_TABLE = register("skyroot_crafting_table", () -> new SkyrootCraftingTableBlock(Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)));
    public static final DeferredBlock<Block> HOLYSTONE_FURNACE = register("holystone_furnace", () -> new HolystoneFurnaceBlock(Block.Properties.ofFullCopy(Blocks.FURNACE)));
    public static final DeferredBlock<Block> ALTAR = register("altar", () -> new AltarBlock(Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion()));
    public static final DeferredBlock<Block> ARTISANS_BENCH = register("artisans_bench", () -> new ArtisansBenchBlock(Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion()));
    public static final DeferredBlock<Block> ARKENIUM_FORGE = register("arkenium_forge", () -> new ArkeniumForgeBlock(Block.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion()));
    public static final DeferredBlock<Block> SKYROOT_CHEST = register("skyroot_chest", () -> new SkyrootChestBlock(Block.Properties.ofFullCopy(Blocks.CHEST), AetherIIBlockEntityTypes.SKYROOT_CHEST::get));
    public static final DeferredBlock<LadderBlock> SKYROOT_LADDER = register("skyroot_ladder", () -> new LadderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LADDER).strength(0.4F).sound(SoundType.LADDER).noOcclusion()));
    public static final DeferredBlock<BedBlock> SKYROOT_BED = register("skyroot_bed", () -> new SkyrootBedBlock(DyeColor.WHITE, Block.Properties.ofFullCopy(Blocks.WHITE_BED)));

    public static final DeferredBlock<StandingSignBlock> SKYROOT_SIGN = register("skyroot_sign", () -> new StandingSignBlock(AetherIIWoodTypes.SKYROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> SKYROOT_WALL_SIGN = BLOCKS.register("skyroot_wall_sign", () -> new WallSignBlock(AetherIIWoodTypes.SKYROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(SKYROOT_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> SKYROOT_HANGING_SIGN = register("skyroot_hanging_sign", () -> new CeilingHangingSignBlock(AetherIIWoodTypes.SKYROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
    public static final DeferredBlock<WallHangingSignBlock> SKYROOT_WALL_HANGING_SIGN = BLOCKS.register("skyroot_wall_hanging_sign", () -> new WallHangingSignBlock(AetherIIWoodTypes.SKYROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));

    public static final DeferredBlock<StandingSignBlock> GREATROOT_SIGN = register("greatroot_sign", () -> new StandingSignBlock(AetherIIWoodTypes.GREATROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> GREATROOT_WALL_SIGN = BLOCKS.register("greatroot_wall_sign", () -> new WallSignBlock(AetherIIWoodTypes.GREATROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(GREATROOT_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> GREATROOT_HANGING_SIGN = register("greatroot_hanging_sign", () -> new CeilingHangingSignBlock(AetherIIWoodTypes.GREATROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
    public static final DeferredBlock<WallHangingSignBlock> GREATROOT_WALL_HANGING_SIGN = BLOCKS.register("greatroot_wall_hanging_sign", () -> new WallHangingSignBlock(AetherIIWoodTypes.GREATROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));

    public static final DeferredBlock<StandingSignBlock> WISPROOT_SIGN = register("wisproot_sign", () -> new StandingSignBlock(AetherIIWoodTypes.WISPROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> WISPROOT_WALL_SIGN = BLOCKS.register("wisproot_wall_sign", () -> new WallSignBlock(AetherIIWoodTypes.WISPROOT, Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(GREATROOT_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> WISPROOT_HANGING_SIGN = register("wisproot_hanging_sign", () -> new CeilingHangingSignBlock(AetherIIWoodTypes.WISPROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
    public static final DeferredBlock<WallHangingSignBlock> WISPROOT_WALL_HANGING_SIGN = BLOCKS.register("wisproot_wall_hanging_sign", () -> new WallHangingSignBlock(AetherIIWoodTypes.WISPROOT, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));

    // Moa Egg
    public static final DeferredBlock<Block> MOA_EGG = register("moa_egg", () -> new MoaEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.5F).sound(SoundType.METAL).noOcclusion()));

    // Bookshelves
    public static final DeferredBlock<Block> SKYROOT_BOOKSHELF = register("skyroot_bookshelf", () -> new BookshelfBlock(Block.Properties.ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<Block> HOLYSTONE_BOOKSHELF = register("holystone_bookshelf", () -> new BookshelfBlock(Block.Properties.ofFullCopy(HOLYSTONE_BRICKS.get())));

    // Furniture
    public static final DeferredBlock<OutpostCampfireBlock> OUTPOST_CAMPFIRE = register("outpost_campfire", () -> new OutpostCampfireBlock(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(15.0F, 1200.0F).noOcclusion().pushReaction(PushReaction.BLOCK)));

    private static <T extends Block> DeferredBlock<T> baseRegister(String name, Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> register = BLOCKS.register(name, block);
        AetherIIItems.ITEMS.register(name, item.apply(register));
        return register;
    }

    private static <B extends Block> DeferredBlock<B> register(String name, Supplier<B> block) {
        return baseRegister(name, block, AetherIIBlocks::registerBlockItem);
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final DeferredBlock<T> deferredBlock) {
        return () -> {
            DeferredBlock<T> block = Objects.requireNonNull(deferredBlock);
            if (block == HOLYSTONE_ROCK) {
                return new RockItem(HOLYSTONE_ROCK.get(), new Item.Properties());
            } else if (block == AMBROSIUM_TORCH) {
                return new StandingAndWallBlockItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN);
            } else if (block == SKYROOT_CHEST) {
                return new EntityBlockItem(block.get(), SkyrootChestBlockEntity::new, new Item.Properties());
            } else if (block == SKYROOT_BED) {
                return new EntityBlockItem(block.get(), SkyrootBedBlockEntity::new, new Item.Properties().stacksTo(1));
            } else if (block == SKYROOT_SIGN) {
                return new SignItem(new Item.Properties().stacksTo(16), SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get());
            } else if (block == SKYROOT_HANGING_SIGN) {
                return new HangingSignItem(SKYROOT_HANGING_SIGN.get(), SKYROOT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16));
            } else if (block == GREATROOT_SIGN) {
                return new SignItem(new Item.Properties().stacksTo(16), GREATROOT_SIGN.get(), GREATROOT_WALL_SIGN.get());
            } else if (block == GREATROOT_HANGING_SIGN) {
                return new HangingSignItem(GREATROOT_HANGING_SIGN.get(), GREATROOT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16));
            } else if (block == WISPROOT_SIGN) {
                return new SignItem(new Item.Properties().stacksTo(16), WISPROOT_SIGN.get(), WISPROOT_WALL_SIGN.get());
            } else if (block == WISPROOT_HANGING_SIGN) {
                return new HangingSignItem(WISPROOT_HANGING_SIGN.get(), WISPROOT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16));
            } else if (block == OUTPOST_CAMPFIRE) {
                return new EntityBlockItem(block.get(), OutpostCampfireBlockEntity::new, new Item.Properties());
            } else {
                return new BlockItem(block.get(), new Item.Properties());
            }
        };
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