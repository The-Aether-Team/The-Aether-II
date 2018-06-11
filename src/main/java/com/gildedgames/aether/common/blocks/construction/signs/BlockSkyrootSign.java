package com.gildedgames.aether.common.blocks.construction.signs;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.BlockSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSkyrootSign extends BlockSign implements IInternalBlock
{
	protected BlockSkyrootSign()
	{
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
		this.disableStats();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySkyrootSign();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemsAether.skyroot_sign;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			return tileentity instanceof TileEntitySkyrootSign && ((TileEntitySkyrootSign) tileentity).executeCommand(playerIn);
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(ItemsAether.skyroot_sign);
	}
}
