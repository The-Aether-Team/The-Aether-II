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
		long time = aePlayer.getPlayer().worldObj.getWorldTime();

		if ((time > 1000L && time < 8000L) || (time > 13000L && time < 20000L))
		{
			return aePlayer.getPlayer().dimension == AetherCore.getAetherDimID();
		}

		return false;
	}

	@Override
	public SoundEvent getMusicResource(IPlayerAetherCapability player)
	{
		World world = player.getPlayer().getEntityWorld();

		long time = world.getWorldTime();

		if (time > 1000L && time < 9000L)
		{
			return new SoundEvent(AetherCore.getResource("aemusic.day"));
		}
		else if (time > 13000L && time < 20000L)
		{
			return new SoundEvent(AetherCore.getResource("aemusic.night"));
		}

		return null;
	}

	@Override
	public int getQuietPeriod(IPlayerAetherCapability player)
	{
		return player.getPlayer().getEntityWorld().rand.nextInt(800) + 1800;
	}
}
