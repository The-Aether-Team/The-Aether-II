package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class AetherMusicManager
{
	private final Random rand = new Random();
	private final Minecraft mc;
	private ISound currentMusic, currentRecord;
	private static int timeUntilNextMusic = 100;

	public AetherMusicManager(Minecraft mcIn)
	{
		this.mc = mcIn;
	}

	public void update()
	{
		if (this.mc.player != null)
		{
			SoundEvent tracktype = this.getRandomTrack(PlayerAether.getPlayer(this.mc.player));

			if (tracktype == null)
			{
				timeUntilNextMusic--;

				if (timeUntilNextMusic <= 0)
				{
					timeUntilNextMusic += 100;
				}
			}
			else
			{
				if (mc.player.world.provider.getDimensionType() != DimensionsAether.AETHER)
				{
					this.stopMusic();
				}
				else
				{
					if (this.currentMusic != null)
					{
						if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic))
						{
							timeUntilNextMusic = Math.min(MathHelper.getInt(this.rand, 1200, 1500), timeUntilNextMusic);
							this.currentMusic = null;
						}
						else
						{
							timeUntilNextMusic = Math.min(timeUntilNextMusic, 1500);
						}
					}

					if (this.currentMusic == null && timeUntilNextMusic-- <= 0)
					{
						this.playMusic(tracktype);
					}
				}
			}
		}
	}

	public boolean playingMusic()
	{
		return this.currentMusic != null;
	}

	public boolean playingRecord()
	{
		return this.currentRecord != null;
	}

	public ISound getRecord()
	{
		return this.currentRecord;
	}

	public SoundEvent getRandomTrack(IPlayerAether player)
	{
		World world = player.getEntity().getEntityWorld();

		long time = world.getWorldTime();

		if (this.isPlayable(player))
		{
			if (time > 1000L && time < 9000L)
			{
				return new SoundEvent(AetherCore.getResource("music.day"));
			}
			else if (time > 13000L && time < 20000L)
			{
				return new SoundEvent(AetherCore.getResource("music.night"));
			}
		}

		return null;
	}

	public boolean isPlayable(IPlayerAether aePlayer)
	{
		long time = aePlayer.getEntity().world.getWorldTime();

		if ((time > 1000L && time < 8000L) || (time > 13000L && time < 20000L))
		{
			return aePlayer.getEntity().world.provider.getDimensionType() == DimensionsAether.AETHER;
		}

		return false;
	}

	public void playMusic(SoundEvent requestedMusicType)
	{
		this.currentMusic = PositionedSoundRecord.getMusicRecord(requestedMusicType);
		this.mc.getSoundHandler().playSound(this.currentMusic);
		timeUntilNextMusic = Integer.MAX_VALUE;
	}

	public void trackRecord(ISound record)
	{
		this.currentRecord = record;
	}

	public void stopMusic()
	{
		if (this.currentMusic != null)
		{
			this.mc.getSoundHandler().stopSound(this.currentMusic);
			this.currentMusic = null;
			timeUntilNextMusic = 0;
		}
	}
}
