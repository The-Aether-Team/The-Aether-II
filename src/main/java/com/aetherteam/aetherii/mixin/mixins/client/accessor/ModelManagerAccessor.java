package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ModelManager.class)
public interface ModelManagerAccessor {
    @Accessor("VANILLA_ATLASES")
    static Map<ResourceLocation, ResourceLocation> getAtlases() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("VANILLA_ATLASES")
    static void setAtlases(Map<ResourceLocation, ResourceLocation> vanillaAtlases) {
        throw new UnsupportedOperationException();
    }
}
