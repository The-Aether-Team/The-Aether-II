package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.gui.screen.menu.CustomBranding;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/internal/BrandingControl;forEachLine(ZZLjava/util/function/BiConsumer;)V", remap = false))
    private void aether_ii$forEachLine(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphics guiGraphics, @Local(ordinal = 2) int alpha) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (!(titleScreen instanceof CustomBranding customBranding) || !customBranding.forEachLineBranding(includeMC, reverse, lineConsumer, guiGraphics, alpha)) {
            original.call(includeMC, reverse, lineConsumer);
        }
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/internal/BrandingControl;forEachAboveCopyrightLine(Ljava/util/function/BiConsumer;)V", remap = false))
    private void aether_ii$forEachAboveCopyrightLine(BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphics guiGraphics, @Local(ordinal = 2) int alpha) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (!(titleScreen instanceof CustomBranding customBranding) || !customBranding.forEachAboveCopyrightLineBranding(lineConsumer, guiGraphics, alpha)) {
            original.call(lineConsumer);
        }
    }
}
