package com.gildedgames.aether.common.world.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IWorldGen
{

	boolean generate(World world, Random rand, BlockPos position, boolean centered);

}
