package com.aetherteam.aetherii.api.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.SkyrootLizardVariant;
import com.aetherteam.aetherii.api.SwetVariant;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.api.styles.StyleMaterial;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIRegistries {
    public static final ResourceKey<Registry<BestiaryEntry>> BESTIARY_ENTRY = createRegistryKey("bestiary_entry");
    public static final ResourceKey<Registry<EffectsEntry>> EFFECTS_ENTRY = createRegistryKey("effects_entry");
    public static final ResourceKey<Registry<ExplorationEntry>> EXPLORATION_ENTRY = createRegistryKey("exploration_entry");
    public static final ResourceKey<Registry<StyleDesign>> STYLE_DESIGN = createRegistryKey("style_design");
    public static final ResourceKey<Registry<StyleMaterial>> STYLE_MATERIAL = createRegistryKey("style_material");
    public static final ResourceKey<Registry<SwetVariant>> SWET_VARIANT = createRegistryKey("swet_variant");
    public static final ResourceKey<Registry<SkyrootLizardVariant>> SKYROOT_LIZARD_VARIANT = createRegistryKey("skyroot_lizard_variant");
    public static final ResourceKey<Registry<RewardWrapper>> REWARD_WRAPPER = createRegistryKey("reward_wrapper");
    public static final ResourceKey<Registry<Mural>> MURAL = createRegistryKey("mural");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }
}
