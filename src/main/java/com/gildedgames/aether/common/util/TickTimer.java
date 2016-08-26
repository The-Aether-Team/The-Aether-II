package com.gildedgames.aether.common.util;

public class TickTimer
{

	private int ticksPassed;

	private final int ticksPerSecond;

	public static final int TICKS_PER_SECOND_DEFAULT = 20;

	public TickTimer()
	{
		this(TICKS_PER_SECOND_DEFAULT);
	}

	public TickTimer(int ticksPerSecond)
	{
		this.ticksPerSecond = ticksPerSecond;
	}

	public void tick()
	{
		this.ticksPassed++;
	}

	public void reset()
	{
		this.ticksPassed = 0;
	}

	public int getTicksPerSecond()
	{
		return this.ticksPerSecond;
	}

	public boolean isMultipleOfSeconds(int seconds)
	{
		return this.getTicksPassed() % (seconds * this.getTicksPerSecond()) == 0;
	}

	public boolean isMultipleOfSeconds()
	{
		return this.getTicksPassed() % this.getTicksPerSecond() == 0;
	}

	public boolean isMultipleOfTicks(int ticks)
	{
		return this.getTicksPassed() % ticks == 0;
	}

	public int getTicksPassed()
	{
		return this.ticksPassed;
	}

	public void setTicksPassed(int ticksPassed)
	{
		this.ticksPassed = ticksPassed;
	}

	public int getSecondsPassed()
	{
		return this.ticksPassed / 20;
	}

}
