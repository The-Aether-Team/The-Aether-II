package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Map;
import java.util.Optional;

public class AetherIITreeGrowers {
    public static final TreeGrower SKYROOT = new TreeGrower(
            "skyroot",
            0.2F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SHORT_SKYROOT),
            Optional.of(HighlandsConfiguredFeatures.LARGE_SKYROOT),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYROOT = new TreeGrower(
            "irradiated_skyroot",
            0.2F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYROOT_IRRADIATED),
            Optional.of(HighlandsConfiguredFeatures.LARGE_SKYROOT_IRRADIATED),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower SKYPLANE = new TreeGrower(
            "skyplane",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SHORT_SKYPLANE),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYPLANE = new TreeGrower(
            "irradiated_skyplane",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYPLANE_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower SKYBIRCH = new TreeGrower(
            "skybirch",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYBIRCH),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYBIRCH = new TreeGrower(
            "irradiated_skybirch",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYBIRCH_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower SKYPINE = new TreeGrower(
            "skypine",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYPINE),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_SKYPINE = new TreeGrower(
            "irradiated_skypine",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYPINE_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower WISPROOT = new TreeGrower(
            "wisproot",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPROOT),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_WISPROOT = new TreeGrower(
            "irradiated_wisproot",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPROOT_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower WISPTOP = new TreeGrower(
            "wisptop",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPTOP),
            Optional.empty()
    );
    public static final TreeGrower IRRADIATED_WISPTOP = new TreeGrower(
            "irradiated_wisptop",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPTOP_IRRADIATED),
            Optional.empty()
    );

    public static final TreeGrower GREATROOT = new TreeGrower(
            "greatroot",
            Optional.of(HighlandsConfiguredFeatures.GREATROOT),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower IRRADIATED_GREATROOT = new TreeGrower(
            "irradiated_greatroot",
            Optional.of(HighlandsConfiguredFeatures.GREATROOT_IRRADIATED),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATOAK = new TreeGrower(
            "greatoak",
            Optional.of(HighlandsConfiguredFeatures.GREATOAK),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower IRRADIATED_GREATOAK = new TreeGrower(
            "irradiated_greatoak",
            Optional.of(HighlandsConfiguredFeatures.GREATOAK_IRRADIATED),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATBOA = new TreeGrower(
            "greatboa",
            Optional.of(HighlandsConfiguredFeatures.GREATBOA),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower IRRADIATED_GREATBOA = new TreeGrower(
            "irradiated_greatboa",
            Optional.of(HighlandsConfiguredFeatures.GREATBOA_IRRADIATED),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.of(HighlandsConfiguredFeatures.TREES_AMBEROOT_SPARSE),
            Optional.empty(),
            Optional.empty());

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