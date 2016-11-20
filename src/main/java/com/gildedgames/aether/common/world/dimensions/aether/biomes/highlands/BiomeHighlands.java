package com.gildedgames.aether.common.world.dimensions.aether.biomes.highlands;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeHighlands extends BiomeAetherBase
{

	public BiomeHighlands(Biome.BiomeProperties properties, ResourceLocation registryName)
	{
		super(properties, registryName);

		this.default_ecosystem = new EcosystemHighlandPlains();

		this.registerEcosystem(new EcosystemHighlandJungle());
		this.registerEcosystem(new EcosystemHighlands());
		this.registerEcosystem(new EcosystemHighlandForest());
		this.registerEcosystem(new EcosystemCrystalHighlands());
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

}
