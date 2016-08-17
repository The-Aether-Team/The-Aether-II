package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;

import java.util.List;

public class AIDetonateClose extends EntityAI<EntityLiving>
{

    private TickTimer timer;

    private Class<? extends Entity> detonateNear;

    private double distanceRequired;

    public AIDetonateClose(EntityLiving entity, Class<? extends Entity> detonateNear, double distanceRequired)
    {
        super(entity);

        this.detonateNear = detonateNear;
        this.distanceRequired = distanceRequired;
        this.timer = new TickTimer();

        this.setMutexBits(6);
    }

    @Override
    public void startExecuting()
    {
        this.entity().playSound(SoundsAether.detonating, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public boolean shouldExecute()
    {
        List<Entity> entityList = this.entity().worldObj.getEntitiesWithinAABB(this.detonateNear, this.entity().getEntityBoundingBox().expand(this.distanceRequired, this.distanceRequired, this.distanceRequired));

        int playerCount = 0;
        int creativeCount = 0;

        for (Entity entity : entityList)
        {
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;

                playerCount++;

                if (player.capabilities.isCreativeMode)
                {
                    creativeCount++;
                }
            }
        }

        return !entityList.isEmpty() && creativeCount < playerCount;
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
    public void updateTask()
    {
        this.entity().setAIMoveSpeed(0.0F);
        this.entity().getNavigator().setSpeed(0.0F);
        this.entity().getNavigator().clearPathEntity();

        this.timer.tick();

        if (this.timer.getTicksPassed() >= 70)
        {
            this.entity().playSound(SoundsAether.detonate, 1.0F, (this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);

            this.entity().setDead();

            this.entity().worldObj.newExplosion(this.entity(), this.entity().posX, this.entity().posY, this.entity().posZ, 2, false, false);
        }
    }

}
