package com.aetherteam.aetherii.entity.variant;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Comparator;
import java.util.Optional;

public final class VariantUtils {
    private VariantUtils() {
    }

    public static <T> Optional<? extends Holder<T>> selectVariantToSpawn(SpawnContext context, ResourceKey<? extends Registry<T>> registryKey) {
        Registry<T> registry = context.level().registryAccess().registryOrThrow(registryKey);
        return registry.holders()
                .filter(holder -> holder.value() instanceof PriorityProvider<?, ?>)
                .map(holder -> new Candidate<>(holder, priority(context, holder.value())))
                .filter(candidate -> candidate.priority() >= 0)
                .max(Comparator.comparingInt(Candidate::priority))
                .map(Candidate::holder);
    }

    @SuppressWarnings("unchecked")
    private static int priority(SpawnContext context, Object value) {
        int priority = -1;
        for (PriorityProvider.Selector<SpawnContext, SpawnCondition> selector : ((PriorityProvider<SpawnContext, SpawnCondition>) value).selectors()) {
            if (selector.condition().test(context)) {
                priority = Math.max(priority, selector.priority());
            }
        }
        return priority;
    }

    private record Candidate<T>(Holder<T> holder, int priority) {
    }
}
