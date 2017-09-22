package com.gildedgames.aether.api.world;

import com.gildedgames.aether.api.AetherCapabilities;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class IslandSectorHelper
{
	/**
	 * Helper method which returns the {@link ISectorAccess} for a world, or throws
	 * an {@link RuntimeException} if it does not exist.
	 *
	 * @param world The world
	 * @return The {@link ISectorAccess} belonging to the world
	 */
	@Nonnull
	public static ISectorAccess getAccess(World world)
	{
		ISectorAccess access = null;

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);
		}

		if (access == null)
		{
			throw new RuntimeException("World does not have ISectorAccess capability");
		}

		return access;
	}
}
