package com.gildedgames.aether.common.blocks.construction.skyroot_sign;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootSign;
import net.minecraft.block.BlockSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSkyrootSign extends BlockSign
{
	protected BlockSkyrootSign()
	{
		this.setHardness(1.0F);
		this.setStepSound(soundTypeWood);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
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
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos)
	{
		return ItemsAether.skyroot_sign;
	}
}
