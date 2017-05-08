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

public class EcosystemCrystalHighlands implements Ecosystem
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

			this.decorations.add(new WorldDecorationSimple(3, GenerationAether.blue_skyroot_tree));

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

			this.decorations.add(new WorldDecorationSimple(4, GenerationAether.dark_blue_skyroot_tree));
			this.decorations.add(new WorldDecorationSimple(3, GenerationAether.dark_blue_skyroot_oak));
			this.decorations.add(new WorldDecorationSimple(1, 0.06F, GenerationAether.storm_aercloud)
			{
				@Override
				public BlockPos findPositionToPlace(World world, Random rand, BlockPos pos)
				{
					int width = 16;
					int minY = 90;
					int maxY = 130;

					return pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));
				}
			});
		}

		return this.decorations;
	}

}
