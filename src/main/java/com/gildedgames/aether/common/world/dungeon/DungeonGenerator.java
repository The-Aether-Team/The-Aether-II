package com.gildedgames.aether.common.world.dungeon;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import com.gildedgames.util.modules.world.common.BlockPosDimension;

public interface DungeonGenerator
{
	
	BlockPosDimension getEntrancePos();
	
	boolean isLayoutReady();
	
	void generateLayout(DungeonInstance inst, DungeonRoomProvider provider, Random rand);

	void generateChunk(World world, DungeonInstance inst, ChunkPrimer primer, int chunkX, int chunkZ);
	
	void populateChunk(World world, DungeonInstance inst, IChunkProvider chunkProvider, int chunkX, int chunkZ);
	
}
