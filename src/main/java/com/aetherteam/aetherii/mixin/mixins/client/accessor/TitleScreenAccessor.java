package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor("splash")
    SplashRenderer aetherII$getSplash();

    @Accessor("splash")
    void aetherII$setSplash(SplashRenderer splash);

    @Mutable
    @Accessor("fading")
    void aetherII$setFading(boolean fading);

    @Accessor("logoRenderer")
    LogoRenderer aetherII$getLogoRenderer();

    @Mutable
    @Accessor("logoRenderer")
    void aetherII$setLogoRenderer(LogoRenderer splash);

    @Invoker
    Component callGetMultiplayerDisabledReason();
}
