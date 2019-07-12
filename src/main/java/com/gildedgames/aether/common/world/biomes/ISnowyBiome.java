package com.gildedgames.aether.common.world.biomes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.BlockState;

/**
 * If implemented for a biome, snowfall and ice-freezing will occur with
 * the specified blocks.
 */
public interface ISnowyBiome
{
	default BlockState getFrozenWaterBlock()
	{
		return BlocksAether.highlands_ice.getDefaultState();
	}

	default BlockState getSnowBlock()
	{
		return BlocksAether.highlands_snow_layer.getDefaultState();
	}

}
