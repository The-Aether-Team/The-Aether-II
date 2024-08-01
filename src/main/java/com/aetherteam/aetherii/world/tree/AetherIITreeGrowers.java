package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.features.HighlandsConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

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

    public static final TreeGrower SKYPLANE = new TreeGrower(
            "skyplane",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SHORT_SKYPLANE),
            Optional.empty()
    );

    public static final TreeGrower SKYBIRCH = new TreeGrower(
            "skybirch",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYBIRCH),
            Optional.empty()
    );

    public static final TreeGrower SKYPINE = new TreeGrower(
            "skypine",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.SKYPINE),
            Optional.empty()
    );

    public static final TreeGrower WISPROOT = new TreeGrower(
            "wisproot",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPROOT),
            Optional.empty()
    );

    public static final TreeGrower WISPTOP = new TreeGrower(
            "wisptop",
            Optional.empty(),
            Optional.of(HighlandsConfiguredFeatures.WISPTOP),
            Optional.empty()
    );

    public static final TreeGrower GREATROOT = new TreeGrower(
            "greatroot",
            Optional.of(HighlandsConfiguredFeatures.GREATROOT),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATOAK = new TreeGrower(
            "greatoak",
            Optional.of(HighlandsConfiguredFeatures.GREATOAK),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATBOA = new TreeGrower(
            "greatboa",
            Optional.of(HighlandsConfiguredFeatures.GREATBOA),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.of(HighlandsConfiguredFeatures.TREES_AMBEROOT_SPARSE),
            Optional.empty(),
            Optional.empty());
}