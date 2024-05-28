package com.aetherteam.aetherii.world.tree;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIITreeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AetherIITreeGrowers {
    public static final TreeGrower AMBEROOT = new TreeGrower(
            "amberoot",
            Optional.empty(),
            Optional.of(AetherIITreeFeatures.AMBEROOT),
            Optional.empty()
    );
}