package com.gildedgames.aether.common.entities.genes;

import com.gildedgames.aether.api.entity.genes.Gene;
import com.gildedgames.aether.api.entity.genes.Inheritance;
import com.gildedgames.aether.api.entity.genes.Mutation;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DataGene<T> implements Gene
{

	private final String name;

	private final T data;

	private final Inheritance inheritance;

	private final Mutation[] potentialMutations;

	public DataGene(String name, T data, Inheritance inheritance, Mutation... potentialMutations)
	{
		this.name = name;
		this.data = data;

		this.inheritance = inheritance;

		this.potentialMutations = potentialMutations;
	}

	@Override
	@SideOnly(Side.CLIENT)
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
