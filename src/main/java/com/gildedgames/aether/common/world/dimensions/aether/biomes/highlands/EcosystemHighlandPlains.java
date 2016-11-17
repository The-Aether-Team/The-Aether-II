package com.gildedgames.aether.common.world.dimensions.aether.biomes.highlands;

import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.biome.WorldDecorationSimple;
import com.google.common.collect.Lists;

import java.util.List;

public class EcosystemHighlandPlains implements Ecosystem
{

	private List<WorldDecoration> decorations;

	@Override
	public boolean hasDesiredTemperature()
	{
		return false;
	}

	@Override
	public boolean hasDesiredMoisture()
	{
		return false;
	}

	@Override
	public double getDesiredTemperature()
	{
		return 0;
	}

	@Override
	public double getDesiredMoisture()
	{
		return 0;
	}

	@Override
	public List<WorldDecoration> getDecorations()
	{
		if (this.decorations == null)
		{
			this.decorations = Lists.newArrayList();

			this.decorations.add(new WorldDecorationSimple(5, GenerationAether.aether_grass));
			this.decorations.add(new WorldDecorationSimple(1, GenerationAether.blue_skyroot_tree, GenerationAether.green_skyroot_tree, GenerationAether.green_skyroot_small_pine, GenerationAether.golden_oak));
		}

		return this.decorations;
	}

}
