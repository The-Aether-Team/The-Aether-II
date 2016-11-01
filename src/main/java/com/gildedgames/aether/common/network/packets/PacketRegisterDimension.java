package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRegisterDimension implements IMessage
{

	private int dimID;

	private DimensionType type;

	public PacketRegisterDimension()
	{

	}

	public PacketRegisterDimension(DimensionType type, int dimID)
	{
		this.dimID = dimID;
		this.type = type;
	}

	private DimensionType fromOrdinal(int ordinal)
	{
		DimensionType[] type = DimensionType.values();

		return type[ordinal > type.length || ordinal < 0 ? 0 : ordinal];
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dimID = buf.readInt();
		this.type = this.fromOrdinal(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimID);
		buf.writeInt(this.type.ordinal());
	}

	public static class Handler extends MessageHandlerClient<PacketRegisterDimension, PacketRegisterDimension>
	{
		@Override
		public PacketRegisterDimension onMessage(PacketRegisterDimension message, EntityPlayer player)
		{
			if (!DimensionManager.isDimensionRegistered(message.dimID))
			{
				DimensionManager.registerDimension(message.dimID, message.type);
			}

			return null;
		}
	}

}
