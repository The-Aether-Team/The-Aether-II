package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * Transforms generic blocks in a {@link ChunkMask} into real-world blocks for usage by block accessors or
 * chunk generation.
 */
public class IslandChunkMaskTransformer implements IChunkMaskTransformer
{
	private final IBlockState[] states;

	public IslandChunkMaskTransformer()
	{
		this.states = new IBlockState[IslandBlockType.values().length];

		this.setMaskValue(IslandBlockType.AIR_BLOCK, Blocks.AIR.getDefaultState());
		this.setMaskValue(IslandBlockType.WATER_BLOCK, Blocks.WATER.getDefaultState());

		this.setMaskValue(IslandBlockType.STONE_BLOCK, BlocksAether.holystone.getDefaultState());
		this.setMaskValue(IslandBlockType.STONE_MOSSY_BLOCK, BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE));

		this.setMaskValue(IslandBlockType.SNOW_BLOCK, BlocksAether.highlands_snow.getDefaultState());
		this.setMaskValue(IslandBlockType.FERROSITE_BLOCK, BlocksAether.ferrosite.getDefaultState());

		this.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, BlocksAether.aether_grass.getDefaultState());
		this.setMaskValue(IslandBlockType.SOIL_BLOCK, BlocksAether.aether_dirt.getDefaultState());
		this.setMaskValue(IslandBlockType.COAST_BLOCK, BlocksAether.quicksoil.getDefaultState());
	}

	public void setMaskValue(IslandBlockType type, IBlockState state)
	{
		this.states[type.ordinal()] = state;
	}

	@Override
	public IBlockState remapBlock(int val)
	{
		return this.states[val];
	}
}
