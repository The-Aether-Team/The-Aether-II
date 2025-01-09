package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AttributeMap.class)
public interface AttributeMapAccessor {
    @Accessor("attributes")
    Map<Holder<Attribute>, AttributeInstance> aether_ii$getAttributes();
}
