package com.gildedgames.aether.client.events.listeners.sound;

import com.gildedgames.aether.common.items.IUsesCustomSound;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemUseSoundListener
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSoundEvent(final PlaySoundEvent event)
    {
        if (Minecraft.getMinecraft().player != null)
        {
            ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();

            if (stack.getItem() instanceof IUsesCustomSound)
            {
                IUsesCustomSound item = (IUsesCustomSound) stack.getItem();

                if (item.usesCustomSound(stack))
                {
                    if (event.getName().equals(item.getDefaultSound().getSoundName().getPath()))
                    {
                        event.setResultSound(null);
                    }
                }
            }
        }
    }
}
