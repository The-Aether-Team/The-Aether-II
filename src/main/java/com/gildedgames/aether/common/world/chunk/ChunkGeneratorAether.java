package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.OpenSimplexNoise;
import com.gildedgames.aether.common.world.chunk.gen.*;
import com.gildedgames.aether.common.world.features.WorldGenAetherCaves;
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
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import java.util.List;
import java.util.Random;

public class ChunkGeneratorAether implements IChunkGenerator
{

	private final IBlockState air, aether_stone, cold_aercloud;

	private final World worldObj;

	private final Random rand;

	public final static int PLACEMENT_FLAG_TYPE = 2;

	private ChunkGen mainLand, mainLandOverhangs, smallLayer1, smallLayer2, smallLayer3;

	private NoiseGeneratorOctaves[] octaveNoiseGenerators;

	private NoiseGeneratorOctaves cloudNoiseGenerator;

	private WorldGenAetherCaves caveGenerator = new WorldGenAetherCaves();

	private double[] cloudNoise;

	private double[][] noiseFields;

	private Biome[] biomes;

	public ChunkGeneratorAether(World world, long seed)
	{
		this.air = Blocks.AIR.getDefaultState();
		this.aether_stone = BlocksAether.holystone.getDefaultState();
		this.cold_aercloud = BlocksAether.aercloud.getDefaultState();

		this.worldObj = world;
		this.rand = new Random(seed);

		this.noiseFields = new double[9][];
		this.noiseFields[1] = new double[256];
		this.noiseFields[2] = new double[256];
		this.noiseFields[3] = new double[256];

		this.octaveNoiseGenerators = new NoiseGeneratorOctaves[7];
		this.octaveNoiseGenerators[0] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[1] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[2] = new NoiseGeneratorOctaves(this.rand, 32);
		this.octaveNoiseGenerators[3] = new NoiseGeneratorOctaves(this.rand, 64);
		this.octaveNoiseGenerators[4] = new NoiseGeneratorOctaves(this.rand, 4);
		this.octaveNoiseGenerators[5] = new NoiseGeneratorOctaves(this.rand, 10);
		this.octaveNoiseGenerators[6] = new NoiseGeneratorOctaves(this.rand, 16);

		this.cloudNoiseGenerator = new NoiseGeneratorOctaves(this.rand, 12);

		this.mainLand = new ChunkGenContinents(world, seed, new GenPropsMainLand(this.aether_stone, 250.0D, 100));
		//this.mainLandOverhangs = newsystem ChunkGenFloatingIsland(world, seed + 50L, newsystem GenPropsMainLand(aether_stone, 100.0D, 30));

		this.smallLayer1 = new ChunkGenFloatingIsland(world, seed + 100L, new GenPropsSmallIslands(this.aether_stone, 100.0D, 50));
		this.smallLayer2 = new ChunkGenFloatingIsland(world, seed + 200L, new GenPropsSmallIslands(this.aether_stone, 100.0D, 60));
		this.smallLayer3 = new ChunkGenFloatingIsland(world, seed + 300L, new GenPropsSmallIslands(this.aether_stone, 100.0D, 70));
	}

	@Override
	public BlockPos getStrongholdGen(World world, String structureName, BlockPos pos)
	{
		return null;
	}

	public final double bilinearInterpolate(double bottomLeftValue, double topLeftValue, double bottomRightValue, double topRightValue, double bottomLeftX, double topRightX, double bottomLeftY, double topRightY, double x, double y)
	{
		double x2x1, y2y1, x2x, y2y, yy1, xx1;
		x2x1 = topRightX - bottomLeftX;
		y2y1 = topRightY - bottomLeftY;
		x2x = topRightX - x;
		y2y = topRightY - y;
		yy1 = y - bottomLeftY;
		xx1 = x - bottomLeftX;

		return 1.0 / (x2x1 * y2y1) * (
				bottomLeftValue * x2x * y2y +
						bottomRightValue * xx1 * y2y +
						topLeftValue * x2x * yy1 +
						topRightValue * xx1 * yy1
		);
	}

	public final double lerp(double x, double y, double z)
	{
		return y + x * (z - y);
	}

