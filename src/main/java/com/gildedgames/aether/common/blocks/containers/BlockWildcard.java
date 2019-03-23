package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.common.entities.tiles.TileEntityWildcard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWildcard extends BlockContainer
{

	public BlockWildcard()
	{
		super(Material.ROCK);

		this.setHardness(2.5f);

		this.setSoundType(SoundType.STONE);
	}

	@Override
	public EnumBlockRenderType getRenderType(final IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state)
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
	public TileEntity createNewTileEntity(final World worldIn, final int meta)
	{
		return new TileEntityWildcard();
	}

	@Override
	public boolean onBlockActivated(
			final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side,
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
