package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketPartialSectorData;
import com.gildedgames.aether.common.network.packets.PacketUnloadSector;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.orbis_api.preparation.IPrepManager;
import com.gildedgames.orbis_api.preparation.IPrepRegistryEntry;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import com.gildedgames.orbis_api.util.ChunkMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PlayerSectorModule extends PlayerAetherModule
{
	private ChunkMap<WatchedSector> map = new ChunkMap<>();

	private Future<IPrepSector> waiting;

	public PlayerSectorModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{
		if (this.getWorld().isRemote)
		{
			return;
		}

		this.uploadNext();

		this.update();

		this.unloadNext();
	}

	private void update()
	{
		for (WatchedSector loadEntry : this.map.getValues())
		{
			loadEntry.updateDistance(this.getEntity());
			loadEntry.watching = false;

			if (loadEntry.sector != null)
			{
				loadEntry.sector.removeWatchingPlayer(this.getEntity().getEntityId());
			}
		}

		IPrepManager manager = PrepHelper.getManager(this.getWorld());

		if (manager == null)
		{
			return;
		}

		int radius = 80;

		IPrepRegistryEntry entry = manager.getRegistryEntry();

		int chunkX = ((int) this.getEntity().posX) >> 4;
		int chunkY = ((int) this.getEntity().posZ) >> 4;

		int minChunkX = chunkX - radius;
		int minChunkY = chunkY - radius;

		int maxChunkX = chunkX + radius;
		int maxChunkY = chunkY + radius;

		int minSectorX = Math.floorDiv(minChunkX, entry.getSectorChunkArea());
		int minSectorY = Math.floorDiv(minChunkY, entry.getSectorChunkArea());

		int maxSectorX = Math.floorDiv(maxChunkX, entry.getSectorChunkArea());
		int maxSectorZ = Math.floorDiv(maxChunkY, entry.getSectorChunkArea());

		for (int x = minSectorX; x < maxSectorX; x++)
		{
			for (int z = minSectorY; z < maxSectorZ; z++)
			{
				if (!this.map.containsKey(x, z))
				{
					WatchedSector watched = new WatchedSector(entry, x, z);
					watched.watching = true;
					watched.updateDistance(this.getEntity());

					this.map.put(x, z, watched);
				}
				else
				{
					WatchedSector watched = this.map.get(x, z);
					watched.watching = true;
				}
			}
		}

		if (this.waiting == null)
		{
			WatchedSector closest = null;

			for (WatchedSector watched : this.map.getValues())
			{
				if (!watched.watching || watched.sector != null)
				{
					continue;
				}

				if (closest == null)
				{
					closest = watched;
				}
				else
				{
					if (watched.distance < closest.distance)
					{
						closest = watched;
					}
				}
			}

			if (closest != null)
			{
				this.waiting = manager.getAccess().provideSector(closest.sectorX, closest.sectorZ, true);
			}
		}
	}


	private void uploadNext()
	{
		if (this.waiting != null && this.waiting.isDone())
		{
			try
			{
				IPrepSector sector = this.waiting.get();
				sector.addWatchingPlayer(this.getEntity().getEntityId());

				WatchedSector watched = this.map.get(sector.getData().getSectorX(), sector.getData().getSectorY());
				watched.sector = sector;

				IPrepManager manager = PrepHelper.getManager(this.getWorld());

				if (manager == null)
				{
					return;
				}

				manager.getAccess().retainSector(sector);

				NetworkingAether.sendPacketToPlayer(new PacketPartialSectorData((PrepSectorDataAether) sector.getData()), (EntityPlayerMP) this.getEntity());
			}
			catch (InterruptedException | ExecutionException e)
			{
				throw new RuntimeException("Exception while generating AOT sector for player", e);
			}
			finally
			{
				this.waiting = null;
			}
		}
	}

	private void unloadNext()
	{
		ArrayList<WatchedSector> unloads = null;

		for (WatchedSector loadEntry : this.map.getValues())
		{
			if (!loadEntry.watching)
			{
				if (unloads == null)
				{
					unloads = new ArrayList<>();
				}

				unloads.add(loadEntry);
			}
		}

		if (unloads == null)
		{
			return;
		}

		for (WatchedSector loadEntry : unloads)
		{
			if (loadEntry.sector != null)
			{
				NetworkingAether.sendPacketToPlayer(new PacketUnloadSector(loadEntry.sector.getData()), (EntityPlayerMP) this.getEntity());

				this.map.remove(loadEntry.sector.getData().getSectorX(), loadEntry.sector.getData().getSectorY());
			}
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}

	private class WatchedSector
	{
		private final int sectorX, sectorZ;

		private IPrepSector sector;

		private IPrepRegistryEntry entry;

		private boolean watching;

		private double distance;

		public WatchedSector(IPrepRegistryEntry entry, int sectorX, int sectorZ)
		{
			this.entry = entry;
			this.sectorX = sectorX;
			this.sectorZ = sectorZ;
		}

		public void updateDistance(EntityPlayer player)
		{
			double x = ((this.sectorX * this.entry.getSectorChunkArea()) + (this.entry.getSectorChunkArea() / 2.0)) * 16.0;
			double z = ((this.sectorZ * this.entry.getSectorChunkArea()) + (this.entry.getSectorChunkArea() / 2.0)) * 16.0;

			this.distance = player.getDistance(x, player.posY, z);
		}
	}
}
