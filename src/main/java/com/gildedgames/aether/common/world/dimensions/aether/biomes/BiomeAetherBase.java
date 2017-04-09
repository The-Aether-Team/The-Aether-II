package com.gildedgames.aether.common.world.dimensions.aether.biomes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.GenUtil;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.noise.OpenSimplexNoise;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BiomeAetherBase extends Biome
{

	protected final BiomeAetherDecorator biomeDecorator = new BiomeAetherDecorator();

	protected final List<Ecosystem> ecosystems = Collections.synchronizedList(Lists.<Ecosystem>newArrayList());

	protected Ecosystem default_ecosystem;

	private boolean hasInit = false;

	private OpenSimplexNoise tempNoise, moistureNoise;

	public BiomeAetherBase(BiomeProperties properties, ResourceLocation registryName)
	{
		super(properties);

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.aether_dirt.getDefaultState();

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();

		this.setRegistryName(registryName);
	}

	public abstract IBlockState getCoastalBlock();

	public void registerEcosystem(Ecosystem ecosystem)
	{
		if (!this.ecosystems.contains(ecosystem))
		{
			this.ecosystems.add(ecosystem);
		}
	}

	@Override
	public final WorldGenAbstractTree genBigTreeChance(Random random)
	{
		return null;
	}

	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xC0C0FF;
	}

	@Override
	public final void decorate(World world, Random random, BlockPos pos)
	{
		if (!this.hasInit)
		{
			Random rand = new Random(world.getSeed());

			this.tempNoise = new OpenSimplexNoise(rand.nextLong());
			this.moistureNoise = new OpenSimplexNoise(rand.nextLong() + 100L);

			this.hasInit = true;
		}

		this.biomeDecorator.genDecorations(world, random, pos, this);
	}

	protected final void decorateEcosystems(World world, Random random, BlockPos pos)
	{
		if (world.isRemote)
		{
			return;
		}

		double scale = 600D;

		int x, z;

		double[] temperatureValue = new double[64];
		double[] moistureValue = new double[64];

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				temperatureValue[j + (i * 8)] = GenUtil.octavedNoise(this.tempNoise, 4, 0D, 1D,
						(double) (pos.getX() + i) / scale, (double) (pos.getZ() + j) / scale);
				moistureValue[j + (i * 8)] = GenUtil.octavedNoise(this.moistureNoise, 4, 0D, 1D,
						(double) (pos.getX() + i) / scale, (double) (pos.getZ() + j) / scale);
			}
		}

		int lowestRequiredChance = Integer.MAX_VALUE;

		for (Ecosystem ecosystem : this.ecosystems)
		{
			for (WorldDecoration decoration : ecosystem.getDecorations())
			{
				for (int count = 0; count < decoration.getGenerationCount(); count++)
				{
					if (decoration.shouldGenerate(random))
					{
						BlockPos placeAt = decoration.findPositionToPlace(world, random, pos);

						x = placeAt.getX() - pos.getX();
						z = placeAt.getZ() - pos.getZ();

						double temperatureDiff = ecosystem.hasDesiredTemperature() ?
								Math.abs(ecosystem.getDesiredTemperature() - temperatureValue[z + (x / 2)]) : 0;
						double moistureDiff =
								ecosystem.hasDesiredMoisture() ? Math.abs(ecosystem.getDesiredMoisture() - moistureValue[z + (x / 2)]) : 0;

						int requiredChance = (int) ((temperatureDiff + moistureDiff) * 100 * 1.5);

						if (requiredChance < lowestRequiredChance)
						{
							lowestRequiredChance = requiredChance;
						}

						if (random.nextInt(100) > requiredChance)
						{
							decoration.getGenerator(random).generate(world, random, placeAt);
						}
					}
				}
			}
		}

		if (this.default_ecosystem != null)
		{
			for (WorldDecoration decoration : this.default_ecosystem.getDecorations())
			{
				for (int count = 0; count < decoration.getGenerationCount(); count++)
				{
					if (decoration.shouldGenerate(random))
					{
						if (random.nextInt(100) < lowestRequiredChance)
						{
							BlockPos placeAt = decoration.findPositionToPlace(world, random, pos);

							decoration.getGenerator(random).generate(world, random, placeAt);
						}
					}
				}
			}
		}
	}

}
