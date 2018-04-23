package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceHandler;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.world.instances.IInstance;
import com.gildedgames.orbis_api.world.instances.IPlayerInstances;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCancelIntro implements IMessage
{

	public PacketCancelIntro()
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

	public static class HandlerServer extends MessageHandlerServer<PacketCancelIntro, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketCancelIntro message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final NecromancerTowerInstanceHandler handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

			final IPlayerInstances hook = OrbisAPI.instances().getPlayer(player);

			if (hook.getInstance() != null)
			{
				final IInstance instance = hook.getInstance();

				if (player.dimension == instance.getDimensionId())
				{
					handler.teleportBack((EntityPlayerMP) player);
				}
			}

			return null;
		}
	}

}
