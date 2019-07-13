package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherFlowerBase extends BlockAetherPlant implements IBlockSnowy
{
	public final static int SNOWY_META = 1, NORMAL_META = 0;

	public BlockAetherFlowerBase(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		super.tick(state, world, pos, rand);

		if (!world.isRemote && this.canGrow(world, pos, state, false))
		{
			this.grow(world, rand, pos, state);
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final BlockPos pos)
	{
		return super.canPlaceBlockAt(world, pos);
	}

	@Override
	public int getLightValue(final BlockState state)
	{
		return this.lightValue;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX,
			float hitY, float hitZ)
	{
		ItemStack main = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();

		boolean addSnow = false;

		if (!state.get(PROPERTY_SNOWY))
		{
			if (main != null && main.getItem() instanceof BlockItem)
			{
				if (((BlockItem) main.getItem()).getBlock() instanceof BlockSnow)
				{
					addSnow = true;
					main.shrink(1);
				}
			}
			else if (offhand != null && offhand.getItem() instanceof BlockItem && ((BlockItem) offhand.getItem()).getBlock() instanceof BlockSnow)
			{
				addSnow = true;
				offhand.shrink(1);
			}
		}

		if (addSnow)
		{
			worldIn.setBlockState(pos, state.with(PROPERTY_SNOWY, Boolean.TRUE), 2);
		}

		return addSnow;
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().with(BlockSnow.LAYERS, 1), 2);
			}
		}
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(PROPERTY_SNOWY, meta == SNOWY_META);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(PROPERTY_SNOWY) ? SNOWY_META : NORMAL_META;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_SNOWY);
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public Vec3d getOffset(final BlockState state, final IBlockReader access, final BlockPos pos)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return false;
	}

}
