package com.aetherteam.aetherii.entity.attributes;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.common.extensions.IAttributeExtension;

public class EffectResistanceAttribute extends PercentageAttribute {
    private final Holder<MobEffect> effect;

    public EffectResistanceAttribute(Holder<MobEffect> effect, String pDescriptionId, double pDefaultValue, double pMin, double pMax, double scaleFactor) {
        super(pDescriptionId, pDefaultValue, pMin, pMax, scaleFactor);
        this.effect = effect;
    }

    public EffectResistanceAttribute(Holder<MobEffect> effect, String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
        this.effect = effect;
    }

    @Override
    public MutableComponent toComponent(AttributeModifier modif, TooltipFlag flag) {
        Attribute attr = this;
        double value = modif.amount();
        String key = value > 0.0 ? "neoforge.modifier.plus" : "neoforge.modifier.take";
        ChatFormatting color = attr.getStyle(value > 0.0);
        Component attrDesc = Component.translatable(attr.getDescriptionId(), Component.translatable(this.effect.value().getDescriptionId()));
        Component valueComp = this.toValueComponent(modif.operation(), value, flag);
        MutableComponent comp = Component.translatable(key, valueComp, attrDesc).withStyle(color);
        return comp.append(this.getDebugInfo(modif, flag));
    }

    @Override
    public MutableComponent toValueComponent(AttributeModifier.Operation op, double value, TooltipFlag flag) {
        return IAttributeExtension.isNullOrAddition(op)
                ? Component.translatable("neoforge.value.percent", FORMAT.format(value * this.scaleFactor))
                : Component.translatable("neoforge.value.percent", FORMAT.format(value * 100.0));
    }

    public Holder<MobEffect> getEffect() {
        return this.effect;
    }
}
