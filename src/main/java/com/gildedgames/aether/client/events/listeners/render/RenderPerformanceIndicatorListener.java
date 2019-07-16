package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.gui.PerformanceIngame;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderPerformanceIndicatorListener
{
	private static final PerformanceIngame PERFORMANCE_LOGGER = new PerformanceIngame();

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderGui(final RenderGameOverlayEvent event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			PERFORMANCE_LOGGER.renderIcon();
		}
	}
}
