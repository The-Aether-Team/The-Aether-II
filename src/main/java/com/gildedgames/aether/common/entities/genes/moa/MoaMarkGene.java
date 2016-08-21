package com.gildedgames.aether.common.entities.genes.moa;

import com.gildedgames.aether.api.genes.Gene;
import com.gildedgames.aether.api.genes.Inheritance;
import com.gildedgames.aether.api.genes.Mutation;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class MoaMarkGene implements Gene
{

	private String name, resourceName;

	private Inheritance inheritance;

	private Mutation[] potentialMutations;

	private ResourceLocation back, head, tail, wing;

	public MoaMarkGene(String name, String resourceName, Inheritance inheritance, Mutation... potentialMutations)
	{
		this.name = name;
		this.resourceName = resourceName;

		this.inheritance = inheritance;

		this.potentialMutations = potentialMutations;

		this.back = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/moa/back/" + resourceName + ".png");
		this.head = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/moa/head/" + resourceName + ".png");
		this.tail = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/moa/tail/" + resourceName + ".png");
		this.wing = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/moa/wing/" + resourceName + ".png");
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

	public ResourceLocation getBack()
	{
		return this.back;
	}

	public ResourceLocation getHead()
	{
		return this.head;
	}

	public ResourceLocation getTail()
	{
		return this.tail;
	}

	public ResourceLocation getWing()
	{
		return this.wing;
	}

	public String getResourceName() { return this.resourceName; }

}
