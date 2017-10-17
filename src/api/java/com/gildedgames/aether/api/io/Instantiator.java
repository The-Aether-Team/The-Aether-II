package com.gildedgames.aether.api.io;

import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Instantiator<T> implements Function<World, T>
{

	private final Class<T> clazz;

	/**
	 * Requires the passed class to have a default constructor with World parameter (can be private). Will throw a NPE if it has no default constructor.
	 * @param clazz
	 */
	public Instantiator(final Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public T apply(final World world)
	{
		try
		{
			final Constructor<T> constructor;

			if (world == null)
			{
				constructor = this.clazz.getDeclaredConstructor();
			}
			else
			{
				constructor = this.clazz.getDeclaredConstructor(World.class);
			}

			constructor.setAccessible(true);

			final T instance = world != null ? constructor.newInstance(world) : constructor.newInstance();

			constructor.setAccessible(false);

			return instance;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new NullPointerException("Something went wrong trying to create an instances of " + this.clazz.getName()
					+ ". Most likely you forgot to create a World constructor/default constructor for it.");
		}
	}

}
