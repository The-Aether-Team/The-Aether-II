package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.client.gui.overlays.OverlayDamageSystem;
import com.gildedgames.aether.client.gui.overlays.OverlayEffectSystem;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiHudListener
{
	private static final Minecraft mc = Minecraft.getInstance();

	private static final OverlayDamageSystem DAMAGE_SYSTEM_OVERLAY = new OverlayDamageSystem(mc);

	private static final OverlayEffectSystem EFFECT_SYSTEM_OVERLAY = new OverlayEffectSystem(mc);

	/**
	 * Adds client-side modifiers for the damage system rendering so crossbows
	 * and dart shooters show the damage they cause with their ammo
	 * @param event
	 */
	@SubscribeEvent
	public static void onTick(final TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			Minecraft mc = Minecraft.getInstance();

			if (mc.world != null && mc.player != null)
			{
				final ItemStack ammoStack = mc.player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

				if (ammoStack.getItem() == ItemsAether.bolt && ammoStack.getCount() > 0 && mc.player.getHeldItemMainhand().getItem() instanceof ItemCrossbow)
				{
					ItemBoltType type = ItemCrossbow.BOLT_TYPES[ammoStack.getDamage()];

					DAMAGE_SYSTEM_OVERLAY.setSlashModifier(type.getSlashDamageLevel());
					DAMAGE_SYSTEM_OVERLAY.setPierceModifier(type.getPierceDamageLevel());
					DAMAGE_SYSTEM_OVERLAY.setImpactModifier(type.getImpactDamageLevel());
				}
				else if (mc.player.getHeldItemMainhand().getItem() == ItemsAether.dart_shooter && mc.player.getHeldItemMainhand().getCount() > 0)
				{
					ItemDartType type = ItemDart.ITEM_VARIANTS[mc.player.getHeldItemMainhand().getDamage()];

					DAMAGE_SYSTEM_OVERLAY.setSlashModifier(type.getSlashDamageLevel());
					DAMAGE_SYSTEM_OVERLAY.setPierceModifier(type.getPierceDamageLevel());
					DAMAGE_SYSTEM_OVERLAY.setImpactModifier(type.getImpactDamageLevel());
				}
				else
				{
					DAMAGE_SYSTEM_OVERLAY.setSlashModifier(0);
					DAMAGE_SYSTEM_OVERLAY.setPierceModifier(0);
					DAMAGE_SYSTEM_OVERLAY.setImpactModifier(0);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (mc.world != null)
		{
			boolean atNecromancerInstance = mc.world.getDimension().getType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR))
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
			boolean atNecromancerInstance = mc.world.getDimension().getType() == DimensionsAether.NECROMANCER_TOWER;

			if (atNecromancerInstance && (event.getType() == RenderGameOverlayEvent.ElementType.AIR
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTH
					|| event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR
					|| event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR))
			{
				event.setCanceled(true);
			}
			else if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
			{
				DAMAGE_SYSTEM_OVERLAY.render();
				EFFECT_SYSTEM_OVERLAY.render();
			}
		}
	}

	@SubscribeEvent
	public static void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
		{
			final PlayerEntity player = Minecraft.getInstance().player;

			if (player.getAir() == 300 && player.isPotionActive(Effects.WATER_BREATHING))
			{
				event.setCanceled(true);
			}
		}
	}
}
