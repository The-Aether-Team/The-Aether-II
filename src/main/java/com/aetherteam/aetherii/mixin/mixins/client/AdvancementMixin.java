package com.aetherteam.aetherii.mixin.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Advancement.class)
public class AdvancementMixin {
    @WrapOperation(method = "decorateName(Lnet/minecraft/advancements/DisplayInfo;)Lnet/minecraft/network/chat/Component;", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementType;getChatColor()Lnet/minecraft/ChatFormatting;"))
    private static ChatFormatting getChatColor(AdvancementType instance, Operation<ChatFormatting> original, @Local(argsOnly = true) DisplayInfo display) {
        Component name = display.getTitle();
        if (name.getContents() instanceof TranslatableContents translatableContents) {
            if (translatableContents.getKey().contains("advancement.aether_ii")) {
                if (instance != AdvancementType.CHALLENGE) {
                    return ChatFormatting.AQUA;
                } else {
                    return ChatFormatting.GOLD;
                }
            }
        }
        return original.call(instance);
    }
}
