package com.gildedgames.aether.common.world.aether.biomes.magnetic_hills;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.orbis.api.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BiomeMagneticHills extends BiomeAetherBase
{

	public BiomeMagneticHills(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.MAGNETIC);

		this.setDefaultSubBiome(new SubBiomeMagneticHills());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.crude_scatterglass.getDefaultState();
	}

	@Override
	public IIslandGenerator getIslandGenerator()
	{
		return IslandGenerators.MAGNETIC_HILLS;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		final List<NBT> components = Lists.newArrayList();

		final BlockPos center = new BlockPos(islandData.getBounds().getCenterX(), 0, islandData.getBounds().getCenterZ());

		components
				.add(new MagneticHillsData(center, islandData.getSeed(), 200,
						300));

		return components;
	}

	@Override
	public float getRarityWeight()
	{
		return 1.0F;
	}

	@Override
	public void postDecorate(final World world, final Random rand, final BlockPos pos)
	{

	}

}
