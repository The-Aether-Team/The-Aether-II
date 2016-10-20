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

	public static double bilinearInterpolate(double bottomLeftValue, double topLeftValue, double bottomRightValue, double topRightValue, double bottomLeftX, double topRightX, double bottomLeftY, double topRightY, double x, double y)
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

	public static BlockPos getTopBlock(World world, BlockPos pos)
	{
		BlockPos searchPos = new BlockPos(pos.getX(), world.getActualHeight(), pos.getZ());

		while (world.isAirBlock(searchPos))
		{
			if (searchPos.getY() <= 0)
			{
				break;
			}

			searchPos = searchPos.down();
		}

		return searchPos;
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

}
