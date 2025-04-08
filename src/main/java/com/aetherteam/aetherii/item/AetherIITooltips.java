package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.item.consumeeffect.ReduceStatusEffectConsumeEffect;
import com.aetherteam.aetherii.item.miscellaneous.TooltipTemplate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;

import java.util.ArrayList;
import java.util.List;

public class AetherIITooltips {
    public static final TooltipTemplate CURATIVE = (stack, context, tooltipComponents, tooltipFlag) -> { //todo translatable
        Consumable consumable = stack.get(DataComponents.CONSUMABLE);
        if (consumable != null) {
            List<Holder<MobEffect>> removeEffects = new ArrayList<>();
            List<Holder<MobEffect>> reduceEffects = new ArrayList<>();
            for (ConsumeEffect consumeEffect : consumable.onConsumeEffects().stream().filter((consumeEffect) -> consumeEffect instanceof RemoveStatusEffectsConsumeEffect).toList()) {
                RemoveStatusEffectsConsumeEffect removeStatusEffectsConsumeEffect = (RemoveStatusEffectsConsumeEffect) consumeEffect;
                for (Holder<MobEffect> effect : removeStatusEffectsConsumeEffect.effects()) {
                    removeEffects.add(effect);
                }
            }
            for (ConsumeEffect consumeEffect : consumable.onConsumeEffects().stream().filter((consumeEffect) -> consumeEffect instanceof ReduceStatusEffectConsumeEffect).toList()) {
                ReduceStatusEffectConsumeEffect reduceStatusEffectConsumeEffect = (ReduceStatusEffectConsumeEffect) consumeEffect;
                reduceEffects.addAll(reduceStatusEffectConsumeEffect.effects().keySet());
            }
            if (!removeEffects.isEmpty()) {
                MutableComponent removeComponents = Component.translatable(removeEffects.getFirst().value().getDescriptionId());
                for (int i = 1; i < removeEffects.size(); i++) {
                    removeComponents = removeComponents.append(Component.literal(", ").append(Component.translatable(removeEffects.get(i).value().getDescriptionId())));
                }
                tooltipComponents.add(Component.literal("Removes: ").withStyle(ChatFormatting.GREEN).append(removeComponents.withStyle(ChatFormatting.GRAY)));
            }
            if (!reduceEffects.isEmpty()) {
                MutableComponent reduceComponents = Component.translatable(reduceEffects.getFirst().value().getDescriptionId());
                for (int i = 1; i < reduceEffects.size(); i++) {
                    reduceComponents = reduceComponents.append(Component.literal(", ").append(Component.translatable(reduceEffects.get(i).value().getDescriptionId())));
                }
                tooltipComponents.add(Component.literal("Reduces: ").withStyle(ChatFormatting.DARK_GREEN).append(reduceComponents.withStyle(ChatFormatting.GRAY)));
            }
        }
    };
}