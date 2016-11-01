package com.gildedgames.aether.common.world.util.delaunay_triangulation;

import java.util.Comparator;

public class ConnectionComparer implements Comparator<Connection>
{

	@Override
	public int compare(Connection arg0, Connection arg1)
	{
		return arg0.getWeight() - arg1.getWeight();
	}

}
