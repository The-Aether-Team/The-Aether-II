package com.gildedgames.aether.api.world.generation;

import net.minecraft.world.World;

import java.util.Random;

public interface PostPlacementTemplate
{

	void postGenerate(World world, Random rand, TemplateLoc loc);

}
