package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.gildedgames.aether.common.world.util.ChunkMap;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
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

		File file = new File(AetherCore.getWorldDirectory(), "//data/island_sectors/" + sectorKey + ".dat");

		return file;
	}

	public int getSectorCoord(int chunkCoord)
	{
		return (chunkCoord / IslandSector.CHUNK_WIDTH_PER_SECTOR) - (chunkCoord < 0 ? 1 : 0);
	}

	public IslandData getIslandIfOnlyOne(World world, BlockPos pos)
	{
		List<IslandData> islandsToGen = this.getAllIslands(world, pos);

		boolean oneIslandOnly = islandsToGen.size() == 1;

		return oneIslandOnly ? islandsToGen.get(0) : null;
	}

	public List<IslandData> getAllIslands(World world, BlockPos pos)
	{
		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		int sectorX = IslandSectorAccess.inst().getSectorCoord(chunkX);
		int sectorY = IslandSectorAccess.inst().getSectorCoord(chunkZ);

		IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(world, sectorX, sectorY);

		if (sector == null)
		{
			return null;
		}

		final List<IslandData> islandsToGen = Lists.newArrayList();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				List<IslandData> islands = sector.getIslandDataAtBlockPos(pos.getX() + x, pos.getZ() + z);

				if (islands.size() <= 0)
				{
					continue;
				}

				for (IslandData data : islands)
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

	public IslandSector attemptToLoadSector(World world, int sectorX, int sectorY)
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
			File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

			file.delete();
		}

		if (!this.wasSectorEverCreated(world, sectorX, sectorY) || sector == null)
		{
			return null;
		}

		this.addSectorToMemory(sector);

		return sector;
	}

	public IslandSector attemptToLoadSector(World world, int sectorX, int sectorY, long seedForNewSector)
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
			File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

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

	public boolean isSectorLoadedInMemory(int sectorX, int sectorY)
	{
		return this.activeSectors.containsKey(sectorX, sectorY);
	}

	public boolean wasSectorEverCreated(World world, int sectorX, int sectorY)
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
	public IslandSector loadSectorFromMemory(World world, int sectorX, int sectorY)
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

	public void unloadSectorFromMemory(World world, IslandSector sector)
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

	public void unloadSectorFromMemory(World world, int sectorX, int sectorY)
	{
		if (world.isRemote)
		{
			return;
		}

		if (!this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return;
		}

		this.saveSectorToDisk(world, sectorX, sectorY);
		this.activeSectors.remove(sectorX, sectorY);
	}

	public void saveSectorToDisk(World world, int sectorX, int sectorY)
	{
		if (world.isRemote)
		{
			return;
		}

		if (!this.isSectorLoadedInMemory(sectorX, sectorY))
		{
			return;
		}

		this.saveSectorToDisk(world, this.activeSectors.get(sectorX, sectorY));
	}

	public void saveSectorToDisk(World world, IslandSector sector)
	{
		if (world.isRemote)
		{
			return;
		}

		this.saveSectorToDisk(sector);
	}

	private void saveSectorToDisk(IslandSector sector)
	{
		File file = IslandSectorAccess.createSectorFile(sector.getSectorX(), sector.getSectorY());

		NBTTagCompound tag = new NBTTagCompound();

		NBTHelper.fullySerialize("s", sector, tag);

		NBTHelper.writeNBTToFile(tag, file);
	}

	@Nullable
	private IslandSector loadSectorFromDisk(int sectorX, int sectorY)
	{
		File file = IslandSectorAccess.createSectorFile(sectorX, sectorY);

		if (file.exists())
		{
			NBTTagCompound tag = NBTHelper.readNBTFromFile(file);

			IslandSector sector = NBTHelper.fullyDeserialize("s", tag);

			return sector;
		}

		return null;
	}

	public IslandSector createSectorAndSaveToDisk(World world, int sectorX, int sectorY, long seed)
	{
		if (world.isRemote)
		{
			return null;
		}

		IslandSector sector = IslandSectorFactory.create(sectorX, sectorY, seed);

		this.saveSectorToDisk(world, sector);

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

		if (world.provider.getDimensionType() != DimensionsAether.AETHER || world.isRemote)
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
			this.unloadSectorFromMemory(world, sector);
		}
	}

}
