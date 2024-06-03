package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class FallableLongJumpMidJump extends Behavior<Mob> {
    private long startTime;
    private final UniformInt timeBetweenLongJumps;

    public FallableLongJumpMidJump(UniformInt timeBetweenLongJumps) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_PRESENT), 100);
        this.timeBetweenLongJumps = timeBetweenLongJumps;
    }

    protected boolean canStillUse(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        return !pEntity.onGround() && startTime + 15 > pGameTime;
    }


    @Override
    protected void start(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        pEntity.setDiscardFriction(true);
        pEntity.setPose(Pose.LONG_JUMPING);
    }

    @Override
    protected void stop(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        pEntity.getBrain().eraseMemory(MemoryModuleType.LONG_JUMP_MID_JUMP);
        pEntity.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.timeBetweenLongJumps.sample(pLevel.random));
    }
}
