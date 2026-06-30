package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIMobEffectTagData extends TagsProvider<MobEffect> {
    public AetherIIMobEffectTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.MOB_EFFECT, registries, AetherII.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.MobEffects.DART_EFFECTS).add(
                AetherIIMobEffects.VULNERABILITY.getKey(),
                AetherIIMobEffects.TOXIN.getKey(),
                AetherIIMobEffects.VENOM.getKey()
        );
        this.tag(AetherIITags.MobEffects.MILK_DOESNT_CLEAR).add(
                AetherIIMobEffects.VULNERABILITY.getKey(),
                AetherIIMobEffects.WOUND.getKey(),
                AetherIIMobEffects.STUN.getKey(),
                AetherIIMobEffects.FRACTURE.getKey(),
                AetherIIMobEffects.AMBROSIUM_POISONING.getKey(),
                AetherIIMobEffects.CHARGED.getKey(),
                AetherIIMobEffects.WEBBED.getKey(),
                AetherIIMobEffects.IMMOLATION.getKey(),
                AetherIIMobEffects.FROSTBITE.getKey(),
                AetherIIMobEffects.FUNGAL_ROT.getKey(),
                AetherIIMobEffects.CRYSTALLIZED.getKey(),
                AetherIIMobEffects.NATURAL_CAMOUFLAGE.getKey(),
                AetherIIMobEffects.HEALING_OVERFLOW.getKey(),
                AetherIIMobEffects.ELECTRIC_SHOCK.getKey(),
                AetherIIMobEffects.CARRION_TRAP.getKey(),
                AetherIIMobEffects.GRAVITATIONAL_PULL.getKey()
        );
    }
}
