package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRenderHandler
{
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
}
