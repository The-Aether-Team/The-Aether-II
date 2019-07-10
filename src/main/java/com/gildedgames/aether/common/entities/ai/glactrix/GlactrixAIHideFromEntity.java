package com.gildedgames.aether.common.entities.ai.glactrix;

import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class GlactrixAIHideFromEntity extends EntityAIBase
{

	static final int maxDist = 12;
	private final EntityGlactrix glactrix;
	protected Class<? extends EntityLivingBase> hideFromClass;
	protected EntityLivingBase hideFrom;

	public GlactrixAIHideFromEntity(final EntityGlactrix glactrix, final Class<? extends EntityLivingBase> clazz)
	{
		this.glactrix = glactrix;
		this.hideFromClass = clazz;

	}

	@Override
	public boolean shouldExecute()
	{
		final List entities = this.glactrix.world.getEntitiesWithinAABB(this.hideFromClass, this.glactrix.getEntityBoundingBox().expand(maxDist, maxDist, maxDist));

		if (entities.isEmpty())
		{
			return false;
		}

		EntityLivingBase threat = null;

		for (final Object o : entities)
		{
			if (o instanceof EntityLivingBase && !(o instanceof EntityPlayer && ((EntityPlayer) o).isCreative()))
			{
				threat = (EntityLivingBase) o;
			}
		}

		if (threat == null)
		{
			return false;
		}
		else
		{
			this.hideFrom = threat;

			return this.hideFrom.canEntityBeSeen(this.glactrix);
		}
	}

	@Override
	public void startExecuting()
	{
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		if (!this.hideFrom.canEntityBeSeen(this.glactrix))
		{
			this.glactrix.setHiding(false);
			return false;
		}

		this.glactrix.setHiding(true);
		return true;
	}
}
