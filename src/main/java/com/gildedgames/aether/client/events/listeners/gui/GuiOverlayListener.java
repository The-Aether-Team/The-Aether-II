package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.client.gui.overlays.OverlayPortal;
import com.gildedgames.aether.client.gui.overlays.OverlaySwet;
import com.gildedgames.aether.client.models.entities.player.*;
import com.gildedgames.aether.client.renderer.particles.ParticleRainProxyFactory;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiOverlayListener
{
	private static final List<IOverlay> overlays = Lists.newArrayList();

	public static void init()
	{
		for (RenderLivingBase<?> playerRender : new HashSet<>(Minecraft.getInstance().getRenderManager().getSkinMap().values()))
		{
			List<LayerRenderer<?>> original = new ArrayList<>(playerRender.layerRenderers);
			List<LayerRenderer<?>> updated = new ArrayList<>();

			for (LayerRenderer<?> i : original)
			{
				if (i instanceof LayerBipedArmor)
				{
					updated.add(new LayerArmorProxy(playerRender, (LayerBipedArmor) i));
				}
				else
				{
					updated.add(i);
				}
			}

			updated.add(new LayerAetherPlayerGloves(playerRender));
			updated.add(new LayerHeadShadow(playerRender));
			updated.add(new LayerSwetLatch(playerRender));
			updated.add(new LayerAetherPatronArmor(playerRender));

			original.clear();
			original.addAll(updated);
		}

		overlays.add(new OverlayPortal());
		overlays.add(new OverlaySwet());

		// See documentation of ParticleRainProxyFactory. Ugly hack.
		Minecraft.getInstance().getRenderManager().registerParticle(ParticleTypes.WATER_DROP.getParticleID(), new ParticleRainProxyFactory());
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
