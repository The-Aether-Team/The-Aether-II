package com.gildedgames.aether.common.world.aether.island;

public class PoissonDiskSampling
{

	/*public static List<Point> generate(Random rand, int width, int height, double minDist, int newPointsCount)
	{
		int cellSize = (int) (minDist / Math.sqrt(2));

		PointGrid grid = new PointGrid((int) Math.ceil(width / cellSize), (int) Math.ceil(height / cellSize));

		RandomQueue<Point> processList = new RandomQueue<>();

		List<Point> samplePoints = Lists.newArrayList();

		Point firstPoint = new Point(rand.nextInt(width), rand.nextInt(height));

		processList.push(firstPoint);
		samplePoints.add(firstPoint);
		grid.set(firstPoint, realToGridX(firstPoint, cellSize), realToGridY(firstPoint, cellSize));

		while (!processList.isEmpty())
		{
			Point point = processList.pop();

			for (int i = 0; i < newPointsCount; i++)
			{
				Point newPoint = generateRandomPointAround(rand, point, minDist);

				if (isInsideArea(newPoint, width, height))
				{

				}
			}
		}
	}

	private static boolean isInsideArea(Point point, int width, int height)
	{
		return point.x <= width && point.y <= height && point.x >= 0 && point.y >= 0;
	}

	private static int realToGridX(Point point, int cellSize)
	{
		return (int)(point.x / cellSize);
	}

	private static int realToGridY(Point point, int cellSize)
	{
		return (int)(point.y / cellSize);
	}

	private static Point generateRandomPointAround(Random rand, Point point, double minDist)
	{
		double r1 = rand.nextDouble();
		double r2 = rand.nextDouble();

		double radius = minDist * (r1 + 1.0);

		double angle = 2.0 * Math.PI * r2;

		double newX = point.x + radius * Math.cos(angle);
		double newY = point.y + radius * Math.sin(angle);

		return new Point(newX, newY);
	}

	private static boolean inNeighbourhood(PointGrid grid, Point point, double minDist, int cellSize)
	{
		int gridX = realToGridX(point, cellSize);
		int gridY = realToGridY(point, cellSize);

		cellsAroundPoint = squareAroundPoint
	}*/

}
