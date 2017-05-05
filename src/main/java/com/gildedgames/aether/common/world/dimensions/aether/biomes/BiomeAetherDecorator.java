package com.gildedgames.aether.common.world.dimensions.aether.biomes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.content.TemplatesAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherFlowers;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherLakes;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherMinable;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenQuicksoil;
import com.gildedgames.aether.common.world.dimensions.aether.features.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.dimensions.aether.features.aerclouds.WorldGenPurpleAercloud;
import com.gildedgames.aether.common.world.dimensions.aether.features.trees.WorldGenOrangeTree;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.google.common.collect.Lists;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.List;
import java.util.Random;

public class BiomeAetherDecorator
{

	private final WorldGenAetherMinable genAmbrosium, genZanite, genGravitite, genIcestone, genArkenium, genCoarseAetherDirtOnDirt, genCoarseAetherDirtOnHolystone;

	private final WorldGenAetherMinable genMossyHolystone, genCrudeScatterglass;

	private final WorldGenAetherFlowers genPurpleFlowers, genWhiteRoses, genBurstblossom;

	private final WorldGenOrangeTree genOrangeTree;

	private final WorldGenAetherFlowers genBlueberryBushes;

	private final WorldGenQuicksoil genQuicksoil;

	private final WorldGenAetherLakes genAetherLakes;

	private final WorldGenAercloud genColdColumbusAercloud, genColdFlatAercloud, genBlueAercloud;

	private final WorldGenPurpleAercloud genPurpleAercloud;

	public boolean generateBushes = true;

	public BiomeAetherDecorator()
	{
		final BlockMatcher holystoneMatcher = BlockMatcher.forBlock(BlocksAether.holystone);

		this.genAmbrosium = new WorldGenAetherMinable(BlocksAether.ambrosium_ore.getDefaultState(), 16, holystoneMatcher);
		this.genZanite = new WorldGenAetherMinable(BlocksAether.zanite_ore.getDefaultState(), 8, holystoneMatcher);
		this.genGravitite = new WorldGenAetherMinable(BlocksAether.gravitite_ore.getDefaultState(), 5, holystoneMatcher);
		this.genIcestone = new WorldGenAetherMinable(BlocksAether.icestone_ore.getDefaultState(), 12, holystoneMatcher);
		this.genArkenium = new WorldGenAetherMinable(BlocksAether.arkenium_ore.getDefaultState(), 6, holystoneMatcher);

		final BlockMatcher dirtMatcher = BlockMatcher.forBlock(BlocksAether.aether_dirt);

		this.genCoarseAetherDirtOnDirt = new WorldGenAetherMinable(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, dirtMatcher);
		this.genCoarseAetherDirtOnHolystone = new WorldGenAetherMinable(BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, holystoneMatcher);

		this.genMossyHolystone = new WorldGenAetherMinable(BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), 20, holystoneMatcher);
		this.genCrudeScatterglass = new WorldGenAetherMinable(BlocksAether.crude_scatterglass.getDefaultState(), 16, holystoneMatcher);

