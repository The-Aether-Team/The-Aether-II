package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachmentCapability;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffectsProvider;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityPropertiesProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.item.ItemBreakable;
import com.gildedgames.aether.common.capabilities.item.ItemBreakableProvider;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffects;
import com.gildedgames.aether.common.capabilities.item.effects.ItemEffectsProvider;
import com.gildedgames.aether.common.capabilities.item.properties.ItemPropertiesImpl;
import com.gildedgames.aether.common.capabilities.item.properties.ItemPropertiesProvider;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherProvider;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentProvider;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.PlacementFlagCapability;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.PlacementFlagProvider;
import com.gildedgames.aether.common.world.chunk.hooks.events.AttachCapabilitiesChunkEvent;
import com.gildedgames.aether.common.capabilities.instances.InstanceRegistryImpl;
import com.gildedgames.aether.common.capabilities.instances.PlayerInstances;
import com.gildedgames.aether.common.capabilities.instances.PlayerInstancesProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManagerAether
{

	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(CapabilityManagerAether.class);

		CapabilityManager.INSTANCE.register(IItemEffectsCapability.class, new ItemEffects.Storage(), ItemEffects.class);
		CapabilityManager.INSTANCE.register(IItemPropertiesCapability.class, new ItemPropertiesImpl.Storage(), ItemPropertiesImpl.class);
		CapabilityManager.INSTANCE.register(IPlayerAetherCapability.class, new PlayerAetherImpl.Storage(), PlayerAetherImpl.class);
		CapabilityManager.INSTANCE.register(IEntityEffectsCapability.class, new EntityEffects.Storage(), EntityEffects.class);
		CapabilityManager.INSTANCE.register(IEntityPropertiesCapability.class, new EntityProperties.Storage(), EntityProperties.class);
		CapabilityManager.INSTANCE.register(IItemBreakable.class, new ItemBreakable.Storage(), ItemBreakable.class);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo.class);
		CapabilityManager.INSTANCE.register(IPlacementFlagCapability.class, new PlacementFlagCapability.Storage(), PlacementFlagCapability.class);
		CapabilityManager.INSTANCE.register(IChunkAttachmentCapability.class, new ChunkAttachmentCapability.Storage(), ChunkAttachmentCapability.class);
		CapabilityManager.INSTANCE.register(IPlayerInstances.class, new PlayerInstances.Storage(), PlayerInstances.class);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();

			for (int index = 0; index < player.inventory.getSizeInventory(); index++)
			{
				ItemStack stack = player.inventory.getStackInSlot(index);

				if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_BREAKABLE, null))
				{
					IItemBreakable breakable = stack.getCapability(AetherCapabilities.ITEM_BREAKABLE, null);

					if (!breakable.canBreak() && stack.isItemStackDamageable())
					{
						if (!stack.hasTagCompound())
						{
							stack.setTagCompound(new NBTTagCompound());
						}

						if (!stack.getTagCompound().getBoolean("Unbreakable"))
						{
							stack.getTagCompound().setBoolean("Unbreakable", true);
							stack.setItemDamage(stack.getMaxDamage());
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();

		ItemStack stack = player.getActiveItemStack();

		if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
		{
			IItemPropertiesCapability props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

			IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(player);

			if (props != null && props.isEquippable())
			{
				int nextEmptySlot = aePlayer.getEquipmentInventory().getNextEmptySlotForType(props.getEquipmentType());

				if (nextEmptySlot != -1)
				{
					aePlayer.getEquipmentInventory().setInventorySlotContents(nextEmptySlot, stack.copy());

					if (!player.capabilities.isCreativeMode)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityLoad(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() == null)
		{
			return;
		}

		event.addCapability(AetherCore.getResource("EntityEffects"), new EntityEffectsProvider(new EntityEffects(event.getEntity())));
		event.addCapability(AetherCore.getResource("EntityProperties"), new EntityPropertiesProvider(new EntityProperties(event.getEntity())));
		event.addCapability(AetherCore.getResource("EntitySpawningInfo"), new EntitySpawningInfoProvider());

		if (event.getEntity() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAetherImpl((EntityPlayer) event.getEntity())));
			event.addCapability(AetherCore.getResource("PlayerInstances"), new PlayerInstancesProvider((EntityPlayer) event.getEntity()));
		}
	}

	@SubscribeEvent
    public static void onItemLoad(AttachCapabilitiesEvent.Item event)
    {
		ItemEffects.ItemEffectsProvider provider = ItemEffects.getProvider(event.getItem());

		ItemEffects effects = new ItemEffects(provider == null ? null : provider.provide());
		event.addCapability(AetherCore.getResource("ItemStackEffects"), new ItemEffectsProvider(effects));

        event.addCapability(AetherCore.getResource("ItemStackProperties"), new ItemPropertiesProvider(event.getItemStack()));
		event.addCapability(AetherCore.getResource("ItemBreakable"), new ItemBreakableProvider());
    }

    @SubscribeEvent
	public static void onWorldLoad(AttachCapabilitiesEvent.World event)
	{
		event.addCapability(AetherCore.getResource("AetherHooks"), new ChunkAttachmentProvider(new ChunkAttachmentCapability()));
	}

	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachmentCapability pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);

			pool.destroy(event);
		}
	}

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachmentCapability pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);

			pool.init(event);
		}
	}

    @SubscribeEvent
	public static void onChunkCapabilityAttach(AttachCapabilitiesChunkEvent event)
	{
		event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
	}

	@SubscribeEvent
	public static void onChunkDataLoaded(ChunkDataEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachmentCapability pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);

			pool.load(event);
		}
	}

	@SubscribeEvent
	public static void onChunkDataUnloaded(ChunkDataEvent.Save event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachmentCapability pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);

			pool.save(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		IPlayerInstances oldPlayer = AetherAPI.instances().getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			IPlayerInstances newPlayer = AetherAPI.instances().getPlayer((EntityPlayer) event.getEntity());

			Capability.IStorage<IPlayerInstances> storage = AetherCapabilities.PLAYER_INSTANCES.getStorage();

			NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_INSTANCES, oldPlayer, null);

			storage.readNBT(AetherCapabilities.PLAYER_INSTANCES, newPlayer, null, state);
		}
	}

}
