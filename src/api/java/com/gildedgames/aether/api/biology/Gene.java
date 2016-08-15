package com.gildedgames.aether.api.biology;

public interface Gene
{

	String name();

	Mutation[] potentialMutations();

	Inheritance inheritance();

}
