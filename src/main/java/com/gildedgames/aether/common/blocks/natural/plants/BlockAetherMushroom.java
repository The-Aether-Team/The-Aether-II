package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockAetherMushroom extends BlockAetherFlowerBase implements IPlantable
{
	@Override
	public boolean isSuitableSoilBlock(World world, BlockPos pos, final IBlockState state)
	{
		if (state.getBlock() == BlocksAether.aether_dirt)
		{
			return true;
		}
		else
		{
			return world.getLight(pos) < 13 && state.getBlock().canSustainPlant(state, world, pos.down(), EnumFacing.UP, this);
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Cave;
	}

	@Override
	public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != this)
		{
			return this.getDefaultState();
		}
		return state;
	}
}
