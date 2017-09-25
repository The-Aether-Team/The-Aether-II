package com.gildedgames.aether.common.capabilities.world.sectors;

import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.ChunkMap;
import com.gildedgames.aether.common.world.aether.island.data.IslandSector;
import com.gildedgames.aether.common.world.aether.island.data.IslandSectorFactory;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Implementation of {@link ISectorAccess} that uses a flat-file database. Not thread-safe!
 */
public class IslandSectorAccessFlatFile implements ISectorAccess
{
	private final World world;

	private final File folder;

	private final ChunkMap<ISector> loaded = new ChunkMap<>();

	private final Queue<ISector> dirty = new ArrayDeque<>();

	public IslandSectorAccessFlatFile(final World world, final File folder)
	{
		this.world = world;
		this.folder = folder;

		if (this.folder.exists())
		{
			if (!this.folder.isDirectory())
			{
				throw new RuntimeException("Storage directory is a file");
			}
		}
		else if (!this.folder.mkdirs())
		{
			throw new RuntimeException("Failed to create storage directory");
		}
	}

	@Override
	public Optional<ISector> getLoadedSector(final int chunkX, final int chunkZ)
	{
		final int sectorX = Math.floorDiv(chunkX, IslandSector.CHUNK_SIZE);
		final int sectorZ = Math.floorDiv(chunkZ, IslandSector.CHUNK_SIZE);

		return Optional.ofNullable(this.loaded.get(sectorX, sectorZ));
	}

	@Override
	public ISector provideSector(final int chunkX, final int chunkZ)
	{
		final int sectorX = Math.floorDiv(chunkX, IslandSector.CHUNK_SIZE);
		final int sectorZ = Math.floorDiv(chunkZ, IslandSector.CHUNK_SIZE);

		// Check if the sector is already loaded
		if (this.loaded.containsKey(sectorX, sectorZ))
		{
			return this.loaded.get(sectorX, sectorZ);
		}

		// Attempt to load sector from disk
		ISector sector = this.readSectorFromDisk(sectorX, sectorZ);

		// Generate the sector if it didn't exist on disk
		if (sector == null)
		{
			sector = this.generateSector(sectorX, sectorZ);
		}

		// Store the sector in memory
		this.loaded.put(sectorX, sectorZ, sector);

		// Queue the sector for saving if we just generated it
		if (sector.isDirty())
		{
			this.dirty.add(sector);
		}

		// Begin watching the sector
		sector.addWatchingChunk(chunkX, chunkZ);

		return sector;
	}

	@Override
	public void onChunkLoaded(final int chunkX, final int chunkZ)
	{
		final int sectorX = Math.floorDiv(chunkX, IslandSector.CHUNK_SIZE);
		final int sectorZ = Math.floorDiv(chunkZ, IslandSector.CHUNK_SIZE);

		// Check if the sector is already loaded
		if (this.loaded.containsKey(sectorX, sectorZ))
		{
			return;
		}

		// Attempt to load sector from disk
		final ISector sector = this.readSectorFromDisk(sectorX, sectorZ);

		if (sector != null)
		{
			// Store the sector in memory
			this.loaded.put(sectorX, sectorZ, sector);

			// Begin watching the sector
			sector.addWatchingChunk(chunkX, chunkZ);
		}
	}

	@Override
	public void onChunkUnloaded(final int chunkX, final int chunkZ)
	{
		final int sectorX = Math.floorDiv(chunkX, IslandSector.CHUNK_SIZE);
		final int sectorZ = Math.floorDiv(chunkZ, IslandSector.CHUNK_SIZE);

		final ISector sector = this.loaded.get(sectorX, sectorZ);

		if (sector != null)
		{
			sector.removeWatchingChunk(chunkX, chunkZ);

			if (!sector.hasWatchers())
			{
				// If the sector is dirty, queue it for saving, otherwise drop it
				if (sector.isDirty())
				{
					this.dirty.add(sector);
				}
				else
				{
					this.loaded.remove(sectorX, sectorZ);
				}
			}
		}
	}

