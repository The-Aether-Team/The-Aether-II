package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RenderDebugOverlayListener
{

	@SubscribeEvent
	public static void onRenderDebugInfo(RenderGameOverlayEvent.Text event)
	{
		if (!Minecraft.getMinecraft().gameSettings.showDebugInfo)
		{
			return;
		}

		if (Minecraft.getMinecraft().world.getWorldInfo().isRaining())
		{
			IPrecipitationManager precipitation = Minecraft.getMinecraft().world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

			if (precipitation != null)
			{
				event.getLeft().add("");
				event.getLeft().add(TextFormatting.DARK_AQUA + "[" + TextFormatting.AQUA + "Aether Precipitation" + TextFormatting.DARK_AQUA + "]");
				event.getLeft().add("- Strength: " + precipitation.getStrength().name());
				event.getLeft().add("- Wind Velocity: (" + String.format("%.2f, %.2f", precipitation.getWindVector().x, precipitation.getWindVector().y) + ")");
			}
		}


	}
}
