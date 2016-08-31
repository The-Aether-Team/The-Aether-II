package com.gildedgames.aether.api.capabilites.entity.properties;

import com.google.common.base.Supplier;

public class ElementalDamageSource
{

	private final Supplier<Double> damage;

	private final ElementalState elementalState;

	public ElementalDamageSource(ElementalState elementalState, Supplier<Double> damage)
	{
		this.elementalState = elementalState;
		this.damage = damage;
	}

	public double getDamage()
	{
		return this.damage.get();
	}

	public ElementalState getElementalState()
	{
		return this.elementalState;
	}

}
