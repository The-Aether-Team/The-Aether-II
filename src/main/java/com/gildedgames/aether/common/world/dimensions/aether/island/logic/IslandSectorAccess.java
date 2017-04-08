package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.gildedgames.aether.common.world.util.ChunkMap;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public class IslandSectorAccess
{

	private ChunkMap<IslandSector> activeSectors = new ChunkMap<>();

	private final static IslandSectorAccess INST = new IslandSectorAccess();

	private IslandSectorAccess()
	{

	}

	@Deprecated
	// Static access?! -JS
	public static IslandSectorAccess inst()
	{
		return IslandSectorAccess.INST;
	}

	private static String createSectorKey(final int sectorX, final int sectorY)
	{
		return sectorX + "_" + sectorY;
	}

	// TODO: Needs to be changed to chunk hook format... -JS
	private static File createSectorFile(final int sectorX, final int sectorY)
	{
		final String sectorKey = IslandSectorAccess.createSectorKey(sectorX, sectorY);

		final File file = new File(AetherCore.getWorldDirectory(), "//data/island_sectors/" + sectorKey + ".dat");

		return file;
	}

	public int getSectorCoord(final int chunkCoord)
	{
		return (chunkCoord / IslandSector.CHUNK_WIDTH_PER_SECTOR) - (chunkCoord < 0 ? 1 : 0);
	}

	public IslandData getIslandIfOnlyOne(final World world, final BlockPos pos)
	{
		final List<IslandData> islandsToGen = this.getAllIslands(world, pos);

		final boolean oneIslandOnly = islandsToGen.size() == 1;

		return oneIslandOnly ? islandsToGen.get(0) : null;
	}

	public List<IslandData> getAllIslands(final World world, final BlockPos pos)
	{
		final int chunkX = pos.getX() >> 4;
		final int chunkZ = pos.getZ() >> 4;

		final IslandSector sector = IslandSectorAccess.inst().attemptToLoadSectorInChunk(world, chunkX, chunkZ);

		if (sector == null)
		{
			return null;
		}

		final List<IslandData> islandsToGen = Lists.newArrayList();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final List<IslandData> islands = sector.getIslandDataAtBlockPos(pos.getX() + x, pos.getZ() + z);

				if (islands.size() <= 0)
				{
					continue;
				}

				for (final IslandData data : islands)
				{
					if (!islandsToGen.contains(data))
					{
						islandsToGen.add(data);
					}
				}
			}
		}

		return islandsToGen;
	}

	public IslandSector attemptToLoadSectorInChunk(final World world, final int chunkX, final int chunkY)
	{
		final int sectorX = IslandSectorAccess.INST.getSectorCoord(chunkX);
		final int sectorY = IslandSectorAccess.INST.getSectorCoord(chunkY);

		final IslandSector sector = this.attemptToLoadSector(world, sectorX, sectorY);

		if (sector != null)
		{
			sector.trackLoadedChunk(chunkX, chunkY);
		}

		return sector;
	}

	public IslandSector attemptToLoadSectorInChunk(final World world, final int chunkX, final int chunkY, final long seedForNewSector)
	{
		final int sectorX = IslandSectorAccess.INST.getSectorCoord(chunkX);
		final int sectorY = IslandSectorAccess.INST.getSectorCoord(chunkY);

		final IslandSector sector = this.attemptToLoadSector(world, sectorX, sectorY, seedForNewSector);

		if (sector != null)
		{
			sector.trackLoadedChunk(chunkX, chunkY);
		}

		return sector;
	}

	private IslandSector attemptToLoadSector(final World world, final int sectorX, final int sectorY)
	{
		if (world.isRemote)
		{
			return null;
		}

		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return this.loadSectorFromMemory(world, sectorX, sectorY);
		}

		final IslandSector sector = this.loadSectorFromDisk(sectorX, sectorY);

		if (sector == null && this.wasSectorEverCreated(world, sectorX, sectorY))
		{
			final File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

			file.delete();
		}

		if (!this.wasSectorEverCreated(world, sectorX, sectorY) || sector == null)
		{
			return null;
		}

		this.addSectorToMemory(sector);

		return sector;
	}

	private IslandSector attemptToLoadSector(final World world, final int sectorX, final int sectorY, final long seedForNewSector)
	{
		if (world.isRemote)
		{
			return null;
		}

		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return this.loadSectorFromMemory(world, sectorX, sectorY);
		}

		IslandSector sector = this.loadSectorFromDisk(sectorX, sectorY);

		if (sector == null && this.wasSectorEverCreated(world, sectorX, sectorY))
		{
			final File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

			file.delete();
		}

		if (!this.wasSectorEverCreated(world, sectorX, sectorY) || sector == null)
		{
			sector = this.createSectorAndSaveToDisk(world, sectorX, sectorY, seedForNewSector);

			this.addSectorToMemory(sector);

			return sector;
		}

		this.addSectorToMemory(sector);

		return sector;
	}

	private boolean isSectorLoadedInMemory(final int sectorX, final int sectorY)
	{
		return this.activeSectors.containsKey(sectorX, sectorY);
	}

	public boolean wasSectorEverCreatedInChunk(final World world, final int chunkX, final int chunkY)
	{
		if (world.isRemote)
		{
			return false;
		}

		final int sectorX = IslandSectorAccess.INST.getSectorCoord(chunkX);
		final int sectorY = IslandSectorAccess.INST.getSectorCoord(chunkY);

		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return true;
		}

		return IslandSectorAccess.createSectorFile(sectorX, sectorY).exists();
	}

	private boolean wasSectorEverCreated(final World world, final int sectorX, final int sectorY)
	{
		if (world.isRemote)
		{
			return false;
		}

		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return true;
		}

		return IslandSectorAccess.createSectorFile(sectorX, sectorY).exists();
	}

	@Nullable
	private IslandSector loadSectorFromMemory(final World world, final int sectorX, final int sectorY)
	{
		if (world.isRemote)
		{
			return null;
		}

		if (this.activeSectors.containsKey(sectorX, sectorY))
		{
			return this.activeSectors.get(sectorX, sectorY);
		}

		AetherCore.LOGGER.debug("Something is attempting to load an Island Sector from memory before it has been loaded into memory! Please check if the sector has been loaded into memory first. If not, create the sector.");

		return null;
	}

	public void unloadSectorFromMemory(final World world, final IslandSector sector)
	{
		if (world.isRemote)
		{
			return;
		}

		if (!this.isSectorLoadedInMemory(sector.getSectorX(), sector.getSectorY()))
		{
			return;
		}

		this.saveSectorToDisk(world, sector);
		this.activeSectors.remove(sector.getSectorX(), sector.getSectorY());
	}

	public void saveSectorToDisk(final World world, final IslandSector sector)
	{
		if (world.isRemote)
		{
			return;
		}

		this.saveSectorToDisk(sector);
	}

	private void saveSectorToDisk(final IslandSector sector)
	{
		final File file = IslandSectorAccess.createSectorFile(sector.getSectorX(), sector.getSectorY());

		final NBTTagCompound tag = new NBTTagCompound();

		NBTHelper.fullySerialize("s", sector, tag);

		NBTHelper.writeNBTToFile(tag, file);
	}

	@Nullable
	private IslandSector loadSectorFromDisk(final int sectorX, final int sectorY)
	{
		final File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

		if (file.exists())
		{
			final NBTTagCompound tag = NBTHelper.readNBTFromFile(file);

			final IslandSector sector = NBTHelper.fullyDeserialize("s", tag, null);

			return sector;
		}

		return null;
	}

	private IslandSector createSectorAndSaveToDisk(final World world, final int sectorX, final int sectorY, final long seed)
	{
		if (world.isRemote)
		{
			return null;
		}

		final IslandSector sector = IslandSectorFactory.create(sectorX, sectorY, seed);

		this.saveSectorToDisk(world, sector);

		return sector;
	}

	private void addSectorToMemory(final IslandSector sector)
	{
		this.activeSectors.put(sector.getSectorX(), sector.getSectorY(), sector);
	}

	public void onServerStopping(final FMLServerStoppingEvent event)
	{
		for (final IslandSector sector : this.activeSectors.getValues())
		{
			this.saveSectorToDisk(sector);
		}

		this.activeSectors = new ChunkMap<>();
	}

	@SubscribeEvent
	public void onChunkUnWatch(final ChunkWatchEvent.UnWatch event) // Unload sectors which are no longer in generating range
	{
		final ChunkPos unloadedChunk = event.getChunk();
		final WorldServer world = event.getPlayer().getServerWorld();

		if (world.provider.getDimensionType() != DimensionsAether.AETHER || world.isRemote)
		{
			return;
		}

		final List<IslandSector> sectorsToUnload = Lists.newArrayList();

		for (final IslandSector sector : this.activeSectors.getValues())
		{
			final boolean shouldUnloadSector = sector.isLoadedChunksEmpty();

			if (sector.isChunkLoaded(unloadedChunk.chunkXPos, unloadedChunk.chunkZPos))
			{
				sector.untrackLoadedChunk(unloadedChunk.chunkXPos, unloadedChunk.chunkZPos);
			}

			if (shouldUnloadSector)
			{
				sectorsToUnload.add(sector);
			}
		}

		for (final IslandSector sector : sectorsToUnload)
		{
			this.unloadSectorFromMemory(world, sector);
		}
	}

}
