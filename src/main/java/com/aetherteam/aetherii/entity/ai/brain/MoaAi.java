package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.entity.ai.brain.behavior.BabyOnlyAnimalPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.BetterStrollToPoi;
import com.aetherteam.aetherii.entity.ai.brain.behavior.FallRandomStroll;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import com.aetherteam.aetherii.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class MoaAi {
    public static final ImmutableList<SensorType<? extends Sensor<? super Moa>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY
    );
    public static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.HOME
    );

    public static final Brain.Provider<Moa> BRAIN_PROVIDER = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static List<ActivityData<Moa>> getActivities() {
        return List.of(initCoreActivity(), initIdleActivity(), initFightActivity());
    }

    public static Brain<Moa> makeBrain(Moa owner, Dynamic<?> dynamic) {
        Brain<Moa> brain = BRAIN_PROVIDER.makeBrain(dynamic);
        getActivities().forEach(activity -> activity.addToBrain(brain));
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initMoaHomeMemories(Moa owner, RandomSource random) {
        owner.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(owner.level().dimension(), owner.blockPosition()));
    }


    private static ActivityData<Moa> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new BabyOnlyAnimalPanic<>(0.14F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                StopBeingAngryIfTargetDead.create(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS),
                new CountDownCooldownTicks(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get())
        ));
    }

    private static ActivityData<Moa> initIdleActivity() {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(2, BehaviorBuilder.triggerIf(Predicate.not(Moa::isPlayerGrown), BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.12F)))),
                Pair.of(3, StartAttacking.create(moa -> findNearestValidAttackTarget((ServerLevel) moa.level(), moa))),
                Pair.of(4, new RunOne<>(ImmutableList.of(
                        Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), FallRandomStroll.stroll(0.06F)), 2),
                        Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), SetWalkTargetFromLookTarget.create(0.06F, 3)), 2),
                        Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), BetterStrollToPoi.create(MemoryModuleType.HOME, 0.06F, 2, 6)), 2),
                        Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Moa::isSitting), StrollAroundPoi.create(MemoryModuleType.HOME, 0.06F, 6)), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )))
        ));
    }

    private static ActivityData<Moa> initFightActivity() {
        return ActivityData.create(Activity.FIGHT, 10, ImmutableList.of(
                StopAttackingIfTargetInvalid.create(),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(0.14F),
                MeleeAttack.create(5)
        ), MemoryModuleType.ATTACK_TARGET);
    }


    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel serverLevel, Moa owner) {

        Brain<Moa> brain = owner.getBrain();

        if (owner.isBaby()) {
            return Optional.empty();
        }

        Optional<LivingEntity> target = BehaviorUtils.getLivingEntityFromUUIDMemory(owner, MemoryModuleType.ANGRY_AT);
        if (target.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(owner, target.get()) && target.filter(player -> player.closerThan(owner, 6.0)).isPresent()) { //todo track line of sight and distance and follow range and dont make it too fast.
            return target;
        } else if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
            Optional<Player> optional1 = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
            if (optional1.isPresent()) {
                return optional1;
            }
        } else if (owner.getBrain().hasMemoryValue(MemoryModuleType.HOME)) {
            Optional<Player> nearestPlayer = getTargetIfWithinRange(owner, MemoryModuleType.NEAREST_VISIBLE_PLAYER); //todo they need to be able to have the moa see the player if its near the nest and ignore follow range.
            Optional<GlobalPos> homePos = owner.getBrain().getMemory(MemoryModuleType.HOME);

            if (homePos.isPresent() && nearestPlayer.isPresent()) {
                if (nearestPlayer.get().level().dimension() == homePos.get().dimension()) {
                    if (homePos.get().pos().distManhattan(nearestPlayer.get().blockPosition()) <= 5) {
                        // When near nest, begin anger.
                        return nearestPlayer;
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<Player> getTargetIfWithinRange(Moa owner, MemoryModuleType<Player> nearestPlayerMemory) {
        return owner.getBrain().getMemory(nearestPlayerMemory).filter(player -> player.closerThan(owner, 18.0));
    }

    public static void updateActivity(Moa owner) {
        owner.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static void maybeRetaliate(ServerLevel serverLevel, Moa owner, LivingEntity target) {
        if (!owner.isPlayerGrown()) {
            if (Sensor.isEntityAttackableIgnoringLineOfSight(owner, target)) {
                if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(owner, target, 4.0)) {
                    if (!owner.isBaby()) {
                        setAngerTarget(serverLevel, owner, target);
                    }
                    broadcastAngerTarget(serverLevel, owner, target);
                }
            }
        } else {
            owner.setSitting(false);
        }
    }

    private static Optional<NearestVisibleLivingEntities> getAdultMoa(Moa owner) {
        return owner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    protected static void broadcastAngerTarget(ServerLevel serverLevel, Moa owner, LivingEntity target) {
        Optional<NearestVisibleLivingEntities> moa = getAdultMoa(owner);

        if (moa.isPresent()) {
            for (LivingEntity moa1 : moa.get().findAll(entity -> !entity.isBaby()
                    && entity instanceof Moa moa2
                    && !moa2.isPlayerGrown()
                    && entity.getBrain().hasMemoryValue(MemoryModuleType.HOME))) {
                setAngerTarget(serverLevel, (Moa) moa1, target);
            }
        }
    }

    protected static void setAngerTarget(ServerLevel serverLevel, Moa owner, LivingEntity target) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(owner, target)) {
            owner.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            owner.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && serverLevel.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                owner.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }
}