		this.genPurpleFlowers = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER), 64);
		this.genWhiteRoses = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE), 64);
		this.genBurstblossom = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.BURSTBLOSSOM), 64);

		this.genOrangeTree = new WorldGenOrangeTree();

		this.genBlueberryBushes = new WorldGenAetherFlowers(BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true), 32);

		this.genQuicksoil = new WorldGenQuicksoil();
		this.genAetherLakes = new WorldGenAetherLakes(Blocks.WATER.getDefaultState());

		this.genColdFlatAercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 64, true);
		this.genColdColumbusAercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 16, false);
		this.genBlueAercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.BLUE_AERCLOUD), 8, false);

		this.genPurpleAercloud = new WorldGenPurpleAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.PURPLE_AERCLOUD), 4, false);
	}

	protected void genDecorations(final World world, final Random random, final BlockPos pos, final Biome genBase)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, pos));

		final int chunkX = pos.getX() >> 4;
		final int chunkZ = pos.getZ() >> 4;

		this.generateOres(world, random, pos);

		int x, y, z;

		int count;

		final IslandSector sector = IslandSectorAccess.inst().attemptToLoadSectorInChunk(world, chunkX, chunkZ);

		if (sector == null)
		{
			return;
		}

		final List<IslandData> islandsToGenerate = Lists.newArrayList();

		for (x = 0; x < 16; x++)
		{
			for (z = 0; z < 16; z++)
			{
				final List<IslandData> islands = sector.getIslandDataAtBlockPos(pos.getX() + x, pos.getZ() + z);

				if (islands.size() <= 0)
				{
					continue;
				}

				for (final IslandData data : islands)
				{
					if (!islandsToGenerate.contains(data))
					{
						islandsToGenerate.add(data);
					}
				}
			}
		}

		final boolean oneIslandOnly = islandsToGenerate.size() == 1;
		IslandData island = null;

		if (oneIslandOnly)
		{
			island = islandsToGenerate.get(0);
		}

		// Mysterious Henge
		if (oneIslandOnly && island.getMysteriousHengePos() == null && random.nextBoolean())
		{
			for (int i = 0; i < 10; i++)
			{
				x = random.nextInt(16) + 8;
				z = random.nextInt(16) + 8;

				final BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

				final boolean generated = GenerationAether.mysterious_henge.generate(world, random, pos2, true);

				if (generated)
				{
					island.setMysteriousHengePos(pos2);
					break;
				}
			}
		}

		// Moa Nests
		if (random.nextInt(4) == 0)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			final BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

			GenerationAether.skyroot_moa_nest.generate(world, random, pos2);
		}

		// Tree Generator
		for (count = 0; count < 4; count++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			final WorldGenerator treeGen = genBase.genBigTreeChance(random);

			final BlockPos randPos = world.getHeight(pos.add(x, 0, z));

			if (treeGen != null)
			{
				treeGen.generate(world, random, randPos);
			}
		}

		//Decorate Ecosystems
		if (genBase instanceof BiomeAetherBase)
		{
			final BiomeAetherBase biome = (BiomeAetherBase) genBase;

			biome.decorateEcosystems(world, random, pos);
		}

		// Orange Tree Generator
		for (count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genOrangeTree.generate(world, random, pos.add(x, y, z));
		}

		// Blueberry Bush Generator
		if (this.generateBushes)
		{
			for (count = 0; count < 2; count++)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(128);
				z = random.nextInt(16) + 8;

				this.genBlueberryBushes.generate(world, random, pos.add(x, y, z));
			}
		}

		// Purple Flowers Generator
		for (count = 0; count < 6; count++)
		{
			if (random.nextInt(2) == 0)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(128);
				z = random.nextInt(16) + 8;

				this.genPurpleFlowers.generate(world, random, pos.add(x, y, z));
			}
		}

		// White Rose Generator
		for (count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genWhiteRoses.generate(world, random, pos.add(x, y, z));
		}

		// Burstblossom Generator
		for (count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genBurstblossom.generate(world, random, pos.add(x, y, z));
		}

		this.generateClouds(world, random, new BlockPos(pos.getX(), 0, pos.getZ()));

		// Lake Generator
		/*if (random.nextInt(4) == 0)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;
		
			this.genAetherLakes.generate(world, random, pos.add(x, y, z));
		}*/
	}

	private void generateMineable(final WorldGenAetherMinable minable, final World world, final Random random, final BlockPos pos, final int minY, final int maxY, final int attempts)
	{
		for (int count = 0; count < attempts; count++)
		{
			final BlockPos randomPos = pos.add(random.nextInt(16), random.nextInt(maxY - minY) + minY, random.nextInt(16));

			minable.generate(world, random, randomPos);
		}
	}

	private void generateCloud(final WorldGenAercloud gen, final World world, final BlockPos pos, final Random rand, final int rarity, final int width, final int minY, final int maxY)
	{
		if (rand.nextInt(rarity) == 0)
		{
			final BlockPos nextPos = pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));

			gen.generate(world, rand, nextPos);
		}
	}

	protected int nextInt(final Random random, final int i)
	{
		if (i <= 1)
		{
			return 0;
		}

		return random.nextInt(i);
	}

	protected void generateOres(final World world, final Random random, final BlockPos pos)
	{
		this.generateMineable(this.genAmbrosium, world, random, pos, 0, 128, 20);
		this.generateMineable(this.genZanite, world, random, pos, 0, 128, 15);
		this.generateMineable(this.genGravitite, world, random, pos, 0, 128, 10);
		this.generateMineable(this.genIcestone, world, random, pos, 0, 128, 20);
		this.generateMineable(this.genArkenium, world, random, pos, 0, 128, 13);

		this.generateMineable(this.genCoarseAetherDirtOnDirt, world, random, pos, 0, 128, 10);
		this.generateMineable(this.genCoarseAetherDirtOnHolystone, world, random, pos, 0, 128, 20);

		this.generateMineable(this.genMossyHolystone, world, random, pos, 0, 90, 45);
		this.generateMineable(this.genCrudeScatterglass, world, random, pos, 0, 110, 25);
	}

	protected void generateClouds(final World world, final Random random, final BlockPos pos)
	{
		this.generateCloud(this.genBlueAercloud, world, pos, random, 15, 16, 90, 130);
		//		this.generateCloud(this.genColdFlatAercloud, world, pos, random, 10, 16, 32);
		this.generateCloud(this.genColdColumbusAercloud, world, pos, random, 30, 16, 90, 130);

		this.generateCloud(this.genPurpleAercloud, world, pos, random, 50, 4, 90, 130);
	}
}
