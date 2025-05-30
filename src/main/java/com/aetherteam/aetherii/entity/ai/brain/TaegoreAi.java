package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.behavior.taegore.*;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class TaegoreAi {
    public static final ImmutableList<SensorType<? extends Sensor<? super Taegore>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            AetherIISensorTypes.TAEGORE_TEMPTATIONS.get()
    );
    public static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.IS_PANICKING,
            AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(),
            AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(),
            AetherIIMemoryModuleTypes.TAEGORE_SEARCH_COOLDOWN.get(),
            AetherIIMemoryModuleTypes.TAEGORE_EXPLORED_POSITIONS.get()
    );
    public static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static Brain<?> makeBrain(EntityType<? extends Taegore> entityType, Brain<Taegore> brain) {
        initCoreActivity(brain);
        initDigActivity(brain);
        initSniffingActivity(brain);
        initIdleActivity(entityType, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    private static void initCoreActivity(Brain<Taegore> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim<>(0.8F),
                new TaegorePanic(1.25F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static void initDigActivity(Brain<Taegore> brain) {
        brain.addActivityWithConditions(
                Activity.DIG,
                ImmutableList.of(Pair.of(0, new TaegoreDigging(160, 180)), Pair.of(0, new TaegoreFinishedDigging(40))),
                Set.of(
                        Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                        Pair.of(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static void initSniffingActivity(Brain<Taegore> brain) {
        brain.addActivityWithConditions(
                Activity.SNIFF,
                ImmutableList.of(Pair.of(0, new TaegoreSearching())),
                Set.of(
                        Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
                        Pair.of(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(), MemoryStatus.VALUE_PRESENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static void initIdleActivity(EntityType<? extends Taegore> entityType, Brain<Taegore> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 2.0F, 24, true)),
                Pair.of(1, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(2, new AnimalMakeLove(entityType)),
                Pair.of(3, new FollowTemptation(livingEntity -> 1.2F)),
                Pair.of(4, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.1F)),
                Pair.of(5, new RunOne<>(ImmutableList.of(
                        Pair.of(new TaegoreBeginSearch(40, 80), 1),
                        Pair.of(RandomStroll.stroll(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(0.8F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )))
        ));
    }

    public static void updateActivity(Taegore owner) {
        owner.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.DIG, Activity.SNIFF, Activity.IDLE));

        Optional<Player> optionalTargetFromMemory = owner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optionalTargetFromMemory.isPresent()) {
            if (optionalTargetFromMemory.get().isSprinting()) {
                owner.getBrain().setMemory(MemoryModuleType.AVOID_TARGET, optionalTargetFromMemory);
            } else if (owner.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
                owner.getBrain().setMemory(MemoryModuleType.AVOID_TARGET, Optional.empty());
            }
        }
    }

    public static void resetSearch(ServerLevel serverLevel, Taegore owner) {
        owner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get());
        owner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get());
        serverLevel.broadcastEntityEvent(owner, (byte) Taegore.DIGGING_STOP_EVENT);
    }

    public static Predicate<ItemStack> getTemptations() {
        return (stack) -> stack.is(AetherIITags.Items.TAEGORE_FOOD);
    }
}
