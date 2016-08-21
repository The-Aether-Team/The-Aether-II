package com.gildedgames.aether.api.genes;

public class GeneRegion<T extends Gene>
{

	private final String name;

	private final T gene;

	public GeneRegion(String name, T gene)
	{
		this.name = name;
		this.gene = gene;
	}

	public String name()
	{
		return this.name;
	}

	public T gene()
	{
		return this.gene;
	}

}
