package com.gildedgames.aether.common.player.abilites;

import com.gildedgames.aether.common.items.consumables.ItemCloudParachute;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.io.IOSyncable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class AbilityParachute implements IOSyncable<ByteBuf, ByteBuf>, NBT
{
	private final PlayerAether player;

	private ItemCloudParachute.ParachuteType parachuteType = ItemCloudParachute.ParachuteType.COLD;

	private boolean isOpen;

	private boolean isDirty;

	public AbilityParachute(PlayerAether player)
	{
		this.player = player;
	}

	public void onUpdate()
	{

	}

	public void openParachute(ItemCloudParachute.ParachuteType parachute)
	{
		this.parachuteType = parachute;
		this.isOpen = true;

		this.markDirty();
	}

	public ItemCloudParachute.ParachuteType getParachuteType()
	{
		return this.parachuteType;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("parachuteType", this.parachuteType.ordinal());
		output.setBoolean("isOpen", this.isOpen);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.parachuteType = ItemCloudParachute.ParachuteType.fromOrdinal(input.getInteger("parachuteType"));
		this.isOpen = input.getBoolean("isOpen");
	}

	@Override
	public void syncTo(ByteBuf output, SyncSide to)
	{
		output.writeInt(this.parachuteType.ordinal());
		output.writeBoolean(this.isOpen);
	}

	@Override
	public void syncFrom(ByteBuf input, SyncSide from)
	{
		this.parachuteType = ItemCloudParachute.ParachuteType.fromOrdinal(input.readInt());
		this.isOpen = input.readBoolean();
	}

	public boolean isOpen()
	{
		return this.isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;

		this.markDirty();
	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}
}
