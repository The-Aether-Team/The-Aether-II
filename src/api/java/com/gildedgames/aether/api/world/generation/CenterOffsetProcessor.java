package com.gildedgames.aether.api.world.generation;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public interface CenterOffsetProcessor
{

	BlockPos process(Rotation rotation);

}