package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.capabilities.player.modules.EquipmentModule;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.gildedgames.aether.common.world.util.TeleporterGeneric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import java.util.List;

public class PlayerAetherEvents
{
	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);

		if (event.getTarget() instanceof EntityPlayer)
		{
			NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, EquipmentModule.getAllEquipment(aePlayer.getEquipmentInventory())), player);
		}
	}

	@SubscribeEvent
	public static void onPlayerJoined(PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.player;

		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);

		NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, EquipmentModule.getAllEquipment(aePlayer.getEquipmentInventory())), player);
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event)
	{
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			EntityPlayer player = aePlayer.getPlayer();

			if (player instanceof EntityPlayerMP)
			{
				aePlayer.setDeathPos(player.getPosition());
			}

			ItemStack companionItem = aePlayer.getCompanionModule().getEquippedCompanionItem();

			if (companionItem != null && companionItem.getItem() instanceof ItemDeathSeal)
			{
				long ticksUntilUsable = ItemDeathSeal.getTicksUntilEnabled(companionItem, player.worldObj);

				if (ticksUntilUsable <= 0)
				{
					event.setCanceled(true);

					player.setHealth(0.5f);

					player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 * 7, 3));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 7, 4));

					ItemDeathSeal.setDisabledTimer(companionItem, player.worldObj, 20 * 60 * 15);

					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentTranslation("chat.aether.resurrected", player.getDisplayName()));

					return;
				}
			}

			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public static void onDrops(PlayerDropsEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public static void onUpdate(LivingUpdateEvent event)
	{
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}
	}


	@SubscribeEvent
	public static void onPlayerTeleported(PlayerChangedDimensionEvent event)
	{
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);

			if (!event.player.getEntityWorld().isRemote)
			{
				NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(event.player, EquipmentModule.getAllEquipment(aePlayer.getEquipmentInventory())), (EntityPlayerMP) event.player);
			}

			//aePlayer.getEquipmentModule().resetEffects();
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		IPlayerAetherCapability oldPlayer = PlayerAetherImpl.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			PlayerAetherImpl newPlayer = PlayerAetherImpl.getPlayer(event.getEntity());

			Capability.IStorage<IPlayerAetherCapability> storage = AetherCapabilities.PLAYER_DATA.getStorage();

			NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);

			storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);

			//newPlayer.getEquipmentModule().resetEffects();
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		IPlacementFlagCapability data = ChunkAttachmentCapability.get(event.getWorld()).getAttachment(new ChunkPos(event.getPos()), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

		if (data != null)
		{
			data.mark(event.getPos());
		}

		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.getPlayer());

		if (aePlayer != null)
		{
			aePlayer.onPlaceBlock(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onRespawn();
		}

		if (event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).worldObj.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			BlockPos bedPos = event.player.getBedLocation(event.player.dimension);

			EntityPlayerMP mp = (EntityPlayerMP)event.player;

			WorldServer toWorld = DimensionManager.getWorld(0);

			if (bedPos != null)
			{
				bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
			}

			if (bedPos == null)
			{
				List<IslandData> islands = IslandSectorAccess.inst().getAllIslands(mp.worldObj, aePlayer.getDeathPos());

				boolean shouldSpawnAtHenge = false;
				boolean obstructed = false;

				IslandData island = null;

				for (IslandData data : islands)
				{
					if (data != null && data.getMysteriousHengePos() != null)
					{
						shouldSpawnAtHenge = true;
						island = data;

						break;
					}
				}

				BlockPos pos = null;

				if (shouldSpawnAtHenge)
				{
					pos = BlockUtil.getTopSolidOrLiquidBlockFromY(mp.getServerWorld(), island.getMysteriousHengePos());

					BlockPos down = pos.down();

					obstructed = mp.getServerWorld().getBlockState(pos.up()) != Blocks.AIR.getDefaultState() || mp.getServerWorld().getBlockState(pos.up().up()) != Blocks.AIR.getDefaultState();

					shouldSpawnAtHenge = BlockUtil.isSolid(mp.getServerWorld(), down) && !obstructed;
				}

				if (shouldSpawnAtHenge)
				{
					mp.connection.setPlayerLocation(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
				}
				else
				{
					Teleporter teleporter = new TeleporterGeneric(toWorld);

					CommonEvents.teleportEntity(mp, toWorld, teleporter, 0);

					pos = toWorld.provider.getRandomizedSpawnPoint();

					mp.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
				}

				if (obstructed)
				{
					mp.addChatComponentMessage(new TextComponentString("The nearest Mysterious Henge was obstructed with blocks - you could not respawn there."));
				}
			}
		}
	}

}
