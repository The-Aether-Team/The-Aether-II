package com.gildedgames.aether.client.sound.generators;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public interface IMusicGenerator
{
	/**
	 * Determines whether or not this music can be played in the environment
	 * @param aePlayer The player
	 * @return True if this music applies to the environment
	 */
	boolean isPlayable(IPlayerAetherCapability aePlayer);

	/**
	 * @param player The player
	 * @return The {@link ResourceLocation} based off the environment
	 */
	SoundEvent getMusicResource(IPlayerAetherCapability player);

	/**
	 * @return How long the music manager should wait before trying another song.
	 */
	int getQuietPeriod(IPlayerAetherCapability player);
}