	private double[] initializeNoiseField(double[] inputDoubles, int x, int y, int z, int width, int height, int length)
	{
		ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, inputDoubles, x, y, z, width, height, length);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
		{
			return event.getNoisefield();
		}

		if (inputDoubles == null)
		{
			inputDoubles = new double[width * height * length];
		}

		double const1 = 684.41200000000003D * 2.0D;
		double const2 = 684.41200000000003D;

		this.noiseFields[4] = this.octaveNoiseGenerators[2].generateNoiseOctaves(this.noiseFields[4], x, y, z, width, height, length, const1 / 80D, const2 / 160D, const1 / 80D);
		this.noiseFields[5] = this.octaveNoiseGenerators[0].generateNoiseOctaves(this.noiseFields[5], x, y, z, width, height, length, const1, const2, const1);
		this.noiseFields[6] = this.octaveNoiseGenerators[1].generateNoiseOctaves(this.noiseFields[6], x, y, z, width, height, length, const1, const2, const1);

		this.noiseFields[7] = this.octaveNoiseGenerators[5].generateNoiseOctaves(this.noiseFields[7], x, z, width, length, 1.121D, 1.121D, 0.5D);//Note: The last argument is never used
		this.noiseFields[8] = this.octaveNoiseGenerators[6].generateNoiseOctaves(this.noiseFields[8], x, z, width, length, 200D, 200D, 0.5D);

		int index = 0;

		for (int x1 = 0; x1 < width; x1++)
		{
			for (int z1 = 0; z1 < length; z1++)
			{
				for (int y1 = 0; y1 < height; y1++)
				{
					double finalHeight;

					double sample1 = this.noiseFields[5][index] / 512D;
					double sample2 = this.noiseFields[6][index] / 512D;
					double sample3 = (this.noiseFields[4][index] / 10D + 1.0D) / 2D;

					if (sample3 < 0.0D)
					{
						finalHeight = sample1;
					}
					else if (sample3 > 1.0D)
					{
						finalHeight = sample2;
					}
					else
					{
						finalHeight = sample1 + (sample2 - sample1) * sample3;
					}

					finalHeight -= 20D;

					if (y1 > height - 32)//If y1 > 1
					{
						double dy = (y1 - (height - 32)) / 31D;
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;//
					}

					if (y1 < 8)
					{
						double dy = (8 - y1) / 7D;
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;
					}

					inputDoubles[index] = finalHeight;
					index++;
				}
			}
		}

