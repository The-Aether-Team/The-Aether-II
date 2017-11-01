package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.world_object.IWorldObjectGroup;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;

import java.util.Collection;
import java.util.List;

/**
 * TODO: IMPLEMENT CACHING for the object groups
 * with a file persisisting cache. Will drastically
 * improve performance and prevent any issues with
 * OOM exceptions.
 */
public class WorldObjectManager extends WorldSavedData
{
	private static final String DATA_NAME = AetherCore.MOD_ID + "_WorldObjectManager";

	private final List<IWorldObjectManagerObserver> observers = Lists.newArrayList();

	private int dimension;

	private World world;

	private BiMap<Integer, IWorldObjectGroup> idToGroup = HashBiMap.create();

	private int nextId;

	public WorldObjectManager()
	{
		super(DATA_NAME);
	}

	public WorldObjectManager(final String s)
	{
		super(s);
	}

	public WorldObjectManager(final World world)
	{
		super(DATA_NAME);

		this.world = world;
	}

	public static WorldObjectManager get(final World world)
	{
		final World using;

		if (world.isRemote)
		{
			if (Minecraft.getMinecraft().isIntegratedServerRunning())
			{
				using = DimensionManager.getWorld(world.provider.getDimension());
			}
			else
			{
				using = world;
			}
		}
		else
		{
			using = world.getMinecraftServer().worldServerForDimension(world.provider.getDimension());
		}

		final MapStorage storage = using.getPerWorldStorage();
		WorldObjectManager instance = (WorldObjectManager) storage.getOrLoadData(WorldObjectManager.class, DATA_NAME);

		if (instance == null)
		{
			instance = new WorldObjectManager(world);
			storage.setData(DATA_NAME, instance);
		}

		return instance;
	}

	/**
	 * Should be called when an observer is added to
	 * this manager
	 */
	private void refreshObserver(final IWorldObjectManagerObserver observer)
	{
		observer.onReloaded(this);
	}

	public World getWorld()
	{
		return this.world;
	}

	public void setWorld(final World world)
	{
		this.world = world;
	}

	public <T extends IWorldObjectGroup> int getID(final T group)
	{
		return this.idToGroup.inverse().get(group);
	}

	public <T extends IWorldObjectGroup> T getGroup(final int id)
	{
		IWorldObjectGroup group = this.idToGroup.get(id);

		if (!this.idToGroup.containsKey(id) || group == null)
		{
			group = new WorldObjectGroup(this.world);

			this.setGroup(id, group);
		}

		return (T) group;
	}

	public <T extends IWorldObjectGroup> void setGroup(final int id, final T object)
	{
		this.idToGroup.put(id, object);

		for (final IWorldObjectManagerObserver observer : this.observers)
		{
			observer.onGroupAdded(this, object);
		}

		this.markDirty();
	}

	public <T extends IWorldObjectGroup> void addGroup(final T object)
	{
		this.setGroup(this.nextId++, object);
	}

	public Collection<IWorldObjectGroup> getGroups()
	{
		return this.idToGroup.values();
	}

	public void addObserver(final IWorldObjectManagerObserver observer)
	{
		this.observers.add(observer);

		this.refreshObserver(observer);
	}

	public boolean removeObserver(final IWorldObjectManagerObserver observer)
	{
		return this.observers.remove(observer);
	}

	public boolean containsObserver(final IWorldObjectManagerObserver observer)
	{
		return this.observers.contains(observer);
	}

	@Override
	public void readFromNBT(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.nextId = tag.getInteger("nextId");
		this.dimension = tag.getInteger("dimension");

		if (this.world == null)
		{
			this.world = DimensionManager.getWorld(this.dimension);
		}

		this.idToGroup = HashBiMap.create(funnel.getIntMap(this.world, "groups"));

		for (final IWorldObjectManagerObserver observer : this.observers)
		{
			observer.onReloaded(this);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setInteger("nextId", this.nextId);
		tag.setInteger("dimension", this.dimension);

		funnel.setIntMap("groups", this.idToGroup);

		return tag;
	}

}
