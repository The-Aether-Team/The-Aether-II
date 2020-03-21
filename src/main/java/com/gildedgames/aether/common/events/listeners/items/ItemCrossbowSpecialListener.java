package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemCrossbowSpecialListener
{
    @SubscribeEvent
    public static void onHoldCrossbow(final TickEvent.PlayerTickEvent event)
    {
        final UUID CROSSBOW_UUID = UUID.fromString("1A1D30BD-CF8E-4996-B76A-5C139A8F9685");

        final AttributeModifier CROSSBOW_SPECIAL = new AttributeModifier(CROSSBOW_UUID, "aether.statusLoadingCrossbowSpecial", -1.0D, 1);
        IAttributeInstance crossbowSlowedLevel = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (!(event.player.getActiveItemStack().getItem() instanceof ItemCrossbow) || !((ItemCrossbow) event.player.getHeldItemMainhand().getItem()).getIsSpecialLoaded()
                || !event.player.isSneaking())
        {
            if (crossbowSlowedLevel.getModifier(CROSSBOW_SPECIAL.getID()) != null)
            {
                crossbowSlowedLevel.removeModifier(CROSSBOW_SPECIAL);
            }
        }
        else
        {
            if (!crossbowSlowedLevel.hasModifier(CROSSBOW_SPECIAL))
            {
                crossbowSlowedLevel.applyModifier(CROSSBOW_SPECIAL);
            }
        }
    }
}
