package com.aetherteam.aetherii.world;

import com.aetherteam.aetherii.AetherIIConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    /**
     * Used to determine a destination dimension for Aether-related teleportation. By default, this is "aether:the_aether".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherIIConfig.Server#portal_destination_dimension_ID}.
     * @see com.aetherteam.aetherii.block.portal.AetherPortalBlock
     * @see com.aetherteam.aetherii.block.portal.AetherPortalForcer
     * @see com.aetherteam.aetherii.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> destinationDimension() {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(AetherIIConfig.SERVER.portal_destination_dimension_ID.get()));
    }

    /**
     * Used to determine a return dimension for Aether-related teleportation. By default, this is "minecraft:overworld".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherIIConfig.Server#portal_return_dimension_ID}.
     * @see com.aetherteam.aetherii.block.portal.AetherPortalBlock
     * @see com.aetherteam.aetherii.block.portal.AetherPortalForcer
     * @see com.aetherteam.aetherii.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> returnDimension() {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(AetherIIConfig.SERVER.portal_return_dimension_ID.get()));
    }
}