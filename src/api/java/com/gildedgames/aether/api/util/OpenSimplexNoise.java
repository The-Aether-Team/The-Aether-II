package com.gildedgames.aether.api.util;

/*
 * OpenSimplex Noise in Java.
 * by Kurt Spencer
 * 
 * v1.1 (October 5, 2014)
 * - Added 2D and 4D implementations.
 * - Proper gradient sets for all dimensions, from a
 *   dimensionally-generalizable scheme with an actual
 *   rhyme and reason behind it.
 * - Removed default permutation array in favor of
 *   default seed.
 * - Changed seed-based constructor to be independent
 *   of any particular randomization library, so results
 *   will be the same when ported to other languages.
 */

public class OpenSimplexNoise
{

	private static final double STRETCH_CONSTANT_2D = -0.211324865405187; //(1/Math.sqrt(2+1)-1)/2;

	private static final double SQUISH_CONSTANT_2D = 0.366025403784439; //(Math.sqrt(2+1)-1)/2;

	private static final double STRETCH_CONSTANT_3D = -1.0 / 6; //(1/Math.sqrt(3+1)-1)/3;

	private static final double SQUISH_CONSTANT_3D = 1.0 / 3; //(Math.sqrt(3+1)-1)/3;

	private static final double NORM_CONSTANT_2D = 47;

	private static final double NORM_CONSTANT_3D = 103;

	private static final long DEFAULT_SEED = 0;

	//Gradients for 2D. They approximate the directions to the
	//vertices of an octagon from the center.
	private static final byte[] gradients2D = new byte[] {
			5, 2, 2, 5,
			-5, 2, -2, 5,
			5, -2, 2, -5,
			-5, -2, -2, -5,
	};

	//Gradients for 3D. They approximate the directions to the
	//vertices of a rhombicuboctahedron from the center, skewed so
	//that the triangular and square facets can be inscribed inside
	//circles of the same radius.
	private static final byte[] gradients3D = new byte[] {
			-11, 4, 4, -4, 11, 4, -4, 4, 11,
			11, 4, 4, 4, 11, 4, 4, 4, 11,
			-11, -4, 4, -4, -11, 4, -4, -4, 11,
			11, -4, 4, 4, -11, 4, 4, -4, 11,
			-11, 4, -4, -4, 11, -4, -4, 4, -11,
			11, 4, -4, 4, 11, -4, 4, 4, -11,
			-11, -4, -4, -4, -11, -4, -4, -4, -11,
			11, -4, -4, 4, -11, -4, 4, -4, -11,
	};

	private final short[] perm;

	private final short[] permGradIndex3D;

	public OpenSimplexNoise()
	{
		this(DEFAULT_SEED);
	}

	public OpenSimplexNoise(final short[] perm)
	{
		this.perm = perm;
		this.permGradIndex3D = new short[256];

		for (int i = 0; i < 256; i++)
		{
			//Since 3D has 24 gradients, simple bitmask won't work, so precompute modulo array.
			this.permGradIndex3D[i] = (short) ((perm[i] % (gradients3D.length / 3)) * 3);
		}
	}

	//Initializes the class using a permutation array generated from a 64-bit seed.
	//Generates a proper permutation (i.e. doesn't merely perform N successive pair swaps on a base array)
	//Uses a simple 64-bit LCG.
	public OpenSimplexNoise(long seed)
	{
		this.perm = new short[256];
		this.permGradIndex3D = new short[256];
		final short[] source = new short[256];
		for (short i = 0; i < 256; i++)
		{
			source[i] = i;
		}
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		for (int i = 255; i >= 0; i--)
		{
			seed = seed * 6364136223846793005L + 1442695040888963407L;
			int r = (int) ((seed + 31) % (i + 1));
			if (r < 0)
			{
				r += (i + 1);
			}
			this.perm[i] = source[r];
			this.permGradIndex3D[i] = (short) ((this.perm[i] % (gradients3D.length / 3)) * 3);
			source[r] = source[i];
		}
	}

	private static int fastFloor(final double x)
	{
		final int xi = (int) x;
		return x < xi ? xi - 1 : xi;
	}

