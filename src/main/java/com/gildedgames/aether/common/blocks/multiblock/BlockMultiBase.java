package com.gildedgames.aether.common.blocks.multiblock;

import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.aether.common.entities.tiles.multiblock.ITileEntityMultiblock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public abstract class BlockMultiBase extends BlockContainer implements IWorldObjectHoverable
{
	public BlockMultiBase(Material material)
	{
		super(material);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
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
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
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
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
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
