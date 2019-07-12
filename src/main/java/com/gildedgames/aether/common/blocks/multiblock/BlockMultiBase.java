package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.aether.common.entities.tiles.multiblock.ITileEntityMultiblock;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public abstract class BlockMultiBase extends ContainerBlock implements IWorldObjectHoverable
{
	public BlockMultiBase(Material material)
	{
		super(material);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing,
			float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}

		TileEntity entity = worldIn.getTileEntity(pos);

		if (entity instanceof ITileEntityMultiblock)
		{
			((ITileEntityMultiblock) entity).onInteract(playerIn);
		}

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, BlockState state)
	{
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof ITileEntityMultiblock)
		{
			ITileEntityMultiblock controller = (ITileEntityMultiblock) te;

			controller.onDestroyed();
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player)
	{
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof ITileEntityMultiblock)
		{
			ITileEntityMultiblock controller = (ITileEntityMultiblock) te;

			return controller.getPickedStack(world, pos, state);
		}

		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		TileEntity entity = world.getTileEntity(result.getBlockPos());

		if (entity instanceof IWorldObjectHoverable)
		{
			return ((IWorldObjectHoverable) entity).getHoverText(world, result);
		}

		return null;
	}
}
