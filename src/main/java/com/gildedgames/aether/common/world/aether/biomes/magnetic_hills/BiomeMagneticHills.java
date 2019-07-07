package com.gildedgames.aether.common.world.aether.biomes.magnetic_hills;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.generation.BlueprintWorldGen;
import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.generation.WorldDecorationSimple;
import com.gildedgames.aether.api.world.generation.positioners.PositionerLevels;
import com.gildedgames.aether.api.world.generation.positioners.PositionerSurface;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.init.GenerationAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
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
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BiomeMagneticHills extends BiomeAetherBase
{

	public BiomeMagneticHills(final BiomeProperties properties, final ResourceLocation registryName)
	{
		super(properties, registryName);

		this.topBlock = BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.MAGNETIC);
	}

	@Override
	public IBlockState getCoastalBlock()
	{
		return BlocksAether.ferrosite_sand.getDefaultState();
	}

	@Override
	public IIslandGenerator createIslandGenerator(Random rand, IIslandData islandData)
	{
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
				.maxTerrainHeight(10 + rand.nextInt(50))
				.terraces(rand.nextInt(20) == 0)
				.lakeConcentrationModifier(0.5 + (rand.nextDouble() * -2.5))
				.magneticPillars(true));
	}

	@Override
	public Collection<NBT> createIslandComponents(final IIslandData islandData)
	{
		final List<NBT> components = Lists.newArrayList();

		final BlockPos center = new BlockPos(islandData.getBounds().getCenterX(), 0, islandData.getBounds().getCenterZ());

		components
				.add(new MagneticHillsData(center, islandData.getSeed(), 300,
						600));

		return components;
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

		decorations.add(new WorldDecorationSimple(2, 0.0F, EventType.GRASS, new PositionerSurface(), GenerationAether.short_aether_grass));
		decorations.add(new WorldDecorationSimple(1, 0.2F, EventType.GRASS, new PositionerSurface(), GenerationAether.skyroot_twigs));

        List<IBlockState> flowers = Lists.newArrayList();

        flowers.addAll(GenUtil.GENERAL_FLOWER_STATES);

        decorations.add(GenUtil.createFlowerDecorations(rand, flowers, Lists.newArrayList(BlocksAether.magnetic_shroom.getDefaultState())));
        decorations.add(GenUtil.createShroomDecorations(GenUtil.SHROOM_STATES));
        decorations.add(GenUtil.createShroomDecorations(Lists.newArrayList(BlocksAether.magnetic_shroom.getDefaultState())));

        decorations.add(new WorldDecorationSimple(6, 0.0F, EventType.GRASS, new PositionerLevels(0, 128), GenerationAether.holystone_rocks));

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
			treeDecorations.add(new WorldDecorationSimple(15, 0.0F, EventType.TREE, new PositionerSurface(),
					new BlueprintWorldGen(chosen[rand.nextInt(chosen.length)])));
		}

		treeDecorations.add(new WorldDecorationSimple(1, 0.0F, EventType.TREE, new PositionerSurface(),
				new BlueprintWorldGen(GenerationAether.AMBEROOT_TREE)));

		treeDecorations.add(new WorldDecorationSimple(1, 0.0F, EventType.TREE, new PositionerSurface(),
				new TemplateWorldGen(GenerationAether.skyroot_moa_nest_tree_1)));

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
