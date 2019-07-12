package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;

public abstract class EntityAI<T extends Entity> extends Goal
{

	private final T entity;

	public EntityAI(T entity)
	{
		this.entity = entity;
	}

	public T entity()
	{
		return this.entity;
	}

}
