package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.structure.StructureInjectionLogic;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityStructureExtended extends TileEntityStructure
{

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		int i = compound.getInteger("posX");
		int j = compound.getInteger("posY");
		int k = compound.getInteger("posZ");

		this.setPosition(new BlockPos(i, j, k));

		int l = compound.getInteger("sizeX");
		int i1 = compound.getInteger("sizeY");
		int j1 = compound.getInteger("sizeZ");

		this.setSize(new BlockPos(l, i1, j1));

		this.refreshMode();
	}

	public void refreshMode()
	{
		if (this.worldObj != null)
		{
			BlockPos blockpos = this.getPos();
			IBlockState iblockstate = this.worldObj.getBlockState(blockpos);

			if (iblockstate.getBlock() == BlocksAether.structure_extended)
			{
				this.worldObj.setBlockState(blockpos, iblockstate.withProperty(BlockStructure.MODE, this.getMode()), 2);
			}
		}
	}

	@Override
	public void setMode(TileEntityStructure.Mode modeIn)
	{
		super.setMode(modeIn);
		IBlockState iblockstate = this.worldObj.getBlockState(this.getPos());

		if (iblockstate.getBlock() == BlocksAether.structure_extended)
		{
			this.worldObj.setBlockState(this.getPos(), iblockstate.withProperty(BlockStructure.MODE, modeIn), 2);
		}
	}

	@Override
	public boolean detectSize()
	{
		return StructureInjectionLogic.detectSize(this);
	}

	@SideOnly(Side.CLIENT)
	public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

}
