package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class PhygPanicGoal extends PanicGoal {
    public PhygPanicGoal(Phyg phyg, double speed) {
        super(phyg, speed);
    }

    @Override
    protected boolean shouldPanic() {
        return super.shouldPanic() || this.mob.getFirstPassenger() instanceof LivingEntity livingEntity && !livingEntity.isHolding(itemstack -> itemstack.is(AetherIITags.Items.PHYG_CALM_ITEMS));
    }
}
