package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.EntityLiving;

public class AIThrowBombs extends EntityAI<EntityLiving>
{

    private TickTimer timer;

    private double distanceRequired;

    public AIThrowBombs(EntityLiving entity, double distanceRequired)
    {
        super(entity);

        this.distanceRequired = distanceRequired;
        this.timer = new TickTimer();

        this.setMutexBits(6);
    }

    @Override
    public void startExecuting()
    {

    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity().getAttackTarget() != null && this.entity().getAttackTarget().getDistanceToEntity(this.entity()) <= this.distanceRequired;
    }

    @Override
    public boolean continueExecuting()
    {
        return true;
    }

    @Override
    public boolean isInterruptible()
    {
        return false;
    }

    @Override
    public void resetTask()
    {
        this.timer.reset();
    }

    @Override
    public void updateTask()
    {
        this.entity().setAIMoveSpeed(0.0F);
        this.entity().getNavigator().setSpeed(0.0F);
        this.entity().getNavigator().clearPathEntity();

        this.timer.tick();

        if (this.timer.getTicksPassed() >= 70)
        {
            

            this.timer.reset();
        }
    }

}
