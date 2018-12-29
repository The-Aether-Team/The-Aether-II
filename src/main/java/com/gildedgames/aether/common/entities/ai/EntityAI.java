package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class EntityAI<T extends Entity> extends EntityAIBase
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
