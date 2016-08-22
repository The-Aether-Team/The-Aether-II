package com.gildedgames.aether.common.world.labyrinth;

public interface DungeonDefinition
{

	DungeonGenerator createGenerator();
	
	DungeonRoomProvider createRoomProvider();
	
}
