package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.world.chunk.ChunkAttachment;
import com.gildedgames.aether.common.entities.util.shared.SharedAetherAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerAetherHooks
{
	@SubscribeEvent
	public static void onPlayerJoined(final PlayerLoggedInEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.sendFullUpdate();
		}
	}

	@SubscribeEvent
	public static void onDeath(final LivingDeathEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public static void onDrops(final PlayerDropsEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public static void onUpdate(final LivingUpdateEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public static void onFall(final LivingFallEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(final PlayerEvent.BreakSpeed event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(final LivingHurtEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}

		// TODO: remove this dumb debug effect
		final EntityLivingBase entity = event.getEntityLiving();

		final IAttributeInstance attribute = entity.getEntityAttribute(SharedAetherAttributes.STAT_VOLATILE);

		if (attribute == null)
		{
			return;
		}

		final double attempt = Math.random();
		final double threshold = attribute.getAttributeValue();

		if (attempt <= threshold)
		{
			entity.getEntityWorld().newExplosion(entity, entity.posX, entity.posY, entity.posZ, 2.0f, false, true);
			entity.setDead();
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(final BlockEvent.PlaceEvent event)
	{
		final IPlacementFlagCapability data = ChunkAttachment.get(event.getWorld())
				.getAttachment(new ChunkPos(event.getPos()), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		if (data != null)
		{
			data.markModified(event.getPos());
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		if (aePlayer != null)
		{
			aePlayer.onPlaceBlock(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{
		final PlayerAether oldPlayer = PlayerAether.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			final PlayerAether newPlayer = PlayerAether.getPlayer(event.getEntity());

			final IStorage<IPlayerAether> storage = AetherCapabilities.PLAYER_DATA.getStorage();

			final NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);
			storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(final PlayerChangedDimensionEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(final PlayerRespawnEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onRespawn(event);
		}

		/*if (event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(((EntityPlayerMP) event.player).dimension);
			final EntityPlayerMP mp = (EntityPlayerMP) event.player;
			final WorldServer toWorld = DimensionManager.getWorld(0);

			if (bedPos != null)
			{
				bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			if (bedPos == null)
			{
				final ISector loadedSector = IslandSectorHelper.getAccess(mp.world)
						.getLoadedSector(aePlayer.getEntity().chunkCoordX, aePlayer.getEntity().chunkCoordZ)
						.orElseThrow(() -> new IllegalArgumentException("Couldn't find island sector:"));
				final Collection<IIslandData> islands = loadedSector
						.getIslandsForRegion(aePlayer.getEntity().getPosition().getX(), 0, aePlayer.getEntity().getPosition().getZ(), 1, 255, 1);

				boolean shouldSpawnAtRespawnPoint = false;
				boolean obstructed = false;
				IIslandData island = null;

				for (final IIslandData data : islands)
				{
					if (data != null && data.getRespawnPoint() != null)
					{
						shouldSpawnAtRespawnPoint = true;
						island = data;
						break;
					}
				}

				BlockPos pos = null;

				if (shouldSpawnAtRespawnPoint)
				{
					pos = mp.world.getTopSolidOrLiquidBlock(island.getRespawnPoint());
					final BlockPos down = pos.down();
					obstructed = mp.getServerWorld().getBlockState(pos.up()) != Blocks.AIR.getDefaultState()
							|| mp.getServerWorld().getBlockState(pos.up(2)) != Blocks.AIR.getDefaultState();
					shouldSpawnAtRespawnPoint = BlockUtil.isSolid(mp.getServerWorld().getBlockState(pos.down())) && !obstructed;

					mp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
					if (!aePlayer.hasDiedInAetherBefore())
					{
						//TODO: Reimplement
						// from 1.10 code; looks like EDISION_GUI_ID has not been implemented yet.
						//mp.openGui(AetherCore.INSTANCE, AetherGuiHandler.EDISON_GUI_ID, mp.worldObj, pos.getX(), pos.getY(), pos.getZ());

						aePlayer.setHasDiedInAetherBefore(true);

						//NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(aePlayer.hasDiedInAetherBefore()), mp);
					}
				}
				else
				{
					final Teleporter teleporter = new TeleporterGeneric(toWorld);

					CommonEvents.teleportEntity(mp, toWorld, teleporter, 3);
					pos = toWorld.provider.getRandomizedSpawnPoint();
					mp.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
				}

				if (obstructed)
				{
					mp.sendMessage(new TextComponentString("The nearest Outpost was obstructed with blocks - you could not respawn there."));
				}
			}

		}*/
	}

	@SubscribeEvent
	public static void onBeginWatching(final PlayerEvent.StartTracking event)
	{
		final PlayerAether aeSourcePlayer = PlayerAether.getPlayer(event.getEntityPlayer());
		final PlayerAether aeTargetPlayer = PlayerAether.getPlayer(event.getTarget());

		if (aeSourcePlayer != null && aeTargetPlayer != null)
		{
			aeTargetPlayer.onPlayerBeginWatching(aeSourcePlayer);
		}
	}
}
