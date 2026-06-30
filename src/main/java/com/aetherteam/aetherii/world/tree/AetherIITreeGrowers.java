package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Map;

public class AetherIITreeGrowers {
    public static final AbstractTreeGrower SKYROOT = new AetherIIMegaTreeGrower(
            0.2F,
            HolyIslesConfiguredFeatures.SHORT_SKYROOT,
            HolyIslesConfiguredFeatures.LARGE_SKYROOT
    );
    public static final AbstractTreeGrower IRRADIATED_SKYROOT = new AetherIIMegaTreeGrower(
            0.2F,
            HolyIslesConfiguredFeatures.SKYROOT_IRRADIATED,
            HolyIslesConfiguredFeatures.LARGE_SKYROOT_IRRADIATED
    );

    public static final AbstractTreeGrower SKYPLANE = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYPLANE);
    public static final AbstractTreeGrower IRRADIATED_SKYPLANE = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYPLANE_IRRADIATED);
    public static final AbstractTreeGrower SKYBIRCH = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYBIRCH);
    public static final AbstractTreeGrower IRRADIATED_SKYBIRCH = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYBIRCH_IRRADIATED);
    public static final AbstractTreeGrower SKYPINE = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYPINE);
    public static final AbstractTreeGrower IRRADIATED_SKYPINE = new AetherIITreeGrower(HolyIslesConfiguredFeatures.SKYPINE_IRRADIATED);
    public static final AbstractTreeGrower WISPROOT = new AetherIITreeGrower(HolyIslesConfiguredFeatures.WISPROOT);
    public static final AbstractTreeGrower IRRADIATED_WISPROOT = new AetherIITreeGrower(HolyIslesConfiguredFeatures.WISPROOT_IRRADIATED);
    public static final AbstractTreeGrower WISPTOP = new AetherIITreeGrower(HolyIslesConfiguredFeatures.WISPTOP);
    public static final AbstractTreeGrower IRRADIATED_WISPTOP = new AetherIITreeGrower(HolyIslesConfiguredFeatures.WISPTOP_IRRADIATED);
    public static final AbstractTreeGrower GREATROOT = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATROOT);
    public static final AbstractTreeGrower IRRADIATED_GREATROOT = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATROOT_IRRADIATED);
    public static final AbstractTreeGrower GREATOAK = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATOAK);
    public static final AbstractTreeGrower IRRADIATED_GREATOAK = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATOAK_IRRADIATED);
    public static final AbstractTreeGrower GREATBOA = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATBOA);
    public static final AbstractTreeGrower IRRADIATED_GREATBOA = new AetherIITreeGrower(HolyIslesConfiguredFeatures.GREATBOA_IRRADIATED);
    public static final AbstractTreeGrower AMBEROOT = new AetherIITreeGrower(HolyIslesConfiguredFeatures.TREES_AMBEROOT_SPARSE);

    public static final Map<AbstractTreeGrower, AbstractTreeGrower> NORMAL_TO_IRRADIATED = Map.ofEntries(
            Map.entry(SKYROOT, IRRADIATED_SKYROOT),
            Map.entry(SKYPLANE, IRRADIATED_SKYPLANE),
            Map.entry(SKYBIRCH, IRRADIATED_SKYBIRCH),
            Map.entry(SKYPINE, IRRADIATED_SKYPINE),
            Map.entry(WISPROOT, IRRADIATED_WISPROOT),
            Map.entry(WISPTOP, IRRADIATED_WISPTOP),
            Map.entry(GREATROOT, IRRADIATED_GREATROOT),
            Map.entry(GREATOAK, IRRADIATED_GREATOAK),
            Map.entry(GREATBOA, IRRADIATED_GREATBOA)
    );

    private static class AetherIITreeGrower extends AbstractTreeGrower {
        private final ResourceKey<ConfiguredFeature<?, ?>> feature;

        AetherIITreeGrower(ResourceKey<ConfiguredFeature<?, ?>> feature) {
            this.feature = feature;
        }

        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return this.feature;
        }
    }

    private static class AetherIIMegaTreeGrower extends AbstractMegaTreeGrower {
        private final float megaChance;
        private final ResourceKey<ConfiguredFeature<?, ?>> feature;
        private final ResourceKey<ConfiguredFeature<?, ?>> megaFeature;

        AetherIIMegaTreeGrower(float megaChance, ResourceKey<ConfiguredFeature<?, ?>> feature, ResourceKey<ConfiguredFeature<?, ?>> megaFeature) {
            this.megaChance = megaChance;
            this.feature = feature;
            this.megaFeature = megaFeature;
        }

        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return this.feature;
        }

        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
            return random.nextFloat() < this.megaChance ? this.megaFeature : null;
        }
    }
}
