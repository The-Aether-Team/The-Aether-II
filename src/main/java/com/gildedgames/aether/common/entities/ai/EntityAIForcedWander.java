package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class EntityAIForcedWander extends RandomWalkingGoal
{
	protected final CreatureEntity entity;

	protected final int chance;

	protected final int xScatter;

	protected final int yScatter;

	public EntityAIForcedWander(final CreatureEntity entity, final double speedIn, final int chance)
	{
		this(entity, speedIn, chance, 10, 7);
	}

	public EntityAIForcedWander(final CreatureEntity entity, final double speedIn, final int chance, final int xScatter, final int yScatter)
	{
		super(entity, speedIn, chance);

		this.entity = entity;
		this.chance = chance;
		this.xScatter = xScatter;
		this.yScatter = yScatter;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity.getNavigator().noPath())
		{
			if (this.entity.getRNG().nextInt(this.chance) == 0)
			{
				this.makeUpdate();

				return super.shouldExecute();
			}
		}

		return false;
	}

	@Override
	@Nullable
	protected Vec3d getPosition()
	{
		final Vec3d pos = RandomPositionGenerator.findRandomTarget(this.entity, this.xScatter, this.yScatter);

		if (pos != null)
		{
			if (!this.entity.world.isBlockLoaded(new BlockPos(pos)))
			{
				return null;
			}
		}

		return pos;
	}

}
