package com.gildedgames.aether.common.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.entities.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.items.IItemEffectsCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.companions.ItemDeathSeal;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.impl.hooks.BlockBitFlagChunkHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
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
			NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, this.getAllEquipment(aePlayer.getEquipmentInventory())), player);
		}
	}

	@SubscribeEvent
	public void onPlayerJoined(PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.player;

		PlayerAether aePlayer = PlayerAether.getPlayer(player);

		NetworkingAether.sendPacketToPlayer(new EquipmentChangedPacket(player, this.getAllEquipment(aePlayer.getEquipmentInventory())), player);

		IEntityEffectsCapability effects = EntityEffects.get(player);

		for (int i = 0; i < aePlayer.getEquipmentInventory().getSizeInventory(); i++)
		{
			ItemStack stack = aePlayer.getEquipmentInventory().getStackInSlot(i);

			if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				IItemEffectsCapability itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
				{
					EntityEffectProcessor processor = effect.getLeft();
					EntityEffectInstance instance = effect.getRight();

					effects.addInstance(processor, instance);
				}
			}
		}
	}

	private List<Pair<Integer, ItemStack>> getAllEquipment(IInventoryEquipment equipment)
	{
		List<Pair<Integer, ItemStack>> stacks = new ArrayList<>();

		for (int i = 0; i < equipment.getSizeInventory(); i++)
		{
			stacks.add(Pair.of(i, equipment.getStackInSlot(i)));
		}

		return stacks;
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.getEntity());

		if (aePlayer != null)
		{
			EntityPlayer player = aePlayer.getPlayer();

			ItemStack companionItem = aePlayer.getCompanionManager().getEquippedCompanionItem();

			if (companionItem != null && companionItem.getItem() instanceof ItemDeathSeal)
			{
				long ticksUntilUsable = ItemDeathSeal.getTicksUntilEnabled(companionItem, player.worldObj);

				if (ticksUntilUsable <= 0)
				{
					event.setCanceled(true);

					player.setHealth(0.5f);

					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 20 * 7, 3));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("resistance"), 20 * 7, 4));

					ItemDeathSeal.setDisabledTimer(companionItem, player.worldObj, 20 * 60 * 15);

					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentTranslation("chat.aether.resurrected", player.getDisplayName()));

					return;
				}
			}

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

	@SubscribeEvent
	public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
	{
		IPlayerAetherCapability aePlayer = PlayerAether.getPlayer(event.player);

		if (aePlayer != null)
		{
			aePlayer.onRespawn();
		}
	}

}
