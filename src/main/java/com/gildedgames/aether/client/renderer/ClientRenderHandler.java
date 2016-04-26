package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.models.entities.player.LayerPlayerGloves;
import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRenderHandler
{
	public static void init()
	{
		RenderManager rManager = Minecraft.getMinecraft().getRenderManager();

		RendererLivingEntity<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		playerRender.addLayer(new LayerPlayerGloves(playerRender));

		playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		playerRender.addLayer(new LayerPlayerGloves(playerRender));
	}

	@SubscribeEvent
	public void onRenderIngameOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (event.type == RenderGameOverlayEvent.ElementType.AIR)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			if (player.getAir() >= 295 && PlayerUtil.isWearingFullSet(Minecraft.getMinecraft().thePlayer, ItemNeptuneArmor.class))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
	{
		if (event.overlayType == RenderBlockOverlayEvent.OverlayType.WATER)
		{
			if (PlayerUtil.isWearingFullSet(Minecraft.getMinecraft().thePlayer, ItemNeptuneArmor.class))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerEvent(RenderPlayerEvent.Post event)
	{
		RenderPlayerHelper.renderFirstPersonHand(PlayerAether.get(Minecraft.getMinecraft().thePlayer), event.partialRenderTick);
	}
}
