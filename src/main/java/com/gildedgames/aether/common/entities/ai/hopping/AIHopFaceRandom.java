package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigateGround;

public class AIHopFaceRandom extends EntityAI<EntityLiving>
{

    private HoppingMoveHelper hoppingMoveHelper;

    private float chosenDegrees;

    private int nextRandomizeTime;

    public AIHopFaceRandom(EntityLiving entity, HoppingMoveHelper hoppingMoveHelper)
    {
        super(entity);

        this.hoppingMoveHelper = hoppingMoveHelper;

        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.entity().getAttackTarget() == null && (this.entity().onGround || this.entity().isInWater() || this.entity().isInLava() || this.entity().isPotionActive(MobEffects.LEVITATION));
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (--this.nextRandomizeTime <= 0)
        {
            this.nextRandomizeTime = 40 + this.entity().getRNG().nextInt(60);
            this.chosenDegrees = (float)this.entity().getRNG().nextInt(360);
        }

        this.hoppingMoveHelper.setDirection(this.chosenDegrees);
    }
}
