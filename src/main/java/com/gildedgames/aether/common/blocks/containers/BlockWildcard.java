package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntityWildcard;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWildcard extends ContainerBlock
{

	public BlockWildcard(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockRenderType getRenderType(final BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return true;
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
		return new TileEntityWildcard();
	}

	@Override
	public boolean onBlockActivated(
			final World world, final BlockPos pos, final BlockState state, final PlayerEntity player, final Hand hand, final Direction side,
			final float hitX, final float hitY, final float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}

		final TileEntity te = world.getTileEntity(pos);

		if (!player.isSneaking())
		{
			return false;
		}

		if (te instanceof ILockableContainer)
		{
			player.displayGUIChest((ILockableContainer) te);

			return true;
		}

		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		tooltip.add(I18n.format("aether.warning.deprecated_item"));
	}

}
