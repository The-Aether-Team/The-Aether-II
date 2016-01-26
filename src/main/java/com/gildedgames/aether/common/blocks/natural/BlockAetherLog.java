package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAetherLog extends Block
{
	public static final PropertyEnum PROPERTY_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	public BlockAetherLog()
	{
		super(Material.wood);

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(2.0f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(PROPERTY_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		byte size = 4;
		int chunkSize = size + 1;

		if (world.isAreaLoaded(pos.add(-chunkSize, -chunkSize, -chunkSize), pos.add(chunkSize, chunkSize, chunkSize)))
		{
			for (Object obj : BlockPos.getAllInBox(pos.add(-size, -size, -size), pos.add(size, size, size)))
			{
				BlockPos neighborPos = (BlockPos) obj;
				IBlockState neighborState = world.getBlockState(neighborPos);

				if (neighborState.getBlock().isLeaves(world, neighborPos))
				{
					neighborState.getBlock().beginLeavesDecay(world, neighborPos);
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

		return this.getDefaultState().withProperty(PROPERTY_AXIS, axis);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		switch (((BlockLog.EnumAxis) state.getValue(PROPERTY_AXIS)))
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
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
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
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_AXIS);
	}
}
