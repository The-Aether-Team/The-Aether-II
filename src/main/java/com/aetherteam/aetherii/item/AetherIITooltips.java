package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.item.consumeeffect.ApplyStatusEffectsConsumeEffect;
import com.aetherteam.aetherii.item.consumeeffect.ConsumeEffect;
import com.aetherteam.aetherii.item.consumeeffect.ReduceStatusEffectConsumeEffect;
import com.aetherteam.aetherii.item.consumeeffect.RemoveStatusEffectsConsumeEffect;
import com.aetherteam.aetherii.item.components.Consumable;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.item.miscellaneous.TooltipTemplate;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AetherIITooltips {
    public static final TooltipTemplate CURATIVE = (stack, context, tooltipComponents, tooltipFlag) -> {
        Consumable consumable = AetherIIConsumables.get(stack);
        if (consumable != null) {
            List<MobEffect> removeEffects = new ArrayList<>();
            List<Holder<MobEffect>> reduceEffects = new ArrayList<>();
            for (ConsumeEffect consumeEffect : consumable.effects().stream().filter((consumeEffect) -> consumeEffect instanceof RemoveStatusEffectsConsumeEffect).map(ConsumeEffect.class::cast).toList()) {
                RemoveStatusEffectsConsumeEffect removeStatusEffectsConsumeEffect = (RemoveStatusEffectsConsumeEffect) consumeEffect;
                for (MobEffect effect : removeStatusEffectsConsumeEffect.effects()) {
                    removeEffects.add(effect);
                }
            }
            for (ConsumeEffect consumeEffect : consumable.effects().stream().filter((consumeEffect) -> consumeEffect instanceof ReduceStatusEffectConsumeEffect).map(ConsumeEffect.class::cast).toList()) {
                ReduceStatusEffectConsumeEffect reduceStatusEffectConsumeEffect = (ReduceStatusEffectConsumeEffect) consumeEffect;
                reduceEffects.addAll(reduceStatusEffectConsumeEffect.effects().keySet());
            }
            if (!removeEffects.isEmpty()) {
                MutableComponent removeComponents = Component.translatable(removeEffects.get(0).getDescriptionId());
                for (int i = 1; i < removeEffects.size(); i++) {
                    removeComponents = removeComponents.append(Component.literal(", ").append(Component.translatable(removeEffects.get(i).getDescriptionId())));
                }
                tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.curative.removes", removeComponents.withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GREEN));
            }
            if (!reduceEffects.isEmpty()) {
                MutableComponent reduceComponents = Component.translatable(reduceEffects.get(0).value().getDescriptionId());
                for (int i = 1; i < reduceEffects.size(); i++) {
                    reduceComponents = reduceComponents.append(Component.literal(", ").append(Component.translatable(reduceEffects.get(i).value().getDescriptionId())));
                }
                tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.curative.reduces", reduceComponents.withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_GREEN));
            }
        }
    };

    public static final TooltipTemplate TEA = (stack, context, tooltipComponents, tooltipFlag) -> {
        Consumable consumable = AetherIIConsumables.get(stack);
        if (consumable != null) {
            List<MobEffectInstance> effects = new ArrayList<>();
            for (ConsumeEffect consumeEffect : consumable.effects().stream().filter((consumeEffect) -> consumeEffect instanceof ApplyStatusEffectsConsumeEffect).map(ConsumeEffect.class::cast).toList()) {
                ApplyStatusEffectsConsumeEffect applyStatusEffectConsumeEffect = (ApplyStatusEffectsConsumeEffect) consumeEffect;
                effects.addAll(applyStatusEffectConsumeEffect.effects());
            }
            Consumer<Component> tooltipAdder = tooltipComponents::add;

            List<Pair<Holder<Attribute>, AttributeModifier>> list = Lists.newArrayList();
            boolean flag = true;

            for (MobEffectInstance effectInstance : effects) {
                flag = false;
                MutableComponent component = Component.translatable(effectInstance.getDescriptionId());
                MobEffect effect = effectInstance.getEffect();
                effect.getAttributeModifiers().forEach((attribute, attributeModifier) -> list.add(new Pair<>(Holder.direct(attribute), new AttributeModifier(attributeModifier.getId(), attributeModifier.getName(), effect.getAttributeModifierValue(effectInstance.getAmplifier(), attributeModifier), attributeModifier.getOperation()))));
                if (effectInstance.getAmplifier() > 0) {
                    component = Component.translatable("potion.withAmplifier", component, Component.translatable("potion.potency." + effectInstance.getAmplifier()));
                }

                if (!effectInstance.endsWithin(20)) {
                    component = Component.translatable("potion.withDuration", component, MobEffectUtil.formatDuration(effectInstance, 1.0F));
                }

                tooltipAdder.accept(component.withStyle(effect.getCategory().getTooltipFormatting()));
            }

            if (flag) {
                tooltipAdder.accept(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
            }

            if (!list.isEmpty()) {
                tooltipAdder.accept(CommonComponents.EMPTY);
                tooltipAdder.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
                AccessoryUtil.addPotionTooltip(list, tooltipAdder);
            }
        }
    };
}
