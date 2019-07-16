package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.orbis.lib.util.mc.BlockUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class PlayerRespawnListener
{

	@SubscribeEvent
	public static void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onRespawn(event);

		if (event.player instanceof ServerPlayerEntity && ((ServerPlayerEntity) event.player).world.getDimension().getType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(((ServerPlayerEntity) event.player).dimension);
			final ServerPlayerEntity mp = (ServerPlayerEntity) event.player;

			if (bedPos != null)
			{
				bedPos = PlayerEntity.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			PlayerCampfiresModule campfiresModule = aePlayer.getModule(PlayerCampfiresModule.class);
			BlockPos closestCampfire = campfiresModule.getClosestCampfire();
			BlockPos islandSpawn = IslandHelper.getOutpostPos(mp.getServerWorld(), mp.getPosition());

			final BlockPos respawnPoint = closestCampfire != null ? closestCampfire.add(-1, 0, -1) : islandSpawn;
			boolean obstructed = false;

			if (campfiresModule.shouldRespawnAtCampfire() || bedPos == null)
			{
				BlockPos pos = mp.world.getTopSolidOrLiquidBlock(respawnPoint);

				if (pos.getY() > respawnPoint.getY())
				{
					pos = respawnPoint;
				}

				final BlockPos down = pos.down();

				obstructed = mp.getServerWorld().getBlockState(pos.up()) != Blocks.AIR.getDefaultState()
						|| mp.getServerWorld().getBlockState(pos.up(2)) != Blocks.AIR.getDefaultState();

				if (BlockUtil.isSolid(mp.getServerWorld().getBlockState(down)) && !obstructed)
				{
					mp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);

					PlayerProgressModule progressModule = aePlayer.getModule(PlayerProgressModule.class);

					if (!progressModule.hasDiedInAether())
					{
						progressModule.setHasDiedInAether(true);

						NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(progressModule.hasDiedInAether()), mp);
					}
				}

				campfiresModule.setShouldRespawnAtCampfire(false);
			}

			if (obstructed)
			{
				mp.sendMessage(new StringTextComponent("The nearest Outpost was obstructed with blocks - you could not respawn there."));
			}
		}
	}
}
