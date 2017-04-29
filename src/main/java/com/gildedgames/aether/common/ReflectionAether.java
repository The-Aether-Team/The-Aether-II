package com.gildedgames.aether.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionAether
{
	public static class ReflectionEntry
	{
		private String[] mappings;

		private ReflectionEntry(String... mappings)
		{
			this.mappings = mappings;
		}

		public String[] getMappings()
		{
			return this.mappings;
		}
	}

	public static final ReflectionEntry EQUIPPED_PROGRESS_MAIN_HAND = new ReflectionEntry("field_187469_f", "equippedProgressMainHand");

	public static final ReflectionEntry ACTIVE_ITEMSTACK_USE_COUNT = new ReflectionEntry("field_184628_bn", "activeItemStackUseCount");

	public static final ReflectionEntry INVULNERABLE_DIMENSION_CHANGE = new ReflectionEntry("field_184851_cj", "invulnerableDimensionChange");

	public static final ReflectionEntry BLOCK_HARDNESS = new ReflectionEntry("field_149782_v", "blockHardness");

	public static final ReflectionEntry IS_JUMPING = new ReflectionEntry("field_70703_bu", "isJumping");

	public static final ReflectionEntry SERVER_CURRENT_TIME = new ReflectionEntry("field_175591_ab", "currentTime");

	public static Field getField(Class clazz, String... names)
	{
		for (Field field : clazz.getDeclaredFields())
		{
			for (String name : names)
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

	public static Method getMethod(Class clazz, Class<?>[] args, String... names)
	{
		for (Method method : clazz.getDeclaredMethods())
		{
			for (String name : names)
			{
				if (method.getName().equals(name))
				{
					Class<?>[] matching = method.getParameterTypes();

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

	public static void invokeMethod(Method method, Object obj, Object... args)
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

	public static Object getValue(Field field, Object obj)
	{
		try
		{
			return field.get(obj);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Failed to fetch field value", e);
		}
	}
}
