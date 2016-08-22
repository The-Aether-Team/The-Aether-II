package com.gildedgames.aether.common.world.labyrinth.util;

import java.util.Comparator;

import com.gildedgames.aether.common.world.labyrinth.DungeonRoom;

public class RoomAreaComparer implements Comparator<DungeonRoom>
{

	@Override
	public int compare(DungeonRoom o1, DungeonRoom o2)
	{
		return o2.getArea() - o1.getArea();
	}

}
