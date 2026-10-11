package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIEffectsEntries {
    public static final ResourceKey<EffectsEntry> VULNERABILITY = createKey("vulnerability");
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

    public static final List<Holder<MobEffect>> ENTRY_ORDER = List.of(
            AetherIIMobEffects.VULNERABILITY,
            AetherIIMobEffects.WOUND,
            AetherIIMobEffects.STUN,
            AetherIIMobEffects.FRACTURE,
            AetherIIMobEffects.AMBROSIUM_POISONING,
            AetherIIMobEffects.TOXIN,
            AetherIIMobEffects.VENOM,
            AetherIIMobEffects.CHARGED,
            AetherIIMobEffects.WEBBED,
            AetherIIMobEffects.IMMOLATION,
            AetherIIMobEffects.FROSTBITE,
            AetherIIMobEffects.FUNGAL_ROT,
            AetherIIMobEffects.CRYSTALLIZED,
            AetherIIMobEffects.SATURATION_BOOST
    );

    public static final Map<ResourceKey<EffectsEntry>, Holder<MobEffect>> EFFECTS = Map.ofEntries(
            Map.entry(VULNERABILITY, AetherIIMobEffects.VULNERABILITY),
            Map.entry(WOUND, AetherIIMobEffects.WOUND),
            Map.entry(STUN, AetherIIMobEffects.STUN),
            Map.entry(FRACTURE, AetherIIMobEffects.FRACTURE),
            Map.entry(AMBROSIUM_POISONING, AetherIIMobEffects.AMBROSIUM_POISONING),
            Map.entry(TOXIN, AetherIIMobEffects.TOXIN),
            Map.entry(VENOM, AetherIIMobEffects.VENOM),
            Map.entry(CHARGED, AetherIIMobEffects.CHARGED),
            Map.entry(WEBBED, AetherIIMobEffects.WEBBED),
            Map.entry(IMMOLATION, AetherIIMobEffects.IMMOLATION),
            Map.entry(FROSTBITE, AetherIIMobEffects.FROSTBITE),
            Map.entry(FUNGAL_ROT, AetherIIMobEffects.FUNGAL_ROT),
            Map.entry(CRYSTALLIZED, AetherIIMobEffects.CRYSTALLIZED),
            Map.entry(SATURATION_BOOST, AetherIIMobEffects.SATURATION_BOOST)
    );

    public static final Map<Holder<MobEffect>, List<Holder<Item>>> ITEMS = Map.ofEntries(
            Map.entry(AetherIIMobEffects.VULNERABILITY, List.of()),
            Map.entry(AetherIIMobEffects.WOUND, List.of(AetherIIItems.BANDAGE)),
            Map.entry(AetherIIMobEffects.STUN, List.of(AetherIIItems.BANDAGE)),
            Map.entry(AetherIIMobEffects.FRACTURE, List.of(AetherIIItems.BANDAGE, AetherIIItems.SPLINT)),
            Map.entry(AetherIIMobEffects.AMBROSIUM_POISONING, List.of()),
            Map.entry(AetherIIMobEffects.TOXIN, List.of(AetherIIItems.ANTITOXIN_VIAL)),
            Map.entry(AetherIIMobEffects.VENOM, List.of(AetherIIItems.ANTIVENOM_VIAL)),
            Map.entry(AetherIIMobEffects.CHARGED, List.of()),
            Map.entry(AetherIIMobEffects.WEBBED, List.of()),
            Map.entry(AetherIIMobEffects.IMMOLATION, List.of()),
            Map.entry(AetherIIMobEffects.FROSTBITE, List.of()),
            Map.entry(AetherIIMobEffects.FUNGAL_ROT, List.of()),
            Map.entry(AetherIIMobEffects.CRYSTALLIZED, List.of()),
            Map.entry(AetherIIMobEffects.SATURATION_BOOST, List.of())
    );

    private static ResourceKey<EffectsEntry> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.EFFECTS_ENTRY, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<EffectsEntry> context) {
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : EFFECTS.entrySet()) {
            Holder<MobEffect> holder = entry.getValue();
            context.register(entry.getKey(), new EffectsEntry(
                    entry.getKey().identifier(),
                    Identifier.parse(holder.getKey().identifier().getPath()),
                    holder.value().getDescriptionId(),
                    holder.value().getDescriptionId(),
                    Optional.empty(),
                    "aether_ii.guidebook_effects.description.effect.aether_ii." + holder.getKey().identifier().getPath(),
                    holder,
                    ITEMS.get(holder)
            ));
        }
    }

    public static Registry<EffectsEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.EFFECTS_ENTRY);
    }
}
