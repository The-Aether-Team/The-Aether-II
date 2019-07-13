package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.tiles.TileEntityAltar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockAltar extends Block implements ITileEntityProvider
{
	public static final DirectionProperty PROPERTY_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public BlockAltar(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_FACING, Direction.NORTH));
	}

	@Override
	public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state)
	{
		TileEntityAltar altar = (TileEntityAltar) world.getTileEntity(pos);

		if (altar != null)
		{
			altar.dropContents();
		}

		super.onPlayerDestroy(world, pos, state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(PROPERTY_FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!world.isRemote())
		{
			TileEntityAltar altar = (TileEntityAltar) world.getTileEntity(pos);

			if (altar == null)
			{
				return false;
			}

			ItemStack heldStack = player.inventory.getCurrentItem();

			if (heldStack.isEmpty() || heldStack.isItemEqual(altar.getStackOnAltar()))
			{
				this.dropNextItem(altar, world);
			}
			else if (heldStack.getItem() == ItemsAether.ambrosium_shard)
			{
				if (altar.getAmbrosiumCount() < 16)
				{
					if (!player.isCreative())
					{
						heldStack.shrink(1);
					}

					altar.addAmbrosiumShard();
				}
			}
			else if (AetherAPI.content().altar().getMatchingRecipeFromInput(heldStack) != null)
			{
				ItemStack stack = heldStack.copy();
				stack.setCount(1);

				if (!altar.getStackOnAltar().isEmpty())
				{
					world.addEntity(altar.createEntityItemAboveAltar(altar.getStackOnAltar()));
				}

				altar.setStackOnAltar(stack);

				heldStack.shrink(1);
			}

			altar.attemptCrafting();
		}

		return true;
	}

	private void dropNextItem(TileEntityAltar altar, World world)
	{
		ItemStack stack = null;

		if (!altar.getStackOnAltar().isEmpty())
		{
			stack = altar.getStackOnAltar();

			altar.setStackOnAltar(ItemStack.EMPTY);
		}
		else if (altar.getAmbrosiumCount() > 0)
		{
			stack = new ItemStack(ItemsAether.ambrosium_shard, 1);

			altar.removeAmbrosiumShard();
		}

		if (stack != null)
		{
			world.addEntity(altar.createEntityItemAboveAltar(stack));
		}
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntityAltar();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_FACING);
	}

}
