package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.client.events.listeners.gui.GuiLoadingListener;
import com.gildedgames.aether.client.sound.generators.AetherMusicGenerator;
import com.gildedgames.aether.client.sound.generators.IMusicGenerator;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import java.util.ArrayList;

@Mod.EventBusSubscriber(Dist.CLIENT)
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

	public void addGenerator(final IMusicGenerator generator)
	{
		this.generators.add(generator);
	}

	public void update(final IPlayerAether aePlayer)
	{
		if (this.canPlayMusic() && !this.isPlayingMusic())
		{
			if (this.quietPeriod <= 0)
			{
				final IMusicGenerator generator = this.getNextPlayableSong(aePlayer);

				if (generator == null)
				{
					// If nothing can be played, wait 10 seconds
					this.quietPeriod += 200;
				}
				else
				{
					final SoundEvent event = generator.getMusicResource(aePlayer);

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

	private IMusicGenerator getNextPlayableSong(final IPlayerAether aePlayer)
	{
		for (final IMusicGenerator generator : this.generators)
		{
			if (generator.isPlayable(aePlayer))
			{
				return generator;
			}
		}

		return null;
	}

	public void playMusic(final SoundEvent event)
	{
		if (this.isPlayingMusic())
		{
			this.stopMusic();
		}

		this.currentSong = PositionedSoundRecord.getMusicRecord(event);

		this.getSoundHandler().playSound(this.currentSong);
	}

	public void stopMusic()
	{
		if (this.currentSong != null)
		{
			this.getSoundHandler().stopSound(this.currentSong);

			this.currentSong = null;
		}
	}

	public void onRecordPlayed(final ISound sound)
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
		return !Minecraft.getInstance().isGamePaused() && (this.currentRecord == null
				|| !this.getSoundHandler().isSoundPlaying(this.currentRecord));
	}

	private SoundHandler getSoundHandler()
	{
		return Minecraft.getInstance().getSoundHandler();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	// Lowest priority is important, so we can ensure the sound will actually be played
	public static void onPlaySound(final PlaySoundEvent event)
	{
		if (GuiLoadingListener.isLoadingScreen())
		{
			event.setResultSound(null);
			return;
		}

		if (event.getSound().getCategory() == SoundCategory.MUSIC)
		{
			if (!event.getSound().getSoundLocation().getNamespace().equals(AetherCore.MOD_ID))
			{
				final PlayerEntity player = Minecraft.getInstance().player;

				if (player != null && (player.world.provider.getDimensionType() == DimensionsAether.AETHER
						|| player.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER))
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
