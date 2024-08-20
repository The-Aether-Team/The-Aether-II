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

    public static final DeferredHolder<Attribute, Attribute> SLASH_DAMAGE = ATTRIBUTES.register("slash_damage", () -> new RangedAttribute("attributes.aether_ii.slash_damage", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> IMPACT_DAMAGE = ATTRIBUTES.register("impact_damage", () -> new RangedAttribute("attributes.aether_ii.impact_damage", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> PIERCE_DAMAGE = ATTRIBUTES.register("pierce_damage", () -> new RangedAttribute("attributes.aether_ii.pierce_damage", 0.0, 0.0, 1024.0));

    public static final DeferredHolder<Attribute, Attribute> SLASH_RESISTANCE = ATTRIBUTES.register("slash_resistance", () -> new RangedAttribute("attributes.aether_ii.slash_resistance", 0.0, -1024.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> IMPACT_RESISTANCE = ATTRIBUTES.register("impact_resistance", () -> new RangedAttribute("attributes.aether_ii.impact_resistance", 0.0, -1024.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> PIERCE_RESISTANCE = ATTRIBUTES.register("pierce_resistance", () -> new RangedAttribute("attributes.aether_ii.pierce_resistance", 0.0, -1024.0, 1024.0));

    public static final DeferredHolder<Attribute, Attribute> SWEEP_RANGE = ATTRIBUTES.register("sweep_range", () -> new RangedAttribute("attributes.aether_ii.sweep_range", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> SWEEP_KNOCKBACK = ATTRIBUTES.register("sweep_knockback", () -> new RangedAttribute("attributes.aether_ii.sweep_knockback", 0.4, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> SWEEP_DAMAGE = ATTRIBUTES.register("sweep_damage", () -> new RangedAttribute("attributes.aether_ii.sweep_damage", 1.0, 0.0, 1024.0));

    public static final DeferredHolder<Attribute, Attribute> SHOCK_RANGE = ATTRIBUTES.register("shock_range", () -> new RangedAttribute("attributes.aether_ii.shock_range", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> SHOCK_KNOCKBACK = ATTRIBUTES.register("shock_knockback", () -> new RangedAttribute("attributes.aether_ii.shock_knockback", 1.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> SHOCK_DAMAGE = ATTRIBUTES.register("shock_damage", () -> new RangedAttribute("attributes.aether_ii.shock_damage", 0.1, 0.0, 1024.0));

    public static final DeferredHolder<Attribute, Attribute> STAB_RADIUS = ATTRIBUTES.register("stab_radius", () -> new RangedAttribute("attributes.aether_ii.stab_radius", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> STAB_DISTANCE = ATTRIBUTES.register("stab_distance", () -> new RangedAttribute("attributes.aether_ii.stab_distance", 0.0, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> STAB_KNOCKBACK = ATTRIBUTES.register("stab_knockback", () -> new RangedAttribute("attributes.aether_ii.stab_knockback", 0.2, 0.0, 1024.0));
    public static final DeferredHolder<Attribute, Attribute> STAB_DAMAGE = ATTRIBUTES.register("stab_damage", () -> new RangedAttribute("attributes.aether_ii.stab_damage", 2.0, 0.0, 1024.0));

    public static final DeferredHolder<Attribute, Attribute> SHIELD_STAMINA_REDUCTION = ATTRIBUTES.register("shield_stamina_reduction", () -> new RangedAttribute("attributes.aether_ii.shield_stamina_reduction", 0.0, 0.0, 500.0));
    public static final DeferredHolder<Attribute, Attribute> SHIELD_STAMINA_RESTORATION = ATTRIBUTES.register("shield_stamina_restoration", () -> new RangedAttribute("attributes.aether_ii.shield_stamina_restoration", 2.0, 0.0, 500.0));

    public static void registerEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SLASH_DAMAGE);
        event.add(EntityType.PLAYER, IMPACT_DAMAGE);
        event.add(EntityType.PLAYER, PIERCE_DAMAGE);

        event.add(EntityType.PLAYER, SWEEP_RANGE);
        event.add(EntityType.PLAYER, SWEEP_KNOCKBACK);
        event.add(EntityType.PLAYER, SWEEP_DAMAGE);

        event.add(EntityType.PLAYER, SHOCK_RANGE);
        event.add(EntityType.PLAYER, SHOCK_KNOCKBACK);
        event.add(EntityType.PLAYER, SHOCK_DAMAGE);

        event.add(EntityType.PLAYER, STAB_RADIUS);
        event.add(EntityType.PLAYER, STAB_DISTANCE);
        event.add(EntityType.PLAYER, STAB_KNOCKBACK);
        event.add(EntityType.PLAYER, STAB_DAMAGE);

        event.add(EntityType.PLAYER, SHIELD_STAMINA_REDUCTION);
        event.add(EntityType.PLAYER, SHIELD_STAMINA_RESTORATION);
    }
}
