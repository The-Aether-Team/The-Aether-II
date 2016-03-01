package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class EntityEffect<S extends Entity>
{
	
	private final Ability<S>[] abilities;
	
	private final AbilityRule<S>[] rules;
	
	private final NBTTagCompound attributes;

	protected EntityEffect(Ability<S>[] abilities, AbilityRule<S>[] rules)
	{
		this.abilities = abilities;
		
		this.rules = rules;
		
		this.attributes = new NBTTagCompound();
		
		this.attributes.setInteger("modifier", 1);
	}
	
	public NBTTagCompound getAttributes()
	{
		return this.attributes;
	}

	public Ability<S>[] getAbilities()
	{
		return this.abilities;
	}
	
	public AbilityRule<S>[] getRules()
	{
		return this.rules;
	}
	
}