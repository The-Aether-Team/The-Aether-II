package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSendInventorySize implements IMessage
{
	private byte size;

	public PacketSendInventorySize()
	{

	}

	public PacketSendInventorySize(byte size)
	{
		this.size = size;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.size);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.size = buf.readByte();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSendInventorySize, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSendInventorySize message, PlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);
			tradeModule.setTradeSlots(message.size);

			return null;
		}
	}
}
