package com.aetherteam.aetherii.item.miscellaneous;

import java.util.function.Consumer;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaVariant;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class MoaSpawnEggItem extends SpawnEggItem {
    public MoaSpawnEggItem(EntityType<? extends Mob> defaultType, Properties properties) {
        super(defaultType, properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        MoaVariant moaVariant = stack.get(AetherIIDataComponents.MOA_VARIANT.get());
        if (moaVariant != null) {
            var keratinColor = moaVariant.keratinColor();
            var eyeColor = moaVariant.eyeColor();
            var featherColor = moaVariant.featherColor();
            var featherShape = moaVariant.featherShape();
            var specialVariant = moaVariant.specialVariant().orElse(null);
            if (specialVariant != null) {
                if (specialVariant.keratinColorOverride != null) {
                    keratinColor = specialVariant.keratinColorOverride;
                }
                if (specialVariant.eyeColorOverride != null) {
                    eyeColor = specialVariant.eyeColorOverride;
                }
                if (specialVariant.featherColorOverride != null) {
                    featherColor = specialVariant.featherColorOverride;
                }
                if (specialVariant.featherShapeOverride != null) {
                    featherShape = specialVariant.featherShapeOverride;
                }
            }
            var style = Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true);
            var keratinText = Component.translatable("aether_ii.tooltip.item.moa_egg.keratin", Component.translatable("aether_ii.tooltip.item.moa_egg.keratin_color." + keratinColor.getSerializedName())).withStyle(style);
            var eyeColorText = Component.translatable("aether_ii.tooltip.item.moa_egg.eyes", Component.translatable("aether_ii.tooltip.item.moa_egg.eye_color." + eyeColor.getSerializedName())).withStyle(style);
            var featherText = Component.translatable("aether_ii.tooltip.item.moa_egg.feathers", Component.translatable("aether_ii.tooltip.item.moa_egg.feather_shape." + featherShape.getSerializedName()), Component.translatable("aether_ii.tooltip.item.moa_egg.feather_color." + featherColor.getSerializedName())).withStyle(style);
            if (specialVariant != null) {
                keratinText.append(Component.literal(keratinColor == moaVariant.keratinColor() ? "*" : "**"));
                eyeColorText.append(Component.literal(eyeColor == moaVariant.eyeColor() ? "*" : "**"));
                featherText.append(Component.literal("*".repeat(1 + (featherColor == moaVariant.featherColor() ? 0 : 1) + (featherShape == moaVariant.featherShape() ? 0 : 1))));
            }
            tooltipAdder.accept(keratinText);
            tooltipAdder.accept(eyeColorText);
            tooltipAdder.accept(featherText);
        }
    }
}
