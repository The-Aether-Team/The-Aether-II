package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Map;
import java.util.Optional;

public class AetherIITreeGrowers {
    public static final TreeGrower SKYROOT = new TreeGrower(
            "skyroot",
            0.2F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SHORT_SKYROOT),
            Optional.of(HolyIslesConfiguredFeatures.LARGE_SKYROOT),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYROOT = new TreeGrower(
            "irradiated_skyroot",
            0.2F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYROOT_IRRADIATED),
            Optional.of(HolyIslesConfiguredFeatures.LARGE_SKYROOT_IRRADIATED),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower SKYPLANE = new TreeGrower(
            "skyplane",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYPLANE),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYPLANE = new TreeGrower(
            "irradiated_skyplane",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYPLANE_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower SKYBIRCH = new TreeGrower(
            "skybirch",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYBIRCH),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYBIRCH = new TreeGrower(
            "irradiated_skybirch",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYBIRCH_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower SKYPINE = new TreeGrower(
            "skypine",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYPINE),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYPINE = new TreeGrower(
            "irradiated_skypine",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.SKYPINE_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower WISPROOT = new TreeGrower(
            "wisproot",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.WISPROOT),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_WISPROOT = new TreeGrower(
            "irradiated_wisproot",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.WISPROOT_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower WISPTOP = new TreeGrower(
            "wisptop",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.WISPTOP),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_WISPTOP = new TreeGrower(
            "irradiated_wisptop",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.WISPTOP_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower GREATROOT = new TreeGrower(
            "greatroot",
            Optional.of(HolyIslesConfiguredFeatures.GREATROOT),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_GREATROOT = new TreeGrower(
            "irradiated_greatroot",
            Optional.of(HolyIslesConfiguredFeatures.GREATROOT_IRRADIATED),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower GREATOAK = new TreeGrower(
            "greatoak",
            Optional.of(HolyIslesConfiguredFeatures.GREATOAK),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_GREATOAK = new TreeGrower(
            "irradiated_greatoak",
            Optional.of(HolyIslesConfiguredFeatures.GREATOAK_IRRADIATED),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower GREATBOA = new TreeGrower(
            "greatboa",
            Optional.of(HolyIslesConfiguredFeatures.GREATBOA),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_GREATBOA = new TreeGrower(
            "irradiated_greatboa",
            Optional.of(HolyIslesConfiguredFeatures.GREATBOA_IRRADIATED),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.TREES_AMBEROOT_SPARSE),
            Optional.empty()
    );

    public static final TreeGrower CRYSTALROOT = new TreeGrower(
            "amberoot",
            Optional.empty(),
            Optional.of(HolyIslesConfiguredFeatures.CRYSTALROOT),
            Optional.empty()
    );

    public static final Map<TreeGrower, TreeGrower> NORMAL_TO_IRRADIATED = Map.ofEntries(
            Map.entry(AetherIITreeGrowers.SKYROOT, AetherIITreeGrowers.IRRADIATED_SKYROOT),
            Map.entry(AetherIITreeGrowers.SKYPLANE, AetherIITreeGrowers.IRRADIATED_SKYPLANE),
            Map.entry(AetherIITreeGrowers.SKYBIRCH, AetherIITreeGrowers.IRRADIATED_SKYBIRCH),
            Map.entry(AetherIITreeGrowers.SKYPINE, AetherIITreeGrowers.IRRADIATED_SKYPINE),
            Map.entry(AetherIITreeGrowers.WISPROOT, AetherIITreeGrowers.IRRADIATED_WISPROOT),
            Map.entry(AetherIITreeGrowers.WISPTOP, AetherIITreeGrowers.IRRADIATED_WISPTOP),
            Map.entry(AetherIITreeGrowers.GREATROOT, AetherIITreeGrowers.IRRADIATED_GREATROOT),
            Map.entry(AetherIITreeGrowers.GREATOAK, AetherIITreeGrowers.IRRADIATED_GREATOAK),
            Map.entry(AetherIITreeGrowers.GREATBOA, AetherIITreeGrowers.IRRADIATED_GREATBOA)
    );
}