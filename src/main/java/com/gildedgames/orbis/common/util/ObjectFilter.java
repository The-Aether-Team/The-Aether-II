package com.gildedgames.orbis.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class ObjectFilter
{

	private ObjectFilter()
	{

	}

	public static <T> List<T> getTypesFrom(Object[] array, Class<? extends T> typeClass)
	{
		return getTypesFrom(Arrays.asList(array), typeClass);
	}

	public static <T> List<T> getTypesFrom(T[] array, FilterCondition<T> condition)
	{
		return getTypesFrom(Arrays.asList(array), condition);
	}

	public static <T> T getFirstFrom(Object[] array, Class<? extends T> typeClass)
	{
		return getFirstFrom(Arrays.asList(array), typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromKeys(Map<?, ?> map, Class<? extends K> keyClass, Class<? extends T> typeClass)
	{
		Map<K, T> returnMap = new HashMap<K, T>();

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key != null && typeClass.isAssignableFrom(key.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromValues(Map<?, ?> map, Class<? extends K> keyClass, Class<? extends T> typeClass)
	{
		Map<K, T> returnMap = new HashMap<K, T>();

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (value != null && typeClass.isAssignableFrom(value.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	public static <T> List<T> getTypesFrom(Iterable<?> iterable, Class<? extends T> typeClass)
	{
		return ObjectFilter.getTypesFrom(Lists.newArrayList(iterable), typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getTypesFrom(Collection<?> list, Class<? extends T> typeClass)
	{
		List<T> returnList = new ArrayList<T>();

		for (Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				returnList.add((T) obj);
			}
		}

		return returnList;
	}

	public static <T> List<T> getTypesFrom(Collection<T> list, FilterCondition<T> condition)
	{
		List<T> returnList = new ArrayList<T>();

		for (T obj : list)
		{
			if (obj != null && condition.isType(obj))
			{
				returnList.add(obj);
			}
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFirstFrom(Collection<?> list, Class<? extends T> typeClass)
	{
		for (Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				return (T) obj;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object, Class<? extends T> typeClass)
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

		private List<T> data;

		public FilterCondition(List<T> data)
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
