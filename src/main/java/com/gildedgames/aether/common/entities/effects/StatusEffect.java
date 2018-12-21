package com.gildedgames.aether.common.entities.effects;

import java.util.HashMap;
import java.util.Map;

public abstract class StatusEffect
{
	private String id;
	private String name;
	/**
	 * Current effect build-up amount
	 */
	private float buildupAmount;
	/**
	 * Value at which the effect activates
	 * Default: 100
	 */
	private float triggerValue;
	/**
	 * Value at which rate the effect regresses
	 * Default: 1.0f
	 */
	private float regressionRate;

	/**
	 * Boolean value to determine if player is exposed to this effect
	 */
	private boolean exposed = false;

	/**
	 * Resistance towards this effect
	 */
	public float resistance = 0;

	public static Map<String, StatusEffect> effectsList = new HashMap<>();

	//private final IPlayerAether player;

	public StatusEffect(String id)
	{
		this(id,"Generic",0f, 100.0f, 1.0f);
	}

	public StatusEffect(String id, String displayName, float buildAmount, float trigValue, float regRate)
	{
		this.id = id;
		this.name = displayName;
		this.buildupAmount = buildAmount;
		this.triggerValue = trigValue;
		this.regressionRate = regRate;
		this.effectsList.put(id, this);
	}

	public void regress()
	{
		if(!exposed) this.buildupAmount -= this.regressionRate;
	}

	public void addBuildup(float amount)
	{
		this.buildupAmount += (amount - (resistance * 0.01) * amount);
		if(buildupAmount > triggerValue) buildupAmount = triggerValue;
	}

	public boolean isEffectActive()
	{
		return buildupAmount >= triggerValue;
	}

	public String getName()
	{
		return name;
	}

	public String getID()
	{
		return id;
	}

	public void setExposed(boolean exp)
	{
		this.exposed = exp;
	}

	public abstract void applyEffect();
}

