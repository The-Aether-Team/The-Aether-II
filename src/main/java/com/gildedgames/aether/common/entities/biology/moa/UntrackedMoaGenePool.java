package com.gildedgames.aether.common.entities.biology.moa;

public class UntrackedMoaGenePool extends MoaGenePool
{

	private int seed, fatherSeed, motherSeed;

	public UntrackedMoaGenePool()
	{
		super();
	}

	@Override
	public int getSeed()
	{
		return this.seed;
	}

	@Override
	public int getFatherSeed()
	{
		return this.fatherSeed;
	}

	@Override
	public int getMotherSeed()
	{
		return this.motherSeed;
	}

	protected void setSeed(int seed)
	{
		this.seed = seed;
	}

	protected void setFatherSeed(int seed)
	{
		this.fatherSeed = seed;
	}

	protected void setMotherSeed(int seed)
	{
		this.motherSeed = seed;
	}

	protected boolean shouldRetransform()
	{
		return false;
	}

	protected void setShouldRetransform(boolean flag)
	{

	}

}
