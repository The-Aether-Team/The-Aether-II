package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.DungeonEntranceConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

public class DungeonEntranceFeature extends Feature<DungeonEntranceConfiguration> {
    public DungeonEntranceFeature(Codec<DungeonEntranceConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<DungeonEntranceConfiguration> context) {
        WorldGenLevel level = context.level();
        int x = context.origin().getX();
        int y = context.origin().getY();
        int z = context.origin().getZ();

        BlockPos pos = new BlockPos(x, y, z);
        BlockPos posOffset = new BlockPos(x + context.config().xOffset(), y, z + context.config().zOffset());

        StructureTemplate entrance = level.getLevel().getStructureManager().getOrCreate(context.config().path());
        entrance.placeInWorld(level, posOffset, pos, getSettings(), context.random(), 3);
        return true;
    }

    protected StructurePlaceSettings getSettings() {
        StructurePlaceSettings placeSettings = new StructurePlaceSettings();
        placeSettings.setKnownShape(true);
        return placeSettings;
    }
}