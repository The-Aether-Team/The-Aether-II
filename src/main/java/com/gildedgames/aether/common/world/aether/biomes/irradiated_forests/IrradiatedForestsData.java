package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Rectangle;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Voronoi;
import com.gildedgames.aether.common.world.aether.island.voronoi.VoronoiGraphUtils;
import com.gildedgames.orbis_api.util.LineHelp;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.NBT;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class IrradiatedForestsData implements NBT
{
	private static final int NUM_LLOYD_RELAXATIONS = 1;

	private final Long2ObjectOpenHashMap<CrackChunk> cracks = new Long2ObjectOpenHashMap<>();

	private Voronoi voronoi;

	private int crackPoints, islandWidth, islandLength;

	private long seed;

	private BlockPos min;

	private IrradiatedForestsData()
	{

	}

	public IrradiatedForestsData(final int crackPoints, final long seed, final int islandWidth, final int islandLength, final BlockPos min)
	{
		this.crackPoints = crackPoints;
		this.seed = seed;
		this.islandWidth = islandWidth;
		this.islandLength = islandLength;
		this.min = min;
	}

	public void checkInit()
	{
		if (this.voronoi == null)
		{
			final Random rand = new Random(this.seed);

			this.voronoi = VoronoiGraphUtils
					.lloydRelax(new Voronoi(this.crackPoints, rand, new Rectangle(0, 0, this.islandWidth, this.islandLength)),
							NUM_LLOYD_RELAXATIONS);

			final List<Point> pointsUsed = Lists.newArrayList();

			Point centerSite = null;

			final double radiusX = (this.islandWidth / 2.0);
			final double radiusZ = (this.islandLength / 2.0);

			double oldDist = Integer.MAX_VALUE;

			for (final Point s : this.voronoi.siteCoords())
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

			for (int crackIt = 0; crackIt < 4; crackIt++)
			{
				Point site = centerSite;

				loop:
				for (int i = 0; i < 20; i++)
				{
					final List<Point> sites = this.voronoi.neighborSitesForSite(site);

					Point end = null;
					int sitesChecked = 0;

					while (end == null || pointsUsed.contains(end))
					{
						end = sites.get(rand.nextInt(sites.size()));

						sitesChecked++;

						if (sitesChecked > sites.size())
						{
							break loop;
						}
					}

					final Point a = site;
					final Point b = end;

					final List<Point> divided = this.subdivideFractal(a, b, 4, 0.8, rand);

					for (int j = 0; j <= divided.size(); j++)
					{
						final Point div1 = divided.get(j);
						final int index2 = j + 1;

						if (index2 >= divided.size())
						{
							break;
						}

						final Point div2 = divided.get(index2);

						final Iterable<BlockPos.MutableBlockPos> line = LineHelp
								.createLinePositions(2, new BlockPos(div1.x + this.min.getX(), 0, div1.y + this.min.getZ()),
										new BlockPos(div2.x + this.min.getX(), 0, div2.y + this.min.getZ()));

						for (final BlockPos.MutableBlockPos p : line)
						{
							if (this.getCrackPos(p.getX(), p.getZ()) == null)
							{
								final CrackPos c = new CrackPos(p.getX(), p.getZ(), 1);

								this.setCrackPos(c, c.getX(), c.getZ());
							}
						}
					}

					pointsUsed.add(site);
					site = end;
				}
			}
		}
	}

	private List<Point> subdivideFractal(final Point a, final Point b, final int subdivisionCount, final double amplitude,
			final Random rand)
	{
		List<Point> values = Lists.newArrayList(a, b);

		for (int i = 0; i < subdivisionCount; i++)
		{
			final List<Point> divided = Lists.newArrayList();

			for (int j = 0; j < values.size(); j++)
			{
				final Point div1 = values.get(j);

				final int index2 = j + 1;

				if (index2 >= values.size())
				{
					break;
				}

				final Point div2 = values.get(index2);

				final double angle = Math.PI / 2.0;

				final Point center = Point.interpolate(div1, div2, 0.5);

				double newX = center.x + (div1.x - center.x) * Math.cos(angle) - (div1.y - center.y) * Math.sin(angle);
				double newY = center.y + (div1.x - center.x) * Math.sin(angle) - (div1.y - center.y) * Math.cos(angle);

				final Point c = new Point(newX, newY);

				newX = center.x + (div2.x - center.x) * Math.cos(angle) - (div2.y - center.y) * Math.sin(angle);
				newY = center.y + (div2.x - center.x) * Math.sin(angle) - (div2.y - center.y) * Math.cos(angle);

				final Point d = new Point(newX, newY);

				final Point lerped = Point
						.interpolate(Point.interpolate(div1, div2, 0.5),
								Point.interpolate(c, d, 0.1 + ((amplitude - 0.1) * rand.nextDouble())),
								0.5);

				divided.add(div1);
				divided.add(lerped);
				divided.add(div2);
			}

			values = divided;
		}

		return values;
	}

	private CrackPos getCrackPos(final int x, final int z)
	{
		final long index = ChunkPos.asLong(x >> 4, z >> 4);

		CrackChunk chunk = this.cracks.get(index);

		if (chunk == null)
		{
			chunk = new CrackChunk();

			this.cracks.put(index, chunk);
		}

		return chunk.get(Math.abs(x % 16), Math.abs(z % 16));
	}

	private void setCrackPos(final CrackPos pos, final int x, final int z)
	{
		final long index = ChunkPos.asLong(x >> 4, z >> 4);

		CrackChunk chunk = this.cracks.get(index);

		if (chunk == null)
		{
			chunk = new CrackChunk();

			this.cracks.put(index, chunk);
		}

		chunk.set(pos, Math.abs(x % 16), Math.abs(z % 16));
	}

	@Nullable
	public CrackChunk getCracks(final int chunkX, final int chunkZ)
	{ ;
		return this.cracks.get(ChunkPos.asLong(chunkX, chunkZ));
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.setLong("seed", this.seed);
		tag.setInteger("crackPoints", this.crackPoints);
		tag.setInteger("width", this.islandWidth);
		tag.setInteger("length", this.islandLength);
		funnel.setPos("min", this.min);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.seed = tag.getLong("seed");
		this.crackPoints = tag.getInteger("crackPoints");
		this.islandWidth = tag.getInteger("width");
		this.islandLength = tag.getInteger("length");
		this.min = funnel.getPos("min");
	}
}
