package com.gildedgames.aether.api.lighting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public interface ILightingEngine
{
	void scheduleLightUpdate(EnumSkyBlock lightType, BlockPos pos);

	void procLightUpdates();

	void procLightUpdates(EnumSkyBlock lightType);
}
