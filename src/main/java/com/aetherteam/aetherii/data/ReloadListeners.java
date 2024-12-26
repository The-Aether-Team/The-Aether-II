package com.aetherteam.aetherii.data;

import com.aetherteam.aetherii.block.FreezingBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;

public class ReloadListeners {
    /**
     * @see Aether#eventSetup()
     */
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener());
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener<JsonElement> {
        public RecipeReloadListener() {
            super(ExtraCodecs.JSON, FileToIdConverter.json("recipes"));
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