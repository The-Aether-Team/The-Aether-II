package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.attributes.EffectResistanceAttribute;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aetherteam.aetherii.util.RegistryObjectUtil.entity;

public class AetherIIBestiaryEntries {
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
    public static final ResourceKey<BestiaryEntry> PRISMALLARD = createKey("prismallard");
    public static final ResourceKey<BestiaryEntry> MOA = createKey("moa");
    public static final ResourceKey<BestiaryEntry> SKYROOT_LIZARD = createKey("skyroot_lizard");
    public static final ResourceKey<BestiaryEntry> GLITTERWING = createKey("glitterwing");
    public static final ResourceKey<BestiaryEntry> SHROUDWING = createKey("shroudwing");
    public static final ResourceKey<BestiaryEntry> AECHOR_PLANT = createKey("aechor_plant");
    public static final ResourceKey<BestiaryEntry> CARRION_SPROUT = createKey("carrion_sprout");
    public static final ResourceKey<BestiaryEntry> ZEPHYR = createKey("zephyr");
    public static final ResourceKey<BestiaryEntry> BLUE_SWET = createKey("blue_swet");
    public static final ResourceKey<BestiaryEntry> GOLDEN_SWET = createKey("golden_swet");
    public static final ResourceKey<BestiaryEntry> SKEPHID = createKey("skephid");
    public static final ResourceKey<BestiaryEntry> TEMPEST = createKey("tempest");
    public static final ResourceKey<BestiaryEntry> COCKATRICE = createKey("cockatrice");
    public static final ResourceKey<BestiaryEntry> ARKENIUM_TALUTON = createKey("arkenium_taluton");
    public static final ResourceKey<BestiaryEntry> GRAVITITE_TALUTON = createKey("gravitite_taluton");
    public static final ResourceKey<BestiaryEntry> MIMIC = createKey("mimic");
    public static final ResourceKey<BestiaryEntry> DETONATION_SENTRY = createKey("detonation_sentry");
    public static final ResourceKey<BestiaryEntry> SENTRY_GOLEM = createKey("sentry_golem");
    public static final ResourceKey<BestiaryEntry> SLIDER = createKey("slider");
    public static final ResourceKey<BestiaryEntry> BLADESHROOM_HUNTER = createKey("bladeshroom_hunter");

