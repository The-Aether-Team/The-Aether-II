package com.gildedgames.aether.common.entities.effects;

import java.util.ArrayList;
import java.util.List;

public abstract class StatusEffect
{
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

	public static List<StatusEffect> effectsList = new ArrayList<>();

	//private final IPlayerAether player;

	public StatusEffect()
	{
		this("Generic",0f, 100.0f, 1.0f);
	}

	public StatusEffect(String name, float buildAmount, float trigValue, float regRate)
	{
		this.name = name;
		this.buildupAmount = buildAmount;
		this.triggerValue = trigValue;
		this.regressionRate = regRate;
		this.effectsList.add(this);
	}

	public void regress()
	{
		if(!exposed) this.buildupAmount -= this.regressionRate;
	}

	public void addBuildup(float amount)
	{
		this.buildupAmount += amount;
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

	public void setExposed(boolean exp)
	{
		this.exposed = exp;
	}

	public abstract void applyEffect();
}

