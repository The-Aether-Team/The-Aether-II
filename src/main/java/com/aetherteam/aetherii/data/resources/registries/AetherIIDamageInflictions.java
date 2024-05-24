package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.damage.DamageInfliction;
import com.aetherteam.aetherii.item.AetherIIItems;
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

    public static final ResourceKey<DamageInfliction> SKYROOT_SWORD = createKey("skyroot_sword");
    public static final ResourceKey<DamageInfliction> SKYROOT_HAMMER = createKey("skyroot_hammer");
    public static final ResourceKey<DamageInfliction> SKYROOT_SPEAR = createKey("skyroot_spear");

    private static ResourceKey<DamageInfliction> createKey(String name) {
        return ResourceKey.create(AetherIIDamageInflictions.DAMAGE_INFLICTION_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageInfliction> context) {
        context.register(SKYROOT_SWORD, new DamageInfliction(AetherIIItems.SKYROOT_SWORD.get(), 4.0F, 0, 0));
        context.register(SKYROOT_HAMMER, new DamageInfliction(AetherIIItems.SKYROOT_HAMMER.get(), 0, 4.0F, 0));
        context.register(SKYROOT_SPEAR, new DamageInfliction(AetherIIItems.SKYROOT_SPEAR.get(), 0, 0, 4.0F));
    }

    public static float getSlashDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().slashValue();
            }
        }
        return 0.0F;
    }

    public static float getImpactDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().impactValue();
            }
        }
        return 0.0F;
    }

    public static float getPierceDamage(RegistryAccess registryAccess, ItemStack item) {
        for (Map.Entry<ResourceKey<DamageInfliction>, DamageInfliction> entry : registryAccess.registryOrThrow(DAMAGE_INFLICTION_REGISTRY_KEY).entrySet()) {
            if (item.is(entry.getValue().item())) {
                return entry.getValue().pierceValue();
            }
        }
        return 0.0F;
    }
}
