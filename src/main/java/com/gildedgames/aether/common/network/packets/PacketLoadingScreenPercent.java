package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.client.gui.misc.GuiAetherLoading;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketLoadingScreenPercent implements IMessage
{

	private float percent;

	public PacketLoadingScreenPercent()
	{

	}

	public PacketLoadingScreenPercent(float percent)
	{
		this.percent = percent;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.percent = buf.readFloat();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeFloat(this.percent);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketLoadingScreenPercent, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketLoadingScreenPercent message, final PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			GuiAetherLoading.PERCENT = message.percent;

			return null;
		}
	}

}
