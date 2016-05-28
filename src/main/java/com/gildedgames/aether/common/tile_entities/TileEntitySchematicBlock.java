package com.gildedgames.aether.common.tile_entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public abstract class TileEntitySchematicBlock extends TileEntity implements ITickable
{
	
	private int ticksExisted;
	
	protected boolean isMarkedForGeneration;

	@Override
	public void update()
	{
		if (this.ticksExisted == 0 && this.isMarkedForGeneration())
		{
			this.onSchematicGeneration();
			
			if (this.shouldInvalidateTEOnGen())
			{
				this.invalidate();
			}
		}
			
		this.ticksExisted++;
	}

	public void unmarkForGeneration()
	{
		this.isMarkedForGeneration = false;
	}
	
	public void markForGeneration()
	{
		this.isMarkedForGeneration = true;
	}
	
	public boolean isMarkedForGeneration()
	{
		return this.isMarkedForGeneration;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		compound.setBoolean("markedForGeneration", this.isMarkedForGeneration());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		this.isMarkedForGeneration = compound.getBoolean("markedForGeneration");
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);

		return new S35PacketUpdateTileEntity(pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	private void sendUpdates()
	{
		this.worldObj.markBlockForUpdate(pos);

		this.markDirty();
	}
	
	public abstract void onSchematicGeneration();
	
	public abstract void onMarkedForGeneration(BlockPos start, BlockPos end);
	
	public abstract boolean shouldInvalidateTEOnGen();

}
