package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.entity.ai.brain.behavior.AfterLongJumpFalling;
import com.aetherteam.aetherii.entity.ai.brain.behavior.NeutralAnimalPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.RamAttack;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;

import java.util.Optional;

public class BurrukaiAi {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 2400);
    public static final UniformInt TIME_BETWEEN_EAT = UniformInt.of(600, 1200);
    private static final TargetingConditions RAM_TARGET_CONDITIONS = TargetingConditions.forCombat()
            .selector(p_311675_ -> p_311675_ instanceof Kirrid kirrid && !kirrid.isBaby() && !kirrid.getBrain().hasMemoryValue(MemoryModuleType.RAM_COOLDOWN_TICKS) && kirrid.hasPlate() && p_311675_.level().getWorldBorder().isWithinBounds(p_311675_.getBoundingBox()));

    public static void initMemories(Burrukai pBurrukai, RandomSource pRandom) {
        pBurrukai.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(pRandom));
        pBurrukai.getBrain().setMemory(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), TIME_BETWEEN_EAT.sample(pRandom));
    }

    public static Brain<?> makeBrain(EntityType<? extends Burrukai> entityType, Burrukai burrukai, Brain<Burrukai> pBrain) {
        initCoreActivity(pBrain);
        initIdleActivity(entityType, pBrain);
        initFightActivity(burrukai, pBrain);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.useDefaultActivity();
        return pBrain;
    }

    private static void initCoreActivity(Brain<Burrukai> pBrain) {
        pBrain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new Swim(0.8F),
                        new NeutralAnimalPanic<>(1.25F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        new AfterLongJumpFalling(),
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get())
                )
        );
    }

    private static void initIdleActivity(EntityType<? extends Burrukai> entityType, Brain<Burrukai> pBrain) {
        pBrain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                        Pair.of(0, StartAttacking.create(BurrukaiAi::findNearestValidAttackTarget)),
                        Pair.of(1, new AnimalMakeLove(entityType)),
                        Pair.of(2, new FollowTemptation(p_149446_ -> 1.25F)),
                        Pair.of(3, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                        Pair.of(
                                4,
                                new RunOne<>(
                                        ImmutableList.of(
                                                Pair.of(RandomStroll.stroll(0.8F), 2), Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2), Pair.of(new DoNothing(30, 60), 1)
                                        )
                                )
                        )
                )
        );
    }

    private static void initFightActivity(Burrukai pBurrukai, Brain<Burrukai> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.<Mob>create(p_35118_ -> !isNearestValidAttackTarget(pBurrukai, p_35118_)),
                        new RamAttack(2.25F)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isNearestValidAttackTarget(Burrukai pBurrukai, LivingEntity pTarget) {
        return findNearestValidAttackTarget(pBurrukai).filter(entity -> entity == pTarget).isPresent();
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Burrukai burrukai) {
        if (burrukai.isBaby()) {
            return Optional.empty();
        }

        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(burrukai, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(burrukai, optional.get())) {
            return optional;
        }
        return Optional.empty();
    }

    private static Optional<? extends LivingEntity> getTargetIfWithinRange(Burrukai pBurrukai, MemoryModuleType<? extends LivingEntity> pMemoryType) {
        return pBurrukai.getBrain().getMemory(pMemoryType).filter(p_35108_ -> p_35108_.closerThan(pBurrukai, 18.0));
    }


    public static void updateActivity(Burrukai pBrain) {
        pBrain.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static void maybeRetaliate(Burrukai burrukai, LivingEntity pTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(burrukai, pTarget)) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(burrukai, pTarget, 4.0)) {
                if (!burrukai.isBaby()) {
                    setAngerTarget(burrukai, pTarget);
                }
                //broadcastAngerTarget(burrukai, pTarget);
            }
        }
    }


    private static Optional<NearestVisibleLivingEntities> getAdultBurrukai(Burrukai pBurrukai) {
        return pBurrukai.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    protected static void setAngerTarget(Burrukai pBurrukai, LivingEntity pTarget) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(pBurrukai, pTarget)) {
            pBurrukai.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            pBurrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, pTarget.getUUID(), 600L);

            if (pTarget.getType() == EntityType.PLAYER && pBurrukai.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                pBurrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

}
