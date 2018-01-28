package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.client.gui.overlays.PortalOverlay;
import com.gildedgames.aether.client.gui.overlays.SwetOverlay;
import com.gildedgames.aether.client.models.entities.player.LayerPlayerGloves;
import com.gildedgames.aether.client.models.entities.player.LayerSwetLatch;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.helpers.PlayerUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ClientRenderHandler
{

	private static final List<IOverlay> overlays = Lists.newArrayList();

	public ClientRenderHandler()
	{
		RenderLivingBase<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");

		playerRender.addLayer(new LayerPlayerGloves(playerRender));
		playerRender.addLayer(new LayerSwetLatch(playerRender));

		playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");

		playerRender.addLayer(new LayerPlayerGloves(playerRender));
		playerRender.addLayer(new LayerSwetLatch(playerRender));

		ClientRenderHandler.addOverlay(new PortalOverlay());
		ClientRenderHandler.addOverlay(new SwetOverlay());
	}

	public static void addOverlay(final IOverlay overlay)
	{
		ClientRenderHandler.overlays.add(overlay);
	}

	@SubscribeEvent
	public void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
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
	public void onRenderBlockOverlay(final RenderBlockOverlayEvent event)
	{
		final EntityPlayer entity = event.getPlayer();

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
		{
			if (entity.getAir() >= 295 && PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.iron_bubble))
			{
				event.setCanceled(true);
			}
		}
		else if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
		{
			if (PlayerUtil.isWearingEquipment(aePlayer, ItemsAether.phoenix_rune))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderSpecificHandEvent(final RenderSpecificHandEvent event)
	{
		if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack().isEmpty())
		{
			GlStateManager.pushMatrix();

			//GlStateManager.rotate(event.getInterpolatedPitch(), 1f, 0f, 0f);

			RenderPlayerHelper.renderFirstPersonHand(event, PlayerAether.getPlayer(Minecraft.getMinecraft().player));

			GlStateManager.popMatrix();
		}
	}

}
