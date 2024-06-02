package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIEntityTagData extends EntityTypeTagsProvider {
    public AetherIIEntityTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Entities.SPAWNING_ICE).add(
                EntityType.POLAR_BEAR
        );
        this.tag(AetherIITags.Entities.SPAWNING_AERCLOUDS).add(
                AetherIIEntityTypes.ZEPHYR.get()
        );
        this.tag(AetherIITags.Entities.SPAWNING_LEAVES).add(
                EntityType.OCELOT,
                EntityType.PARROT
        );

        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(AetherIIEntityTypes.ARCTIC_SNOWBALL.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(AetherIIEntityTypes.AERBUNNY.get()).add(AetherIIEntityTypes.PHYG.get());
    }
}
