package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.behavior.NeutralAnimalPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.burrukai.BurrukaiRamAttack;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;

import java.util.Optional;
import java.util.function.Predicate;

public class BurrukaiAi {
    public static final ImmutableList<SensorType<? extends Sensor<? super Burrukai>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            AetherIISensorTypes.BURRUKAI_TEMPTATIONS.get()
    );
    public static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY
    );
    public static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    public static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 2400);

    public static void initMemories(Burrukai burrukai, RandomSource random) {
        burrukai.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
    }

    public static Brain<?> makeBrain(EntityType<? extends Burrukai> entityType, Burrukai burrukai, Brain<Burrukai> brain) {
        initCoreActivity(brain);
        initIdleActivity(entityType, brain);
        initFightActivity(burrukai, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    private static void initCoreActivity(Brain<Burrukai> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim<>(0.8F),
                new NeutralAnimalPanic<>(1.25F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static void initIdleActivity(EntityType<? extends Burrukai> entityType, Brain<Burrukai> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(0, StartAttacking.create(BurrukaiAi::findNearestValidAttackTarget)),
                Pair.of(1, new AnimalMakeLove(entityType)),
                Pair.of(2, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(3, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                Pair.of(4, new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(0.8F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )))
        ));
    }

    private static void initFightActivity(Burrukai burrukai, Brain<Burrukai> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                StopAttackingIfTargetInvalid.create((serverLevel, livingEntity) -> !isNearestValidAttackTarget(serverLevel, burrukai, livingEntity)),
                new BurrukaiRamAttack(2.25F)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static boolean isNearestValidAttackTarget(ServerLevel serverLevel, Burrukai burrukai, LivingEntity target) {
        return findNearestValidAttackTarget(serverLevel, burrukai).filter(entity -> entity == target).isPresent();
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, Burrukai burrukai) {
        Brain<Burrukai> brain = burrukai.getBrain();

        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(burrukai, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(level, burrukai, (LivingEntity) optional.get())) {
            return optional;
        } else {
            if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
                Optional<Player> optional1 = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
                if (optional1.isPresent()) {
                    return optional1;
                }
            }
            return Optional.empty();
        }
    }

    public static void updateActivity(Burrukai owner) {
        owner.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
                Activity.FIGHT,
                Activity.IDLE
        ));
    }

    public static void maybeRetaliate(ServerLevel serverLevel, Burrukai burrukai, LivingEntity target) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(serverLevel, burrukai, target)) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(burrukai, target, 4.0)) {
                if (!burrukai.isBaby()) {
                    setAngerTarget(serverLevel, burrukai, target);
                }
            }
        }
    }

    protected static void setAngerTarget(ServerLevel serverLevel, Burrukai burrukai, LivingEntity target) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(serverLevel, burrukai, target)) {
            burrukai.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            burrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && serverLevel.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                burrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

    public static Predicate<ItemStack> getTemptations() {
        return (stack) -> stack.is(AetherIITags.Items.BURRUKAI_FOOD);
    }
}
