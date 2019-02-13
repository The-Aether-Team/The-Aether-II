package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

import com.gildedgames.aether.api.world.generation.BlueprintWorldGen;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.generation.positioners.PositionerLevels;
import com.gildedgames.aether.api.world.generation.positioners.PositionerSurface;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.IslandGenerators;
import com.gildedgames.orbis_api.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BiomeIrradiatedForests extends BiomeAetherBase
{

	public BiomeIrradiatedForests(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED);
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.quicksoil.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData)
	{
		return IslandGenerators.IRRADIATED_FORESTS;
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		return Lists.newArrayList(new IrradiatedForestsData(120, islandData.getSeed(), islandData.getBounds()));
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

		decorations.add(new WorldDecorationSimple(2, 0.0F, EventType.GRASS, new PositionerSurface(), GenerationAether.short_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2F, EventType.GRASS, new PositionerSurface(), GenerationAether.skyroot_twigs));

		decorations.add(new WorldDecorationSimple(6, 0.0F, EventType.GRASS, new PositionerLevels(0, 128), GenerationAether.holystone_rocks));

		decorations.add(new WorldDecorationSimple(1, 0.06F, EventType.CUSTOM, new PositionerLevels(90, 130), GenerationAether.golden_aercloud));

		return decorations;
	}

	@Override
	public List<WorldDecoration> createTreeDecorations(Random rand)
	{
		List<WorldDecoration> treeDecorations = Lists.newArrayList();

		treeDecorations.add(new WorldDecorationSimple(30, 0.0F, EventType.TREE, new PositionerSurface(), new BlueprintWorldGen(GenerationAether.AMBEROOT_TREE)));

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
