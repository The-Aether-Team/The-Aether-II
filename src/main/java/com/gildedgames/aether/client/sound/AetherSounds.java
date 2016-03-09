package com.gildedgames.aether.client.sound;

import com.gildedgames.aether.client.sound.rules.AetherMusicGenerator;

public class AetherSounds
{
	public static void init()
	{
		AetherMusicManager manager = AetherMusicManager.INSTANCE;

		manager.addGenerator(new AetherMusicGenerator());
	}
}
