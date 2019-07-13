package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.aether.common.entities.tiles.multiblock.ITileEntityMultiblock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BlockMultiBase extends ContainerBlock implements IWorldObjectHoverable
{
	public BlockMultiBase(Properties properties)
	{
		super(properties.hardnessAndResistance(-1.0F, 6000000.0F));
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (world.isRemote())
		{
			return true;
		}

		TileEntity entity = world.getTileEntity(pos);

		if (entity instanceof ITileEntityMultiblock)
		{
			((ITileEntityMultiblock) entity).onInteract(player);
		}

		return true;
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof ITileEntityMultiblock)
		{
			ITileEntityMultiblock controller = (ITileEntityMultiblock) te;

			controller.onDestroyed();
		}

		super.onReplaced(state, world, pos, newState, isMoving);
	}

	@Override
	public abstract TileEntity createNewTileEntity(IBlockReader reader);

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof ITileEntityMultiblock)
		{
			ITileEntityMultiblock controller = (ITileEntityMultiblock) te;

			return controller.getPickedStack(world, pos, state);
		}

		return super.getItem(world, pos, state);
	}

	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		if (result.hitInfo == RayTraceResult.Type.BLOCK)
		{
			TileEntity entity = world.getTileEntity(((BlockRayTraceResult) result).getPos());

			if (entity instanceof IWorldObjectHoverable)
			{
				return ((IWorldObjectHoverable) entity).getHoverText(world, result);
			}

		}

		return null;
	}
}
