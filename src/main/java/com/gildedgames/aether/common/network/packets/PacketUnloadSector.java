package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis_api.preparation.IPrepManager;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketUnloadSector implements IMessage
{
	private int x, z;

	@SuppressWarnings("unchecked")
	public PacketUnloadSector()
	{

	}

	public PacketUnloadSector(IPrepSectorData data)
	{
		this.x = data.getSectorX();
		this.z = data.getSectorY();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.x);
		buf.writeInt(this.z);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketUnloadSector,IMessage>
	{
		@Override
		public IMessage onMessage(PacketUnloadSector message, EntityPlayer player)
		{
			final IPrepManager manager = PrepHelper.getManager(player.world);

			if (manager != null)
			{
				manager.getClientAccess().getLoadedSector(message.x, message.z)
						.ifPresent((sector) -> manager.getClientAccess().removeSector(sector));
			}

			return null;
		}
	}
}
