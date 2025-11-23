package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.variant.ButterflyVariant;
import com.aetherteam.aetherii.entity.variant.spawning.LightCheck;
import com.aetherteam.aetherii.entity.variant.spawning.RandomCheck;
import net.minecraft.advancements.critereon.MinMaxBounds;
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

public class AetherIIButterflyVariants {
    public static final ResourceKey<Registry<ButterflyVariant>> BUTTERFLY_VARIANT_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "butterfly_variant"));

    public static final ResourceKey<ButterflyVariant> ORANGE_MIMIC = createKey("orange_mimic");
    public static final ResourceKey<ButterflyVariant> DRAGONFLY = createKey("dragonfly");
    public static final ResourceKey<ButterflyVariant> DRAPEWING = createKey("drapewing");
    public static final ResourceKey<ButterflyVariant> GLITTERWING = createKey("glitterwing");
    public static final ResourceKey<ButterflyVariant> HIGHLAND = createKey("highland");
    public static final ResourceKey<ButterflyVariant> SUNSET_DRAGONFLY = createKey("sunset_dragonfly");
    public static final ResourceKey<ButterflyVariant> QUICKSOIL_MOTH = createKey("quicksoil_moth");
    public static final ResourceKey<ButterflyVariant> AMBER_MOTH = createKey("amber_moth");
    public static final ResourceKey<ButterflyVariant> PHANTOMFLY = createKey("phantomfly");
    public static final ResourceKey<ButterflyVariant> BLIGHTFLY = createKey("blightfly");
    public static final ResourceKey<ButterflyVariant> LEAF_INSECT = createKey("leaf_insect");

    private static ResourceKey<ButterflyVariant> createKey(String name) {
        return ResourceKey.create(AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ButterflyVariant> context) {
        register(context, GLITTERWING, 1.0F, 0.0F, SpawnPrioritySelectors.fallback(1));
        register(context, HIGHLAND, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.HIGHLANDS));
        register(context, AMBER_MOTH, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.HIGHLANDS));

        register(context, ORANGE_MIMIC, 0.0F, 40.0F, random(biomeCheck(context, AetherIITags.Biomes.HIGHFIELDS), 100, 25));

        register(context, LEAF_INSECT, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 50));
        register(context, DRAGONFLY, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 50));
        register(context, DRAPEWING, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 50));

        register(context, QUICKSOIL_MOTH, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.WET), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 75));
        register(context, SUNSET_DRAGONFLY, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.WET), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 75));

        register(context, PHANTOMFLY, 2.0F, 0.0F, light(0, 8), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 85));
        register(context, BLIGHTFLY, 2.0F, 0.0F, true, light(0, 8), random(biomeCheck(context, AetherIITags.Biomes.HIGHLANDS), 100, 85));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> random(SpawnCondition condition, int bound, int check) {
        return new PriorityProvider.Selector<>(new RandomCheck(condition, bound, check), 1);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biomes(BootstrapContext<ButterflyVariant> context, TagKey<Biome> biomeTag) {
        return new PriorityProvider.Selector<>(biomeCheck(context, biomeTag), 1);
    }

    private static BiomeCheck biomeCheck(BootstrapContext<ButterflyVariant> context, TagKey<Biome> biomeTag) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new BiomeCheck(biomes);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> light(int min, int max) {
        return new PriorityProvider.Selector<>(lightCheck(min, max), 1);
    }

    private static LightCheck lightCheck(int min, int max) {
        return new LightCheck(MinMaxBounds.Ints.between(min, max));
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, float wingXOffset, float wingZRotation, boolean emissive, SpawnPrioritySelectors spawnSelectors) {
        register(context, key, spawnSelectors, wingXOffset, wingZRotation, emissive);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, float wingXOffset, float wingZRotation, SpawnPrioritySelectors spawnSelectors) {
        register(context, key, spawnSelectors, wingXOffset, wingZRotation, false);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, float wingXOffset, float wingZRotation, boolean emissive, PriorityProvider.Selector<SpawnContext, SpawnCondition>... spawnSelectors) {
        register(context, key, new SpawnPrioritySelectors(List.of(spawnSelectors)), wingXOffset, wingZRotation, emissive);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, float wingXOffset, float wingZRotation, PriorityProvider.Selector<SpawnContext, SpawnCondition>... spawnSelectors) {
        register(context, key, new SpawnPrioritySelectors(List.of(spawnSelectors)), wingXOffset, wingZRotation, false);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, SpawnPrioritySelectors spawnConditions, float wingXOffset, float wingZRotation, boolean emissive) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/butterfly/" + key.location().getPath() + ".png");
        ResourceLocation emissiveTexture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/butterfly/" + key.location().getPath() + "_glow.png");

        context.register(key, new ButterflyVariant(texture, emissive ? Optional.of(emissiveTexture) : Optional.empty(), spawnConditions, wingXOffset, wingZRotation));
    }
}
