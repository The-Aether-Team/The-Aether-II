package com.gildedgames.aether.client.sound.generators;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class AetherMusicGenerator implements IMusicGenerator
{
	@Override
	public boolean isPlayable(IPlayerAether aePlayer)
	{
		long time = aePlayer.getEntity().world.getWorldTime();

		if ((time > 1000L && time < 8000L) || (time > 13000L && time < 20000L))
		{
			return aePlayer.getEntity().world.provider.getDimensionType() == DimensionsAether.AETHER;
		}

		return false;
	}

	@Override
	public SoundEvent getMusicResource(IPlayerAether player)
	{
		World world = player.getEntity().getEntityWorld();

		long time = world.getWorldTime();

		if (time > 1000L && time < 9000L)
		{
			return new SoundEvent(AetherCore.getResource("music.day"));
		}
		else if (time > 13000L && time < 20000L)
		{
			return new SoundEvent(AetherCore.getResource("music.night"));
		}

		return null;
	}

	@Override
	public int getQuietPeriod(IPlayerAether player)
	{
		return player.getEntity().getEntityWorld().rand.nextInt(800) + 1800;
	}
}
