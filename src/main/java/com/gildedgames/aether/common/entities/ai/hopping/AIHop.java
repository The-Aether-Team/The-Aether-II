package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.EntityLiving;

public class AIHop extends EntityAI<EntityLiving>
{

    private HoppingMoveHelper hoppingMoveHelper;

    public AIHop(EntityLiving entity, HoppingMoveHelper hoppingMoveHelper)
    {
        super(entity);

        this.hoppingMoveHelper = hoppingMoveHelper;

        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return true;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.hoppingMoveHelper.setSpeed(1.0D);
    }
}
