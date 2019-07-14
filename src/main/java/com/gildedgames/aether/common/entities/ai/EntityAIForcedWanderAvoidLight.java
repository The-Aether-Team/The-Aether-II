package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityAIForcedWanderAvoidLight extends EntityAIForcedWander
{
	protected final int lightLevel;

	public EntityAIForcedWanderAvoidLight(final CreatureEntity entity, final double speedIn, final int chance, final int lightLevel)
	{
		this(entity, speedIn, chance, 10, 7, lightLevel);
	}

	public EntityAIForcedWanderAvoidLight(final CreatureEntity entity, final double speedIn, final int chance, final int xScatter, final int yScatter,
			final int lightLevel)
	{
		super(entity, speedIn, chance, xScatter, yScatter);

		this.lightLevel = lightLevel;
	}

	@Override
	public boolean shouldExecute()
	{
		final World world = this.entity.world;

		final BlockPos entityPos = new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ);

		if (world.isDaytime())
		{
			if (!world.canBlockSeeSky(new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ)))
			{
				return false;
			}

			if (this.entity.world.getBrightness(entityPos) <= this.lightLevel)
			{
				return false;
			}
		}

		return super.shouldExecute();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath() && this.shouldExecute();
	}

	@Override
	@Nullable
	protected Vec3d getPosition()
	{
		if (!this.entity.world.isDaytime())
		{
			return super.getPosition();
		}

		for (int i = 0; i < 10; i++)
		{
			final Vec3d pos = RandomPositionGenerator.findRandomTarget(this.entity, this.xScatter, this.yScatter);

			if (pos != null)
			{
				final BlockPos blockPos = new BlockPos(pos);

				if (!this.entity.world.isBlockLoaded(blockPos))
				{
					continue;
				}

				if (!(this.entity.world.isDaytime() && this.entity.world.canBlockSeeSky(blockPos)))
				{
					return pos;
				}
			}
		}

		return null;
	}

}
