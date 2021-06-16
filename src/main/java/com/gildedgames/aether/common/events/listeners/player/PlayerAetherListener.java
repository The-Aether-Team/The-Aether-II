package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PlayerAetherListener
{
	@SubscribeEvent
	public static void onBeginWatching(final PlayerEvent.StartTracking event)
	{
		if (!(event.getTarget() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aeSourcePlayer = PlayerAether.getPlayer(event.getEntityPlayer());
		final PlayerAether aeTargetPlayer = PlayerAether.getPlayer((EntityPlayer) event.getTarget());

		aeTargetPlayer.onPlayerBeginWatching(aeSourcePlayer);
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(final PlayerLoggedOutEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onLoggedOut();
	}

	@SubscribeEvent
	public static void onDeath(final LivingDeathEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onDeath(event);

		if (aePlayer.getEntity().world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			aePlayer.getModule(PlayerCampfiresModule.class).setDeathPos(new BlockPosDimension(event.getEntity().getPosition(), aePlayer.getEntity().dimension));
		}
	}

	@SubscribeEvent
	public static void onDrops(final PlayerDropsEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onDrops(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onPlayerTick(event);
	}

	@SubscribeEvent
	public static void onFall(final LivingFallEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		aePlayer.onFall(event);
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(final PlayerEvent.BreakSpeed event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());
		event.setNewSpeed(event.getOriginalSpeed() * aePlayer.getMiningSpeedMultiplier());
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
		{
			return;
		}

		final PlayerAether oldPlayer = PlayerAether.getPlayer(event.getOriginal());
		final PlayerAether newPlayer = PlayerAether.getPlayer((EntityPlayer) event.getEntity());

		final IStorage<IPlayerAether> storage = CapabilitiesAether.PLAYER_DATA.getStorage();

		final NBTBase state = storage.writeNBT(CapabilitiesAether.PLAYER_DATA, oldPlayer, null);
		storage.readNBT(CapabilitiesAether.PLAYER_DATA, newPlayer, null, state);
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(final PlayerChangedDimensionEvent event)
	{
		final PlayerAether aePlayer = PlayerAether.getPlayer(event.player);
		aePlayer.onTeleport(event);
	}

}
