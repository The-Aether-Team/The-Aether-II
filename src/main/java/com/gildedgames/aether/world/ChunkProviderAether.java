package com.gildedgames.aether.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import com.gildedgames.aether.Aether;

public class ChunkProviderAether implements IChunkProvider
{

	private Random random;

	private NoiseGeneratorOctaves noiseGenerator1;

	private NoiseGeneratorOctaves noiseGenerator2;

	private NoiseGeneratorOctaves noiseGenerator3;

	private NoiseGeneratorOctaves noiseGenerator4;

	private NoiseGeneratorOctaves noiseGenerator5;

	public NoiseGeneratorOctaves noiseGenerator6;

	public NoiseGeneratorOctaves noiseGenerator7;

	private World worldObj;

	private double[] doubles1;

	private double[] doubles2;

	private double[] doubles3;

	private double[] doubles4;

	double[] noiseData5;

	double[] noiseData6;

	double[] noiseData7;

	double[] noiseData8;

	double[] noiseData9;

	public Block topAetherBlock;

	public Block fillerAetherBlock;

	public static int placementFlagType = 2;

	public ChunkProviderAether(World world, long l)
	{
		this.doubles2 = new double[256];
		this.doubles3 = new double[256];
		this.doubles4 = new double[256];
		this.worldObj = world;
		this.random = new Random(l);
		this.noiseGenerator1 = new NoiseGeneratorOctaves(this.random, 16);
		this.noiseGenerator2 = new NoiseGeneratorOctaves(this.random, 16);
		this.noiseGenerator3 = new NoiseGeneratorOctaves(this.random, 32);
		this.noiseGenerator4 = new NoiseGeneratorOctaves(this.random, 64);
		this.noiseGenerator5 = new NoiseGeneratorOctaves(this.random, 4);
		this.noiseGenerator6 = new NoiseGeneratorOctaves(this.random, 10);
		this.noiseGenerator7 = new NoiseGeneratorOctaves(this.random, 16);
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public boolean chunkExists(int i, int j)
	{
		return true;
	}
	

	 @Override
	public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_) 
	{
		return null;
	}

