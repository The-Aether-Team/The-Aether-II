package com.gildedgames.orbis_core.util;

import com.gildedgames.aether.api.orbis.IDimensions;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.orbis_core.data.region.IMutableRegion;
import com.gildedgames.orbis_core.data.region.Region;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class RegionHelp
{
	public static boolean intersects(final IShape shape1, final IShape shape2)
	{
		for (final BlockPos pos : shape1.getShapeData())
		{
			if (shape2.contains(pos))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean contains(final IShape shape1, final IShape shape2)
	{
		for (final BlockPos pos : shape1.getShapeData())
		{
			if (!shape2.contains(pos))
			{
				return false;
			}
		}

		return true;
	}

	public static BlockPos getBottomCenter(final IRegion region)
	{
		final BlockPos min = region.getMin();
		return new BlockPos(min.getX() + region.getWidth() / 2, min.getY(), min.getZ() + region.getLength() / 2);
	}

	public static BlockPos getCenter(final IRegion region)
	{
		final BlockPos min = region.getMin();

		return new BlockPos(min.getX() + region.getWidth() / 2, min.getY() + region.getHeight() / 2, min.getZ() + region.getLength() / 2);
	}

	public static boolean intersects(final IRegion region1, final IRegion region2)
	{
		final BlockPos min1 = region1.getMin();
		final BlockPos min2 = region2.getMin();

		final BlockPos max1 = region1.getMax();
		final BlockPos max2 = region2.getMax();

		return max1.getX() >= min2.getX() && min1.getX() <= max2.getX() && max1.getZ() >= min2.getZ() && min1.getZ() <= max2.getZ();
	}

	public static boolean intersects2D(final IRegion region1, final IRegion region2)
	{
		final BlockPos min1 = region1.getMin();
		final BlockPos min2 = region2.getMin();

		final BlockPos max1 = region1.getMax();
		final BlockPos max2 = region2.getMax();

		return max1.getX() >= min2.getX() && min1.getX() <= max2.getX() && max1.getZ() >= min2.getZ() && min1.getZ() <= max2.getZ() && max1.getY() >= min2
				.getY() && min1.getY() <= max2.getY();
	}

	public static boolean contains(final IRegion region1, final IRegion region2)
	{
		final BlockPos min1 = region1.getMin();
		final BlockPos min2 = region2.getMin();

		final BlockPos max1 = region1.getMax();
		final BlockPos max2 = region2.getMax();

		return max1.getX() >= max2.getX() && min1.getX() <= min2.getX() && max1.getZ() >= max2.getZ() && min1.getZ() <= min2.getZ() && max1.getY() >= max2
				.getY() && min1.getY() <= min2.getY();
	}

	public static boolean contains(final IRegion region, final BlockPos pos)
	{
		return contains(region, pos.getX(), pos.getY(), pos.getZ());
	}

	public static boolean contains(final IRegion region, final int x, final int y, final int z)
	{
		final BlockPos min = region.getMin();
		final BlockPos max = region.getMax();
		return x <= max.getX() && x >= min.getX() && y <= max.getY() && y >= min.getY() && z <= max.getZ() && z >= min.getZ();
	}

	public static boolean isACorner(final BlockPos pos, final IRegion region)
	{
		final int x = pos.getX();
		final int y = pos.getY();
		final int z = pos.getZ();
		final BlockPos min = region.getMin();
		final BlockPos max = region.getMax();
		final boolean xOnEdge = min.getX() == x || max.getX() == x;
		final boolean yOnEdge = min.getY() == y || max.getY() == y;
		final boolean zOnEdge = min.getZ() == z || max.getZ() == z;
		return xOnEdge && yOnEdge && zOnEdge;
	}

	public static void translate(final IMutableRegion region, final int translateX, final int translateY, final int translateZ)
	{
		final BlockPos min = region.getMin();
		final BlockPos newMin = new BlockPos(min.getX() + translateX, min.getY() + translateY, min.getZ() + translateZ);

		final BlockPos max = region.getMax();
		final BlockPos newMax = new BlockPos(max.getX() + translateX, max.getY() + translateY, max.getZ() + translateZ);

		region.setBounds(newMin, newMax);
	}

	public static void relocate(final IMutableRegion region, final BlockPos newMin)
	{
		final BlockPos min = region.getMin();
		final BlockPos max = region.getMax();

		final int difX = max.getX() - min.getX();
		final int difY = max.getY() - min.getY();
		final int difZ = max.getZ() - min.getZ();

		final BlockPos newMax = new BlockPos(newMin.getX() + difX, newMin.getY() + difY, newMin.getZ() + difZ);
		region.setBounds(newMin, newMax);
	}

	public static BlockPos getMin(final BlockPos corner1, final BlockPos corner2)
	{
		final int minX = Math.min(corner1.getX(), corner2.getX());
		final int minY = Math.min(corner1.getY(), corner2.getY());
		final int minZ = Math.min(corner1.getZ(), corner2.getZ());

		return new BlockPos(minX, minY, minZ);
	}

	public static BlockPos getMax(final BlockPos corner1, final BlockPos corner2)
	{
		final int maxX = Math.max(corner1.getX(), corner2.getX());
		final int maxY = Math.max(corner1.getY(), corner2.getY());
		final int maxZ = Math.max(corner1.getZ(), corner2.getZ());

		return new BlockPos(maxX, maxY, maxZ);
	}

	public static BlockPos getMax(final BlockPos min, final int width, final int height, final int length)
	{
		return new BlockPos(min.getX() + width - 1, min.getY() + height - 1, min.getZ() + length - 1);
	}

	public static BlockPos getMax(final BlockPos min, final IDimensions dimensions)
	{
		return getMax(min, dimensions.getWidth(), dimensions.getHeight(), dimensions.getLength());
	}

	public static BlockPos getMin(final Collection<? extends IRegion> regions)
	{
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;

		for (final IRegion region : regions)
		{
			final BlockPos min = region.getMin();
			minX = Math.min(minX, min.getX());
			minY = Math.min(minY, min.getY());
			minZ = Math.min(minZ, min.getZ());
		}
		return new BlockPos(minX, minY, minZ);
	}

	public static BlockPos getMax(final Collection<? extends IRegion> regions)
	{
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int maxZ = Integer.MIN_VALUE;

		for (final IRegion region : regions)
		{
			final BlockPos max = region.getMax();
			maxX = Math.max(maxX, max.getX());
			maxY = Math.max(maxY, max.getY());
			maxZ = Math.max(maxZ, max.getZ());
		}
		return new BlockPos(maxX, maxY, maxZ);
	}

	public static IRegion createRegion(final BlockPos corner1, final BlockPos corner2)
	{
		return new Region(corner1, corner2);
	}

	public static IRegion empty()
	{
		return new Region(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0));
	}

	public static int getWidth(final BlockPos min, final BlockPos max)
	{
		return max.getX() - min.getX() + 1;
	}

	public static int getHeight(final BlockPos min, final BlockPos max)
	{
		return max.getY() - min.getY() + 1;
	}

	public static int getLength(final BlockPos min, final BlockPos max)
	{
		return max.getZ() - min.getZ() + 1;
	}

	public static IRegion intersection(final IRegion region1, final IRegion region2)
	{
		if (RegionHelp.intersects(region1, region2))
		{
			final BlockPos min1 = region1.getMin();
			final BlockPos min2 = region2.getMin();
			final BlockPos max1 = region1.getMax();
			final BlockPos max2 = region2.getMax();
			return new Region(new BlockPos(Math.max(min1.getX(), min2.getX()), Math.max(min1.getY(), min2.getY()), Math.max(min1.getZ(), min2.getZ())),
					new BlockPos(Math.min(max1.getX(), max2.getX()), Math.min(max1.getY(), max2.getY()), Math.min(max1.getZ(), max2.getZ())));
		}
		return RegionHelp.empty();
	}

	public static IRegion expand(final IRegion region, final int width)
	{
		return new Region(region.getMin().add(-width, -width, -width), region.getMax().add(width, width, width));
	}

	public static IRegion subtract(final IRegion region, final int x, final int y, final int z)
	{
		return new Region(region.getMin().add(-x, -y, -z), region.getMax().add(-x, -y, -z));
	}
}
