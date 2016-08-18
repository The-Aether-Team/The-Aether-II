package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class AIAlarmClose extends EntityAI<EntityLiving>
{

    private TickTimer timer;

    private double distanceRequired;

    public AIAlarmClose(EntityLiving entity, double distanceRequired)
    {
        super(entity);

        this.distanceRequired = distanceRequired;
        this.timer = new TickTimer();

        this.setMutexBits(6);
    }

    @Override
    public void startExecuting()
    {
        List<EntityLiving> entityList = this.entity().worldObj.getEntitiesWithinAABB(EntityLiving.class, this.entity().getEntityBoundingBox().expand(this.distanceRequired, this.distanceRequired, this.distanceRequired));

        for (EntityLiving entity : entityList)
        {
            if (entity == this.entity() || entity.getClass() == this.entity().getClass())
            {
                continue;
            }

            entity.getNavigator().tryMoveToEntityLiving(this.entity().getAttackTarget(), 2.0D);
            entity.setAttackTarget(this.entity().getAttackTarget());
        }
    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity().getAttackTarget() != null && this.entity().getAttackTarget().getDistanceToEntity(this.entity()) <= this.distanceRequired && this.entity().canEntityBeSeen(this.entity().getAttackTarget());
    }

    @Override
    public boolean continueExecuting()
    {
        return this.entity().getAttackTarget() != null && (this.entity().canEntityBeSeen(this.entity().getAttackTarget()) || this.timer.getSecondsPassed() <= 10);
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
        this.timer.tick();
    }

}
