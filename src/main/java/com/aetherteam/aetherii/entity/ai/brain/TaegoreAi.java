package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.behavior.taegore.*;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.passive.Taegore;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import com.aetherteam.aetherii.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public static final Brain.Provider<Taegore> BRAIN_PROVIDER = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);

    public static List<ActivityData<Taegore>> getActivities(EntityType<? extends Taegore> entityType) {
        return List.of(initCoreActivity(), initIdleActivity(entityType), initSniffingActivity(), initDigActivity());
    }

    public static Brain<Taegore> makeBrain(Taegore owner, Dynamic<?> dynamic) {
        Brain<Taegore> brain = BRAIN_PROVIDER.makeBrain(dynamic);
        getActivities((EntityType<? extends Taegore>) owner.getType()).forEach(activity -> activity.addToBrain(brain));
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);


    private static ActivityData<Taegore> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new TaegorePanic(1.25F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    private static ActivityData<Taegore> initDigActivity() {
        return ActivityData.create(
                Activity.DIG,
                ImmutableList.of(Pair.of(0, new TaegoreDigging(240)), Pair.of(0, new TaegoreFinishedDigging(50))),
                Set.of(
                        Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                        Pair.of(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get(), MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static ActivityData<Taegore> initSniffingActivity() {
        return ActivityData.create(
                Activity.SNIFF,
                ImmutableList.of(Pair.of(0, new TaegoreSearching())),
                Set.of(
                        Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
                        Pair.of(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get(), MemoryStatus.VALUE_PRESENT),
                        Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static ActivityData<Taegore> initIdleActivity(EntityType<? extends Taegore> entityType) {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.7F, 24, true)),
                Pair.of(1, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(2, new AnimalMakeLove(entityType, 1.0F)),
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
            if (optionalTargetFromMemory.get().isSprinting() && !EquipmentUtil.hasArmorAbility(optionalTargetFromMemory.get(), AetherIITags.Items.BEAST_PELT_ARMOR)) {
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

    public static Ingredient getTemptations() {
        return Ingredient.of(AetherIITags.Items.TAEGORE_FOOD);
    }
}
