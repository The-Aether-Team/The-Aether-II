package com.gildedgames.aether.island_gen;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Voronoi;
import com.gildedgames.aether.common.world.aether.island.voronoi.Center;
import com.gildedgames.aether.common.world.aether.island.voronoi.VoronoiGraph;
import com.gildedgames.aether.common.world.aether.island.voronoi.groundshapes.HeightAlgorithm;

import java.awt.*;
import java.util.Random;

public class IslandGraph extends VoronoiGraph<Color>
{
	public IslandGraph(final Voronoi v, final int numLloydRelaxations, final Random r,
			final HeightAlgorithm algorithm)
	{
		super(v, numLloydRelaxations, r, algorithm);
	}

	@Override
	protected Enum getBiome(final Center p)
	{
		if (p.ocean)
		{
			return ColorData.OCEAN;
		}
		else if (p.water)
		{
			if (p.elevation < 0.1)
			{
				return ColorData.MARSH;
			}
			if (p.elevation > 0.8)
			{
				return ColorData.ICE;
			}
			return ColorData.LAKE;
		}
		else if (p.coast)
		{
			return ColorData.BEACH;
		}
		else if (p.elevation > 0.8)
		{
			if (p.moisture > 0.50)
			{
				return ColorData.SNOW;
			}
			else if (p.moisture > 0.33)
			{
				return ColorData.TUNDRA;
			}
			else if (p.moisture > 0.16)
			{
				return ColorData.BARE;
			}
			else
			{
				return ColorData.SCORCHED;
			}
		}
		else if (p.elevation > 0.6)
		{
			if (p.moisture > 0.66)
			{
				return ColorData.TAIGA;
			}
			else if (p.moisture > 0.33)
			{
				return ColorData.SHRUBLAND;
			}
			else
			{
				return ColorData.TEMPERATE_DESERT;
			}
		}
		else if (p.elevation > 0.3)
		{
			if (p.moisture > 0.83)
			{
				return ColorData.TEMPERATE_RAIN_FOREST;
			}
			else if (p.moisture > 0.50)
			{
				return ColorData.TEMPERATE_DECIDUOUS_FOREST;
			}
			else if (p.moisture > 0.16)
			{
				return ColorData.GRASSLAND;
			}
			else
			{
				return ColorData.TEMPERATE_DESERT;
			}
		}
		else
		{
			if (p.moisture > 0.66)
			{
				return ColorData.TROPICAL_RAIN_FOREST;
			}
			else if (p.moisture > 0.33)
			{
				return ColorData.TROPICAL_SEASONAL_FOREST;
			}
			else if (p.moisture > 0.16)
			{
				return ColorData.GRASSLAND;
			}
			else
			{
				return ColorData.SUBTROPICAL_DESERT;
			}
		}
	}

	@Override
	protected Color getVisual(final Enum biome)
	{
		return ((ColorData) biome).color;
	}

	public enum ColorData
	{

		OCEAN(0x44447a), LAKE(0x336699), BEACH(0xa09077), SNOW(0xffffff),
		TUNDRA(0xbbbbaa), BARE(0x888888), SCORCHED(0x555555), TAIGA(0x99aa77),
		SHURBLAND(0x889977), TEMPERATE_DESERT(0xc9d29b),
		TEMPERATE_RAIN_FOREST(0x448855), TEMPERATE_DECIDUOUS_FOREST(0x679459),
		GRASSLAND(0x88aa55), SUBTROPICAL_DESERT(0xd2b98b), SHRUBLAND(0x889977),
		ICE(0x99ffff), MARSH(0x2f6666), TROPICAL_RAIN_FOREST(0x337755),
		TROPICAL_SEASONAL_FOREST(0x559944), COAST(0x33335a),
		LAKESHORE(0x225588), RIVER(0x225588);

		public Color color;

		ColorData(final int color)
		{
			this.color = new Color(color);
		}
	}
}
