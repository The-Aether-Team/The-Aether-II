package com.gildedgames.aether.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionAether
{
	public static final ReflectionEntry EQUIPPED_PROGRESS_MAIN_HAND = new ReflectionEntry("field_187469_f", "equippedProgressMainHand");

	public static final ReflectionEntry ACTIVE_ITEMSTACK_USE_COUNT = new ReflectionEntry("field_184628_bn", "activeItemStackUseCount");

	public static final ReflectionEntry INVULNERABLE_DIMENSION_CHANGE = new ReflectionEntry("field_184851_cj", "invulnerableDimensionChange");

	public static final ReflectionEntry BLOCK_HARDNESS = new ReflectionEntry("field_149782_v", "blockHardness");

	public static final ReflectionEntry IS_JUMPING = new ReflectionEntry("field_70703_bu", "isJumping");

	public static final ReflectionEntry SERVER_CURRENT_TIME = new ReflectionEntry("field_175591_ab", "currentTime");

	public static final ReflectionEntry BUTTON_LIST = new ReflectionEntry("field_146292_n", "buttonList");

	public static Field getField(final Class clazz, final String... names)
	{
		for (final Field field : clazz.getDeclaredFields())
		{
			for (final String name : names)
			{
				if (field.getName().equals(name))
				{
					field.setAccessible(true);

					return field;
				}
			}
		}

		throw new RuntimeException("Couldn't find field");
	}

	public static Method getMethod(final Class clazz, final Class<?>[] args, final String... names)
	{
		for (final Method method : clazz.getDeclaredMethods())
		{
			for (final String name : names)
			{
				if (method.getName().equals(name))
				{
					final Class<?>[] matching = method.getParameterTypes();

					boolean matches = true;

					if (matching.length != args.length)
					{
						matches = false;
					}
					else
					{
						for (int i = 0; i < args.length; i++)
						{
							if (matching[i] != args[i])
							{
								matches = false;

								break;
							}
						}
					}

					if (matches)
					{
						method.setAccessible(true);

						return method;
					}
				}
			}
		}

		throw new RuntimeException("Couldn't find method");
	}

	public static void invokeMethod(final Method method, final Object obj, final Object... args)
	{
		try
		{
			method.invoke(obj, args);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException("Failed to invoke method through reflection", e);
		}
	}

	public static Object getValue(final Field field, final Object obj)
	{
		try
		{
			return field.get(obj);
		}
		catch (final IllegalAccessException e)
		{
			throw new RuntimeException("Failed to fetch field value", e);
		}
	}

	public static void setValue(final Field field, final Object instance, final Object value)
	{
		try
		{
			field.set(instance, value);
		}
		catch (final IllegalAccessException e)
		{
			throw new RuntimeException("Failed to fetch field value", e);
		}
	}

	public static class ReflectionEntry
	{
		private final String[] mappings;

		private ReflectionEntry(final String... mappings)
		{
			this.mappings = mappings;
		}

		public String[] getMappings()
		{
			return this.mappings;
		}
	}
}
