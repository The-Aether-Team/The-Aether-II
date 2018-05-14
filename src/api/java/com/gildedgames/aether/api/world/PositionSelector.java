package com.gildedgames.aether.api.world;

import net.minecraft.world.World;

public interface PositionSelector
{

	int getPosY(World world, int posX, int posZ);

	int getScatter(World world);

}
