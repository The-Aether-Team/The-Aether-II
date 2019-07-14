package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.BodyController;

public class EntityBodyHelperNoRotation extends BodyController
{
	private final MobEntity living;

	public EntityBodyHelperNoRotation(MobEntity entity)
	{
		super(entity);

		this.living = entity;
	}

	@Override
	public void updateRenderAngles()
	{
		this.living.renderYawOffset = this.living.rotationYaw;
	}
}
