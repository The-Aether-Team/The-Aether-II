package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.entity.ai.brain.behavior.BabyOnlyAnimalPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.BetterStrollToPoi;
import com.aetherteam.aetherii.entity.ai.brain.behavior.FallRandomStroll;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;

import java.util.Optional;
import java.util.function.Predicate;

public class MoaAi {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static void initMemories(Moa pMoa, RandomSource pRandom) {
    }

    public static void initMoaHomeMemories(Moa pMoa, RandomSource pRandom) {
        pMoa.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(pMoa.level().dimension(), pMoa.blockPosition()));
    }

    public static Brain<?> makeBrain(Moa moa, Brain<Moa> pBrain) {
        initCoreActivity(pBrain);
        initIdleActivity(pBrain);
        initFightActivity(moa, pBrain);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.useDefaultActivity();
        return pBrain;
    }

    private static void initCoreActivity(Brain<Moa> pBrain) {
        pBrain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new Swim<>(0.8F),
                        new BabyOnlyAnimalPanic<>(0.14F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        StopBeingAngryIfTargetDead.create(),
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get()))
        );
    }

    private static void initIdleActivity(Brain<Moa> pBrain) {
        pBrain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                        Pair.of(2, BehaviorBuilder.triggerIf(Predicate.not(Moa::isPlayerGrown), BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.12F)))),
                        Pair.of(3, StartAttacking.<Moa>create(MoaAi::findNearestValidAttackTarget)),
                        Pair.of(
                                4,
                                new RunOne<>(
                                        ImmutableList.of(
                                                Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), FallRandomStroll.stroll(0.06F)), 2), Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), SetWalkTargetFromLookTarget.create(0.06F, 3)), 2),
                                                Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), BetterStrollToPoi.create(MemoryModuleType.HOME, 0.06F, 2, 6)), 2),
                                                Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), StrollAroundPoi.create(MemoryModuleType.HOME, 0.06F, 6)), 2),
                                                Pair.of(new DoNothing(30, 60), 1)
                                        )
                                )
                        )
                )
        );
    }


    private static void initFightActivity(Moa pMoa, Brain<Moa> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.create((serverLevel, livingEntity) -> !isNearestValidAttackTarget(serverLevel, pMoa, livingEntity)),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(0.14F),
                        MeleeAttack.create(20)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isNearestValidAttackTarget(ServerLevel serverLevel, Moa pMoa, LivingEntity pTarget) {
        return findNearestValidAttackTarget(serverLevel, pMoa).filter(entity -> entity == pTarget).isPresent();
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel serverLevel, Moa moa) {
        if (moa.isBaby()) {
            return Optional.empty();
        }

        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(moa, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(serverLevel, moa, optional.get())) {
            return optional;
        } else if (moa.getBrain().hasMemoryValue(MemoryModuleType.HOME)) {
            Optional<? extends LivingEntity> optional1 = getTargetIfWithinRange(moa, MemoryModuleType.NEAREST_VISIBLE_PLAYER);
            Optional<GlobalPos> globalPos = moa.getBrain().getMemory(MemoryModuleType.HOME);
            if (globalPos.isPresent() && optional1.isPresent()) {
                if (optional1.get().level().dimension() == globalPos.get().dimension()) {
                    if (globalPos.get().pos().distManhattan(optional1.get().blockPosition()) <= 5) {
                        //when near of nest. begin angry
                        return optional1;
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<? extends LivingEntity> getTargetIfWithinRange(Moa pMoa, MemoryModuleType<? extends LivingEntity> pMemoryType) {
        return pMoa.getBrain().getMemory(pMemoryType).filter(p_35108_ -> p_35108_.closerThan(pMoa, 18.0));
    }


    public static void updateActivity(Moa pBrain) {
        pBrain.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static void maybeRetaliate(ServerLevel serverLevel, Moa pMoa, LivingEntity pTarget) {
        if (!pMoa.isPlayerGrown()) {
            if (Sensor.isEntityAttackableIgnoringLineOfSight(serverLevel, pMoa, pTarget)) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(pMoa, pTarget, 4.0)) {
                    if (!pMoa.isBaby()) {
                        setAngerTarget(serverLevel, pMoa, pTarget);
                    }
                    broadcastAngerTarget(serverLevel, pMoa, pTarget);
                }
            }
        } else {
            pMoa.setSitting(false);
        }
    }


    private static Optional<NearestVisibleLivingEntities> getAdultMoa(Moa pMoa) {
        return pMoa.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    protected static void broadcastAngerTarget(ServerLevel serverLevel, Moa pMoa, LivingEntity pTarget) {
        Optional<NearestVisibleLivingEntities> moa = getAdultMoa(pMoa);

        if (moa.isPresent()) {
            for (LivingEntity moa1 : moa.get().findAll(entity -> {
                return !entity.isBaby() && entity instanceof Moa moa2 && !moa2.isPlayerGrown() && entity.getBrain().hasMemoryValue(MemoryModuleType.HOME);
            })) {
                setAngerTarget(serverLevel, (Moa) moa1, pTarget);
            }
        }
    }

    protected static void setAngerTarget(ServerLevel serverLevel, Moa pMoa, LivingEntity pTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(serverLevel, pMoa, pTarget)) {
            pMoa.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            pMoa.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, pTarget.getUUID(), 600L);

            if (pTarget.getType() == EntityType.PLAYER && serverLevel.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                pMoa.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }
}
