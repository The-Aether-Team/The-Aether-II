package com.gildedgames.aether.client.sound.generators;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AetherMusicGenerator implements IMusicGenerator
{
	@Override
	public boolean isPlayable(PlayerAether aePlayer)
	{
		return aePlayer.getEntity().dimension == AetherCore.getAetherDimID();
	}

	@Override
	public ResourceLocation getMusicResource(PlayerAether player)
	{
		World world = player.getEntity().getEntityWorld();

		if (world.getWorldTime() > 12800L || world.getWorldTime() < 22300L)
		{
			return new ResourceLocation("aether:aemusic.day");
		}
		else
		{
			return new ResourceLocation("aether:aemusic.night");
		}
	}

	@Override
	public int getQuietPeriod(PlayerAether player)
	{
		return player.getEntity().getEntityWorld().rand.nextInt(800) + 1800;
	}
}
