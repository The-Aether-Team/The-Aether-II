package com.gildedgames.aether.entities.effects;

import net.minecraft.nbt.NBTTagCompound;

public class EntityEffectInstance
{
	private final EntityEffectRule[] rules;

	private final NBTTagCompound attributes;

	public EntityEffectInstance(EntityEffectRule... rules)
	{
		this.rules = rules;
		this.attributes = new NBTTagCompound();
	}

	public EntityEffectRule[] getRules()
	{
		return this.rules;
	}

	public NBTTagCompound getAttributes()
	{
		return this.attributes;
	}

	public EntityEffectInstance cloneInstance()
	{
		return new EntityEffectInstance(this.getRules());
	}
}