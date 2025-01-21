package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.SwetVariant;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Optional;

public class AetherIISwetVariants {
    public static final ResourceKey<Registry<SwetVariant>> SWET_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "swet_variant"));

    public static final ResourceKey<SwetVariant> BLUE = createKey("blue");
    public static final ResourceKey<SwetVariant> GREEN = createKey("green");
    public static final ResourceKey<SwetVariant> PURPLE = createKey("purple");
    public static final ResourceKey<SwetVariant> GOLDEN = createKey("golden");

    private static ResourceKey<SwetVariant> createKey(String name) {
        return ResourceKey.create(AetherIISwetVariants.SWET_VARIANT_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<SwetVariant> context) {
        register(context, BLUE, "blue", AetherIIItems.BLUE_SWET_GEL, AetherIITags.Biomes.BLUE_SWET_SPAWNING);
        register(context, GREEN, "green", AetherIIItems.GREEN_SWET_GEL, AetherIITags.Biomes.GREEN_SWET_SPAWNING);
        register(context, PURPLE, "purple", AetherIIItems.PURPLE_SWET_GEL, AetherIITags.Biomes.PURPLE_SWET_SPAWNING);
        register(context, GOLDEN, "golden", AetherIIItems.GOLDEN_SWET_GEL, AetherIITags.Biomes.GOLDEN_SWET_SPAWNING);
    }

    private static void register(BootstrapContext<SwetVariant> context, ResourceKey<SwetVariant> key, String name, Holder<Item> gelItem, ResourceKey<Biome> spawnBiome) {
        register(context, key, name, gelItem, HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(spawnBiome)));
    }

    private static void register(BootstrapContext<SwetVariant> context, ResourceKey<SwetVariant> key, String name, Holder<Item> gelItem, TagKey<Biome> spawnBiomes) {
        register(context, key, name, gelItem, context.lookup(Registries.BIOME).getOrThrow(spawnBiomes));
    }

    private static void register(BootstrapContext<SwetVariant> context, ResourceKey<SwetVariant> key, String name, Holder<Item> gelItem, HolderSet<Biome> spawnBiomes) {
        context.register(key, new SwetVariant(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/" + name + ".png"), gelItem, spawnBiomes));
    }

    public static Holder<SwetVariant> getSpawnVariant(RandomSource random, RegistryAccess registryAccess, Holder<Biome> biome) {
        Registry<SwetVariant> registry = registryAccess.lookupOrThrow(AetherIISwetVariants.SWET_VARIANT_REGISTRY_KEY);
        Optional<Holder.Reference<SwetVariant>> optional = registry.listElements().filter((variant) -> variant.value().biomes().contains(biome)).skip(random.nextInt(registry.listElements().toList().size())).findFirst().or(() -> registry.get(BLUE));
        Objects.requireNonNull(registry);
        return optional.or(registry::getAny).orElseThrow();
    }

    public static Registry<SwetVariant> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIISwetVariants.SWET_VARIANT_REGISTRY_KEY);
    }
}
