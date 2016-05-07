package com.gildedgames.aether.common.world.dungeon;

public interface DungeonDefinition
{

	DungeonGenerator createGenerator();
	
	DungeonRoomProvider createRoomProvider();
	
}
