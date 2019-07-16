package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketTalkedTo implements IMessage
{

	private ResourceLocation speaker;

	private boolean flag = true;

	public PacketTalkedTo()
	{

	}

	public PacketTalkedTo(ResourceLocation speaker, final boolean flag)
	{
		this.speaker = speaker;
		this.flag = flag;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.speaker = buf.readResourceLocation();
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeResourceLocation(this.speaker);
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTalkedTo>
	{
		@Override
		protected void onMessage(PacketTalkedTo message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerProgressModule.class).setHasTalkedTo(message.speaker, message.flag);
		}
	}
}
