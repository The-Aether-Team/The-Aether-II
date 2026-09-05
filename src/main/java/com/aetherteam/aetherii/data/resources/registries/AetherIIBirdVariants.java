package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.entity.passive.Bird;
import com.aetherteam.aetherii.entity.variant.BirdVariant;
import com.aetherteam.aetherii.entity.variant.spawning.LightCheck;
import com.aetherteam.aetherii.entity.variant.spawning.RandomCheck;
import net.minecraft.advancements.criterion.MinMaxBounds;
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

public class AetherIIBirdVariants {
    public static final ResourceKey<BirdVariant> CHONK_GOLDBILL = createKey("goldbill");
    public static final ResourceKey<BirdVariant> CHONK_OVERCAST = createKey("overcast");
    public static final ResourceKey<BirdVariant> CHONK_PHOENIX = createKey("phoenix");
    public static final ResourceKey<BirdVariant> CHONK_WHISKER = createKey("whisker");
    public static final ResourceKey<BirdVariant> CHONK_WILLOW = createKey("willow");

    public static final ResourceKey<BirdVariant> FINCH_AMBER = createKey("amber");
    public static final ResourceKey<BirdVariant> FINCH_ARCTIC = createKey("arctic");
    public static final ResourceKey<BirdVariant> FINCH_BLIGHT = createKey("blight");
    public static final ResourceKey<BirdVariant> FINCH_BUNNY = createKey("bunny");
    public static final ResourceKey<BirdVariant> FINCH_GLINT = createKey("glint");
    public static final ResourceKey<BirdVariant> FINCH_MAGNETIC = createKey("magnetic");
    public static final ResourceKey<BirdVariant> FINCH_MOUNTAIN = createKey("mountain");
    public static final ResourceKey<BirdVariant> FINCH_SPICE = createKey("spice");

    public static final ResourceKey<BirdVariant> MACAW_GUST = createKey("gust");
    public static final ResourceKey<BirdVariant> MACAW_HORNED = createKey("horned");
    public static final ResourceKey<BirdVariant> MACAW_ORANGE = createKey("orange");
    public static final ResourceKey<BirdVariant> MACAW_SAILBACK = createKey("sailback");
    public static final ResourceKey<BirdVariant> MACAW_SPECKLED = createKey("speckled");

    public static final ResourceKey<BirdVariant> PHEASANT_BLOSSOM = createKey("blossom");
    public static final ResourceKey<BirdVariant> PHEASANT_HIGHLAND = createKey("highland");

    public static final ResourceKey<BirdVariant> WARBLER = createKey("warbler");

    private static ResourceKey<BirdVariant> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.BIRD_VARIANT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<BirdVariant> context) { //todo biome spawning
        register(context, CHONK_GOLDBILL, Bird.BirdType.CHONK, SpawnPrioritySelectors.fallback(1));
        register(context, CHONK_OVERCAST, Bird.BirdType.CHONK, SpawnPrioritySelectors.fallback(1));
        register(context, CHONK_PHOENIX, Bird.BirdType.CHONK, SpawnPrioritySelectors.fallback(1));
        register(context, CHONK_WHISKER, Bird.BirdType.CHONK, SpawnPrioritySelectors.fallback(1));
        register(context, CHONK_WILLOW, Bird.BirdType.CHONK, SpawnPrioritySelectors.fallback(1));

        register(context, FINCH_AMBER, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_ARCTIC, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_BLIGHT, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_BUNNY, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_GLINT, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_MAGNETIC, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_MOUNTAIN, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));
        register(context, FINCH_SPICE, Bird.BirdType.FINCH, SpawnPrioritySelectors.fallback(1));

        register(context, MACAW_GUST, Bird.BirdType.MACAW, SpawnPrioritySelectors.fallback(1));
        register(context, MACAW_HORNED, Bird.BirdType.MACAW, SpawnPrioritySelectors.fallback(1));
        register(context, MACAW_ORANGE, Bird.BirdType.MACAW, SpawnPrioritySelectors.fallback(1));
        register(context, MACAW_SAILBACK, Bird.BirdType.MACAW, SpawnPrioritySelectors.fallback(1));
        register(context, MACAW_SPECKLED, Bird.BirdType.MACAW, SpawnPrioritySelectors.fallback(1));

        register(context, PHEASANT_BLOSSOM, Bird.BirdType.PHEASANT, SpawnPrioritySelectors.fallback(1));
        register(context, PHEASANT_HIGHLAND, Bird.BirdType.PHEASANT, SpawnPrioritySelectors.fallback(1));

        register(context, WARBLER, Bird.BirdType.WARBLER, SpawnPrioritySelectors.fallback(1));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> random(SpawnCondition condition, int bound, int check) {
        return new PriorityProvider.Selector<>(new RandomCheck(condition, bound, check), 1);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biomes(BootstrapContext<BirdVariant> context, TagKey<Biome> biomeTag) {
        return new PriorityProvider.Selector<>(biomeCheck(context, biomeTag), 1);
    }

    private static BiomeCheck biomeCheck(BootstrapContext<BirdVariant> context, TagKey<Biome> biomeTag) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new BiomeCheck(biomes);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> light(int min, int max) {
        return new PriorityProvider.Selector<>(lightCheck(min, max), 1);
    }

    private static LightCheck lightCheck(int min, int max) {
        return new LightCheck(MinMaxBounds.Ints.between(min, max));
    }

    private static void register(BootstrapContext<BirdVariant> context, ResourceKey<BirdVariant> key, Bird.BirdType type, SpawnPrioritySelectors spawnConditions) {
        Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/bird/" + type.toString().toLowerCase() + "/" + key.identifier().getPath() + ".png");
        context.register(key, new BirdVariant(texture, type, spawnConditions));
    }
}
