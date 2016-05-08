package com.gildedgames.aether.client.sound.generators;

import com.gildedgames.aether.common.entities.player.PlayerAetherBase;
import net.minecraft.util.ResourceLocation;

public interface IMusicGenerator
{
	/**
	 * Determines whether or not this music can be played in the environment
	 * @param aePlayer The player
	 * @return True if this music applies to the environment
	 */
	boolean isPlayable(PlayerAetherBase aePlayer);

	/**
	 * @param player The player
	 * @return The {@link ResourceLocation} based off the environment
	 */
	ResourceLocation getMusicResource(PlayerAetherBase player);

	/**
	 * @return How long the music manager should wait before trying another song.
	 */
	int getQuietPeriod(PlayerAetherBase player);
}
