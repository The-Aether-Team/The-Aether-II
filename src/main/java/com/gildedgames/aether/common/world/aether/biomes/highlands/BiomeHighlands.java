package com.gildedgames.aether.common.world.aether.biomes.highlands;

import com.gildedgames.aether.api.world.generation.TemplateDefinitionPool;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.aether.common.world.templates.TemplateWorldGen;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.gildedgames.orbis.api.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

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
	public IIslandGenerator createIslandGenerator(Random rand)
	{
		int range = rand.nextInt(20);

		if (range < 5)
		{
			return IslandGenerators.HIGHLAND_MEGACOAST;
		}
		else if (range < 10)
		{
			return IslandGenerators.HIGHLAND_PLAINS;
		}

		return IslandGenerators.HIGHLANDS;
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

		decorations.add(new WorldDecorationSimple(2, GenerationAether.short_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2F, GenerationAether.skyroot_twigs));

		decorations.add(new WorldDecorationSimple(6, GenerationAether.holystone_rocks)
		{
			@Override
			public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
			{
				final int x = rand.nextInt(16) + 8;
				final int y = rand.nextInt(128);
				final int z = rand.nextInt(16) + 8;

				return pos.add(x, y, z);
			}
		});

		decorations.add(new WorldDecorationSimple(1, 0.06F, GenerationAether.golden_aercloud)
		{
			@Override
			public BlockPos findPositionToPlace(final IBlockAccessExtended blockAccess, final Random rand, final BlockPos pos)
			{
				final int width = 16;
				final int minY = 90;
				final int maxY = 130;

				return pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));
			}
		});

		return decorations;
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		List<WorldDecoration> treeDecorations = Lists.newArrayList();

		TemplateDefinitionPool[] forest = new TemplateDefinitionPool[]
				{
						GenerationAether.blue_skyroot_tree, GenerationAether.green_skyroot_tree, GenerationAether.green_skyroot_oak,
						GenerationAether.green_skyroot_windswept, GenerationAether.green_skyroot_small_pine, GenerationAether.green_skyroot_pine
				};

		TemplateDefinitionPool[] neopolitan = new TemplateDefinitionPool[]
				{
						GenerationAether.blue_skyroot_tree, GenerationAether.green_skyroot_tree, GenerationAether.green_skyroot_oak,
						GenerationAether.dark_blue_skyroot_oak, GenerationAether.dark_blue_skyroot_tree, GenerationAether.golden_oak
				};

		TemplateDefinitionPool[] chosen = rand.nextBoolean() ? neopolitan : forest;

		int amountOfTreeTypes = 2 + rand.nextInt(4);

		for (int i = 0; i < amountOfTreeTypes; i++)
		{
			treeDecorations.add(new WorldDecorationSimple(15,
					new TemplateWorldGen(chosen[rand.nextInt(forest.length)])));
		}

		treeDecorations.add(new WorldDecorationSimple(1 + rand.nextInt(3),
				new TemplateWorldGen(GenerationAether.skyroot_moa_nest_tree_1)));

		treeDecorations.add(new WorldDecorationSimple(1 + rand.nextInt(3), 0.5F * rand.nextFloat(),
				new TemplateWorldGen(GenerationAether.golden_oak)));

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
