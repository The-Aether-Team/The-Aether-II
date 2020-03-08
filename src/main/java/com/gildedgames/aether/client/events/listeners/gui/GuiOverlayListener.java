package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.client.gui.overlays.PortalOverlay;
import com.gildedgames.aether.client.gui.overlays.SwetOverlay;
import com.gildedgames.aether.client.models.entities.player.*;
import com.gildedgames.aether.client.renderer.particles.ParticleRainProxyFactory;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiOverlayListener
{
	private static final List<IOverlay> overlays = Lists.newArrayList();

	public static void init()
	{
		for (RenderLivingBase<?> playerRender : new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
		{
			playerRender.addLayer(new LayerArmorProxy(playerRender, new LayerAetherPlayerGloves(playerRender)));
			playerRender.addLayer(new LayerArmorProxy(playerRender, new LayerHeadShadow(playerRender)));
			playerRender.addLayer(new LayerArmorProxy(playerRender, new LayerSwetLatch(playerRender)));
			playerRender.addLayer(new LayerAetherPatronArmor(playerRender));
		}

		overlays.add(new PortalOverlay());
		overlays.add(new SwetOverlay());

		// See documentation of ParticleRainProxyFactory. Ugly hack.
		Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new ParticleRainProxyFactory());
	}

	@SubscribeEvent
	public static void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			for (final IOverlay overlay : overlays)
			{
				if (overlay.isEnabled())
				{
					overlay.draw();
				}
			}
		}
	}
}
