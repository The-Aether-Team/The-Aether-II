package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCandyCane extends BlockRotatedPillar
{
	public static final EnumProperty<EnumAxis> BLOCK_AXIS = EnumProperty.create("axis", BlockCandyCane.EnumAxis.class);

	public BlockCandyCane()
	{
		super(Material.GLASS);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta,
			LivingEntity placer)
	{
		return this.getStateFromMeta(meta).withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		if (world.isRaining())
		{
			entity.motionX *= 0.1D;
			entity.motionZ *= 0.1D;
		}
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return Items.SUGAR;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return this.quantityDropped(random) + random.nextInt(fortune + 1);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 6 + random.nextInt(3);
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}

	@Override
	protected ItemStack getSilkTouchDrop(BlockState state)
	{
		return new ItemStack(BlocksAether.candy_cane_block);
	}

	@Override
	public MapColor getMapColor(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return MapColor.getBlockColor(EnumDyeColor.RED);
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.getValue(BLOCK_AXIS))
				{
					case X:
						return state.withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.Z);
					case Z:
						return state.withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		BlockCandyCane.EnumAxis axis = BlockCandyCane.EnumAxis.NONE;

		switch (meta & 7)
		{
			case 1:
				axis = BlockCandyCane.EnumAxis.Y;
				break;
			case 2:
				axis = BlockCandyCane.EnumAxis.X;
				break;
			case 3:
				axis = BlockCandyCane.EnumAxis.Z;
				break;
		}

		return this.getDefaultState().withProperty(BLOCK_AXIS, axis);
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		int meta = 0;

		switch (state.getValue(BLOCK_AXIS))
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BLOCK_AXIS);
	}

	public enum EnumAxis implements IStringSerializable
	{
		X("x"),
		Y("y"),
		Z("z"),
		NONE("none");

		private final String name;

		EnumAxis(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}

		public static BlockCandyCane.EnumAxis fromFacingAxis(Direction.Axis axis)
		{
			switch (axis)
			{
				case X:
					return X;
				case Y:
					return Y;
				case Z:
					return Z;
				default:
					return NONE;
			}
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}
}
