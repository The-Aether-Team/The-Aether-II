package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.RecipePrioritiesProvider;

import java.util.concurrent.CompletableFuture;

public class AetherIIRecipePrioritiesData extends RecipePrioritiesProvider {
    public AetherIIRecipePrioritiesData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AetherII.MODID);
    }

    @Override
    protected void start() {
        this.add("skyroot_stick", 5);
        this.add("skyroot_sword", 5);
        this.add("skyroot_pickaxe", 5);
        this.add("skyroot_axe", 5);
        this.add("skyroot_shovel", 5);
        this.add("skyroot_trowel", 5);
        this.add("skyroot_hammer", 5);
        this.add("skyroot_spear", 5);
        this.add("skyroot_crafting_table", 5);
        this.add("skyroot_chest", 5);
        this.add("skyroot_ladder", 5);

        this.add("taegore_hide_helmet", 5);
        this.add("taegore_hide_chestplate", 5);
        this.add("taegore_hide_leggings", 5);
        this.add("taegore_hide_boots", 5);
        this.add("taegore_hide_gloves", 5);
        this.add("burrukai_pelt_helmet", 5);
        this.add("burrukai_pelt_chestplate", 5);
        this.add("burrukai_pelt_leggings", 5);
        this.add("burrukai_pelt_boots", 5);
        this.add("burrukai_pelt_gloves", 5);
        this.add("hide_bundle", 5);

        this.add("cloudwool", 5);

        this.add("holystone_furnace", 5);
        this.add("holystone_pickaxe", 5);
        this.add("holystone_axe", 5);
        this.add("holystone_shovel", 5);
        this.add("holystone_trowel", 5);
        this.add("holystone_shortsword", 5);
        this.add("holystone_hammer", 5);
        this.add("holystone_spear", 5);
    }
}
