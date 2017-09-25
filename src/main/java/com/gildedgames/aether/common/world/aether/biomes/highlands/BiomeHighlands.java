package com.gildedgames.aether.common.world.aether.biomes.highlands;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeHighlands extends BiomeAetherBase
{

	public BiomeHighlands(final Biome.BiomeProperties properties, final ResourceLocation registryName)
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

}
