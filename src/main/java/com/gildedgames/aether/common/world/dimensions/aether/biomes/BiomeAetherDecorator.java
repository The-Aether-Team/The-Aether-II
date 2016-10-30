package com.gildedgames.aether.common.world.dimensions.aether.biomes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud.AercloudVariant;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.registry.GenerationAether;
import com.gildedgames.aether.common.registry.TemplatesAether;
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BiomeAetherDecorator
{

	protected WorldGenAetherMinable genAmbrosium, genZanite, genGravitite, genIcestone, genArkenium;

	protected WorldGenAetherMinable genMossyHolystone, genCrudeScatterglass;

	protected WorldGenAetherFlowers genPurpleFlowers, genWhiteRoses, genBurstblossom;

	protected WorldGenOrangeTree genOrangeTree;

	protected WorldGenAetherFlowers genBlueberryBushes;

	protected WorldGenQuicksoil genQuicksoil;

	protected WorldGenAetherLakes genAetherLakes;

	protected WorldGenAercloud genColdColumbusAercloud, genColdFlatAercloud, genBlueAercloud;

	protected WorldGenPurpleAercloud genPurpleAercloud;

	public boolean generateBushes = true;

	public BiomeAetherDecorator()
	{
		BlockMatcher holystoneMatcher = BlockMatcher.forBlock(BlocksAether.holystone);

		this.genAmbrosium = new WorldGenAetherMinable(BlocksAether.ambrosium_ore.getDefaultState(), 16, holystoneMatcher);
		this.genZanite = new WorldGenAetherMinable(BlocksAether.zanite_ore.getDefaultState(), 8, holystoneMatcher);
		this.genGravitite = new WorldGenAetherMinable(BlocksAether.gravitite_ore.getDefaultState(), 4, holystoneMatcher);
		this.genIcestone = new WorldGenAetherMinable(BlocksAether.icestone_ore.getDefaultState(), 10, holystoneMatcher);
		this.genArkenium = new WorldGenAetherMinable(BlocksAether.arkenium_ore.getDefaultState(), 8, holystoneMatcher);

		this.genMossyHolystone = new WorldGenAetherMinable(BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), 20, holystoneMatcher);
		this.genCrudeScatterglass = new WorldGenAetherMinable(BlocksAether.crude_scatterglass.getDefaultState(), 20, holystoneMatcher);

		this.genPurpleFlowers = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER), 64);
		this.genWhiteRoses = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE), 64);
		this.genBurstblossom = new WorldGenAetherFlowers(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.BURSTBLOSSOM), 64);

		this.genOrangeTree = new WorldGenOrangeTree();

		this.genBlueberryBushes = new WorldGenAetherFlowers(BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true), 32);

		this.genQuicksoil = new WorldGenQuicksoil();
		this.genAetherLakes = new WorldGenAetherLakes(Blocks.WATER.getDefaultState());

		this.genColdFlatAercloud = new WorldGenAercloud(this.getAercloudState(BlockAercloud.COLD_AERCLOUD), 64, true);
		this.genColdColumbusAercloud = new WorldGenAercloud(this.getAercloudState(BlockAercloud.COLD_AERCLOUD), 16, false);
		this.genBlueAercloud = new WorldGenAercloud(this.getAercloudState(BlockAercloud.BLUE_AERCLOUD), 8, false);

		this.genPurpleAercloud = new WorldGenPurpleAercloud(this.getAercloudState(BlockAercloud.PURPLE_AERCLOUD), 4, false);
	}

	protected IBlockState getAercloudState(AercloudVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.PROPERTY_VARIANT, variant);
	}

	protected void genDecorations(final World world, Random random, BlockPos pos, Biome genBase)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, pos));

		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		this.generateOres(world, random, pos);

		int x, y, z;

		int count;

		int sectorX = IslandSectorAccess.inst().getSectorCoord(chunkX);
		int sectorY = IslandSectorAccess.inst().getSectorCoord(chunkZ);

		IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(world, sectorX, sectorY);

		if (sector == null)
		{
			return;
		}

		final List<IslandData> islandsToGenerate = Lists.newArrayList();

		for (x = 0; x < 16; x++)
		{
			for (z = 0; z < 16; z++)
			{
				IslandData island = sector.getIslandDataAtBlockPos(pos.getX() + x, pos.getZ() + z);

				if (island == null || islandsToGenerate.contains(island))
				{
					continue;
				}

				islandsToGenerate.add(island);
			}
		}

		boolean oneIslandOnly = islandsToGenerate.size() == 1;
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

				BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z));

				boolean generated = GenerationAether.mysterious_henge.generate(world, random, pos2, true);

				if (generated)
				{
					island.setMysteriousHengePos(pos2);
					break;
				}
			}
		}

		//if (random.nextInt(5) == 0)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z));

			pos2 = pos2.add(0, -TemplatesAether.labyrinth_entrance_underground_1.getSize().getY(), 0);

			GenerationAether.labyrinth_entrance_underground_1.generate(world, random, pos2);
		}

		// Labyrinth Entrance
		if (oneIslandOnly && island.getLabyrinthEntrancePos() == null && random.nextInt(5) == 0)
		{
			for (int i = 0; i < 10; i++)
			{
				x = random.nextInt(16) + 8;
				z = random.nextInt(16) + 8;

				BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z));

				boolean generated = GenerationAether.labyrinth_entrance.generate(world, random, pos2);

				if (generated)
				{
					island.setLabyrinthEntrancePos(pos2);
					break;
				}
			}
		}

		// Moa Nests
		if (random.nextInt(4) == 0)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

			GenerationAether.skyroot_moa_nest.generate(world, random, pos2);
		}

		// Tree Generator
		for (count = 0; count < 4; count++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			WorldGenerator treeGen = genBase.genBigTreeChance(random);

			BlockPos randPos = world.getHeight(pos.add(x, 0, z));

			if (treeGen != null)
			{
				treeGen.generate(world, random, randPos);
			}
		}

		//Decorate Ecosystems
		if (genBase instanceof BiomeAetherBase)
		{
			BiomeAetherBase biome = (BiomeAetherBase) genBase;

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

		// Aether Tall Grass Generator
		for (count = 0; count < 4; count++)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;
			y = this.nextInt(random, world.getHeight(pos.add(x, 0, z)).getY() * 2);

			GenerationAether.aether_grass.generate(world, random, pos.add(x, y, z));
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

	private void generateMineable(WorldGenAetherMinable minable, World world, Random random, BlockPos pos, int minY, int maxY, int attempts)
	{
		for (int count = 0; count < attempts; count++)
		{
			BlockPos randomPos = pos.add(random.nextInt(16), random.nextInt(maxY - minY) + minY, random.nextInt(16));

			minable.generate(world, random, randomPos);
		}
	}

	private void generateCaveMineable(WorldGenAetherMinable minable, World world, Random random, BlockPos pos, int minY, int maxY, int attempts)
	{
		for (int count = 0; count < attempts; count++)
		{
			BlockPos randomPos = pos.add(random.nextInt(16), random.nextInt(maxY - minY) + minY, random.nextInt(16));
//			randomPos = world.getTopBlockHeight(randomPos);

			if (world.getLightFor(EnumSkyBlock.SKY, randomPos) <= 7)
			{
				minable.generate(world, random, randomPos);
			}
		}
	}

	private void generateCloud(WorldGenAercloud gen, World world, BlockPos pos, Random rand, int rarity, int width, int minY, int maxY)
	{
		if (rand.nextInt(rarity) == 0)
		{
			BlockPos nextPos = pos.add(rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));

			gen.generate(world, rand, nextPos);
		}
	}

	protected int nextInt(Random random, int i)
	{
		if (i <= 1)
		{
			return 0;
		}

		return random.nextInt(i);
	}

	protected void generateOres(World world, Random random, BlockPos pos)
	{
		this.generateMineable(this.genAmbrosium, world, random, pos, 0, 128, 20);
		this.generateMineable(this.genZanite, world, random, pos, 0, 128, 15);
		this.generateMineable(this.genGravitite, world, random, pos, 0, 100, 6);
		this.generateMineable(this.genIcestone, world, random, pos, 0, 128, 10);
		this.generateMineable(this.genArkenium, world, random, pos, 0, 128, 20);
		this.generateCaveMineable(this.genMossyHolystone, world, random, pos, 0, 90, 45);
		this.generateCaveMineable(this.genCrudeScatterglass, world, random, pos, 0, 90, 45);
	}

	protected void generateClouds(World world, Random random, BlockPos pos)
	{
		this.generateCloud(this.genBlueAercloud, world, pos, random, 15, 16, 90, 130);
//		this.generateCloud(this.genColdFlatAercloud, world, pos, random, 10, 16, 32);
		this.generateCloud(this.genColdColumbusAercloud, world, pos, random, 30, 16, 90, 130);

		this.generateCloud(this.genPurpleAercloud, world, pos, random, 50, 4, 90, 130);
	}
}
