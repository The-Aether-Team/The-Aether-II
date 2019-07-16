package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import com.gildedgames.aether.common.network.IMessage;

public class PacketProgressBooleanData implements IMessage
{
	private String key;

	private boolean flag;

	public PacketProgressBooleanData(String key, final boolean flag)
	{
		this.key = key;
		this.flag = flag;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.key = buf.readString();
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeString(this.key);
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketProgressBooleanData>
	{
		@Override
		protected void onMessage(PacketProgressBooleanData message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getModule(PlayerProgressModule.class).putBoolean(message.key, message.flag);
		}
	}
}
