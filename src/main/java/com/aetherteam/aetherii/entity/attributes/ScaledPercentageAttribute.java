package com.aetherteam.aetherii.entity.attributes;

import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.common.extensions.IAttributeExtension;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ScaledPercentageAttribute extends PercentageAttribute {
    DecimalFormat SHORTENED_FORMAT = Util.make(new DecimalFormat("#"), (fmt) -> fmt.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));

    public ScaledPercentageAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax, double scaleFactor) {
        super(pDescriptionId, pDefaultValue, pMin, pMax, scaleFactor);
    }

    public ScaledPercentageAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
    }

    @Override
    public MutableComponent toValueComponent(AttributeModifier.Operation op, double value, TooltipFlag flag) {
        return IAttributeExtension.isNullOrAddition(op) ?
                Component.translatable("neoforge.value.percent", SHORTENED_FORMAT.format((value / this.getMaxValue()) * this.scaleFactor))
                : Component.translatable("neoforge.value.percent", SHORTENED_FORMAT.format((value / this.getMaxValue()) * 100.0));
    }

    @Override
    public MutableComponent toBaseComponent(double value, double entityBase, boolean merged, TooltipFlag flag) {
        Attribute attr = this;
        MutableComponent comp = Component.translatable("attribute.modifier.equals.0", Component.translatable("neoforge.value.percent", SHORTENED_FORMAT.format((value / this.getMaxValue()) * this.scaleFactor)), Component.translatable(attr.getDescriptionId()));
        if (flag.isAdvanced() && !merged) {
            Component debugInfo = Component.literal(" ").append(Component.translatable("neoforge.attribute.debug.base", FORMAT.format(entityBase), SHORTENED_FORMAT.format(((value / this.getMaxValue()) * this.scaleFactor) - entityBase)).withStyle(ChatFormatting.GRAY));
            comp.append(debugInfo);
        }
        return comp;
    }

    @Override
    public @Nullable ResourceLocation getBaseId() {
        if (this == AetherIIAttributes.SHIELD_STAMINA_REDUCTION.get()) {
            return TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID;
        }
        return super.getBaseId();
    }
}
