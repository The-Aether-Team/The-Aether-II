package com.aetherteam.aetherii.entity.ai;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class ActivityData<E extends LivingEntity> {
    private final Activity activity;
    private final int priority;
    private final ImmutableList<? extends BehaviorControl<? super E>> behaviors;
    private final ImmutableList<Pair<Integer, ? extends BehaviorControl<? super E>>> prioritizedBehaviors;
    private final Set<Pair<MemoryModuleType<?>, MemoryStatus>> requiredMemoryStates;
    private final MemoryModuleType<?> memoryToEraseWhenStopped;

    private ActivityData(Activity activity, int priority, List<? extends BehaviorControl<? super E>> behaviors, List<Pair<Integer, ? extends BehaviorControl<? super E>>> prioritizedBehaviors, Set<Pair<MemoryModuleType<?>, MemoryStatus>> requiredMemoryStates, MemoryModuleType<?> memoryToEraseWhenStopped) {
        this.activity = activity;
        this.priority = priority;
        this.behaviors = ImmutableList.copyOf(behaviors);
        this.prioritizedBehaviors = ImmutableList.copyOf(prioritizedBehaviors);
        this.requiredMemoryStates = requiredMemoryStates;
        this.memoryToEraseWhenStopped = memoryToEraseWhenStopped;
    }

    public static <E extends LivingEntity> ActivityData<E> create(Activity activity, int priority, List<? extends BehaviorControl<? super E>> behaviors) {
        return new ActivityData<>(activity, priority, behaviors, List.of(), Set.of(), null);
    }

    public static <E extends LivingEntity> ActivityData<E> create(Activity activity, List<Pair<Integer, ? extends BehaviorControl<? super E>>> prioritizedBehaviors) {
        return new ActivityData<>(activity, 0, List.of(), prioritizedBehaviors, Set.of(), null);
    }

    public static <E extends LivingEntity> ActivityData<E> create(Activity activity, List<Pair<Integer, ? extends BehaviorControl<? super E>>> prioritizedBehaviors, Set<Pair<MemoryModuleType<?>, MemoryStatus>> requiredMemoryStates) {
        return new ActivityData<>(activity, 0, List.of(), prioritizedBehaviors, requiredMemoryStates, null);
    }

    public static <E extends LivingEntity> ActivityData<E> create(Activity activity, int priority, List<? extends BehaviorControl<? super E>> behaviors, MemoryModuleType<?> memoryToEraseWhenStopped) {
        return new ActivityData<>(activity, priority, behaviors, List.of(), Set.of(), memoryToEraseWhenStopped);
    }

    public void addToBrain(Brain<E> brain) {
        if (!this.prioritizedBehaviors.isEmpty()) {
            if (this.requiredMemoryStates.isEmpty()) {
                brain.addActivity(this.activity, this.prioritizedBehaviors);
            } else {
                brain.addActivityWithConditions(this.activity, this.prioritizedBehaviors, this.requiredMemoryStates);
            }
        } else if (this.memoryToEraseWhenStopped != null) {
            brain.addActivityAndRemoveMemoryWhenStopped(this.activity, this.priority, this.behaviors, this.memoryToEraseWhenStopped);
        } else {
            brain.addActivity(this.activity, this.priority, this.behaviors);
        }
    }
}