	/**
	 * Saves all dirty sectors to the disk synchronously and unloads
	 * those of which have no players watching them.
	 */
	@Override
	public void flush()
	{
		while (!this.dirty.isEmpty())
		{
			final ISector sector = this.dirty.remove();

			this.writeSectorToDisk(sector);

			sector.markClean();

			// If the sector has no watchers after flushing, remove it from cache
			if (!sector.hasWatchers())
			{
				synchronized (this.loaded)
				{
					this.loaded.remove(sector.getX(), sector.getZ());
				}
			}
		}
	}

	/**
	 * Writes a sector to the disk synchronously.
	 *
	 * @param sector The sector to write
	 */
	private void writeSectorToDisk(final ISector sector)
	{
		final File file = this.getSectorFile(sector.getX(), sector.getZ());

		try (FileOutputStream out = new FileOutputStream(file))
		{
			this.writeSectorToStream(sector, out);
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to save sector to disk", e);
		}
	}

	/**
	 * Reads a sector from the disk synchronously
	 *
	 * @param sectorX The sector's x-coordinate
	 * @param sectorZ The sector's z-coordinate
	 * @return An {@link ISector}, null if it doesn't exist
	 */
	private ISector readSectorFromDisk(final int sectorX, final int sectorZ)
	{
		final File file = this.getSectorFile(sectorX, sectorZ);

		if (!file.exists())
		{
			return null;
		}

		try (FileInputStream stream = new FileInputStream(file))
		{
			final ISector sector = this.readSectorFromStream(stream);

			if (sector.getX() != sectorX || sector.getZ() != sectorZ)
			{
				throw new IOException("Sector has wrong coordinates on disk");
			}

			return sector;
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to read sector from disk", e);
		}

		return null;
	}

	/**
	 * Reads an {@link ISector} from an {@link InputStream}.
	 *
	 * @param stream The {@link InputStream} containing the sector's data
	 * @return The {@link ISector} read from the stream
	 *
	 * @throws IOException If an I/O exception occurs while reading
	 */
	private ISector readSectorFromStream(final InputStream stream) throws IOException
	{
		final NBTTagCompound tag = CompressedStreamTools.readCompressed(stream);

		return new IslandSector(this.world, tag);
	}

	/**
	 * Writes an {@link ISector} to an {@link OutputStream}.
	 *
	 * @param sector The sector to write
	 * @param out The {@link OutputStream} to write to
	 *
	 * @throws IOException If an I/O exception occurs while writing
	 */
	private void writeSectorToStream(final ISector sector, final OutputStream out) throws IOException
	{
		final NBTTagCompound tag = new NBTTagCompound();
		sector.write(tag);

		CompressedStreamTools.writeCompressed(tag, out);
	}

	/**
	 * Generates a sector for the specified coordinates.
	 *
	 * @param sectorX The sector's x-coordinate
	 * @param sectorZ The sector's z-coordinate
	 * @return The generated {@link ISector}
	 */
	@Nonnull
	private ISector generateSector(final int sectorX, final int sectorZ)
	{
		final long seed = this.world.getSeed() ^ ((long) sectorX * 341873128712L + (long) sectorZ * 132897987541L);

		final ISector sector = IslandSectorFactory.create(this.world, sectorX, sectorZ, seed);
		sector.markDirty();

		return sector;
	}

	/**
	 * Returns the location of a sector on disk by it's coordinates.
	 *
	 * @param sectorX The sector's x-coordinate
	 * @param sectorZ The sector's z-coordinate
	 * @return A {@link File} pointing to the sector on disk
	 */
	private File getSectorFile(final int sectorX, final int sectorZ)
	{
		return new File(this.folder, "sector." + sectorX + "." + sectorZ + ".nbt");
	}

	public static class Storage implements Capability.IStorage<ISectorAccess>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(final Capability<ISectorAccess> capability, final ISectorAccess instance, final EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(final Capability<ISectorAccess> capability, final ISectorAccess instance, final EnumFacing side, final NBTBase nbt)
		{

		}
	}
}
