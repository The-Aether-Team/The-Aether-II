package com.gildedgames.aether.common.util;

public class PerformanceStopwatch
{
	private long start, last;

	public PerformanceStopwatch()
	{
		this.start = System.currentTimeMillis();
		this.last = this.start;
	}

	public long measure()
	{
		long now = System.currentTimeMillis();

		long duration = now - this.last;

		this.last = now;

		return duration;
	}

	public long total()
	{
		return System.currentTimeMillis() - this.start;
	}
}
