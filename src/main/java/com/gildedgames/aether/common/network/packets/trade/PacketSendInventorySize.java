package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketSendInventorySize implements IMessage
{
	private byte size;

	public PacketSendInventorySize(byte size)
	{
		this.size = size;
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeByte(this.size);
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.size = buf.readByte();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSendInventorySize>
	{
		@Override
		protected void onMessage(PacketSendInventorySize message, ClientPlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);
			tradeModule.setTradeSlots(message.size);
		}
	}
}
