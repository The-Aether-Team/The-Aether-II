package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.entity.passive.Cherub;
import com.aetherteam.aetherii.entity.variant.CherubVariant;
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

public class AetherIICherubVariants {
    public static final ResourceKey<CherubVariant> CHONK_GOLDBILL = createKey("goldbill");
    public static final ResourceKey<CherubVariant> CHONK_OVERCAST = createKey("overcast");
    public static final ResourceKey<CherubVariant> CHONK_PHOENIX = createKey("phoenix");
    public static final ResourceKey<CherubVariant> CHONK_WHISKER = createKey("whisker");
    public static final ResourceKey<CherubVariant> CHONK_WILLOW = createKey("willow");

    public static final ResourceKey<CherubVariant> FINCH_AMBER = createKey("amber");
    public static final ResourceKey<CherubVariant> FINCH_ARCTIC = createKey("arctic");
    public static final ResourceKey<CherubVariant> FINCH_BLIGHT = createKey("blight");
    public static final ResourceKey<CherubVariant> FINCH_BUNNY = createKey("bunny");
    public static final ResourceKey<CherubVariant> FINCH_GLINT = createKey("glint");
    public static final ResourceKey<CherubVariant> FINCH_MAGNETIC = createKey("magnetic");
    public static final ResourceKey<CherubVariant> FINCH_MOUNTAIN = createKey("mountain");
    public static final ResourceKey<CherubVariant> FINCH_SPICE = createKey("spice");

    public static final ResourceKey<CherubVariant> MACAW_GUST = createKey("gust");
    public static final ResourceKey<CherubVariant> MACAW_HORNED = createKey("horned");
    public static final ResourceKey<CherubVariant> MACAW_ORANGE = createKey("orange");
    public static final ResourceKey<CherubVariant> MACAW_SAILBACK = createKey("sailback");
    public static final ResourceKey<CherubVariant> MACAW_SPECKLED = createKey("speckled");

    public static final ResourceKey<CherubVariant> PHEASANT_BLOSSOM = createKey("blossom");
    public static final ResourceKey<CherubVariant> PHEASANT_HIGHLAND = createKey("highland");

    public static final ResourceKey<CherubVariant> WARBLER = createKey("warbler");

    private static ResourceKey<CherubVariant> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.CHERUB_VARIANT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<CherubVariant> context) {
        register(context, CHONK_GOLDBILL, Cherub.CherubType.CHONK, biomes(context, AetherIITags.Biomes.HIGHFIELDS), biomes(context, AetherIITags.Biomes.MAGNETIC));
        register(context, CHONK_OVERCAST, Cherub.CherubType.CHONK, biome(context, HolyIslesBiomes.FRIGID_SIERRA));
        register(context, CHONK_PHOENIX, Cherub.CherubType.CHONK, biomes(context, AetherIITags.Biomes.HIGHFIELDS));
        register(context, CHONK_WHISKER, Cherub.CherubType.CHONK, biomes(context, AetherIITags.Biomes.MAGNETIC), biomes(context, AetherIITags.Biomes.ARCTIC));
        register(context, CHONK_WILLOW, Cherub.CherubType.CHONK, biome(context, HolyIslesBiomes.GLISTENING_SWAMP));

        register(context, FINCH_AMBER, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.HOLY_ISLES));
        register(context, FINCH_ARCTIC, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.ARCTIC));
        register(context, FINCH_BLIGHT, Cherub.CherubType.FINCH, light(0, 8), random(biomeCheck(context, AetherIITags.Biomes.HOLY_ISLES), 100, 85));
        register(context, FINCH_BUNNY, Cherub.CherubType.FINCH, biome(context, HolyIslesBiomes.VERDANT_WOODS));
        register(context, FINCH_GLINT, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.HOLY_ISLES));
        register(context, FINCH_MAGNETIC, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.MAGNETIC));
        register(context, FINCH_MOUNTAIN, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.ARCTIC));
        register(context, FINCH_SPICE, Cherub.CherubType.FINCH, biomes(context, AetherIITags.Biomes.HIGHFIELDS));

        register(context, MACAW_GUST, Cherub.CherubType.MACAW, biomes(context, AetherIITags.Biomes.HOLY_ISLES));
        register(context, MACAW_HORNED, Cherub.CherubType.MACAW, biome(context, HolyIslesBiomes.FLOURISHING_FIELD), biome(context, HolyIslesBiomes.VERDANT_WOODS));
        register(context, MACAW_ORANGE, Cherub.CherubType.MACAW, biomes(context, AetherIITags.Biomes.HIGHFIELDS));
        register(context, MACAW_SAILBACK, Cherub.CherubType.MACAW, biomes(context, AetherIITags.Biomes.MAGNETIC), biomes(context, AetherIITags.Biomes.ARCTIC));
        register(context, MACAW_SPECKLED, Cherub.CherubType.MACAW, biomes(context, AetherIITags.Biomes.ARCTIC));

        register(context, PHEASANT_BLOSSOM, Cherub.CherubType.PHEASANT, biomes(context, AetherIITags.Biomes.HIGHFIELDS));
        register(context, PHEASANT_HIGHLAND, Cherub.CherubType.PHEASANT, biome(context, HolyIslesBiomes.SHROUDED_FOREST), biome(context, HolyIslesBiomes.VIOLET_HIGHWOODS), biome(context, HolyIslesBiomes.ENDURING_WOODLAND));

        register(context, WARBLER, Cherub.CherubType.WARBLER, biomes(context, AetherIITags.Biomes.WET));
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> random(SpawnCondition condition, int bound, int check) {
        return new PriorityProvider.Selector<>(new RandomCheck(condition, bound, check), 1);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biome(BootstrapContext<CherubVariant> context, ResourceKey<Biome> biomeKey) {
        HolderSet<Biome> biomes = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biomeKey));
        return new PriorityProvider.Selector<>(new BiomeCheck(biomes), 1);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> biomes(BootstrapContext<CherubVariant> context, TagKey<Biome> biomeTag) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new PriorityProvider.Selector<>(new BiomeCheck(biomes), 1);
    }

    private static BiomeCheck biomeCheck(BootstrapContext<CherubVariant> context, TagKey<Biome> biomeTag) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(biomeTag);
        return new BiomeCheck(biomes);
    }

    private static PriorityProvider.Selector<SpawnContext, SpawnCondition> light(int min, int max) {
        return new PriorityProvider.Selector<>(lightCheck(min, max), 1);
    }

    private static LightCheck lightCheck(int min, int max) {
        return new LightCheck(MinMaxBounds.Ints.between(min, max));
    }

    private static void register(BootstrapContext<CherubVariant> context, ResourceKey<CherubVariant> key, Cherub.CherubType type, PriorityProvider.Selector<SpawnContext, SpawnCondition>... spawnSelectors) {
        register(context, key, type, new SpawnPrioritySelectors(List.of(spawnSelectors)));
    }

    private static void register(BootstrapContext<CherubVariant> context, ResourceKey<CherubVariant> key, Cherub.CherubType type, SpawnPrioritySelectors spawnConditions) {
        Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/cherub/" + type.toString().toLowerCase() + "/" + key.identifier().getPath() + ".png");
        context.register(key, new CherubVariant(texture, type, spawnConditions));
    }
}
