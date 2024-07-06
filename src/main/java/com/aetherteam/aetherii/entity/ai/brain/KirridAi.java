package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.entity.ai.brain.behavior.*;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.schedule.Activity;

public class KirridAi {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 2400);
    public static final UniformInt TIME_BETWEEN_EAT = UniformInt.of(600, 1200);
    private static final TargetingConditions RAM_TARGET_CONDITIONS = TargetingConditions.forCombat()
            .selector(p_311675_ -> p_311675_ instanceof Kirrid kirrid && !kirrid.isBaby() && !kirrid.getBrain().hasMemoryValue(MemoryModuleType.RAM_COOLDOWN_TICKS) && kirrid.hasPlate() && p_311675_.level().getWorldBorder().isWithinBounds(p_311675_.getBoundingBox()));

    public static void initMemories(Kirrid pKirrid, RandomSource pRandom) {
        pKirrid.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(pRandom));
        pKirrid.getBrain().setMemory(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), TIME_BETWEEN_EAT.sample(pRandom));
    }

    public static Brain<?> makeBrain(EntityType<? extends Kirrid> entityType, Brain<Kirrid> pBrain) {
        initCoreActivity(pBrain);
        initIdleActivity(entityType, pBrain);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.useDefaultActivity();
        return pBrain;
    }

    private static void initCoreActivity(Brain<Kirrid> pBrain) {
        pBrain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new Swim(0.8F),
                        new KirridPanic(2.0F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        new AfterLongJumpFalling(),
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get())
                )
        );
    }

    private static void initIdleActivity(EntityType<? extends Kirrid> entityType, Brain<Kirrid> pBrain) {
        pBrain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                        Pair.of(0, new AnimalMakeLove(entityType)),
                        Pair.of(1, new FollowTemptation(p_149446_ -> 1.25F)),
                        Pair.of(2, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                        Pair.of(
                                3,
                                new RunOne<>(
                                        ImmutableList.of(
                                                Pair.of(FallRandomStroll.stroll(1.5F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2), Pair.of(new KirridRamTarget(
                                                        p_149474_ -> TIME_BETWEEN_RAMS,
                                                        RAM_TARGET_CONDITIONS,
                                                        1.5F,
                                                        p_149468_ -> SoundEvents.GOAT_RAM_IMPACT
                                                ), 2), Pair.of(new KirridEatGrass(), 2), Pair.of(new DoNothing(30, 60), 1)
                                        )
                                )
                        )
                )
        );
    }

    public static void updateActivity(Kirrid pBrain) {
        pBrain.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.RAM, Activity.IDLE));
    }
}
