package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIEntityTypeTagData extends EntityTypeTagsProvider {
    public AetherIIEntityTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, AetherII.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether II
        this.tag(AetherIITags.EntityTypes.AETHER_MOBS).add(
                AetherIIEntityTypes.FLYING_COW.get(),
                AetherIIEntityTypes.SHEEPUFF.get(),
                AetherIIEntityTypes.PHYG.get(),
                AetherIIEntityTypes.AERBUNNY.get(),
                AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(),
                AetherIIEntityTypes.MAGNETIC_TAEGORE.get(),
                AetherIIEntityTypes.ARCTIC_TAEGORE.get(),
                AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(),
                AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(),
                AetherIIEntityTypes.ARCTIC_BURRUKAI.get(),
                AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(),
                AetherIIEntityTypes.MAGNETIC_KIRRID.get(),
                AetherIIEntityTypes.ARCTIC_KIRRID.get(),
                AetherIIEntityTypes.MOA.get(),
                AetherIIEntityTypes.PRISMALLARD.get(),
                AetherIIEntityTypes.SKYROOT_LIZARD.get(),
                AetherIIEntityTypes.GLITTERWING.get(),
                AetherIIEntityTypes.SHROUDWING.get(),
                AetherIIEntityTypes.AECHOR_PLANT.get(),
                AetherIIEntityTypes.CARRION_SPROUT.get(),
                AetherIIEntityTypes.ZEPHYR.get(),
                AetherIIEntityTypes.SKEPHID.get(),
                AetherIIEntityTypes.TEMPEST.get(),
                AetherIIEntityTypes.COCKATRICE.get(),
                AetherIIEntityTypes.ARKENIUM_TALUTON.get(),
                AetherIIEntityTypes.GRAVITITE_TALUTON.get(),
                AetherIIEntityTypes.MIMIC.get(),
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.SLIDER.get(),
                AetherIIEntityTypes.DEMOLITION_PROJECTILE.get(),
                AetherIIEntityTypes.BLADESHROOM_HUNTER.get(),
                AetherIIEntityTypes.HOLYSTONE_ROCK.get(),
                AetherIIEntityTypes.ARCTIC_SNOWBALL.get(),
                AetherIIEntityTypes.SKYROOT_PINECONE.get(),
                AetherIIEntityTypes.PRISMALLARD_EGG.get(),
                AetherIIEntityTypes.LASSO_LOOP.get(),
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get(),
                AetherIIEntityTypes.TOXIC_DART.get(),
                AetherIIEntityTypes.ZEPHYR_WEBBING_BALL.get(),
                AetherIIEntityTypes.TEMPEST_THUNDERBALL.get()
        );
        this.tag(AetherIITags.EntityTypes.TAEGORE).add(
                AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(),
                AetherIIEntityTypes.MAGNETIC_TAEGORE.get(),
                AetherIIEntityTypes.ARCTIC_TAEGORE.get()
        );
        this.tag(AetherIITags.EntityTypes.BURRUKAI).add(
                AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(),
                AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(),
                AetherIIEntityTypes.ARCTIC_BURRUKAI.get()
        );
        this.tag(AetherIITags.EntityTypes.KIRRID).add(
                AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(),
                AetherIIEntityTypes.MAGNETIC_KIRRID.get(),
                AetherIIEntityTypes.ARCTIC_KIRRID.get()
        );
        this.tag(AetherIITags.EntityTypes.SWETS).add(
                AetherIIEntityTypes.BLUE_SWET.get(),
                AetherIIEntityTypes.GOLDEN_SWET.get()
        );
        this.tag(AetherIITags.EntityTypes.TALUTONS).add(
                AetherIIEntityTypes.ARKENIUM_TALUTON.get(),
                AetherIIEntityTypes.GRAVITITE_TALUTON.get()
        );
        this.tag(AetherIITags.EntityTypes.PLANT_MOBS).add(
                AetherIIEntityTypes.CARRION_SPROUT.get(),
                AetherIIEntityTypes.AECHOR_PLANT.get()
        );
        this.tag(AetherIITags.EntityTypes.SENTRY_RUINS_MOBS).add(
                AetherIIEntityTypes.MIMIC.get(),
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.SLIDER.get()
        );
        this.tag(AetherIITags.EntityTypes.DUNGEON_MOBS).addTag(
                AetherIITags.EntityTypes.SENTRY_RUINS_MOBS
        );

        this.tag(AetherIITags.EntityTypes.NO_DOUBLE_DROPS).add(
                EntityType.PLAYER
        ).addTag(
                Tags.EntityTypes.BOSSES
        );
        this.tag(AetherIITags.EntityTypes.NO_AMBROSIUM_DROPS).add(
                EntityType.PLAYER
        );
        this.tag(AetherIITags.EntityTypes.ZEPHYR_BLOW_BLACKLIST).addTag(
                AetherIITags.EntityTypes.PLANT_MOBS
        );

        this.tag(AetherIITags.EntityTypes.PLANT_DAMAGING_PROJECTILES);
        this.tag(AetherIITags.EntityTypes.SLIDER_DAMAGING_PROJECTILES);
        this.tag(AetherIITags.EntityTypes.STICKABLE_PROJECTILES).add(
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get(),
                AetherIIEntityTypes.TOXIC_DART.get(),
                AetherIIEntityTypes.VENOMOUS_DART.get()
        );
        this.tag(AetherIITags.EntityTypes.STICKABLE_PROJECTILES_EMISSIVE).add(
                AetherIIEntityTypes.VENOMOUS_DART.get()
        );

        this.tag(AetherIITags.EntityTypes.SPAWNING_ICE).add(
                EntityType.POLAR_BEAR
        );
        this.tag(AetherIITags.EntityTypes.SPAWNING_AERCLOUDS).add(
                AetherIIEntityTypes.TEMPEST.get(),
                AetherIIEntityTypes.ZEPHYR.get()
        );
        this.tag(AetherIITags.EntityTypes.SPAWNING_LEAVES).add(
                EntityType.OCELOT,
                EntityType.PARROT
        );

        // Vanilla
        this.tag(EntityTypeTags.ARROWS).add(
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get()
        );
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                AetherIIEntityTypes.HOLYSTONE_ROCK.get(),
                AetherIIEntityTypes.ARCTIC_SNOWBALL.get(),
                AetherIIEntityTypes.SKYROOT_PINECONE.get(),
                AetherIIEntityTypes.TOXIC_DART.get(),
                AetherIIEntityTypes.VENOMOUS_DART.get(),
                AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(),
                AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get()
        );
        this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(
                AetherIIEntityTypes.PHYG.get(),
                AetherIIEntityTypes.SHEEPUFF.get(),
                AetherIIEntityTypes.FLYING_COW.get(),
                AetherIIEntityTypes.AERBUNNY.get(),
                AetherIIEntityTypes.MOA.get(),
                AetherIIEntityTypes.PRISMALLARD.get()
        ).addTag(
                AetherIITags.EntityTypes.KIRRID
        );
        this.tag(minecraftEntityTypeTag("can_breathe_under_water")).add(
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.SLIDER.get()
        ).addTag(
                AetherIITags.EntityTypes.TALUTONS
        );
        this.tag(EntityTypeTags.FROG_FOOD).addTag(
                AetherIITags.EntityTypes.SWETS
        );
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(
                AetherIIEntityTypes.PHYG.get(),
                AetherIIEntityTypes.FLYING_COW.get(),
                AetherIIEntityTypes.AERBUNNY.get(),
                AetherIIEntityTypes.MOA.get(),
                AetherIIEntityTypes.PRISMALLARD.get(),
                AetherIIEntityTypes.AERWHALE.get(),
                AetherIIEntityTypes.SKYROOT_LIZARD.get(),
                AetherIIEntityTypes.ZEPHYR.get(),
                AetherIIEntityTypes.TEMPEST.get(),
                AetherIIEntityTypes.SKEPHID.get(),
                AetherIIEntityTypes.GRAVITITE_TALUTON.get(),
                AetherIIEntityTypes.GLITTERWING.get(),
                AetherIIEntityTypes.SHROUDWING.get()
        ).addTag(
                AetherIITags.EntityTypes.KIRRID
        );
        this.tag(EntityTypeTags.DISMOUNTS_UNDERWATER).add(
                AetherIIEntityTypes.PHYG.get(),
                AetherIIEntityTypes.FLYING_COW.get(),
                AetherIIEntityTypes.MOA.get()
        );
        // NeoForge
        this.tag(Tags.EntityTypes.BOSSES).add(
                AetherIIEntityTypes.SLIDER.get()
        );
    }

    private static TagKey<EntityType<?>> minecraftEntityTypeTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(path));
    }
}
