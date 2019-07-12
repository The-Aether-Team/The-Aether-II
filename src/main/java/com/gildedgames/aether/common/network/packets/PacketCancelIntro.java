package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.init.InstancesAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.world.instances.necromancer_tower.NecromancerTowerInstanceHelper;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.world.instances.IInstance;
import com.gildedgames.orbis.lib.world.instances.IPlayerInstances;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
		public IMessage onMessage(final PacketCancelIntro message, final PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final NecromancerTowerInstanceHelper handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

			final IPlayerInstances hook = OrbisLib.instances().getPlayer(player);

			if (hook.getInstance() != null)
			{
				final IInstance instance = hook.getInstance();

				if (player.dimension == instance.getDimensionId())
				{
					handler.teleportBack((ServerPlayerEntity) player);
				}
			}

			return null;
		}
	}

}
