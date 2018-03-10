package com.gildedgames.aether.common.entities.ai.necromancer;

import com.gildedgames.aether.common.entities.living.npc.EntityNecromancer;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecromancerAIGoUpTower extends EntityAIMoveToBlock
{

	private final EntityNecromancer necromancer;

	private final BlockPos[] positions;

	private int index;

	private BlockPos lastPos;

	private boolean shouldExecute;

	public NecromancerAIGoUpTower(final EntityNecromancer necromancer, final BlockPos... positions)
	{
		super(necromancer, 0.5D, 50);

		this.necromancer = necromancer;
		this.positions = positions;
	}

	public void setShouldExecute(final boolean shouldExecute)
	{
		this.shouldExecute = shouldExecute;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.necromancer.getDistanceSqToCenter(this.getCurrentPos().up()) <= 1.0D)
		{
			if (this.index < this.positions.length - 1)
			{
				this.index++;
			}
		}

		this.runDelay = 0;

		return super.shouldExecute() && this.shouldExecute;
	}

	private BlockPos getCurrentPos()
	{
		return this.positions[this.index];
	}

	@Override
	protected boolean shouldMoveTo(final World worldIn, final BlockPos pos)
	{
		if (!this.shouldExecute)
		{
			return false;
		}

		if (this.lastPos != null)
		{
			final double dist = this.getCurrentPos().getDistance(pos.getX(), pos.getY(), pos.getZ());
			final double lastDist = this.getCurrentPos().getDistance(this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ());

			if (dist < lastDist)
			{
				this.lastPos = pos;

				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			final double dist = this.getCurrentPos().getDistance(pos.getX(), pos.getY(), pos.getZ());
			final double distNow = this.getCurrentPos()
					.getDistance(this.necromancer.getPosition().getX(), this.necromancer.getPosition().getY(), this.necromancer.getPosition().getZ());

			if (dist < distNow)
			{
				this.lastPos = pos;

				return true;
			}
		}

		return false;
	}

	@Override
	public void updateTask()
	{
		super.updateTask();
	}
}
