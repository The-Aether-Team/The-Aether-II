package com.gildedgames.aether.common.entities.biology.util;

import com.gildedgames.aether.api.biology.Gene;
import com.gildedgames.aether.api.biology.Inheritance;
import com.gildedgames.aether.api.biology.Mutation;

public class DataGene<T> implements Gene
{

	private String name;

	private T data;

	private Inheritance inheritance;

	private Mutation[] potentialMutations;

	public DataGene(String name, T data, Inheritance inheritance, Mutation... potentialMutations)
	{
		this.name = name;
		this.data = data;

		this.inheritance = inheritance;

		this.potentialMutations = potentialMutations;
	}

	@Override
	public String name()
	{
		return this.name;
	}

	@Override
	public Mutation[] potentialMutations()
	{
		return this.potentialMutations;
	}

	@Override
	public Inheritance inheritance()
	{
		return this.inheritance;
	}

	public T data()
	{
		return this.data;
	}

}