	//2D OpenSimplex Noise.
	public double eval(final double x, final double y)
	{

		//Place input coordinates onto grid.
		final double stretchOffset = (x + y) * STRETCH_CONSTANT_2D;
		final double xs = x + stretchOffset;
		final double ys = y + stretchOffset;

		//Floor to get grid coordinates of rhombus (stretched square) super-cell origin.
		int xsb = fastFloor(xs);
		int ysb = fastFloor(ys);

		//Skew out to get actual coordinates of rhombus origin. We'll need these later.
		final double squishOffset = (xsb + ysb) * SQUISH_CONSTANT_2D;
		final double xb = xsb + squishOffset;
		final double yb = ysb + squishOffset;

		//Compute grid coordinates relative to rhombus origin.
		final double xins = xs - xsb;
		final double yins = ys - ysb;

		//Sum those together to get a value that determines which region we're in.
		final double inSum = xins + yins;

		//Positions relative to origin point.
		double dx0 = x - xb;
		double dy0 = y - yb;

		//We'll be defining these inside the next block and using them afterwards.
		final double dx_ext;
		final double dy_ext;
		final int xsv_ext;
		final int ysv_ext;

		double value = 0;

		//Contribution (1,0)
		final double dx1 = dx0 - 1 - SQUISH_CONSTANT_2D;
		final double dy1 = dy0 - 0 - SQUISH_CONSTANT_2D;
		double attn1 = 2 - dx1 * dx1 - dy1 * dy1;
		if (attn1 > 0)
		{
			attn1 *= attn1;
			value += attn1 * attn1 * this.extrapolate(xsb + 1, ysb, dx1, dy1);
		}

		//Contribution (0,1)
		final double dx2 = dx0 - 0 - SQUISH_CONSTANT_2D;
		final double dy2 = dy0 - 1 - SQUISH_CONSTANT_2D;
		double attn2 = 2 - dx2 * dx2 - dy2 * dy2;
		if (attn2 > 0)
		{
			attn2 *= attn2;
			value += attn2 * attn2 * this.extrapolate(xsb, ysb + 1, dx2, dy2);
		}

		if (inSum <= 1)
		{ //We're inside the triangle (2-Simplex) at (0,0)
			final double zins = 1 - inSum;
			if (zins > xins || zins > yins)
			{ //(0,0) is one of the closest two triangular vertices
				if (xins > yins)
				{
					xsv_ext = xsb + 1;
					ysv_ext = ysb - 1;
					dx_ext = dx0 - 1;
					dy_ext = dy0 + 1;
				}
				else
				{
					xsv_ext = xsb - 1;
					ysv_ext = ysb + 1;
					dx_ext = dx0 + 1;
					dy_ext = dy0 - 1;
				}
			}
			else
			{ //(1,0) and (0,1) are the closest two vertices.
				xsv_ext = xsb + 1;
				ysv_ext = ysb + 1;
				dx_ext = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
				dy_ext = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
			}
		}
		else
		{ //We're inside the triangle (2-Simplex) at (1,1)
			final double zins = 2 - inSum;
			if (zins < xins || zins < yins)
			{ //(0,0) is one of the closest two triangular vertices
				if (xins > yins)
				{
					xsv_ext = xsb + 2;
					ysv_ext = ysb;
					dx_ext = dx0 - 2 - 2 * SQUISH_CONSTANT_2D;
					dy_ext = dy0 + 0 - 2 * SQUISH_CONSTANT_2D;
				}
				else
				{
					xsv_ext = xsb;
					ysv_ext = ysb + 2;
					dx_ext = dx0 + 0 - 2 * SQUISH_CONSTANT_2D;
					dy_ext = dy0 - 2 - 2 * SQUISH_CONSTANT_2D;
				}
			}
			else
			{ //(1,0) and (0,1) are the closest two vertices.
				dx_ext = dx0;
				dy_ext = dy0;
				xsv_ext = xsb;
				ysv_ext = ysb;
			}
			xsb += 1;
			ysb += 1;
			dx0 = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
			dy0 = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
		}

		//Contribution (0,0) or (1,1)
		double attn0 = 2 - dx0 * dx0 - dy0 * dy0;
		if (attn0 > 0)
		{
			attn0 *= attn0;
			value += attn0 * attn0 * this.extrapolate(xsb, ysb, dx0, dy0);
		}

		//Extra Vertex
		double attn_ext = 2 - dx_ext * dx_ext - dy_ext * dy_ext;
		if (attn_ext > 0)
		{
			attn_ext *= attn_ext;
			value += attn_ext * attn_ext * this.extrapolate(xsv_ext, ysv_ext, dx_ext, dy_ext);
		}

		return value / NORM_CONSTANT_2D;
	}

