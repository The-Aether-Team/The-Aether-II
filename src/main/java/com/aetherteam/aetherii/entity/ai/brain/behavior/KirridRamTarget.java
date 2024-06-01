package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class KirridRamTarget extends Behavior<Kirrid> {
    public static final int TIME_OUT_DURATION = 200;
    public static final float RAM_SPEED_FORCE_FACTOR = 1.65F;
    private final Function<Kirrid, UniformInt> getTimeBetweenRams;
    private final TargetingConditions ramTargeting;
    private final float speed;
    private final ToDoubleFunction<Kirrid> getKnockbackForce;
    private Vec3 ramDirection;
    private final Function<Kirrid, SoundEvent> getImpactSound;
    private final Function<Kirrid, SoundEvent> getHornBreakSound;

    public KirridRamTarget(
            Function<Kirrid, UniformInt> pGetTimeBetweenRams,
            TargetingConditions pRamTargeting,
            float pSpeed,
            ToDoubleFunction<Kirrid> pGetKnockbackForce,
            Function<Kirrid, SoundEvent> pGetImpactSound,
            Function<Kirrid, SoundEvent> pGetHornBreakSound
    ) {
        super(ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), 200);
        this.getTimeBetweenRams = pGetTimeBetweenRams;
        this.ramTargeting = pRamTargeting;
        this.speed = pSpeed;
        this.getKnockbackForce = pGetKnockbackForce;
        this.getImpactSound = pGetImpactSound;
        this.getHornBreakSound = pGetHornBreakSound;
        this.ramDirection = Vec3.ZERO;
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, Kirrid pOwner) {
        return pOwner.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    protected boolean canStillUse(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        return pEntity.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    protected void start(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        BlockPos blockpos = pEntity.blockPosition();
        Brain<?> brain = pEntity.getBrain();
        Vec3 vec3 = brain.getMemory(MemoryModuleType.RAM_TARGET).get();
        this.ramDirection = new Vec3((double) blockpos.getX() - vec3.x(), 0.0, (double) blockpos.getZ() - vec3.z()).normalize();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3, this.speed, 0));
    }

    protected void tick(ServerLevel pLevel, Kirrid pOwner, long pGameTime) {
        List<LivingEntity> list = pLevel.getNearbyEntities(LivingEntity.class, this.ramTargeting, pOwner, pOwner.getBoundingBox());
        Brain<?> brain = pOwner.getBrain();
        if (!list.isEmpty()) {
            LivingEntity livingentity = list.get(0);
            if (livingentity.hurt(pLevel.damageSources().noAggroMobAttack(pOwner), (float) 0)) {
                if (livingentity instanceof Kirrid kirrid && kirrid.dropHorn()) {
                    this.finishRam(pLevel, pOwner);
                } else {
                    this.finishRam(pLevel, pOwner);
                }
            }
            int i = pOwner.hasEffect(MobEffects.MOVEMENT_SPEED) ? pOwner.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
            int j = pOwner.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pOwner.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
            float f = 0.25F * (float) (i - j);
            float f1 = Mth.clamp(pOwner.getSpeed() * 1.65F, 0.2F, 3.0F) + f;
            float f2 = livingentity.isDamageSourceBlocked(pLevel.damageSources().mobAttack(pOwner)) ? 0.5F : 1.0F;
            livingentity.knockback((double) (f2 * f1) * this.getKnockbackForce.applyAsDouble(pOwner), this.ramDirection.x(), this.ramDirection.z());
            pLevel.playSound(null, pOwner, this.getImpactSound.apply(pOwner), SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else if (this.hasRammedHornBreakingBlock(pLevel, pOwner)) {
            pLevel.playSound(null, pOwner, this.getImpactSound.apply(pOwner), SoundSource.NEUTRAL, 1.0F, 1.0F);
            /*boolean flag = pOwner.dropHorn();
            if (flag) {
                pLevel.playSound(null, pOwner, this.getHornBreakSound.apply(pOwner), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }*/

            this.finishRam(pLevel, pOwner);
        } else {
            Optional<WalkTarget> optional = brain.getMemory(MemoryModuleType.WALK_TARGET);
            Optional<Vec3> optional1 = brain.getMemory(MemoryModuleType.RAM_TARGET);
            boolean flag1 = optional.isEmpty() || optional1.isEmpty() || optional.get().getTarget().currentPosition().closerThan(optional1.get(), 0.25);
            if (flag1) {
                this.finishRam(pLevel, pOwner);
            }
        }
    }

    private boolean hasRammedHornBreakingBlock(ServerLevel pLevel, Kirrid pOwner) {
        Vec3 vec3 = pOwner.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize();
        BlockPos blockpos = BlockPos.containing(pOwner.position().add(vec3));
        return pLevel.getBlockState(blockpos).is(BlockTags.SNAPS_GOAT_HORN) || pLevel.getBlockState(blockpos.above()).is(BlockTags.SNAPS_GOAT_HORN);
    }

    protected void finishRam(ServerLevel pLevel, Kirrid pOwner) {
        pLevel.broadcastEntityEvent(pOwner, (byte) 59);
        pOwner.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getTimeBetweenRams.apply(pOwner).sample(pLevel.random));
        pOwner.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
    }
}
