package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIMobEffectTagData extends TagsProvider<MobEffect> {
    public AetherIIMobEffectTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.MOB_EFFECT, registries, AetherII.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.MobEffects.MILK_DOESNT_CLEAR).add(
                AetherIIEffects.WOUND.getKey(),
                AetherIIEffects.STUN.getKey(),
                AetherIIEffects.FRACTURE.getKey(),
                AetherIIEffects.AMBROSIUM_POISONING.getKey(),
                AetherIIEffects.CHARGED.getKey(),
                AetherIIEffects.WEBBED.getKey(),
                AetherIIEffects.IMMOLATION.getKey(),
                AetherIIEffects.FROSTBITE.getKey(),
                AetherIIEffects.FUNGAL_ROT.getKey(),
                AetherIIEffects.CRYSTALLIZED.getKey());
    }
}
