package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderFirstPersonHandListener
{
	@SubscribeEvent
	public static void onRenderSpecificHandEvent(final RenderSpecificHandEvent event)
	{
		if (event.getHand() == Hand.MAIN_HAND && event.getItemStack().isEmpty())
		{
			GlStateManager.pushMatrix();

			//GlStateManager.rotatef(event.getInterpolatedPitch(), 1f, 0f, 0f);

			RenderPlayerHelper.renderFirstPersonHand(event, PlayerAether.getPlayer(Minecraft.getInstance().player));

			GlStateManager.popMatrix();
		}
	}

}
