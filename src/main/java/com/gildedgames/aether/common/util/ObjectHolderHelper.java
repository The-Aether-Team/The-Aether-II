package com.gildedgames.aether.common.util;

import java.lang.reflect.Field;

public class ObjectHolderHelper
{
	public static void validateEntries(Class<?> clazz, Class<?> type)
	{
		for (Field field : clazz.getDeclaredFields())
		{
			if (field.getType() != type)
			{
				continue;
			}

			try
			{
				if (field.get(null) == null)
				{
					throw new IllegalStateException("Field is null in registrar " + clazz.getSimpleName() + ": " + field.getName());
				}
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException("Could not use reflection to access field " + field.getName() + " in registrar " + clazz.getSimpleName(), e);
			}
		}
	}
}
