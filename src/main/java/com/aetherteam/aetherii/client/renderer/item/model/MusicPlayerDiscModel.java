package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponents;
import com.aetherteam.aetherii.item.components.StoredMusic;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MusicPlayerDiscModel {
    static final MusicPlayerDiscModel INSTANCE = new MusicPlayerDiscModel();

    public ItemStack getDisplayStack(ItemStack itemStack) {
        StoredMusic music = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.STORED_MUSIC);
        if (music != null) {
            ItemStack selectedStack = music.item().value().getDefaultInstance();
            if (!selectedStack.isEmpty()) {
                ItemStack displayStack = selectedStack.copy();
                ResourceLocation model = AetherIIDataComponents.get(selectedStack, DataComponents.ITEM_MODEL);
                if (model != null) {
                    ResourceLocation location = model.withSuffix("_animated");
                    if (AetherIIClientProxy.isMusicPlayerActive(music.getSoundEvent().value())) {
                        AetherIIDataComponents.set(displayStack, DataComponents.ITEM_MODEL, location);
                    }
                }
                return displayStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public record Unbaked() {
        public static final MapCodec<MusicPlayerDiscModel.Unbaked> MAP_CODEC = MapCodec.unit(new MusicPlayerDiscModel.Unbaked());

        public MapCodec<MusicPlayerDiscModel.Unbaked> type() {
            return MAP_CODEC;
        }

        public MusicPlayerDiscModel bake() {
            return MusicPlayerDiscModel.INSTANCE;
        }
    }
}
