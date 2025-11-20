package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.ButterflyVariant;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

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
        register(context, ORANGE_MIMIC, AetherIITags.Biomes.HIGHLANDS, 0.0F, 40.0F);
        
        register(context, DRAGONFLY, AetherIITags.Biomes.HIGHLANDS, 1.0F, 0.0F);
        register(context, DRAPEWING, AetherIITags.Biomes.HIGHLANDS, 1.0F, 0.0F);
        register(context, GLITTERWING, AetherIITags.Biomes.HIGHLANDS, 1.0F, 0.0F);
        register(context, HIGHLAND, AetherIITags.Biomes.HIGHLANDS, 1.0F, 0.0F);
        register(context, SUNSET_DRAGONFLY, AetherIITags.Biomes.HIGHLANDS, 1.0F, 0.0F);

        register(context, QUICKSOIL_MOTH, AetherIITags.Biomes.HIGHLANDS, 2.0F, 0.0F);
        register(context, AMBER_MOTH, AetherIITags.Biomes.HIGHLANDS, 2.0F, 0.0F);
        register(context, PHANTOMFLY, AetherIITags.Biomes.HIGHLANDS, 2.0F, 0.0F);
        register(context, BLIGHTFLY, AetherIITags.Biomes.HIGHLANDS, 2.0F, 0.0F);
        register(context, LEAF_INSECT, AetherIITags.Biomes.HIGHLANDS, 2.0F, 0.0F);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, ResourceKey<Biome> biome, float wingXOffset, float wingZRotation) {
        register(context, key, highPrioBiome(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome))), wingXOffset, wingZRotation);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, TagKey<Biome> biomes, float wingXOffset, float wingZRotation) {
        register(context, key, highPrioBiome(context.lookup(Registries.BIOME).getOrThrow(biomes)), wingXOffset, wingZRotation);
    }

    private static SpawnPrioritySelectors highPrioBiome(HolderSet<Biome> biomes) {
        return SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1);
    }

    private static void register(BootstrapContext<ButterflyVariant> context, ResourceKey<ButterflyVariant> key, SpawnPrioritySelectors spawnConditions, float wingXOffset, float wingZRotation) {
        context.register(key, new ButterflyVariant(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/butterfly/" + key.location().getPath() + ".png"), spawnConditions, wingXOffset, wingZRotation));
    }
}
