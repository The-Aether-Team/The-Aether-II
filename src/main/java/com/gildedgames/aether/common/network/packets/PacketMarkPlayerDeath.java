package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketMarkPlayerDeath implements IMessage
{
	private boolean flag;

	public PacketMarkPlayerDeath(final boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketMarkPlayerDeath>
	{
		@Override
		protected void onMessage(PacketMarkPlayerDeath message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerProgressModule.class).setHasDiedInAether(message.flag);
		}
	}
}
