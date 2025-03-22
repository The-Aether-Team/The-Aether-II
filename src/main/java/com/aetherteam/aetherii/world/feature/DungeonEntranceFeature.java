package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.DungeonEntranceConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class DungeonEntranceFeature extends Feature<DungeonEntranceConfiguration> {
    public DungeonEntranceFeature(Codec<DungeonEntranceConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DungeonEntranceConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        DungeonEntranceConfiguration config = context.config();

        StructureTemplate template = level.getLevel().getStructureManager().getOrCreate(config.path());
        template.placeInWorld(level, new BlockPos(pos.getX() - config.xOffset(), pos.getY(), pos.getZ() - config.zOffset()), pos, getSettings(), random, 3);

        return true;
    }

    protected StructurePlaceSettings getSettings() {
        StructurePlaceSettings placeSettings = new StructurePlaceSettings();
        placeSettings.setKnownShape(true);
        return placeSettings;
    }
}