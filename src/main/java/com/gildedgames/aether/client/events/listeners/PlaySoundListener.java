package com.gildedgames.aether.client.events.listeners;

import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlaySoundListener
{
	@SubscribeEvent
	public void onSoundEvent(PlaySoundEvent event)
	{
		if (AetherHelper.isAether(Minecraft.getInstance().world))
		{
			if (event.getName().equals(SoundEvents.WEATHER_RAIN.getName().getPath()) ||
					event.getName().equals(SoundEvents.WEATHER_RAIN_ABOVE.getName().getPath()))
			{
				event.setCanceled(true);
			}
		}
	}
}
