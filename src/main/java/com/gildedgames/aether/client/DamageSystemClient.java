package com.gildedgames.aether.client;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class DamageSystemClient
{
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
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.world != null && mc.player != null)
			{
				final ItemStack ammoStack = mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

				if (ammoStack.getItem() == ItemsAether.bolt && ammoStack.getCount() > 0 && mc.player.getHeldItemMainhand().getItem() instanceof ItemCrossbow)
				{
					ItemBoltType type = ItemCrossbow.BOLT_TYPES[ammoStack.getItemDamage()];

					ClientEventHandler.getDamageSystemOverlay().setSlashModifier(type.getSlashDamageLevel());
					ClientEventHandler.getDamageSystemOverlay().setPierceModifier(type.getPierceDamageLevel());
					ClientEventHandler.getDamageSystemOverlay().setImpactModifier(type.getImpactDamageLevel());
				}
				else if (mc.player.getHeldItemMainhand().getItem() == ItemsAether.dart_shooter && mc.player.getHeldItemMainhand().getCount() > 0)
				{
					ItemDartType type = ItemDart.ITEM_VARIANTS[mc.player.getHeldItemMainhand().getItemDamage()];

					ClientEventHandler.getDamageSystemOverlay().setSlashModifier(type.getSlashDamageLevel());
					ClientEventHandler.getDamageSystemOverlay().setPierceModifier(type.getPierceDamageLevel());
					ClientEventHandler.getDamageSystemOverlay().setImpactModifier(type.getImpactDamageLevel());
				}
				else
				{
					ClientEventHandler.getDamageSystemOverlay().setSlashModifier(0);
					ClientEventHandler.getDamageSystemOverlay().setPierceModifier(0);
					ClientEventHandler.getDamageSystemOverlay().setImpactModifier(0);
				}
			}
		}
	}
}
