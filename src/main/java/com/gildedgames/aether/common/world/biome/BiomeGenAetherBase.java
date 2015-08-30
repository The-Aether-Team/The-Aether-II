package com.gildedgames.aether.common.world.biome;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud.AercloudVariant;
import com.gildedgames.aether.common.world.features.WorldGenClouds;

public abstract class BiomeGenAetherBase extends BiomeGenBase
{
	public BiomeGenAetherBase(int id)
	{
		super(id);

		this.setBiomeName("Aether");
		this.setDisableRain();
		this.setTemperatureRainfall(0.5f, 0f);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public int getSkyColorByTemp(float temp)
	{
		return 0xc0c0ff;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x70DB70;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return this.getModdedBiomeDecorator(new BiomeDecoratorAether());
	}

	private IBlockState stateFromVariant(AercloudVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.PROPERTY_VARIANT, variant);
	}

	private IBlockState purpleFromFacing(EnumFacing facing)
	{
		return BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.PROPERTY_VARIANT, BlockAercloud.PURPLE_AERCLOUD).withProperty(BlockAercloud.PROPERTY_FACING, facing);
	}
	
	private void generateCloud(World world, Random rand, IBlockState state, int rarity, int x1, int z1, int width, int height, int offsetY, int numOfBlocks, boolean flat)
	{
		if (rand.nextInt(rarity) == 0)
		{
			int x = x1 + rand.nextInt(width);
			int y = rand.nextInt(height);
			int z = z1 + rand.nextInt(width);
			(new WorldGenClouds(state, numOfBlocks, flat)).generate(world, rand, new BlockPos(x, y, z));
		}
	}

	public void generateClouds(World world, Random rand, int chunkX, int chunkZ)
	{
		int x1 = chunkX * 16;
		int z1 = chunkZ * 16;

		//								state								                rarity      width height offsetY numOfBlocks flat
		this.generateCloud(world, rand, this.purpleFromFacing(EnumFacing.NORTH),            50, x1, z1, 4,    32,    0,      4,          false);
		this.generateCloud(world, rand, this.purpleFromFacing(EnumFacing.EAST),             50, x1, z1, 4,    32,    0,      4,          false);
		this.generateCloud(world, rand, this.purpleFromFacing(EnumFacing.WEST),             50, x1, z1, 4,    32,    0,      4,          false);
		this.generateCloud(world, rand, this.purpleFromFacing(EnumFacing.SOUTH),            50, x1, z1, 4,    32,    0,      4,          false);
		
		this.generateCloud(world, rand, this.stateFromVariant(BlockAercloud.BLUE_AERCLOUD), 15, x1, z1, 16,   65,    32,     8,          false);

		this.generateCloud(world, rand, this.stateFromVariant(BlockAercloud.COLD_AERCLOUD), 10, x1, z1, 16,   65,    32,     16,         false);

		this.generateCloud(world, rand, this.stateFromVariant(BlockAercloud.COLD_AERCLOUD), 30, x1, z1, 16, 32, 0, 64, true);


		/*if (rand.nextInt(6) == 0)
		{
			int x = x1 + rand.nextInt(16);
			int y = rand.nextInt(64) + 64;
			int z = z1 + rand.nextInt(16);
			(new AetherGenCumulusClouds(AetherBlocks.Aercloud.blockID, 10, 3)).generate(world, rand, new BlockPos(x, y, z));
		}*/

	}
}