    public static final List<Holder<EntityType<?>>> ENTRY_ORDER = List.of(
            entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE), entity(AetherIIEntityTypes.MAGNETIC_TAEGORE), entity(AetherIIEntityTypes.ARCTIC_TAEGORE),
            entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID), entity(AetherIIEntityTypes.MAGNETIC_KIRRID), entity(AetherIIEntityTypes.ARCTIC_KIRRID),
            entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI), entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI), entity(AetherIIEntityTypes.ARCTIC_BURRUKAI),
            entity(AetherIIEntityTypes.PHYG), entity(AetherIIEntityTypes.SHEEPUFF), entity(AetherIIEntityTypes.FLYING_COW), entity(AetherIIEntityTypes.AERBUNNY), entity(AetherIIEntityTypes.PRISMALLARD),
            entity(AetherIIEntityTypes.SKYROOT_LIZARD), entity(AetherIIEntityTypes.GLITTERWING), entity(AetherIIEntityTypes.SHROUDWING),
            entity(AetherIIEntityTypes.MOA),
            entity(AetherIIEntityTypes.BLUE_SWET), entity(AetherIIEntityTypes.GOLDEN_SWET), entity(AetherIIEntityTypes.AECHOR_PLANT), entity(AetherIIEntityTypes.CARRION_SPROUT),
            entity(AetherIIEntityTypes.SKEPHID), entity(AetherIIEntityTypes.ZEPHYR),
            entity(AetherIIEntityTypes.TEMPEST), entity(AetherIIEntityTypes.COCKATRICE),
            entity(AetherIIEntityTypes.ARKENIUM_TALUTON), entity(AetherIIEntityTypes.GRAVITITE_TALUTON),
            entity(AetherIIEntityTypes.MIMIC), entity(AetherIIEntityTypes.DETONATION_SENTRY), entity(AetherIIEntityTypes.SENTRY_GOLEM), entity(AetherIIEntityTypes.SLIDER)
    );

    public static final Map<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> ENTITIES = Map.ofEntries(
            Map.entry(FLYING_COW, entity(AetherIIEntityTypes.FLYING_COW)),
            Map.entry(SHEEPUFF, entity(AetherIIEntityTypes.SHEEPUFF)),
            Map.entry(PHYG, entity(AetherIIEntityTypes.PHYG)),
            Map.entry(AERBUNNY, entity(AetherIIEntityTypes.AERBUNNY)),
            Map.entry(HIGHFIELDS_TAEGORE, entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE)),
            Map.entry(MAGNETIC_TAEGORE, entity(AetherIIEntityTypes.MAGNETIC_TAEGORE)),
            Map.entry(ARCTIC_TAEGORE, entity(AetherIIEntityTypes.ARCTIC_TAEGORE)),
            Map.entry(HIGHFIELDS_BURRUKAI, entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI)),
            Map.entry(MAGNETIC_BURRUKAI, entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI)),
            Map.entry(ARCTIC_BURRUKAI, entity(AetherIIEntityTypes.ARCTIC_BURRUKAI)),
            Map.entry(HIGHFIELDS_KIRRID, entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID)),
            Map.entry(MAGNETIC_KIRRID, entity(AetherIIEntityTypes.MAGNETIC_KIRRID)),
            Map.entry(ARCTIC_KIRRID, entity(AetherIIEntityTypes.ARCTIC_KIRRID)),
            Map.entry(PRISMALLARD, entity(AetherIIEntityTypes.PRISMALLARD)),
            Map.entry(MOA, entity(AetherIIEntityTypes.MOA)),
            Map.entry(SKYROOT_LIZARD, entity(AetherIIEntityTypes.SKYROOT_LIZARD)),
            Map.entry(GLITTERWING, entity(AetherIIEntityTypes.GLITTERWING)),
            Map.entry(SHROUDWING, entity(AetherIIEntityTypes.SHROUDWING)),
            Map.entry(AECHOR_PLANT, entity(AetherIIEntityTypes.AECHOR_PLANT)),
            Map.entry(CARRION_SPROUT, entity(AetherIIEntityTypes.CARRION_SPROUT)),
            Map.entry(ZEPHYR, entity(AetherIIEntityTypes.ZEPHYR)),
            Map.entry(BLUE_SWET, entity(AetherIIEntityTypes.BLUE_SWET)),
            Map.entry(GOLDEN_SWET, entity(AetherIIEntityTypes.GOLDEN_SWET)),
            Map.entry(SKEPHID, entity(AetherIIEntityTypes.SKEPHID)),
            Map.entry(TEMPEST, entity(AetherIIEntityTypes.TEMPEST)),
            Map.entry(COCKATRICE, entity(AetherIIEntityTypes.COCKATRICE)),
            Map.entry(ARKENIUM_TALUTON, entity(AetherIIEntityTypes.ARKENIUM_TALUTON)),
            Map.entry(GRAVITITE_TALUTON, entity(AetherIIEntityTypes.GRAVITITE_TALUTON)),
            Map.entry(MIMIC, entity(AetherIIEntityTypes.MIMIC)),
            Map.entry(DETONATION_SENTRY, entity(AetherIIEntityTypes.DETONATION_SENTRY)),
            Map.entry(SENTRY_GOLEM, entity(AetherIIEntityTypes.SENTRY_GOLEM)),
            Map.entry(SLIDER, entity(AetherIIEntityTypes.SLIDER))
