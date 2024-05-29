package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIITreeFeatures;
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

    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.AMBEROOT),
            Optional.empty()
    );
}