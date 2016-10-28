package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.OpenSimplexNoise;
import com.gildedgames.aether.common.world.GenUtil;
import com.google.common.collect.Lists;
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
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
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

		int x, z;

		double scale = 600D;

		double[] temperatureValue = new double[576];
		double[] moistureValue = new double[576];

		for (int i = 0; i < 24; i++)
		{
			for (int j = 0; j < 24; j++)
			{
				temperatureValue[j + (i * 24)] = GenUtil.octavedNoise(this.tempNoise, 4, 0D, 1D, (double)(pos.getX() + i) / scale, (double)(pos.getZ() + j) / scale);
				moistureValue[j + (i * 24)] = GenUtil.octavedNoise(this.moistureNoise, 4, 0D, 1D, (double)(pos.getX() + i) / scale, (double)(pos.getZ() + j) / scale);
			}
		}

		for (Ecosystem ecosystem : this.ecosystems)
		{
			for (WorldDecoration decoration : ecosystem.getDecorations())
			{
				for (int count = 0; count < decoration.getGenerationCount(); count++)
				{
					if (decoration.shouldGenerate(random))
					{
						x = random.nextInt(16) + 8;
						z = random.nextInt(16) + 8;

						double temperatureDiff = ecosystem.hasDesiredTemperature() ? Math.abs(ecosystem.getDesiredTemperature() - temperatureValue[z + (x * 24)]) : 0;
						double moistureDiff = ecosystem.hasDesiredMoisture() ? Math.abs(ecosystem.getDesiredMoisture() - moistureValue[z + (x * 24)]) : 0;

						int requiredChance = (int) ((temperatureDiff + moistureDiff) * 100 * 1.5);

						if (random.nextInt(100) > requiredChance)
						{
							BlockPos placeAt = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z));

							decoration.getGenerator().generate(world, random, placeAt);
						}
					}
				}
			}
		}
	}

}