//            Map.entry(BLADESHROOM_HUNTER, entity(AetherIIEntityTypes.BLADESHROOM_HUNTER))
    );
    public static final List<Holder<EntityType<?>>> NAMED = List.of(
            entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE),
            entity(AetherIIEntityTypes.MAGNETIC_TAEGORE),
            entity(AetherIIEntityTypes.ARCTIC_TAEGORE),
            entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI),
            entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI),
            entity(AetherIIEntityTypes.ARCTIC_BURRUKAI),
            entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID),
            entity(AetherIIEntityTypes.MAGNETIC_KIRRID),
            entity(AetherIIEntityTypes.ARCTIC_KIRRID)
    );
    public static final Map<Holder<EntityType<?>>, Double> SCALED = Map.ofEntries(
            Map.entry(entity(AetherIIEntityTypes.ZEPHYR), 1.0)
    );
    public static final Map<Holder<EntityType<?>>, ImmutableMap<Holder<Attribute>, Double>> ATTRIBUTES = Map.ofEntries(
            Map.entry(entity(AetherIIEntityTypes.AERBUNNY), AetherIIStats.AERBUNNY),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE), AetherIIStats.HIGHFIELDS_TAEGORE),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_TAEGORE), AetherIIStats.MAGNETIC_TAEGORE),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_TAEGORE), AetherIIStats.ARCTIC_TAEGORE),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI), AetherIIStats.HIGHFIELDS_BURRUKAI),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI), AetherIIStats.MAGNETIC_BURRUKAI),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_BURRUKAI), AetherIIStats.ARCTIC_BURRUKAI),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID), AetherIIStats.HIGHFIELDS_KIRRID),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_KIRRID), AetherIIStats.MAGNETIC_KIRRID),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_KIRRID), AetherIIStats.ARCTIC_KIRRID),
            Map.entry(entity(AetherIIEntityTypes.MOA), AetherIIStats.MOA),
            Map.entry(entity(AetherIIEntityTypes.PRISMALLARD), AetherIIStats.PRISMALLARD),
            Map.entry(entity(AetherIIEntityTypes.SKYROOT_LIZARD), AetherIIStats.SKYROOT_LIZARD),
            Map.entry(entity(AetherIIEntityTypes.GLITTERWING), AetherIIStats.GLITTERWING),
            Map.entry(entity(AetherIIEntityTypes.SHROUDWING), AetherIIStats.SHROUDWING),
            Map.entry(entity(AetherIIEntityTypes.AECHOR_PLANT), AetherIIStats.AECHOR_PLANT),
            Map.entry(entity(AetherIIEntityTypes.CARRION_SPROUT), AetherIIStats.CARRION_SPROUT),
            Map.entry(entity(AetherIIEntityTypes.ZEPHYR), AetherIIStats.ZEPHYR),
            Map.entry(entity(AetherIIEntityTypes.TEMPEST), AetherIIStats.TEMPEST),
            Map.entry(entity(AetherIIEntityTypes.COCKATRICE), AetherIIStats.COCKATRICE),
            Map.entry(entity(AetherIIEntityTypes.BLUE_SWET), AetherIIStats.SWET),
            Map.entry(entity(AetherIIEntityTypes.GOLDEN_SWET), AetherIIStats.SWET),
            Map.entry(entity(AetherIIEntityTypes.SKEPHID), AetherIIStats.SKEPHID),
            Map.entry(entity(AetherIIEntityTypes.ARKENIUM_TALUTON), AetherIIStats.ARKENIUM_TALUTON),
            Map.entry(entity(AetherIIEntityTypes.GRAVITITE_TALUTON), AetherIIStats.GRAVITITE_TALUTON),
            Map.entry(entity(AetherIIEntityTypes.MIMIC), AetherIIStats.MIMIC),
            Map.entry(entity(AetherIIEntityTypes.DETONATION_SENTRY), AetherIIStats.DETONATION_SENTRY),
            Map.entry(entity(AetherIIEntityTypes.SENTRY_GOLEM), AetherIIStats.SENTRY_GOLEM),
            Map.entry(entity(AetherIIEntityTypes.SLIDER), AetherIIStats.SLIDER)
