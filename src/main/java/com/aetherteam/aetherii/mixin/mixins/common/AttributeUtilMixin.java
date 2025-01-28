package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.AttributeUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttributeUtil.class)
public class AttributeUtilMixin {
    @WrapOperation(method = "applyModifierTooltips(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;Lnet/neoforged/neoforge/common/util/AttributeTooltipContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"), remap = false)
    private static MutableComponent translatable(String key, Operation<MutableComponent> original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof TieredShieldItem) {
            return Component.translatable("aether_ii.tooltip.item.modifiers.blocking");
        }
        return original.call(key);
    }

    @WrapOperation(method = "applyTextFor(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;Lcom/google/common/collect/Multimap;Lnet/neoforged/neoforge/common/util/AttributeTooltipContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"), remap = false)
    private static MutableComponent withStyle(MutableComponent instance, ChatFormatting format, Operation<MutableComponent> original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR) && format == ChatFormatting.DARK_GREEN) {
            return instance.withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR);
        }
        return original.call(instance, format);
    }
}
