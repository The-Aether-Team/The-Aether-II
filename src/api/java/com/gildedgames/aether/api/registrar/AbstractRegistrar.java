package com.gildedgames.aether.api.registrar;

import javax.annotation.Nonnull;

public abstract class AbstractRegistrar
{
	/**
	 * This method doesn't make much sense at first glance. We're using it to trick IDEs into thinking
	 * that the fields above will *not* be set to null by annotating this method as never to be null.
	 */
	@SuppressWarnings("ConstantConditions")
	@Nonnull
	protected static <T> T getDefault()
	{
		return null;
	}
}
