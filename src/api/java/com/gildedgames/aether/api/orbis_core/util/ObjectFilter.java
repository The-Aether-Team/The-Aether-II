package com.gildedgames.aether.api.orbis_core.util;

import com.google.common.collect.Lists;

import java.util.*;

public class ObjectFilter
{

	private ObjectFilter()
	{

	}

	public static <T> List<T> getTypesFrom(final Object[] array, final Class<? extends T> typeClass)
	{
		return getTypesFrom(Arrays.asList(array), typeClass);
	}

	public static <T> List<T> getTypesFrom(final T[] array, final FilterCondition<T> condition)
	{
		return getTypesFrom(Arrays.asList(array), condition);
	}

	public static <T> T getFirstFrom(final Object[] array, final Class<? extends T> typeClass)
	{
		return getFirstFrom(Arrays.asList(array), typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromKeys(final Map<?, ?> map, final Class<? extends K> keyClass, final Class<? extends T> typeClass)
	{
		final Map<K, T> returnMap = new HashMap<K, T>();

		for (final Map.Entry<?, ?> entry : map.entrySet())
		{
			final Object key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && typeClass.isAssignableFrom(key.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromValues(final Map<?, ?> map, final Class<? extends K> keyClass, final Class<? extends T> typeClass)
	{
		final Map<K, T> returnMap = new HashMap<K, T>();

		for (final Map.Entry<?, ?> entry : map.entrySet())
		{
			final Object key = entry.getKey();
			final Object value = entry.getValue();

			if (value != null && typeClass.isAssignableFrom(value.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	public static <T> List<T> getTypesFrom(final Iterable<?> iterable, final Class<? extends T> typeClass)
	{
		return ObjectFilter.getTypesFrom(Lists.newArrayList(iterable), typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getTypesFrom(final Collection<?> list, final Class<? extends T> typeClass)
	{
		final List<T> returnList = new ArrayList<T>();

		for (final Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				returnList.add((T) obj);
			}
		}

		return returnList;
	}

	public static <T> List<T> getTypesFrom(final Collection<T> list, final FilterCondition<T> condition)
	{
		final List<T> returnList = new ArrayList<T>();

		for (final T obj : list)
		{
			if (obj != null && condition.isType(obj))
			{
				returnList.add(obj);
			}
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFirstFrom(final Collection<?> list, final Class<? extends T> typeClass)
	{
		for (final Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				return (T) obj;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(final Object object, final Class<? extends T> typeClass)
	{
		T returnObject = null;

		if (object != null && typeClass.isAssignableFrom(object.getClass()))
		{
			returnObject = (T) object;
		}

		return returnObject;
	}

	public static abstract class FilterCondition<T>
	{

		private final List<T> data;

		public FilterCondition(final List<T> data)
		{
			this.data = data;
		}

		public List<T> data()
		{
			return this.data;
		}

		public abstract boolean isType(T object);

	}

}
