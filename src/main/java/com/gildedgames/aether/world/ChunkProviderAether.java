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
 * WARNING:
 * This was reconstructed with almost no knowledge of how
 * world generation works. This entire class is dumb luck.
 *
 * If you break it, you're fixing it.
 * ~ Collin
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

	private double[] func_28073_a(double source[], int i, int j, int k, int l, int i1, int j1)
	{
		if (source == null)
		{
			source = new double[l * i1 * j1];
		}

		double d = 684.41200000000003D;
		double d1 = 684.41200000000003D;

		this.field_28090_g = this.ngo6.generateNoiseOctaves(this.field_28090_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
		this.field_28089_h = this.ngo7.generateNoiseOctaves(this.field_28089_h, i, k, l, j1, 200D, 200D, 0.5D);

		d *= 2D;
		this.field_28093_d = this.ngo3.generateNoiseOctaves(this.field_28093_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
		this.field_28092_e = this.ngo1.generateNoiseOctaves(this.field_28092_e, i, j, k, l, i1, j1, d, d1, d);
		this.field_28091_f = this.ngo2.generateNoiseOctaves(this.field_28091_f, i, j, k, l, i1, j1, d, d1, d);

		int k1 = 0;

		for (int j2 = 0; j2 < l; j2++)
		{
			for (int l2 = 0; l2 < j1; l2++)
			{
				for (int j3 = 0; j3 < i1; j3++)
				{
					double d8;
					double d10 = this.field_28092_e[k1] / 512D;
					double d11 = this.field_28091_f[k1] / 512D;
					double d12 = (this.field_28093_d[k1] / 10D + 1.0D) / 2D;

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

					if (j3 > i1 - k3)
					{
						double d13 = (j3 - (i1 - k3)) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d13) + -30D * d13;
					}

					k3 = 8;

					if (j3 < k3)
					{
						double d14 = (k3 - j3) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d14) + -30D * d14;
					}

					source[k1] = d8;
					k1++;
				}
			}
		}

		return source;
	}

	public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
	{
		byte byte0 = 2;
		int k = byte0 + 1;
		byte byte1 = 33;
		int l = byte0 + 1;

		this.field_28080_q = this.func_28073_a(this.field_28080_q, x * byte0, 0, z * byte0, k, byte1, l);

		for (int i1 = 0; i1 < byte0; i1++)
		{
			for (int j1 = 0; j1 < byte0; j1++)
			{
				for (int k1 = 0; k1 < 32; k1++)
				{
					double d = 0.25D;
					double d1 = this.field_28080_q[((i1 * l + j1) * byte1 + k1)];
					double d2 = this.field_28080_q[((i1 * l + j1 + 1) * byte1 + k1)];
					double d3 = this.field_28080_q[(((i1 + 1) * l + j1) * byte1 + k1)];
					double d4 = this.field_28080_q[(((i1 + 1) * l + j1 + 1) * byte1 + k1)];
					double d5 = (this.field_28080_q[(i1 * l + j1) * byte1 + k1 + 1] - d1) * d;
					double d6 = (this.field_28080_q[(i1 * l + j1 + 1) * byte1 + k1 + 1] - d2) * d;
					double d7 = (this.field_28080_q[((i1 + 1) * l + j1) * byte1 + k1 + 1] - d3) * d;
					double d8 = (this.field_28080_q[((i1 + 1) * l + j1 + 1) * byte1 + k1 + 1] - d4) * d;

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

							char c = '\200';

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

	public void setBlocksInChunk2(int i, int j, ChunkPrimer primer)
	{
		double d = 0.03125D;
		this.field_28079_r = this.ngo4.generateNoiseOctaves(this.field_28079_r, i * 16, j * 16, 0, 16, 16, 1, d, d, 1.0D);
		this.field_28078_s = this.ngo4.generateNoiseOctaves(this.field_28078_s, i * 16, 109, j * 16, 16, 1, 16, d, 1.0D, d);
		this.field_28077_t = this.ngo5.generateNoiseOctaves(this.field_28077_t, i * 16, j * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);

		for (int k = 0; k < 16; k++)
		{
			for (int l = 0; l < 16; l++)
			{
				int i1 = (int) (this.field_28077_t[k + l * 16] / 3D + 3D + this.rand.nextDouble() * 0.25D);

				int j1 = -1;
				IBlockState topBlock = this.topBlock;
				IBlockState fillerBlock = this.fillerBlock;
				IBlockState stoneBlock = this.stoneBlock;

				for (int k1 = 127; k1 >= 0; k1--)
				{
					int index = (l * 16 + k) * 128 + k1;

					IBlockState stateAtPos = primer.getBlockState(index);

					if (stateAtPos == Blocks.air.getDefaultState())
					{
						j1 = -1;
						continue;
					}

					if (stateAtPos != stoneBlock)
					{
						continue;
					}

					if (j1 == -1)
					{
						if (i1 <= 0)
						{
							topBlock = Blocks.air.getDefaultState();
							fillerBlock = stoneBlock;
						}

						j1 = i1;

						if (k1 >= 0)
						{
							primer.setBlockState(index, topBlock);
						}
						else
						{
							primer.setBlockState(index, fillerBlock);
						}

						continue;
					}

					if (j1 <= 0)
					{
						continue;
					}

					j1--;
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
		this.setBlocksInChunk2(x, z, primer);

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
