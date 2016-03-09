package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.client.sound.rules.IMusicGenerator;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class AetherMusicManager
{
	public static final AetherMusicManager INSTANCE = new AetherMusicManager();

	private final ArrayList<IMusicGenerator> generators = new ArrayList<>();

	private ISound currentSong, currentRecord;

	private int musicTimer;

	public void addGenerator(IMusicGenerator generator)
	{
		this.generators.add(generator);
	}

	public void update(PlayerAether aePlayer)
	{
		if (this.canPlayMusic() && !this.isPlayingMusic())
		{
			if (this.musicTimer <= 0)
			{
				IMusicGenerator generator = this.getNextPlayableSong(aePlayer);

				if (generator == null)
				{
					// If nothing can be played, wait 10 seconds
					this.musicTimer += 200;
				}
				else
				{
					ResourceLocation resource = generator.getMusicResource(aePlayer);

					this.playMusic(resource);

					// Waits 90 seconds after the song finishes before trying to play another
					this.musicTimer = 1800;
				}
			}

			this.musicTimer--;
		}
	}

	private IMusicGenerator getNextPlayableSong(PlayerAether aePlayer)
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

	private void playMusic(ResourceLocation location)
	{
		AetherCore.LOGGER.info("Playing music " + location);

		if (this.isPlayingMusic())
		{
			this.stopMusic();
		}

		this.currentSong = PositionedSoundRecord.create(location);

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
}
