package com.gildedgames.aether.common.world.dungeon;

import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.util.core.util.BlockPosDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public interface DungeonGenerator
{

	BlockPosDimension getEntrancePos();

	boolean isLayoutReady();

	void generateLayout(MinecraftServer server, long seed, DungeonInstance inst, DungeonRoomProvider provider);

	void generateChunk(World world, Random rand, DungeonInstance inst, ChunkPrimer primer, int chunkX, int chunkZ);

	void populateChunk(World world, Random rand, DungeonInstance inst, int chunkX, int chunkZ);

}
