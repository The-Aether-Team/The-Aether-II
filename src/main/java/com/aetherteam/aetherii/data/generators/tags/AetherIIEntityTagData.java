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

import java.util.concurrent.CompletableFuture;

public class AetherIIEntityTagData extends EntityTypeTagsProvider {
    public AetherIIEntityTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AetherII.MODID);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether II
        this.tag(AetherIITags.Entities.TAEGORE).add(
                AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(),
                AetherIIEntityTypes.MAGNETIC_TAEGORE.get(),
                AetherIIEntityTypes.ARCTIC_TAEGORE.get()
        );
        this.tag(AetherIITags.Entities.BURRUKAI).add(
                AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(),
                AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(),
                AetherIIEntityTypes.ARCTIC_BURRUKAI.get()
        );
        this.tag(AetherIITags.Entities.KIRRID).add(
                AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(),
                AetherIIEntityTypes.MAGNETIC_KIRRID.get(),
                AetherIIEntityTypes.ARCTIC_KIRRID.get()
        );
        this.tag(AetherIITags.Entities.AETHER_MOBS).add(
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
                AetherIIEntityTypes.LASSO_LOOP.get(),
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get(),
                AetherIIEntityTypes.TOXIC_DART.get(),
                AetherIIEntityTypes.ZEPHYR_WEBBING_BALL.get(),
                AetherIIEntityTypes.TEMPEST_THUNDERBALL.get()
        );
        this.tag(AetherIITags.Entities.NO_DOUBLE_DROPS).addTag(Tags.EntityTypes.BOSSES).add(EntityType.PLAYER);
        this.tag(AetherIITags.Entities.NO_AMBROSIUM_DROPS).add(EntityType.PLAYER);
        this.tag(AetherIITags.Entities.ZEPHYR_BLOW_BLACKLIST).add(
                AetherIIEntityTypes.AECHOR_PLANT.get(),
                AetherIIEntityTypes.CARRION_SPROUT.get()
        );

        this.tag(AetherIITags.Entities.SENTRY_RUINS_MOBS).add(
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.SLIDER.get()
        );
        this.tag(AetherIITags.Entities.DUNGEON_MOBS).add(
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.MIMIC.get(),
                AetherIIEntityTypes.SLIDER.get()
        );

        this.tag(AetherIITags.Entities.STICKABLE_PROJECTILES).add(
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get(),
                AetherIIEntityTypes.TOXIC_DART.get(),
                AetherIIEntityTypes.VENOMOUS_DART.get()
        );
        this.tag(AetherIITags.Entities.STICKABLE_PROJECTILES_EMISSIVE).add(
                AetherIIEntityTypes.VENOMOUS_DART.get()
        );

        this.tag(AetherIITags.Entities.SPAWNING_ICE).add(
                EntityType.POLAR_BEAR
        );
        this.tag(AetherIITags.Entities.SPAWNING_AERCLOUDS).add(
                AetherIIEntityTypes.TEMPEST.get(),
                AetherIIEntityTypes.ZEPHYR.get()
        );
        this.tag(AetherIITags.Entities.SPAWNING_LEAVES).add(
                EntityType.OCELOT,
                EntityType.PARROT
        );

        // Vanilla
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                AetherIIEntityTypes.HOLYSTONE_ROCK.get(),
                AetherIIEntityTypes.ARCTIC_SNOWBALL.get(),
                AetherIIEntityTypes.SKYROOT_PINECONE.get(),
                AetherIIEntityTypes.SCATTERGLASS_BOLT.get(),
                AetherIIEntityTypes.AMBER_DART.get()
        );
        this.tag(EntityTypeTags.REDIRECTABLE_PROJECTILE).add(
                AetherIIEntityTypes.TEMPEST_THUNDERBALL.get()
        );
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).addTag(
                AetherIITags.Entities.KIRRID
        ).add(
                AetherIIEntityTypes.PHYG.get(),
                AetherIIEntityTypes.FLYING_COW.get(),
                AetherIIEntityTypes.AERBUNNY.get(),
                AetherIIEntityTypes.MOA.get(),
                AetherIIEntityTypes.AERWHALE.get(),
                AetherIIEntityTypes.SKYROOT_LIZARD.get(),
                AetherIIEntityTypes.ZEPHYR.get(),
                AetherIIEntityTypes.TEMPEST.get(),
                AetherIIEntityTypes.SKEPHID.get(),
                AetherIIEntityTypes.GRAVITITE_TALUTON.get(),
                AetherIIEntityTypes.GLITTERWING.get(),
                AetherIIEntityTypes.SHROUDWING.get()
        );

        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(
                AetherIIEntityTypes.DETONATION_SENTRY.get(),
                AetherIIEntityTypes.SENTRY_GOLEM.get(),
                AetherIIEntityTypes.SLIDER.get()
        );
    }
}
