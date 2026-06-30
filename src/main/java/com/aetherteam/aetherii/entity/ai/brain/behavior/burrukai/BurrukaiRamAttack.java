package com.aetherteam.aetherii.entity.ai.brain.behavior.burrukai;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BurrukaiRamAttack extends Behavior<Burrukai> {
    private final float speed;
    private int ramTick;
    @Nullable
    private BlockPos blockPos;

    public BurrukaiRamAttack(float speed) {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
        ));
        this.speed = speed;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Burrukai owner) {
        return owner.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Burrukai owner, long gameTime) {
        return owner.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void start(ServerLevel serverLevel, Burrukai owner, long gameTime) {
        this.ramTick = 0;
        serverLevel.broadcastEntityEvent(owner, (byte) Burrukai.RAM_START_EVENT);
        owner.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Burrukai owner, long gameTime) {
        super.stop(serverLevel, owner, gameTime);
        this.finishRam(serverLevel, owner);
    }

    @Override
    protected void tick(ServerLevel serverLevel, Burrukai owner, long gameTime) {
        Brain<?> brain = owner.getBrain();
        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);

        if (++this.ramTick >= 30) {
            if (target.isPresent() && target.get().isAlive()) {
                LivingEntity ramTarget = target.get();
                if (this.blockPos == null) {
                    Vec3 vec3 = Vec3.directionFromRotation(0.0F, ramTarget.getYRot()).reverse().scale(4.0F);
                    this.blockPos = ramTarget.blockPosition().offset(new Vec3i((int) vec3.x, (int) vec3.y, (int) vec3.z));
                }
                if (owner.distanceToSqr(ramTarget) < owner.getBbWidth() * owner.getBbWidth()) {
                    if (owner.doHurtTarget(ramTarget)) {
                        this.finishRam(serverLevel, owner);
                        serverLevel.broadcastEntityEvent(owner, (byte) Burrukai.RAM_START_EVENT);
                        serverLevel.playSound(null, owner, AetherIISoundEvents.ENTITY_BURRUKAI_RAM_IMPACT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        AetherIIDataAttachments.get(ramTarget, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(ramTarget, owner, EffectBuildupPresets.STUN, 350);
                    }
                } else if (this.ramTick >= 50) {
                    this.finishRam(serverLevel, owner);
                    serverLevel.broadcastEntityEvent(owner, (byte) Burrukai.RAM_START_EVENT);
                } else {
                    brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(this.blockPos, this.speed, 1));
                }
            }
        }
        if (target.isPresent() && target.get().isAlive()) {
            if (this.blockPos != null) {
                owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.blockPos));
            } else {
                owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target.get(), true));
            }
        }
    }

    protected void finishRam(ServerLevel serverLevel, Burrukai owner) {
        serverLevel.broadcastEntityEvent(owner, (byte) Burrukai.RAM_STOP_EVENT);
        this.ramTick = 0;
        this.blockPos = null;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return false;
    }
}
