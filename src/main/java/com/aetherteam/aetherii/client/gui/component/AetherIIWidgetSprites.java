package com.aetherteam.aetherii.client.gui.component;

import net.minecraft.resources.ResourceLocation;

public record AetherIIWidgetSprites(ResourceLocation enabled, ResourceLocation disabled, ResourceLocation enabledFocused, ResourceLocation disabledFocused) {
    public AetherIIWidgetSprites(ResourceLocation enabled, ResourceLocation focused) {
        this(enabled, enabled, focused, focused);
    }

    public AetherIIWidgetSprites(ResourceLocation enabled, ResourceLocation disabled, ResourceLocation focused) {
        this(enabled, disabled, focused, focused);
    }

    public ResourceLocation get(boolean active, boolean focused) {
        if (active) {
            return focused ? this.enabledFocused() : this.enabled();
        } else {
            return focused ? this.disabledFocused() : this.disabled();
        }
    }
}


