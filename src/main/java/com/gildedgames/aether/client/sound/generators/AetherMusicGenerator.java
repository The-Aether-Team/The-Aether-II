package com.gildedgames.aether.client.sound.generators;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class AetherMusicGenerator implements IMusicGenerator
{
	@Override
	public boolean isPlayable(IPlayerAetherCapability aePlayer)
	{
		return aePlayer.getPlayer().dimension == AetherCore.getAetherDimID();
	}

	@Override
	public SoundEvent getMusicResource(IPlayerAetherCapability player)
	{
		World world = player.getPlayer().getEntityWorld();

		if (world.getWorldTime() > 12800L || world.getWorldTime() < 22300L)
		{
			return new SoundEvent(AetherCore.getResource("aether:aemusic.day"));
		}
		else
		{
			return new SoundEvent(AetherCore.getResource("aether:aemusic.night"));
		}
	}

	@Override
	public int getQuietPeriod(IPlayerAetherCapability player)
	{
		return player.getPlayer().getEntityWorld().rand.nextInt(800) + 1800;
	}
}