//            Map.entry(entity(AetherIIEntityTypes.BLADESHROOM_HUNTER), AetherIIStats.BLADESHROOM_HUNTER)
    );
    public static final Map<Holder<EntityType<?>>, List<BestiaryEntry.LootDisplay>> LOOT = Map.ofEntries(
            Map.entry(entity(AetherIIEntityTypes.FLYING_COW), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 2))),
            Map.entry(entity(AetherIIEntityTypes.SHEEPUFF), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 2), BestiaryEntry.LootDisplay.block(AetherIIBlocks.WHITE_CLOUDWOOL, 1.0, 1, 1))),
            Map.entry(entity(AetherIIEntityTypes.PHYG), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 2))),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_TAEGORE), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_TAEGORE), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.RAW_TAEGORE_MEAT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 0, 2), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PLATE, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 0, 2), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PLATE, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_BURRUKAI), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_RIB_CUT, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.BEAST_PELT, 1.0, 0, 2), BestiaryEntry.LootDisplay.item(AetherIIItems.BURRUKAI_PLATE, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_KIRRID), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_KIRRID), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.KIRRID_LOIN, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CLOUDWOOL, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.MOA), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.MOA_FEATHER, 1.0, 0, 2))),
            Map.entry(entity(AetherIIEntityTypes.PRISMALLARD), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.PRISMALLARD_LEG, 1.0, 1, 2), BestiaryEntry.LootDisplay.item(AetherIIItems.PRISMALLARD_FEATHER, 1.0, 1, 2))),
            Map.entry(entity(AetherIIEntityTypes.AECHOR_PLANT), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.AECHOR_PETAL, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.AECHOR_CUTTING, 1.0, 0, 1))),
            Map.entry(entity(AetherIIEntityTypes.CARRION_SPROUT), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.WYNDBERRY, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.CARRION_CUTTING, 1.0, 0, 1))),
            Map.entry(entity(AetherIIEntityTypes.ZEPHYR), List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.COLD_AERCLOUD, 1.0, 1, 3), BestiaryEntry.LootDisplay.block(AetherIIBlocks.BLUE_AERCLOUD, 0.1111, 1, 2))), //, BestiaryEntry.LootDisplay.item(AetherIIItems.ZEPHYR_HUSK, 0.075, 1, 1)
            Map.entry(entity(AetherIIEntityTypes.BLUE_SWET), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.SWET_GEL, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.SWET_SUGAR, 1.0, 0, 1))),
            Map.entry(entity(AetherIIEntityTypes.GOLDEN_SWET), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.SWET_GEL, 1.0, 1, 3), BestiaryEntry.LootDisplay.item(AetherIIItems.SWET_SUGAR, 1.0, 2, 3))),
            Map.entry(entity(AetherIIEntityTypes.SKEPHID), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.CLOUDTWINE, 1.0, 1, 2))),
            Map.entry(entity(AetherIIEntityTypes.TEMPEST), List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.STORM_AERCLOUD, 1.0, 1, 3))), //, BestiaryEntry.LootDisplay.item(AetherIIItems.CHARGE_CATALYST, 0.075, 1, 1)
            Map.entry(entity(AetherIIEntityTypes.COCKATRICE), List.of(BestiaryEntry.LootDisplay.item(AetherIIItems.COCKATRICE_FEATHER, 1.0, 1, 3))),
            Map.entry(entity(AetherIIEntityTypes.ARKENIUM_TALUTON), List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.HOLYSTONE, 1.0, 0, 2))), //, BestiaryEntry.LootDisplay.item(AetherIIItems.ARKENIUM_CORE, 0.075, 1, 1)
            Map.entry(entity(AetherIIEntityTypes.GRAVITITE_TALUTON), List.of(BestiaryEntry.LootDisplay.block(AetherIIBlocks.HOLYSTONE, 1.0, 0, 2))), //, BestiaryEntry.LootDisplay.item(AetherIIItems.GRAVITITE_CORE, 0.075, 1, 1)
            Map.entry(entity(AetherIIEntityTypes.MIMIC), List.of()), //BestiaryEntry.LootDisplay.item(AetherIIItems.EYE_OF_THE_MIMIC, 0.075, 1, 1)
            Map.entry(entity(AetherIIEntityTypes.DETONATION_SENTRY), List.of()),
            Map.entry(entity(AetherIIEntityTypes.SENTRY_GOLEM), List.of()),
            Map.entry(entity(AetherIIEntityTypes.SLIDER), List.of())
