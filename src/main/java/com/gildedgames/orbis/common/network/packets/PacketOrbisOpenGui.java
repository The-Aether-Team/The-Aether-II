package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisOpenGui implements IMessage
{

	public PacketOrbisOpenGui()
	{

	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisOpenGui, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisOpenGui message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			if (module.inDeveloperMode() && module.powers().getCurrentPower().hasCustomGui())
			{
				module.powers().getCurrentPower().onOpenGui(player);
			}

			return null;
		}
	}
}
