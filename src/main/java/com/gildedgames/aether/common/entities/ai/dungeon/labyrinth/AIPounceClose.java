package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;

public class AIPounceClose extends EntityAI<EntityLiving>
{

    private TickTimer timer;

    private double distanceRequired;

    private HoppingMoveHelper hoppingMoveHelper;

    private BlockPos pounceTo;

	private boolean continueExecuting;

    public AIPounceClose(EntityLiving entity, HoppingMoveHelper hoppingMoveHelper, double distanceRequired)
    {
        super(entity);

        this.hoppingMoveHelper = hoppingMoveHelper;
        this.distanceRequired = distanceRequired;
        this.timer = new TickTimer();

        this.setMutexBits(6);
    }

    @Override
    public void startExecuting()
    {
    	this.continueExecuting = true;
        this.pounceTo = this.entity().getAttackTarget().getPosition();
    }

    @Override
    public boolean shouldExecute()
    {
    	if (this.entity().getAttackTarget() == null)
		{
			return false;
		}

    	double dist = this.entity().getAttackTarget().getDistanceToEntity(this.entity());

        return dist <= this.distanceRequired;
    }

    @Override
    public boolean continueExecuting()
    {
        return this.continueExecuting;
    }

    @Override
    public boolean isInterruptible()
    {
        return false;
    }

    @Override
    public void updateTask()
    {
       	this.entity().setAIMoveSpeed(0.0F);
        this.entity().getNavigator().setSpeed(0.0F);
		this.hoppingMoveHelper.setDirection(this.entity().rotationYaw);
        this.entity().getNavigator().clearPathEntity();

        EntityUtil.facePos(this.entity(), this.pounceTo, 10.0F, 10.0F);

        this.timer.tick();

        if (this.timer.getTicksPassed() >= 20)
        {
            this.entity().setAIMoveSpeed(4.0F);
            this.entity().getNavigator().setSpeed(4.0F);
            this.hoppingMoveHelper.setSpeed(4.0D);
            this.hoppingMoveHelper.setDirection(this.entity().rotationYaw);

			if (this.entity().getAttackTarget() != null)
			{
				this.pounceTo = this.entity().getAttackTarget().getPosition();
			}
        }

        if (this.timer.getTicksPassed() >= 30)
		{
			this.timer.reset();

			this.entity().setAIMoveSpeed(1.5F);
			this.entity().getNavigator().setSpeed(1.5F);
			this.hoppingMoveHelper.setSpeed(1.5D);

			this.continueExecuting = false;
		}
    }

}
