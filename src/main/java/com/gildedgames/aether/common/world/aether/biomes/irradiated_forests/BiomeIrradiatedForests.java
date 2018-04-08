package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

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
import java.util.Random;

public class BiomeIrradiatedForests extends BiomeAetherBase
{

	public BiomeIrradiatedForests(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED);

		this.setDefaultSubBiome(new SubBiomeIrradiated());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator getIslandGenerator()
	{
		return IslandGenerators.IRRADIATED_FORESTS;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Lists.newArrayList(new IrradiatedForestsData(120, islandData.getSeed(), islandData.getBounds().getWidth(), islandData.getBounds().getLength(),
				new BlockPos(islandData.getBounds().getMinX(), islandData.getBounds().getMinY(), islandData.getBounds().getMinZ())));
	}

	@Override
	public float getRarityWeight()
	{
		return 0.75F;
	}

	@Override
	public void postDecorate(final World world, final Random rand, final BlockPos pos)
	{

	}

}
