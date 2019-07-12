package com.gildedgames.aether.common.world.biomes.forgotten_highlands;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecoration;
import com.gildedgames.aether.api.world.decoration.WorldDecorationSimple;
import com.gildedgames.aether.api.world.generation.BlueprintWorldGen;
import com.gildedgames.aether.api.world.generation.positioners.PositionerLevels;
import com.gildedgames.aether.api.world.generation.positioners.PositionerSurface;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.init.GenerationAether;
import com.gildedgames.aether.common.world.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.island.generators.IslandGeneratorForgottenHighlands;
import com.gildedgames.aether.common.world.util.GenUtil;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BiomeForgottenHighlands extends BiomeAetherBase
{

	public BiomeForgottenHighlands(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.AETHER);
	}

	@Override
	public BlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData)
	{
		return new IslandGeneratorForgottenHighlands();
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Collections.emptyList();
	}

	@Override
	public float getRarityWeight()
	{
		return 0.75F;
	}

	@Override
	public void postDecorate(final World world, final Random rand, final BlockPos pos)
	{

	}

	@Override
	public List<WorldDecoration> createBasicDecorations(Random rand)
	{
		List<WorldDecoration> decorations = Lists.newArrayList();

		decorations
				.add(new WorldDecorationSimple(6, 0.0F, new PositionerSurface(), GenerationAether.short_aether_grass,
						GenerationAether.aether_grass, GenerationAether.long_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2F, f.GRASS, new PositionerSurface(), GenerationAether.skyroot_twigs));


        List<BlockState> flowers = Lists.newArrayList();

        flowers.addAll(GenUtil.GENERAL_FLOWER_STATES);

        decorations.add(GenUtil.createFlowerDecorations(rand, flowers, Lists.newArrayList(BlocksAether.forgotten_rose.getDefaultState())));
        decorations.add(GenUtil.createShroomDecorations(GenUtil.SHROOM_STATES));

        decorations.add(new WorldDecorationSimple(6, 0.0F, f.GRASS, new PositionerLevels(0, 128), GenerationAether.holystone_rocks));

		decorations.add(new WorldDecorationSimple(1, 0.06F, f.CUSTOM, new PositionerLevels(90, 130), GenerationAether.golden_aercloud));

		return decorations;
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		List<WorldDecoration> treeDecorations = Lists.newArrayList();

		treeDecorations.add(new WorldDecorationSimple(6, 0.0F, f.TREE, new PositionerSurface(), new BlueprintWorldGen(GenerationAether.SKYROOT_OAK_GREEN)));
		treeDecorations.add(new WorldDecorationSimple(1, 0.0F, f.TREE, new PositionerSurface(), new BlueprintWorldGen(GenerationAether.AMBEROOT_TREE)));

		return treeDecorations;
	}

	@Override
	public float createForestTreeCountModifier(Random rand)
	{
		if (rand.nextInt(30) == 0)
		{
			return 0.1F + (rand.nextFloat() * 0.9F);
		}

		return 0.75F + (rand.nextFloat() * 0.25F);
	}

	@Override
	public float createOpenAreaDecorationGenChance(Random rand)
	{
		return 0.15F * rand.nextFloat();
	}

}
