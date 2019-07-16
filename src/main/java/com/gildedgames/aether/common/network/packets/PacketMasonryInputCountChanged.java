package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketMasonryInputCountChanged implements IMessage
{
	private int inputCount;

	public PacketMasonryInputCountChanged(int inputCount)
	{
		this.inputCount = inputCount;
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.inputCount = buf.readInt();
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeInt(this.inputCount);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketMasonryInputCountChanged>
	{
		@Override
		protected void onMessage(PacketMasonryInputCountChanged message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			if (player.openContainer instanceof ContainerMasonryBench)
			{
				ContainerMasonryBench container = (ContainerMasonryBench) player.openContainer;

				container.setInputCount(message.inputCount);
			}
		}
	}
}
