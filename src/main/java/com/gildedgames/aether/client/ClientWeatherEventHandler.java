package com.gildedgames.aether.client;

import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientWeatherEventHandler
{
	@SubscribeEvent
	public void onSoundEvent(PlaySoundEvent event)
	{
		if (AetherHelper.isAether(Minecraft.getMinecraft().world))
		{
			if (event.getName().equals(SoundEvents.WEATHER_RAIN.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.WEATHER_RAIN_ABOVE.getSoundName().getPath()))
			{
				event.setCanceled(true);
			}
		}
	}
}
