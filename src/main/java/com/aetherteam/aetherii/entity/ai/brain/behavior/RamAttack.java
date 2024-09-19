package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.passive.AetherAnimal;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RamAttack extends Behavior<AetherAnimal> {
    private final TargetingConditions ramTargeting = TargetingConditions.DEFAULT;
    private final float speed;
    private int ramTick;
    @Nullable
    private BlockPos blockPos;

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
        pEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    protected void tick(ServerLevel pLevel, AetherAnimal pOwner, long pGameTime) {
        Brain<?> brain = pOwner.getBrain();
        Optional<LivingEntity> optional = brain.getMemory(MemoryModuleType.ATTACK_TARGET);

        if (++ramTick >= 60) {

            if (optional.isPresent() && optional.get().isAlive()) {

                LivingEntity target = optional.get();
                if (blockPos == null) {
                    Vec3 vec3 = target.calculateViewVector(0.0F, target.getYRot()).reverse().scale(4.0F);
                    blockPos = target.blockPosition().offset(new Vec3i((int) vec3.x, (int) vec3.y, (int) vec3.z));
                }
                if (blockPos != null) {
                    if (pOwner.distanceToSqr(target) < pOwner.getBbWidth() * pOwner.getBbWidth()) {
                        if (pOwner.doHurtTarget(target)) {
                            this.finishRam(pLevel, pOwner);
                            pLevel.broadcastEntityEvent(pOwner, (byte) 61);
                            pLevel.playSound(null, pOwner, SoundEvents.GOAT_RAM_IMPACT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                            target.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.STUN, 500);
                        }
                    } else if (blockPos.distSqr(pOwner.blockPosition()) < 5 || this.ramTick >= 100) {
                        this.finishRam(pLevel, pOwner);
                        pLevel.broadcastEntityEvent(pOwner, (byte) 61);
                    } else {
                        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, this.speed, 1));
                    }
                }
            }
        }
        if (optional.isPresent() && optional.get().isAlive() && blockPos != null) {
            pOwner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(blockPos));
        }
    }


    protected void finishRam(ServerLevel pLevel, AetherAnimal pOwner) {
        pLevel.broadcastEntityEvent(pOwner, (byte) 62);
        this.ramTick = 0;
        this.blockPos = null;
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
