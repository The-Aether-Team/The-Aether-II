package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.world.biome.Biome;

public class BiomeVoid extends BiomeAetherBase
{

	public BiomeVoid()
	{
		super(new Biome.BiomeProperties("Aether Void").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_void"));
	}

}
