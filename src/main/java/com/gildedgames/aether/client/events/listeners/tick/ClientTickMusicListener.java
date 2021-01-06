package com.gildedgames.aether.client.events.listeners.tick;

import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientTickMusicListener
{
	private static Minecraft mc = Minecraft.getMinecraft();

	private static AetherMusicManager musicTicker = new AetherMusicManager(mc);

	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event)
	{
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!mc.isGamePaused())
				{
					if (!musicTicker.playingRecord())
					{
						musicTicker.update();
					}
				}
			}
		}

		if (!(mc.getSoundHandler().isSoundPlaying(musicTicker.getRecord())))
		{
			musicTicker.trackRecord(null);
		}
	}

	@SubscribeEvent
	public static void onMusicControl(final PlaySoundEvent event)
	{
		ISound sound = event.getResultSound();

		if (sound == null)
		{
			return;
		}

		SoundCategory category = sound.getCategory();

		if (category == SoundCategory.MUSIC)
		{
			if (mc.player != null)
			{
				if (mc.player.world.provider.getDimensionType() == DimensionsAether.AETHER || mc.player.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
				{
					if (!sound.getSoundLocation().toString().contains(AetherCore.MOD_ID) && (musicTicker.playingMusic() || !musicTicker.playingMusic()))
					{
						event.setResultSound(null);

						return;
					}
				}
			}
		}
		else if (category == SoundCategory.RECORDS && !(event.getName().contains("block.note")))
		{
			musicTicker.trackRecord(event.getSound());
			mc.getSoundHandler().stopSounds();

			return;
		}
	}
}
