package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, AetherII.MODID);

    public static final DeferredHolder<Attribute, Attribute> SLASH_RANGE = ATTRIBUTES.register("slash_range", () -> new RangedAttribute("attributes.aether_ii.slash_range", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> SHOCK_RANGE = ATTRIBUTES.register("shock_range", () -> new RangedAttribute("attributes.aether_ii.shock_range", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> STAB_RADIUS = ATTRIBUTES.register("stab_radius", () -> new RangedAttribute("attributes.aether_ii.stab_radius", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> STAB_DISTANCE = ATTRIBUTES.register("stab_distance", () -> new RangedAttribute("attributes.aether_ii.stab_distance", 0.0, 0.0, 1024.0));

    public static void registerEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SLASH_RANGE.get());
        event.add(EntityType.PLAYER, SHOCK_RANGE.get());
        event.add(EntityType.PLAYER, STAB_RADIUS.get());
        event.add(EntityType.PLAYER, STAB_DISTANCE.get());
    }
}
