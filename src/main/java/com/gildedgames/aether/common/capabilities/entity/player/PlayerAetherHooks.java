package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.entities.util.shared.SharedAetherAttributes;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMarkPlayerDeath;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.chunk.capabilities.ChunkAttachment;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.gildedgames.aether.common.world.util.TeleporterGeneric;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
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

import java.util.List;

public class PlayerAetherHooks
{
	@SubscribeEvent
	public static void onPlayerJoined(PlayerLoggedInEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.sendFullUpdate();
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
		EntityLivingBase entity = event.getEntityLiving();


		IAttributeInstance attribute = entity.getEntityAttribute(SharedAetherAttributes.STAT_VOLATILE);

		if (attribute == null)
		{
			return;
		}

		double attempt = Math.random();
		double threshold = attribute.getAttributeValue();

		if (attempt <= threshold)
		{
			entity.getEntityWorld().newExplosion(entity, entity.posX, entity.posY, entity.posZ, 2.0f, false, true);
			entity.setDead();
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		IPlacementFlagCapability data = ChunkAttachment.get(event.getWorld()).getAttachment(new ChunkPos(event.getPos()), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		if (data != null)
		{
			data.markModified(event.getPos());
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
	public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event)
	{
		PlayerAether aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);
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

		if (event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(((EntityPlayerMP) event.player).dimension);
			EntityPlayerMP mp = (EntityPlayerMP)event.player;
			WorldServer toWorld = DimensionManager.getWorld(0);

			if (bedPos != null)
			{
				bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			if (bedPos == null)
			{
				List<IslandData> islands = IslandSectorAccess.inst().getAllIslands(mp.world, aePlayer.getEntity().getPosition());
				boolean shouldSpawnAtOutpost = false;
				boolean obstructed = false;
				IslandData island = null;

				for (IslandData data : islands)
				{
					if (data != null && data.getMysteriousHengePos() != null)
					{
						shouldSpawnAtOutpost = true;
						island = data;
						break;
					}
				}

				BlockPos pos = null;

				if (shouldSpawnAtOutpost)
				{
					pos = mp.world.getTopSolidOrLiquidBlock(island.getMysteriousHengePos());
					BlockPos down = pos.down();
					obstructed = mp.getServerWorld().getBlockState(pos.up()) != Blocks.AIR.getDefaultState() || mp.getServerWorld().getBlockState(pos.up(2)) != Blocks.AIR.getDefaultState();
					shouldSpawnAtOutpost = BlockUtil.isSolid(mp.getServerWorld().getBlockState(pos.down())) && !obstructed;

					mp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
					if (!aePlayer.hasDiedInAetherBefore())
					{
						// from 1.10 code; looks like EDISION_GUI_ID has not been implemented yet.
						//mp.openGui(AetherCore.INSTANCE, AetherGuiHandler.EDISON_GUI_ID, mp.worldObj, pos.getX(), pos.getY(), pos.getZ());

						aePlayer.setHasDiedInAetherBefore(true);

						NetworkingAether.sendPacketToPlayer(new PacketMarkPlayerDeath(), mp);
					}
				}
				else
				{
					Teleporter teleporter = new TeleporterGeneric(toWorld);

					CommonEvents.teleportEntity(mp, toWorld, teleporter, 3);
					pos = toWorld.provider.getRandomizedSpawnPoint();
					mp.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
				}

				if (obstructed)
				{
					mp.sendMessage(new TextComponentString("The nearest Mysterious Henge was obstructed with blocks - you could not respawn there."));
				}
			}

		}
	}

	@SubscribeEvent
	public static void onBeginWatching(PlayerEvent.StartTracking event)
	{
		PlayerAether aeSourcePlayer = PlayerAether.getPlayer(event.getEntityPlayer());
		PlayerAether aeTargetPlayer = PlayerAether.getPlayer(event.getTarget());

		if (aeSourcePlayer != null && aeTargetPlayer != null)
		{
			aeTargetPlayer.onPlayerBeginWatching(aeSourcePlayer);
		}

	}
}
