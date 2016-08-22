package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.aether.common.blocks.BlocksAether;
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

public class ChunkGeneratorAetherOld implements IChunkGenerator
{
    private final IBlockState air, aether_stone, cold_aercloud;

    private final World worldObj;

    private final Random random;

    private NoiseGeneratorOctaves[] octaveNoiseGenerators;

    private NoiseGeneratorOctaves cloudNoiseGenerator;

    private WorldGenAetherCaves caveGenerator = new WorldGenAetherCaves();

    private double[] cloudNoise;

    private double[][] noiseFields;

    private Biome[] biomesForGeneration;

    public final static int PLACEMENT_FLAG_TYPE = 2;

    public ChunkGeneratorAetherOld(World world, long seed)
    {
        this.air = Blocks.AIR.getDefaultState();
        this.aether_stone = BlocksAether.holystone.getDefaultState();
        this.cold_aercloud = BlocksAether.aercloud.getDefaultState();

        this.worldObj = world;
        this.random = new Random(seed);

        this.noiseFields = new double[9][];
        this.noiseFields[1] = new double[256];
        this.noiseFields[2] = new double[256];
        this.noiseFields[3] = new double[256];

        this.octaveNoiseGenerators = new NoiseGeneratorOctaves[7];
        this.octaveNoiseGenerators[0] = new NoiseGeneratorOctaves(this.random, 16);
        this.octaveNoiseGenerators[1] = new NoiseGeneratorOctaves(this.random, 16);
        this.octaveNoiseGenerators[2] = new NoiseGeneratorOctaves(this.random, 32);
        this.octaveNoiseGenerators[3] = new NoiseGeneratorOctaves(this.random, 64);
        this.octaveNoiseGenerators[4] = new NoiseGeneratorOctaves(this.random, 4);
        this.octaveNoiseGenerators[5] = new NoiseGeneratorOctaves(this.random, 10);
        this.octaveNoiseGenerators[6] = new NoiseGeneratorOctaves(this.random, 16);

        this.cloudNoiseGenerator = new NoiseGeneratorOctaves(this.random, 12);
    }

    @Override
    public BlockPos getStrongholdGen(World world, String structureName, BlockPos pos)
    {
        return null;
    }

    public void setBlocksInChunk(ChunkPrimer primer, int chunkX, int chunkZ)
    {
        final int dimXZ = 2;
        final int dimXZPlusOne = dimXZ + 1;

        final int dimY = 32;
        final int dimYPlusOne = dimY + 1;

        this.noiseFields[0] = this.initializeNoiseField(this.noiseFields[0], chunkX * dimXZ, 0, chunkZ * dimXZ, dimXZPlusOne, dimYPlusOne, dimXZPlusOne);

        for (int x = 0; x < dimXZ; x++)
        {
            for (int z = 0; z < dimXZ; z++)
            {
                for (int y = 0; y < dimY; y++)
                {
                    double minXMinZ = this.noiseFields[0][(x * dimXZPlusOne + z) * dimYPlusOne + y];
                    double minXMaxZ = this.noiseFields[0][(x * dimXZPlusOne + z + 1) * dimYPlusOne + y];
                    double maxXMinZ = this.noiseFields[0][((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y];
                    double maxXMaxZ = this.noiseFields[0][((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y];

                    double dMinXMinZ = (this.noiseFields[0][(x * dimXZPlusOne + z) * dimYPlusOne + y + 1] - minXMinZ) / 4;
                    double dMinXMaxZ = (this.noiseFields[0][(x * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - minXMaxZ) / 4;
                    double dMaxXMinZ = (this.noiseFields[0][((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y + 1] - maxXMinZ) / 4;
                    double dMaxXMaxZ = (this.noiseFields[0][((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - maxXMaxZ) / 4;

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
                                IBlockState fillBlock = this.air;

                                if (d15 > 0.0D)
                                {
                                    fillBlock = this.aether_stone;
                                }

                                int blockX = xIter + x * 8;
                                int blockY = yIter + y * 4;
                                int blockZ = zIter + z * 8;

                                primer.setBlockState(blockX, blockY, blockZ, fillBlock);
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

                int sthWithHeightMap = (int) (this.noiseFields[3][x + z * 16] / 3D + 3D + this.random.nextDouble() / 4);

                int j1 = -1;

                IBlockState topAetherBlock = biome.topBlock;
                IBlockState fillAetherBlock = biome.fillerBlock;
                IBlockState stone = this.aether_stone;

                for (int y = 127; y >= 0; y--)
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

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

        ChunkPrimer primer = new ChunkPrimer();

        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);

        this.setBlocksInChunk(primer, chunkX, chunkZ);
        this.replaceBiomeBlocks(primer, chunkX, chunkZ, this.biomesForGeneration);

        this.caveGenerator.generate(this.worldObj, chunkX, chunkZ, primer);

        this.genClouds(primer, chunkX, chunkZ);

        Chunk chunk = new Chunk(this.worldObj, primer, chunkX, chunkZ);
        chunk.generateSkylightMap();

        return chunk;
    }

    @Override
    public void populate(int chunkX, int chunkZ)
    {
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, this.worldObj, this.random, chunkX, chunkZ, false));

        int x = chunkX * 16;
        int z = chunkZ * 16;

        BlockPos pos = new BlockPos(x, 0, z);

        Biome biome = this.worldObj.getBiome(pos.add(16, 0, 16));

        this.random.setSeed(this.worldObj.getSeed());

        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;

        this.random.setSeed(chunkX * i1 + chunkZ * j1 ^ this.worldObj.getSeed());

        biome.decorate(this.worldObj, this.random, pos);
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
