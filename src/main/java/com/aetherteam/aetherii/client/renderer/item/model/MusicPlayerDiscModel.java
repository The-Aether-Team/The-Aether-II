package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.StoredMusic;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundEngineAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundManagerAccessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class MusicPlayerDiscModel implements ItemModel {
    static final ItemModel INSTANCE = new MusicPlayerDiscModel();

    @Override
    public void update(ItemStackRenderState itemStackRenderState, ItemStack itemStack, ItemModelResolver itemModelResolver, ItemDisplayContext itemDisplayContext, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        itemStackRenderState.appendModelIdentityElement(this);
        StoredMusic music = itemStack.get(AetherIIDataComponents.STORED_MUSIC);
        if (music != null) {
            ItemStack selectedStack = music.item().value().getDefaultInstance();
            if (!selectedStack.isEmpty()) {
                ItemStack displayStack = selectedStack.copy();
                ResourceLocation model = selectedStack.get(DataComponents.ITEM_MODEL);
                if (model != null) {
                    ResourceLocation location = model.withSuffix("_animated");
                    if (AetherIIClientProxy.isPlayingSoundEvent(music.sound().value())) {
                        displayStack.set(DataComponents.ITEM_MODEL, location);
                    }
                }
                itemModelResolver.appendItemLayers(itemStackRenderState, displayStack, itemDisplayContext, clientLevel, livingEntity, i);
            }
        }
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<MusicPlayerDiscModel.Unbaked> MAP_CODEC = MapCodec.unit(new MusicPlayerDiscModel.Unbaked());

        @Override
        public MapCodec<MusicPlayerDiscModel.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(ItemModel.BakingContext context) {
            return MusicPlayerDiscModel.INSTANCE;
        }

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) { }
    }
}
