package com.aetherteam.aetherii.effect;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.effect.beneficial.SaturationBoostEffect;
import com.aetherteam.aetherii.effect.harmful.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AetherII.MODID);

    // Beneficial
    public static final DeferredHolder<MobEffect, MobEffect> SATURATION_BOOST = EFFECTS.register("saturation_boost", SaturationBoostEffect::new);

    // Harmful
    public static final DeferredHolder<MobEffect, MobEffect> WOUND = EFFECTS.register("wound", WoundEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> STUN = EFFECTS.register("stun", () -> new StunEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.stun.slowness"), -0.75F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> FRACTURE = EFFECTS.register("fracture", () -> new FractureEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.slowness"), -0.3F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.jump_hinder"), -0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.fall_increase"), -2.0F, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> AMBROSIUM_POISONING = EFFECTS.register("ambrosium_poisoning", AmbrosiumPoisoningEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TOXIN = EFFECTS.register("toxin", ToxinEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> VENOM = EFFECTS.register("venom", VenomEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CHARGED = EFFECTS.register("charged", ChargedEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> WEBBED = EFFECTS.register("webbed", () -> new WebbedEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.webbed.slowness"), -0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.webbed.jump_hinder"), -0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> IMMOLATION = EFFECTS.register("immolation", ImmolationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FROSTBITE = EFFECTS.register("frostbite", () -> new FrostbiteEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.frostbite.slowness"), -0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.frostbite.jump_hinder"), -0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.frostbite.mining_fatigue"), -0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> FUNGAL_ROT = EFFECTS.register("fungal_rot", FungalRotEffect::new); //todo
    public static final DeferredHolder<MobEffect, MobEffect> CRYSTALLIZED = EFFECTS.register("crystallized", CrystallizedEffect::new); //todo

    public static void registerUniqueBehaviors(IEventBus bus) {
        bus.addListener(StunEffect::disableAttacks);
        bus.addListener(StunEffect::disableDamage);
        bus.addListener(StunEffect::disableEntityInteractSpecific);
        bus.addListener(StunEffect::disableEntityInteract);
        bus.addListener(StunEffect::disableRightClickBlock);
        bus.addListener(StunEffect::disableRightClickItem);
        bus.addListener(StunEffect::disableLeftClickBlock);

        bus.addListener(FractureEffect::onEffectRemoval);

        bus.addListener(AmbrosiumPoisoningEffect::preventHealing);

        bus.addListener(WebbedEffect::reduceByJumping);
    }
}
