package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntityHolystoneFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockHolystoneFurnace extends ContainerBlock
{
	public static final BooleanProperty PROPERTY_IS_LIT = BooleanProperty.create("is_lit");

	public static final DirectionProperty PROPERTY_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public static final int UNLIT_META = 0, LIT_META = 1;

	public BlockHolystoneFurnace(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(3.5f));

		this.setDefaultState(this.getStateContainer().getBaseState()
				.with(PROPERTY_IS_LIT, Boolean.FALSE)
				.with(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		world.setBlockState(pos, state.with(PROPERTY_FACING, placer.getHorizontalFacing()), 2);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (!world.isRemote())
		{
			TileEntity tileEntity = world.getTileEntity(pos);

			if (tileEntity instanceof TileEntityHolystoneFurnace)
			{
				player.openContainer((INamedContainerProvider) tileEntity);
			}
		}

		return true;
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityHolystoneFurnace)
			{
				InventoryHelper.dropInventoryItems(world, pos, (TileEntityHolystoneFurnace) tileentity);

				world.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, world, pos, newState, isMoving);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (state.get(PROPERTY_IS_LIT))
		{
			Direction facing = state.get(PROPERTY_FACING).getOpposite();

			double x = pos.getX() + 0.5D;
			double y = pos.getY() + rand.nextDouble() * 6.0D / 16.0D + 0.125D;
			double z = pos.getZ() + 0.5D;

			double xOffset = 0.5D;
			double zOffset = rand.nextDouble() * 0.6D - 0.3D;

			if (rand.nextDouble() < 0.1D)
			{
				world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F,
						false);
			}

			switch (facing)
			{
				case WEST:
					world.addParticle(ParticleTypes.SMOKE, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					world.addParticle(ParticleTypes.FLAME, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					break;
				case EAST:
					world.addParticle(ParticleTypes.SMOKE, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					world.addParticle(ParticleTypes.FLAME, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					break;
				case NORTH:
					world.addParticle(ParticleTypes.SMOKE, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
					world.addParticle(ParticleTypes.FLAME, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
					break;
				case SOUTH:
					world.addParticle(ParticleTypes.SMOKE, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
					world.addParticle(ParticleTypes.FLAME, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public int getLightValue(BlockState state)
	{
		return state.get(PROPERTY_IS_LIT) ? 13 : 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_IS_LIT, PROPERTY_FACING);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityHolystoneFurnace();
	}
}
