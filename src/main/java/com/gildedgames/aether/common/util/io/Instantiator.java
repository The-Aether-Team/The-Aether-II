package com.gildedgames.aether.common.util.io;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Instantiator implements Callable<Object>
{

	private final Class<?> clazz;

	/**
	 * Requires the passed class to have a default empty constructor (can be private). Will throw a NPE if it has no default empty constructor.
	 * @param clazz
	 */
	public Instantiator(final Class<?> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public Object call()
	{
		try
		{
			Constructor<?> constructor = this.clazz.getDeclaredConstructor();

			constructor.setAccessible(true);

			Object instance = constructor.newInstance();

			constructor.setAccessible(false);

			if (instance != null)
			{
				return instance;
			}

			final Constructor<?> constr = (Constructor<?>) this.clazz.getDeclaredConstructors()[0];
			final List<Object> params = new ArrayList<>();

			constr.setAccessible(false);

			for (Class<?> pType : constr.getParameterTypes())
			{
				params.add(pType.isPrimitive() ? ClassUtils.primitiveToWrapper(pType).newInstance() : null);
			}

			Object inst = constr.newInstance(params.toArray());

			if (inst == null)
			{
				throw new NullPointerException("Something went wrong trying to create an instances of " + this.clazz.getName()
						+ ". Most likely you forgot to create an empty constructor for it.");
			}

			return inst;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new NullPointerException("Something went wrong trying to create an instances of " + this.clazz.getName()
					+ ". Most likely you forgot to create an empty constructor for it.");
		}
	}

}
