package com.gildedgames.aether.common.util;

public class TickTimer
{

	private int ticksPassed;

	public TickTimer()
	{

	}

	public TickTimer(int ticksPassed)
	{
		this.ticksPassed = ticksPassed;
	}

	public void tick()
	{
		this.ticksPassed++;
	}

	public void reset()
	{
		this.ticksPassed = 0;
	}

	public int getTicksPassed()
	{
		return this.ticksPassed;
	}

	public int getSecondsPassed()
	{
		return this.ticksPassed % 20;
	}

}
