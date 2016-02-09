package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class EntityEffect<S extends Entity>
{
	
	private final Ability<S> ability;
	
	private final AbilityRule<S>[] rules;
	
	private final NBTTagCompound attributes;
	
	@SafeVarargs
	public EntityEffect(Ability<S> ability, AbilityRule<S>... rules)
	{
		this.ability = ability;
		this.rules = rules;
		this.attributes = new NBTTagCompound();
		
		this.attributes.setInteger("modifier", 1);
	}
	
	public NBTTagCompound getAttributes()
	{
		return this.attributes;
	}

	public Ability<S> getAbility()
	{
		return this.ability;
	}
	
	public AbilityRule<S>[] getRules()
	{
		return this.rules;
	}
	
}