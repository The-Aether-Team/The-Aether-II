package com.gildedgames.aether.common.world.dimensions.aether.island;

import java.util.List;
import java.util.Random;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.WorldGeneratorIsland;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ChunkGeneratorIsland implements IChunkGenerator
{

	private final World worldObj;

	private final Random rand;

	private final double[][] noiseFields;

	private double[] cloudNoise1, cloudNoise2, cloudNoise3, cloudNoise4;

	private final NoiseGeneratorOctaves[] octaveNoiseGenerators;

	private Biome[] biomes;

	private final WorldGeneratorIsland islandGenerator;

	private final WorldGenAetherCaves caveGenerator = new WorldGenAetherCaves();

	private final NoiseGeneratorOctaves cloudGenLayer1;

	private final NoiseGeneratorOctaves cloudGenLayer2;

	private final NoiseGeneratorOctaves cloudGenLayer3;

	private final NoiseGeneratorOctaves cloudGenLayer4;

	public ChunkGeneratorIsland(final World world, final long seed)
	{
		this.worldObj = world;

		if (!this.worldObj.isRemote)
		{
			this.worldObj.setSeaLevel(255);
		}

		this.rand = new Random(seed);

		this.noiseFields = new double[9][];
		this.noiseFields[1] = new double[256];
		this.noiseFields[2] = new double[256];
		this.noiseFields[3] = new double[256];

		this.islandGenerator = new WorldGeneratorIsland(world);

		this.octaveNoiseGenerators = new NoiseGeneratorOctaves[7];

		this.octaveNoiseGenerators[0] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[1] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[2] = new NoiseGeneratorOctaves(this.rand, 32);
		this.octaveNoiseGenerators[3] = new NoiseGeneratorOctaves(this.rand, 64);
		this.octaveNoiseGenerators[4] = new NoiseGeneratorOctaves(this.rand, 4);
		this.octaveNoiseGenerators[5] = new NoiseGeneratorOctaves(this.rand, 10);
		this.octaveNoiseGenerators[6] = new NoiseGeneratorOctaves(this.rand, 16);

		this.cloudGenLayer1 = new NoiseGeneratorOctaves(this.rand, 12);
		this.cloudGenLayer2 = new NoiseGeneratorOctaves(new Random(seed + 100L), 12);
		this.cloudGenLayer3 = new NoiseGeneratorOctaves(new Random(seed + 200L), 12);
		this.cloudGenLayer4 = new NoiseGeneratorOctaves(new Random(seed + 300L), 12);
	}

	@Override
	public BlockPos getStrongholdGen(final World world, final String structureName, final BlockPos pos)
	{
		return null;
	}

	public void replaceBiomeBlocks(final ChunkPrimer primer, final int chunkX, final int chunkY, final Biome[] biomes)
	{
		final double oneThirtySnd = 0.03125D;

		this.noiseFields[1] = this.octaveNoiseGenerators[3].generateNoiseOctaves(this.noiseFields[1],
				chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd, oneThirtySnd, 1.0D);
		this.noiseFields[2] = this.octaveNoiseGenerators[3].generateNoiseOctaves(this.noiseFields[2],
				chunkX * 16, 109, chunkY * 16, 16, 1, 16, oneThirtySnd, 1.0D, oneThirtySnd);
		this.noiseFields[3] = this.octaveNoiseGenerators[4].generateNoiseOctaves(this.noiseFields[3],
				chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd * 2D, oneThirtySnd * 2D, oneThirtySnd * 2D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final Biome biome = biomes[z + x * 16];

				final int sthWithHeightMap = (int) (this.noiseFields[3][x + z * 16] / 3D + 3D + this.rand.nextDouble() / 4);

				int j1 = -1;

				IBlockState topAetherBlock = biome.topBlock;
				IBlockState fillAetherBlock = biome.fillerBlock;
				final IBlockState stone = BlocksAether.holystone.getDefaultState();

				for (int y = 220; y >= 0; y--)
				{
					final Block block = primer.getBlockState(x, y, z).getBlock();

					if (block == Blocks.AIR)
					{
						j1 = -1;
						continue;
					}

					if (block != stone.getBlock())
					{
						continue;
					}

					if (j1 == -1)
					{
						if (sthWithHeightMap <= 0)
						{
							topAetherBlock = Blocks.AIR.getDefaultState();
							fillAetherBlock = stone;
						}

						j1 = sthWithHeightMap;

						if (y >= 0 && (y + 1 >= this.worldObj.getActualHeight()
								|| primer.getBlockState(x, y + 1, z) == Blocks.AIR.getDefaultState()))
						{
							primer.setBlockState(x, y, z, topAetherBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, fillAetherBlock);
						}

						continue;
					}

					if (j1 <= 0)
					{
						continue;
					}

					primer.setBlockState(x, y, z, fillAetherBlock);

					j1--;
				}
			}
		}
	}

	public void generateIslands(final ChunkPrimer primer, final int chunkX, final int chunkZ)
	{
		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final int minX = chunkX * 16;
		final int minY = 0;
		final int minZ = chunkZ * 16;

		final int maxX = minX + 15;
		final int maxY = this.worldObj.getActualHeight();
		final int maxZ = minZ + 15;

		//StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

		long sectorSeed = 0;

		if (!IslandSectorAccess.inst().wasSectorEverCreatedInChunk(this.worldObj, chunkX, chunkZ))
		{
			this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

			sectorSeed = this.rand.nextLong();
		}

		final IslandSector sector = IslandSectorAccess.inst().attemptToLoadSectorInChunk(this.worldObj, chunkX, chunkZ, sectorSeed);

		if (sector == null)
		{
			return;
		}

		final List<IslandData> islandsToGenerate = Lists.newArrayList();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final List<IslandData> islands = sector.getIslandDataAtBlockPos(posX + x, posZ + z);

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

				/*int islandWidth = (int)island.getBounds().getMinX();
				int islandHeight = (int)island.getBounds().getMinY();
				
				int stepX = posX - islandWidth + x;
				int stepZ = posZ - islandHeight + z;
				
				if (stepX < 0 || stepZ < 0 || stepX > island.getBounds().getWidth() - 1 || stepZ > island.getBounds().getHeight() - 1)
				{
					continue;
				}
				
				if (!islandsToGenerate.contains(island))
				{
				
				}*/
			}
		}

		for (final IslandData island : islandsToGenerate)
		{
			this.islandGenerator.genIslandForChunk(primer, island, sector, chunkX, chunkZ);
		}
	}

	public void genClouds(final ChunkPrimer primer, double[] noise, final NoiseGeneratorOctaves noiseGen, final double threshold, final int posY, final int chunkX,
			final int chunkZ)
	{
		final int height = 160;
		final int sampleSize = 40;

		noise = noiseGen.generateNoiseOctaves(noise, chunkX * 16, 0, chunkZ * 16, 16, height, 16, 64.0D, 1.5D, 64.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				for (int y = 0; y < height; y += sampleSize)
				{
					double samples = 0.0D;

					for (int y2 = y; y2 < y + sampleSize; y2++)
					{
						samples += noise[(x * 16 + z) * height + y];
					}

					final double sample = samples / sampleSize;

					if (sample / 5.0D > threshold)
					{
						if (primer.getBlockState(x, y, z) == Blocks.AIR.getDefaultState())
						{
							primer.setBlockState(x, posY + y / sampleSize, z, BlocksAether.aercloud.getDefaultState());
						}
					}
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(final int chunkX, final int chunkZ)
	{
		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

		final ChunkPrimer primer = new ChunkPrimer();

		this.generateIslands(primer, chunkX, chunkZ);

		this.biomes = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomes, chunkX * 16, chunkZ * 16, 16, 16);

		this.replaceBiomeBlocks(primer, chunkX, chunkZ, this.biomes);

		this.caveGenerator.generate(this.worldObj, chunkX, chunkZ, primer);

//		this.genClouds(primer, this.cloudNoise1, this.cloudGenLayer1, 10.0D, 40, chunkX, chunkZ);
		//this.genClouds(primer, this.cloudNoise2, this.cloudGenLayer2, 100.0D, 65, chunkX, chunkZ);

		//this.genClouds(primer, 90.0D, 240, chunkX, chunkZ);
		//this.genClouds(primer, this.cloudNoise3, this.cloudGenLayer3, 130.0D, 180, chunkX, chunkZ);
		//this.genClouds(primer, this.cloudNoise4, this.cloudGenLayer4, 200.0D, 148, chunkX, chunkZ);

		/*for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z< 16; z++)
			{
				for (int y = 0; y < 80; y++)
				{
					primer.setBlockState(x, y, z, Blocks.DIRT.getDefaultState());
				}
			}
		}*/

		final Chunk chunk = new Chunk(this.worldObj, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, this.worldObj, this.rand, chunkX, chunkZ, false));

		final int x = chunkX * 16;
		final int z = chunkZ * 16;

		final BlockPos pos = new BlockPos(x, 0, z);

		final Biome biome = this.worldObj.getBiome(pos.add(16, 0, 16));

		this.rand.setSeed(this.worldObj.getSeed());

		final long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		final long j1 = this.rand.nextLong() / 2L * 2L + 1L;

		this.rand.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());

		biome.decorate(this.worldObj, this.rand, pos);
	}

	@Override
	public boolean generateStructures(final Chunk chunkIn, final int x, final int z)
	{
		return false;
	}

	@Override
	public void recreateStructures(final Chunk chunk, final int chunkX, final int chunkZ)
	{

	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final BlockPos pos)
	{
		final Biome biomegenbase = this.worldObj.getBiome(pos);

		if (biomegenbase == null)
		{
			return null;
		}
		else
		{
			return biomegenbase.getSpawnableList(creatureType);
		}
	}
}
