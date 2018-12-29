package com.gildedgames.aether.common.entities.effects;

import java.util.HashMap;
import java.util.Map;

public abstract class StatusEffect
{
	private final String id;

	private final String name;

	/**
	 * Current effect build-up amount
	 */
	private float buildupAmount;

	/**
	 * Value at which the effect activates
	 * Default: 100
	 */
	private final float triggerValue;

	/**
	 * Value at which rate the effect regresses
	 * Default: 1.0f
	 */
	private final float regressionRate;

	/**
	 * Boolean value to determine if player is exposed to this effect
	 */
	private boolean exposed = false;

	/**
	 * Resistance towards this effect
	 */
	public final float resistance = 0;

	public static final Map<String, StatusEffect> effectsList = new HashMap<>();

	//private final IPlayerAether player;

	public StatusEffect(String id)
	{
		this(id, "Generic", 0f, 100.0f, 1.0f);
	}

	public StatusEffect(String id, String displayName, float buildAmount, float trigValue, float regRate)
	{
		this.id = id;
		this.name = displayName;
		this.buildupAmount = buildAmount;
		this.triggerValue = trigValue;
		this.regressionRate = regRate;
		effectsList.put(id, this);
	}

	public void regress()
	{
		if (!this.exposed)
		{
			this.buildupAmount -= this.regressionRate;
		}
	}

	public void addBuildup(float amount)
	{
		this.buildupAmount += (amount - (this.resistance * 0.01) * amount);
		if (this.buildupAmount > this.triggerValue)
		{
			this.buildupAmount = this.triggerValue;
		}
	}

	public boolean isEffectActive()
	{
		return this.buildupAmount >= this.triggerValue;
	}

	public String getName()
	{
		return this.name;
	}

	public String getID()
	{
		return this.id;
	}

	public void setExposed(boolean exp)
	{
		this.exposed = exp;
	}

	public abstract void applyEffect();
}

