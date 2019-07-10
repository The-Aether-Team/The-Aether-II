package com.gildedgames.aether.common.world.biomes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.state.IBlockState;

/**
 * If implemented for a biome, snowfall and ice-freezing will occur with
 * the specified blocks.
 */
public interface ISnowyBiome
{
	default IBlockState getFrozenWaterBlock()
	{
		return BlocksAether.highlands_ice.getDefaultState();
	}

	default IBlockState getSnowBlock()
	{
		return BlocksAether.highlands_snow_layer.getDefaultState();
	}

}
