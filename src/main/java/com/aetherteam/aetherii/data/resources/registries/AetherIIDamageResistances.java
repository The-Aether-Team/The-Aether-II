package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.damage.DamageResistance;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
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
    public static final ResourceKey<DamageResistance> ZEPHER = createKey("zephyr");
    public static final ResourceKey<DamageResistance> AERBUNNY = createKey("aerbunny");
    public static final ResourceKey<DamageResistance> SHEEPUFF = createKey("sheepuff");
    public static final ResourceKey<DamageResistance> FLYING_COW = createKey("flying_cow");
    public static final ResourceKey<DamageResistance> PHYG = createKey("phyg");
    public static final ResourceKey<DamageResistance> KIRRID = createKey("kirrid");

    private static ResourceKey<DamageResistance> createKey(String name) {
        return ResourceKey.create(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageResistance> context) {
        context.register(EXAMPLE_1, new DamageResistance(EntityType.PIG, -2, 2, 2));
        context.register(EXAMPLE_2, new DamageResistance(EntityType.SHEEP, 2, -2, 2));
        context.register(EXAMPLE_3, new DamageResistance(EntityType.COW, 2, 2, -2));
        context.register(ZEPHER, new DamageResistance(AetherIIEntityTypes.ZEPHYR.get(), 0, 2, -2));
        context.register(AERBUNNY, new DamageResistance(AetherIIEntityTypes.AERBUNNY.get(), 0, 2, -2));
        context.register(SHEEPUFF, new DamageResistance(AetherIIEntityTypes.SHEEPUFF.get(), 0, 0, 0));
        context.register(FLYING_COW, new DamageResistance(AetherIIEntityTypes.FLYING_COW.get(), 0, 0, 0));
        context.register(PHYG, new DamageResistance(AetherIIEntityTypes.PHYG.get(), 0, 0, 0));
        context.register(KIRRID, new DamageResistance(AetherIIEntityTypes.KIRRID.get(), 0, 2, -2));
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
