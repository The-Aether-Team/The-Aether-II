package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.preparation.IChunkMaskTransformer;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.world.preparation.mask.ChunkMask;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * Transforms generic blocks in a {@link ChunkMask} into real-world blocks for usage by block accessors or
 * chunk generation.
 */
public class IslandChunkMaskTransformer implements IChunkMaskTransformer
{
	private final BlockState[] states;

	public IslandChunkMaskTransformer()
	{
		this.states = new BlockState[IslandBlockType.VALUES.length];

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

	public void setMaskValue(IslandBlockType type, BlockState state)
	{
		int key = type.ordinal();

		this.states[key] = state;
	}

	@Override
	public BlockState getBlockState(int key)
	{
		return this.states[key];
	}

	@Override
	public int getBlockCount()
	{
		return this.states.length;
	}
}
