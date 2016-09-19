package com.gildedgames.aether.common.world.island.logic;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.util.ChunkMap;
import com.gildedgames.util.core.util.GGHelper;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class IslandSectorAccess
{

	private ChunkMap<IslandSector> activeSectors = new ChunkMap<>();

	private final static IslandSectorAccess INST = new IslandSectorAccess();

	private IslandSectorAccess()
	{

	}

	public static IslandSectorAccess inst()
	{
		return IslandSectorAccess.INST;
	}

	private static String createSectorKey(int sectorX, int sectorY)
	{
		return sectorX + "_" + sectorY;
	}

	private static File createSectorFile(int sectorX, int sectorY)
	{
		String sectorKey = IslandSectorAccess.createSectorKey(sectorX, sectorY);

		File file = new File(UtilModule.getWorldDirectory(), "//data/island_sectors/" + sectorKey + ".dat");

		return file;
	}

	public int getSectorCoord(int chunkCoord)
	{
		return chunkCoord / IslandSector.CHUNK_WIDTH_PER_SECTOR;
	}

	public IslandSector attemptToLoadSector(int sectorX, int sectorY, long seedForNewSector)
	{
		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return this.loadSectorFromMemory(sectorX, sectorY);
		}

		if (!this.wasSectorEverCreated(sectorX, sectorY))
		{
			IslandSector sector = this.createSectorAndSaveToDisk(sectorX, sectorY, seedForNewSector);

			this.addSectorToMemory(sector);

			return sector;
		}

		IslandSector sector = this.loadSectorFromDisk(sectorX, sectorY);

		this.addSectorToMemory(sector);

		return sector;
	}

	public boolean isSectorLoadedInMemory(int sectorX, int sectorY)
	{
		return this.activeSectors.containsKey(sectorX, sectorY);
	}

	public boolean wasSectorEverCreated(int sectorX, int sectorY)
	{
		if (this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return true;
		}

		return IslandSectorAccess.createSectorFile(sectorX, sectorY).exists();
	}

	@Nullable
	public IslandSector loadSectorFromMemory(int sectorX, int sectorY)
	{
		if (this.activeSectors.containsKey(sectorX, sectorY))
		{
			return this.activeSectors.get(sectorX, sectorY);
		}

		AetherCore.LOGGER.debug("Something is attempting to load an Island Sector from memory before it has been loaded into memory! Please check if the sector has been loaded into memory first. If not, create the sector.");

		return null;
	}

	public void unloadSectorFromMemory(IslandSector sector)
	{
		if (!this.isSectorLoadedInMemory(sector.getSectorX(), sector.getSectorY()))
		{
			return;
		}

		this.saveSectorToDisk(sector);
		this.activeSectors.remove(sector.getSectorX(), sector.getSectorY());
	}

	public void unloadSectorFromMemory(int sectorX, int sectorY)
	{
		if (!this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return;
		}

		this.saveSectorToDisk(sectorX, sectorY);
		this.activeSectors.remove(sectorX, sectorY);
	}

	public void saveSectorToDisk(int sectorX, int sectorY)
	{
		if (!this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return;
		}

		this.saveSectorToDisk(this.activeSectors.get(sectorX, sectorY));
	}

	public void saveSectorToDisk(IslandSector sector)
	{
		File file = IslandSectorAccess.createSectorFile(sector.getSectorX(), sector.getSectorY());

		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("x", sector.getSectorX());
		tag.setInteger("y", sector.getSectorY());

		tag.setLong("s", sector.getSeed());

		GGHelper.writeNBTToFile(tag, file);
	}

	@Nullable
	private IslandSector loadSectorFromDisk(int sectorX, int sectorY)
	{
		File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

		if (file.exists())
		{
			NBTTagCompound tag = GGHelper.readNBTFromFile(file);

			IslandSector sector = IslandSectorFactory.create(tag.getInteger("x"), tag.getInteger("y"), tag.getLong("s"));

			return sector;
		}

		return null;
	}

	public IslandSector createSectorAndSaveToDisk(int sectorX, int sectorY, long seed)
	{
		IslandSector sector = IslandSectorFactory.create(sectorX, sectorY, seed);

		this.saveSectorToDisk(sector);

		return sector;
	}

	private void addSectorToMemory(IslandSector sector)
	{
		this.activeSectors.put(sector.getSectorX(), sector.getSectorY(), sector);
	}

	public void onServerStopping(FMLServerStoppingEvent event)
	{
		for (IslandSector sector : this.activeSectors.getValues())
		{
			this.saveSectorToDisk(sector);
		}

		this.activeSectors = new ChunkMap<>();
	}

	@SubscribeEvent
	public void onChunkUnWatch(ChunkWatchEvent.UnWatch event) // Unload sectors which are no longer in generating range
	{
		WorldServer world = event.getPlayer().getServerWorld();

		if (world.provider.getDimensionType() != DimensionsAether.AETHER)
		{
			return;
		}

		List<IslandSector> sectorsToUnload = Lists.newArrayList();

		for (IslandSector sector : this.activeSectors.getValues())
		{
			Iterator<Chunk> chunks = world.getPlayerChunkMap().getChunkIterator();
			boolean shouldUnloadSector = true;

			while (chunks.hasNext())
			{
				Chunk chunk = chunks.next();
				ChunkPos pos = chunk.getChunkCoordIntPair();

				if (pos == event.getChunk())
				{
					continue;
				}

				int sectorX = this.getSectorCoord(pos.chunkXPos);
				int sectorY = this.getSectorCoord(pos.chunkZPos);

				if (sectorX == sector.getSectorX() && sectorY == sector.getSectorY())
				{
					shouldUnloadSector = false;
					break;
				}
			}

			if (shouldUnloadSector)
			{
				sectorsToUnload.add(sector);
			}
		}

		for (IslandSector sector : sectorsToUnload)
		{
			this.unloadSectorFromMemory(sector);
		}
	}

}
