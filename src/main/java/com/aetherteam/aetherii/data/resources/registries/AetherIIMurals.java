package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

public class AetherIIMurals {
    public static final DeferredRegister<Mural> MURALS = DeferredRegister.create(AetherIIRegistries.MURAL, AetherII.MODID);
    public static final Registry<Mural> MURALS_REGISTRY = MURALS.makeRegistry((builder) -> builder.sync(true).defaultKey(AetherIIRegistries.MURAL.identifier()));

    public static final DeferredHolder<Mural, Mural> TEST = register("test", 2, 2, "test_mural");
    public static final DeferredHolder<Mural, Mural> LARGE_TEST = register("large_test", 3, 2, "large_test_mural");
    public static final DeferredHolder<Mural, Mural> GIANT_TEST = register("giant_test", 4, 4, "giant_test_mural");

    private static DeferredHolder<Mural, Mural> register(String name, int width, int height, String assetId) {
        return register(name, width, height, assetId, Component.translatable(Identifier.fromNamespaceAndPath(AetherII.MODID, name).toLanguageKey("mural", "title")).withStyle(ChatFormatting.YELLOW));
    }

    private static DeferredHolder<Mural, Mural> register(String name, int width, int height, String assetId, @Nullable Component title) {
        var mural = new Mural(width, height, Identifier.fromNamespaceAndPath(AetherII.MODID, assetId), title);
        var holder = MURALS.register(name, id -> mural);
        return holder;
    }
}
