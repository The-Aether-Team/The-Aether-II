package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.registry.minecraft.BiomesAether;
import com.gildedgames.aether.common.world.island.logic.IslandBiomeSet;
import net.minecraft.world.biome.Biome;

public class IslandBiomeSets
{

	public static final IslandBiomeSet HIGHLANDS = new IslandBiomeSet()
	{

		@Override
		public Biome provide(double temperature, double moisture)
		{
			Biome biome = BiomesAether.HIGHLANDS;

			if (temperature < 0.2 && moisture > 0.3)
			{
				biome = BiomesAether.HIGHLANDS_CRYSTAL;
			}

			if (temperature > 0.5 && moisture > 0.2)
			{
				biome = BiomesAether.HIGHLANDS_FOREST;
			}

			if (temperature > 0.2 && moisture < 0.2)
			{
				biome = BiomesAether.HIGHLANDS_PLAINS;
			}

			return biome;
		}

	};

	public static final IslandBiomeSet ENCHANTED_HIGHLANDS = new IslandBiomeSet()
	{

		@Override
		public Biome provide(double temperature, double moisture)
		{
			return BiomesAether.ENCHANTED_FOREST;
		}

	};

}
