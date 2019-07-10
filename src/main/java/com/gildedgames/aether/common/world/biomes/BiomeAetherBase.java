package com.gildedgames.aether.common.world.biomes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecoration;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class BiomeAetherBase extends Biome
{

	private final BiomeAetherDecorator biomeDecorator = new BiomeAetherDecorator();

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

	public abstract IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData);

	public abstract Collection<NBT> createIslandComponents(IIslandData islandData);

	public abstract float getRarityWeight();

	public abstract void postDecorate(World world, Random rand, BlockPos pos);

	public abstract List<WorldDecoration> createBasicDecorations(Random rand);

	public abstract List<WorldDecoration> createTreeDecorations(Random rand);

	public abstract float createForestTreeCountModifier(Random rand);

	public abstract float createOpenAreaDecorationGenChance(Random rand);
}
