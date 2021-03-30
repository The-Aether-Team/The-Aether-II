package com.gildedgames.aether.common.world.biomes;

import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecorationUtil;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.templates.TemplateLoc;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockValkyrieGrass;
import com.gildedgames.aether.common.init.GenerationAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.decorations.WorldGenAetherMinable;
import com.gildedgames.aether.common.world.decorations.WorldGenIrradiatedPool;
import com.gildedgames.aether.common.world.decorations.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.decorations.aerclouds.WorldGenPurpleAercloud;
import com.gildedgames.aether.common.world.decorations.plants.WorldGenBrettlPlant;
import com.gildedgames.aether.common.world.decorations.plants.WorldGenFoliage;
import com.gildedgames.aether.common.world.decorations.trees.WorldGenOrangeTree;
import com.gildedgames.aether.common.world.island.BlockAccessIsland;
import com.gildedgames.aether.common.world.templates.TemplatePlacer;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.core.BlueprintDefinitionPool;
import com.gildedgames.orbis.lib.core.CreationData;
import com.gildedgames.orbis.lib.core.PlacedBlueprint;
import com.gildedgames.orbis.lib.core.baking.BakedBlueprint;
import com.gildedgames.orbis.lib.core.util.BlueprintPlacer;
import com.gildedgames.orbis.lib.data.schedules.ScheduleRegion;
import com.gildedgames.orbis.lib.processing.DataPrimer;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeAetherDecorator
{

	private final WorldGenAetherMinable genAmbrosium, genZanite, genGravitite, genIcestone, genArkenium, genCoarseAetherDirtOnDirt, genCoarseAetherDirtOnHolystone;

    private final WorldGenAetherMinable genCrudeScatterglass, genIrradiatedHolystone, genIrradiatedDust, genMossyHolystone, genHolystoneQuartzOre;

	private final WorldGenFoliage genPurpleFlowers, genWhiteRoses, genBurstblossom, genAechorSprout;

	private final WorldGenOrangeTree genOrangeTree;

	private final WorldGenFoliage genBlueberryBushes, genValkyrieGrass, genPlumproots;

	private final WorldGenAercloud genColdColumbusAercloud, genBlueAercloud, genGreenAercloud, genGoldAercloud, genStormAercloud;

	private final WorldGenPurpleAercloud genPurpleAercloud;

	private final WorldGenBrettlPlant genBrettlPlant;

	private final WorldGenIrradiatedPool genIrradiatedPool;

	public final boolean generateBushes = true;

	public BiomeAetherDecorator()
	{
        final IBlockState[] dirtStates = new IBlockState[] { BlocksAether.aether_dirt.getDefaultState() };
        final IBlockState[] holystoneStates = new IBlockState[] { BlocksAether.holystone.getDefaultState() };

		this.genAmbrosium = new WorldGenAetherMinable(BlocksAether.ambrosium_ore.getDefaultState(), 16, holystoneStates);

		this.genZanite = new WorldGenAetherMinable(BlocksAether.zanite_ore.getDefaultState(), 8, holystoneStates);
		this.genHolystoneQuartzOre = new WorldGenAetherMinable(BlocksAether.holystone_quartz_ore.getDefaultState(), 8, holystoneStates);
		this.genGravitite = new WorldGenAetherMinable(BlocksAether.gravitite_ore.getDefaultState(), 5, holystoneStates);
		this.genIcestone = new WorldGenAetherMinable(BlocksAether.icestone_ore.getDefaultState(), 12, holystoneStates);
		this.genArkenium = new WorldGenAetherMinable(BlocksAether.arkenium_ore.getDefaultState(), 6, holystoneStates);

		this.genAmbrosium.setEmitsLight(true);
		this.genGravitite.setFloating(true);

		this.genIrradiatedHolystone = new WorldGenAetherMinable(
				BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.IRRADIATED_HOLYSTONE), 22, holystoneStates);

		this.genMossyHolystone = new WorldGenAetherMinable(
				BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE), 32, holystoneStates);

		this.genIrradiatedDust = new WorldGenAetherMinable(
				BlocksAether.irradiated_dust_block.getDefaultState(), 4, holystoneStates);

		this.genCoarseAetherDirtOnDirt = new WorldGenAetherMinable(
                BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, dirtStates);
        this.genCoarseAetherDirtOnHolystone = new WorldGenAetherMinable(
                BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.PROPERTY_VARIANT, BlockAetherDirt.COARSE_DIRT), 22, holystoneStates);

        this.genCrudeScatterglass = new WorldGenAetherMinable(BlocksAether.crude_scatterglass.getDefaultState(), 10, holystoneStates);

		this.genPurpleFlowers = new WorldGenFoliage(64,
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER));
		this.genWhiteRoses = new WorldGenFoliage(64,
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE));
		this.genBurstblossom = new WorldGenFoliage(64,
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.BURSTBLOSSOM));
		this.genAechorSprout = new WorldGenFoliage(4,
				BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.AECHOR_SPROUT));

		this.genOrangeTree = new WorldGenOrangeTree();

		this.genBlueberryBushes = new WorldGenFoliage(32,
				BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE, true));

		this.genValkyrieGrass = new WorldGenFoliage(64,
				BlocksAether.valkyrie_grass.getDefaultState().withProperty(BlockValkyrieGrass.PROPERTY_HARVESTABLE, true)
						.withProperty(BlockValkyrieGrass.PROPERTY_VARIANT, BlockValkyrieGrass.FULL));

		this.genPlumproots = new WorldGenFoliage(64, BlocksAether.plumproot.getDefaultState());

		this.genBrettlPlant = new WorldGenBrettlPlant();

		this.genColdColumbusAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.COLD_AERCLOUD), 16, false);
		this.genBlueAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.BLUE_AERCLOUD), 8, false);
		this.genGreenAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.GREEN_AERCLOUD), 8, false);
		this.genGoldAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.GOLDEN_AERCLOUD), 8, false);
		this.genStormAercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.STORM_AERCLOUD), 8, false);

		this.genPurpleAercloud = new WorldGenPurpleAercloud(BlockAercloud.getAercloudState(BlockAercloud.PURPLE_AERCLOUD), 4, false);

		this.genIrradiatedPool = new WorldGenIrradiatedPool();
	}

	public void prepareDecorationsWholeIsland(final World world, BlockAccessIsland access, final IIslandData island, final Random random)
	{
		final DataPrimer primer = new DataPrimer(access);

		final int startX = island.getBounds().getMinX();
		final int startZ = island.getBounds().getMinZ();

		boolean generated = false;

		final BlueprintDefinition outpostDef = GenerationAether.OUTPOST.getRandomDefinition(random);

		final Rotation rotation = (outpostDef.hasRandomRotation()) ?
				BlueprintPlacer.ROTATIONS[world.rand.nextInt(BlueprintPlacer.ROTATIONS.length)] :
				BlueprintPlacer.ROTATIONS[0];

		BakedBlueprint outpostBake = new BakedBlueprint(outpostDef, new CreationData(world).seed(random.nextLong()).rotation(rotation));

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

					generated = primer.canGenerate(outpostBake, pos);

					if (generated)
					{
						final PlacedBlueprint placed = island.placeBlueprint(outpostBake, pos);

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

		BakedBlueprint baked = new BakedBlueprint(def, new CreationData(world).rotation(rotation));

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

					final boolean generated = primer.canGenerate(baked, pos);

					if (generated)
					{
						island.placeBlueprint(baked, pos);

						rotation = (def.hasRandomRotation()) ?
								BlueprintPlacer.ROTATIONS[rand.nextInt(BlueprintPlacer.ROTATIONS.length)] :
								BlueprintPlacer.ROTATIONS[0];

						baked = new BakedBlueprint(def, new CreationData(world).rotation(rotation));

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
		ChunkPos chunkPos = new ChunkPos(pos);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, random, chunkPos));

		WorldSlice slice = new WorldSlice(world, chunkPos);

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			this.generateOres(slice, random, pos);
		}

		IIslandData island = IslandHelper.get(world, chunkPos.x, chunkPos.z);

		if (island == null)
		{
			return;
		}

		WorldDecorationUtil.generateDecorations(island.getBasicDecorations(), world, random, pos);

		final WorldProviderAether provider = WorldProviderAether.get(world);

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
		{
			WorldDecorationUtil.generateDecorationsWithNoise(island.getTreeDecorations(), world, random, pos, provider.getNoise(),
					island.getOpenAreaDecorationGenChance(), island.getForestTreeCountModifier());
		}

		BlockPos.PooledMutableBlockPos randomPos = BlockPos.PooledMutableBlockPos.retain();

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			// Moa Nests
			if (random.nextInt(4) == 0)
			{
				int x = random.nextInt(16) + 8;
				int z = random.nextInt(16) + 8;

				final BlockPos pos2 = world.getTopSolidOrLiquidBlock(pos.add(x, 0, z)).add(0, -1, 0);

				TemplatePlacer.place(world, GenerationAether.skyroot_moa_nest.getRandomDefinition(random), new TemplateLoc().set(pos2), random);
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
		{
			// Orange Tree Generator
			for (int count = 0; count < 3; count++)
			{
				int x = random.nextInt(16) + 8;
				int y = random.nextInt(180) + 64;
				int z = random.nextInt(16) + 8;

				this.genOrangeTree.generate(world, random, translate(randomPos, pos, x, y, z));
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM))
		{
			if (this.generateBushes)
			{
				this.generateFoliage(this.genBlueberryBushes, slice, random, pos, 15);
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS))
		{
			this.generateFoliage(this.genPurpleFlowers, slice, random, pos, 5);
			this.generateFoliage(this.genWhiteRoses, slice, random, pos,5);
			this.generateFoliage(this.genBurstblossom, slice, random, pos,5);
			this.generateFoliage(this.genAechorSprout, slice, random, pos,5);

			this.generateFoliage(this.genValkyrieGrass, slice, random, pos,5);
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
            this.generateFoliage(this.genAechorSprout, slice, random, pos,5);

			// Valkyrie Grass Generator
            this.generateFoliage(this.genValkyrieGrass, slice, random, pos,5);


			// Brettl Plant Generator
			if (random.nextInt(5) == 0)
			{
				int x = random.nextInt(16) + 8;
				int y = random.nextInt(180) + 64;
				int z = random.nextInt(16) + 8;

				this.genBrettlPlant.generate(world, random, translate(randomPos, pos, x, y, z));
			}
		}

		if (TerrainGen.decorate(world, random, chunkPos, DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
		{
			this.generateFoliage(this.genPlumproots, slice, random, pos, 50);
		}

		randomPos.release();

		this.generateClouds(slice, random, new BlockPos(pos.getX(), 0, pos.getZ()));

		// Post decorate
		if (genBase instanceof BiomeAetherBase)
		{
			final BiomeAetherBase aetherBiome = (BiomeAetherBase) genBase;

			aetherBiome.postDecorate(world, random, pos);
		}

		if (world.getBiome(pos) == BiomesAether.IRRADIATED_FORESTS)
		{
			if (random.nextInt(10) == 0)
			{
				this.genIrradiatedPool.generate(slice, random, pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, random, chunkPos));
	}

	/**
	 * @param gen The generation feature
	 * @param slice A slice of the world centered at {@param pos}
	 * @param random The random number generator for the generation pass
	 * @param pos The position around which this feature should generate
	 * @param chance The 1 in N chance to generate
	 */
	private void generateFoliage(final WorldGenFoliage gen, final WorldSlice slice, final Random random, final BlockPos pos, final int chance)
	{
		if (random.nextInt(chance) == 0)
		{
			int x = pos.getX() + random.nextInt(16) + 8;
			int z = pos.getZ() + random.nextInt(16) + 8;

			gen.generate(slice, random, x, z);
		}
	}

	private void generateMinable(final WorldGenAetherMinable minable, final WorldSlice slice, final Random random, final BlockPos pos,
			final int maxY, final int attempts)
	{
		double attemptsPerLevel = (double) attempts / (double) maxY;

		int topY = Math.max(slice.getWorld().getTopSolidOrLiquidBlock(pos).getY() + 8, 255);

		if (topY <= 0)
		{
			return;
		}

		int adjustedMaxY = Math.min(topY, maxY);

		if (adjustedMaxY < 0)
		{
			return;
		}

		int bottomY = Math.min(getBottomSolidOrLiquidBlock(slice.getWorld(), pos).getY() - 8, 0);

		if (adjustedMaxY <= bottomY)
		{
			return;
		}

		double adjustedAttempts = (double) (adjustedMaxY - bottomY) * attemptsPerLevel;

		BlockPos.PooledMutableBlockPos randomPos = BlockPos.PooledMutableBlockPos.retain();

		for (int count = 0; count < adjustedAttempts; count++)
		{
			translate(randomPos, pos, random.nextInt(16), random.nextInt(adjustedMaxY - bottomY) + bottomY, random.nextInt(16));

			minable.generate(slice, random, randomPos);
		}

		randomPos.release();
	}

	private void generateCloud(final WorldGenAercloud gen, final WorldSlice slice, final BlockPos pos, final Random rand, final int rarity, final int width,
			final int minY, final int maxY)
	{
		BlockPos.PooledMutableBlockPos randomPos = BlockPos.PooledMutableBlockPos.retain();

		if (rand.nextInt(rarity) == 0)
		{
			translate(randomPos, pos, rand.nextInt(width), minY + rand.nextInt(maxY - minY), rand.nextInt(width));

			gen.generate(slice, rand, randomPos);
		}

		randomPos.release();

	}

	protected int nextInt(final Random random, final int i)
	{
		if (i <= 1)
		{
			return 0;
		}

		return random.nextInt(i);
	}

	private static BlockPos translate(BlockPos.PooledMutableBlockPos pos, BlockPos origin, int x, int y, int z)
	{
		return pos.setPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
	}

	protected void generateOres(final WorldSlice slice, final Random random, final BlockPos pos)
	{
		this.generateMinable(this.genAmbrosium, slice, random, pos, 256, 10);
		this.generateMinable(this.genZanite, slice, random, pos, 256, 10);
		this.generateMinable(this.genGravitite, slice, random, pos, 50, 5);
		this.generateMinable(this.genIcestone, slice, random, pos, 256, 10);
		this.generateMinable(this.genHolystoneQuartzOre, slice, random, pos, 256, 10);
		this.generateMinable(this.genArkenium, slice, random, pos, 70, 7);
		this.generateMinable(this.genCrudeScatterglass, slice, random, pos, 110, 10);
		this.generateMinable(this.genMossyHolystone, slice, random, pos, 132, 16);
		this.generateMinable(this.genIrradiatedHolystone, slice, random, pos, 70, 12);
		this.generateMinable(this.genIrradiatedDust, slice, random, pos, 60, 5);



	}

	protected void generateClouds(final WorldSlice world, final Random random, final BlockPos pos)
	{
		//TOP
		this.generateCloud(this.genStormAercloud, world, pos, random, 30, 16, 238, 256);
		this.generateCloud(this.genPurpleAercloud, world, pos, random, 50, 4, 226, 246);
		this.generateCloud(this.genBlueAercloud, world, pos, random, 50, 16, 218, 236);
		this.generateCloud(this.genColdColumbusAercloud, world, pos, random, 15, 16, 190, 230);
		this.generateCloud(this.genGreenAercloud, world, pos, random, 50, 16, 208, 226);
		this.generateCloud(this.genGoldAercloud, world, pos, random, 50, 16, 185, 205);

		//MIDDLE
		this.generateCloud(this.genColdColumbusAercloud, world, pos, random, 15, 16, 110, 150);
		this.generateCloud(this.genGreenAercloud, world, pos, random, 50, 16, 100, 110);
		this.generateCloud(this.genPurpleAercloud, world, pos, random, 50, 4, 90, 100);
		this.generateCloud(this.genBlueAercloud, world, pos, random, 50, 16, 85, 95);

		//BOTTOM
		this.generateCloud(this.genStormAercloud, world, pos, random, 15, 16, 40, 55);
		this.generateCloud(this.genGreenAercloud, world, pos, random, 30, 16, 25, 35);
		this.generateCloud(this.genGoldAercloud, world, pos, random, 30, 16, 12, 24);
		this.generateCloud(this.genBlueAercloud, world, pos, random, 30, 16, 5, 15);
	}

	private static BlockPos getBottomSolidOrLiquidBlock(World world, BlockPos pos)
	{
		Chunk chunk = world.getChunk(pos);

		int lowestSection = -1;

		for (int i = 0; i < chunk.getBlockStorageArray().length; i++)
		{
			if (chunk.getBlockStorageArray()[i] != Chunk.NULL_BLOCK_STORAGE)
			{
				lowestSection = i * 16;

				break;
			}
		}

		if (lowestSection == -1)
		{
			return new BlockPos(pos.getX(), 0, pos.getZ());
		}

		BlockPos blockpos;
		BlockPos blockpos1;

		for (blockpos = new BlockPos(pos.getX(), lowestSection, pos.getZ()); blockpos.getY() <= 255; blockpos = blockpos1)
		{
			blockpos1 = blockpos.up();
			IBlockState state = chunk.getBlockState(blockpos1);

			if (state.getMaterial().blocksMovement() && !state.getBlock().isLeaves(state, world, blockpos1) && !state.getBlock().isFoliage(world, blockpos1))
			{
				break;
			}
		}

		return blockpos;
	}
}
