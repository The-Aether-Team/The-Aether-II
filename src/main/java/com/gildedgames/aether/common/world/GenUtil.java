package com.gildedgames.aether.common.world;

import com.gildedgames.aether.common.util.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

import java.util.Random;

public class GenUtil
{

	public static double octavedNoise(NoiseGeneratorPerlin noise, int octaves, double roughness, double scale, double x, double z)
	{
		double noiseSum = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		double weightSum = 0;

		for (int octave = 0; octave < octaves; octave++)
		{
			noiseSum += noise.getValue(x * layerFrequency, z * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			weightSum += layerWeight;
			layerWeight *= roughness;
		}

		return noiseSum / weightSum;
	}

	public static double octavedNoise(NoiseGeneratorSimplex noise, int octaves, double roughness, double scale, double x, double z)
	{
		double noiseSum = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		double weightSum = 0;

		for (int octave = 0; octave < octaves; octave++)
		{
			noiseSum += noise.getValue(x * layerFrequency, z * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			weightSum += layerWeight;
			layerWeight *= roughness;
		}

		return noiseSum / weightSum;
	}

	public static double octavedNoise3D(OpenSimplexNoise noise, int octaves, double roughness, double scale, double x, double y, double z)
	{
		double noiseSum = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		double weightSum = 0;

		for (int octave = 0; octave < octaves; octave++)
		{
			noiseSum += noise.eval(x * layerFrequency, y * layerFrequency, z * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			weightSum += layerWeight;
			layerWeight *= roughness;
		}

		return noiseSum / weightSum;
	}

	public static double octavedNoise(OpenSimplexNoise noise, int octaves, double roughness, double scale, double x, double z)
	{
		double noiseSum = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		double weightSum = 0;

		for (int octave = 0; octave < octaves; octave++)
		{
			noiseSum += noise.eval(x * layerFrequency, z * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			weightSum += layerWeight;
			layerWeight *= roughness;
		}

		return noiseSum / weightSum;
	}

	/**
	 * Fast array filling for identical value
	 * @param array
	 * @param value
	 */
	public static <T> void fillArray(T[] array, T value)
	{
		int len = array.length;

		if (len > 0)
		{
			array[0] = value;
		}

		for (int i = 1; i < len; i += i)
		{
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}

	public static void fillArray(char[] array, char value)
	{
		int len = array.length;

		if (len > 0)
		{
			array[0] = value;
		}

		for (int i = 1; i < len; i += i)
		{
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}

	public static void fillArray(short[] array, short value)
	{
		int len = array.length;

		if (len > 0)
		{
			array[0] = value;
		}

		for (int i = 1; i < len; i += i)
		{
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}

	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState state)
	{
		GenUtil.cuboid(world, min, max, state, state, true);
	}

	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState state, boolean replaceSolidBlocks)
	{
		GenUtil.cuboid(world, min, max, state, state, replaceSolidBlocks);
	}

	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState edgeBlockState, IBlockState fillBlockState)
	{
		GenUtil.cuboid(world, min, max, edgeBlockState, fillBlockState, true);
	}

	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState edgeBlockState, IBlockState fillBlockState, boolean replaceSolidBlocks)
	{
		for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max))
		{
			if (replaceSolidBlocks || world.isAirBlock(pos))
			{
				if (pos.getY() != min.getY() && pos.getY() != max.getY() && pos.getX() != min.getX() && pos.getX() != max.getX() && pos.getZ() != min.getZ() && pos.getZ() != max.getZ())
				{
					world.setBlockState(pos, fillBlockState);
				}
				else
				{
					world.setBlockState(pos, edgeBlockState);
				}
			}
		}
	}

	public static void cuboidVaried(World world, BlockPos min, BlockPos max, IBlockState block1, IBlockState block2, int rarity, Random rand)
	{
		GenUtil.cuboidVaried(world, min, max, block1, block2, rarity, rand, true);
	}

	public static void cuboidVaried(World world, BlockPos min, BlockPos max, IBlockState block1, IBlockState block2, int rarity, Random rand, boolean replaceSolidBlocks)
	{
		for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max))
		{
			if (replaceSolidBlocks || world.isAirBlock(pos))
			{
				IBlockState chosenState = block1;

				if (rand.nextInt(rarity) == 1)
				{
					chosenState = block2;
				}

				world.setBlockState(pos, chosenState);
			}
		}
	}

}
