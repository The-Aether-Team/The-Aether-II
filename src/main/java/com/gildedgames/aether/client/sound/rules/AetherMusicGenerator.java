package com.gildedgames.aether.client.sound.rules;

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
			return new ResourceLocation("aether:aemusic.night");
		}
		else
		{
			return new ResourceLocation("aether:aemusic.day");
		}
	}
}
