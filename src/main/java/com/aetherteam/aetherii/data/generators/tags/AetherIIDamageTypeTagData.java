package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIDamageTypeTagData extends TagsProvider<DamageType> {
    public AetherIIDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, registries, AetherII.MODID, existingFileHelper);
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
        this.tag(minecraftDamageTypeTag("no_knockback")).add(
                AetherIIDamageTypes.PLAYER_AOE_NO_KNOCKBACK,
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.FRACTURE,
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM,
                AetherIIDamageTypes.IMMOLATION,
                AetherIIDamageTypes.ALKAHEST,
                AetherIIDamageTypes.CARRION_SPROUT
        );
        this.tag(minecraftDamageTypeTag("is_player_attack")).add(
                AetherIIDamageTypes.PLAYER_AOE,
                AetherIIDamageTypes.PLAYER_AOE_NO_KNOCKBACK
        );
        this.tag(minecraftDamageTypeTag("panic_causes")).add(
                AetherIIDamageTypes.WOUND,
                AetherIIDamageTypes.TOXIN,
                AetherIIDamageTypes.VENOM,
                AetherIIDamageTypes.CHARGED,
                AetherIIDamageTypes.IMMOLATION,
                AetherIIDamageTypes.SHOCK,
                AetherIIDamageTypes.CRUSH
        );
        this.tag(minecraftDamageTypeTag("panic_environmental_causes")).add(
                AetherIIDamageTypes.ALKAHEST
        );

    }

    private static TagKey<DamageType> minecraftDamageTypeTag(String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(path));
    }
}
