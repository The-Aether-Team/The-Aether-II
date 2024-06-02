package com.aetherteam.aetherii.entity.ai.brain.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;

public class FallableLongJumpMidJump extends LongJumpMidJump {
    private long startTime;

    public FallableLongJumpMidJump(UniformInt timeBetweenLongJumps, SoundEvent sound) {
        super(timeBetweenLongJumps, sound);
    }

    protected boolean canStillUse(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        return !pEntity.onGround() || startTime + 5 < pGameTime;
    }


    @Override
    protected void start(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        super.start(pLevel, pEntity, pGameTime);
        startTime = pGameTime;
    }

    @Override
    protected void stop(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        super.stop(pLevel, pEntity, pGameTime);
        if (pEntity.onGround()) {
            pEntity.setPose(Pose.LONG_JUMPING);
        }
    }
}
