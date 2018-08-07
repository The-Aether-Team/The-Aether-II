package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;

public class EntityBodyHelperNoRotation extends EntityBodyHelper
{
	private final EntityLivingBase living;

	public EntityBodyHelperNoRotation(EntityLivingBase livingIn)
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
