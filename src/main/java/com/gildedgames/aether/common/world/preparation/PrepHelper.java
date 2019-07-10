package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.preparation.IPrepManager;
import com.gildedgames.aether.api.world.preparation.IPrepSector;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class PrepHelper
{
	/**
	 * Helper method which returns the {@link IPrepManager) for a world, or throws
	 * an {@link RuntimeException} if it does not exist.
	 *
	 * @param world The world
	 * @return The {@link IPrepManager} belonging to the world
	 */
	@Nullable
	public static IPrepManager getManager(World world)
	{
		IPrepManager access = null;

		if (world.hasCapability(CapabilitiesAether.PREP_MANAGER, null))
		{
			access = world.getCapability(CapabilitiesAether.PREP_MANAGER, null);
		}

		return access;
	}

	public static IPrepSector getSector(World world, int chunkX, int chunkY)
	{
		IPrepManager manager = PrepHelper.getManager(world);

		if (manager == null)
		{
			return null;
		}

		try
		{
			return manager.getAccess().provideSectorForChunk(chunkX, chunkY, false).get();
		}
		catch (InterruptedException | ExecutionException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static boolean isSectorLoaded(World world, int chunkX, int chunkY)
	{
		IPrepManager manager = PrepHelper.getManager(world);

		return isSectorLoaded(manager, chunkX, chunkY);
	}

	public static boolean isSectorLoaded(IPrepManager manager, int chunkX, int chunkY)
	{
		Optional<IPrepSector> sector = manager.getAccess().getLoadedSectorForChunk(chunkX, chunkY);

		return sector.isPresent();
	}

}
