package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public static final List<Holder<EntityType<?>>> NAMED = List.of(
            AetherIIEntityTypes.HIGHFIELDS_TAEGORE,
            AetherIIEntityTypes.MAGNETIC_TAEGORE,
            AetherIIEntityTypes.ARCTIC_TAEGORE,
            AetherIIEntityTypes.HIGHFIELDS_BURRUKAI,
            AetherIIEntityTypes.MAGNETIC_BURRUKAI,
            AetherIIEntityTypes.ARCTIC_BURRUKAI,
            AetherIIEntityTypes.HIGHFIELDS_KIRRID,
            AetherIIEntityTypes.MAGNETIC_KIRRID,
            AetherIIEntityTypes.ARCTIC_KIRRID
    );
    public static final Map<Holder<EntityType<?>>, List<BestiaryEntry.LootDisplay>> LOOT = Map.ofEntries(
            Map.entry(AetherIIEntityTypes.FLYING_COW, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 2))),
            Map.entry(AetherIIEntityTypes.SHEEPUFF, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 2), BestiaryEntry.LootDisplay.block(AetherIIBlocks.WHITE_CLOUDWOOL, 1.0, 1, 1))),
            Map.entry(AetherIIEntityTypes.PHYG, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 2))),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.TAEGORE_HIDE, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.MAGNETIC_TAEGORE, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.TAEGORE_HIDE, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.ARCTIC_TAEGORE, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.TAEGORE_HIDE, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PELT, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.MAGNETIC_BURRUKAI, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PELT, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.ARCTIC_BURRUKAI, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PELT, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.HIGHFIELDS_KIRRID, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.MAGNETIC_KIRRID, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.ARCTIC_KIRRID, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(AetherIIEntityTypes.MOA, List.of(BestiaryEntry.LootDisplay.item(Items.FEATHER.builtInRegistryHolder(), 1.0, 0, 2))),
            Map.entry(AetherIIEntityTypes.AECHOR_PLANT, List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.AECHOR_PETAL, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.AECHOR_CUTTING, 1.0, 0, 1))),
            Map.entry(AetherIIEntityTypes.ZEPHYR, List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.COLD_AERCLOUD, 1.0, 0, 2))),
            Map.entry(AetherIIEntityTypes.TEMPEST, List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.STORM_AERCLOUD, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.CHARGE_CORE, 1.0, 0, 1))),
            Map.entry(AetherIIEntityTypes.COCKATRICE, List.of(BestiaryEntry.LootDisplay.item(Items.FEATHER.builtInRegistryHolder(), 1.0, 1, 3)))
    );
    public static final Map<Holder<EntityType<?>>, TagKey<Item>> FED = Map.ofEntries(
            Map.entry(AetherIIEntityTypes.FLYING_COW, AetherIITags.Items.FLYING_COW_FOOD),
            Map.entry(AetherIIEntityTypes.SHEEPUFF, AetherIITags.Items.SHEEPUFF_FOOD),
            Map.entry(AetherIIEntityTypes.PHYG, AetherIITags.Items.PHYG_FOOD),
            Map.entry(AetherIIEntityTypes.AERBUNNY, AetherIITags.Items.AERBUNNY_FOOD),
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
            Optional<String> name = Optional.empty();
            Optional<String> slotName = Optional.empty();
            Optional<String> slotSubtitle = Optional.empty();
            if (NAMED.contains(holder)) {
                name = Optional.of("aether_ii.guidebook_bestiary.name.entity.aether_ii." + entity.toShortString());
                slotName = Optional.of("aether_ii.guidebook_bestiary.slot_name.entity.aether_ii." + entity.toShortString());
                slotSubtitle = Optional.of("aether_ii.guidebook_bestiary.slot_subtitle.entity.aether_ii." + entity.toShortString());
            }
            List<BestiaryEntry.LootDisplay> loot = new ArrayList<>();
            if (LOOT.containsKey(holder)) {
                loot.addAll(LOOT.get(holder));
            }
            Optional<TagKey<Item>> food = Optional.empty();
            if (FED.containsKey(holder)) {
                food = Optional.of(FED.get(holder));
            }
            ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entity.toShortString()).withPrefix(path);
            ResourceLocation understandId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "understand_" + entity.toShortString()).withPrefix(path);
            context.register(entry.getKey(), new BestiaryEntry(holder,
                    ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/" + entity.toShortString()),
                    name,
                    slotName,
                    slotSubtitle,
                    "aether_ii.guidebook_bestiary.description.entity.aether_ii." + entity.toShortString(),
                    loot,
                    food,
                    observeId,
                    understandId
            ));
        }
    }

    public static Registry<BestiaryEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
    }

    public static Map<EntityType<?>, TagKey<Item>> getFedEntityTypes() {
         return AetherIIBestiaryEntries.FED.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().value(), Map.Entry::getValue));
    }
}
