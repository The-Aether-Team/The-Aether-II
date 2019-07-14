package com.gildedgames.aether.common.entities.ai.companion;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAICompanionFollow extends Goal
{
	private final PathNavigator navigator;

	private final EntityCompanion entity;

	private float oldWaterCost;

	private int timeToRecalcPath;

	public EntityAICompanionFollow(final EntityCompanion entity)
	{
		this.entity = entity;

		this.navigator = entity.getNavigator();
	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity.getOwner() != null && this.entity.getDistanceSq(this.entity.getOwner()) > 12.0D;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		if (this.entity.getOwner() == null)
		{
			return false;
		}

		final double distance = this.entity.getDistanceSq(this.entity.getOwner());

		return !this.navigator.noPath() && this.entity.hurtTime <= 0 && distance > 5.0D;
	}

	@Override
	public void startExecuting()
	{
		this.timeToRecalcPath = 0;

		this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);

		this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	@Override
	public void resetTask()
	{
		this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);

		this.navigator.clearPath();
	}

	private boolean isEmptyBlock(final BlockPos pos)
	{
		final BlockState state = this.entity.world.getBlockState(pos);

		return state.getMaterial() == Material.AIR || !state.isFullCube();
	}

	@Override
	public void tick()
	{
		final PlayerEntity owner = this.entity.getOwner();

		if (owner == null)
		{
			return;
		}

		this.entity.getLookController().setLookPositionWithEntity(owner, 10.0F, 40f);

		if (this.timeToRecalcPath-- <= 0)
		{
			this.timeToRecalcPath = 10;

			if (this.entity.getDistanceSq(this.entity.getOwner()) > 144.0D)
			{
				final int i = MathHelper.floor(owner.posX) - 2;
				final int j = MathHelper.floor(owner.posZ) - 2;
				final int k = MathHelper.floor(owner.getBoundingBox().minY);

				for (int l = 0; l <= 4; ++l)
				{
					for (int i1 = 0; i1 <= 4; ++i1)
					{
						if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.entity.world.getBlockState(new BlockPos(
								i + l, k - 1, j + i1)).getMaterial().isSolid() && this.isEmptyBlock(new BlockPos(i + l, k, j + i1))
								&& this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1)))
						{
							this.entity.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1)
									+ 0.5F), this.entity.rotationYaw, this.entity.rotationPitch);

							this.navigator.clearPath();

							return;
						}
					}
				}
			}
			else
			{
				this.navigator.tryMoveToEntityLiving(owner, 0.5D);
			}
		}
	}
}
