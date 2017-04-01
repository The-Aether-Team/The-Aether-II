package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.capabilites.entity.stats.EntityStats;
import com.gildedgames.aether.api.capabilites.entity.stats.IEntityStat;
import com.gildedgames.aether.api.capabilites.entity.stats.IEntityStatContainer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.DiedInAetherPacket;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import java.util.Optional;

public class PlayerAetherEvents
{
	@SubscribeEvent
	public static void onPlayerJoined(PlayerLoggedInEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			NetworkingAether.sendPacketToPlayer(new DiedInAetherPacket(aePlayer.hasDiedInAetherBefore()), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public static void onDrops(PlayerDropsEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingUpdateEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}

		// TODO: remove this dumb debug effect
		if (event.getEntity().hasCapability(AetherCapabilities.ENTITY_STATS, null))
		{
			Entity entity = event.getEntity();

			IEntityStatContainer container = entity.getCapability(AetherCapabilities.ENTITY_STATS, null);

			Optional<IEntityStat> stat = container.getStat(EntityStats.VOLATILE);

			if (stat.isPresent())
			{
				double attempt = Math.random();
				double threshold = stat.get().getValue();

				if (attempt <= threshold)
				{
					entity.getEntityWorld().newExplosion(entity, entity.posX, entity.posY, entity.posZ, 2.0f, false, true);
					entity.setDead();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		IPlacementFlagCapability data = ChunkAttachment.get(event.getWorld()).getAttachment(new ChunkPos(event.getPos()), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		if (data != null)
		{
			data.mark(event.getPos());
		}

		PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		if (aePlayer != null)
		{
			aePlayer.onPlaceBlock(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		PlayerAether oldPlayer = PlayerAether.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			PlayerAether newPlayer = PlayerAether.getPlayer(event.getEntity());

			IStorage<IPlayerAether> storage = AetherCapabilities.PLAYER_DATA.getStorage();

			NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);
			storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onRespawn(event);
		}
	}
}
