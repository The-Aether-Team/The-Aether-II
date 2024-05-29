package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.damage.DamageResistance;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

public class AetherIIDamageResistances {
    public static final ResourceKey<Registry<DamageResistance>> DAMAGE_RESISTANCE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(AetherII.MODID, "damage_resistance"));

    public static final ResourceKey<DamageResistance> EXAMPLE_1 = createKey("example_1");
    public static final ResourceKey<DamageResistance> EXAMPLE_2 = createKey("example_2");
    public static final ResourceKey<DamageResistance> EXAMPLE_3 = createKey("example_3");

    private static ResourceKey<DamageResistance> createKey(String name) {
        return ResourceKey.create(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageResistance> context) {
        context.register(EXAMPLE_1, new DamageResistance(EntityType.PIG, -2, 2, 2));
        context.register(EXAMPLE_2, new DamageResistance(EntityType.SHEEP, 2, -2, 2));
        context.register(EXAMPLE_3, new DamageResistance(EntityType.COW, 2, 2, -2));
    }

    public static boolean hasEntity(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return true;
            }
        }
        return false;
    }

    public static double getSlashDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().slashValue();
            }
        }
        return 0.0;
    }

    public static double getImpactDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().impactValue();
            }
        }
        return 0.0;
    }

    public static double getPierceDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().pierceValue();
            }
        }
        return 0.0;
    }
}
