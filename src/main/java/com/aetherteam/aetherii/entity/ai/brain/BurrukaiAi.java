package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.behavior.NeutralAnimalPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.burrukai.BurrukaiRamAttack;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import com.aetherteam.aetherii.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public static final Brain.Provider<Burrukai> BRAIN_PROVIDER = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);

    public static List<ActivityData<Burrukai>> getActivities(EntityType<? extends Burrukai> entityType) {
        return List.of(initCoreActivity(), initIdleActivity(entityType), initFightActivity());
    }

    public static Brain<Burrukai> makeBrain(Burrukai owner, Dynamic<?> dynamic) {
        Brain<Burrukai> brain = BRAIN_PROVIDER.makeBrain(dynamic);
        getActivities((EntityType<? extends Burrukai>) owner.getType()).forEach(activity -> activity.addToBrain(brain));
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initMemories(Burrukai burrukai, RandomSource random) {
        burrukai.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
    }


    private static ActivityData<Burrukai> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new NeutralAnimalPanic<>(1.25F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static ActivityData<Burrukai> initIdleActivity(EntityType<? extends Burrukai> entityType) {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(0, StartAttacking.create(burrukai -> findNearestValidAttackTarget((ServerLevel) burrukai.level(), burrukai))),
                Pair.of(1, new AnimalMakeLove(entityType, 1.0F)),
                Pair.of(2, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(3, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                Pair.of(4, new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(0.8F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )))
        ));
    }

    private static ActivityData<Burrukai> initFightActivity() {
        return ActivityData.create(Activity.FIGHT, 10, ImmutableList.of(
                StopAttackingIfTargetInvalid.create(),
                new BurrukaiRamAttack(2.25F)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, Burrukai burrukai) {
        Brain<Burrukai> brain = burrukai.getBrain();

        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(burrukai, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(burrukai, (LivingEntity) optional.get())) {
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
        if (Sensor.isEntityAttackableIgnoringLineOfSight(burrukai, target)) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(burrukai, target, 4.0)) {
                if (!burrukai.isBaby()) {
                    setAngerTarget(serverLevel, burrukai, target);
                }
            }
        }
    }

    protected static void setAngerTarget(ServerLevel serverLevel, Burrukai burrukai, LivingEntity target) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(burrukai, target)) {
            burrukai.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            burrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && serverLevel.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                burrukai.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(AetherIITags.Items.BURRUKAI_FOOD);
    }
}
