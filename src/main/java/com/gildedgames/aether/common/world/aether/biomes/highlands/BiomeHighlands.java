package com.gildedgames.aether.common.world.aether.biomes.highlands;

import com.gildedgames.aether.api.world.generation.BlueprintWorldGen;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.generation.positioners.PositionerLevels;
import com.gildedgames.aether.api.world.generation.positioners.PositionerSurface;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.aether.common.world.aether.island.gen.types.IslandGeneratorHighlands;
import com.gildedgames.aether.common.world.templates.TemplateWorldGen;
import com.gildedgames.aether.common.world.util.GenUtil;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BiomeHighlands extends BiomeAetherBase
{

	public BiomeHighlands(final Biome.BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData)
	{
		int range = rand.nextInt(20);

		boolean firstIsland = islandData.getBounds().getMinX() == 0 && islandData.getBounds().getMinZ() == 0;

		if (range < 3)
		{
			return IslandGenerators.HIGHLAND_MEGACOAST;
		}

		int coastHeight = 1 + rand.nextInt(3);

		double coastSpread = rand.nextDouble() * 0.6;

		if (coastHeight == 0)
		{
			coastSpread = 0.0;
		}

		return new IslandGeneratorHighlands(IslandVariables.build()
				.coastHeight(coastHeight)
				.coastSpread(coastSpread)
				.lakeBlendRange(0.05 + (rand.nextDouble() * 0.5))
				.lakeDepth(rand.nextInt(40) + 5)
				.lakeScale(40.0D + (rand.nextDouble() * 30.0D))
				.lakeThreshold(rand.nextDouble() * 0.3)
				.maxTerrainHeight(firstIsland ? 10 + rand.nextInt(30) : 10 + rand.nextInt(120))
				.terraces(rand.nextBoolean())
				.lakeConcentrationModifier(0.5 + (rand.nextDouble() * -2.5)));
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

	@Override
	public List<WorldDecoration> createBasicDecorations(Random rand)
	{
		List<WorldDecoration> decorations = Lists.newArrayList();

		decorations.add(new WorldDecorationSimple(2, 0.0f, EventType.GRASS, new PositionerSurface(), GenerationAether.short_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2f, EventType.GRASS, new PositionerSurface(), GenerationAether.skyroot_twigs));

        List<IBlockState> flowers = Lists.newArrayList();

        flowers.addAll(GenUtil.GENERAL_FLOWER_STATES);

        decorations.add(GenUtil.createFlowerDecorations(rand, flowers, Lists.newArrayList(BlocksAether.highlands_tulips.getDefaultState())));
        decorations.add(GenUtil.createShroomDecorations(GenUtil.SHROOM_STATES));

        decorations.add(new WorldDecorationSimple(6, 0.0f, EventType.GRASS, new PositionerLevels(0, 128), GenerationAether.holystone_rocks));

		decorations.add(new WorldDecorationSimple(1, 0.06F, EventType.CUSTOM, new PositionerLevels(90, 130), GenerationAether.golden_aercloud));

		return decorations;
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		List<WorldDecoration> treeDecorations = Lists.newArrayList();

		BlueprintDefinition[] forest = new BlueprintDefinition[]
				{
						GenerationAether.SKYROOT_OAK_GREEN, GenerationAether.WISPROOT_GREEN
				};

		BlueprintDefinition[] neopolitan = new BlueprintDefinition[]
				{
						GenerationAether.SKYROOT_OAK_GREEN, GenerationAether.SKYROOT_OAK_DARK_BLUE, GenerationAether.SKYROOT_OAK_BLUE,
						GenerationAether.WISPROOT_GREEN, GenerationAether.WISPROOT_BLUE, GenerationAether.WISPROOT_DARK_BLUE
				};

		BlueprintDefinition[] chosen = rand.nextBoolean() ? neopolitan : forest;

		int amountOfTreeTypes = 2 + rand.nextInt(4);

		for (int i = 0; i < amountOfTreeTypes; i++)
		{
			treeDecorations.add(new WorldDecorationSimple(15, 0.0f, EventType.TREE, new PositionerSurface(),
					new BlueprintWorldGen(chosen[rand.nextInt(chosen.length)])));
		}

		treeDecorations.add(new WorldDecorationSimple(1 + rand.nextInt(3), 0.0f, EventType.TREE, new PositionerSurface(),
				new TemplateWorldGen(GenerationAether.skyroot_moa_nest_tree_1)));

		float k = rand.nextFloat();

		treeDecorations.add(new WorldDecorationSimple(1 + rand.nextInt(3), 0.5F * k, EventType.TREE, new PositionerSurface(),
				new BlueprintWorldGen(GenerationAether.AMBEROOT_TREE)));

		return treeDecorations;
	}

	@Override
	public float createForestTreeCountModifier(Random rand)
	{
		return 0.75F + (rand.nextFloat() * 0.25F);
	}

	@Override
	public float createOpenAreaDecorationGenChance(Random rand)
	{
		return 0.8F * rand.nextFloat();
	}

}
