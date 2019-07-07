package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderFirstPersonHandListener
{
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

}
