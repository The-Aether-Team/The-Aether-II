package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.client.renderer.entities.living.RenderPlayerHelper;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RenderFirstPersonHandListener
{
	@SubscribeEvent
	public static void onRenderSpecificHandEvent(RenderSpecificHandEvent event)
	{
		if (event.getItemStack().getItem() instanceof ItemCrossbow)
		{
			event.setCanceled(true);
		}

		RenderPlayerHelper.renderItemFirstPerson(Minecraft.getMinecraft().player, event.getPartialTicks(), event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress());
	}
}
