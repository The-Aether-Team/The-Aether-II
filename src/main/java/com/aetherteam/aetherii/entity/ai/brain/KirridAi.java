package com.aetherteam.aetherii.entity.ai.brain;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.behavior.FallRandomStroll;
import com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid.KirridEatGrass;
import com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid.KirridPanic;
import com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid.KirridRamOther;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.brain.sensor.AetherIISensorTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import com.aetherteam.aetherii.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Set;

public class KirridAi {
    public static final ImmutableList<SensorType<? extends Sensor<? super Kirrid>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            AetherIISensorTypes.KIRRID_TEMPTATIONS.get()
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
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.RAM_COOLDOWN_TICKS,
            AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(),
            AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(),
            MemoryModuleType.IS_PANICKING
    );

    public static final Brain.Provider<Kirrid> BRAIN_PROVIDER = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    public static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    public static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 2400);
    public static final UniformInt TIME_BETWEEN_EAT = UniformInt.of(600, 1200);

    public static List<ActivityData<Kirrid>> getActivities(EntityType<? extends Kirrid> entityType) {
        return List.of(initCoreActivity(), initIdleActivity(entityType));
    }

    public static Brain<Kirrid> makeBrain(Kirrid owner, Dynamic<?> dynamic) {
        Brain<Kirrid> brain = BRAIN_PROVIDER.makeBrain(dynamic);
        getActivities((EntityType<? extends Kirrid>) owner.getType()).forEach(activity -> activity.addToBrain(brain));
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initMemories(Kirrid kirrid, RandomSource random) {
        kirrid.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
        kirrid.getBrain().setMemory(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(), TIME_BETWEEN_EAT.sample(random));
    }


    private static ActivityData<Kirrid> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new KirridPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS),
                new CountDownCooldownTicks(AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get())
        ));
    }

    private static ActivityData<Kirrid> initIdleActivity(EntityType<? extends Kirrid> entityType) {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(0, new AnimalMakeLove(entityType, 1.0F)),
                Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(2, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.25F)),
                Pair.of(3, new RunOne<>(ImmutableList.of(
                        Pair.of(FallRandomStroll.stroll(1.5F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                        Pair.of(new KirridRamOther(1.5F), 2),
                        Pair.of(new KirridEatGrass(), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                )))
        ));
    }

    public static void updateActivity(Kirrid owner) {
        owner.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
                Activity.RAM,
                Activity.IDLE
        ));
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(AetherIITags.Items.KIRRID_FOOD);
    }
}
