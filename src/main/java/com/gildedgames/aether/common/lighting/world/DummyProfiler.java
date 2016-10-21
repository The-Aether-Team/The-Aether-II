package com.gildedgames.aether.common.lighting.world;

import net.minecraft.profiler.Profiler;

public class DummyProfiler extends Profiler
{

	public long threadToIgnore;

	private Profiler profiler;

	public DummyProfiler(Profiler profiler)
	{
		this.profiler = profiler;
	}

	@Override
	public void startSection(String par1Str)
	{
		if (Thread.currentThread().getId() != this.threadToIgnore)
		{
			this.profiler.startSection(par1Str);
		}
	}

	@Override
	public void endSection()
	{
		if (Thread.currentThread().getId() != this.threadToIgnore)
		{
			this.profiler.endSection();
		}
	}

}
