package com.gildedgames.aether.common.world.util;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class GenUtil
{

	public static double bilinearInterpolate(final double bottomLeftValue, final double topLeftValue, final double bottomRightValue, final double topRightValue,
			final double bottomLeftX, final double topRightX, final double bottomLeftY, final double topRightY, final double x, final double y)
	{
		final double x2x1;
		final double y2y1;
		final double x2x;
		final double y2y;
		final double yy1;
		final double xx1;
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

	public static BlockPos rotate(final BlockPos origin, final BlockPos pos, final Rotation rotation)
	{
		final int i = pos.getX() - origin.getX();
		final int j = pos.getY() - origin.getY();
		final int k = pos.getZ() - origin.getZ();

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

	public static double octavedNoise(final OpenSimplexNoise noise, final int octaves, final double roughness, final double scale, final double x,
			final double z)
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
