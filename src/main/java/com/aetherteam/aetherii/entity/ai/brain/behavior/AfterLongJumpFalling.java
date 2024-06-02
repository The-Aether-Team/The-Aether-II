package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;

public class AfterLongJumpFalling extends Behavior<Mob> {
    public AfterLongJumpFalling() {
        super(ImmutableMap.of(), 10);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel pLevel, Mob pOwner) {
        return pOwner.onGround() && pOwner.getPose() == Pose.LONG_JUMPING;
    }


    @Override
    protected void start(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        super.start(pLevel, pEntity, pGameTime);
        if (pEntity.onGround()) {
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(0.1F, 1.0, 0.1F));
            pLevel.playSound(null, pEntity, SoundEvents.GOAT_STEP, SoundSource.NEUTRAL, 2.0F, 1.0F);
        }
        pEntity.setPose(Pose.STANDING);
        pEntity.setDiscardFriction(false);

    }
}
