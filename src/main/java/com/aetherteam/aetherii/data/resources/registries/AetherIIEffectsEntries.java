package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.aetherteam.aetherii.util.RegistryObjectUtil.effect;
import static com.aetherteam.aetherii.util.RegistryObjectUtil.item;

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
            effect(AetherIIMobEffects.VULNERABILITY),
            effect(AetherIIMobEffects.WOUND),
            effect(AetherIIMobEffects.STUN),
            effect(AetherIIMobEffects.FRACTURE),
            effect(AetherIIMobEffects.AMBROSIUM_POISONING),
            effect(AetherIIMobEffects.TOXIN),
            effect(AetherIIMobEffects.VENOM),
            effect(AetherIIMobEffects.CHARGED),
            effect(AetherIIMobEffects.WEBBED),
            effect(AetherIIMobEffects.IMMOLATION),
            effect(AetherIIMobEffects.FROSTBITE),
            effect(AetherIIMobEffects.FUNGAL_ROT),
            effect(AetherIIMobEffects.CRYSTALLIZED),
            effect(AetherIIMobEffects.SATURATION_BOOST)
    );

    public static final Map<ResourceKey<EffectsEntry>, Holder<MobEffect>> EFFECTS = Map.ofEntries(
            Map.entry(VULNERABILITY, effect(AetherIIMobEffects.VULNERABILITY)),
            Map.entry(WOUND, effect(AetherIIMobEffects.WOUND)),
            Map.entry(STUN, effect(AetherIIMobEffects.STUN)),
            Map.entry(FRACTURE, effect(AetherIIMobEffects.FRACTURE)),
            Map.entry(AMBROSIUM_POISONING, effect(AetherIIMobEffects.AMBROSIUM_POISONING)),
            Map.entry(TOXIN, effect(AetherIIMobEffects.TOXIN)),
            Map.entry(VENOM, effect(AetherIIMobEffects.VENOM)),
            Map.entry(CHARGED, effect(AetherIIMobEffects.CHARGED)),
            Map.entry(WEBBED, effect(AetherIIMobEffects.WEBBED)),
            Map.entry(IMMOLATION, effect(AetherIIMobEffects.IMMOLATION)),
            Map.entry(FROSTBITE, effect(AetherIIMobEffects.FROSTBITE)),
            Map.entry(FUNGAL_ROT, effect(AetherIIMobEffects.FUNGAL_ROT)),
            Map.entry(CRYSTALLIZED, effect(AetherIIMobEffects.CRYSTALLIZED)),
            Map.entry(SATURATION_BOOST, effect(AetherIIMobEffects.SATURATION_BOOST))
    );

    public static final Map<Holder<MobEffect>, List<Holder<Item>>> ITEMS = Map.ofEntries(
            Map.entry(effect(AetherIIMobEffects.VULNERABILITY), List.of()),
            Map.entry(effect(AetherIIMobEffects.WOUND), List.of(item(AetherIIItems.BANDAGE))),
            Map.entry(effect(AetherIIMobEffects.STUN), List.of(item(AetherIIItems.BANDAGE))),
            Map.entry(effect(AetherIIMobEffects.FRACTURE), List.of(item(AetherIIItems.BANDAGE), item(AetherIIItems.SPLINT))),
            Map.entry(effect(AetherIIMobEffects.AMBROSIUM_POISONING), List.of()),
            Map.entry(effect(AetherIIMobEffects.TOXIN), List.of(item(AetherIIItems.ANTITOXIN_VIAL))),
            Map.entry(effect(AetherIIMobEffects.VENOM), List.of(item(AetherIIItems.ANTIVENOM_VIAL))),
            Map.entry(effect(AetherIIMobEffects.CHARGED), List.of()),
            Map.entry(effect(AetherIIMobEffects.WEBBED), List.of()),
            Map.entry(effect(AetherIIMobEffects.IMMOLATION), List.of()),
            Map.entry(effect(AetherIIMobEffects.FROSTBITE), List.of()),
            Map.entry(effect(AetherIIMobEffects.FUNGAL_ROT), List.of()),
            Map.entry(effect(AetherIIMobEffects.CRYSTALLIZED), List.of()),
            Map.entry(effect(AetherIIMobEffects.SATURATION_BOOST), List.of())
    );

    private static ResourceKey<EffectsEntry> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.EFFECTS_ENTRY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<EffectsEntry> context) {
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : EFFECTS.entrySet()) {
            Holder<MobEffect> holder = entry.getValue();
            ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(holder.value());
            context.register(entry.getKey(), new EffectsEntry(
                    entry.getKey().location(),
                    new ResourceLocation(id.getPath()),
                    holder.value().getDescriptionId(),
                    holder.value().getDescriptionId(),
                    Optional.empty(),
                    "aether_ii.guidebook_effects.description.effect.aether_ii." + id.getPath(),
                    holder,
                    ITEMS.get(holder)
            ));
        }
    }

    public static Registry<EffectsEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.EFFECTS_ENTRY);
    }
}
