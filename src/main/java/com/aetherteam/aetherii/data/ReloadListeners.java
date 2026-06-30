package com.aetherteam.aetherii.data;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.FreezingBlock;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;

import java.util.Map;

public class ReloadListeners {
    public static ResourceLocation RECIPE_CACHING = new ResourceLocation(AetherII.MODID, "recipe_caching");
    private static final Gson GSON = new Gson();

    /**
     * @see Aether#eventSetup()
     */
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener());
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener {
        public RecipeReloadListener() {
            super(GSON, "recipes");
        }

        /**
         * Resets the block caches for {@link FreezingBlock} recipes.
         */
        @Override
        protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
            FreezingBlock.cachedBlocks.clear();
            FreezingBlock.cachedResults.clear();
        }
    }
}
