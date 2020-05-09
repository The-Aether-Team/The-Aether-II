package com.gildedgames.aether.client.events.listeners.sound;

import com.gildedgames.aether.common.capabilities.DamageSystem;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class PlaySoundListener
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRainSoundEvent(final PlaySoundEvent event)
	{
		if (AetherHelper.isAether(Minecraft.getMinecraft().world))
		{
			if (event.getName().equals(SoundEvents.WEATHER_RAIN.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.WEATHER_RAIN_ABOVE.getSoundName().getPath()))
			{
				event.setResultSound(null);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAttackSoundEvent(final PlaySoundEvent event)
	{
		if (DamageSystem.blocked)
		{
			if (event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP.getSoundName().getPath()) ||
					event.getName().equals(SoundEvents.ENTITY_PLAYER_ATTACK_WEAK.getSoundName().getPath()))
			{
				event.setResultSound(null);
			}
		}
	}
}
