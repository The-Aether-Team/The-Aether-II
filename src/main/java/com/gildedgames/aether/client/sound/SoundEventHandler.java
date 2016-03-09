package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundEventHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	// Lowest priority is important, so we can ensure the sound will actually be played
	public void onPlaySound(PlaySoundEvent event)
	{
		if (event.category == SoundCategory.MUSIC)
		{
			if (!event.result.getSoundLocation().getResourceDomain().equals(AetherCore.MOD_ID))
			{
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;

				if (player != null && player.dimension == AetherCore.CONFIG.getAetherDimID())
				{
					event.result = null;
				}
			}
		}
		else if (event.category == SoundCategory.RECORDS)
		{
			AetherMusicManager.INSTANCE.onRecordPlayed(event.sound);
		}
	}
}
