package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.entity.passive.Prismallard;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

import java.util.EnumSet;

public class LookAtThreatGoal extends LookAtPlayerGoal {
    private final Prismallard prismallard;

    public LookAtThreatGoal(Prismallard prismallard, Class<? extends LivingEntity> mobClass, float lookDistance) {
        super(prismallard, mobClass, lookDistance, 1.0F);
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
        this.prismallard = prismallard;
    }

    @Override
    public boolean canUse() {
        return this.prismallard.onGround() && !this.prismallard.isInLove() && super.canUse() && this.lookAt != null && this.lookAt.getClass() != prismallard.getClass();
    }

    @Override
    public boolean canContinueToUse() {
        return this.prismallard.onGround() && !this.prismallard.isInLove() && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        this.prismallard.setThreat(true);
        this.prismallard.getNavigation().stop();
    }

    @Override
    public void stop() {
        super.stop();
        this.prismallard.setThreat(false);
    }
}
