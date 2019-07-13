package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherFlowerBase extends BlockAetherPlant implements IBlockSnowy
{
	public final static int SNOWY_META = 1, NORMAL_META = 0;

	public BlockAetherFlowerBase(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		super.tick(state, world, pos, rand);

		if (!world.isRemote() && this.canGrow(world, pos, state, false))
		{
			this.grow(world, rand, pos, state);
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		ItemStack main = player.getHeldItemMainhand();
		ItemStack offhand = player.getHeldItemOffhand();

		boolean addSnow = false;

		if (!state.get(PROPERTY_SNOWY))
		{
			if (!main.isEmpty() && main.getItem() instanceof BlockItem)
			{
				if (((BlockItem) main.getItem()).getBlock() instanceof SnowBlock)
				{
					addSnow = true;
					main.shrink(1);
				}
			}
			else if (!offhand.isEmpty() && offhand.getItem() instanceof BlockItem && ((BlockItem) offhand.getItem()).getBlock() instanceof SnowBlock)
			{
				addSnow = true;
				offhand.shrink(1);
			}
		}

		if (addSnow)
		{
			world.setBlockState(pos, state.with(PROPERTY_SNOWY, Boolean.TRUE), 2);
		}

		return addSnow;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().with(SnowBlock.LAYERS, 1), 2);
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_SNOWY);
	}

	@Override
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
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
