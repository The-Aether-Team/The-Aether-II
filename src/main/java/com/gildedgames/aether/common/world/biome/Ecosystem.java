package com.gildedgames.aether.common.world.biome;

import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;

public interface Ecosystem
{

	boolean hasDesiredTemperature();

	boolean hasDesiredMoisture();

	double getDesiredTemperature();

	double getDesiredMoisture();

	List<WorldDecoration> fetchDecorations();

	void initDecorations(WorldServer world, TemplateManager manager);

	boolean hasInitDecorations();

	void setInitDecorations(boolean flag);

}