//            Map.entry(entity(AetherIIEntityTypes.BLADESHROOM_HUNTER), List.of())
    );
    public static final Map<Holder<EntityType<?>>, TagKey<Item>> FED = Map.ofEntries(
            Map.entry(entity(AetherIIEntityTypes.FLYING_COW), AetherIITags.Items.FLYING_COW_FOOD),
            Map.entry(entity(AetherIIEntityTypes.SHEEPUFF), AetherIITags.Items.SHEEPUFF_FOOD),
            Map.entry(entity(AetherIIEntityTypes.PHYG), AetherIITags.Items.PHYG_FOOD),
            Map.entry(entity(AetherIIEntityTypes.AERBUNNY), AetherIITags.Items.AERBUNNY_FOOD),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_TAEGORE), AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_TAEGORE), AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_TAEGORE), AetherIITags.Items.TAEGORE_FOOD),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI), AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_BURRUKAI), AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_BURRUKAI), AetherIITags.Items.BURRUKAI_FOOD),
            Map.entry(entity(AetherIIEntityTypes.HIGHFIELDS_KIRRID), AetherIITags.Items.KIRRID_FOOD),
            Map.entry(entity(AetherIIEntityTypes.MAGNETIC_KIRRID), AetherIITags.Items.KIRRID_FOOD),
            Map.entry(entity(AetherIIEntityTypes.ARCTIC_KIRRID), AetherIITags.Items.KIRRID_FOOD),
            Map.entry(entity(AetherIIEntityTypes.MOA), AetherIITags.Items.MOA_FOOD),
            Map.entry(entity(AetherIIEntityTypes.PRISMALLARD), AetherIITags.Items.PRISMALLARD_FOOD)
    );

    private static ResourceKey<BestiaryEntry> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.BESTIARY_ENTRY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<BestiaryEntry> context) {
        for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : ENTITIES.entrySet()) {
            Holder<EntityType<?>> holder = entry.getValue();
            EntityType<?> entity = holder.value();
            String name = NAMED.contains(holder) ? "aether_ii.guidebook_bestiary.name.entity.aether_ii." + entity.toShortString() : entity.getDescriptionId();
            String slotName = NAMED.contains(holder) ? "aether_ii.guidebook_bestiary.slot_name.entity.aether_ii." + entity.toShortString() : entity.getDescriptionId();
            Optional<String> slotSubtitle = NAMED.contains(holder) ? Optional.of("aether_ii.guidebook_bestiary.slot_subtitle.entity.aether_ii." + entity.toShortString()) : Optional.empty();
            double health = getAttributeValue(holder, Attributes.MAX_HEALTH);
            double slashDefense = getAttributeValue(holder, AetherIIAttributes.SLASH_RESISTANCE);
            double impactDefense = getAttributeValue(holder, AetherIIAttributes.IMPACT_RESISTANCE);
            double pierceDefense = getAttributeValue(holder, AetherIIAttributes.PIERCE_RESISTANCE);
            List<BestiaryEntry.EffectResistanceDisplay> effectResistances = getEffectResistances(holder);
            Optional<Double> scaleMultiplier = SCALED.containsKey(holder) ? Optional.of(SCALED.get(holder)) : Optional.empty();
            List<BestiaryEntry.LootDisplay> loot = LOOT.containsKey(holder) ? LOOT.get(holder) : new ArrayList<>();
            Optional<TagKey<Item>> food = FED.containsKey(holder) ? Optional.of(FED.get(holder)) : Optional.empty();

            context.register(entry.getKey(), new BestiaryEntry(
                    entry.getKey().location(),
                    new ResourceLocation(AetherII.MODID, "guidebook/bestiary/" + entity.toShortString()),
                    name,
                    slotName,
                    slotSubtitle,
                    "aether_ii.guidebook_bestiary.description.entity.aether_ii." + entity.toShortString(),
                    holder,
                    (int) health,
                    (int) slashDefense,
                    (int) impactDefense,
                    (int) pierceDefense,
                    effectResistances,
                    scaleMultiplier,
                    loot,
                    food
            ));
        }
    }

    public static Registry<BestiaryEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.BESTIARY_ENTRY);
    }

    public static List<BestiaryEntry.EffectResistanceDisplay> getEffectResistances(Holder<EntityType<?>> holder) {
        ArrayList<BestiaryEntry.EffectResistanceDisplay> effectResistances = new ArrayList<>();
        if (ATTRIBUTES.containsKey(holder)) {
            for (Map.Entry<Holder<Attribute>, Double> attribute : ATTRIBUTES.get(holder).entrySet()) {
                if (attribute.getKey().value() instanceof EffectResistanceAttribute) {
                    effectResistances.add(new BestiaryEntry.EffectResistanceDisplay(serializableAttributeHolder(attribute.getKey()), attribute.getValue().intValue()));
                }
            }
        }
        return effectResistances;
    }

    private static double getAttributeValue(Holder<EntityType<?>> holder, Attribute attribute) {
        if (ATTRIBUTES.containsKey(holder)) {
            for (Map.Entry<Holder<Attribute>, Double> attributeEntry : ATTRIBUTES.get(holder).entrySet()) {
                if (attributeEntry.getKey().value() == attribute) {
                    return attributeEntry.getValue();
                }
            }
        }
        return 0.0;
    }

    private static double getAttributeValue(Holder<EntityType<?>> holder, RegistryObject<Attribute> attribute) {
        return getAttributeValue(holder, attribute.get());
    }

    private static Holder<Attribute> serializableAttributeHolder(Holder<Attribute> attribute) {
        if (attribute.unwrapKey().isPresent()) {
            return attribute;
        }
        for (RegistryObject<Attribute> registryObject : AetherIIAttributes.ATTRIBUTES.getEntries()) {
            if (registryObject.get() == attribute.value()) {
                ResourceKey<Attribute> key = ResourceKey.create(Registries.ATTRIBUTE, registryObject.getId());
                return BuiltInRegistries.ATTRIBUTE.getHolder(key).map(holder -> (Holder<Attribute>) holder).orElse(attribute);
            }
        }
        return BuiltInRegistries.ATTRIBUTE.getResourceKey(attribute.value())
                .flatMap(BuiltInRegistries.ATTRIBUTE::getHolder)
                .map(holder -> (Holder<Attribute>) holder)
                .orElse(attribute);
    }

    public static Map<EntityType<?>, TagKey<Item>> getFedEntityTypes() {
         return AetherIIBestiaryEntries.FED.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().value(), Map.Entry::getValue));
    }
}
