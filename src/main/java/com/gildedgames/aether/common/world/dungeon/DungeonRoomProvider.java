package com.gildedgames.aether.common.world.dungeon;

import java.util.List;
import java.util.Random;

public interface DungeonRoomProvider
{

	List<DungeonRoom> createRooms(Random rand);
	
}
