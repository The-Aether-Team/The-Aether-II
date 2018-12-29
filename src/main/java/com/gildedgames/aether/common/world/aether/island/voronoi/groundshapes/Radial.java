package com.gildedgames.aether.common.world.aether.island.voronoi.groundshapes;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Rectangle;

import java.util.Random;

/**
 *
 */
public class Radial implements HeightAlgorithm
{

	private final double ISLAND_FACTOR;  // 1.0 means no small islands; 2.0 leads to a lot

	private final int bumps;

	private final double startAngle;

	private final double dipAngle;

	private final double dipWidth;

	/**
	 *
	 * @param ISLAND_FACTOR 1.0 means no small islands, 2.0 leads to a lot
	 * @param bumps
	 * @param startAngle
	 * @param dipAngle
	 * @param dipWidth
	 */
	public Radial(final double ISLAND_FACTOR, final int bumps, final double startAngle, final double dipAngle, final double dipWidth)
	{
		this.ISLAND_FACTOR = ISLAND_FACTOR;
		this.bumps = bumps;
		this.startAngle = startAngle;
		this.dipAngle = dipAngle;
		this.dipWidth = dipWidth;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isWater(Point p, final Rectangle bounds, final Random r)
	{
		p = new Point(2 * (p.x / bounds.width - 0.5), 2 * (p.y / bounds.height - 0.5));

		final double angle = Math.atan2(p.y, p.x);
		final double length = 0.5 * (Math.max(Math.abs(p.x), Math.abs(p.y)) + p.length());

		double r1 = 0.5 + 0.40 * Math.sin(this.startAngle + this.bumps * angle + Math.cos((this.bumps + 3) * angle));
		double r2 = 0.7 - 0.20 * Math.sin(this.startAngle + this.bumps * angle - Math.sin((this.bumps + 2) * angle));
		if (Math.abs(angle - this.dipAngle) < this.dipWidth
				|| Math.abs(angle - this.dipAngle + 2 * Math.PI) < this.dipWidth
				|| Math.abs(angle - this.dipAngle - 2 * Math.PI) < this.dipWidth)
		{
			r1 = r2 = 0.2;
		}
		return !(length < r1 || (length > r1 * this.ISLAND_FACTOR && length < r2));
	}
}