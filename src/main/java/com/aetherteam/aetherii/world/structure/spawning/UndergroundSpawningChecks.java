package com.aetherteam.aetherii.world.structure.spawning;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class UndergroundSpawningChecks {

    public static int findStartingHeight(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, WorldgenRandom random, RandomState randomState, StructureTemplateManager templateManager, ResourceLocation checkedTemplate, int aboveBottom, int belowTop, int additionalThickness) {
        int minX = chunkPos.getMinBlockX() - 1;
        int minZ = chunkPos.getMinBlockZ() - 1;
        int maxX = chunkPos.getMaxBlockX() + 1;
        int maxZ = chunkPos.getMaxBlockZ() + 1;
        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                generator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                generator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
        };
        int roomHeight = checkTemplateHeight(templateManager, checkedTemplate, random);
        int height = heightAccessor.getMinBuildHeight();
        int maxHeight = heightAccessor.getMaxBuildHeight() - belowTop;
        int thickness = roomHeight + additionalThickness;
        int currentThickness = 0;
        for (int y = height + aboveBottom; y <= maxHeight; y++) {
            if (checkEachCornerAtY(columns, y)) {
                ++currentThickness;
            } else {
                if (currentThickness > thickness) {
                    thickness = currentThickness;
                    height = y;
                }
                currentThickness = 0;
            }
        }
        int offset = (thickness + roomHeight) / 2;
        height -= offset;
        return height;
    }

    public static int checkTemplateHeight(StructureTemplateManager manager, ResourceLocation roomName, WorldgenRandom random) {
        StructureTemplate template = manager.getOrCreate(roomName);
        return template.getSize(Rotation.getRandom(random)).getY();
    }

    public static boolean checkEachCornerAtY(NoiseColumn[] columns, int y) {
        for (NoiseColumn column : columns) {
            if (column.getBlock(y).isAir() || column.getBlock(y).is(Blocks.WATER)) {
                return false;
            }
        }
        return true;
    }
}