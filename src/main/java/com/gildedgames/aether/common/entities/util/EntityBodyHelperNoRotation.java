package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;

public class EntityBodyHelperNoRotation extends EntityBodyHelper
{
	public EntityBodyHelperNoRotation(EntityLivingBase livingIn)
	{
		super(livingIn);
	}

	@Override
	public void updateRenderAngles()
	{
		
	}
}
