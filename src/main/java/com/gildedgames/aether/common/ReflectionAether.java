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

	public static final ReflectionEntry IN_MORE_WORLD_OPTIONS_DISPLAY = new ReflectionEntry("field_146344_y", "inMoreWorldOptionsDisplay");

	public static final ReflectionEntry EQUIPPED_PROGRESS_MAIN_HAND = new ReflectionEntry("field_187469_f", "equippedProgressMainHand");

	public static final ReflectionEntry NET_CLIENT_HANDLER = new ReflectionEntry("field_78774_b", "netClientHandler");

	public static final ReflectionEntry INVULNERABLE_DIMENSION_CHANGE = new ReflectionEntry("field_184851_cj", "invulnerableDimensionChange");

	public static final ReflectionEntry BLOCK_HARDNESS = new ReflectionEntry("field_149782_v", "blockHardness");

	public static final ReflectionEntry IS_JUMPING = new ReflectionEntry("field_70703_bu", "isJumping");

	public static final ReflectionEntry FOOD_LEVEL = new ReflectionEntry("field_75127_a", "foodLevel");

	public static final ReflectionEntry FOOD_SATURATION_LEVEL = new ReflectionEntry("field_75125_b", "foodSaturationLevel");

	public static final ReflectionEntry FOOD_EXHAUSTION_LEVEL = new ReflectionEntry("field_75126_c", "foodExhaustionLevel");

	public static final ReflectionEntry TILE_STRUCTURE = new ReflectionEntry("field_189846_f", "tileStructure");

	public static final ReflectionEntry NAME_INPUT = new ReflectionEntry("field_189853_u");

	public static final ReflectionEntry RELATIVE_X_INPUT = new ReflectionEntry("field_189854_v");

	public static final ReflectionEntry RELATIVE_Y_INPUT = new ReflectionEntry("field_189855_w");

	public static final ReflectionEntry RELATIVE_Z_INPUT = new ReflectionEntry("field_189856_x");

	public static final ReflectionEntry SIZE_X_INPUT = new ReflectionEntry("field_189857_y");

	public static final ReflectionEntry SIZE_Y_INPUT = new ReflectionEntry("field_189858_z");

	public static final ReflectionEntry SIZE_Z_INPUT = new ReflectionEntry("field_189825_A");

	public static final ReflectionEntry METADATA_INPUT = new ReflectionEntry("field_189828_D");

	public static final ReflectionEntry INTEGRITY_INPUT = new ReflectionEntry("field_189826_B");

	public static final ReflectionEntry SEED_INPUT = new ReflectionEntry("field_189827_C");

	public static final ReflectionEntry EVENT_LISTENERS = new ReflectionEntry("field_73021_x", "eventListeners");

	public static Field getField(Class clazz, String... names)
	{
		for (Field field : clazz.getDeclaredFields())
		{
			for (String name : names)
			{
				if (field.getName().equals(name))
				{
					return field;
				}
			}
		}

		return null;
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

		return null;
	}

	public static void invokeMethod(Method method, Object obj, Object... args)
	{
		try
		{
			method.invoke(obj, args);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
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
			e.printStackTrace();
		}

		return null;
	}
}
