package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.IPlantable;

public class BlockAetherMushroom extends BlockAetherFlowerBase implements IPlantable
{
	public BlockAetherMushroom(Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean isSuitableSoilBlock(IWorldReader world, BlockPos pos, final BlockState state)
	{
		if (state.getBlock() == BlocksAether.aether_dirt)
		{
			return true;
		}
		else
		{
			return world.getLight(pos) < 13 && state.getBlock().canSustainPlant(state, world, pos.down(), Direction.UP, this);
		}
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos)
	{
		return PlantType.Cave;
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() != this)
		{
			return this.getDefaultState();
		}
		return state;
	}
}
