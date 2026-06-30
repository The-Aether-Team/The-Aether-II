package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import com.google.common.collect.BiMap;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntitySubPredicate.Types.class)
public interface EntitySubPredicateTypesAccessor {
    @Mutable
    @Accessor("TYPES")
    static void aether_ii$setTypes(BiMap<String, EntitySubPredicate.Type> types) {
        throw new AssertionError();
    }
}
