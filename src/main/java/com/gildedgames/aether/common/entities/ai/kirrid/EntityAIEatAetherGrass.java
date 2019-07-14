package com.gildedgames.aether.common.entities.ai.kirrid;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.EnumSet;

public class EntityAIEatAetherGrass extends Goal
{
	/** The entity owner of this AITask */
	private final MobEntity entity;

	/** The world the grass eater entity is eating from */
	private final World world;

	/** Number of ticks since the entity started to eat grass */
	private int timer;

	/** Chance of executing **/
	private int chance;

	public EntityAIEatAetherGrass(final MobEntity grassEaterEntityIn, int chance)
	{
		this.entity = grassEaterEntityIn;
		this.world = grassEaterEntityIn.world;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		this.chance = chance;
	}


	public EntityAIEatAetherGrass(final MobEntity grassEaterEntityIn)
	{
		this(grassEaterEntityIn, 1000);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (this.entity.getRNG().nextInt(this.entity.isChild() ? 50 : this.chance) != 0)
		{
			return false;
		}
		else
		{
			final BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.posY, this.entity.posZ);
			return this.world.getBlockState(blockpos).getBlock() == BlocksAether.tall_aether_grass
					|| this.world.getBlockState(blockpos.down()).getBlock() == BlocksAether.aether_grass;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.timer = 40;
		this.world.setEntityState(this.entity, (byte) 10);
		this.entity.getNavigator().clearPath();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.timer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return this.timer > 0;
	}

	/**
	 * Number of ticks since the entity started to eat grass
	 */
	public int getTimer()
	{
		return this.timer;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void tick()
	{
		this.timer = Math.max(0, this.timer - 1);

		if (this.timer == 4)
		{
			final BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.posY, this.entity.posZ);

			if (this.world.getBlockState(blockpos).getBlock() == BlocksAether.tall_aether_grass)
			{
				if (this.world.getGameRules().func_223586_b(GameRules.field_223599_b))
				{
					this.world.destroyBlock(blockpos, false);
				}

				this.entity.eatGrassBonus();
			}
			else
			{
				final BlockPos blockpos1 = blockpos.down();

				if (this.world.getBlockState(blockpos1).getBlock() == BlocksAether.aether_grass)
				{
					if (this.world.getGameRules().func_223586_b(GameRules.field_223599_b))
					{
						this.world.playEvent(2001, blockpos1, Block.getStateId(BlocksAether.aether_grass.getDefaultState()));
						this.world.setBlockState(blockpos1, BlocksAether.aether_dirt.getDefaultState(), 2);
					}

					this.entity.eatGrassBonus();
				}
			}
		}
	}
}
