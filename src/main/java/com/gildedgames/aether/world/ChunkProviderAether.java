package com.gildedgames.aether.world;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import java.util.List;
import java.util.Random;

/*
 * I'm a stupid class.
 * Please don't touch me.
 */
public class ChunkProviderAether implements IChunkProvider
{
	private World world;

	private Random rand;

	private NoiseGeneratorOctaves ngo1, ngo2, ngo3, ngo4, ngo5, ngo6, ngo7;

	private double[] field_28080_q, field_28079_r, field_28078_s, field_28077_t;

	private double[] field_28093_d, field_28092_e, field_28091_f, field_28090_g, field_28089_h;

	private final IBlockState topBlock, fillerBlock, airBlock, stoneBlock;

	public ChunkProviderAether(World world, long seed)
	{
		this.world = world;

		this.rand = new Random(seed);

		this.ngo1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.ngo2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.ngo3 = new NoiseGeneratorOctaves(this.rand, 32);
		this.ngo4 = new NoiseGeneratorOctaves(this.rand, 64);
		this.ngo5 = new NoiseGeneratorOctaves(this.rand, 4);
		this.ngo6 = new NoiseGeneratorOctaves(this.rand, 10);
		this.ngo7 = new NoiseGeneratorOctaves(this.rand, 16);

		this.topBlock = Aether.getBlocks().aether_dirt.getDefaultState().withProperty(BlockAetherDirt.GRASS_VARIANT, BlockAetherDirt.AETHER_GRASS);
		this.fillerBlock = Aether.getBlocks().holystone.getDefaultState();
		this.stoneBlock = Aether.getBlocks().holystone.getDefaultState();
		this.airBlock = Blocks.air.getDefaultState();
	}

	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}

	private double[] generateNoiseOctaves(double source[], int offsetX, int offsetY, int offsetZ, int length, int width, int height)
	{
		if (source == null)
		{
			source = new double[length * width * height];
		}

		double noise1 = 684.41200000000003D;
		double noise2 = 684.41200000000003D;

		// generateNoiseOctaves(arrayToFill, offsetX, offsetZ, length, height, noiseX, noiseY, noiseZ);
		this.field_28090_g = this.ngo6.generateNoiseOctaves(this.field_28090_g, offsetX, offsetZ, length, height, 1.121D, 1.121D, 0.5D);
		this.field_28089_h = this.ngo7.generateNoiseOctaves(this.field_28089_h, offsetX, offsetZ, length, height, 200D, 200D, 0.5D);

		// generateNoiseOctaves(arrayToFill, offsetX, offsetY, offsetZ, length, width, height, noiseX, noiseY, noiseZ);
		noise1 *= 2D;
		this.field_28093_d = this.ngo3.generateNoiseOctaves(this.field_28093_d, offsetX, offsetY, offsetZ, length, width, height, noise1 / 80D, noise2 / 160D, noise1 / 80D);
		this.field_28092_e = this.ngo1.generateNoiseOctaves(this.field_28092_e, offsetX, offsetY, offsetZ, length, width, height, noise1, noise2, noise1);
		this.field_28091_f = this.ngo2.generateNoiseOctaves(this.field_28091_f, offsetX, offsetY, offsetZ, length, width, height, noise1, noise2, noise1);

		int index = 0;

		for (int x = 0; x < length; x++)
		{
			for (int y = 0; y < height; y++)
			{
				for (int z = 0; z < width; z++)
				{
					double d8;
					double d10 = this.field_28092_e[index] / 512D;
					double d11 = this.field_28091_f[index] / 512D;
					double d12 = (this.field_28093_d[index] / 10D + 1.0D) / 2D;

					if (d12 < 0.0D)
					{
						d8 = d10;
					}
					else if (d12 > 1.0D)
					{
						d8 = d11;
					}
					else
					{
						d8 = d10 + (d11 - d10) * d12;
					}

					d8 -= 8D;
					int k3 = 32;

					if (z > width - k3)
					{
						double d13 = (z - (width - k3)) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d13) + -30D * d13;
					}

					k3 = 8;

					if (z < k3)
					{
						double d14 = (k3 - z) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d14) + -30D * d14;
					}

					source[index] = d8;
					index++;
				}
			}
		}

		return source;
	}

	public void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer)
	{
		byte offsetX = 2;
		int k = offsetX + 1;
		byte offsetZ = 33;
		int l = offsetX + 1;

		this.field_28080_q = this.generateNoiseOctaves(this.field_28080_q, chunkX * offsetX, 0, chunkZ * offsetX, k, offsetZ, l);

		for (int i1 = 0; i1 < offsetX; i1++)
		{
			for (int j1 = 0; j1 < offsetX; j1++)
			{
				for (int k1 = 0; k1 < 32; k1++)
				{
					double d = 0.25D;
					double d1 = this.field_28080_q[((i1 * l + j1) * offsetZ + k1)];
					double d2 = this.field_28080_q[((i1 * l + j1 + 1) * offsetZ + k1)];
					double d3 = this.field_28080_q[(((i1 + 1) * l + j1) * offsetZ + k1)];
					double d4 = this.field_28080_q[(((i1 + 1) * l + j1 + 1) * offsetZ + k1)];
					double d5 = (this.field_28080_q[(i1 * l + j1) * offsetZ + k1 + 1] - d1) * d;
					double d6 = (this.field_28080_q[(i1 * l + j1 + 1) * offsetZ + k1 + 1] - d2) * d;
					double d7 = (this.field_28080_q[((i1 + 1) * l + j1) * offsetZ + k1 + 1] - d3) * d;
					double d8 = (this.field_28080_q[((i1 + 1) * l + j1 + 1) * offsetZ + k1 + 1] - d4) * d;

					for (int l1 = 0; l1 < 4; l1++)
					{
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 8; i2++)
						{
							int index = i2 + i1 * 8 << 11 | j1 * 8 << 7 | k1 * 4 + l1;

							int c = 128;

							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 8; k2++)
							{
								IBlockState state = this.airBlock;

								if (d15 > 0.0D)
								{
									state = this.fillerBlock;
								}

								primer.setBlockState(index, state);

								index += c;
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	public void fillChunk(int chunkX, int chunkZ, ChunkPrimer primer)
	{
		double noiseFactor = 0.03125D;
		this.field_28079_r = this.ngo4.generateNoiseOctaves(this.field_28079_r, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, noiseFactor, noiseFactor, 1.0D);
		this.field_28078_s = this.ngo4.generateNoiseOctaves(this.field_28078_s, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, noiseFactor, 1.0D, noiseFactor);
		this.field_28077_t = this.ngo5.generateNoiseOctaves(this.field_28077_t, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, noiseFactor * 2D, noiseFactor * 2D, noiseFactor * 2D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int i1 = (int) (this.field_28077_t[x + z * 16] / 3D + 3D + this.rand.nextDouble() * 0.25D);

				int top = -1;
				IBlockState topBlock = this.topBlock;
				IBlockState fillerBlock = this.fillerBlock;

				for (int y = 127; y >= 0; y--)
				{
					int index = (z * 16 + x) * 128 + y;

					IBlockState blockState = primer.getBlockState(index);

					if (blockState == Blocks.air.getDefaultState())
					{
						top = -1;
						continue;
					}

					if (blockState != this.stoneBlock)
					{
						continue;
					}

					if (top == -1)
					{
						if (i1 <= 0)
						{
							topBlock = this.airBlock;
							fillerBlock = this.stoneBlock;
						}

						top = i1;

						if (y >= 0)
						{
							primer.setBlockState(index, topBlock);
						}
						else
						{
							primer.setBlockState(index, fillerBlock);
						}

						continue;
					}

					if (top <= 0)
					{
						continue;
					}

					top--;
					primer.setBlockState(index, fillerBlock);
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);

		ChunkPrimer primer = new ChunkPrimer();

		this.setBlocksInChunk(x, z, primer);
		this.fillChunk(x, z, primer);

		Chunk chunk = new Chunk(this.world, primer, x, z);
		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public Chunk provideChunk(BlockPos blockPos)
	{
		return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
	}

	@Override
	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		// TODO: Populate chunks with trees and stuff.
	}

	@Override
	public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
	{
		return false;
	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate progressUpdate)
	{
		return true;
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "RandomAetherLevelSource";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		BiomeGenBase biomeGenBase = this.world.getBiomeGenForCoords(pos);

		return biomeGenBase.getSpawnableList(creatureType);
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
	{
		return null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z)
	{

	}

	@Override
	public void saveExtraData()
	{

	}
}
