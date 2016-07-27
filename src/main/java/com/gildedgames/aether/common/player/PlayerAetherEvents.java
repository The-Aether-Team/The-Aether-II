package com.gildedgames.aether.common.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.impl.hooks.BlockBitFlagChunkHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PlayerAetherEvents
{
	@SubscribeEvent
	public void onPlayerStartTracking(PlayerEvent.StartTracking event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

		PlayerAether aePlayer = PlayerAether.getPlayer(player);

		if (event.getTarget() instanceof EntityPlayer)
		{
			IInventoryEquipment equipment = aePlayer.getEquipmentInventory();

			List<Pair<Integer, ItemStack>> items = new ArrayList<>();

			for (int i = 0; i < equipment.getSizeInventory(); i++)
			{
				ItemStack stack = equipment.getStackInSlot(i);

				if (stack != null)
				{
					items.add(Pair.of(i, stack));
				}
			}

			NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(items), player);
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDeath(event);
		}
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onDrops(event);
		}
	}

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onUpdate(event);
		}
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onFall(event);
		}
	}

	@SubscribeEvent
	public void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
		}
	}

	@SubscribeEvent
	public void onLivingEntityHurt(LivingHurtEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			aePlayer.onHurt(event);
		}
	}


	@SubscribeEvent
	public void onPlayerTeleported(PlayerChangedDimensionEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onTeleport(event);
		}
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		IPlayerAetherCapability oldPlayer = PlayerAether.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			IPlayerAetherCapability newPlayer = PlayerAether.getPlayer(event.getEntity());

			Capability.IStorage<IPlayerAetherCapability> storage = AetherCapabilities.PLAYER_DATA.getStorage();

			NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_DATA, oldPlayer, null);

			storage.readNBT(AetherCapabilities.PLAYER_DATA, newPlayer, null, state);
		}
	}

	@SubscribeEvent
	public void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		BlockBitFlagChunkHook data = ChunkModule.api().getHook(event.getWorld(), event.getPos(), AetherCore.PROXY.getPlacementFlagProvider());

		if (data != null)
		{
			data.mark(event.getPos());
		}
	}
}
