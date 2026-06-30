package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.blockentity.MuralSection;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AetherIIClientCaches {
    public static final Map<MuralBlockEntity.MuralData, List<BakedQuad>> CACHED_MURAL_BLOCK_PARTS = new ConcurrentHashMap<>();
    public static final Map<MuralSection, List<BakedQuad>> CACHED_MURAL_ITEM_PARTS = new ConcurrentHashMap<>();
    @Nullable
    public static RecipeManager CLIENT_CACHES = null;

    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) { // Clear cache as UVs can change from resource packs
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
            CACHED_MURAL_BLOCK_PARTS.clear();
            CACHED_MURAL_ITEM_PARTS.clear();
        });
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) { // Clear stale holders to prevent memory leaks
        CACHED_MURAL_BLOCK_PARTS.clear();
        CACHED_MURAL_ITEM_PARTS.clear();
    }

    public static void onReceiveRecipes(RecipesUpdatedEvent event) {
        CLIENT_CACHES = event.getRecipeManager();
    }
}
