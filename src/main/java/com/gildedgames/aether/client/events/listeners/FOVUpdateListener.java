package com.gildedgames.aether.client.events.listeners;

import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class FOVUpdateListener
{
    @SubscribeEvent
    public static void onFOVUpdate(final FOVUpdateEvent event)
    {
        Item item = Minecraft.getMinecraft().player.getActiveItemStack().getItem();

        if (item instanceof ItemCrossbow)
        {
            if (((ItemCrossbow) item).getIsSpecialLoaded())
            {
                event.setNewfov(0.8f);
            }
        }
    }
}
