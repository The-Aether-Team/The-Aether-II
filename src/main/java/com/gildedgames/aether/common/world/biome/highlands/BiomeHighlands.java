package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeHighlands extends BiomeAetherBase
{

	public BiomeHighlands(Biome.BiomeProperties properties, ResourceLocation registryName)
	{
		super(properties, registryName);

		this.registerEcosystem(new EcosystemHighlandJungle());
		this.registerEcosystem(new EcosystemHighlands());
		this.registerEcosystem(new EcosystemHighlandForest());
		this.registerEcosystem(new EcosystemHighlandPlains());
		this.registerEcosystem(new EcosystemCrystalHighlands());
	}

}
