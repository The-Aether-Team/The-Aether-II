package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.AetherAnimal;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.Optional;

public class RamAttack extends Behavior<AetherAnimal> {
    private final TargetingConditions ramTargeting = TargetingConditions.DEFAULT;
    private final float speed;
    private int ramTick;

    public RamAttack(
            float pSpeed
    ) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), 600);
        this.speed = pSpeed;
    }

    private LivingEntity getTarget(AetherAnimal pAnimal) {
        return pAnimal.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, AetherAnimal entity, long gameTime) {
        return getTarget(entity) != null;
    }

    protected void start(ServerLevel pLevel, AetherAnimal pEntity, long pGameTime) {
        this.ramTick = 0;
        pLevel.broadcastEntityEvent(pEntity, (byte) 61);
    }

    protected void tick(ServerLevel pLevel, AetherAnimal pOwner, long pGameTime) {
        Brain<?> brain = pOwner.getBrain();
        Optional<LivingEntity> optional = brain.getMemory(MemoryModuleType.ATTACK_TARGET);

        if (++ramTick >= 80) {
            if (optional.isPresent() && optional.get().isAlive()) {
                LivingEntity target = optional.get();
                if (pOwner.distanceToSqr(target) < pOwner.getBbWidth() * pOwner.getBbWidth()) {
                    if (target.hurt(pOwner.damageSources().mobAttack(pOwner), 6)) {
                        this.finishRam(pLevel, pOwner);
                        pLevel.broadcastEntityEvent(pOwner, (byte) 61);
                        pLevel.playSound(null, pOwner, SoundEvents.GOAT_RAM_IMPACT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    }
                } else {
                    brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target, this.speed, 1));
                }
            }
        }
        if (optional.isPresent() && optional.get().isAlive()) {
            pOwner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(optional.get(), true));
        }
    }


    protected void finishRam(ServerLevel pLevel, AetherAnimal pOwner) {
        pLevel.broadcastEntityEvent(pOwner, (byte) 62);
        this.ramTick = 0;
    }

    @Override
    protected void stop(ServerLevel pLevel, AetherAnimal pEntity, long pGameTime) {
        super.stop(pLevel, pEntity, pGameTime);
        this.finishRam(pLevel, pEntity);
    }

    @Override
    protected boolean timedOut(long pGameTime) {
        return false;
    }
}
