package com.aetherteam.aetherii.data.resources.registries;

import javax.annotation.Nullable;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AetherIIMurals {
    public static final DeferredRegister<Mural> MURALS = DeferredRegister.create(AetherIIRegistries.MURAL, AetherII.MODID);
    public static final Supplier<IForgeRegistry<Mural>> MURALS_REGISTRY = MURALS.makeRegistry(() -> new RegistryBuilder<Mural>().setDefaultKey(AetherIIRegistries.MURAL.location()));

    public static final RegistryObject<Mural> TEST = register("test", 2, 2, "test_mural");
    public static final RegistryObject<Mural> LARGE_TEST = register("large_test", 3, 2, "large_test_mural");
    public static final RegistryObject<Mural> GIANT_TEST = register("giant_test", 4, 4, "giant_test_mural");

    private static RegistryObject<Mural> register(String name, int width, int height, String assetId) {
        return register(name, width, height, assetId, Component.translatable(new ResourceLocation(AetherII.MODID, name).toLanguageKey("mural", "title")).withStyle(ChatFormatting.YELLOW));
    }

    private static RegistryObject<Mural> register(String name, int width, int height, String assetId, @Nullable Component title) {
        var mural = new Mural(width, height, new ResourceLocation(AetherII.MODID, assetId), title);
        var holder = MURALS.register(name, () -> mural);
        return holder;
    }

    public static Optional<ResourceLocation> getKey(Mural mural) {
        IForgeRegistry<Mural> registry = MURALS_REGISTRY.get();
        return registry != null ? Optional.ofNullable(registry.getKey(mural)) : Optional.empty();
    }

    public static Optional<Holder<Mural>> getHolder(ResourceKey<Mural> key) {
        IForgeRegistry<Mural> registry = MURALS_REGISTRY.get();
        return registry != null ? registry.getHolder(key) : Optional.empty();
    }

    public static Stream<Holder<Mural>> holders() {
        IForgeRegistry<Mural> registry = MURALS_REGISTRY.get();
        if (registry == null) {
            return Stream.empty();
        }
        return registry.getEntries().stream()
                .map(entry -> registry.getHolder(entry.getKey()).orElseGet(() -> Holder.direct(entry.getValue())));
    }
}
