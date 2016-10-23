package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.features.TemplatePipeline;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class BiomeVoid extends BiomeAetherBase
{

	public BiomeVoid()
	{
		super(new Biome.BiomeProperties("Aether Void").setRainDisabled().setTemperature(0.5f));

		this.setRegistryName(AetherCore.getResource("aether_void"));
	}

	@Override
	public WorldGenTemplate fetchTemplateToGenerate(Random random)
	{
		return null;
	}

	@Override
	public void initTemplates(WorldServer world, TemplatePipeline pipeline, TemplateManager manager)
	{

	}

}
