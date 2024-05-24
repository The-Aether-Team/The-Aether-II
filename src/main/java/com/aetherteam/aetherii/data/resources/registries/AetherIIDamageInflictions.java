package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.damage.DamageInfliction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;

public class AetherIIDamageInflictions {
    public static final ResourceKey<Registry<DamageInfliction>> DAMAGE_INFLICTION_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(AetherII.MODID, "damage_infliction"));

    public static final ResourceKey<DamageInfliction> EXAMPLE = createKey("example");

    private static ResourceKey<DamageInfliction> createKey(String name) {
        return ResourceKey.create(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageInfliction> context) {
        context.register(EXAMPLE, new DamageInfliction(Items.APPLE, 100, 100, 100));
    }

    public static int getSlashDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().slashValue();
            }
        }
        return 0;
    }

    public static int getImpactDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().impactValue();
            }
        }
        return 0;
    }

    public static int getPierceDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().pierceValue();
            }
        }
        return 0;
    }
}
