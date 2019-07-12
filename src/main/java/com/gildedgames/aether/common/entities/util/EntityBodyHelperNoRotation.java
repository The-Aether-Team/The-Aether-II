package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.LivingEntity;

public class EntityBodyHelperNoRotation extends EntityBodyHelper
{
	private final LivingEntity living;

	public EntityBodyHelperNoRotation(LivingEntity livingIn)
	{
		super(livingIn);

		this.living = livingIn;
	}

	@Override
	public void updateRenderAngles()
	{
		this.living.renderYawOffset = this.living.rotationYaw;
	}
}
