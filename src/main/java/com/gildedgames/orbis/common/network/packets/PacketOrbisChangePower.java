package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisChangePower implements IMessage
{
	private int powerIndex;

	public PacketOrbisChangePower()
	{

	}

	public PacketOrbisChangePower(final PlayerOrbisModule module, final IGodPower power)
	{
		this.powerIndex = module.powers().getPowerIndex(power.getClass());
	}

	public PacketOrbisChangePower(final int powerIndex)
	{
		this.powerIndex = powerIndex;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.powerIndex = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.powerIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisChangePower, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisChangePower message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			if (module.inDeveloperMode())
			{
				module.powers().setCurrentPower(message.powerIndex);
			}

			return null;
		}
	}
}
