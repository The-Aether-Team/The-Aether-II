package com.gildedgames.aether.common.world.spawning;

import net.minecraft.world.World;

public interface HeightSelector
{

	int getPosY(World world, int posX, int posZ);

}
