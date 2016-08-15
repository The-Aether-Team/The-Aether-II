package com.gildedgames.aether.api.biology;

import com.google.common.base.Supplier;

public class Mutation<T extends Gene>
{

	private Supplier<T> geneSupplier;

	private float chanceToMutate;

	private Mutation(Supplier<T> geneSupplier, float chanceToMutate)
	{
		this.geneSupplier = geneSupplier;
		this.chanceToMutate = chanceToMutate;
	}

	public T getGene()
	{
		return this.geneSupplier.get();
	}

	public float getChanceToMutate()
	{
		return this.chanceToMutate;
	}

}
