package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.gui.PerformanceIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderPerformanceIndicatorListener
{
	private static final PerformanceIngame PERFORMANCE_LOGGER = new PerformanceIngame();

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderGui(final RenderGameOverlayEvent event)
	{
		final ScaledResolution scaledRes = new ScaledResolution(Minecraft.getInstance());

		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			PERFORMANCE_LOGGER.renderIcon();
		}
	}
}
