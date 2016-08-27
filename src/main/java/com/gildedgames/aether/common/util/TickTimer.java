package com.gildedgames.aether.common.util;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.nbt.NBTTagCompound;

public class TickTimer implements NBT
{

	private int ticksPassed;

	private int ticksPerSecond;

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

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("ticksPassed", this.ticksPassed);
		output.setInteger("ticksPerSecond", this.ticksPerSecond);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.ticksPassed = input.getInteger("ticksPassed");
		this.ticksPerSecond = input.getInteger("ticksPerSecond");
	}
}
