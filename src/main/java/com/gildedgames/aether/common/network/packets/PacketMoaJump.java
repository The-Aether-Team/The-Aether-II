package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerAbilitiesModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import com.gildedgames.aether.common.network.IMessage;
import net.minecraft.network.PacketBuffer;

public class PacketMoaJump implements IMessage
{
	private int midAirJumpsAllowed;

	public PacketMoaJump(final int midAirJumpsAllowed)
	{
		this.midAirJumpsAllowed = midAirJumpsAllowed;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.midAirJumpsAllowed = buf.readInt();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.midAirJumpsAllowed);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketMoaJump>
	{
		@Override
		protected void onMessage(PacketMoaJump message, ClientPlayerEntity player)
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerAbilitiesModule.class).setMidAirJumpsAllowed(message.midAirJumpsAllowed);
		}
	}
}
