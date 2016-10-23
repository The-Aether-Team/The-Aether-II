package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.features.TemplatePipeline;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public abstract class BiomeAetherBase extends Biome
{

	protected final BiomeAetherDecorator biomeDecorator = new BiomeAetherDecorator();

	private boolean hasInit = false;

	public BiomeAetherBase(BiomeProperties properties)
	{
		super(properties);

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.aether_dirt.getDefaultState();

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xC0C0FF;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos)
	{
		this.biomeDecorator.genDecorations(world, random, pos, this);
	}

	public abstract WorldGenTemplate fetchTemplateToGenerate(Random random);

	public abstract void initTemplates(WorldServer world, TemplatePipeline pipeline, TemplateManager manager);

	public final void markTemplatesInitiated()
	{
		this.hasInit = true;
	}

	public final boolean areTemplatesInitiated()
	{
		return this.hasInit;
	}

}
