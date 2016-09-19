package com.gildedgames.aether.common.world.util;

import com.gildedgames.aether.common.world.dungeon.DungeonRoom;

import java.util.Comparator;

public class RoomAreaComparer implements Comparator<DungeonRoom>
{

	@Override
	public int compare(DungeonRoom o1, DungeonRoom o2)
	{
		return o2.getArea() - o1.getArea();
	}

}
