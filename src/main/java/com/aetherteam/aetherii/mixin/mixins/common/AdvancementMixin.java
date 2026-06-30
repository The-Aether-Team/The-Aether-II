package com.aetherteam.aetherii.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Advancement.class)
public class AdvancementMixin {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/FrameType;getChatColor()Lnet/minecraft/ChatFormatting;"))
    private ChatFormatting getChatColor(FrameType instance, Operation<ChatFormatting> original, @Local(argsOnly = true) DisplayInfo display) {
        Component name = display.getTitle();
        if (name.getContents() instanceof TranslatableContents translatableContents && translatableContents.getKey().contains("advancement.aether_ii")) {
            return instance != FrameType.CHALLENGE ? ChatFormatting.AQUA : ChatFormatting.GOLD;
        }
        return original.call(instance);
    }
}
