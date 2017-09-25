package com.gildedgames.aether.common.world.aether.island.population;

import java.util.List;

public interface SubBiome
{

	boolean hasDesiredTemperature();

	boolean hasDesiredMoisture();

	double getDesiredTemperature();

	double getDesiredMoisture();

	List<WorldDecoration> getDecorations();

}
