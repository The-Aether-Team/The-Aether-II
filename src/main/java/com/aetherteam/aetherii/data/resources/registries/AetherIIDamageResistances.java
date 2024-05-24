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

    public static final ResourceKey<DamageResistance> EXAMPLE = createKey("example");

    private static ResourceKey<DamageResistance> createKey(String name) {
        return ResourceKey.create(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageResistance> context) {
        context.register(EXAMPLE, new DamageResistance(EntityType.PIG, 100, 100, 100));
    }

    public static float getSlashDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().slashValue();
            }
        }
        return 0.0F;
    }

    public static float getImpactDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().impactValue();
            }
        }
        return 0.0F;
    }

    public static float getPierceDefense(RegistryAccess registryAccess, Entity entity) {
        for (Map.Entry<ResourceKey<DamageResistance>, DamageResistance> entry : registryAccess.registryOrThrow(DAMAGE_RESISTANCE_REGISTRY_KEY).entrySet()) {
            if (entity.getType() == entry.getValue().entityType()) {
                return entry.getValue().pierceValue();
            }
        }
        return 0.0F;
    }
}
