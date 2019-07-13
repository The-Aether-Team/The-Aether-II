package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntityHolystoneFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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

		this.setDefaultState(this.stateContainer.getBaseState()
				.with(PROPERTY_IS_LIT, Boolean.FALSE)
				.with(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		world.setBlockState(pos, state.with(PROPERTY_FACING, placer.getHorizontalFacing()), 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(pos);

			if (tileEntity instanceof TileEntityHolystoneFurnace)
			{
				player.displayGUIChest((IInventory) tileEntity);
			}
		}

		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileEntityHolystoneFurnace)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand)
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
					world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					world.spawnParticle(ParticleTypes.FLAME, x - xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					break;
				case EAST:
					world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					world.spawnParticle(ParticleTypes.FLAME, x + xOffset, y, z + zOffset, 0.0D, 0.0D, 0.0D);
					break;
				case NORTH:
					world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
					world.spawnParticle(ParticleTypes.FLAME, x + zOffset, y, z - xOffset, 0.0D, 0.0D, 0.0D);
					break;
				case SOUTH:
					world.spawnParticle(ParticleTypes.SMOKE_NORMAL, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
					world.spawnParticle(ParticleTypes.FLAME, x + zOffset, y, z + xOffset, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return true;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return true;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		return state.get(PROPERTY_IS_LIT) ? 13 : 0;
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		int meta = state.get(PROPERTY_FACING).getIndex();

		if (state.get(PROPERTY_IS_LIT))
		{
			meta |= 8;
		}

		return meta;
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		Direction facing = Direction.byHorizontalIndex(meta & 7);

		boolean isLit = (meta & 8) == 8;

		return this.getDefaultState().with(PROPERTY_FACING, facing).with(PROPERTY_IS_LIT, isLit);
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
