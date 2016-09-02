package com.gildedgames.aether.api.capabilites.entity.effects;

import net.minecraft.nbt.NBTTagCompound;

/**
 * An instance of an {@link EntityEffectProcessor} containing the item's special characteristics.
 */
public class EntityEffectInstance
{

	public enum State
	{
		APPLIED, CANCELLED;
	}

	private final EntityEffectRule[] rules;

	private final NBTTagCompound attributes;

	private State state;

	public EntityEffectInstance(EntityEffectRule... rules)
	{
		this.rules = rules;
		this.attributes = new NBTTagCompound();
	}

	public State getState()
	{
		return this.state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return A list of rules required to activate the corresponding {@link EntityEffectProcessor}.
	 */
	public EntityEffectRule[] getRules()
	{
		return this.rules;
	}

	/**
	 * @return The {@link NBTTagCompound} containing custom traits of the item's effects.
	 */
	public NBTTagCompound getAttributes()
	{
		return this.attributes;
	}

	/**
	 * Used internally to clone special traits.
	 * @return A clone of this instance.
	 */
	public EntityEffectInstance cloneInstance()
	{
		return new EntityEffectInstance(this.getRules());
	}
}
