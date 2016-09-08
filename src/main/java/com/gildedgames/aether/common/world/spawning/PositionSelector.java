package com.gildedgames.aether.common.world.spawning;

import net.minecraft.world.World;

public interface PositionSelector
{

	int getPosY(World world, int posX, int posZ);

	int getScatter(World world);

}
