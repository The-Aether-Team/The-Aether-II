package com.gildedgames.aether.common.world.dungeon;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Random;

public interface DungeonDefinition
{

	DungeonGenerator createGenerator();

	DungeonRoomProvider createRoomProvider();

	Entity createRandomMob(World world, Random rand);

}
