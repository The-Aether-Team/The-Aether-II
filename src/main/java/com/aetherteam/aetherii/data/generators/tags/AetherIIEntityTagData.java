package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIEntityTagData extends EntityTypeTagsProvider {
    public AetherIIEntityTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether II
        this.tag(AetherIITags.Entities.BURRUKAI)
                .add(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get());
        this.tag(AetherIITags.Entities.KIRRID).add(
                AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(),
                AetherIIEntityTypes.MAGNETIC_KIRRID.get(),
                AetherIIEntityTypes.ARCTIC_KIRRID.get()
        );
        this.tag(AetherIITags.Entities.NO_DOUBLE_DROPS).addTag(Tags.EntityTypes.BOSSES).add(EntityType.PLAYER);
        this.tag(AetherIITags.Entities.NO_AMBROSIUM_DROPS).add(EntityType.PLAYER);
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

        // Vanilla
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), AetherIIEntityTypes.SKYROOT_PINECONE.get(), AetherIIEntityTypes.SCATTERGLASS_BOLT.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).addTag(AetherIITags.Entities.KIRRID).add(AetherIIEntityTypes.AERBUNNY.get()).add(AetherIIEntityTypes.PHYG.get()).add(AetherIIEntityTypes.ZEPHYR.get()).add(AetherIIEntityTypes.FLYING_COW.get());
    }
}
