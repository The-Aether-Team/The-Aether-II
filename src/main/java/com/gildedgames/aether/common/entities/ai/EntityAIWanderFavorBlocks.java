package com.gildedgames.aether.common.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class EntityAIWanderFavorBlocks extends EntityAIBase
{
	protected final EntityCreature entity;

	protected double x;
	protected double y;
	protected double z;

	protected final double speed;

	protected int executionChance;
	protected final ArrayList<Block> favoriteBlocks;
	protected boolean mustUpdate;


	public EntityAIWanderFavorBlocks(EntityCreature creatureIn, double speedIn, ArrayList<Block> favoriteBlocks)
	{
		this(creatureIn, speedIn, 60, favoriteBlocks);
	}

	public EntityAIWanderFavorBlocks(EntityCreature creatureIn, double speedIn, int chance, ArrayList<Block> favoriteBlocks)
	{
		this.entity = creatureIn;
		this.speed = speedIn;
		this.executionChance = chance;
		this.favoriteBlocks = favoriteBlocks;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.mustUpdate)
		{
			if (this.entity.getIdleTime() >= 100)
			{
				return false;
			}

			if (this.entity.getRNG().nextInt(this.executionChance) != 0)
			{
				return false;
			}
		}

		Vec3d vec3d = this.getPosition();

		if (vec3d == null)
		{
			return false;
		}
		else
		{
			return this.setPosFromVec(vec3d);
		}

	}

	private boolean setPosFromVec(Vec3d vec3d)
	{
		this.x = vec3d.x;
		this.y = vec3d.y;
		this.z = vec3d.z;

		this.mustUpdate = false;

		return true;
	}

	@Nullable
	protected Vec3d getPosition()
	{
		if (!this.favoriteBlocks.isEmpty())
		{
			for (int i = 0; i < 10; i++)
			{
				Vec3d pos = RandomPositionGenerator.findRandomTarget(this.entity, 10, 4);

				if (pos != null)
				{
					BlockPos blockPos = new BlockPos(pos);

					Block potential = this.entity.world.getBlockState(blockPos).getBlock();

					if (this.favoriteBlocks.contains(potential))
					{
						return pos;
					}
				}
			}
		}

		if (this.entity.getRNG().nextInt(8) == 0)
		{
			return RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
		}

		return null;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);

		if (!this.entity.getNavigator().noPath())
		{
			Path path = this.entity.getNavigator().getPath();

			for (int i = path.getCurrentPathIndex(); i < path.getCurrentPathLength(); i++)
			{
				PathPoint pp = path.getPathPointFromIndex(i);
				BlockPos blockPos = new BlockPos(pp.x, pp.y, pp.z);

				if (!this.entity.world.canSeeSky(blockPos))
				{
					//this.entity.getNavigator().clearPath();
				}
			}
		}
	}

	public void markUpdate()
	{
		this.mustUpdate = true;
	}

	public void setExecutionChance(int newChance)
	{
		this.executionChance = newChance;
	}
}
