package com.gildedgames.aether.common.world.biome.highlands;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.biome.BiomeAetherBase;
import com.gildedgames.aether.common.world.biome.Ecosystem;
import com.gildedgames.aether.common.world.biome.WorldDecoration;
import com.gildedgames.aether.common.world.features.WorldGenMoaNest;
import com.gildedgames.aether.common.world.features.WorldGenTemplate;
import com.gildedgames.aether.common.world.features.WorldGenTemplateGroup;
import com.gildedgames.aether.common.world.features.placement_conditions.FlatGroundPlacementCondition;
import com.gildedgames.aether.common.world.features.placement_conditions.ReplaceablePlacementCondition;
import com.google.common.collect.Lists;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class EcosystemHighlandJungle implements Ecosystem
{

	public static WorldGenTemplate green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3, green_skyroot_windswept_4, green_skyroot_windswept_5;

	public static WorldGenTemplate labyrinth_ruins_1, labyrinth_ruins_2, labyrinth_ruins_3, labyrinth_ruins_4, labyrinth_ruins_5;

	public static WorldGenTemplate moa_nest_tree_1;

	public static WorldGenTemplateGroup green_skyroot_windswept, labyrinth_ruins_group;

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
		return 0.5;
	}

	@Override
	public double getDesiredMoisture()
	{
		return 0.5;
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

		this.green_skyroot_windswept_1 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/windswept/green/green_skyroot_windswept_1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_windswept_2 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/windswept/green/green_skyroot_windswept_2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_windswept_3 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/windswept/green/green_skyroot_windswept_3")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_windswept_4 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/windswept/green/green_skyroot_windswept_4")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.green_skyroot_windswept_5 = new WorldGenTemplate(manager.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "highlands/trees/windswept/green/green_skyroot_windswept_5")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

		this.green_skyroot_windswept = new WorldGenTemplateGroup(this.green_skyroot_windswept_1, this.green_skyroot_windswept_2, this.green_skyroot_windswept_3, this.green_skyroot_windswept_4, this.green_skyroot_windswept_5);

		this.labyrinth_ruins_1 = new WorldGenTemplate(BiomeAetherBase.MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "labyrinth_ruins_1")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.labyrinth_ruins_2 = new WorldGenTemplate(BiomeAetherBase.MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "labyrinth_ruins_2")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.labyrinth_ruins_3 = new WorldGenTemplate(BiomeAetherBase.MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "labyrinth_ruins_3")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.labyrinth_ruins_4 = new WorldGenTemplate(BiomeAetherBase.MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "labyrinth_ruins_4")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());
		this.labyrinth_ruins_5 = new WorldGenTemplate(BiomeAetherBase.MANAGER.getTemplate(server, new ResourceLocation(AetherCore.MOD_ID, "labyrinth_ruins_5")), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

		this.moa_nest_tree_1 = new WorldGenMoaNest(BiomeAetherBase.MANAGER.getTemplate(server, AetherCore.getResource("moa_nest/skyroot_moa_nest_tree_1")), new BlockPos(4, 5, 4), new FlatGroundPlacementCondition(), new ReplaceablePlacementCondition());

		this.labyrinth_ruins_group = new WorldGenTemplateGroup(this.labyrinth_ruins_1, this.labyrinth_ruins_2, this.labyrinth_ruins_3, this.labyrinth_ruins_4, this.labyrinth_ruins_5);

		this.decorations.add(new WorldDecoration()
		{
			@Override
			public boolean shouldGenerate(Random random)
			{
				return random.nextInt(8) == 0;
			}

			@Override
			public int getGenerationCount()
			{
				return 1;
			}

			@Override public WorldGenerator getGenerator()
			{
				return EcosystemHighlandJungle.this.labyrinth_ruins_group;
			}
		});

		this.decorations.add(new WorldDecoration()
		{
			@Override
			public boolean shouldGenerate(Random random)
			{
				return random.nextInt(2) == 0;
			}

			@Override
			public int getGenerationCount()
			{
				return 1;
			}

			@Override public WorldGenerator getGenerator()
			{
				return EcosystemHighlandJungle.this.moa_nest_tree_1;
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
				return EcosystemHighlandJungle.this.green_skyroot_windswept;
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
