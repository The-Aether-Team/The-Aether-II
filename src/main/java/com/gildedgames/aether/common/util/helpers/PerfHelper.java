package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.AetherCore;

public class PerfHelper
{
	public static void measure(String name, Runnable o)
	{
		long start = System.nanoTime();

		o.run();

		AetherCore.LOGGER.debug("'{}' completed in {}ms", name, ((System.nanoTime() - start) / 1000000));
	}
}
