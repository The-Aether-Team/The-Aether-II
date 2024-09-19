package com.aetherteam.aetherii.world.structure.spawning;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public class HeightSpawningChecks {

    public boolean checkHeight(Structure.GenerationContext context, int minY, int maxY) {
        ChunkPos chunkpos = context.chunkPos();
        int posTest = context.chunkGenerator().getFirstOccupiedHeight(chunkpos.getWorldPosition().getX(), chunkpos.getWorldPosition().getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        return posTest > minY && posTest < maxY;
    }
}