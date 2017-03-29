package com.gildedgames.aether.api.genes;

import java.util.function.Supplier;

public class Mutation<T extends Gene>
{

	private final Supplier<T> geneSupplier;

	private final float chanceToMutate;

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
