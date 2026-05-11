package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.AttributeUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttributeUtil.class)
public class AttributeUtilMixin {
    @WrapOperation(method = "applyTextFor(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;Lcom/google/common/collect/Multimap;Lnet/neoforged/neoforge/common/util/AttributeTooltipContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"), remap = false)
    private static MutableComponent withStyle(MutableComponent instance, ChatFormatting format, Operation<MutableComponent> original, @Local(argsOnly = true) ItemStack stack) {
        if (Minecraft.getInstance().level != null && stack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR) && format == ChatFormatting.DARK_GREEN) {
            if (ReinforcementTier.isItemAtMaxTier(Minecraft.getInstance().level.registryAccess(), stack)) {
                return instance.withStyle(AetherIIItems.UPGRADED_WEAPON_COLOR);
            } else {
                return instance.withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR);
            }
        }
        return original.call(instance, format);
    }
}
