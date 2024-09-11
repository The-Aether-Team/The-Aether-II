package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIEffectCures;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.MobEffectInstanceAccessor;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.Set;

public class FractureEffect extends MobEffect {
    private static final ResourceLocation SLASH_WEAKNESS = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.slash_weakness");
    private static final ResourceLocation IMPACT_WEAKNESS = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.impact_weakness");
    private static final ResourceLocation PIERCE_WEAKNESS = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect.fracture.pierce_weakness");

    public FractureEffect() {
        super(MobEffectCategory.HARMFUL, 14078644);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        AttributeInstance slashResistance = livingEntity.getAttribute(AetherIIAttributes.SLASH_RESISTANCE);
        AttributeInstance impactResistance = livingEntity.getAttribute(AetherIIAttributes.IMPACT_RESISTANCE);
        AttributeInstance pierceResistance = livingEntity.getAttribute(AetherIIAttributes.PIERCE_RESISTANCE);

        if (slashResistance != null && slashResistance.getValue() < 0 && !slashResistance.hasModifier(SLASH_WEAKNESS)) {
            slashResistance.addTransientModifier(new AttributeModifier(SLASH_WEAKNESS, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        if (impactResistance != null && impactResistance.getValue() < 0 && !impactResistance.hasModifier(IMPACT_WEAKNESS)) {
            impactResistance.addTransientModifier(new AttributeModifier(IMPACT_WEAKNESS, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        if (pierceResistance != null && pierceResistance.getValue() < 0 && !pierceResistance.hasModifier(PIERCE_WEAKNESS)) {
            pierceResistance.addTransientModifier(new AttributeModifier(PIERCE_WEAKNESS, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.isSprinting()) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.FRACTURE), 1.0F);
            livingEntity.setSprinting(false);
        }
        if (livingEntity.isCrouching() && livingEntity.getDeltaMovement().x() == 0 && livingEntity.getDeltaMovement().z() == 0) {
            MobEffectInstance instance = livingEntity.getEffect(AetherIIEffects.FRACTURE);
            if (instance != null) {
                ((MobEffectInstanceAccessor) instance).aether_ii$setDuration(Math.max(0, instance.mapDuration(mapper -> mapper - 4)));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
        cures.add(AetherIIEffectCures.SPLINT);
    }

    public static void onEffectRemoval(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = event.getEffect();
        if (effect.is(AetherIIEffects.FRACTURE)) {
            AttributeInstance slashResistance = entity.getAttribute(AetherIIAttributes.SLASH_RESISTANCE);
            AttributeInstance impactResistance = entity.getAttribute(AetherIIAttributes.IMPACT_RESISTANCE);
            AttributeInstance pierceResistance = entity.getAttribute(AetherIIAttributes.PIERCE_RESISTANCE);

            if (slashResistance != null && slashResistance.hasModifier(SLASH_WEAKNESS)) {
                slashResistance.removeModifier(SLASH_WEAKNESS);
            }
            if (impactResistance != null && impactResistance.hasModifier(IMPACT_WEAKNESS)) {
                impactResistance.removeModifier(IMPACT_WEAKNESS);
            }
            if (pierceResistance != null && pierceResistance.hasModifier(PIERCE_WEAKNESS)) {
                pierceResistance.removeModifier(PIERCE_WEAKNESS);
            }
        }
    }
}
