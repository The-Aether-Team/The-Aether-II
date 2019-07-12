package com.gildedgames.aether.common.entities.ai.glactrix;

import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class GlactrixAIHideFromEntity extends Goal
{

	static final int maxDist = 12;
	private final EntityGlactrix glactrix;
	protected Class<? extends LivingEntity> hideFromClass;
	protected LivingEntity hideFrom;

	public GlactrixAIHideFromEntity(final EntityGlactrix glactrix, final Class<? extends LivingEntity> clazz)
	{
		this.glactrix = glactrix;
		this.hideFromClass = clazz;

	}

	@Override
	public boolean shouldExecute()
	{
		final List entities = this.glactrix.world.getEntitiesWithinAABB(this.hideFromClass, this.glactrix.getBoundingBox().expand(maxDist, maxDist, maxDist));

		if (entities.isEmpty())
		{
			return false;
		}

		LivingEntity threat = null;

		for (final Object o : entities)
		{
			if (o instanceof LivingEntity && !(o instanceof PlayerEntity && ((PlayerEntity) o).isCreative()))
			{
				threat = (LivingEntity) o;
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
