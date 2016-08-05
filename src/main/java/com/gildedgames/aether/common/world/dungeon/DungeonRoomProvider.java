package com.gildedgames.aether.common.world.dungeon;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public interface DungeonRoomProvider
{

	List<DungeonRoom> createRooms(MinecraftServer server, Random rand);

	DungeonRoom createEntranceRoom(MinecraftServer server, Random rand);

	DungeonRoom createConnectionBottom(MinecraftServer server, Random rand);

	DungeonRoom createConnectionTop(MinecraftServer server, Random rand);
	
}
