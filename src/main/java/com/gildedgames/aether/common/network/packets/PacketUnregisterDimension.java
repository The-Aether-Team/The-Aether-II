package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketUnregisterDimension implements IMessage
{

	private int dimID;

	public PacketUnregisterDimension()
	{

	}

	public PacketUnregisterDimension(int dimID)
	{
		this.dimID = dimID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dimID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimID);
	}

	public static class Handler extends MessageHandlerClient<PacketUnregisterDimension, PacketUnregisterDimension>
	{
		@Override
		public PacketUnregisterDimension onMessage(PacketUnregisterDimension message, EntityPlayer player)
		{
			if (DimensionManager.isDimensionRegistered(message.dimID))
			{
				DimensionManager.unregisterDimension(message.dimID);
			}

			return null;
		}
	}

}
