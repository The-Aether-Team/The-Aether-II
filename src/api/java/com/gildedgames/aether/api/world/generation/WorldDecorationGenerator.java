package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public interface WorldDecorationGenerator
{
	boolean generate(WorldSlice slice, Random rand, BlockPos pos);
}
