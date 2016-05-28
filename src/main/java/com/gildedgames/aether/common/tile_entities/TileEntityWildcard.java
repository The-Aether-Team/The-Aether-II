package com.gildedgames.aether.common.tile_entities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.registry.GameData;

import com.google.common.collect.Iterators;

public class TileEntityWildcard extends TileEntitySchematicBlock
{

	@Override
	public void onSchematicGeneration()
	{
		Random rand = new Random();
		
		Block block = Iterators.get(GameData.getBlockRegistry().iterator(), rand.nextInt(Iterators.size(GameData.getBlockRegistry().iterator())));
		
		this.getWorld().setBlockState(this.getPos(), block.getDefaultState());
	}
	
	@Override
	public void onMarkedForGeneration(BlockPos start, BlockPos end)
	{
		
	}
	
	@Override
	public boolean shouldInvalidateTEOnGen()
	{
		return true;
	}

}
