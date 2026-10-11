package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import com.aetherteam.aetherii.entity.variant.spawning.RandomCheck;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.*;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;

public class AetherIIShroudwingVariants {
    public static final ResourceKey<ShroudwingVariant> SCARAB = createKey("scarab");
    public static final ResourceKey<ShroudwingVariant> PURPLE = createKey("purple");
    public static final ResourceKey<ShroudwingVariant> FIRE = createKey("fire");
    public static final ResourceKey<ShroudwingVariant> ORE = createKey("ore");
    public static final ResourceKey<ShroudwingVariant> ARCTIC = createKey("arctic");
    public static final ResourceKey<ShroudwingVariant> MOSS = createKey("moss");

    private static ResourceKey<ShroudwingVariant> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.SHROUDWING_VARIANT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ShroudwingVariant> context) {
        register(context, SCARAB, SpawnPrioritySelectors.fallback(1));
        register(context, PURPLE, random(biomeCheck(context, AetherIITags.Biomes.HOLY_ISLES), 100, 75), random(biomeCheck(context, AetherIITags.Biomes.MAGNETIC), 100, 75), random(biomeCheck(context, AetherIITags.Biomes.ARCTIC), 100, 75));
        register(context, FIRE, random(biomeCheck(context, AetherIITags.Biomes.HIGHFIELDS), 100, 50));
        register(context, ORE, biomes(context, AetherIITags.Biomes.HOLY_ISLES));
        register(context, ARCTIC, biomes(context, AetherIITags.Biomes.ARCTIC));
        register(context, MOSS, random(biomeCheck(context, AetherIITags.Biomes.HOLY_ISLES), 100, 35), random(biomeCheck(context, AetherIITags.Biomes.MAGNETIC), 100, 35));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> random(SpawnCondition condition, int bound, int check) {
        return new PriorityProvider.Selector<>(new RandomCheck(condition, bound, check), 1);
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
        Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/shroudwing/" + key.identifier().getPath() + ".png");
        Identifier emissiveTexture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/shroudwing/" + key.identifier().getPath() + "_glow.png");

        context.register(key, new ShroudwingVariant(texture, emissive ? Optional.of(emissiveTexture) : Optional.empty(), spawnConditions));
    }
}
