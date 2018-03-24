package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.aether.island.population.SubBiome;
import com.gildedgames.aether.common.world.aether.island.population.WorldDecoration;
import com.gildedgames.aether.common.world.aether.island.population.WorldDecorationSimple;
import com.gildedgames.aether.common.world.templates.TemplateWorldGen;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class SubBiomeIrradiated implements SubBiome
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
		return -0.4;
	}

	@Override
	public double getDesiredMoisture()
	{
		return -0.4;
	}

	@Override
	public List<WorldDecoration> getDecorations()
	{
		if (this.decorations == null)
		{
			this.decorations = Lists.newArrayList();

			this.decorations.add(new WorldDecorationSimple(6, new TemplateWorldGen(GenerationAether.golden_oak)));

			this.decorations.add(new WorldDecorationSimple(2, GenerationAether.short_aether_grass));
			this.decorations.add(new WorldDecorationSimple(1, 0.2F, GenerationAether.skyroot_twigs));

			this.decorations.add(new WorldDecorationSimple(6, GenerationAether.holystone_rocks)
			{
				@Override
				public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
				{
					final int x = rand.nextInt(16) + 8;
					final int y = rand.nextInt(128);
					final int z = rand.nextInt(16) + 8;

					return pos.add(x, y, z);
				}
			});

			this.decorations.add(new WorldDecorationSimple(1, 0.06F, GenerationAether.golden_aercloud)
			{
				@Override
				public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
				{
					final int width = 16;
					final int minY = 90;
					final int maxY = 130;

					return pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));
				}
			});
		}

		return this.decorations;
	}

}
