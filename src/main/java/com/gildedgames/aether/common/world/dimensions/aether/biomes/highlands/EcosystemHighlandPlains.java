package com.gildedgames.aether.common.world.dimensions.aether.biomes.highlands;

import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.biome.WorldDecorationSimple;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

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

			this.decorations.add(new WorldDecorationSimple(5, GenerationAether.short_aether_grass, GenerationAether.aether_grass));

			this.decorations.add(new WorldDecorationSimple(6, GenerationAether.holystone_rocks)
			{
				@Override
				public BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
				{
					int x = rand.nextInt(16) + 8;
					int y = rand.nextInt(128);
					int z = rand.nextInt(16) + 8;

					return pos.add(x, y, z);
				}
			});

			this.decorations.add(new WorldDecorationSimple(1, 0.1F, GenerationAether.skyroot_twigs));
			this.decorations.add(new WorldDecorationSimple(1, GenerationAether.blue_skyroot_tree, GenerationAether.green_skyroot_tree, GenerationAether.green_skyroot_small_pine, GenerationAether.golden_oak));
		}

		return this.decorations;
	}

}
