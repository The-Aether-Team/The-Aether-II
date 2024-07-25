package com.aetherteam.aetherii.world.structure.spawning;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class UndergroundSpawningChecks {

    /*
    private ChunkPos searchNearbyChunks(ChunkPos chunkPos, MutableInt height, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        int y;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x != 0 || z != 0) {
                    ChunkPos offset = new ChunkPos(chunkPos.x + x, chunkPos.z + z);
                    y = UndergroundJigsawStructure.findStartingHeight(generator, heightAccessor, offset, randomState, templateManager, aboveBottom, belowTop);
                    if (y > heightAccessor.getMinBuildHeight()) {
                        height.setValue(y);
                        return offset;
                    }
                }
            }
        }
        return chunkPos;
    }


    private static int findStartingHeight(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState random, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        int minX = chunkPos.getMinBlockX() - 1;
        int minZ = chunkPos.getMinBlockZ() - 1;
        int maxX = chunkPos.getMaxBlockX() + 1;
        int maxZ = chunkPos.getMaxBlockZ() + 1;
        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, random),
                generator.getBaseColumn(minX, maxZ, heightAccessor, random),
                generator.getBaseColumn(maxX, minZ, heightAccessor, random),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, random)
        };
        int roomHeight = checkRoomHeight(templateManager, this.startPool.value().getRandomTemplate(random));
        int height = heightAccessor.getMinBuildHeight();
        int maxHeight = heightAccessor.getMaxBuildHeight() - belowTop;
        int thickness = roomHeight + 16;
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

     */

    private static int checkRoomHeight(StructureTemplateManager manager, ResourceLocation roomName) {
        StructureTemplate template = manager.getOrCreate(roomName);
        return template.getSize().getY();
    }

    private static boolean checkEachCornerAtY(NoiseColumn[] columns, int y) {
        for (NoiseColumn column : columns) {
            if (column.getBlock(y).isAir() || column.getBlock(y).is(AetherIITags.Blocks.AERCLOUDS)) {
                return false;
            }
        }
        return true;
    }
}