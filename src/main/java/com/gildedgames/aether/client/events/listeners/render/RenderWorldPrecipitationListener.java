package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.renderer.world.RenderWorldPrecipitation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderWorldPrecipitationListener
{
	@SubscribeEvent
	public static void onClientRenderTick(TickEvent.ClientTickEvent event)
	{
		World world = Minecraft.getInstance().world;

		if (world == null || !world.isRemote)
		{
			return;
		}

		IRenderHandler handler = world.provider.getWeatherRenderer();

		if (handler instanceof RenderWorldPrecipitation)
		{
			((RenderWorldPrecipitation) handler).tick();
		}
	}
}
