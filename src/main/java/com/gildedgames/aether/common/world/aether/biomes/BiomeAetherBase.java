package com.gildedgames.aether.common.world.aether.biomes;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.island.gen.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.island.population.SubBiome;
import com.gildedgames.orbis.api.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BiomeAetherBase extends Biome
{

	private final SubBiomeDecorator subBiomeDecorator = new SubBiomeDecorator();

	private final BiomeAetherDecorator biomeDecorator = new BiomeAetherDecorator();

	private final List<SubBiome> subBiomes = Collections.synchronizedList(Lists.<SubBiome>newArrayList());

	private SubBiome defaultSubBiome;

	public BiomeAetherBase(final BiomeProperties properties, final ResourceLocation registryName)
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

	public BiomeAetherDecorator getBiomeDecorator()
	{
		return this.biomeDecorator;
	}

	public abstract IBlockState getCoastalBlock();

	public void registerSubBiome(final SubBiome subBiome)
	{
		if (!this.subBiomes.contains(subBiome))
		{
			this.subBiomes.add(subBiome);
		}
	}

	@Override
	public final WorldGenAbstractTree getRandomTreeFeature(final Random random)
	{
		return null;
	}

	@Override
	public int getSkyColorByTemp(final float temp)
	{
		return 0xC0C0FF;
	}

	@Override
	public final void decorate(final World world, final Random random, final BlockPos pos)
	{
		this.biomeDecorator.genDecorations(world, random, pos, this);
	}

	public final SubBiomeDecorator getSubBiomeDecorator()
	{
		return this.subBiomeDecorator;
	}

	public final SubBiome getDefaultSubBiome()
	{
		return this.defaultSubBiome;
	}

	public final void setDefaultSubBiome(final SubBiome defaultSubBiome)
	{
		this.defaultSubBiome = defaultSubBiome;
	}

	public List<SubBiome> getSubBiomes()
	{
		return this.subBiomes;
	}

	public abstract IIslandGenerator getIslandGenerator();

	public abstract Collection<NBT> createIslandComponents(IIslandData islandData);

	public abstract float getRarityWeight();

}