	//3D OpenSimplex Noise.
	public double eval(final double x, final double y, final double z)
	{

		//Place input coordinates on simplectic honeycomb.
		final double stretchOffset = (x + y + z) * STRETCH_CONSTANT_3D;
		final double xs = x + stretchOffset;
		final double ys = y + stretchOffset;
		final double zs = z + stretchOffset;

		//Floor to get simplectic honeycomb coordinates of rhombohedron (stretched cube) super-cell origin.
		final int xsb = fastFloor(xs);
		final int ysb = fastFloor(ys);
		final int zsb = fastFloor(zs);

		//Skew out to get actual coordinates of rhombohedron origin. We'll need these later.
		final double squishOffset = (xsb + ysb + zsb) * SQUISH_CONSTANT_3D;
		final double xb = xsb + squishOffset;
		final double yb = ysb + squishOffset;
		final double zb = zsb + squishOffset;

		//Compute simplectic honeycomb coordinates relative to rhombohedral origin.
		final double xins = xs - xsb;
		final double yins = ys - ysb;
		final double zins = zs - zsb;

		//Sum those together to get a value that determines which region we're in.
		final double inSum = xins + yins + zins;

		//Positions relative to origin point.
		double dx0 = x - xb;
		double dy0 = y - yb;
		double dz0 = z - zb;

		//We'll be defining these inside the next block and using them afterwards.
		final double dx_ext0;
		double dy_ext0;
		final double dz_ext0;
		double dx_ext1, dy_ext1, dz_ext1;
		final int xsv_ext0;
		int ysv_ext0;
		final int zsv_ext0;
		int xsv_ext1, ysv_ext1, zsv_ext1;

		double value = 0;
		if (inSum <= 1)
		{ //We're inside the tetrahedron (3-Simplex) at (0,0,0)

			//Determine which two of (0,0,1), (0,1,0), (1,0,0) are closest.
			byte aPoint = 0x01;
			double aScore = xins;
			byte bPoint = 0x02;
			double bScore = yins;
			if (aScore >= bScore && zins > bScore)
			{
				bScore = zins;
				bPoint = 0x04;
			}
			else if (aScore < bScore && zins > aScore)
			{
				aScore = zins;
				aPoint = 0x04;
			}

			//Now we determine the two lattice points not part of the tetrahedron that may contribute.
			//This depends on the closest two tetrahedral vertices, including (0,0,0)
			final double wins = 1 - inSum;
			if (wins > aScore || wins > bScore)
			{ //(0,0,0) is one of the closest two tetrahedral vertices.
				final byte c = (bScore > aScore ? bPoint : aPoint); //Our other closest vertex is the closest out of a and b.

				if ((c & 0x01) == 0)
				{
					xsv_ext0 = xsb - 1;
					xsv_ext1 = xsb;
					dx_ext0 = dx0 + 1;
					dx_ext1 = dx0;
				}
				else
				{
					xsv_ext0 = xsv_ext1 = xsb + 1;
					dx_ext0 = dx_ext1 = dx0 - 1;
				}

				if ((c & 0x02) == 0)
				{
					ysv_ext0 = ysv_ext1 = ysb;
					dy_ext0 = dy_ext1 = dy0;
					if ((c & 0x01) == 0)
					{
						ysv_ext1 -= 1;
						dy_ext1 += 1;
					}
					else
					{
						ysv_ext0 -= 1;
						dy_ext0 += 1;
					}
				}
				else
				{
					ysv_ext0 = ysv_ext1 = ysb + 1;
					dy_ext0 = dy_ext1 = dy0 - 1;
				}

				if ((c & 0x04) == 0)
				{
					zsv_ext0 = zsb;
					zsv_ext1 = zsb - 1;
					dz_ext0 = dz0;
					dz_ext1 = dz0 + 1;
				}
				else
				{
					zsv_ext0 = zsv_ext1 = zsb + 1;
					dz_ext0 = dz_ext1 = dz0 - 1;
				}
			}
			else
			{ //(0,0,0) is not one of the closest two tetrahedral vertices.
				final byte c = (byte) (aPoint | bPoint); //Our two extra vertices are determined by the closest two.

				if ((c & 0x01) == 0)
				{
					xsv_ext0 = xsb;
					xsv_ext1 = xsb - 1;
					dx_ext0 = dx0 - 2 * SQUISH_CONSTANT_3D;
					dx_ext1 = dx0 + 1 - SQUISH_CONSTANT_3D;
				}
				else
				{
					xsv_ext0 = xsv_ext1 = xsb + 1;
					dx_ext0 = dx0 - 1 - 2 * SQUISH_CONSTANT_3D;
					dx_ext1 = dx0 - 1 - SQUISH_CONSTANT_3D;
				}

				if ((c & 0x02) == 0)
				{
					ysv_ext0 = ysb;
					ysv_ext1 = ysb - 1;
					dy_ext0 = dy0 - 2 * SQUISH_CONSTANT_3D;
					dy_ext1 = dy0 + 1 - SQUISH_CONSTANT_3D;
				}
				else
				{
					ysv_ext0 = ysv_ext1 = ysb + 1;
					dy_ext0 = dy0 - 1 - 2 * SQUISH_CONSTANT_3D;
					dy_ext1 = dy0 - 1 - SQUISH_CONSTANT_3D;
				}

				if ((c & 0x04) == 0)
				{
					zsv_ext0 = zsb;
					zsv_ext1 = zsb - 1;
					dz_ext0 = dz0 - 2 * SQUISH_CONSTANT_3D;
					dz_ext1 = dz0 + 1 - SQUISH_CONSTANT_3D;
				}
				else
				{
					zsv_ext0 = zsv_ext1 = zsb + 1;
					dz_ext0 = dz0 - 1 - 2 * SQUISH_CONSTANT_3D;
					dz_ext1 = dz0 - 1 - SQUISH_CONSTANT_3D;
				}
			}

			//Contribution (0,0,0)
			double attn0 = 2 - dx0 * dx0 - dy0 * dy0 - dz0 * dz0;
			if (attn0 > 0)
			{
				attn0 *= attn0;
				value += attn0 * attn0 * this.extrapolate(xsb, ysb, zsb, dx0, dy0, dz0);
			}

			//Contribution (1,0,0)
			final double dx1 = dx0 - 1 - SQUISH_CONSTANT_3D;
			final double dy1 = dy0 - 0 - SQUISH_CONSTANT_3D;
			final double dz1 = dz0 - 0 - SQUISH_CONSTANT_3D;
			double attn1 = 2 - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
			if (attn1 > 0)
			{
				attn1 *= attn1;
				value += attn1 * attn1 * this.extrapolate(xsb + 1, ysb, zsb, dx1, dy1, dz1);
			}

			//Contribution (0,1,0)
			final double dx2 = dx0 - 0 - SQUISH_CONSTANT_3D;
			final double dy2 = dy0 - 1 - SQUISH_CONSTANT_3D;
			final double dz2 = dz1;
			double attn2 = 2 - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
			if (attn2 > 0)
			{
				attn2 *= attn2;
				value += attn2 * attn2 * this.extrapolate(xsb, ysb + 1, zsb, dx2, dy2, dz2);
			}

			//Contribution (0,0,1)
			final double dx3 = dx2;
			final double dy3 = dy1;
			final double dz3 = dz0 - 1 - SQUISH_CONSTANT_3D;
			double attn3 = 2 - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
			if (attn3 > 0)
			{
				attn3 *= attn3;
				value += attn3 * attn3 * this.extrapolate(xsb, ysb, zsb + 1, dx3, dy3, dz3);
			}
		}
		else if (inSum >= 2)
		{ //We're inside the tetrahedron (3-Simplex) at (1,1,1)

			//Determine which two tetrahedral vertices are the closest, out of (1,1,0), (1,0,1), (0,1,1) but not (1,1,1).
			byte aPoint = 0x06;
			double aScore = xins;
			byte bPoint = 0x05;
			double bScore = yins;
			if (aScore <= bScore && zins < bScore)
			{
				bScore = zins;
				bPoint = 0x03;
			}
			else if (aScore > bScore && zins < aScore)
			{
				aScore = zins;
				aPoint = 0x03;
			}

			//Now we determine the two lattice points not part of the tetrahedron that may contribute.
			//This depends on the closest two tetrahedral vertices, including (1,1,1)
			final double wins = 3 - inSum;
			if (wins < aScore || wins < bScore)
			{ //(1,1,1) is one of the closest two tetrahedral vertices.
				final byte c = (bScore < aScore ? bPoint : aPoint); //Our other closest vertex is the closest out of a and b.

				if ((c & 0x01) != 0)
				{
					xsv_ext0 = xsb + 2;
					xsv_ext1 = xsb + 1;
					dx_ext0 = dx0 - 2 - 3 * SQUISH_CONSTANT_3D;
					dx_ext1 = dx0 - 1 - 3 * SQUISH_CONSTANT_3D;
				}
				else
				{
					xsv_ext0 = xsv_ext1 = xsb;
					dx_ext0 = dx_ext1 = dx0 - 3 * SQUISH_CONSTANT_3D;
				}

				if ((c & 0x02) != 0)
				{
					ysv_ext0 = ysv_ext1 = ysb + 1;
					dy_ext0 = dy_ext1 = dy0 - 1 - 3 * SQUISH_CONSTANT_3D;
					if ((c & 0x01) != 0)
					{
						ysv_ext1 += 1;
						dy_ext1 -= 1;
					}
					else
					{
						ysv_ext0 += 1;
						dy_ext0 -= 1;
					}
				}
				else
				{
					ysv_ext0 = ysv_ext1 = ysb;
					dy_ext0 = dy_ext1 = dy0 - 3 * SQUISH_CONSTANT_3D;
				}

				if ((c & 0x04) != 0)
				{
					zsv_ext0 = zsb + 1;
					zsv_ext1 = zsb + 2;
					dz_ext0 = dz0 - 1 - 3 * SQUISH_CONSTANT_3D;
					dz_ext1 = dz0 - 2 - 3 * SQUISH_CONSTANT_3D;
				}
				else
				{
					zsv_ext0 = zsv_ext1 = zsb;
					dz_ext0 = dz_ext1 = dz0 - 3 * SQUISH_CONSTANT_3D;
				}
			}
			else
			{ //(1,1,1) is not one of the closest two tetrahedral vertices.
				final byte c = (byte) (aPoint & bPoint); //Our two extra vertices are determined by the closest two.

				if ((c & 0x01) != 0)
				{
					xsv_ext0 = xsb + 1;
					xsv_ext1 = xsb + 2;
					dx_ext0 = dx0 - 1 - SQUISH_CONSTANT_3D;
					dx_ext1 = dx0 - 2 - 2 * SQUISH_CONSTANT_3D;
				}
				else
				{
					xsv_ext0 = xsv_ext1 = xsb;
					dx_ext0 = dx0 - SQUISH_CONSTANT_3D;
					dx_ext1 = dx0 - 2 * SQUISH_CONSTANT_3D;
				}

				if ((c & 0x02) != 0)
				{
					ysv_ext0 = ysb + 1;
					ysv_ext1 = ysb + 2;
					dy_ext0 = dy0 - 1 - SQUISH_CONSTANT_3D;
					dy_ext1 = dy0 - 2 - 2 * SQUISH_CONSTANT_3D;
				}
				else
				{
					ysv_ext0 = ysv_ext1 = ysb;
					dy_ext0 = dy0 - SQUISH_CONSTANT_3D;
					dy_ext1 = dy0 - 2 * SQUISH_CONSTANT_3D;
				}

				if ((c & 0x04) != 0)
				{
					zsv_ext0 = zsb + 1;
					zsv_ext1 = zsb + 2;
					dz_ext0 = dz0 - 1 - SQUISH_CONSTANT_3D;
					dz_ext1 = dz0 - 2 - 2 * SQUISH_CONSTANT_3D;
				}
				else
				{
					zsv_ext0 = zsv_ext1 = zsb;
					dz_ext0 = dz0 - SQUISH_CONSTANT_3D;
					dz_ext1 = dz0 - 2 * SQUISH_CONSTANT_3D;
				}
			}

			//Contribution (1,1,0)
			final double dx3 = dx0 - 1 - 2 * SQUISH_CONSTANT_3D;
			final double dy3 = dy0 - 1 - 2 * SQUISH_CONSTANT_3D;
			final double dz3 = dz0 - 0 - 2 * SQUISH_CONSTANT_3D;
			double attn3 = 2 - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
			if (attn3 > 0)
			{
				attn3 *= attn3;
				value += attn3 * attn3 * this.extrapolate(xsb + 1, ysb + 1, zsb, dx3, dy3, dz3);
			}

			//Contribution (1,0,1)
			final double dx2 = dx3;
			final double dy2 = dy0 - 0 - 2 * SQUISH_CONSTANT_3D;
			final double dz2 = dz0 - 1 - 2 * SQUISH_CONSTANT_3D;
			double attn2 = 2 - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
			if (attn2 > 0)
			{
				attn2 *= attn2;
				value += attn2 * attn2 * this.extrapolate(xsb + 1, ysb, zsb + 1, dx2, dy2, dz2);
			}

			//Contribution (0,1,1)
			final double dx1 = dx0 - 0 - 2 * SQUISH_CONSTANT_3D;
			final double dy1 = dy3;
			final double dz1 = dz2;
			double attn1 = 2 - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
			if (attn1 > 0)
			{
				attn1 *= attn1;
				value += attn1 * attn1 * this.extrapolate(xsb, ysb + 1, zsb + 1, dx1, dy1, dz1);
			}

			//Contribution (1,1,1)
			dx0 = dx0 - 1 - 3 * SQUISH_CONSTANT_3D;
			dy0 = dy0 - 1 - 3 * SQUISH_CONSTANT_3D;
			dz0 = dz0 - 1 - 3 * SQUISH_CONSTANT_3D;
			double attn0 = 2 - dx0 * dx0 - dy0 * dy0 - dz0 * dz0;
			if (attn0 > 0)
			{
				attn0 *= attn0;
				value += attn0 * attn0 * this.extrapolate(xsb + 1, ysb + 1, zsb + 1, dx0, dy0, dz0);
			}
		}
		else
		{ //We're inside the octahedron (Rectified 3-Simplex) in between.
			double aScore;
			byte aPoint;
			boolean aIsFurtherSide;
			double bScore;
			byte bPoint;
			boolean bIsFurtherSide;

			//Decide between point (0,0,1) and (1,1,0) as closest
			final double p1 = xins + yins;
			if (p1 > 1)
			{
				aScore = p1 - 1;
				aPoint = 0x03;
				aIsFurtherSide = true;
			}
			else
			{
				aScore = 1 - p1;
				aPoint = 0x04;
				aIsFurtherSide = false;
			}

			//Decide between point (0,1,0) and (1,0,1) as closest
			final double p2 = xins + zins;
			if (p2 > 1)
			{
				bScore = p2 - 1;
				bPoint = 0x05;
				bIsFurtherSide = true;
			}
			else
			{
				bScore = 1 - p2;
				bPoint = 0x02;
				bIsFurtherSide = false;
			}

			//The closest out of the two (1,0,0) and (0,1,1) will replace the furthest out of the two decided above, if closer.
			final double p3 = yins + zins;
			if (p3 > 1)
			{
				final double score = p3 - 1;
				if (aScore <= bScore && aScore < score)
				{
					aScore = score;
					aPoint = 0x06;
					aIsFurtherSide = true;
				}
				else if (aScore > bScore && bScore < score)
				{
					bScore = score;
					bPoint = 0x06;
					bIsFurtherSide = true;
				}
			}
			else
			{
				final double score = 1 - p3;
				if (aScore <= bScore && aScore < score)
				{
					aScore = score;
					aPoint = 0x01;
					aIsFurtherSide = false;
				}
				else if (aScore > bScore && bScore < score)
				{
					bScore = score;
					bPoint = 0x01;
					bIsFurtherSide = false;
				}
			}

			//Where each of the two closest points are determines how the extra two vertices are calculated.
			if (aIsFurtherSide == bIsFurtherSide)
			{
				if (aIsFurtherSide)
				{ //Both closest points on (1,1,1) side

					//One of the two extra points is (1,1,1)
					dx_ext0 = dx0 - 1 - 3 * SQUISH_CONSTANT_3D;
					dy_ext0 = dy0 - 1 - 3 * SQUISH_CONSTANT_3D;
					dz_ext0 = dz0 - 1 - 3 * SQUISH_CONSTANT_3D;
					xsv_ext0 = xsb + 1;
					ysv_ext0 = ysb + 1;
					zsv_ext0 = zsb + 1;

					//Other extra point is based on the shared axis.
					final byte c = (byte) (aPoint & bPoint);
					if ((c & 0x01) != 0)
					{
						dx_ext1 = dx0 - 2 - 2 * SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 - 2 * SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 - 2 * SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb + 2;
						ysv_ext1 = ysb;
						zsv_ext1 = zsb;
					}
					else if ((c & 0x02) != 0)
					{
						dx_ext1 = dx0 - 2 * SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 - 2 - 2 * SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 - 2 * SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb;
						ysv_ext1 = ysb + 2;
						zsv_ext1 = zsb;
					}
					else
					{
						dx_ext1 = dx0 - 2 * SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 - 2 * SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 - 2 - 2 * SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb;
						ysv_ext1 = ysb;
						zsv_ext1 = zsb + 2;
					}
				}
				else
				{//Both closest points on (0,0,0) side

					//One of the two extra points is (0,0,0)
					dx_ext0 = dx0;
					dy_ext0 = dy0;
					dz_ext0 = dz0;
					xsv_ext0 = xsb;
					ysv_ext0 = ysb;
					zsv_ext0 = zsb;

					//Other extra point is based on the omitted axis.
					final byte c = (byte) (aPoint | bPoint);
					if ((c & 0x01) == 0)
					{
						dx_ext1 = dx0 + 1 - SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 - 1 - SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 - 1 - SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb - 1;
						ysv_ext1 = ysb + 1;
						zsv_ext1 = zsb + 1;
					}
					else if ((c & 0x02) == 0)
					{
						dx_ext1 = dx0 - 1 - SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 + 1 - SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 - 1 - SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb + 1;
						ysv_ext1 = ysb - 1;
						zsv_ext1 = zsb + 1;
					}
					else
					{
						dx_ext1 = dx0 - 1 - SQUISH_CONSTANT_3D;
						dy_ext1 = dy0 - 1 - SQUISH_CONSTANT_3D;
						dz_ext1 = dz0 + 1 - SQUISH_CONSTANT_3D;
						xsv_ext1 = xsb + 1;
						ysv_ext1 = ysb + 1;
						zsv_ext1 = zsb - 1;
					}
				}
			}
			else
			{ //One point on (0,0,0) side, one point on (1,1,1) side
				final byte c1;
				final byte c2;
				if (aIsFurtherSide)
				{
					c1 = aPoint;
					c2 = bPoint;
				}
				else
				{
					c1 = bPoint;
					c2 = aPoint;
				}

				//One contribution is a permutation of (1,1,-1)
				if ((c1 & 0x01) == 0)
				{
					dx_ext0 = dx0 + 1 - SQUISH_CONSTANT_3D;
					dy_ext0 = dy0 - 1 - SQUISH_CONSTANT_3D;
					dz_ext0 = dz0 - 1 - SQUISH_CONSTANT_3D;
					xsv_ext0 = xsb - 1;
					ysv_ext0 = ysb + 1;
					zsv_ext0 = zsb + 1;
				}
				else if ((c1 & 0x02) == 0)
				{
					dx_ext0 = dx0 - 1 - SQUISH_CONSTANT_3D;
					dy_ext0 = dy0 + 1 - SQUISH_CONSTANT_3D;
					dz_ext0 = dz0 - 1 - SQUISH_CONSTANT_3D;
					xsv_ext0 = xsb + 1;
					ysv_ext0 = ysb - 1;
					zsv_ext0 = zsb + 1;
				}
				else
				{
					dx_ext0 = dx0 - 1 - SQUISH_CONSTANT_3D;
					dy_ext0 = dy0 - 1 - SQUISH_CONSTANT_3D;
					dz_ext0 = dz0 + 1 - SQUISH_CONSTANT_3D;
					xsv_ext0 = xsb + 1;
					ysv_ext0 = ysb + 1;
					zsv_ext0 = zsb - 1;
				}

				//One contribution is a permutation of (0,0,2)
				dx_ext1 = dx0 - 2 * SQUISH_CONSTANT_3D;
				dy_ext1 = dy0 - 2 * SQUISH_CONSTANT_3D;
				dz_ext1 = dz0 - 2 * SQUISH_CONSTANT_3D;
				xsv_ext1 = xsb;
				ysv_ext1 = ysb;
				zsv_ext1 = zsb;
				if ((c2 & 0x01) != 0)
				{
					dx_ext1 -= 2;
					xsv_ext1 += 2;
				}
				else if ((c2 & 0x02) != 0)
				{
					dy_ext1 -= 2;
					ysv_ext1 += 2;
				}
				else
				{
					dz_ext1 -= 2;
					zsv_ext1 += 2;
				}
			}

			//Contribution (1,0,0)
			final double dx1 = dx0 - 1 - SQUISH_CONSTANT_3D;
			final double dy1 = dy0 - 0 - SQUISH_CONSTANT_3D;
			final double dz1 = dz0 - 0 - SQUISH_CONSTANT_3D;
			double attn1 = 2 - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
			if (attn1 > 0)
			{
				attn1 *= attn1;
				value += attn1 * attn1 * this.extrapolate(xsb + 1, ysb, zsb, dx1, dy1, dz1);
			}

			//Contribution (0,1,0)
			final double dx2 = dx0 - 0 - SQUISH_CONSTANT_3D;
			final double dy2 = dy0 - 1 - SQUISH_CONSTANT_3D;
			final double dz2 = dz1;
			double attn2 = 2 - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
			if (attn2 > 0)
			{
				attn2 *= attn2;
				value += attn2 * attn2 * this.extrapolate(xsb, ysb + 1, zsb, dx2, dy2, dz2);
			}

			//Contribution (0,0,1)
			final double dx3 = dx2;
			final double dy3 = dy1;
			final double dz3 = dz0 - 1 - SQUISH_CONSTANT_3D;
			double attn3 = 2 - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
			if (attn3 > 0)
			{
				attn3 *= attn3;
				value += attn3 * attn3 * this.extrapolate(xsb, ysb, zsb + 1, dx3, dy3, dz3);
			}

			//Contribution (1,1,0)
			final double dx4 = dx0 - 1 - 2 * SQUISH_CONSTANT_3D;
			final double dy4 = dy0 - 1 - 2 * SQUISH_CONSTANT_3D;
			final double dz4 = dz0 - 0 - 2 * SQUISH_CONSTANT_3D;
			double attn4 = 2 - dx4 * dx4 - dy4 * dy4 - dz4 * dz4;
			if (attn4 > 0)
			{
				attn4 *= attn4;
				value += attn4 * attn4 * this.extrapolate(xsb + 1, ysb + 1, zsb, dx4, dy4, dz4);
			}

			//Contribution (1,0,1)
			final double dx5 = dx4;
			final double dy5 = dy0 - 0 - 2 * SQUISH_CONSTANT_3D;
			final double dz5 = dz0 - 1 - 2 * SQUISH_CONSTANT_3D;
			double attn5 = 2 - dx5 * dx5 - dy5 * dy5 - dz5 * dz5;
			if (attn5 > 0)
			{
				attn5 *= attn5;
				value += attn5 * attn5 * this.extrapolate(xsb + 1, ysb, zsb + 1, dx5, dy5, dz5);
			}

			//Contribution (0,1,1)
			final double dx6 = dx0 - 0 - 2 * SQUISH_CONSTANT_3D;
			final double dy6 = dy4;
			final double dz6 = dz5;
			double attn6 = 2 - dx6 * dx6 - dy6 * dy6 - dz6 * dz6;
			if (attn6 > 0)
			{
				attn6 *= attn6;
				value += attn6 * attn6 * this.extrapolate(xsb, ysb + 1, zsb + 1, dx6, dy6, dz6);
			}
		}

		//First extra vertex
		double attn_ext0 = 2 - dx_ext0 * dx_ext0 - dy_ext0 * dy_ext0 - dz_ext0 * dz_ext0;
		if (attn_ext0 > 0)
		{
			attn_ext0 *= attn_ext0;
			value += attn_ext0 * attn_ext0 * this.extrapolate(xsv_ext0, ysv_ext0, zsv_ext0, dx_ext0, dy_ext0, dz_ext0);
		}

		//Second extra vertex
		double attn_ext1 = 2 - dx_ext1 * dx_ext1 - dy_ext1 * dy_ext1 - dz_ext1 * dz_ext1;
		if (attn_ext1 > 0)
		{
			attn_ext1 *= attn_ext1;
			value += attn_ext1 * attn_ext1 * this.extrapolate(xsv_ext1, ysv_ext1, zsv_ext1, dx_ext1, dy_ext1, dz_ext1);
		}

		return value / NORM_CONSTANT_3D;
	}

	private double extrapolate(final int xsb, final int ysb, final double dx, final double dy)
	{
		final int index = this.perm[(this.perm[xsb & 0xFF] + ysb) & 0xFF] & 0x0E;
		return gradients2D[index] * dx
				+ gradients2D[index + 1] * dy;
	}

	private double extrapolate(final int xsb, final int ysb, final int zsb, final double dx, final double dy, final double dz)
	{
		final int index = this.permGradIndex3D[(this.perm[(this.perm[xsb & 0xFF] + ysb) & 0xFF] + zsb) & 0xFF];
		return gradients3D[index] * dx
				+ gradients3D[index + 1] * dy
				+ gradients3D[index + 2] * dz;
	}

}
