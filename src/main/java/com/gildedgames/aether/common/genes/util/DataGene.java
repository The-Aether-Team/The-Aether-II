package com.gildedgames.aether.common.genes.util;

import com.gildedgames.aether.api.genes.Gene;
import com.gildedgames.aether.api.genes.Inheritance;
import com.gildedgames.aether.api.genes.Mutation;
import net.minecraft.client.resources.I18n;

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
	public String localizedName()
	{
		return I18n.format(this.name);
	}

	@Override
	public String unlocalizedName()
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
