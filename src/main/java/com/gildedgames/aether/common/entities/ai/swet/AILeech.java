package com.gildedgames.aether.common.entities.ai.swet;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;

public class AILeech extends EntityAI<EntitySwet>
{

    private TickTimer timer;

    private HoppingMoveHelper hoppingMoveHelper;

    private double leechDistance;

    public AILeech(EntitySwet entity, HoppingMoveHelper hoppingMoveHelper, double leechDistance)
    {
        super(entity);

        this.timer = new TickTimer();
        this.hoppingMoveHelper = hoppingMoveHelper;
        this.leechDistance = leechDistance;

        this.setMutexBits(2);
    }

    @Override
    public void startExecuting()
    {

    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity().getAttackTarget() != null && this.entity().canEntityBeSeen(this.entity().getAttackTarget()) && this.entity().getDistanceToEntity(this.entity().getAttackTarget()) <= this.leechDistance;
    }

    @Override
    public boolean continueExecuting()
    {
        return !this.entity().isDead && this.entity().getHealth() > 0 && this.shouldExecute();
    }

    @Override
    public boolean isInterruptible()
    {
        return false;
    }

    @Override
    public void updateTask()
    {
        this.entity().faceEntity(this.entity().getAttackTarget(), 10.0F, 10.0F);

        this.entity().setAIMoveSpeed(0.0F);
        this.entity().getNavigator().setSpeed(0.0F);
        this.entity().getNavigator().clearPathEntity();
        this.hoppingMoveHelper.setSpeed(0.0D);

        this.timer.tick();

        if (this.timer.getTicksPassed() >= 40)
        {
            this.entity().setAttacked(false);

            if (this.entity().getAttackTarget() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)this.entity().getAttackTarget();

                player.getFoodStats().setFoodLevel((int) (player.getFoodStats().getFoodLevel() * 0.95F));
            }

            this.entity().setAttacked(true);

            this.timer.reset();

            this.entity().playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
        }
    }

}
