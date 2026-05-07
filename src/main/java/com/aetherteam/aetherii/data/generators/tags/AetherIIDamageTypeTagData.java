package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class AetherIIDamageTypeTagData extends KeyTagProvider<DamageType> {
    public AetherIIDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.DAMAGE_TYPE, registries, AetherII.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Aether II
        this.tag(AetherIITags.DamageTypes.TYPED).add(
                DamageTypes.PLAYER_ATTACK,
                DamageTypes.ARROW,
                DamageTypes.TRIDENT,
                DamageTypes.THROWN
        );

        // Vanilla
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.FRACTURE,
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM,
                AetherIIDamageTypes.IMMOLATION
        );
        this.tag(DamageTypeTags.BYPASSES_SHIELD).add(
                AetherIIDamageTypes.ALKAHEST,
                AetherIIDamageTypes.CARRION_SPROUT
        );
        this.tag(DamageTypeTags.IS_FIRE).add(
                AetherIIDamageTypes.IMMOLATION
        );
        this.tag(DamageTypeTags.IS_LIGHTNING).add(
                AetherIIDamageTypes.CHARGED,
                AetherIIDamageTypes.SHOCK
        );
        this.tag(DamageTypeTags.IGNITES_ARMOR_STANDS).add(
                AetherIIDamageTypes.IMMOLATION
        );
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(
                AetherIIDamageTypes.PLAYER_AOE_NO_KNOCKBACK,
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.FRACTURE,
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM,
                AetherIIDamageTypes.IMMOLATION,
                AetherIIDamageTypes.ALKAHEST,
                AetherIIDamageTypes.CARRION_SPROUT
        );
        this.tag(DamageTypeTags.IS_PLAYER_ATTACK).add(
                AetherIIDamageTypes.PLAYER_AOE,
                AetherIIDamageTypes.PLAYER_AOE_NO_KNOCKBACK
        );
        this.tag(DamageTypeTags.PANIC_CAUSES).add(
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM,
                AetherIIDamageTypes.CHARGED,
                AetherIIDamageTypes.IMMOLATION,
                AetherIIDamageTypes.SHOCK,
                AetherIIDamageTypes.CRUSH
        );
        this.tag(DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES).add(
                AetherIIDamageTypes.ALKAHEST
        );

        // NeoForge
        this.tag(Tags.DamageTypes.IS_POISON).add(
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM
        );
        this.tag(Tags.DamageTypes.IS_ENVIRONMENT).add(
                AetherIIDamageTypes.ALKAHEST
        );
        this.tag(Tags.DamageTypes.IS_PHYSICAL).add(
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.FRACTURE,
                AetherIIDamageTypes.CARRION_SPROUT,
                AetherIIDamageTypes.CRUSH
        );
    }
}