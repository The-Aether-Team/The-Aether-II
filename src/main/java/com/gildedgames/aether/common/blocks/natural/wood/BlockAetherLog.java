package com.gildedgames.aether.common.blocks.natural.wood;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.state.EnumProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockAetherLog extends BlockLog
{
	public static final EnumProperty<BlockLog.EnumAxis> PROPERTY_LOG_AXIS = EnumProperty.create("axis", BlockLog.EnumAxis.class);

	private final AetherWoodType type;

	public BlockAetherLog(AetherWoodType type)
	{
		super();

		this.type = type;

		this.setSoundType(SoundType.WOOD);

		this.setHardness(2.0f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y));

		Blocks.FIRE.setFireInfo(this, 5, 5);
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot)
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
	public void breakBlock(World world, BlockPos pos, BlockState state)
	{
		byte size = 4;

		int chunkSize = size + 1;

		if (world.isAreaLoaded(pos.add(-chunkSize, -chunkSize, -chunkSize), pos.add(chunkSize, chunkSize, chunkSize)))
		{
			for (BlockPos neighborPos : BlockPos.getAllInBox(pos.add(-size, -size, -size), pos.add(size, size, size)))
			{
				BlockState neighborState = world.getBlockState(neighborPos);

				if (neighborState.getBlock().isLeaves(neighborState, world, neighborPos))
				{
					neighborState.getBlock().beginLeavesDecay(neighborState, world, neighborPos);
				}
			}
		}
	}

	@Override
	public BlockState getStateFromMeta(int meta)
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
	public int getMetaFromState(BlockState state)
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
	public boolean canSustainLeaves(BlockState state, IBlockReader world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockReader world, BlockPos pos)
	{
		return true;
	}

	@Override
	public int damageDropped(BlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_LOG_AXIS);
	}

	public AetherWoodType getAetherWoodType()
	{
		return this.type;
	}
}
