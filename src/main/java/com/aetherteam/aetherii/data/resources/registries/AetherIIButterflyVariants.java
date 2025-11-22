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
        register(context, GLITTERWING, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.HIGHLANDS, 1));
        register(context, HIGHLAND, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.HIGHLANDS, 1));
        register(context, AMBER_MOTH, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.HIGHLANDS, 1));

        register(context, ORANGE_MIMIC, 0.0F, 40.0F, biomes(context, AetherIITags.Biomes.HIGHFIELDS, 1), random(10, 4, 1));

        register(context, LEAF_INSECT, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(10, 4, 1));
        register(context, DRAGONFLY, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(10, 4, 1));
        register(context, DRAPEWING, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.LUSH, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(10, 4, 1));

        register(context, QUICKSOIL_MOTH, 2.0F, 0.0F, biomes(context, AetherIITags.Biomes.WET, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(10, 4, 1));
        register(context, SUNSET_DRAGONFLY, 1.0F, 0.0F, biomes(context, AetherIITags.Biomes.WET, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(10, 4, 1));

        register(context, PHANTOMFLY, 2.0F, 0.0F, light(0, 8, 2), random(10, 7, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1), random(20, 4, 1));
        register(context, BLIGHTFLY, 2.0F, 0.0F, true, light(0, 8, 2), random(10, 7, 2), biomes(context, AetherIITags.Biomes.HIGHLANDS, 1),  random(20, 4, 1));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biomes(BootstrapContext<ButterflyVariant> context, TagKey<Biome> biomeTag, int priority) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new PriorityProvider.Selector<>(new BiomeCheck(biomes), priority);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> light(int min, int max, int priority) {
        return new PriorityProvider.Selector<>(new LightCheck(MinMaxBounds.Ints.between(min, max)), priority);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> random(int bound, int check, int priority) {
        return new PriorityProvider.Selector<>(new RandomCheck(bound, check), priority);
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
