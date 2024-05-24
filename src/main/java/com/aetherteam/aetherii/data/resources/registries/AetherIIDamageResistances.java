package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.damage.DamageResistance;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class AetherIIDamageResistances {
    public static final ResourceKey<Registry<DamageResistance>> DAMAGE_RESISTANCE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(AetherII.MODID, "damage_resistance"));

    public static final ResourceKey<DamageResistance> EXAMPLE = createKey("example");

    private static ResourceKey<DamageResistance> createKey(String name) {
        return ResourceKey.create(AetherIIDamageResistances.DAMAGE_RESISTANCE_REGISTRY_KEY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DamageResistance> context) {
        context.register(EXAMPLE, new DamageResistance(EntityType.PIG, 100, 100, 100));
    }
}
