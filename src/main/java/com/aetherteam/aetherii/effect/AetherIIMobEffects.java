package com.aetherteam.aetherii.effect;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.effect.beneficial.*;
import com.aetherteam.aetherii.effect.harmful.*;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AetherIIMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AetherII.MODID);

    // Beneficial
    public static final RegistryObject<MobEffect> SATURATION_BOOST = EFFECTS.register("saturation_boost", () -> new SaturationBoostEffect()
            .addAttributeModifier(attribute(AetherIIAttributes.SATURATION_BOOST), modifierId("effect.saturation_boost.double_saturation"), 1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> NATURAL_CAMOUFLAGE = EFFECTS.register("natural_camouflage", NaturalCamouflageEffect::new);
    public static final RegistryObject<MobEffect> HEALING_OVERFLOW = EFFECTS.register("healing_overflow", HealingOverflowEffect::new);

    // Harmful
    public static final RegistryObject<MobEffect> VULNERABILITY = EFFECTS.register("vulnerability", () -> new VulnerabilityEffect()
            .addAttributeModifier(attribute(AetherIIAttributes.SLASH_RESISTANCE), modifierId("effect.vulnerability.slash_weakness"), -1.0F, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(attribute(AetherIIAttributes.IMPACT_RESISTANCE), modifierId("effect.vulnerability.impact_weakness"), -1.0F, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(attribute(AetherIIAttributes.PIERCE_RESISTANCE), modifierId("effect.vulnerability.pierce_weakness"), -1.0F, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> WOUND = EFFECTS.register("wound", WoundEffect::new);
    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
    public static final RegistryObject<MobEffect> FRACTURE = EFFECTS.register("fracture", () -> new FractureEffect()
            .addAttributeModifier(Attributes.JUMP_STRENGTH, modifierId("effect.fracture.jump_hinder"), -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(attribute(AetherIIAttributes.SLASH_RESISTANCE), modifierId("effect.fracture.slash_weakness"), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE)
            .addAttributeModifier(attribute(AetherIIAttributes.IMPACT_RESISTANCE), modifierId("effect.fracture.impact_weakness"), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE)
            .addAttributeModifier(attribute(AetherIIAttributes.PIERCE_RESISTANCE), modifierId("effect.fracture.pierce_weakness"), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
    public static final RegistryObject<MobEffect> AMBROSIUM_POISONING = EFFECTS.register("ambrosium_poisoning", AmbrosiumPoisoningEffect::new);
    public static final RegistryObject<MobEffect> TOXIN = EFFECTS.register("toxin", ToxinEffect::new);
    public static final RegistryObject<MobEffect> VENOM = EFFECTS.register("venom", VenomEffect::new);
    public static final RegistryObject<MobEffect> CHARGED = EFFECTS.register("charged", ChargedEffect::new);
    public static final RegistryObject<MobEffect> WEBBED = EFFECTS.register("webbed", () -> new WebbedEffect()
            .addAttributeModifier(Attributes.JUMP_STRENGTH, modifierId("effect.webbed.jump_hinder"), -0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> IMMOLATION = EFFECTS.register("immolation", ImmolationEffect::new);
    public static final RegistryObject<MobEffect> FROSTBITE = EFFECTS.register("frostbite", () -> new FrostbiteEffect()
            .addAttributeModifier(Attributes.JUMP_STRENGTH, modifierId("effect.frostbite.jump_hinder"), -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, modifierId("effect.frostbite.mining_fatigue"), -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> FUNGAL_ROT = EFFECTS.register("fungal_rot", FungalRotEffect::new); //todo
    public static final RegistryObject<MobEffect> CRYSTALLIZED = EFFECTS.register("crystallized", CrystallizedEffect::new); //todo

    public static final RegistryObject<MobEffect> ELECTRIC_SHOCK = EFFECTS.register("electric_shock", ElectricShockEffect::new);
    public static final RegistryObject<MobEffect> CARRION_TRAP = EFFECTS.register("carrion_trap", () -> new CarrionPullEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, modifierId("effect.carrion_pull.slowness"), -0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.JUMP_STRENGTH, modifierId("effect.carrion_pull.jump_hinder"), -0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, modifierId("effect.carrion_pull.knockback_resistance"), 1.0F, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> GRAVITATIONAL_PULL = EFFECTS.register("gravitational_pull", () -> new GravitationalPullEffect()
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, modifierId("effect.gravitational_pull.slowness"), -0.375F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void registerUniqueBehaviors(IEventBus bus) {
        bus.addListener(AmbrosiumPoisoningEffect::preventHealing);

        bus.addListener(FractureEffect::onEntityPostTick);

        bus.addListener(FrostbiteEffect::onEntityPostTick);

        bus.addListener(ImmolationEffect::onEntityPostTick);

        bus.addListener(StunEffect::onEntityPostTick);
        bus.addListener(StunEffect::disableAttacks);
        bus.addListener(StunEffect::disableDamage);
        bus.addListener(StunEffect::disableEntityInteractSpecific);
        bus.addListener(StunEffect::disableEntityInteract);
        bus.addListener(StunEffect::disableRightClickBlock);
        bus.addListener(StunEffect::disableRightClickItem);
        bus.addListener(StunEffect::disableLeftClickBlock);

        bus.addListener(WebbedEffect::onEntityPostTick);
        bus.addListener(WebbedEffect::reduceByJumping);

        bus.addListener(NaturalCamouflageEffect::onEntityPostTick);
        bus.addListener(NaturalCamouflageEffect::adjustVisibilityModifier);
    }

    private static Attribute attribute(RegistryObject<Attribute> attribute) {
        return attribute.get();
    }

    private static String modifierId(String path) {
        return UUID.nameUUIDFromBytes((AetherII.MODID + ":" + path).getBytes(StandardCharsets.UTF_8)).toString();
    }
}
