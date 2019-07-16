package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.init.InstancesAether;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.world.instances.necromancer_tower.NecromancerTowerInstanceHelper;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.world.instances.IInstance;
import com.gildedgames.orbis.lib.world.instances.IPlayerInstances;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketCancelIntro implements IMessage
{
	public static class HandlerServer extends MessageHandlerServer<PacketCancelIntro>
	{
		@Override
		protected void onMessage(PacketCancelIntro message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final NecromancerTowerInstanceHelper handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

			final IPlayerInstances hook = OrbisLib.instances().getPlayerInstanceData(player);

			if (hook.getInstance() != null)
			{
				final IInstance instance = hook.getInstance();

				if (player.dimension == instance.getDimensionId())
				{
					handler.teleportBack(player);
				}
			}
		}
	}

}