		return inputDoubles;
	}

	public void genClouds(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		int height = 160;
		int sampleSize = 40;

		this.cloudNoise = this.cloudNoiseGenerator.generateNoiseOctaves(this.cloudNoise, chunkX * 16, 0, chunkZ * 16, 16, height, 16, 64.0D, 1.5D, 64.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				for (int y = 0; y < height; y += sampleSize)
				{
					double samples = 0.0D;

					for (int y2 = y; y2 < y + sampleSize; y2++)
					{
						samples += this.cloudNoise[(x * 16 + z) * height + y];
					}

					double sample = samples / sampleSize;

					if (sample / 5.0D > 200.0D)
					{
						if (primer.getBlockState(x, y, z) == this.air)
						{
							primer.setBlockState(x, 8 + y / sampleSize, z, this.cold_aercloud);
						}
					}
				}
			}
		}
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
				IBlockState stone = this.aether_stone;

				for (int y = 256; y >= 0; y--)
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
							topAetherBlock = this.air;
							fillAetherBlock = stone;
						}

						j1 = sthWithHeightMap;

						if (y >= 0)
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

	public void initHeightMap(int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		this.mainLand.prepare(chunkX, chunkZ, posX, posZ);
		//this.mainLandOverhangs.prepare(chunkX, chunkZ, posX, posZ);

		this.smallLayer1.prepare(chunkX, chunkZ, posX, posZ);
		this.smallLayer2.prepare(chunkX, chunkZ, posX, posZ);
		this.smallLayer3.prepare(chunkX, chunkZ, posX, posZ);
	}

	public void setBlocksInChunk(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		final int dimXZ = 2;
		final int dimXZPlusOne = dimXZ + 1;

		final int dimY = 32;
		final int dimYPlusOne = dimY + 1;

		this.noiseFields[0] = this.initializeNoiseField(this.noiseFields[0], chunkX * dimXZ, 0, chunkZ * dimXZ, dimXZPlusOne, dimYPlusOne, dimXZPlusOne);

		for (int z = 0; z < 16; z++)
		{
			for (int x = 0; x < 16; x++)
			{
				for (int y = 0; y < 10; y++)
				{
					primer.setBlockState(x, y, z, BlocksAether.aerogel.getDefaultState());
				}
			}
		}

		this.mainLand.build(primer, chunkX, chunkZ, posX, posZ);
		//this.mainLandOverhangs.build(primer, chunkX, chunkZ, posX, posZ);

		this.smallLayer1.build(primer, chunkX, chunkZ, posX, posZ);
		this.smallLayer2.build(primer, chunkX, chunkZ, posX, posZ);
		this.smallLayer3.build(primer, chunkX, chunkZ, posX, posZ);
	}

	private OpenSimplexNoise simplex = new OpenSimplexNoise();

	public double octaveNoise(int octaves, double x, double z)
	{
		double value = 0.0;
		int i;

		for (i = 0; i < octaves; i++)
		{
			value += this.simplex.eval(x * Math.pow(2, i), z * Math.pow(2, i));
		}

		return value;
	}

	public double octaveNoise(int octaves, double x, double y, double z)
	{
		double value = 0.0;
		int i;

		for (i = 0; i < octaves; i++)
		{
			value += this.simplex.eval(x * Math.pow(2, i), y * Math.pow(2, i), z * Math.pow(2, i));
		}

		return value;
	}

	public void floating_rock(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		double posX = chunkX * 16;
		double posZ = chunkZ * 16;

		double width = 1256;
		double length = 1256;

		double centerX = width / 2.0D;
		double centerZ = length / 2.0D;

		for (double x = 0; x < 16; x++)
		{
			for (double z = 0; z < 16; z++)
			{
				double globalX = posX + x;
				double globalZ = posZ + z;

				final double FEATURE_SIZE = 2000D;

				double noiseValue = this.octaveNoise(6, globalX / FEATURE_SIZE, globalZ / FEATURE_SIZE);

				/*double distanceX = (centerX - globalX) * (centerX - globalX);
				double distanceZ = (centerZ - globalZ) * (centerZ - globalZ);

				double distanceToCenter = Math.sqrt(distanceX + distanceZ);

				distanceToCenter = distanceToCenter / width;

				noiseValue -= distanceToCenter;*/

				if (noiseValue > -0.5D)
				{
					double evalHeight = (noiseValue) * 40;

					for (double y = 50; y < 50 + evalHeight; y++)
					{
						final double FEATURE_SIZE_HIGH = 2000D;

						double highNoise = this.octaveNoise(7, globalX / FEATURE_SIZE_HIGH, y / FEATURE_SIZE_HIGH, globalZ / FEATURE_SIZE_HIGH);
						//double highNoise = this.octaveNoise(6, globalX / FEATURE_SIZE, y / FEATURE_SIZE, globalZ / FEATURE_SIZE);

						highNoise += noiseValue;

						highNoise = highNoise / 2.0D;

						if (highNoise > 0.0D)
						{
							primer.setBlockState((int) x, (int) y, (int) z, this.aether_stone);
						}
					}
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		//this.mainLand = newsystem ChunkGenContinents(this.worldObj, this.worldObj.getSeed(), newsystem GenPropsMainLand(aether_stone, 250.0D, 100));

		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

		ChunkPrimer primer = new ChunkPrimer();

		this.biomes = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomes, chunkX * 16, chunkZ * 16, 16, 16);

		//this.initHeightMap(chunkX, chunkZ);
		//this.setBlocksInChunk(primer, chunkX, chunkZ);

		this.floating_rock(primer, chunkX, chunkZ);

		this.replaceBiomeBlocks(primer, chunkX, chunkZ, this.biomes);

		//this.caveGenerator.generate(this.worldObj, chunkX, chunkZ, primer);

		//this.genClouds(primer, chunkX, chunkZ);

		Chunk chunk = new Chunk(this.worldObj, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();
		//chunk.resetRelightChecks();

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
