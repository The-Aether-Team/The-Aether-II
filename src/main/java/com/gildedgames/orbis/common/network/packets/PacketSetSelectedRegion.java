package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis_core.world_objects.WorldShape;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetSelectedRegion implements IMessage
{
	private int regionId;

	public PacketSetSelectedRegion()
	{

	}

	public PacketSetSelectedRegion(final int regionId)
	{
		this.regionId = regionId;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.regionId = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.regionId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSetSelectedRegion, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetSelectedRegion message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final WorldShape shape = WorldObjectManager.get(player.world).getGroup(0).getObject(message.regionId);

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			module.powers().getSelectPower().setSelectedRegion(shape);

			return null;
		}
	}
}
