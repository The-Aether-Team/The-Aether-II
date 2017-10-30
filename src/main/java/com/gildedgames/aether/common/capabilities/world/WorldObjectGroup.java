package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectGroup;
import com.gildedgames.aether.api.orbis.IWorldObjectGroupObserver;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class WorldObjectGroup implements IWorldObjectGroup
{
	private final List<IWorldObjectGroupObserver> observers = Lists.newArrayList();

	private BiMap<Integer, IWorldObject> idToObject = HashBiMap.create();

	private World world;

	private int nextId;

	private WorldObjectGroup()
	{

	}

	public WorldObjectGroup(final World world)
	{
		this.setWorld(world);
	}

	@Override
	public void setWorld(final World world)
	{
		this.world = world;
	}

	@Override
	public <T extends IWorldObject> int getID(final T object)
	{
		if (object == null)
		{
			throw new NullPointerException();
		}

		return this.idToObject.inverse().get(object);
	}

	@Override
	public <T extends IWorldObject> T getObject(final int id)
	{
		return (T) this.idToObject.get(id);
	}

	@Override
	public <T extends IWorldObject> void setObject(final int id, final T object)
	{
		this.idToObject.put(id, object);

		for (final IWorldObjectGroupObserver observer : this.observers)
		{
			observer.onObjectAdded(this, object);
		}

		WorldObjectManager.get(this.world).markDirty();
	}

	@Override
	public <T extends IWorldObject> void addObject(final T object)
	{
		this.setObject(this.nextId++, object);
	}

	@Override
	public <T extends IWorldObject> boolean removeObject(final T object)
	{
		return this.removeObject(this.getID(object));
	}

	@Override
	public boolean removeObject(final int id)
	{
		final IWorldObject object = this.idToObject.get(id);

		if (this.idToObject.containsKey(id))
		{
			this.idToObject.remove(id);

			for (final IWorldObjectGroupObserver observer : this.observers)
			{
				observer.onObjectRemoved(this, object);
			}

			WorldObjectManager.get(this.world).markDirty();
		}

		return object != null;
	}

	@Override
	public Collection<IWorldObject> getObjects()
	{
		return this.idToObject.values();
	}

	/**
	 * Should be called when an observer is added to
	 * this manager
	 */
	private void refreshObserver(final IWorldObjectGroupObserver observer)
	{
		for (final IWorldObject object : this.idToObject.values())
		{
			observer.onObjectAdded(this, object);
		}
	}

	@Override
	public void addObserver(final IWorldObjectGroupObserver observer)
	{
		this.observers.add(observer);

		this.refreshObserver(observer);
	}

	@Override
	public boolean removeObserver(final IWorldObjectGroupObserver observer)
	{
		return this.observers.remove(observer);
	}

	@Override
	public boolean containsObserver(final IWorldObjectGroupObserver observer)
	{
		return this.observers.contains(observer);
	}

	@Override
	public IShape getIntersectingShape(final BlockPos pos)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (area.contains(pos))
				{
					return area;
				}
			}
		}

		return null;
	}

	@Override
	public <T extends IShape> T getIntersectingShape(final Class<T> shapeType, final BlockPos pos)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (area.contains(pos) && area.getClass() == shapeType)
				{
					return shapeType.cast(area);
				}
			}
		}

		return null;
	}

	@Override
	public IShape getIntersectingShape(final IShape shape)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (RegionHelp.intersects(area, shape))
				{
					return area;
				}
			}

		}

		return null;
	}

	@Override
	public <T extends IShape> T getIntersectingShape(final Class<T> shapeType, final IShape shape)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (RegionHelp.intersects(area, shape) && area.getClass() == shapeType)
				{
					return shapeType.cast(area);
				}
			}
		}

		return null;
	}

	@Override
	public IShape getContainedShape(final IShape shape)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (RegionHelp.contains(area, shape))
				{
					return area;
				}
			}
		}

		return null;
	}

	@Override
	public IShape getContainedShape(final Class<? extends IShape> shapeType, final IShape shape)
	{
		for (final IWorldObject obj : this.idToObject.values())
		{
			if (obj instanceof IShape)
			{
				final IShape area = (IShape) obj;

				if (RegionHelp.contains(area, shape) && area.getClass() == shapeType)
				{
					return area;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isIntersectingShapes(final IShape shape)
	{
		return this.getIntersectingShape(shape) != null;
	}

	@Override
	public boolean isContainedInShape(final IShape shape)
	{
		return this.getContainedShape(shape) != null;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("nextId", this.nextId);

		funnel.setIntMap("idToObject", this.idToObject);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.nextId = tag.getInteger("nextId");

		this.idToObject = HashBiMap.create(funnel.getIntMap(this.world, "idToObject"));
	}
}
