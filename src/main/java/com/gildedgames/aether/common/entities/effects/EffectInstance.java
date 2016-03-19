package com.gildedgames.aether.common.entities.effects;

import net.minecraft.nbt.NBTTagCompound;

public class EffectInstance
{

	private final EffectRule[] rules;

	private final NBTTagCompound attributes;	

	public EffectInstance(EffectRule... rules)
	{
		this.rules = rules;
		this.attributes = new NBTTagCompound();
	}
	
	public EffectRule[] getRules()
	{
		return this.rules;
	}
	
	public NBTTagCompound getAttributes()
	{
		return this.attributes;
	}
	
	public EffectInstance cloneInstance()
	{
		return new EffectInstance(this.getRules());
	}
	
}