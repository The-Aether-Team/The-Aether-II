package com.gildedgames.aether.common.world.dimensions.aether.biomes.highlands;

import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.biome.WorldDecorationSimple;
import com.google.common.collect.Lists;

import java.util.List;

public class EcosystemHighlandJungle implements Ecosystem
{

	private List<WorldDecoration> decorations;

	@Override
	public boolean hasDesiredTemperature()
	{
		return true;
	}

	@Override
	public boolean hasDesiredMoisture()
	{
		return true;
	}

	@Override
	public double getDesiredTemperature()
	{
		return 0.5;
	}

	@Override
	public double getDesiredMoisture()
	{
		return 0.5;
	}

	@Override
	public List<WorldDecoration> getDecorations()
	{
		if (this.decorations == null)
		{
			this.decorations = Lists.newArrayList();

			this.decorations.add(new WorldDecorationSimple(8, GenerationAether.aether_grass));
			this.decorations.add(new WorldDecorationSimple(1, GenerationAether.labyrinth_ruins, 0.125F));
			this.decorations.add(new WorldDecorationSimple(1, GenerationAether.skyroot_moa_nest_tree_1, 0.5F));
			this.decorations.add(new WorldDecorationSimple(15, GenerationAether.green_skyroot_windswept));
		}

		return this.decorations;
	}

}
