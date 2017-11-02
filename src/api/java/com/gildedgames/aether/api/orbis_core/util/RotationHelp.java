package com.gildedgames.aether.api.orbis_core.util;

import com.gildedgames.aether.api.orbis.IDimensions;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.google.common.collect.AbstractIterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;

public class RotationHelp
{

	public static Iterable<OrbisTuple<BlockPos, BlockPos>> getAllInRegionRotated(final IRegion region, final Rotation rotation)
	{
		return getAllInBoxRotated(region.getMin(), region.getMax(), rotation);
	}

	/**
	 * Returns a tuple with blockpositions. The first BlockPos is the BlockPos used for iterating through the region.
	 * The second BlockPos is a rotated BlockPos
	 */
	public static Iterable<OrbisTuple<BlockPos, BlockPos>> getAllInBoxRotated(final BlockPos from, final BlockPos to, final Rotation rotation)
	{
		final boolean clockwise = getGoClockwise(rotation, Rotation.NONE);
		final int rotAmount = Math.abs(getRotationAmount(rotation, Rotation.NONE));

		final BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
		final BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));

		final int width = RegionHelp.getWidth(min, max);
		final int length = RegionHelp.getLength(min, max);

		final int centerX = min.getX() + width / 2;
		final int centerZ = min.getZ() + length / 2;

		final BlockPos center = new BlockPos(centerX, from.getY(), centerZ);

		final int roundingX = getRounding(width);
		final int roundingZ = getRounding(length);

		return new Iterable<OrbisTuple<BlockPos, BlockPos>>()
		{
			@Override
			public Iterator<OrbisTuple<BlockPos, BlockPos>> iterator()
			{
				return new AbstractIterator<OrbisTuple<BlockPos, BlockPos>>()
				{
					private BlockPos.MutableBlockPos theBlockPos = null;

					private BlockPos.MutableBlockPos rotated = null;

					private OrbisTuple<BlockPos, BlockPos> theTuple;

					void doRotate()
					{
						RotationHelp.setRotated(this.rotated, this.theBlockPos, center, roundingX, roundingZ, rotAmount, clockwise);
					}

					OrbisTuple<BlockPos, BlockPos> computeNext0()
					{
						if (this.theBlockPos == null)
						{
							this.theBlockPos = new BlockPos.MutableBlockPos(min.getX(), min.getY(), min.getZ());
							this.rotated = new BlockPos.MutableBlockPos(0, 0, 0);
							this.doRotate();
							this.theTuple = new OrbisTuple<BlockPos, BlockPos>(this.theBlockPos, this.rotated);
							return this.theTuple;
						}
						else if (this.theBlockPos.equals(max))
						{
							return this.endOfData();
						}
						else
						{
							int i = this.theBlockPos.getX();
							int k = this.theBlockPos.getY();
							int j = this.theBlockPos.getZ();

							if (i < max.getX())
							{
								++i;
							}
							else if (k < max.getY())
							{
								i = min.getX();
								++k;
							}
							else if (j < max.getZ())
							{
								i = min.getX();
								k = min.getY();
								++j;
							}

							setX(this.theBlockPos, i);
							this.theBlockPos.setY(k);
							setZ(this.theBlockPos, j);

							this.doRotate();

							return this.theTuple;
						}
					}

					@Override
					protected OrbisTuple<BlockPos, BlockPos> computeNext()
					{
						return this.computeNext0();
					}
				};
			}
		};
	}

	public static int getRounding(final int dimension)
	{
		return dimension % 2 == 0 ? 1 : 0;
	}

	public static void setX(final BlockPos.MutableBlockPos pos, final int x)
	{
		pos.setPos(x, pos.getY(), pos.getZ());
	}

	public static void setZ(final BlockPos.MutableBlockPos pos, final int z)
	{
		pos.setPos(pos.getX(), pos.getY(), z);
	}

	public static void setRotated(
			final BlockPos.MutableBlockPos rotated, final BlockPos toRotate, final BlockPos center, final int roundingX, final int roundingZ,
			final int rotAmount,
			final boolean clockwise)
	{
		final int centerX = center.getX();
		final int centerZ = center.getZ();
		final int relX = toRotate.getX() - centerX;
		rotated.setY(toRotate.getY());
		final int relZ = toRotate.getZ() - centerZ;
		if (rotAmount == 0)
		{
			setX(rotated, toRotate.getX());
			setZ(rotated, toRotate.getZ());
		}
		else if (rotAmount == 1)
		{
			if (clockwise)
			{
				setX(rotated, centerX - relZ - roundingZ);
				setZ(rotated, centerZ + relX);
			}
			else
			{
				setX(rotated, centerX + relZ);
				setZ(rotated, centerZ - relX - roundingX);
			}
		}
		else
		{
			setX(rotated, centerX - relX - roundingX);
			setZ(rotated, centerZ - relZ - roundingZ);
		}
	}

	public static BlockPos rotate(final BlockPos pos, final BlockPos center, final Rotation rotation, final int width, final int length)
	{
		final int rotAmount = getRotationAmount(rotation, Rotation.NONE);
		final boolean clockwise = getGoClockwise(rotation, Rotation.NONE);

		final BlockPos.MutableBlockPos rotated = new BlockPos.MutableBlockPos(0, 0, 0);

		setRotated(rotated, pos, center, getRounding(width), getRounding(length), rotAmount, clockwise);

		return rotated;
	}

	public static BlockPos rotate(final BlockPos pos, final IRegion region, final Rotation rotation)
	{
		return rotate(pos, RegionHelp.getBottomCenter(region), rotation, region.getWidth(), region.getLength());
	}

	public static IRegion rotate(final IRegion region, final BlockPos center, final Rotation rotation, final int width, final int length)
	{
		final BlockPos corner1 = rotate(region.getMin(), center, rotation, width, length);
		final BlockPos corner2 = rotate(region.getMax(), center, rotation, width, length);

		return new Region(corner1, corner2);
	}

	public static IRegion rotate(final IRegion region, final IRegion rotateIn, final Rotation rotation)
	{
		return rotate(region, RegionHelp.getBottomCenter(rotateIn), rotation, rotateIn.getWidth(), rotateIn.getLength());
	}

	public static IRegion rotate(final IRegion region, final Rotation rotation)
	{
		final BlockPos center = RegionHelp.getBottomCenter(region);
		return regionFromCenter(center, region, rotation);
	}

	public static IRegion regionFromCenter(final int centerX, final int centerY, final int centerZ, final IDimensions dimensions, final Rotation rotation)
	{
		final int width = getWidth(dimensions, rotation);
		final int length = getLength(dimensions, rotation);

		final BlockPos min = new BlockPos(centerX - width / 2, centerY, centerZ - length / 2);
		final BlockPos max = new BlockPos(min.getX() + width - 1, min.getY() + dimensions.getHeight() - 1, min.getZ() + length - 1);

		return new Region(min, max);
	}

	public static IRegion regionFromCenter(final BlockPos center, final IDimensions dimensions, final Rotation rotation)
	{
		return regionFromCenter(center.getX(), center.getY(), center.getZ(), dimensions, rotation);
	}

	public static Rotation fromFacing(final EnumFacing facing)
	{
		switch (facing)
		{
			case NORTH:
				return Rotation.NONE;
			case SOUTH:
				return Rotation.CLOCKWISE_180;
			case EAST:
				return Rotation.CLOCKWISE_90;
			case WEST:
				return Rotation.COUNTERCLOCKWISE_90;
			default:
				throw new IllegalArgumentException();
		}
	}

	public static int rotatedMinX(final int minX, final int width, final int length, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return minX + width / 2 - length / 2;
		}
		else
		{
			return minX;
		}
	}

	public static int rotatedMinZ(final int minZ, final int width, final int length, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return minZ + length / 2 - width / 2;
		}
		else
		{
			return minZ;
		}
	}

	public static int getWidth(final IDimensions dimensions, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return dimensions.getLength();
		}
		else
		{
			return dimensions.getWidth();
		}
	}

	public static int getLength(final IDimensions dimensions, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return dimensions.getWidth();
		}
		else
		{
			return dimensions.getLength();
		}
	}

	public static int getWidth(final int width, final int length, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return length;
		}
		else
		{
			return width;
		}
	}

	public static int getLength(final int width, final int length, final Rotation rotation)
	{
		if (isOddDiffWithNeutral(rotation))
		{
			return width;
		}
		else
		{
			return length;
		}
	}

	public static BlockPos getMax(final BlockPos min, final int width, final int height, final int length, final Rotation rotation)
	{
		return RegionHelp.getMax(min, getWidth(width, length, rotation), height, getLength(width, length, rotation));
	}

	public static BlockPos getMax(final BlockPos min, final IDimensions dimensions, final Rotation rotation)
	{
		return RotationHelp.getMax(min, dimensions.getWidth(), dimensions.getHeight(), dimensions.getLength(), rotation);
	}

	private static int indexDifference(final Rotation rot1, final Rotation rot2)
	{
		return rot1.ordinal() - rot2.ordinal();
	}

	public static int getRotationAmount(final Rotation rot1, final Rotation rot2)
	{
		final int rotationAmount = indexDifference(rot1, rot2);

		if (rotationAmount == 3 || rotationAmount == -3)
		{
			return 1;
		}

		return rotationAmount;
	}

	public static boolean isOddDiffWithNeutral(final Rotation rot)
	{
		return Math.abs(getRotationAmount(rot, Rotation.NONE)) == 1;
	}

	public static Rotation getNextRotation(final Rotation rotation, final boolean clockwise)
	{
		int newIndex = rotation.ordinal() + (clockwise ? 1 : -1);

		if (newIndex < 0)
		{
			newIndex = Rotation.values().length - 1;
		}
		else if (newIndex == Rotation.values().length)
		{
			newIndex = 0;
		}

		return Rotation.values()[newIndex];
	}

	public static boolean getGoClockwise(final Rotation rot1, final Rotation rot2)
	{
		final int rotationAmount = indexDifference(rot1, rot2);
		final boolean clockwise = rotationAmount > 0;

		if (rotationAmount == 3)
		{
			return false;
		}
		else if (rotationAmount == -3)
		{
			return true;
		}
		return clockwise;
	}
}
