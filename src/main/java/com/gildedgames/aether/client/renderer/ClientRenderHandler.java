package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.client.gui.overlays.PortalOverlay;
import com.gildedgames.aether.client.gui.overlays.SwetOverlay;
import com.gildedgames.aether.client.models.entities.player.*;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.client.renderer.particles.ParticleRainProxyFactory;
import com.gildedgames.aether.client.renderer.world.RenderWorldPrecipitation;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientRenderHandler
{
	private static final List<IOverlay> overlays = Lists.newArrayList();

	public static void init()
	{
		for (RenderLivingBase<?> playerRender : new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
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

		ClientRenderHandler.overlays.add(new PortalOverlay());
		ClientRenderHandler.overlays.add(new SwetOverlay());

		// See documentation of ParticleRainProxyFactory. Ugly hack.
		Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new ParticleRainProxyFactory());
	}

	@SubscribeEvent
	public static void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
		{
			final EntityPlayer player = Minecraft.getMinecraft().player;

			if (player.getAir() == 300 && player.isPotionActive(MobEffects.WATER_BREATHING))
			{
				event.setCanceled(true);
			}
		}

		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			for (final IOverlay overlay : ClientRenderHandler.overlays)
			{
				if (overlay.isEnabled())
				{
					overlay.draw();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onRenderSpecificHandEvent(final RenderSpecificHandEvent event)
	{
		if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack().isEmpty())
		{
			GlStateManager.pushMatrix();

			//GlStateManager.rotate(event.getInterpolatedPitch(), 1f, 0f, 0f);

			RenderPlayerHelper.renderFirstPersonHand(event, PlayerAether.getPlayer(Minecraft.getMinecraft().player));

			GlStateManager.popMatrix();
		}
	}

	@SubscribeEvent
	public static void onRenderDebugInfo(RenderGameOverlayEvent.Text event)
	{
		if (!Minecraft.getMinecraft().gameSettings.showDebugInfo)
		{
			return;
		}

		if (Minecraft.getMinecraft().world.getWorldInfo().isRaining())
		{
			IPrecipitationManager precipitation = Minecraft.getMinecraft().world.getCapability(AetherCapabilities.PRECIPITATION_MANAGER, null);

			if (precipitation != null)
			{
				event.getLeft().add("");
				event.getLeft().add(TextFormatting.DARK_AQUA + "[" + TextFormatting.AQUA + "Aether Precipitation" + TextFormatting.DARK_AQUA + "]");
				event.getLeft().add("- Strength: " + precipitation.getStrength().name());
				event.getLeft().add("- Wind Velocity: (" + String.format("%.2f, %.2f", precipitation.getWindVector().x, precipitation.getWindVector().y) + ")");
			}
		}
	}

	@SubscribeEvent
	public static void onClientRenderTick(TickEvent.ClientTickEvent event)
	{
		World world = Minecraft.getMinecraft().world;

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
