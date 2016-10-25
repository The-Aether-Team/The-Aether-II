package com.gildedgames.aether.common.world;

import com.gildedgames.aether.common.util.OpenSimplexNoise;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

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

	public static BlockPos rotate(BlockPos origin, BlockPos pos, Rotation rotation)
	{
		int i = pos.getX() - origin.getX();
		int j = pos.getY() - origin.getY();
		int k = pos.getZ() - origin.getZ();

		switch (rotation)
		{
		case COUNTERCLOCKWISE_90:
			return new BlockPos(origin.getX() + k, pos.getY(), origin.getZ() - i);
		case CLOCKWISE_90:
			return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() + i);
		case CLOCKWISE_180:
			return new BlockPos(origin.getX() - i, pos.getY(), origin.getZ() - k);
		default:
			return pos;
		}
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
