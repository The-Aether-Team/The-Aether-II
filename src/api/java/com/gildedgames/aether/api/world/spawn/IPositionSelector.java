package com.gildedgames.aether.api.world.spawn;

import net.minecraft.world.World;

public interface IPositionSelector
{

	int getPosY(World world, int posX, int posZ);

	int getScatter(World world);

}
