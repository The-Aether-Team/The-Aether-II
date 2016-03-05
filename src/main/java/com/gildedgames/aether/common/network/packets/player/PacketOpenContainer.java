package com.gildedgames.aether.common.network.packets.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.io.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOpenContainer implements IMessage
{
	private int containerId;

	public PacketOpenContainer() { }

	public PacketOpenContainer(int containerId)
	{
		this.containerId = containerId;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.containerId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.containerId);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOpenContainer, IMessage>
	{
		@Override
		public IMessage onMessage(PacketOpenContainer message, EntityPlayer player)
		{
			if (player instanceof EntityPlayerMP)
			{
				BlockPos pos = player.getPosition();

				player.openGui(AetherCore.INSTANCE, message.containerId, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
			}

			return null;
		}
	}
}
