package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAetherLog extends BlockLog
{
	public static final PropertyEnum<BlockLog.EnumAxis> PROPERTY_LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	public BlockAetherLog()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(2.0f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y));

		Blocks.FIRE.setFireInfo(this, 5, 5);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.getValue(PROPERTY_LOG_AXIS))
				{
					case X:
						return state.withProperty(PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Z);
					case Z:
						return state.withProperty(PROPERTY_LOG_AXIS, BlockLog.EnumAxis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		byte size = 4;

		int chunkSize = size + 1;

		if (world.isAreaLoaded(pos.add(-chunkSize, -chunkSize, -chunkSize), pos.add(chunkSize, chunkSize, chunkSize)))
		{
			for (BlockPos neighborPos : BlockPos.getAllInBox(pos.add(-size, -size, -size), pos.add(size, size, size)))
			{
				IBlockState neighborState = world.getBlockState(neighborPos);

				if (neighborState.getBlock().isLeaves(neighborState, world, neighborPos))
				{
					neighborState.getBlock().beginLeavesDecay(neighborState, world, neighborPos);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		BlockLog.EnumAxis axis = BlockLog.EnumAxis.NONE;

		switch (meta & 7)
		{
			case 1:
				axis = BlockLog.EnumAxis.Y;
				break;
			case 2:
				axis = BlockLog.EnumAxis.X;
				break;
			case 3:
				axis = BlockLog.EnumAxis.Z;
				break;
		}

		return this.getDefaultState().withProperty(PROPERTY_LOG_AXIS, axis);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		switch (state.getValue(PROPERTY_LOG_AXIS))
		{
			case Y:
				meta |= 1;
				break;
			case X:
				meta |= 2;
				break;
			case Z:
				meta |= 3;
				break;
		}

		return meta;
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_LOG_AXIS);
	}
}
