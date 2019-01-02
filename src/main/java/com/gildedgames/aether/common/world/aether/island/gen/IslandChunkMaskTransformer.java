package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * Transforms generic blocks in a {@link ChunkSegmentMask} into real-world blocks for usage by block accessors or
 * chunk generation.
 */
public class IslandChunkMaskTransformer implements IChunkMaskTransformer
{
	private final IBlockState[] states;

	private final int[] blocks, metas;

	public IslandChunkMaskTransformer()
	{
		int count = IslandBlockType.values().length;

		this.states = new IBlockState[count];

		this.blocks = new int[count];
		this.metas = new int[count];

		this.setMaskValue(IslandBlockType.AIR_BLOCK, Blocks.AIR.getDefaultState());
		this.setMaskValue(IslandBlockType.WATER_BLOCK, Blocks.WATER.getDefaultState());

		this.setMaskValue(IslandBlockType.STONE_BLOCK, BlocksAether.holystone.getDefaultState());
		this.setMaskValue(IslandBlockType.STONE_MOSSY_BLOCK,
				BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE));

		this.setMaskValue(IslandBlockType.SNOW_BLOCK, BlocksAether.highlands_snow.getDefaultState());
		this.setMaskValue(IslandBlockType.FERROSITE_BLOCK, BlocksAether.ferrosite.getDefaultState());

		this.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, BlocksAether.aether_grass.getDefaultState());
		this.setMaskValue(IslandBlockType.SOIL_BLOCK, BlocksAether.aether_dirt.getDefaultState());
		this.setMaskValue(IslandBlockType.COAST_BLOCK, BlocksAether.quicksoil.getDefaultState());

		this.setMaskValue(IslandBlockType.CLOUD_BED_BLOCK, BlocksAether.aercloud.getDefaultState());
		this.setMaskValue(IslandBlockType.VEIN_BLOCK, BlocksAether.highlands_ice.getDefaultState());
	}

	public void setMaskValue(IslandBlockType type, IBlockState state)
	{
		int index = type.ordinal();

		this.states[index] = state;

		this.blocks[index] = Block.getIdFromBlock(state.getBlock());
		this.metas[index] = state.getBlock().getMetaFromState(state);
	}

	@Override
	public IBlockState getBlockState(int val)
	{
		return this.states[val];
	}

	@Override
	public int getBlockID(int val)
	{
		return this.blocks[val];
	}

	@Override
	public int getBlockMeta(int val)
	{
		return this.metas[val];
	}
}
