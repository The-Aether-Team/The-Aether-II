package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

public class CrackLineSegment
{
	public final double x1, x2, y1, y2;

	public CrackLineSegment(double x1, double x2, double y1, double y2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public double distanceToPoint(double x, double y)
	{
		double a = x - this.x1;
		double b = y - this.y1;
		double c = this.x2 - this.x1;
		double d = this.y2 - this.y1;

		double dot = a * c + b * d;
		double length = c * c + d * d;

		double param = dot / length;

		double xx, yy;

		if (param < 0)
		{
			xx = this.x1;
			yy = this.y1;
		}
		else if (param > 1)
		{
			xx = this.x2;
			yy = this.y2;
		}
		else
		{
			xx = this.x1 + param * c;
			yy = this.y1 + param * d;
		}

		double dx = x - xx;
		double dy = y - yy;

		return Math.sqrt(dx * dx + dy * dy);
	}


	public boolean intersects(double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY)
	{
		double minX = this.x1;
		double maxX = this.x2;

		if (this.x1 > this.x2)
		{
			minX = this.x2;
			maxX = this.x1;
		}

		if (maxX > boundsMaxX)
		{
			maxX = boundsMaxX;
		}

		if (minX < boundsMinX)
		{
			minX = boundsMinX;
		}

		if (minX > maxX)
		{
			return false;
		}

		double minY = this.y1;
		double maxY = this.y2;

		double dx = this.x2 - this.x1;

		if (Math.abs(dx) > 0.0000001)
		{
			double a = (this.y2 - this.y1) / dx;
			double b = this.y1 - a * this.x2;

			minY = a * minX + b;
			maxY = a * maxX + b;
		}

		if (minY > maxY)
		{
			double tmp = maxY;

			maxY = minY;
			minY = tmp;
		}

		if (maxY > boundsMaxY)
		{
			maxY = boundsMaxY;
		}

		if (minY < boundsMinY)
		{
			minY = boundsMinY;
		}

		return !(minY > maxY);
	}
}
