package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

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

    private static ResourceKey<BestiaryEntry> createKey(String name) {
        return ResourceKey.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<BestiaryEntry> context) {
        context.register(FLYING_COW, new BestiaryEntry(AetherIIEntityTypes.FLYING_COW, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(SHEEPUFF, new BestiaryEntry(AetherIIEntityTypes.SHEEPUFF, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(PHYG, new BestiaryEntry(AetherIIEntityTypes.PHYG, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(AERBUNNY, new BestiaryEntry(AetherIIEntityTypes.AERBUNNY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(HIGHFIELDS_TAEGORE, new BestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(MAGNETIC_TAEGORE, new BestiaryEntry(AetherIIEntityTypes.MAGNETIC_TAEGORE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(ARCTIC_TAEGORE, new BestiaryEntry(AetherIIEntityTypes.ARCTIC_TAEGORE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(HIGHFIELDS_BURRUKAI, new BestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(MAGNETIC_BURRUKAI, new BestiaryEntry(AetherIIEntityTypes.MAGNETIC_BURRUKAI, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(ARCTIC_BURRUKAI, new BestiaryEntry(AetherIIEntityTypes.ARCTIC_BURRUKAI, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(HIGHFIELDS_KIRRID, new BestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_KIRRID, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(MAGNETIC_KIRRID, new BestiaryEntry(AetherIIEntityTypes.MAGNETIC_KIRRID, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(ARCTIC_KIRRID, new BestiaryEntry(AetherIIEntityTypes.ARCTIC_KIRRID, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(MOA, new BestiaryEntry(AetherIIEntityTypes.MOA, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(SKYROOT_LIZARD, new BestiaryEntry(AetherIIEntityTypes.SKYROOT_LIZARD, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(AECHOR_PLANT, new BestiaryEntry(AetherIIEntityTypes.AECHOR_PLANT, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(ZEPHYR, new BestiaryEntry(AetherIIEntityTypes.ZEPHYR, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(TEMPEST, new BestiaryEntry(AetherIIEntityTypes.TEMPEST, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
        context.register(COCKATRICE, new BestiaryEntry(AetherIIEntityTypes.COCKATRICE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/entry_undiscovered")));
    }

    public static Registry<BestiaryEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
    }
}
