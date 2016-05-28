package com.gildedgames.aether.common.tile_entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos.MutableBlockPos;

import com.gildedgames.util.core.nbt.NBTHelper;

public class TileEntityLinkedSchematicBoundary extends TileEntitySchematicBlock
{
	
	private BlockPos linkedPos;

	public boolean isLinked()
	{
		return this.linkedPos != null;
	}
	
	public void linkToPos(BlockPos pos)
	{
		this.linkedPos = pos;
	}
	
	@Override
	public void unmarkForGeneration()
	{
		super.unmarkForGeneration();
		
		Iterable<MutableBlockPos> bounds = BlockPos.getAllInBoxMutable(this.getPos(), this.linkedPos);
		
		for(MutableBlockPos pos : bounds)
		{
			TileEntity te = this.getWorld().getTileEntity(pos);
			
			if (te instanceof TileEntitySchematicBlock)
			{
				TileEntitySchematicBlock schematicBlock = (TileEntitySchematicBlock)te;
			
				schematicBlock.isMarkedForGeneration = false;
			}
		}
	}
	
	@Override
	public void markForGeneration()
	{
		super.markForGeneration();
		
		Iterable<MutableBlockPos> bounds = BlockPos.getAllInBoxMutable(this.getPos(), this.linkedPos);
		
		for(MutableBlockPos pos : bounds)
		{
			TileEntity te = this.getWorld().getTileEntity(pos);
			
			if (te instanceof TileEntitySchematicBlock)
			{
				TileEntitySchematicBlock schematicBlock = (TileEntitySchematicBlock)te;
			
				schematicBlock.isMarkedForGeneration = true;
				
				schematicBlock.onMarkedForGeneration(this.getPos(), this.linkedPos);
			}
		}
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		NBTHelper.writeBlockPos(compound, "linkedPos", this.linkedPos);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		this.linkedPos = NBTHelper.readBlockPos(compound, "linkedPos");
	}

	@Override
	public void onSchematicGeneration()
	{
		this.getWorld().setBlockToAir(this.getPos());
	}
	
	@Override
	public void onMarkedForGeneration(BlockPos start, BlockPos end)
	{
		
	}
	
	@Override
	public boolean shouldInvalidateTEOnGen()
	{
		return true;
	}
	
}
