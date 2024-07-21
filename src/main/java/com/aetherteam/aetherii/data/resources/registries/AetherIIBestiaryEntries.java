package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIBestiaryEntries {
    public static final ResourceKey<Registry<BestiaryEntry>> BESTIARY_ENTRY_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "bestiary_entry"));

    public static final ResourceKey<BestiaryEntry> FLYING_COW = createKey("flying_cow");
    public static final ResourceKey<BestiaryEntry> SHEEPUFF = createKey("sheepuff");
    public static final ResourceKey<BestiaryEntry> PHYG = createKey("phyg");
    public static final ResourceKey<BestiaryEntry> AERBUNNY = createKey("aerbunny");
    public static final ResourceKey<BestiaryEntry> HIGHFIELDS_TAEGORE = createKey("highfields_taegore");
    public static final ResourceKey<BestiaryEntry> MAGNETIC_TAEGORE = createKey("magnetic_taegore");
    public static final ResourceKey<BestiaryEntry> ARCTIC_TAEGORE = createKey("arctic_taegore");
    public static final ResourceKey<BestiaryEntry> HIGHFIELDS_BURRUKAI = createKey("highfields_burrukai");
    public static final ResourceKey<BestiaryEntry> MAGNETIC_BURRUKAI = createKey("magnetic_burrukai");
    public static final ResourceKey<BestiaryEntry> ARCTIC_BURRUKAI = createKey("arctic_burrukai");
    public static final ResourceKey<BestiaryEntry> HIGHFIELDS_KIRRID = createKey("highfields_kirrid");
    public static final ResourceKey<BestiaryEntry> MAGNETIC_KIRRID = createKey("magnetic_kirrid");
    public static final ResourceKey<BestiaryEntry> ARCTIC_KIRRID = createKey("arctic_kirrid");
    public static final ResourceKey<BestiaryEntry> MOA = createKey("moa");
    public static final ResourceKey<BestiaryEntry> SKYROOT_LIZARD = createKey("skyroot_lizard");
    public static final ResourceKey<BestiaryEntry> AECHOR_PLANT = createKey("aechor_plant");
    public static final ResourceKey<BestiaryEntry> ZEPHYR = createKey("zephyr");
    public static final ResourceKey<BestiaryEntry> TEMPEST = createKey("tempest");
    public static final ResourceKey<BestiaryEntry> COCKATRICE = createKey("cockatrice");

    public static final Map<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> ENTITIES = Map.ofEntries(
            Map.entry(FLYING_COW, AetherIIEntityTypes.FLYING_COW),
            Map.entry(SHEEPUFF, AetherIIEntityTypes.SHEEPUFF),
            Map.entry(PHYG, AetherIIEntityTypes.PHYG),
            Map.entry(AERBUNNY, AetherIIEntityTypes.AERBUNNY),
            Map.entry(HIGHFIELDS_TAEGORE, AetherIIEntityTypes.HIGHFIELDS_TAEGORE),
            Map.entry(MAGNETIC_TAEGORE, AetherIIEntityTypes.MAGNETIC_TAEGORE),
            Map.entry(ARCTIC_TAEGORE, AetherIIEntityTypes.ARCTIC_TAEGORE),
            Map.entry(HIGHFIELDS_BURRUKAI, AetherIIEntityTypes.HIGHFIELDS_BURRUKAI),
            Map.entry(MAGNETIC_BURRUKAI, AetherIIEntityTypes.MAGNETIC_BURRUKAI),
            Map.entry(ARCTIC_BURRUKAI, AetherIIEntityTypes.ARCTIC_BURRUKAI),
            Map.entry(HIGHFIELDS_KIRRID, AetherIIEntityTypes.HIGHFIELDS_KIRRID),
            Map.entry(MAGNETIC_KIRRID, AetherIIEntityTypes.MAGNETIC_KIRRID),
            Map.entry(ARCTIC_KIRRID, AetherIIEntityTypes.ARCTIC_KIRRID),
            Map.entry(MOA, AetherIIEntityTypes.MOA),
            Map.entry(SKYROOT_LIZARD, AetherIIEntityTypes.SKYROOT_LIZARD),
            Map.entry(AECHOR_PLANT, AetherIIEntityTypes.AECHOR_PLANT),
            Map.entry(ZEPHYR, AetherIIEntityTypes.ZEPHYR),
            Map.entry(TEMPEST, AetherIIEntityTypes.TEMPEST),
            Map.entry(COCKATRICE, AetherIIEntityTypes.COCKATRICE)
    );
    public static final Map<Holder<EntityType<?>>, TagKey<Item>> FED = Map.ofEntries(
            Map.entry(AetherIIEntityTypes.FLYING_COW, AetherIITags.Items.FLYING_COW_FOOD),
            Map.entry(AetherIIEntityTypes.SHEEPUFF, AetherIITags.Items.SHEEPUFF_FOOD),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(AetherIIEntityTypes.MAGNETIC_TAEGORE, AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(AetherIIEntityTypes.ARCTIC_TAEGORE, AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(AetherIIEntityTypes.MAGNETIC_BURRUKAI, AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(AetherIIEntityTypes.ARCTIC_BURRUKAI, AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_KIRRID, AetherIITags.Items.KIRRID_FOOD),
            Map.entry(AetherIIEntityTypes.MAGNETIC_KIRRID, AetherIITags.Items.KIRRID_FOOD),
            Map.entry(AetherIIEntityTypes.ARCTIC_KIRRID, AetherIITags.Items.KIRRID_FOOD),
            Map.entry(AetherIIEntityTypes.MOA, AetherIITags.Items.MOA_FOOD)
    );

    private static ResourceKey<BestiaryEntry> createKey(String name) {
        return ResourceKey.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<BestiaryEntry> context) {
        String path = "bestiary/";
        for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : ENTITIES.entrySet()) {
            Holder<EntityType<?>> holder = entry.getValue();
            EntityType<?> entity = holder.value();
            Optional<TagKey<Item>> food = Optional.empty();
            if (FED.containsKey(holder)) {
                food = Optional.of(FED.get(holder));
            }
            ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entity.toShortString()).withPrefix(path);
            ResourceLocation understandId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "understand_" + entity.toShortString()).withPrefix(path);
            context.register(entry.getKey(), new BestiaryEntry(holder,
                    ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/" + entity.toShortString()),
                    "aether_ii.guidebook_bestiary.entity.aether_ii." + entity.toShortString(),
                    food,
                    observeId,
                    understandId
            ));
        }
    }

    public static Registry<BestiaryEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
    }
}
