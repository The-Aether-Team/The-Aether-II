package com.gildedgames.aether.api.orbis_core.api;

import net.minecraft.world.World;

import java.util.Random;

public interface PostPlacement
{

	void postGenerate(World world, Random rand, ICreationData data);

}
