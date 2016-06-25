package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundEventHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	// Lowest priority is important, so we can ensure the sound will actually be played
	public void onPlaySound(PlaySoundEvent event)
	{
		if (event.getSound().getCategory() == SoundCategory.MUSIC)
		{
			if (!event.getSound().getSoundLocation().getResourceDomain().equals(AetherCore.MOD_ID))
			{
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;

				if (player != null && player.dimension == AetherCore.CONFIG.getAetherDimID())
				{
					event.setResultSound(null);
				}
			}
		}
		else if (event.getSound().getCategory() == SoundCategory.RECORDS)
		{
			AetherMusicManager.INSTANCE.onRecordPlayed(event.getSound());
		}
	}
}
