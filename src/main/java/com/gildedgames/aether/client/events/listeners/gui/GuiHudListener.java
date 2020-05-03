package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.client.gui.EffectSystemOverlay;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiHudListener
{
	private static final Minecraft mc = Minecraft.getMinecraft();

	private static final EffectSystemOverlay EFFECT_SYSTEM_OVERLAY = new EffectSystemOverlay();

	private static int old_left_height, old_right_height;

	/**
	 * Adds client-side modifiers for the damage system rendering so crossbows
	 * and dart shooters show the damage they cause with their ammo
	 * @param event
	 */
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (mc.world != null)
		{
			boolean atNecromancerInstance = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR
					|| event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		if (mc.world != null)
		{
			boolean atNecromancerInstance = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR
					|| event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE))
			{
				event.setCanceled(true);
			}
			else if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
			{
				EFFECT_SYSTEM_OVERLAY.render(mc);
			}
		}
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
	}
}
