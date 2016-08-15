package com.gildedgames.aether.api.genes;

public interface Gene
{

	String localizedName();

	String unlocalizedName();

	Mutation[] potentialMutations();

	Inheritance inheritance();

}
