package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.RecipePrioritiesProvider;

import java.util.concurrent.CompletableFuture;

public class AetherRecipePrioritiesData extends RecipePrioritiesProvider {
    public AetherRecipePrioritiesData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
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

        this.add("skyroot_crafting_table", 5);
        this.add("skyroot_chest", 5);
        this.add("skyroot_ladder", 5);
    }
}
