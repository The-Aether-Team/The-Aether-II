package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.util.core.util.TeleporterGeneric;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.impl.hooks.BlockBitFlagChunkHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
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
			NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, PlayerAetherEvents.getAllEquipment(aePlayer.getEquipmentInventory())), player);
		}
	}

	@SubscribeEvent
	public static void onPlayerJoined(PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.player;

		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(player);

		NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, PlayerAetherEvents.getAllEquipment(aePlayer.getEquipmentInventory())), player);
	}

	private static List<Pair<Integer, ItemStack>> getAllEquipment(IInventoryEquipment equipment)
	{
		List<Pair<Integer, ItemStack>> stacks = new ArrayList<>();

		for (int i = 0; i < equipment.getSizeInventory(); i++)
		{
			stacks.add(Pair.of(i, equipment.getStackInSlot(i)));
		}

		return stacks;
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event)
	{
		PlayerAetherImpl aePlayer = PlayerAetherImpl.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			EntityPlayer player = aePlayer.getPlayer();

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

					aePlayer.getCompanionModule().syncCompanionItem();

					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentTranslation("chat.aether.resurrected", player.getDisplayName()));

					return;
				}
			}

			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public static void onDrops(LivingDropsEvent event)
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
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);
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
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		BlockBitFlagChunkHook data = ChunkModule.api().getHook(event.getWorld(), event.getPos(), AetherCore.PROXY.getPlacementFlagProvider());

		if (data != null)
		{
			data.mark(event.getPos());
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(event.player);

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
				Teleporter teleporter = new TeleporterGeneric(toWorld);

				CommonEvents.teleportEntity(mp, toWorld, teleporter, 0);

				BlockPos pos = toWorld.provider.getRandomizedSpawnPoint();

				mp.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);

				mp.getServerWorld().updateEntityWithOptionalForce(mp, true);
			}
		}
	}

}
