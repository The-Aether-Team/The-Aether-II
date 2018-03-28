package com.gildedgames.aether.common.world.aether.biomes.highlands;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BiomeHighlandMegacoast extends BiomeAetherBase
{

	public BiomeHighlandMegacoast(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.setDefaultSubBiome(new SubBiomeHighlands());

		this.registerSubBiome(new SubBiomeHighlandJungle());
		this.registerSubBiome(new SubBiomeHighlandPlains());
		this.registerSubBiome(new SubBiomeHighlandForest());
		this.registerSubBiome(new SubBiomeCrystalHighlands());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator getIslandGenerator()
	{
		return IslandGenerators.HIGHLAND_MEGACOAST;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Collections.emptyList();
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
