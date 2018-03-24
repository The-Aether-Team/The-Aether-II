package com.gildedgames.aether.common.world.aether.biomes;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.common.world.aether.island.population.SubBiome;
import com.gildedgames.aether.common.world.aether.island.population.WorldDecoration;
import com.gildedgames.aether.common.world.util.GenUtil;
import com.gildedgames.orbis.api.processing.BlockAccessExtendedWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SubBiomeDecorator
{

	private boolean hasInit = false;

	private OpenSimplexNoise tempNoise, moistureNoise;

	private void attemptInit(final World world)
	{
		if (!this.hasInit)
		{
			final Random rand = new Random(world.getSeed());

			this.tempNoise = new OpenSimplexNoise(rand.nextLong());
			this.moistureNoise = new OpenSimplexNoise(rand.nextLong() + 100L);

			this.hasInit = true;
		}
	}

	private double getTemperatureDifference(final SubBiome subBiome, final double[] temperatureValues, final int x, final int z)
	{
		final double temperatureDiff = subBiome.hasDesiredTemperature() ?
				Math.abs(subBiome.getDesiredTemperature() - temperatureValues[z + (x / 2)]) : 0;

		return temperatureDiff;
	}

	private double getMoistureDifference(final SubBiome subBiome, final double[] moistureValues, final int x, final int z)
	{
		final double moistureDiff =
				subBiome.hasDesiredMoisture() ? Math.abs(subBiome.getDesiredMoisture() - moistureValues[z + (x / 2)]) : 0;

		return moistureDiff;
	}

	public final void decorate(final World world, final Random rand, final BlockPos pos, final BiomeAetherBase biome)
	{
		if (world.isRemote)
		{
			return;
		}

		final BlockAccessExtendedWrapper blockAccess = new BlockAccessExtendedWrapper(world);

		this.attemptInit(world);

		final List<SubBiome> subBiomes = biome.getSubBiomes();
		final SubBiome defaultSubBiome = biome.getDefaultSubBiome();

		int x, z;

		final double scale = 600D;

		final double[] temperatureValues = new double[64];
		final double[] moistureValues = new double[64];

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				temperatureValues[j + (i * 8)] = GenUtil.octavedNoise(this.tempNoise, 4, 0D, 1D,
						(double) (pos.getX() + i) / scale, (double) (pos.getZ() + j) / scale);
				moistureValues[j + (i * 8)] = GenUtil.octavedNoise(this.moistureNoise, 4, 0D, 1D,
						(double) (pos.getX() + i) / scale, (double) (pos.getZ() + j) / scale);
			}
		}

		int lowestRequiredChance = Integer.MAX_VALUE;

		for (final SubBiome subBiome : subBiomes)
		{
			for (final WorldDecoration decoration : subBiome.getDecorations())
			{
				for (int count = 0; count < decoration.getGenerationCount(); count++)
				{
					if (decoration.shouldGenerate(rand))
					{
						final BlockPos placeAt = decoration.findPositionToPlace(blockAccess, rand, pos);

						x = placeAt.getX() - pos.getX();
						z = placeAt.getZ() - pos.getZ();

						final double temperatureDiff = this.getTemperatureDifference(subBiome, temperatureValues, x, z);
						final double moistureDiff = this.getMoistureDifference(subBiome, moistureValues, x, z);

						final int requiredChance = (int) ((temperatureDiff + moistureDiff) * 100 * 1.5);

						if (requiredChance < lowestRequiredChance)
						{
							lowestRequiredChance = requiredChance;
						}

						if (rand.nextInt(100) > requiredChance)
						{
							decoration.getGenerator(rand).generate(blockAccess, world, rand, placeAt);
						}
					}
				}
			}
		}

		if (defaultSubBiome != null)
		{
			for (final WorldDecoration decoration : defaultSubBiome.getDecorations())
			{
				for (int count = 0; count < decoration.getGenerationCount(); count++)
				{
					if (decoration.shouldGenerate(rand))
					{
						if (rand.nextInt(100) < lowestRequiredChance)
						{
							final BlockPos placeAt = decoration.findPositionToPlace(blockAccess, rand, pos);

							decoration.getGenerator(rand).generate(blockAccess, world, rand, placeAt);
						}
					}
				}
			}
		}
	}

}
