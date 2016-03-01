package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;

public class EntityEffectBuilder<S extends Entity>
{
	
	private Ability<S>[] abilities;

	public EntityEffectBuilder()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public EntityEffectBuilder<S> abilities(Ability<S>... abilities)
	{
		this.abilities = abilities;
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public EntityEffect<S> flush(AbilityRule<S>... rules)
	{
		return new EntityEffect<S>(this.abilities, rules);
	}
	
}
