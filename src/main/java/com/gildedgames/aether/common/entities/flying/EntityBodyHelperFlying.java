package com.gildedgames.aether.common.entities.flying;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class EntityBodyHelperFlying extends EntityBodyHelper
{

	private final LivingEntity entity;

	public EntityBodyHelperFlying(LivingEntity livingIn)
	{
		super(livingIn);

		this.entity = livingIn;
	}

	@Override
	public void updateRenderAngles()
	{
		float renderYawOffset = this.entity.renderYawOffset;

		super.updateRenderAngles();

		this.entity.renderYawOffset = renderYawOffset;

		this.entity.renderYawOffset = this.limitAngle(this.entity.renderYawOffset, this.entity.rotationYaw, 10F);
	}

	protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_)
	{
		float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);

		if (f > p_75639_3_)
		{
			f = p_75639_3_;
		}

		if (f < -p_75639_3_)
		{
			f = -p_75639_3_;
		}

		float f1 = p_75639_1_ + f;

		if (f1 < 0.0F)
		{
			f1 += 360.0F;
		}
		else if (f1 > 360.0F)
		{
			f1 -= 360.0F;
		}

		return f1;
	}

}
