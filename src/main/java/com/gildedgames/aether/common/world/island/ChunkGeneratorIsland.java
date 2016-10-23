package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.island.logic.*;
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
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorIsland implements IChunkGenerator
{

	private final World worldObj;

	private final Random rand;

	private double[][] noiseFields;

	private double[] cloudNoise1, cloudNoise2, cloudNoise3, cloudNoise4;

	private NoiseGeneratorOctaves[] octaveNoiseGenerators;

	private Biome[] biomes;

	private WorldGeneratorIsland islandGenerator;

	private WorldGenAetherCaves caveGenerator = new WorldGenAetherCaves();

	private NoiseGeneratorOctaves cloudGenLayer1, cloudGenLayer2, cloudGenLayer3, cloudGenLayer4;

	public ChunkGeneratorIsland(World world, long seed)
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
	public BlockPos getStrongholdGen(World world, String structureName, BlockPos pos)
	{
		return null;
	}

	public void replaceBiomeBlocks(ChunkPrimer primer, int chunkX, int chunkY, Biome[] biomes)
	{
		double oneThirtySnd = 0.03125D;

		this.noiseFields[1] = this.octaveNoiseGenerators[3].generateNoiseOctaves(this.noiseFields[1], chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd, oneThirtySnd, 1.0D);
		this.noiseFields[2] = this.octaveNoiseGenerators[3].generateNoiseOctaves(this.noiseFields[2], chunkX * 16, 109, chunkY * 16, 16, 1, 16, oneThirtySnd, 1.0D, oneThirtySnd);
		this.noiseFields[3] = this.octaveNoiseGenerators[4].generateNoiseOctaves(this.noiseFields[3], chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd * 2D, oneThirtySnd * 2D, oneThirtySnd * 2D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				Biome biome = biomes[z + x * 16];

				int sthWithHeightMap = (int) (this.noiseFields[3][x + z * 16] / 3D + 3D + this.rand.nextDouble() / 4);

				int j1 = -1;

				IBlockState topAetherBlock = biome.topBlock;
				IBlockState fillAetherBlock = biome.fillerBlock;
				IBlockState stone = BlocksAether.holystone.getDefaultState();

				for (int y = 220; y >= 0; y--)
				{
					Block block = primer.getBlockState(x, y, z).getBlock();

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

						if (y >= 0 && (y + 1 >= worldObj.getActualHeight() || primer.getBlockState(x, y + 1, z) == Blocks.AIR.getDefaultState()))
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

	public void generateIslands(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		int minX = chunkX * 16;
		int minY = 0;
		int minZ = chunkZ * 16;

		int maxX = minX + 15;
		int maxY = this.worldObj.getActualHeight();
		int maxZ = minZ + 15;

		//StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

		int sectorX = IslandSectorAccess.inst().getSectorCoord(chunkX);
		int sectorY = IslandSectorAccess.inst().getSectorCoord(chunkZ);

		long sectorSeed = 0;

		if (!IslandSectorAccess.inst().wasSectorEverCreated(this.worldObj, sectorX, sectorY))
		{
			sectorSeed = this.rand.nextLong();
		}

		IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(this.worldObj, sectorX, sectorY, sectorSeed);

		if (sector == null)
		{
			return;
		}

		final List<IslandData> islandsToGenerate = Lists.newArrayList();

		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				IslandData island = sector.getIslandDataAtBlockPos(posX + x, posZ + z);

				if (island == null || islandsToGenerate.contains(island))
				{
					continue;
				}

				islandsToGenerate.add(island);

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

		for (IslandData island : islandsToGenerate)
		{
			this.islandGenerator.genIslandForChunk(primer, island, sector, chunkX, chunkZ);
		}
	}

	public void genClouds(ChunkPrimer primer, double[] noise, NoiseGeneratorOctaves noiseGen, double threshold, int posY, int chunkX, int chunkZ)
	{
		int height = 160;
		int sampleSize = 40;

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

					double sample = samples / sampleSize;

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
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

		ChunkPrimer primer = new ChunkPrimer();

		this.generateIslands(primer, chunkX, chunkZ);

		this.biomes = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomes, chunkX * 16, chunkZ * 16, 16, 16);

		this.replaceBiomeBlocks(primer, chunkX, chunkZ, this.biomes);

		this.caveGenerator.generate(this.worldObj, chunkX, chunkZ, primer);

		this.genClouds(primer, this.cloudNoise1, this.cloudGenLayer1, 10.0D, 40, chunkX, chunkZ);
		this.genClouds(primer, this.cloudNoise2, this.cloudGenLayer2, 100.0D, 65, chunkX, chunkZ);

		//this.genClouds(primer, 90.0D, 240, chunkX, chunkZ);
		this.genClouds(primer, this.cloudNoise3, this.cloudGenLayer3, 130.0D, 180, chunkX, chunkZ);
		this.genClouds(primer, this.cloudNoise4, this.cloudGenLayer4, 200.0D, 148, chunkX, chunkZ);

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

		Chunk chunk = new Chunk(this.worldObj, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public void populate(int chunkX, int chunkZ)
	{
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, this.worldObj, this.rand, chunkX, chunkZ, false));

		int x = chunkX * 16;
		int z = chunkZ * 16;

		BlockPos pos = new BlockPos(x, 0, z);

		Biome biome = this.worldObj.getBiome(pos.add(16, 0, 16));

		this.rand.setSeed(this.worldObj.getSeed());

		long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		long j1 = this.rand.nextLong() / 2L * 2L + 1L;

		this.rand.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());

		biome.decorate(this.worldObj, this.rand, pos);
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	@Override
	public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
	{

	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		Biome biomegenbase = this.worldObj.getBiome(pos);

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
