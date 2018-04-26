package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagCapability;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManagerAether
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(CapabilityManagerAether.class);

		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAether.Storage(), PlayerAether::new);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo::new);
	}

	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() == null)
		{
			return;
		}

		event.addCapability(AetherCore.getResource("EntitySpawningInfo"), new EntitySpawningInfoProvider());

		if (event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAether((EntityPlayer) event.getObject())));
		}
	}

	@SubscribeEvent
	public static void attachChunk(final AttachCapabilitiesEvent<Chunk> event)
	{
		if (event.getObject() == null)
		{
			return;
		}

		if (event.getObject() instanceof Chunk)
		{
			event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
		}
	}

}
