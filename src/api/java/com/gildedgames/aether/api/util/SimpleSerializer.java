package com.gildedgames.aether.api.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Function;

public class SimpleSerializer implements IClassSerializer
{

	private final BiMap<Integer, Class> serialIDToClass = HashBiMap.create();

	private final Map<Class, Function<World, ?>> classToObjectCreation = Maps.newHashMap();

	public SimpleSerializer()
	{

	}

	@Override
	public <T extends NBT> void register(final int id, final Class<T> clazz, final Function<World, T> objectCreation)
	{
		this.serialIDToClass.put(id, clazz);
		this.classToObjectCreation.put(clazz, objectCreation);
	}

	@Override
	public int serialize(final Class<?> obj)
	{
		if (obj == null)
		{
			throw new NullPointerException();
		}

		return this.serialIDToClass.inverse().get(obj);
	}

	@Override
	public int serialize(final Object obj)
	{
		if (obj == null)
		{
			throw new NullPointerException();
		}

		return this.serialize(obj.getClass());
	}

	@Override
	public <T extends NBT> T deserialize(final World world, final int id)
	{
		final Class clazz = this.serialIDToClass.get(id);

		@SuppressWarnings("unchecked") final T obj = (T) this.classToObjectCreation.get(clazz).apply(world);

		return obj;
	}
}
