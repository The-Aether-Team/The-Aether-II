package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIITreeFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AetherIITreeGrowers {

    public static final TreeGrower SKYROOT = new TreeGrower(
            "skyroot",
            0.2F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.SKYROOT),
            Optional.of(AetherIITreeFeatures.LARGE_SKYROOT),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower WISPROOT = new TreeGrower(
            "wisproot",
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.WISPROOT),
            Optional.empty()
    );

    public static final TreeGrower WISPTOP = new TreeGrower(
            "wisptop",
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.WISPTOP),
            Optional.empty()
    );

    public static final TreeGrower GREATROOT = new TreeGrower(
            "greatroot",
            Optional.of(AetherIITreeFeatures.GREATROOT),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATOAK = new TreeGrower(
            "greatoak",
            Optional.of(AetherIITreeFeatures.GREATOAK),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower GREATBOA = new TreeGrower(
            "greatboa",
            Optional.of(AetherIITreeFeatures.GREATBOA),
            Optional.empty(),
            Optional.empty());

    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.AMBEROOT),
            Optional.empty()
    );
}