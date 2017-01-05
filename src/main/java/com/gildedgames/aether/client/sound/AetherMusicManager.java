package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import com.gildedgames.aether.client.sound.generators.AetherMusicGenerator;
import com.gildedgames.aether.client.sound.generators.IMusicGenerator;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class AetherMusicManager
{
	public static final AetherMusicManager INSTANCE = new AetherMusicManager();

	private final ArrayList<IMusicGenerator> generators = new ArrayList<>();

	private ISound currentSong, currentRecord;

	private int quietPeriod;

	public AetherMusicManager()
	{
		this.addGenerator(new AetherMusicGenerator());
	}

	public void addGenerator(IMusicGenerator generator)
	{
		this.generators.add(generator);
	}

	public void update(IPlayerAetherCapability aePlayer)
	{
		if (this.canPlayMusic() && !this.isPlayingMusic())
		{
			if (this.quietPeriod <= 0)
			{
				IMusicGenerator generator = this.getNextPlayableSong(aePlayer);

				if (generator == null)
				{
					// If nothing can be played, wait 10 seconds
					this.quietPeriod += 200;
				}
				else
				{
					SoundEvent event = generator.getMusicResource(aePlayer);

					if (event == null)
					{
						this.quietPeriod += 200;

						return;
					}

					this.playMusic(event);

					this.quietPeriod = generator.getQuietPeriod(aePlayer);
				}
			}

			this.quietPeriod--;
		}
	}

	private IMusicGenerator getNextPlayableSong(IPlayerAetherCapability aePlayer)
	{
		for (IMusicGenerator generator : this.generators)
		{
			if (generator.isPlayable(aePlayer))
			{
				return generator;
			}
		}

		return null;
	}

	private void playMusic(SoundEvent event)
	{
		if (this.isPlayingMusic())
		{
			this.stopMusic();
		}

		this.currentSong = PositionedSoundRecord.getMusicRecord(event);

		this.getSoundHandler().playSound(this.currentSong);
	}

	private void stopMusic()
	{
		if (this.currentSong != null)
		{
			this.getSoundHandler().stopSound(this.currentSong);

			this.currentSong = null;
		}
	}

	public void onRecordPlayed(ISound sound)
	{
		this.currentRecord = sound;

		if (this.isPlayingMusic())
		{
			this.stopMusic();
		}
	}

	private boolean isPlayingMusic()
	{
		return this.currentSong != null && this.getSoundHandler().isSoundPlaying(this.currentSong);
	}

	private boolean canPlayMusic()
	{
		return !Minecraft.getMinecraft().isGamePaused() && (this.currentRecord == null || !this.getSoundHandler().isSoundPlaying(this.currentRecord));
	}

	private SoundHandler getSoundHandler()
	{
		return Minecraft.getMinecraft().getSoundHandler();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	// Lowest priority is important, so we can ensure the sound will actually be played
	public void onPlaySound(PlaySoundEvent event)
	{
		if (event.getSound().getCategory() == SoundCategory.MUSIC)
		{
			if (!event.getSound().getSoundLocation().getResourceDomain().equals(AetherCore.MOD_ID))
			{
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;

				if (player != null && player.worldObj.provider.getDimensionType() == DimensionsAether.AETHER)
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
