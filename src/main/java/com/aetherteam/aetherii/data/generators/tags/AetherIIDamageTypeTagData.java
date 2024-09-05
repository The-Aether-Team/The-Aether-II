package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIDamageTypeTagData extends TagsProvider<DamageType> {
    public AetherIIDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, registries, AetherII.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.DamageTypes.TYPED).add(
                DamageTypes.PLAYER_ATTACK,
                DamageTypes.ARROW,
                DamageTypes.TRIDENT,
                DamageTypes.THROWN);

        this.tag(DamageTypeTags.NO_KNOCKBACK).add(AetherIIDamageTypes.PLAYER_AOE_NO_KNOCKBACK, AetherIIDamageTypes.WOUND, AetherIIDamageTypes.FRACTURE, AetherIIDamageTypes.TOXIN, AetherIIDamageTypes.VENOM);
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(AetherIIDamageTypes.WOUND, AetherIIDamageTypes.FRACTURE, AetherIIDamageTypes.TOXIN, AetherIIDamageTypes.VENOM);
        this.tag(DamageTypeTags.IS_LIGHTNING).add(AetherIIDamageTypes.SHOCK);
    }
}