package com.gildedgames.aether.api.items.equipment.effects;

import java.util.Collection;
import java.util.function.Function;

public class EffectHelper
{
	/**
	 * A helper method to combine the specific value of multiple effect instances together.
	 *
	 * This method iterates through each object, using {@param func} to collect the values.
	 *
	 * @param instances The collection of objects to combine
	 * @param func A {@link Function} which returns the value of each object to be combined
	 * @param <T> The object type
	 *
	 * @return The combined value of every object in {@param instances}
	 */
	public static <T> double combineDouble(Collection<T> instances, Function<T, Double> func)
	{
		double total = 0.0f;

		for (T instance : instances)
		{
			total += func.apply(instance);
		}

		return total;
	}
}
