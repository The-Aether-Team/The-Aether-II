package com.gildedgames.orbis.common.util;

import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RegionHelper
{
	public static IShape getIntersectingWorldShapeGlobal(final Class<? extends IShape> shapeType, final IShape shape, final World world)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		final IShape found = manager.getGroup(0).getIntersectingShape(shapeType, shape);

		if (found != null)
		{
			return found;
		}

		return null;
	}

	public static IShape getIntersectingWorldShapeGlobal(final IShape shape, final World world)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		final IShape found = manager.getGroup(0).getIntersectingShape(shape);

		if (found != null)
		{
			return found;
		}

		return null;
	}

	public static IShape getIntersectingWorldShapeGlobal(final BlockPos pos, final World world)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(world);

		final IShape found = manager.getGroup(0).getIntersectingShape(pos);

		if (found != null)
		{
			return found;
		}

		return null;
	}
}
