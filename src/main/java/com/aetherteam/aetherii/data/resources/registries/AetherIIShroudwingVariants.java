package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.*;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;

public class AetherIIShroudwingVariants {
    public static final ResourceKey<Registry<ShroudwingVariant>> SHROUDWING_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "shroudwing_variant"));

    public static final ResourceKey<ShroudwingVariant> SHROUDWING = createKey("shroudwing");
    public static final ResourceKey<ShroudwingVariant> SCARAB = createKey("scarab");
    public static final ResourceKey<ShroudwingVariant> FIRE_BEETLE = createKey("fire_beetle");

    private static ResourceKey<ShroudwingVariant> createKey(String name) {
        return ResourceKey.create(AetherIIShroudwingVariants.SHROUDWING_VARIANT_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ShroudwingVariant> context) {
        register(context, SHROUDWING, SpawnPrioritySelectors.fallback(1));
        register(context, SCARAB, biomes(context, AetherIITags.Biomes.HIGHLANDS));
        register(context, FIRE_BEETLE, biomes(context, AetherIITags.Biomes.HIGHLANDS));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biomes(BootstrapContext<ShroudwingVariant> context, TagKey<Biome> biomeTag) {
        return new PriorityProvider.Selector<>(biomeCheck(context, biomeTag), 1);
    }

    private static BiomeCheck biomeCheck(BootstrapContext<ShroudwingVariant> context, TagKey<Biome> biomeTag) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new BiomeCheck(biomes);
    }

    private static void register(BootstrapContext<ShroudwingVariant> context, ResourceKey<ShroudwingVariant> key, SpawnPrioritySelectors spawnSelectors) {
        register(context, key, spawnSelectors, false);
    }

    private static void register(BootstrapContext<ShroudwingVariant> context, ResourceKey<ShroudwingVariant> key, PriorityProvider.Selector<SpawnContext, SpawnCondition>... spawnSelectors) {
        register(context, key, new SpawnPrioritySelectors(List.of(spawnSelectors)), false);
    }

    private static void register(BootstrapContext<ShroudwingVariant> context, ResourceKey<ShroudwingVariant> key, SpawnPrioritySelectors spawnConditions, boolean emissive) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/shroudwing/" + key.location().getPath() + ".png");
        ResourceLocation emissiveTexture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/shroudwing/" + key.location().getPath() + "_glow.png");

        context.register(key, new ShroudwingVariant(texture, emissive ? Optional.of(emissiveTexture) : Optional.empty(), spawnConditions));
    }
}
