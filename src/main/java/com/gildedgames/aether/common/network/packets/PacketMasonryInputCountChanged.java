package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMasonryInputCountChanged implements IMessage
{

	private int inputCount;

	public PacketMasonryInputCountChanged()
	{

	}

	public PacketMasonryInputCountChanged(int inputCount)
	{
		this.inputCount = inputCount;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.inputCount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.inputCount);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketMasonryInputCountChanged, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMasonryInputCountChanged message, PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerMasonryBench)
			{
				ContainerMasonryBench container = (ContainerMasonryBench) player.openContainer;

				container.setInputCount(message.inputCount);
			}

			return null;
		}
	}
}
