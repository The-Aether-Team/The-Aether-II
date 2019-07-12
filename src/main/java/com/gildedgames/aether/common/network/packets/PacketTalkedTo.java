package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
	public void fromBytes(final ByteBuf buf)
	{
		this.speaker = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
		this.flag = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.speaker.toString());
		buf.writeBoolean(this.flag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTalkedTo, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketTalkedTo message, final PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerProgressModule.class).setHasTalkedTo(message.speaker, message.flag);

			return null;
		}
	}
}
