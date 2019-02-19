package com.gildedgames.aether.api.util;

/*
 * A speed-improved simplex noise algorithm for 2D and 3D in Java.
 *
 * Based on example code by Stefan Gustavson (stegu@itn.liu.se).
 * Optimisations by Peter Eastman (peastman@drizzle.stanford.edu).
 *
 * "This could be sped up even further, but it's useful as it is."
 *
 * With additional changes by Angeline (angeline@gildedgames.com) for
 * seeding the permutation table.
 */
public class OpenSimplexNoise
{
	// Skewing and unskewing factors for 2, 3, and 4 dimensions
	private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);

	private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;

	private static final double F3 = 1.0 / 3.0;

	private static final double G3 = 1.0 / 6.0;

	private static Grad[] GRAD3 = {
			new Grad(1, 1, 0), new Grad(-1, 1, 0), new Grad(1, -1, 0),
			new Grad(-1, -1, 0), new Grad(1, 0, 1), new Grad(-1, 0, 1),
			new Grad(1, 0, -1), new Grad(-1, 0, -1), new Grad(0, 1, 1),
			new Grad(0, -1, 1), new Grad(0, 1, -1), new Grad(0, -1, -1)
	};

	// To remove the need for index wrapping, double the permutation table length
	private short[] perm = new short[512];

	private short[] permMod12 = new short[512];

	public OpenSimplexNoise()
	{
		this(0L);
	}

	public OpenSimplexNoise(long seed)
	{
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
			this.perm[i + 256] = source[r];
			this.permMod12[i] = (short) (this.perm[i] % 12);
			this.permMod12[i + 256] = (short) (this.perm[i + 256] % 12);

			source[r] = source[i];
		}
	}

	// 2D simplex noise
	public double eval(double xin, double yin)
	{
		// Noise contributions from the three corners
		double n0, n1, n2;

		// Skew the input space to determine which simplex cell we're in
		double s = (xin + yin) * F2; // Hairy factor for 2D

		int i = fastFloor(xin + s);
		int j = fastFloor(yin + s);

		double t = (i + j) * G2;

		// Un-skew the cell origin back to (x,y) space
		double X0 = i - t;
		double Y0 = j - t;

		// The x,y distances from the cell origin
		double x0 = xin - X0;
		double y0 = yin - Y0;

		// For the 2D case, the simplex shape is an equilateral triangle.
		// Determine which simplex we are in.

		// Offsets for second (middle) corner of simplex in (i,j) coordinates
		int i1, j1;

		if (x0 > y0)
		{
			// lower triangle, XY order: (0,0)->(1,0)->(1,1)
			i1 = 1;
			j1 = 0;
		}
		else
		{
			// upper triangle, YX order: (0,0)->(0,1)->(1,1)
			i1 = 0;
			j1 = 1;
		}

		// A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
		// a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
		// c = (3-sqrt(3))/6

		double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) un-skewed coordinates
		double y1 = y0 - j1 + G2;

		double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) un-skewed coordinates
		double y2 = y0 - 1.0 + 2.0 * G2;

		// Work out the hashed gradient indices of the three simplex corners
		int ii = i & 255;
		int jj = j & 255;

		int gi0 = this.permMod12[ii + this.perm[jj]];
		int gi1 = this.permMod12[ii + i1 + this.perm[jj + j1]];
		int gi2 = this.permMod12[ii + 1 + this.perm[jj + 1]];

		// Calculate the contribution from the three corners
		double t0 = 0.5 - x0 * x0 - y0 * y0;

		if (t0 < 0.0)
		{
			n0 = 0.0;
		}
		else
		{
			t0 *= t0;
			n0 = t0 * t0 * dot(GRAD3[gi0], x0, y0);  // (x,y) of grad3 used for 2D gradient
		}

		double t1 = 0.5 - x1 * x1 - y1 * y1;

		if (t1 < 0.0)
		{
			n1 = 0.0;
		}
		else
		{
			t1 *= t1;
			n1 = t1 * t1 * dot(GRAD3[gi1], x1, y1);
		}

		double t2 = 0.5 - x2 * x2 - y2 * y2;

		if (t2 < 0.0)
		{
			n2 = 0.0;
		}
		else
		{
			t2 *= t2;
			n2 = t2 * t2 * dot(GRAD3[gi2], x2, y2);
		}

		// Add contributions from each corner to get the final noise value.
		// The result is scaled to return values in the interval [-1,1].
		return 70.0 * (n0 + n1 + n2);
	}

	// 3D simplex noise
	public double eval(double xin, double yin, double zin)
	{
		// Noise contributions from the four corners
		double n0, n1, n2, n3;

		// Skew the input space to determine which simplex cell we're in
		double s = (xin + yin + zin) * F3; // Very nice and simple skew factor for 3D

		int i = fastFloor(xin + s);
		int j = fastFloor(yin + s);
		int k = fastFloor(zin + s);

		double t = (i + j + k) * G3;

		// Unskew the cell origin back to (x,y,z) space
		double X0 = i - t;
		double Y0 = j - t;
		double Z0 = k - t;

		// The x,y,z distances from the cell origin
		double x0 = xin - X0;
		double y0 = yin - Y0;
		double z0 = zin - Z0;

		// For the 3D case, the simplex shape is a slightly irregular tetrahedron.
		// Determine which simplex we are in.

		// Offsets for second corner of simplex in (i,j,k) coords
		int i1, j1, k1;

		// Offsets for third corner of simplex in (i,j,k) coords
		int i2, j2, k2;

		if (x0 >= y0)
		{
			if (y0 >= z0)
			{
				// X Y Z order
				i1 = 1;
				j1 = 0;
				k1 = 0;
				i2 = 1;
				j2 = 1;
				k2 = 0;
			}
			else if (x0 >= z0)
			{
				// X Z Y order
				i1 = 1;
				j1 = 0;
				k1 = 0;
				i2 = 1;
				j2 = 0;
				k2 = 1;
			}
			else
			{
				// Z X Y order
				i1 = 0;
				j1 = 0;
				k1 = 1;
				i2 = 1;
				j2 = 0;
				k2 = 1;
			}
		}
		else
		{
			if (y0 < z0)
			{
				// Z Y X order
				i1 = 0;
				j1 = 0;
				k1 = 1;
				i2 = 0;
				j2 = 1;
				k2 = 1;
			}
			else if (x0 < z0)
			{
				// Y Z X order
				i1 = 0;
				j1 = 1;
				k1 = 0;
				i2 = 0;
				j2 = 1;
				k2 = 1;
			}
			else
			{
				// Y X Z order
				i1 = 0;
				j1 = 1;
				k1 = 0;
				i2 = 1;
				j2 = 1;
				k2 = 0;
			}
		}

		// A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
		// a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and
		// a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where
		// c = 1/6.

		// Offsets for second corner in (x,y,z) coords
		double x1 = x0 - i1 + G3;
		double y1 = y0 - j1 + G3;
		double z1 = z0 - k1 + G3;

		// Offsets for third corner in (x,y,z) coords
		double x2 = x0 - i2 + 2.0 * G3;
		double y2 = y0 - j2 + 2.0 * G3;
		double z2 = z0 - k2 + 2.0 * G3;

		// Offsets for last corner in (x,y,z) coords
		double x3 = x0 - 1.0 + 3.0 * G3;
		double y3 = y0 - 1.0 + 3.0 * G3;
		double z3 = z0 - 1.0 + 3.0 * G3;

		// Work out the hashed gradient indices of the four simplex corners
		int ii = i & 255;
		int jj = j & 255;
		int kk = k & 255;

		int gi0 = this.permMod12[ii + this.perm[jj + this.perm[kk]]];
		int gi1 = this.permMod12[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1]]];
		int gi2 = this.permMod12[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2]]];
		int gi3 = this.permMod12[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1]]];

		// Calculate the contribution from the four corners
		double t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;

		if (t0 < 0.0)
		{
			n0 = 0.0;
		}
		else
		{
			t0 *= t0;
			n0 = t0 * t0 * dot(GRAD3[gi0], x0, y0, z0);
		}

		double t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;

		if (t1 < 0.0)
		{
			n1 = 0.0;
		}
		else
		{
			t1 *= t1;
			n1 = t1 * t1 * dot(GRAD3[gi1], x1, y1, z1);
		}

		double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;

		if (t2 < 0.0)
		{
			n2 = 0.0;
		}
		else
		{
			t2 *= t2;
			n2 = t2 * t2 * dot(GRAD3[gi2], x2, y2, z2);
		}

		double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;

		if (t3 < 0.0)
		{
			n3 = 0.0;
		}
		else
		{
			t3 *= t3;
			n3 = t3 * t3 * dot(GRAD3[gi3], x3, y3, z3);
		}

		// Add contributions from each corner to get the final noise value.
		// The result is scaled to stay just inside [-1,1]
		return 32.0 * (n0 + n1 + n2 + n3);
	}

	// This method is a *lot* faster than using (int)Math.floor(x)
	private static int fastFloor(double x)
	{
		int xi = (int) x;

		return x < xi ? xi - 1 : xi;
	}

	private static double dot(Grad g, double x, double y)
	{
		return g.x * x + g.y * y;
	}

	private static double dot(Grad g, double x, double y, double z)
	{
		return g.x * x + g.y * y + g.z * z;
	}

	// Inner class to speed upp gradient computations
	// (In Java, array access is a lot slower than member access)
	private static class Grad
	{
		double x, y, z;

		Grad(double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}