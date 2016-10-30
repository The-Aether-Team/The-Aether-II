package com.gildedgames.aether.common.world.biome;

import java.util.List;

public interface Ecosystem
{

	boolean hasDesiredTemperature();

	boolean hasDesiredMoisture();

	double getDesiredTemperature();

	double getDesiredMoisture();

	List<WorldDecoration> getDecorations();

}
