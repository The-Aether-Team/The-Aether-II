package com.gildedgames.aether.common.entities.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEntitySynced extends TileEntity
{

	public TileEntitySynced(TileEntityType<?> type)
	{
		super(type);
	}

	public void sendUpdatesToClients()
	{
		BlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT tag = super.getUpdateTag();

		this.write(tag);

		return tag;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = this.getUpdateTag();

		return new SUpdateTileEntityPacket(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet)
	{
		this.read(packet.getNbtCompound());
	}

}
