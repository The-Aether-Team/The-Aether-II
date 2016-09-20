package com.gildedgames.aether.common.world.island.logic;

import net.minecraft.world.biome.Biome;

public interface IslandBiomeSet
{

	Biome provide(double temperature, double moisture);

}
