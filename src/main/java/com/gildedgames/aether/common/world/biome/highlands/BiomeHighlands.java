package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.features.trees.WorldGenLargeTree;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class BiomeHighlands extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree genGreenSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public static final WorldGenSkyrootTree genBlueSkyrootTree = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.blue_skyroot_leaves.getDefaultState());

	public static final WorldGenLargeTree genGoldenOakTree = new WorldGenLargeTree(BlocksAether.golden_oak_log.getDefaultState(), BlocksAether.golden_oak_leaves.getDefaultState());

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
