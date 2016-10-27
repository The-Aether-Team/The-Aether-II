package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.gildedgames.aether.common.world.features.WorldGenTemplateGroup;
import com.gildedgames.aether.common.world.features.placement_conditions.FlatGroundPlacementCondition;
import com.gildedgames.aether.common.world.features.placement_conditions.ReplaceablePlacementCondition;
import com.google.common.collect.Lists;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class EcosystemHighlandForest implements Ecosystem
{

	public static WorldGenTemplate large_green_skyroot_pine_1, large_green_skyroot_pine_2, large_green_skyroot_pine_3, large_green_skyroot_pine_4;

	public static WorldGenTemplate green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4, green_skyroot_pine_5;

	public static WorldGenTemplateGroup large_green_skyroot_pine, green_skyroot_pine;

	private List<WorldDecoration> decorations = Lists.newArrayList();

	private boolean hasInit;

	@Override
	public boolean hasDesiredTemperature()
	{
		return true;
	}

	@Override
	public boolean hasDesiredMoisture()
	{
		return true;
	}

	@Override
	public double getDesiredTemperature()
	{
		return 0.3;
	}

	@Override
	public double getDesiredMoisture()
	{
		return 0.3;
	}

	@Override
	public List<WorldDecoration> fetchDecorations()
	{
		return this.decorations;
	}

	@Override
	public void initDecorations(WorldServer world, TemplateManager manager)
	{
		MinecraftServer server = world.getMinecraftServer();

		this.large_green_skyroot_pine_1 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/large_green_skyroot_pine_1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.large_green_skyroot_pine_2 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/large_green_skyroot_pine_2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.large_green_skyroot_pine_3 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/large_green_skyroot_pine_3")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.large_green_skyroot_pine_4 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/large_green_skyroot_pine_4")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

		this.green_skyroot_pine_1 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/green_skyroot_pine_1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_pine_2 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/green_skyroot_pine_2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_pine_3 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/green_skyroot_pine_3")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_pine_4 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/green_skyroot_pine_4")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_pine_5 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/pine/green/green_skyroot_pine_5")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

		this.large_green_skyroot_pine = new WorldGenTemplateGroup(this.large_green_skyroot_pine_1, this.large_green_skyroot_pine_2, this.large_green_skyroot_pine_3, this.large_green_skyroot_pine_4);
		this.green_skyroot_pine = new WorldGenTemplateGroup(this.green_skyroot_pine_1, this.green_skyroot_pine_2, this.green_skyroot_pine_3, this.green_skyroot_pine_4, this.green_skyroot_pine_5);

		this.decorations.add(new WorldDecoration()
		{
			@Override
			public boolean shouldGenerate(Random random)
			{
				return true;
			}

			@Override
			public int getGenerationCount()
			{
				return 3;
			}

			@Override public WorldGenerator getGenerator()
			{
				return EcosystemHighlandForest.this.large_green_skyroot_pine;
			}
		});

		this.decorations.add(new WorldDecoration()
		{
			@Override
			public boolean shouldGenerate(Random random)
			{
				return true;
			}

			@Override
			public int getGenerationCount()
			{
				return 15;
			}

			@Override public WorldGenerator getGenerator()
			{
				return EcosystemHighlandForest.this.green_skyroot_pine;
			}
		});
	}

	@Override
	public boolean hasInitDecorations()
	{
		return this.hasInit;
	}

	@Override
	public void setInitDecorations(boolean flag)
	{
		this.hasInit = flag;
	}

}
