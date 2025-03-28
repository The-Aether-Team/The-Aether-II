package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIEffectsEntries {
    public static final ResourceKey<Registry<EffectsEntry>> EFFECTS_ENTRY_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effects_entry"));

    public static final ResourceKey<EffectsEntry> WOUND = createKey("wound");
    public static final ResourceKey<EffectsEntry> STUN = createKey("stun");
    public static final ResourceKey<EffectsEntry> FRACTURE = createKey("fracture");
    public static final ResourceKey<EffectsEntry> AMBROSIUM_POISONING = createKey("ambrosium_poisoning");
    public static final ResourceKey<EffectsEntry> TOXIN = createKey("toxin");
    public static final ResourceKey<EffectsEntry> VENOM = createKey("venom");
    public static final ResourceKey<EffectsEntry> CHARGED = createKey("charged");
    public static final ResourceKey<EffectsEntry> WEBBED = createKey("webbed");
    public static final ResourceKey<EffectsEntry> IMMOLATION = createKey("immolation");
    public static final ResourceKey<EffectsEntry> FROSTBITE = createKey("frostbite");
    public static final ResourceKey<EffectsEntry> FUNGAL_ROT = createKey("fungal_rot");
    public static final ResourceKey<EffectsEntry> CRYSTALLIZED = createKey("crystallized");
    public static final ResourceKey<EffectsEntry> SATURATION_BOOST = createKey("saturation_boost");

    public static final Map<ResourceKey<EffectsEntry>, Holder<MobEffect>> EFFECTS = Map.ofEntries(
            Map.entry(WOUND, AetherIIEffects.WOUND),
            Map.entry(STUN, AetherIIEffects.STUN),
            Map.entry(FRACTURE, AetherIIEffects.FRACTURE),
            Map.entry(AMBROSIUM_POISONING, AetherIIEffects.AMBROSIUM_POISONING),
            Map.entry(TOXIN, AetherIIEffects.TOXIN),
            Map.entry(VENOM, AetherIIEffects.VENOM),
            Map.entry(CHARGED, AetherIIEffects.CHARGED),
            Map.entry(WEBBED, AetherIIEffects.WEBBED),
            Map.entry(IMMOLATION, AetherIIEffects.IMMOLATION),
            Map.entry(FROSTBITE, AetherIIEffects.FROSTBITE),
            Map.entry(FUNGAL_ROT, AetherIIEffects.FUNGAL_ROT),
            Map.entry(CRYSTALLIZED, AetherIIEffects.CRYSTALLIZED),
            Map.entry(SATURATION_BOOST, AetherIIEffects.SATURATION_BOOST)
    );

    public static final Map<Holder<MobEffect>, List<Holder<Item>>> ITEMS = Map.ofEntries(
            Map.entry(AetherIIEffects.WOUND, List.of(AetherIIItems.BANDAGE)),
            Map.entry(AetherIIEffects.STUN, List.of(AetherIIItems.BANDAGE)),
            Map.entry(AetherIIEffects.FRACTURE, List.of(AetherIIItems.BANDAGE, AetherIIItems.SPLINT)),
            Map.entry(AetherIIEffects.AMBROSIUM_POISONING, List.of()),
            Map.entry(AetherIIEffects.TOXIN, List.of(AetherIIItems.ANTITOXIN_VIAL)),
            Map.entry(AetherIIEffects.VENOM, List.of(AetherIIItems.ANTIVENOM_VIAL)),
            Map.entry(AetherIIEffects.CHARGED, List.of()),
            Map.entry(AetherIIEffects.WEBBED, List.of()),
            Map.entry(AetherIIEffects.IMMOLATION, List.of()),
            Map.entry(AetherIIEffects.FROSTBITE, List.of()),
            Map.entry(AetherIIEffects.FUNGAL_ROT, List.of()),
            Map.entry(AetherIIEffects.CRYSTALLIZED, List.of()),
            Map.entry(AetherIIEffects.SATURATION_BOOST, List.of())
    );

    private static ResourceKey<EffectsEntry> createKey(String name) {
        return ResourceKey.create(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<EffectsEntry> context) {
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : EFFECTS.entrySet()) {
            Holder<MobEffect> holder = entry.getValue();
            context.register(entry.getKey(), new EffectsEntry(
                    entry.getKey().location(),
                    ResourceLocation.parse(holder.getKey().location().getPath()),
                    holder.value().getDescriptionId(),
                    holder.value().getDescriptionId(),
                    Optional.empty(),
                    "aether_ii.guidebook_effects.description.effect.aether_ii." + holder.getKey().location().getPath(),
                    holder,
                    ITEMS.get(holder)
            ));
        }
    }

    public static Registry<EffectsEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);
    }
}
