package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerWakeListener
{
	@SubscribeEvent
	public static void onPlayerSleepInBed(final PlayerWakeUpEvent event)
	{
		final World world = event.getEntityPlayer().world;

		if (!world.isRemote() && world.getDimension().getType() == DimensionsAether.AETHER)
		{
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			final ServerWorld worldServer = server.getWorld(0);

			if (world.getGameRules().getBoolean("doDaylightCycle") && event.getEntityPlayer().isPlayerFullyAsleep())
			{
				final long i = worldServer.getWorldInfo().getWorldTime() + 24000L;

				worldServer.getWorldInfo().setWorldTime(i - i % 24000L);
			}
		}
	}

}
