package com.gildedgames.aether.common.world.biomes.irradiated_forests;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.common.math.delaunay.Point;
import com.gildedgames.aether.common.math.delaunay.Rectangle;
import com.gildedgames.aether.common.math.delaunay.Site;
import com.gildedgames.aether.common.math.delaunay.Voronoi;
import com.gildedgames.aether.common.math.voronoi.VoronoiGraphUtils;
import com.gildedgames.orbis.lib.util.ChunkMap;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class IrradiatedForestsData implements NBT
{
	private static final int NUM_LLOYD_RELAXATIONS = 1;

	private final ChunkMap<List<CrackLineSegment>> cracks = new ChunkMap<>();

	private Voronoi voronoi;

	private int crackPoints, islandWidth, islandLength;

	private long seed;

	private BlockPos min;

	private IrradiatedForestsData()
	{

	}

	public IrradiatedForestsData(final int crackPoints, final long seed, IIslandBounds bounds)
	{
		this.crackPoints = crackPoints;
		this.seed = seed;
		this.islandWidth = bounds.getWidth();
		this.islandLength = bounds.getLength();
		this.min = new BlockPos(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ());
	}

	public boolean checkInit()
	{
		boolean generate = this.voronoi == null;

		if (generate)
		{
			final Random rand = new Random(this.seed);

			this.voronoi = new Voronoi(this.crackPoints, rand, new Rectangle(0, 0, this.islandWidth, this.islandLength));
			this.voronoi = VoronoiGraphUtils.lloydRelax(this.voronoi, NUM_LLOYD_RELAXATIONS);

			final List<Site> sitesUsed = Lists.newArrayList();

			Site centerSite = null;

			final double radiusX = (this.islandWidth / 2.0);
			final double radiusZ = (this.islandLength / 2.0);

			double oldDist = Double.POSITIVE_INFINITY;

			for (final Site s : this.voronoi.getSites())
			{
				final double newDistX = Math.abs((radiusX - s.x) * (1.0 / radiusX));
				final double newDistZ = Math.abs((radiusZ - s.y) * (1.0 / radiusZ));

				final double newDist = Math.sqrt(newDistX * newDistX + newDistZ * newDistZ);

				if (newDist < oldDist)
				{
					centerSite = s;

					oldDist = newDist;
				}
			}

			if (centerSite == null)
			{
				throw new IllegalStateException("Couldn't find center site");
			}

			for (int crackIt = 0; crackIt < 4; crackIt++)
			{
				Site site = centerSite;

				loop:
				for (int i = 0; i < 20; i++)
				{
					final Site[] sites = this.voronoi.neighborSitesForSite(site);

					Site end = null;

					int sitesChecked = 0;

					while (end == null || sitesUsed.contains(end))
					{
						end = sites[rand.nextInt(sites.length)];

						sitesChecked++;

						if (sitesChecked > sites.length)
						{
							break loop;
						}
					}

					final Point a = site;
					final Point b = end;

					final Point[] divided = this.subdivideFractal(a, b, 4, 0.8, rand);

					for (int j = 0; j < divided.length - 1; j++)
					{
						final Point div1 = divided[j];
						final Point div2 = divided[j + 1];

						this.addCrackLine(div1.x, div1.y, div2.x, div2.y);
					}

					sitesUsed.add(site);

					site = end;
				}
			}
		}

		return generate;
	}

	private static final double ANGLE_COS = Math.cos(Math.PI / 2.0);
	private static final double ANGLE_SIN = Math.sin(Math.PI / 2.0);

	private Point[] subdivideFractal(final Point a, final Point b, final int subdivisionCount, final double amplitude,
			final Random rand)
	{
		Point[] values = new Point[] { a, b };

		for (int i = 0; i < subdivisionCount; i++)
		{
			final Point[] divided = new Point[(values.length - 1) * 3];

			for (int j = 0; j < values.length - 1; j++)
			{
				final Point div1 = values[j];
				final Point div2 = values[j + 1];

				double centerX = Point.lerp(div1.x, div2.x, 0.5);
				double centerY = Point.lerp(div1.y, div2.y, 0.5);

				double cX = centerX + (div1.x - centerX) * ANGLE_COS - (div1.y - centerY) * ANGLE_SIN;
				double cY = centerY + (div1.x - centerX) * ANGLE_SIN - (div1.y - centerY) * ANGLE_COS;

				double dX = centerX + (div2.x - centerX) * ANGLE_COS - (div2.y - centerY) * ANGLE_SIN;
				double dY = centerY + (div2.x - centerX) * ANGLE_SIN - (div2.y - centerY) * ANGLE_COS;

				final double zAlpha = 0.1 + ((amplitude - 0.1) * rand.nextDouble());
				final double zX = Point.lerp(cX, dX, zAlpha);
				final double zY = Point.lerp(cY, dY, zAlpha);

				final double lerpedX = Point.lerp(centerX, zX, 0.5);
				final double lerpedY = Point.lerp(centerY, zY, 0.5);

				final Point lerped = new Point(lerpedX, lerpedY);

				final int n = j * 3;

				divided[n] = div1;
				divided[n + 1] = lerped;
				divided[n + 2] = div2;
			}

			values = divided;
		}

		return values;
	}

	@Override
	public void write(final CompoundNBT tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.putLong("seed", this.seed);
		tag.putInt("crackPoints", this.crackPoints);
		tag.putInt("width", this.islandWidth);
		tag.putInt("length", this.islandLength);
		funnel.setPos("min", this.min);
	}

	@Override
	public void read(final CompoundNBT tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.seed = tag.getLong("seed");
		this.crackPoints = tag.getInt("crackPoints");
		this.islandWidth = tag.getInt("width");
		this.islandLength = tag.getInt("length");
		this.min = funnel.getPos("min");
	}

	public Collection<CrackLineSegment> getCracksInRegion(int chunkX, int chunkZ, int radius)
	{
		int minX = (chunkX) - radius;
		int minZ = (chunkZ) - radius;

		int maxX = (chunkX) + radius + 1;
		int maxZ = (chunkZ) + radius + 1;

		HashSet<CrackLineSegment> list = new HashSet<>();

		for (int x = minX; x < maxX; x++)
		{
			for (int z = minZ; z < maxZ; z++)
			{
				List<CrackLineSegment> lines = this.cracks.get(x - (this.min.getX() >> 4), z - (this.min.getZ() >> 4));

				if (lines != null)
				{
					list.addAll(lines);
				}
			}
		}

		return list;
	}

	private void addCrackLine(double x0, double y0, double x1, double y1)
	{
		CrackLineSegment segment = new CrackLineSegment(
				(this.min.getX() + x0), (this.min.getX() + x1),
				(this.min.getZ() + y0), (this.min.getZ() + y1)
		);

		x0 /= 16.0;
		x1 /= 16.0;
		y0 /= 16.0;
		y1 /= 16.0;

		double dx = Math.abs(x1 - x0);
		double dy = Math.abs(y1 - y0);

		int x = (int) (Math.floor(x0) / 1.0);
		int y = (int) (Math.floor(y0) / 1.0);

		double dt_dx = 1.0 / dx;
		double dt_dy = 1.0 / dy;

		double t = 0;

		int n = 1;
		int x_inc, y_inc;
		double t_next_vertical, t_next_horizontal;

		if (dx == 0)
		{
			x_inc = 0;
			t_next_horizontal = dt_dx; // infinity
		}
		else if (x1 > x0)
		{
			x_inc = 1;
			n += (int) (Math.floor(x1)) - x;
			t_next_horizontal = (Math.floor(x0) + 1 - x0) * dt_dx;
		}
		else
		{
			x_inc = -1;
			n += x - (int) (Math.floor(x1));
			t_next_horizontal = (x0 - Math.floor(x0)) * dt_dx;
		}

		if (dy == 0)
		{
			y_inc = 0;
			t_next_vertical = dt_dy; // infinity
		}
		else if (y1 > y0)
		{
			y_inc = 1;
			n += (int) (Math.floor(y1)) - y;
			t_next_vertical = (Math.floor(y0) + 1 - y0) * dt_dy;
		}
		else
		{
			y_inc = -1;
			n += y - (int) (Math.floor(y1));
			t_next_vertical = (y0 - Math.floor(y0)) * dt_dy;
		}

		while (n > 0)
		{
			List<CrackLineSegment> list = this.cracks.get(x, y);

			if (list == null)
			{
				list = new ArrayList<>();

				this.cracks.put(x, y, list);
			}

			list.add(segment);

			if (t_next_vertical < t_next_horizontal)
			{
				y += y_inc;
				t = t_next_vertical;
				t_next_vertical += dt_dy;
			}
			else
			{
				x += x_inc;
				t = t_next_horizontal;
				t_next_horizontal += dt_dx;
			}

			--n;
		}

	}
}
