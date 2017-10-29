package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.IWorldObjectManagerObserver;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Collection;
import java.util.List;

/**
 * TODO: IMPLEMENT CACHING for the object groups
 * with a file persisisting cache. Will drastically
 * improve performance and prevent any issues with
 * OOM exceptions.
 */
public class WorldObjectManager implements IWorldObjectManager
{
	private final World world;

	private final List<IWorldObjectManagerObserver> observers = Lists.newArrayList();

	private BiMap<Integer, IWorldObjectGroup> idToGroup = HashBiMap.create();

	private int nextId;

	public WorldObjectManager(final World world)
	{
		this.world = world;
	}

	public static IWorldObjectManager get(final int dimId, EntityPlayer player)
	{
		if (player.world.isRemote)
		{
			if (player.dimension == dimId)
			{
				return get(player.world);
			}
			return null;
		}
		else
		{
			return get(DimensionManager.getWorld(dimId));
		}
	}


	public static IWorldObjectManager get(final World world)
	{
		return world.getCapability(AetherCapabilities.WORLD_OBJECT_MANAGER, null);
	}

	/**
	 * Should be called when an observer is added to
	 * this manager
	 */
	private void refreshObserver(final IWorldObjectManagerObserver observer)
	{
		observer.onReloaded(this);
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public <T extends IWorldObjectGroup> int getID(final T group)
	{
		return this.idToGroup.inverse().get(group);
	}

	@Override
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

	@Override
	public <T extends IWorldObjectGroup> void setGroup(final int id, final T object)
	{
		this.idToGroup.put(id, object);

		for (final IWorldObjectManagerObserver observer : this.observers)
		{
			observer.onGroupAdded(this, object);
		}
	}

	@Override
	public <T extends IWorldObjectGroup> void addGroup(final T object)
	{
		this.setGroup(this.nextId++, object);
	}

	@Override
	public Collection<IWorldObjectGroup> getGroups()
	{
		return this.idToGroup.values();
	}

	@Override
	public void addObserver(final IWorldObjectManagerObserver observer)
	{
		this.observers.add(observer);

		this.refreshObserver(observer);
	}

	@Override
	public boolean removeObserver(final IWorldObjectManagerObserver observer)
	{
		return this.observers.remove(observer);
	}

	@Override
	public boolean containsObserver(final IWorldObjectManagerObserver observer)
	{
		return this.observers.contains(observer);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("nextId", this.nextId);

		funnel.setIntMap("groups", this.idToGroup);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.nextId = tag.getInteger("nextId");

		this.idToGroup = HashBiMap.create(funnel.getIntMap(this.world, "groups"));

		for (final IWorldObjectManagerObserver observer : this.observers)
		{
			observer.onReloaded(this);
		}
	}

	public static class Storage implements Capability.IStorage<IWorldObjectManager>
	{
		@Override
		public NBTBase writeNBT(final Capability<IWorldObjectManager> capability, final IWorldObjectManager instance, final EnumFacing side)
		{
			final NBTTagCompound nbt = new NBTTagCompound();
			instance.write(nbt);

			return nbt;
		}

		@Override
		public void readNBT(final Capability<IWorldObjectManager> capability, final IWorldObjectManager instance, final EnumFacing side, final NBTBase nbt)
		{
			instance.read((NBTTagCompound) nbt);
		}
	}

}
