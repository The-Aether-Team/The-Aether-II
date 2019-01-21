package com.gildedgames.aether.common.world.aether.biomes;

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
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherFlowers;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherMinable;
import com.gildedgames.aether.common.world.aether.features.WorldGenBrettlPlant;
import com.gildedgames.aether.common.world.aether.features.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.aether.features.aerclouds.WorldGenPurpleAercloud;
import com.gildedgames.aether.common.world.aether.features.trees.WorldGenOrangeTree;
import com.gildedgames.aether.common.world.aether.island.data.BlockAccessIsland;
import com.gildedgames.aether.common.world.templates.TemplatePlacer;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.BlueprintDefinitionPool;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.core.baking.BakedBlueprint;
import com.gildedgames.orbis_api.core.util.BlueprintPlacer;
import com.gildedgames.orbis_api.data.schedules.ScheduleRegion;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.DataPrimer;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeAetherDecorator
{

	public final boolean generateBushes = true;

	private final WorldGenAetherMinable genAmbrosium, genZanite, genGravitite, genIcestone, genArkenium, genCoarseAetherDirtOnDirt, genCoarseAetherDirtOnHolystone;

	private final WorldGenAetherMinable genMossyHolystone, genCrudeScatterglass;

	private final WorldGenAetherFlowers genAechorSprout;

	private final WorldGenOrangeTree genOrangeTree;

	private final WorldGenAetherFlowers genBlueberryBushes, genKirridGrass, genPlumproots;

	private final WorldGenAercloud genColdColumbusAercloud, genBlueAercloud;

	private final WorldGenPurpleAercloud genPurpleAercloud;

	private final WorldGenBrettlPlant genBrettlPlant;

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

		this.genMossyHolystone = new WorldGenAetherMinable(
				BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), 20, holystoneMatcher);
		this.genCrudeScatterglass = new WorldGenAetherMinable(BlocksAether.crude_scatterglass.getDefaultState(), 16, holystoneMatcher);

		this.genAechorSprout = new WorldGenAetherFlowers(
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.AECHOR_SPROUT), 4);

		this.genOrangeTree = new WorldGenOrangeTree();

		this.genBlueberryBushes = new WorldGenAetherFlowers(
				BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true), 32);
		this.genKirridGrass = new WorldGenAetherFlowers(
				BlocksAether.valkyrie_grass.getDefaultState().withProperty(BlockValkyrieGrass.PROPERTY_HARVESTABLE, true)
						.withProperty(BlockValkyrieGrass.PROPERTY_VARIANT, BlockValkyrieGrass.FULL), 64);

		this.genPlumproots = new WorldGenAetherFlowers(
				BlocksAether.plumproot.getDefaultState(), 64,
				(state) -> state == BlocksAether.aether_grass.getDefaultState() || state == BlocksAether.aether_dirt.getDefaultState());

		this.genBrettlPlant = new WorldGenBrettlPlant();

		this.genColdColumbusAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 16, false);
		this.genBlueAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.BLUE_AERCLOUD), 8, false);

		this.genPurpleAercloud = new WorldGenPurpleAercloud(BlockAercloud.getAercloudState(BlockAercloud.PURPLE_AERCLOUD), 4, false);
	}

	public void prepareDecorationsWholeIsland(final World world, BlockAccessIsland access, final IIslandData island, IPrepSectorData sectorData,
			final Random random)
	{
		final DataPrimer primer = new DataPrimer(access);

		final int startX = island.getBounds().getMinX();
		final int startZ = island.getBounds().getMinZ();

		boolean generated = false;

		final BlueprintDefinition outpostDef = GenerationAether.OUTPOST.getRandomDefinition(random);

		final Rotation rotation = (outpostDef.hasRandomRotation()) ?
				BlueprintPlacer.ROTATIONS[world.rand.nextInt(BlueprintPlacer.ROTATIONS.length)] :
				BlueprintPlacer.ROTATIONS[0];

		BakedBlueprint outpostBake = new BakedBlueprint(outpostDef.getData(), new CreationData(world).seed(random.nextLong()).rotation(rotation));

		for (int i = 0; i < 10000; i++)
		{
			final int x = random.nextInt(island.getBounds().getWidth());
			final int z = random.nextInt(island.getBounds().getLength());

			if (access.canAccess(startX + x, startZ + z))
			{
				final int y = access.getTopY(startX + x, startZ + z);

				if (y > 0)
				{
					BlockPos pos = new BlockPos(startX + x, y, startZ + z);

					generated = BlueprintPlacer.findPlace(primer, outpostBake, outpostDef.getConditions(), pos, true);

					if (generated)
					{
						final PlacedBlueprint placed = island.placeBlueprint(outpostDef, outpostBake, pos);

						final ScheduleRegion trigger = placed.getBaked().getScheduleFromTriggerID("spawn");

						if (trigger != null)
						{
							island.setOutpostPos(trigger.getBounds().getMin().add(pos));
						}

						break;
					}
				}
			}
		}

		if (!generated)
		{
			AetherCore.LOGGER.info("WARNING: OUTPOST NOT GENERATED ON AN ISLAND!");
		}

		//this.generate(GenerationAether.WISPROOT_TREE, 500, 100, island, access, primer, world, random);
		this.generate(GenerationAether.WELL, 100, random.nextInt(8), island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_ANGEL_STOREROOM, 100, random.nextInt(10), island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_ANGEL_WATCHTOWER, 100, random.nextInt(10), island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_CAMPSITE, 100, 5, island, access, primer, world, random);
		this.generate(GenerationAether.ABAND_HUMAN_HOUSE, 100, random.nextInt(10), island, access, primer, world, random);
		this.generate(GenerationAether.SKYROOT_WATCHTOWER, 100, random.nextInt(10), island, access, primer, world, random);
	}

	private void generate(
			final BlueprintDefinition def, final int tries, final int maxGenerated, final IIslandData island,
			final IBlockAccessExtended access, final DataPrimer primer, final World world, final Random rand)
	{
		if (maxGenerated <= 0)
		{
			return;
		}

		Rotation rotation = (def.hasRandomRotation()) ?
				BlueprintPlacer.ROTATIONS[rand.nextInt(BlueprintPlacer.ROTATIONS.length)] :
				BlueprintPlacer.ROTATIONS[0];

		BakedBlueprint baked = new BakedBlueprint(def.getData(), new CreationData(world).rotation(rotation));

		int amountGenerated = 0;

		final int startX = island.getBounds().getMinX();
		final int startZ = island.getBounds().getMinZ();

		for (int i = 0; i < tries; i++)
		{
			final int x = rand.nextInt(island.getBounds().getWidth());
			final int z = rand.nextInt(island.getBounds().getLength());

			if (access.canAccess(startX + x, startZ + z))
			{
				final int y = access.getTopY(startX + x, startZ + z) - def.getFloorHeight() + (def.getFloorHeight() == 0 ? 1 : 0);

				if (y >= 80)
				{
					BlockPos pos = new BlockPos(startX + x, y, startZ + z);

					final boolean generated = BlueprintPlacer.findPlace(primer, baked, def.getConditions(), pos, true);

					if (generated)
					{
						island.placeBlueprint(def, baked, pos);

						rotation = (def.hasRandomRotation()) ?
								BlueprintPlacer.ROTATIONS[rand.nextInt(BlueprintPlacer.ROTATIONS.length)] :
								BlueprintPlacer.ROTATIONS[0];

						baked = new BakedBlueprint(def.getData(), new CreationData(world).rotation(rotation));

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

	private void generate(final BlueprintDefinitionPool pool, final int tries, final int maxGenerated, final IIslandData island,
			final IBlockAccessExtended access, final DataPrimer primer, final World world, final Random rand)
	{
		if (maxGenerated <= 0)
		{
			return;
		}

		final BlueprintDefinition def = pool.getRandomDefinition(rand);

		this.generate(def, tries, maxGenerated, island, access, primer, world, rand);
	}

	protected void genDecorations(final World world, final Random random, final BlockPos pos, final Biome genBase)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, pos));

		ChunkPos chunkPos = new ChunkPos(pos);

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			this.generateOres(world, random, pos);
		}

		int x, y, z;

		int count;

		IIslandData island = IslandHelper.get(world, chunkPos.x, chunkPos.z);

		if (island == null)
		{
			return;
		}

		/*if (true)
		{
			return;
		}*/

		//Decorate SubBiomes
		IBlockAccessExtended blockAccessExtended = new BlockAccessExtendedWrapper(world);

		WorldDecorationUtil.generateDecorations(island.getBasicDecorations(), world, blockAccessExtended, random, pos);

		final WorldProviderAether provider = WorldProviderAether.get(world);

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
		{
			WorldDecorationUtil.generateDecorationsWithNoise(island.getTreeDecorations(), world, blockAccessExtended, random, pos, provider.getNoise(),
					island.getOpenAreaDecorationGenChance(), island.getForestTreeCountModifier());
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			// Moa Nests
			if (random.nextInt(4) == 0)
			{
				x = random.nextInt(16) + 8;
				z = random.nextInt(16) + 8;

				final BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

				TemplatePlacer.place(world, GenerationAether.skyroot_moa_nest.getRandomDefinition(random), new TemplateLoc().set(pos2), random);
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
		{
			// Orange Tree Generator
			for (count = 0; count < 3; count++)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(180) + 64;
				z = random.nextInt(16) + 8;

				this.genOrangeTree.generate(world, random, pos.add(x, y, z));
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			// Blueberry Bush Generator
			if (this.generateBushes)
			{
				for (count = 0; count < 3; count++)
				{
					x = random.nextInt(16) + 8;
					y = random.nextInt(180) + 64;
					z = random.nextInt(16) + 8;

					this.genBlueberryBushes.generate(world, random, pos.add(x, y, z));
				}
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS))
		{
			/*// Purple Flowers Generator
			for (count = 0; count < 8; count++)
			{
				if (random.nextInt(2) == 0)
				{
					x = random.nextInt(16) + 8;
					y = random.nextInt(180) + 64;
					z = random.nextInt(16) + 8;

					this.genPurpleFlowers.generate(world, random, pos.add(x, y, z));
				}
			}

			// White Rose Generator
			for (count = 0; count < 3; count++)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(180) + 64;
				z = random.nextInt(16) + 8;

				this.genWhiteRoses.generate(world, random, pos.add(x, y, z));
			}

			// Burstblossom Generator
			for (count = 0; count < 3; count++)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(180) + 64;
				z = random.nextInt(16) + 8;

				this.genBurstblossom.generate(world, random, pos.add(x, y, z));
			}*/

			// Aechor Sprout Generator
			for (count = 0; count < 3; count++)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(180) + 64;
				z = random.nextInt(16) + 8;

				this.genAechorSprout.generate(world, random, pos.add(x, y, z));
			}

			// Kirrid Grass Generator
			if (random.nextInt(20) == 0)
			{
				for (count = 0; count < 6; count++)
				{
					x = random.nextInt(16) + 8;
					y = random.nextInt(180) + 64;
					z = random.nextInt(16) + 8;

					this.genKirridGrass.generate(world, random, pos.add(x, y, z));
				}
			}

			// Brettl Plant Generator
			if (random.nextInt(5) == 0)
			{
				x = random.nextInt(16) + 8;
				y = random.nextInt(180) + 64;
				z = random.nextInt(16) + 8;

				this.genBrettlPlant.generate(world, random, pos.add(x, y, z));
			}

			// Kirrid Grass Generator
			if (random.nextInt(20) == 0)
			{
				for (count = 0; count < 6; count++)
				{
					x = random.nextInt(16) + 8;
					y = random.nextInt(180) + 64;
					z = random.nextInt(16) + 8;

					this.genKirridGrass.generate(world, random, pos.add(x, y, z));
				}
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
		{
			// Plumproot Generator
			if (random.nextInt(10) == 0)
			{
				for (count = 0; count < 6; count++)
				{
					x = random.nextInt(16) + 8;
					y = random.nextInt(180) + 64;
					z = random.nextInt(16) + 8;

					this.genPlumproots.generate(world, random, pos.add(x, y, z));
				}
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			this.generateClouds(world, random, new BlockPos(pos.getX(), 0, pos.getZ()));
		}

		// Post decorate
		if (genBase instanceof BiomeAetherBase)
		{
			final BiomeAetherBase aetherBiome = (BiomeAetherBase) genBase;

			aetherBiome.postDecorate(world, random, pos);
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, random, pos));
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
		if (TerrainGen.generateOre(world, random, this.genAmbrosium, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genAmbrosium, world, random, pos, 0, 256, 20);
		}

		if (TerrainGen.generateOre(world, random, this.genZanite, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genZanite, world, random, pos, 0, 256, 18);
		}

		if (TerrainGen.generateOre(world, random, this.genGravitite, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genGravitite, world, random, pos, 0, 50, 10);
		}

		if (TerrainGen.generateOre(world, random, this.genIcestone, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genIcestone, world, random, pos, 0, 256, 20);
		}

		if (TerrainGen.generateOre(world, random, this.genArkenium, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genArkenium, world, random, pos, 0, 70, 15);
		}

		if (TerrainGen.generateOre(world, random, this.genCoarseAetherDirtOnDirt, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genCoarseAetherDirtOnDirt, world, random, pos, 0, 128, 10);
		}

		if (TerrainGen.generateOre(world, random, this.genCoarseAetherDirtOnHolystone, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genCoarseAetherDirtOnHolystone, world, random, pos, 0, 100, 10);
		}
		//this.generateMineable(this.genQuicksoilOnGrass, world, random, pos, 0, 128, 10);

		if (TerrainGen.generateOre(world, random, this.genMossyHolystone, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genMossyHolystone, world, random, pos, 0, 100, 25);
		}

		if (TerrainGen.generateOre(world, random, this.genCrudeScatterglass, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
		{
			this.generateMineable(this.genCrudeScatterglass, world, random, pos, 0, 110, 25);
		}
	}

	protected void generateClouds(final World world, final Random random, final BlockPos pos)
	{
		this.generateCloud(this.genBlueAercloud, world, pos, random, 15, 16, 90, 130);
		//		this.generateCloud(this.genColdFlatAercloud, world, pos, random, 10, 16, 32);
		this.generateCloud(this.genColdColumbusAercloud, world, pos, random, 30, 16, 90, 130);

		this.generateCloud(this.genPurpleAercloud, world, pos, random, 50, 4, 90, 130);
	}
}
