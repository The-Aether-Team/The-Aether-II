package com.gildedgames.aether.api.entity.genes;

public interface Gene
{

	String localizedName();

	String unlocalizedName();

	Mutation[] potentialMutations();

	Inheritance inheritance();

}
