package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockAmbrosiumOre extends BlockAetherOre
{
	public BlockAmbrosiumOre(Block.Properties properties)
	{
		super(properties);

		this.useNeighborBrightness = true;
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return ItemsAether.ambrosium_shard;
	}

	@Override
	protected int getUnmodifiedExpDrop(Random rand)
	{
		return MathHelper.getInt(rand, 0, 1);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		if (world instanceof ChunkCache)
		{
			return 0;
		}

		return super.getLightValue(state, world, pos);
	}
}
