package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.AetherCore;
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
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

// Arguably, this is a less than optimal model for the purposes of garbage collection, but who really cares. It's 2018.
public class PlayerSectorModule extends PlayerAetherModule
{
	private ChunkMap<LoadEntry> map = new ChunkMap<>();

	private IPrepSector lastSector;

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

		for (LoadEntry loadEntry : this.map.getValues())
		{
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

		int centerSectorX = Math.floorDiv(chunkX, entry.getSectorChunkArea());
		int centerSectorY = Math.floorDiv(chunkY, entry.getSectorChunkArea());

		// Prepare this first to give priority to sectors the player is in
		manager.getAccess().provideSector(centerSectorX, centerSectorY, true);

		int minSectorX = Math.floorDiv(minChunkX, entry.getSectorChunkArea());
		int minSectorY = Math.floorDiv(minChunkY, entry.getSectorChunkArea());

		int maxSectorX = Math.floorDiv(maxChunkX, entry.getSectorChunkArea());
		int maxSectorY = Math.floorDiv(maxChunkY, entry.getSectorChunkArea());

		for (int x = minSectorX; x < maxSectorX; x++)
		{
			for (int y = minSectorY; y < maxSectorY; y++)
			{
				if (!this.map.containsKey(x, y))
				{
					LoadEntry loadEntry = new LoadEntry(manager.getAccess().provideSector(x, y, false));

					this.map.put(x, y, loadEntry);
				}

				LoadEntry loadEntry = this.map.get(x, y);
				loadEntry.watching = true;

				if (loadEntry.sector != null)
				{
					loadEntry.sector.addWatchingPlayer(this.getEntity().getEntityId());
				}
			}
		}

		this.uploadPending();
		this.unloadPending();
	}

	private void uploadPending()
	{
		ArrayList<LoadEntry> uploads = new ArrayList<>();

		for (LoadEntry loadEntry : this.map.getValues())
		{
			if (!loadEntry.generated && loadEntry.future.isDone())
			{
				try
				{
					loadEntry.sector = loadEntry.future.get();
					loadEntry.generated = true;

					if (loadEntry.sector != null)
					{
						loadEntry.sector.addWatchingPlayer(this.getEntity().getEntityId());
					}

					uploads.add(loadEntry);
				}
				catch (InterruptedException | ExecutionException e)
				{
					throw new RuntimeException("Failed to generate AOT sector for player", e);
				}
			}
		}

		for (LoadEntry loadEntry : uploads)
		{
			AetherCore.LOGGER.info("Uploading sector " + loadEntry.sector.getData().getSectorX() + ", " + loadEntry.sector.getData().getSectorY() + " to client");

			NetworkingAether.sendPacketToPlayer(new PacketPartialSectorData((PrepSectorDataAether) loadEntry.sector.getData()), (EntityPlayerMP) this.getEntity());
		}
	}

	private void unloadPending()
	{
		ArrayList<LoadEntry> unloads = new ArrayList<>();

		for (LoadEntry loadEntry : this.map.getValues())
		{
			if (!loadEntry.watching)
			{
				unloads.add(loadEntry);
			}
		}

		for (LoadEntry loadEntry : unloads)
		{
			if (loadEntry.sector != null)
			{
				AetherCore.LOGGER.info("Notifying client to unload sector " + loadEntry.sector.getData().getSectorX() + ", " + loadEntry.sector.getData().getSectorY());

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

	private static class LoadEntry
	{
		private final ListenableFuture<IPrepSector> future;

		private IPrepSector sector;

		private boolean generated;

		private boolean watching;

		public LoadEntry(ListenableFuture<IPrepSector> future)
		{
			this.future = future;
		}
	}
}
