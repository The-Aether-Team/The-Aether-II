package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRotateBlueprint implements IMessage
{

	public PacketRotateBlueprint()
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

	public static class HandlerServer extends MessageHandlerServer<PacketRotateBlueprint, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketRotateBlueprint message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			module.powers().getBlueprintPower()
					.setPlacingRotation(RotationHelp.getNextRotation(module.powers().getBlueprintPower().getPlacingRotation(), true));

			return null;
		}
	}
}
