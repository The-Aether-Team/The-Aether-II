package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.blockentity.MuralSection;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AetherIIClientCaches {
    public static final Map<MuralBlockEntity.MuralData, List<BlockModelPart>> CACHED_MURAL_BLOCK_PARTS = new ConcurrentHashMap<>();
    public static final Map<MuralSection, List<BakedQuad>> CACHED_MURAL_ITEM_PARTS = new ConcurrentHashMap<>();

    public static void registerReloadListeners(AddClientReloadListenersEvent event) { // Clear cache as UVs can change from resource packs
        event.addListener(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural_cache"), (ResourceManagerReloadListener) resourceManager -> {
            CACHED_MURAL_BLOCK_PARTS.clear();
            CACHED_MURAL_ITEM_PARTS.clear();
        });
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) { // Clear stale holders to prevent memory leaks
        CACHED_MURAL_BLOCK_PARTS.clear();
        CACHED_MURAL_ITEM_PARTS.clear();
    }
}
