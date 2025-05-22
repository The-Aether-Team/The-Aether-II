package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;

public class AfterLongJumpFalling extends Behavior<Mob> {
    private final Holder<SoundEvent> stepSound;

    public AfterLongJumpFalling(Holder<SoundEvent> stepSound) {
        super(ImmutableMap.of(), 10);
        this.stepSound = stepSound;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Mob owner) {
        return owner.onGround() && owner.getPose() == Pose.LONG_JUMPING;
    }

    @Override
    protected void start(ServerLevel serverLevel, Mob owner, long gameTime) {
        super.start(serverLevel, owner, gameTime);
        if (owner.onGround()) {
            owner.setDeltaMovement(owner.getDeltaMovement().multiply(0.1F, 1.0, 0.1F));
            serverLevel.playSound(null, owner, this.stepSound.value(), SoundSource.NEUTRAL, 2.0F, 1.0F);
        }
        owner.setPose(Pose.STANDING);
        owner.setDiscardFriction(false);
    }
}
