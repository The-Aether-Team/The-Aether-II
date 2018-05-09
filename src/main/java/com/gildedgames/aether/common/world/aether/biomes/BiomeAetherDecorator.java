package com.gildedgames.aether.common.world.aether.biomes;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.api.world.generation.WorldDecorationUtil;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockValkyrieGrass;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.aether.common.world.aether.WorldProviderAether;
import com.gildedgames.aether.common.world.aether.features.*;
import com.gildedgames.aether.common.world.aether.features.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.aether.features.aerclouds.WorldGenPurpleAercloud;
import com.gildedgames.aether.common.world.aether.features.trees.WorldGenOrangeTree;
import com.gildedgames.aether.common.world.aether.island.ChunkGeneratorAether;
import com.gildedgames.aether.common.world.aether.island.data.BlockAccessIsland;
import com.gildedgames.aether.common.world.templates.TemplatePlacer;
import com.gildedgames.orbis_api.core.*;
import com.gildedgames.orbis_api.core.util.BlueprintPlacer;
import com.gildedgames.orbis_api.data.schedules.ScheduleRegion;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.DataPrimer;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class BiomeAetherDecorator
{

	private final WorldGenAetherMinable genAmbrosium, genZanite, genGravitite, genIcestone, genArkenium, genQuicksoilOnGrass, genCoarseAetherDirtOnDirt, genCoarseAetherDirtOnHolystone;

	private final WorldGenAetherMinable genMossyHolystone, genCrudeScatterglass;

	private final WorldGenAetherFlowers genPurpleFlowers, genWhiteRoses, genBurstblossom, genAechorSprout;

	private final WorldGenOrangeTree genOrangeTree;

	private final WorldGenAetherFlowers genBlueberryBushes, genKirridGrass;

	private final WorldGenQuicksoil genQuicksoil;

	private final WorldGenAetherLakes genAetherLakes;

	private final WorldGenAercloud genColdColumbusAercloud, genColdFlatAercloud, genBlueAercloud;

	private final WorldGenPurpleAercloud genPurpleAercloud;

	private final WorldGenBrettlPlant genBrettlPlant;

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

		final BlockMatcher grassMatcher = BlockMatcher.forBlock(BlocksAether.aether_grass);

		this.genCoarseAetherDirtOnDirt = new WorldGenAetherMinable(
				BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, dirtMatcher);
		this.genCoarseAetherDirtOnHolystone = new WorldGenAetherMinable(
				BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, holystoneMatcher);

		this.genQuicksoilOnGrass = new WorldGenAetherMinable(BlocksAether.quicksoil.getDefaultState(), 22, grassMatcher);

		this.genMossyHolystone = new WorldGenAetherMinable(
				BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), 20, holystoneMatcher);
		this.genCrudeScatterglass = new WorldGenAetherMinable(BlocksAether.crude_scatterglass.getDefaultState(), 16, holystoneMatcher);

		this.genPurpleFlowers = new WorldGenAetherFlowers(
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER), 64);
		this.genWhiteRoses = new WorldGenAetherFlowers(
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE), 64);
		this.genBurstblossom = new WorldGenAetherFlowers(
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.BURSTBLOSSOM), 64);
		this.genAechorSprout = new WorldGenAetherFlowers(
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.AECHOR_SPROUT), 4);

		this.genOrangeTree = new WorldGenOrangeTree();

		this.genBlueberryBushes = new WorldGenAetherFlowers(
				BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true), 32);
		this.genKirridGrass = new WorldGenAetherFlowers(
				BlocksAether.valkyrie_grass.getDefaultState().withProperty(BlockValkyrieGrass.PROPERTY_HARVESTABLE, true)
						.withProperty(BlockValkyrieGrass.PROPERTY_VARIANT, BlockValkyrieGrass.FULL), 64);

		this.genQuicksoil = new WorldGenQuicksoil();
		this.genAetherLakes = new WorldGenAetherLakes(Blocks.WATER.getDefaultState());

		this.genBrettlPlant = new WorldGenBrettlPlant();

		this.genColdFlatAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 64, true);
		this.genColdColumbusAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 16, false);
		this.genBlueAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.BLUE_AERCLOUD), 8, false);

		this.genPurpleAercloud = new WorldGenPurpleAercloud(BlockAercloud.getAercloudState(BlockAercloud.PURPLE_AERCLOUD), 4, false);
	}

	public void prepareDecorationsWholeIsland(final World world, final IIslandData island, IPrepSectorData sectorData, final Random random)
	{
		IBlockAccessExtended access = new BlockAccessIsland(world, island, sectorData);
		final DataPrimer primer = new DataPrimer(access);

		final int startX = island.getBounds().getMinX();
		final int startZ = island.getBounds().getMinZ();

		final BlockPos pos = new BlockPos(startX, 0, startZ);

		//TODO: REIMPLEMENT 1.12.2
		boolean generated = false;

		for (int i = 0; i < 5000; i++)
		{
			final BlueprintDefinition outpost = GenerationAether.OUTPOST.getRandomDefinition(random);

			final int x = random.nextInt(island.getBounds().getWidth());
			final int z = random.nextInt(island.getBounds().getLength());

			if (access.canAccess(pos.getX() + x, pos.getZ() + z))
			{
				final BlockPos pos2 = access.getTopPos(pos.add(x, 0, z));

				if (pos2.getY() >= 0)
				{
					final ICreationData data = new CreationData(world).pos(pos2);

					generated = BlueprintPlacer.findPlace(primer, outpost, data, random);

					if (generated)
					{
						BlueprintPlacer.placeForced(primer, outpost, data, random);
						final PlacedBlueprint placed = island.placeBlueprint(outpost, data);

						final ScheduleRegion trigger = placed.getScheduleFromTriggerID("spawn");

						if (trigger != null)
						{
							island.setOutpostPos(trigger.getBounds().getMin());
						}

						break;
					}
				}
			}
		}

		if (!generated)
		{
			AetherCore.LOGGER.info("WARNING: OUTPOST_A NOT GENERATED ON AN ISLAND!");
		}

		this.generate(GenerationAether.ABAND_ANGEL_STOREROOM, 200, random.nextInt(10), -1, pos, island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_ANGEL_WATCHTOWER, 200, random.nextInt(10), -1, pos, island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_CAMPSITE_1A, 300, random.nextInt(10), 0, pos, island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_HUMAN_HOUSE_1A, 200, random.nextInt(10), -1, pos, island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_HUMAN_HOUSE_1B, 200, random.nextInt(10), -5, pos, island, access, primer, world, random);
		this.generate(GenerationAether.SKYROOT_WATCHTOWER_1A, 300, random.nextInt(10), 1, pos, island, access, primer, world, random);
		//this.generate(GenerationAether.WELL, 600, random.nextInt(30), -9, pos, island, manager, primer, world, random);
	}

	private void generate(
			final BlueprintDefinition def, final int tries, final int maxGenerated, final int floorHeight, final BlockPos startPos, final IIslandData island,
			final IBlockAccessExtended access, final DataPrimer primer, final World world, final Random rand)
	{
		if (maxGenerated <= 0)
		{
			return;
		}

		int amountGenerated = 0;

		for (int i = 0; i < tries; i++)
		{
			final int x = rand.nextInt(island.getBounds().getWidth());
			final int z = rand.nextInt(island.getBounds().getLength());

			if (access.canAccess(startPos.getX() + x, startPos.getZ() + z))
			{
				final BlockPos pos2 = access.getTopPos(startPos.add(x, 0, z)).add(0, floorHeight, 0);

				if (pos2.getY() >= 0)
				{
					final ICreationData data = new CreationData(world).pos(pos2);

					final boolean generated = BlueprintPlacer.findPlace(primer, def, data, rand);

					if (generated)
					{
						BlueprintPlacer.placeForced(primer, def, data, rand);
						island.placeBlueprint(def, data);

						amountGenerated++;

						if (amountGenerated >= maxGenerated)
						{
							return;
						}
					}
				}
			}
		}
	}

	private void generate(
			final BlueprintDefinitionPool pool, final int tries, final int maxGenerated, final int floorHeight, final BlockPos startPos,
			final IIslandData island,
			final IBlockAccessExtended access, final DataPrimer primer, final World world, final Random rand)
	{
		if (maxGenerated <= 0)
		{
			return;
		}

		int amountGenerated = 0;

		for (int i = 0; i < tries; i++)
		{
			final BlueprintDefinition blueprint = pool.getRandomDefinition(rand);

			final int x = rand.nextInt(island.getBounds().getWidth());
			final int z = rand.nextInt(island.getBounds().getLength());

			if (access.canAccess(startPos.getX() + x, startPos.getZ() + z))
			{
				final BlockPos pos2 = access.getTopPos(startPos.add(x, 0, z)).add(0, floorHeight, 0);

				if (pos2.getY() >= 0)
				{
					final ICreationData data = new CreationData(world).pos(pos2);

					final boolean generated = BlueprintPlacer.findPlace(primer, blueprint, data, rand);

					if (generated)
					{
						BlueprintPlacer.placeForced(primer, blueprint, data, rand);
						island.placeBlueprint(blueprint, data);

						amountGenerated++;

						if (amountGenerated >= maxGenerated)
						{
							return;
						}
					}
				}
			}
		}
	}

	protected void genDecorations(final World world, final Random random, final BlockPos pos, final Biome genBase)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, pos));

		final int chunkX = pos.getX() >> 4;
		final int chunkZ = pos.getZ() >> 4;

		this.generateOres(world, random, pos);

		int x, y, z;

		int count;

		IIslandData island = IslandHelper.get(world, chunkX, chunkZ);

		if (island == null)
		{
			return;
		}

		//Decorate SubBiomes
		IBlockAccessExtended blockAccessExtended = new BlockAccessExtendedWrapper(world);

		WorldDecorationUtil.generateDecorations(island.getBasicDecorations(), blockAccessExtended, random, pos);

		final WorldProviderAether provider = WorldProviderAether.get(world);

		WorldDecorationUtil.generateDecorationsWithNoise(island.getTreeDecorations(), blockAccessExtended, random, pos, provider.getNoise(),
				island.getOpenAreaDecorationGenChance(), island.getForestTreeCountModifier());

		// Moa Nests
		if (random.nextInt(4) == 0)
		{
			x = random.nextInt(16) + 8;
			z = random.nextInt(16) + 8;

			final BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

			TemplatePlacer.place(world, GenerationAether.skyroot_moa_nest.getRandomDefinition(random), new TemplateLoc().set(pos2), random);
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

		// Burstblossom Generator
		for (count = 0; count < 2; count++)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genAechorSprout.generate(world, random, pos.add(x, y, z));
		}

		// Kirrid Grass Generator
		if (random.nextInt(12) == 0)
		{
			for (count = 0; count < 6; count++)
			{

				x = random.nextInt(16) + 8;
				y = random.nextInt(128);
				z = random.nextInt(16) + 8;

				this.genKirridGrass.generate(world, random, pos.add(x, y, z));
			}
		}

		// Brettl Plant Generator
		if (random.nextInt(2) == 0)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genBrettlPlant.generate(world, random, pos.add(x, y, z));
		}

		this.generateClouds(world, random, new BlockPos(pos.getX(), 0, pos.getZ()));

		// Lake Generator
		if (random.nextInt(4) == 0)
		{
			x = random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = random.nextInt(16) + 8;

			this.genAetherLakes.generate(world, random, pos.add(x, y, z));
		}

		//
		if (genBase instanceof BiomeAetherBase)
		{
			final BiomeAetherBase aetherBiome = (BiomeAetherBase) genBase;

			aetherBiome.postDecorate(world, random, pos);
		}
	}

	private void generateMineable(final WorldGenAetherMinable minable, final World world, final Random random, final BlockPos pos, final int minY,
			final int maxY, final int attempts)
	{
		for (int count = 0; count < attempts; count++)
		{
			final BlockPos randomPos = pos.add(random.nextInt(16), random.nextInt(maxY - minY) + minY, random.nextInt(16));

			minable.generate(world, random, randomPos);
		}
	}

	private void generateCloud(final WorldGenAercloud gen, final World world, final BlockPos pos, final Random rand, final int rarity, final int width,
			final int minY, final int maxY)
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
		this.generateMineable(this.genAmbrosium, world, random, pos, 0, 256, 20);
		this.generateMineable(this.genZanite, world, random, pos, 0, 256, 18);
		this.generateMineable(this.genGravitite, world, random, pos, 0, 50, 10);
		this.generateMineable(this.genIcestone, world, random, pos, 0, 256, 20);
		this.generateMineable(this.genArkenium, world, random, pos, 0, 70, 15);

		this.generateMineable(this.genCoarseAetherDirtOnDirt, world, random, pos, 0, 128, 10);
		this.generateMineable(this.genCoarseAetherDirtOnHolystone, world, random, pos, 0, 100, 10);
		//this.generateMineable(this.genQuicksoilOnGrass, world, random, pos, 0, 128, 10);

		this.generateMineable(this.genMossyHolystone, world, random, pos, 0, 100, 25);
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
