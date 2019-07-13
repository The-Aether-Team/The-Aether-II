package com.gildedgames.aether.common.blocks.natural.wood;

import net.minecraft.block.*;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockAetherLog extends LogBlock
{
	public static final EnumProperty<LogBlock.EnumAxis> PROPERTY_LOG_AXIS = EnumProperty.create("axis", LogBlock.EnumAxis.class);

	private final AetherWoodType type;

	public BlockAetherLog(Block.Properties properties, AetherWoodType type)
	{
		super(properties);

		this.type = type;


		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_LOG_AXIS, LogBlock.EnumAxis.Y));

		Blocks.FIRE.setFireInfo(this, 5, 5);
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.get(PROPERTY_LOG_AXIS))
				{
					case X:
						return state.with(PROPERTY_LOG_AXIS, LogBlock.EnumAxis.Z);
					case Z:
						return state.with(PROPERTY_LOG_AXIS, LogBlock.EnumAxis.X);
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
		LogBlock.EnumAxis axis = LogBlock.EnumAxis.NONE;

		switch (meta & 7)
		{
			case 1:
				axis = LogBlock.EnumAxis.Y;
				break;
			case 2:
				axis = LogBlock.EnumAxis.X;
				break;
			case 3:
				axis = LogBlock.EnumAxis.Z;
				break;
		}

		return this.getDefaultState().with(PROPERTY_LOG_AXIS, axis);
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		int meta = 0;

		switch (state.get(PROPERTY_LOG_AXIS))
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
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_LOG_AXIS);
	}

	public AetherWoodType getAetherWoodType()
	{
		return this.type;
	}
}
