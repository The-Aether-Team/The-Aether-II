package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.features.TemplatePipeline;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.BlockLog;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class BiomeHighlandForest extends BiomeAetherBase
{

	public static final WorldGenSkyrootTree trees = new WorldGenSkyrootTree(BlocksAether.skyroot_log.getDefaultState().withProperty(BlockAetherLog.PROPERTY_LOG_AXIS, BlockLog.EnumAxis.Y),
			BlocksAether.green_skyroot_leaves.getDefaultState());

	public BiomeHighlandForest()
	{
		super(new BiomeProperties("Highland Forest").setRainDisabled().setTemperature(0.5f));

		//this.topBlock = BlocksAether.woven_skyroot_sticks.getDefaultState();

		this.setRegistryName(AetherCore.getResource("aether_highland_forest"));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random random)
	{
		return trees;
	}

	@Override
	public void initTemplates(WorldServer world, TemplatePipeline pipeline, TemplateManager manager)
	{

	}

	@Override
	public WorldGenTemplate fetchTemplateToGenerate(Random random)
	{
		return null;
	}

}
