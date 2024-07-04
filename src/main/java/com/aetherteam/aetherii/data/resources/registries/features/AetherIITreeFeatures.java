package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.tree.decorator.WisprootTreeDecorator;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.AmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.LargeAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.SingularAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatoakFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatrootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.skyroot.*;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisprootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisptopFoliagePlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class AetherIITreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT = AetherIIFeatureUtils.registerKey("skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT = AetherIIFeatureUtils.registerKey("large_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_SKYROOT = AetherIIFeatureUtils.registerKey("nest_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE = AetherIIFeatureUtils.registerKey("skyplane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYBIRCH = AetherIIFeatureUtils.registerKey("skybirch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPINE = AetherIIFeatureUtils.registerKey("skypine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT = AetherIIFeatureUtils.registerKey("wisproot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP = AetherIIFeatureUtils.registerKey("wisptop");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATROOT = AetherIIFeatureUtils.registerKey("greatroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK = AetherIIFeatureUtils.registerKey("greatoak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATBOA = AetherIIFeatureUtils.registerKey("greatboa");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBEROOT = AetherIIFeatureUtils.registerKey("amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGULAR_AMBEROOT = AetherIIFeatureUtils.registerKey("singular_amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_AMBEROOT = AetherIIFeatureUtils.registerKey("large_amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_IRRADIATED = AetherIIFeatureUtils.registerKey("skyroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT_IRRADIATED = AetherIIFeatureUtils.registerKey("large_skyroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE_IRRADIATED = AetherIIFeatureUtils.registerKey("skyplane_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYBIRCH_IRRADIATED = AetherIIFeatureUtils.registerKey("skybirch_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPINE_IRRADIATED = AetherIIFeatureUtils.registerKey("skypine_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT_IRRADIATED = AetherIIFeatureUtils.registerKey("wisproot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP_IRRADIATED = AetherIIFeatureUtils.registerKey("wisptop_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATROOT_IRRADIATED = AetherIIFeatureUtils.registerKey("greatroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK_IRRADIATED = AetherIIFeatureUtils.registerKey("greatoak_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATBOA_IRRADIATED = AetherIIFeatureUtils.registerKey("greatboa_irradiated");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        AetherIIFeatureUtils.register(context, SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, LARGE_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, NEST_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(12, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new NestSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYPLANE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 0), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYBIRCH, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(8, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYBIRCH_LEAVES.get().defaultBlockState()),
                        new SkybirchFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYPINE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 5, 0), BlockStateProvider.simple(AetherIIBlocks.SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, WISPROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());

        AetherIIFeatureUtils.register(context, WISPTOP, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());

        AetherIIFeatureUtils.register(context, GREATROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        AetherIIFeatureUtils.register(context, GREATOAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(13, 5, 6), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        AetherIIFeatureUtils.register(context, GREATBOA, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        AetherIIFeatureUtils.register(context, AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(5, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new AmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SINGULAR_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(6, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new SingularAmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, LARGE_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(8, 5, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new LargeAmberootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, LARGE_SKYROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYPLANE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYBIRCH_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(8, 3, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get().defaultBlockState()),
                        new SkybirchFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, SKYPINE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 5, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        AetherIIFeatureUtils.register(context, WISPROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());

        AetherIIFeatureUtils.register(context, WISPTOP_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());

        AetherIIFeatureUtils.register(context, GREATROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        AetherIIFeatureUtils.register(context, GREATOAK_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(13, 5, 6), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        AetherIIFeatureUtils.register(context, GREATBOA_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());
    }
}