	public void fillWithBlocks1(int chunkX, int chunkZ, ChunkPrimer primer)
	{
		final int dimXZ = 2;
		final int dimXZPlusOne = dimXZ + 1;
		
		final int dimY = 32;
		final int dimYPlusOne = dimY + 1;
		
		this.doubles1 = this.initializeNoiseField(this.doubles1, chunkX * dimXZ, 0, chunkZ * dimXZ, dimXZPlusOne, dimYPlusOne, dimXZPlusOne);
		for (int x = 0; x < dimXZ; x++)
		{
			for (int z = 0; z < dimXZ; z++)
			{
				for (int y = 0; y < dimY; y++)
				{
					double minXMinZ = this.doubles1[(x * dimXZPlusOne + z) * dimYPlusOne + y];
					double minXMaxZ = this.doubles1[(x * dimXZPlusOne + z + 1) * dimYPlusOne + y];
					double maxXMinZ = this.doubles1[((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y];
					double maxXMaxZ = this.doubles1[((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y];
					
					double dMinXMinZ = (this.doubles1[(x * dimXZPlusOne + z) * dimYPlusOne + y + 1] - minXMinZ) / 4;
					double dMinXMaxZ = (this.doubles1[(x * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - minXMaxZ) / 4;
					double dMaxXMinZ = (this.doubles1[((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y + 1] - maxXMinZ) / 4;
					double dMaxXMaxZ = (this.doubles1[((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - maxXMaxZ) / 4;
					
					for (int yIter = 0; yIter < 4; yIter++)
					{
						double d10 = minXMinZ;
						double d11 = minXMaxZ;
						double d12 = (maxXMinZ - minXMinZ) / 8;
						double d13 = (maxXMaxZ - minXMaxZ) / 8;
						
						for (int xIter = 0; xIter < 8; xIter++)
						{
							//Small essay about indices.
							//If you look inside the ChunkPrimer class, you'll see it can contain 65536 elements.
							//So yeah... 16 x 256 x 16!
							
							double d15 = d10;
							double d16 = (d11 - d10) / 8;
							
							for (int zIter = 0; zIter < 8; zIter++)
							{
								Block fillBlock = Blocks.air;
								if (d15 > 0.0D)
								{
									fillBlock = Aether.getBlocks().holystone;
								}
								
								int blockX = xIter + x * 8;
								int blockY = yIter + y * 4;
								int blockZ = zIter + z * 8;
								
								primer.setBlockState(blockX, blockY, blockZ, fillBlock.getDefaultState());
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						minXMinZ += dMinXMinZ;
						minXMaxZ += dMinXMaxZ;
						maxXMinZ += dMaxXMinZ;
						maxXMaxZ += dMaxXMaxZ;
					}
				}
			}
		}
	}

	public void fillWithBlocks2(int chunkX, int chunkY, ChunkPrimer primer)
	{
		double oneThirtySnd = 0.03125D;
		
		this.doubles2 = this.noiseGenerator4.generateNoiseOctaves(this.doubles2, chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd, oneThirtySnd, 1.0D);
		this.doubles3 = this.noiseGenerator4.generateNoiseOctaves(this.doubles3, chunkX * 16, 109, chunkY * 16, 16, 1, 16, oneThirtySnd, 1.0D, oneThirtySnd);
		this.doubles4 = this.noiseGenerator5.generateNoiseOctaves(this.doubles4, chunkX * 16, chunkY * 16, 0, 16, 16, 1, oneThirtySnd * 2D, oneThirtySnd * 2D, oneThirtySnd * 2D);
		
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int sthWithHeightMap = (int) (this.doubles4[x + z * 16] / 3D + 3D + this.random.nextDouble() / 4);

				int j1 = -1;
				this.topAetherBlock = Aether.getBlocks().aether_dirt;
				this.fillerAetherBlock = Aether.getBlocks().aether_dirt;
				Block topAetherBlock = this.topAetherBlock;
				Block fillAetherBlock = this.fillerAetherBlock;
				Block stone = Aether.getBlocks().holystone;

				for (int y = 127; y >= 0; y--)
				{
					IBlockState blockState = primer.getBlockState(x, y, z);
					Block block = blockState.getBlock();
					if (block == Blocks.air)
					{
						j1 = -1;
						continue;
					}
					if (block != stone)
					{
						continue;
					}
					if (j1 == -1)
					{
						if (sthWithHeightMap <= 0)
						{
							topAetherBlock = Blocks.air;
							fillAetherBlock = stone;
						}
						j1 = sthWithHeightMap;
						if (y >= 0)
						{
							primer.setBlockState(x, y, z, topAetherBlock.getDefaultState());
						}
						else
						{
							primer.setBlockState(x, y, z, fillAetherBlock.getDefaultState());
						}
						continue;
					}
					if (j1 <= 0)
					{
						continue;
					}

					j1--;
					primer.setBlockState(x, y, z, fillAetherBlock.getDefaultState());
				}
			}
		}
	}

	private double[] initializeNoiseField(double[] inputDoubles, int x, int y, int z, int width, int height, int length)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, inputDoubles, x, y, z, width, height, length);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
		
		if (inputDoubles == null)
		{
			inputDoubles = new double[width * height * length];
		}
		double const1 = 684.41200000000003D * 2.0D;
		double const2 = 684.41200000000003D;

		this.noiseData8 = this.noiseGenerator6.generateNoiseOctaves(this.noiseData8, x, z, width, length, 1.121D, 1.121D, 0.5D);//Note: The last argument is never used
		this.noiseData9 = this.noiseGenerator7.generateNoiseOctaves(this.noiseData9, x, z, width, length, 200D, 200D, 0.5D);
		
		this.noiseData5 = this.noiseGenerator3.generateNoiseOctaves(this.noiseData5, x, y, z, width, height, length, const1 / 80D, const2 / 160D, const1 / 80D);
		this.noiseData6 = this.noiseGenerator1.generateNoiseOctaves(this.noiseData6, x, y, z, width, height, length, const1, const2, const1);
		this.noiseData7 = this.noiseGenerator2.generateNoiseOctaves(this.noiseData7, x, y, z, width, height, length, const1, const2, const1);
		int xyzIter = 0;
		for (int x1 = 0; x1 < width; x1++)
		{
			for (int z1 = 0; z1 < length; z1++)
			{
				for (int y1 = 0; y1 < height; y1++)
				{
					double finalHeight = 0.0D;
					
					double sample1 = this.noiseData6[xyzIter] / 512D;
					double sample2 = this.noiseData7[xyzIter] / 512D;
					double sample3 = (this.noiseData5[xyzIter] / 10D + 1.0D) / 2D;
					
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
						double dy = (y1 - (height - 32)) / 31D;//(y1 - 1) / 31, 0 < dy < 1
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;//
						
						/*double d6 = (y1 - (height / 2 - 2)) / 64.0D;
                        d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
                        finalHeight = finalHeight * (1.0D - d6) + -3000.0D * d6;*/
					}
					
					if (y1 < 8)
					{
						double dy = (8 - y1) / 7D;
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;
					}
					inputDoubles[xyzIter] = finalHeight;
					xyzIter++;
				}
			}
		}

		return inputDoubles;
	}


	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}


	@Override
	public String makeString()
	{
		return "RandomLevelSource";
	}

	public int getFirstUncoveredCoord(World world, int x, int z)
	{		
		Iterable iter = BlockPos.getAllInBoxMutable(new BlockPos(x, world.getHeight(), z), new BlockPos(x, 0, z));

		for(Object o : iter)
		{
			BlockPos pos = (BlockPos) o;
			if(!world.isAirBlock(pos))
			{
				return pos.getY() - 1;
			}
		}

		return 0;
	}

	@Override
	public void populate(IChunkProvider ichunkprovider, int i, int j)
	{
		int k = i * 16;
		int l = j * 16;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(ichunkprovider, this.worldObj, this.random, i, j, false));

		//Purple Aercloud Generator
		/*if (this.random.nextInt(50) == 0)
		{
			int x = k + this.random.nextInt(4);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(4);
			new AetherGenClouds(AetherBlocks.PurpleAercloud, 6, 4, false).generate(this.worldObj, this.random, x, y, z);
		}
		if (this.random.nextInt(50) == 0)
		{
			int x = k + this.random.nextInt(4);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(4);
			new AetherGenClouds(AetherBlocks.Aercloud, 5, 4, false).generate(this.worldObj, this.random, x, y, z);
		}
		if (this.random.nextInt(50) == 0)
		{
			int x = k + this.random.nextInt(4);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(4);
			new AetherGenClouds(AetherBlocks.Aercloud, 4, 4, false).generate(this.worldObj, this.random, x, y, z);
		}
		if (this.random.nextInt(50) == 0)
		{
			int x = k + this.random.nextInt(4);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(4);
			new AetherGenClouds(AetherBlocks.Aercloud, 3, 4, false).generate(this.worldObj, this.random, x, y, z);
		}

		//Green Aercloud Generator
		if (this.random.nextInt(13) == 0)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(65) + 32;
			int z = l + this.random.nextInt(16);
			new AetherGenClouds(AetherBlocks.Aercloud, 3, 8, false).generate(this.worldObj, this.random, x, y, z);
		}

		//Golden Aercloud Generator
		if (this.random.nextInt(50) == 0)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(32) + 96;
			int z = l + this.random.nextInt(16);
			new AetherGenClouds(AetherBlocks.Aercloud, 2, 4, false).generate(this.worldObj, this.random, x, y, z);
		}

		//Blue Aercloud Generator
		if (this.random.nextInt(13) == 0)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(65) + 32;
			int z = l + this.random.nextInt(16);
			new AetherGenClouds(AetherBlocks.Aercloud, 1, 8, false).generate(this.worldObj, this.random, x, y, z);
		}

		//White Aercloud Generator
		if (this.random.nextInt(7) == 0)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(65) + 32;
			int z = l + this.random.nextInt(16);
			new AetherGenClouds(AetherBlocks.Aercloud, 0, 16, false).generate(this.worldObj, this.random, x, y, z);
		}

		//Flat White Aercloud Generator
		if (this.random.nextInt(25) == 0)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(16);
			new AetherGenClouds(AetherBlocks.Aercloud, 0, 64, true).generate(this.worldObj, this.random, x, y, z);
		}*/

		BiomeGenBase biomegenbase = Aether.getAetherBiome();
		BlockFalling.fallInstantly = true;

		this.random.setSeed(this.worldObj.getSeed());
		long l1 = this.random.nextLong() / 2L * 2L + 1L;
		long l2 = this.random.nextLong() / 2L * 2L + 1L;
		this.random.setSeed(i * l1 + j * l2 ^ this.worldObj.getSeed());

		/*// TODO: add chunk population
		if (false)
		{
			return;
		}

		if (random.nextInt(32) == 0)
		{
			// Floating tree generator
			int x = k + random.nextInt(16);
			int y = random.nextInt(64) + 32;
			int z = l + random.nextInt(16);
			new GenerateFloatingTree().generate(worldObj, random, x, y, z);
		}

		if (gumCount < 800)
		{
			gumCount++;
		}

		// Gumdrop generator
		if (random.nextInt(32) == 0)
		{
			boolean flag = false;
			int x = k + random.nextInt(16) + 8;
			int y = random.nextInt(64) + 32;
			int z = l + random.nextInt(16) + 8;
			flag = new AetherGenGumdrop().generate(worldObj, random, x, y, z);

			if (flag)
			{
				gumCount = 0;
			}
		}


		 * if(random.nextInt(3) == 0) { //Lake generator int x = k +
		 * random.nextInt(16) + 8; int y = random.nextInt(128); int z = l +
		 * random.nextInt(16) + 8; (new
		 * WorldGenLakes(Block.waterStill)).generate(worldObj, random,
		 * x, y, z); }
		 // TODO: Fix, lake gen*/

		//Aether Dirt Clump Generator
		/*for (int n = 0; n < 20; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.AetherDirt, 32).generate(this.worldObj, this.random, x, y, z);
		}

		// Lake Generator
		if (this.random.nextInt(4) == 0)
		{
			int x = k + this.random.nextInt(16) + 8;
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16) + 8;
			new AetherGenLakes(Blocks.water).generate(this.worldObj, this.random, x, y, z);
		}

		// White Flower Generator
		for (int n = 0; n < 6; n++)
		{
			int x = k + this.random.nextInt(16) + 8;
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16) + 8;
			new AetherGenFlowers(AetherBlocks.WhiteRose, 64).generate(this.worldObj, this.random, x, y, z);
		}

		// Purple Flower Generator
		for (int n = 0; n < 6; n++)
		{
			if (this.random.nextInt(2) == 0)
			{
				int x = k + this.random.nextInt(16) + 8;
				int y = this.random.nextInt(128);
				int z = l + this.random.nextInt(16) + 8;
				new AetherGenFlowers(AetherBlocks.PurpleFlower, 64).generate(this.worldObj, this.random, x, y, z);
			}
		}

		//Icestone Generator
		for (int n = 0; n < 10; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.Icestone, 10).generate(this.worldObj, this.random, x, y, z);
		}

		//Ambrosium Generator
		for (int n = 0; n < 20; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.AmbrosiumOre, 16).generate(this.worldObj, this.random, x, y, z);
		}

		//Zanite Generator
		for (int n = 0; n < 15; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(64);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.ZaniteOre, 8).generate(this.worldObj, this.random, x, y, z);
		}

		//Gravitite Generator
		for (int n = 0; n < 6; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(32);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.GravititeOre, 4).generate(this.worldObj, this.random, x, y, z);
		}

		//Continuum Generator
		for (int n = 0; n < 4; n++)
		{
			int x = k + this.random.nextInt(16);
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16);
			new AetherGenMinable(AetherBlocks.ContinuumOre, 4).generate(this.worldObj, this.random, x, y, z);
		}*/

		/*//Bronze Dungeon Generator
		for (int qq = 0; qq < 2; qq++)
		{
			int x = k + random.nextInt(16);
			int y = 32 + random.nextInt(64);
			int z = l + random.nextInt(16);
			new AetherGenDungeonBronze(AetherBlocks.LockedDungeonStone, AetherBlocks.LockedLightDungeonStone,
					 					AetherBlocks.DungeonStone, AetherBlocks.LightDungeonStone,
					 					AetherBlocks.Holystone, 2, AetherBlocks.Holystone,
					 					0, 16, true).generate( worldObj, random, x, y, z);
		}

		// TODO: silver dungeon generation

		 * if (random.nextInt(500) == 0) { // Silver dungeon generator int x = k
		 * + random.nextInt(16); int y = random.nextInt(32) + 64; int z = l +
		 * random.nextInt(16); (new AetherGenDungeonSilver(
		 * AetherBlocks.LockedDungeonStone,
		 * AetherBlocks.LockedLightDungeonStone,
		 * AetherBlocks.DungeonStone,
		 * AetherBlocks.LightDungeonStone,
		 * AetherBlocks.Holystone, 2, AetherBlocks.Holystone, 0,
		 * AetherBlocks.Pillar)).generate(worldObj, random, x, y, z); }*/

		//Quicksoil Generator
		/*if (this.random.nextInt(5) == 0)
		{
			for (int x = k; x < k + 16; x++)
			{
				for (int z = l; z < l + 16; z++)
				{
					for (int n = 0; n < 48; n++)
					{
						if (this.worldObj.getBlock(x, n, z) == Blocks.air && this.worldObj.getBlock(x, n + 1, z) == AetherBlocks.AetherGrass && this.worldObj.getBlock(x, n + 2, z) == Blocks.air)
						{
							new AetherGenQuicksoil(AetherBlocks.Quicksoil).generate(this.worldObj, this.random, x, n, z);
							n = 128;
						}
					}
				}
			}
		}*/

		int numberofgrassgen = 4;

		int numberoftreegen = 3;

		//Aether Tree Generator
		/*for (int i11 = 0; i11 < numberoftreegen; i11++)
		{
			int k15 = k + this.random.nextInt(16) + 8;
			int j18 = l + this.random.nextInt(16) + 8;
			WorldGenerator worldgenerator = biomegenbase.func_150567_a(this.random);
			worldgenerator.setScale(1.0D, 1.0D, 1.0D);
			worldgenerator.generate(this.worldObj, this.random, k15, this.worldObj.getHeightValue(k15, j18), j18);
		}

		//Tall Aether Grass Generator
		for (int i11 = 0; i11 < numberofgrassgen; i11++)
		{
			int k15 = k + this.random.nextInt(16) + 8;
			int j18 = l + this.random.nextInt(16) + 8;
			WorldGenerator worldgenerator = biomegenbase.getRandomWorldGenForGrass(this.random);
			worldgenerator.generate(this.worldObj, this.random, k15, this.worldObj.getHeightValue(k15, j18), j18);
		}*/

		/*// TODO: christmas tree generation

		 * if (random.nextInt(64) == 0 && mod_Aether.christmasContent == true) {
		 * int k15 = k + random.nextInt(16) + 8; int j18 = l +
		 * random.nextInt(16) + 8; WorldGenerator worldgenerator = new
		 * GenerateChristmasTree();//
		 * biomegenbase.getRandomWorldGenForTrees(random);
		 * worldgenerator.setScale(1.0D, 1.0D, 1.0D);
		 * worldgenerator.generate(worldObj, random, k15,
		 * worldObj.getHeightValue(k15, j18), j18); }*/

		//Berry Bush Generator
		/*for (int n = 0; n < 2; n++)
		{
			int x = k + this.random.nextInt(16) + 8;
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16) + 8;
			new AetherGenFlowers(AetherBlocks.BerryBush, 32).generate(this.worldObj, this.random, x, y, z);
		}

		//Orange Fruit Tree Generator
		for (int n = 0; n < 2; n++)
		{
			int x = k + this.random.nextInt(16) + 8;
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16) + 8;
			new AetherGenOrangeFruit(16).generate(this.worldObj, this.random, x, y, z);
		}

		//Waterfall Generator
		for (int k17 = 0; k17 < 50; k17++)
		{
			int j20 = k + this.random.nextInt(16) + 8;
			int l21 = this.random.nextInt(this.random.nextInt(120) + 8);
			int l22 = l + this.random.nextInt(16) + 8;
			new AetherGenLiquids(Blocks.water).generate(this.worldObj, this.random, j20, l21, l22);
		}

		//Entrance Generator
		for (int n = 0; n < 1; n++)
		{
			int x = k + this.random.nextInt(16) + 8;
			int y = this.random.nextInt(128);
			int z = l + this.random.nextInt(16) + 8;
			new SliderLabyrinthEntrance(10).generate(this.worldObj, this.random, x, y, z);
		}*/

		//SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.random);
		//SpawnManager.instance.performWorldGenSpawning(this.worldObj, i, j, this.random);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(ichunkprovider, this.worldObj, this.random, i, j, false));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		this.random.setSeed(x * 0x4f9939f508L + z * 0x1ef1565bd5L);
		
		ChunkPrimer primer = new ChunkPrimer();

		this.fillWithBlocks1(x, z, primer);
		this.fillWithBlocks2(x, z, primer);

		Chunk chunk = new Chunk(this.worldObj, primer, x, z);
		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
	{
		return true;
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public void saveExtraData()
	{

	}

	@Override
	public Chunk provideChunk(BlockPos blockPosIn) 
	{
		return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
	}

	@Override
	public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_) 
	{
		return false;
	}

	@Override
	public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) 
	{
		
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);

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