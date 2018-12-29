package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.world.ISpawnArea;
import com.gildedgames.aether.api.world.ISpawnAreaManager;
import com.gildedgames.aether.api.world.ISpawnHandler;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.util.ChunkMap;
import com.gildedgames.orbis_api.world.data.IWorldDataManager;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SpawnAreaManager implements ISpawnAreaManager
{
	private final IWorldDataManager dataManager;

	private final ChunkMap<ISpawnArea> loadedAreas = new ChunkMap<>();

	private final HashSet<ISpawnArea> dirty = new HashSet<>();

	private final World world;

	private final ISpawnHandler handler;

	public SpawnAreaManager(World world, ISpawnHandler handler)
	{
		this.world = world;
		this.handler = handler;

		this.dataManager = OrbisAPI.services().getWorldDataManager(world);
	}

	private boolean isLoaded(int chunkX, int chunkZ)
	{
		return this.loadedAreas.containsKey(chunkX, chunkZ);
	}

	private ISpawnArea loadArea(final int areaX, final int areaZ)
	{
		if (this.isLoaded(areaX, areaZ))
		{
			return this.getLoadedArea(areaX, areaZ);
		}

		ISpawnArea area = this.fetchArea(areaX, areaZ);

		if (area == null)
		{
			area = new SpawnArea(this.handler.getChunkArea(), areaX, areaZ);
			area.markDirty();
		}

		this.loadedAreas.put(areaX, areaZ, area);

		return area;
	}

	private ISpawnArea fetchArea(int areaX, int areaZ)
	{
		if (this.isLoaded(areaX, areaZ))
		{
			return this.getLoadedArea(areaX, areaZ);
		}

		final String areaID = this.createAreaID(areaX, areaZ);

		try
		{
			byte[] bytes = this.dataManager.readBytes(this, areaID);

			if (bytes != null)
			{
				try (ByteArrayInputStream in = new ByteArrayInputStream(bytes))
				{
					try (DataInputStream dataIn = new DataInputStream(in))
					{
						NBTTagCompound tag = CompressedStreamTools.read(dataIn);

						SpawnArea area = new SpawnArea(this.handler.getChunkArea(), areaX, areaZ);
						area.deserializeNBT(tag);

						return area;
					}
				}
			}
		}
		catch (Exception e)
		{
			AetherCore.LOGGER.warn("Failed to fetch spawn area", e);
		}

		return null;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public void tick()
	{
		for (final ISpawnArea area : this.getLoaded())
		{
			area.setInPlayersRenderDistance(false);
		}

		for (final EntityPlayer player : this.world.playerEntities)
		{
			final int chunkX = MathHelper.floor(player.posX) >> 4;
			final int chunkZ = MathHelper.floor(player.posZ) >> 4;

			final int centerAreaX = chunkX / this.handler.getChunkArea();
			final int centerAreaZ = chunkZ / this.handler.getChunkArea();

			for (int areaX = centerAreaX - 1; areaX <= centerAreaX + 1; areaX++)
			{
				for (int areaZ = centerAreaZ - 1; areaZ <= centerAreaZ + 1; areaZ++)
				{
					ISpawnArea area = this.loadArea(areaX, areaZ);
					area.setInPlayersRenderDistance(true);
				}
			}
		}

		final List<ISpawnArea> areasToRemove = Lists.newArrayList();

		for (ISpawnArea area : this.getLoaded())
		{
			if (!area.hasPlayerInside())
			{
				areasToRemove.add(area);
			}

			if (area.isDirty())
			{
				this.dirty.add(area);
			}
		}

		for (ISpawnArea area : areasToRemove)
		{
			this.loadedAreas.remove(area.getAreaX(), area.getAreaZ());
		}
	}

	@Override
	public Collection<ISpawnArea> getLoaded()
	{
		return this.loadedAreas.getValues();
	}

	private String createAreaID(int areaX, int areaZ)
	{
		return this.handler.getUniqueID() + "/" + areaX + "_" + areaZ + ".nbt";
	}

	@Override
	public ResourceLocation getName()
	{
		return AetherCore.getResource("spawn_areas");
	}

	@Override
	public void flush()
	{
		for (ISpawnArea area : this.dirty)
		{
			final String areaID = this.createAreaID(area.getAreaX(), area.getAreaZ());

			final NBTTagCompound tag = area.serializeNBT();

			try (ByteArrayOutputStream stream = new ByteArrayOutputStream())
			{
				try (DataOutputStream dataOut = new DataOutputStream(stream))
				{
					CompressedStreamTools.write(tag, dataOut);
				}

				stream.flush();

				this.dataManager.writeBytes(this, areaID, stream.toByteArray());
			}
			catch (IOException e)
			{
				AetherCore.LOGGER.warn("Failed to write spawn area", e);
			}

			area.markClean();
		}

		this.dirty.clear();
	}

	@Override
	public ISpawnArea getLoadedArea(int areaX, int areaZ)
	{
		return this.loadedAreas.get(areaX, areaZ);
	}

	@Override
	public void onLivingDeath(LivingDeathEvent event)
	{
		final ISpawningInfo spawningInfo = event.getEntityLiving().getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

		final EntitySpawn area = spawningInfo.getSpawnArea();

		if (area == null)
		{
			return;
		}

		if (area.getSpawnHandlerUniqueID().equals(this.handler.getUniqueID()))
		{
			final ISpawnArea fetchedArea = this.fetchArea(area.getAreaX(), area.getAreaZ());
			fetchedArea.addToEntityCount(-1);
		}
	}

	public class Storage implements Capability.IStorage<SpawnAreaManager>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<SpawnAreaManager> capability, SpawnAreaManager instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<SpawnAreaManager> capability, SpawnAreaManager instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}
