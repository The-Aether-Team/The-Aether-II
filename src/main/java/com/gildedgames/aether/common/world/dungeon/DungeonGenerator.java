package com.gildedgames.aether.common.world.dungeon;

import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import com.gildedgames.util.core.util.BlockPosDimension;

public interface DungeonGenerator
{
	
	BlockPosDimension getEntrancePos();
	
	boolean isLayoutReady();
	
	void generateLayout(MinecraftServer server, Random rand, DungeonInstance inst, DungeonRoomProvider provider);

	void generateChunk(World world, Random rand, DungeonInstance inst, ChunkPrimer primer, int chunkX, int chunkZ);
	
	void populateChunk(World world, Random rand, DungeonInstance inst, int chunkX, int chunkZ);
	
}
