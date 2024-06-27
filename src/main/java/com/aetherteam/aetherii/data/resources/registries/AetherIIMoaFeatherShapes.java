package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.entity.MoaFeatherShape;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIMoaFeatherShapes {
    public static final ResourceKey<Registry<MoaFeatherShape>> MOA_FEATHER_SHAPE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_feather_shape"));

    public static final ResourceKey<MoaFeatherShape> CURVED = createKey("curved");
    public static final ResourceKey<MoaFeatherShape> FLAT = createKey("flat");
    public static final ResourceKey<MoaFeatherShape> POINTED = createKey("pointed");

    private static ResourceKey<MoaFeatherShape> createKey(String name) {
        return ResourceKey.create(AetherIIMoaFeatherShapes.MOA_FEATHER_SHAPE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<MoaFeatherShape> context) {
        context.register(CURVED, new MoaFeatherShape(1.0, 1.0, 1.0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa.png")));
        context.register(FLAT, new MoaFeatherShape(1.0, 1.0, 1.0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa.png")));
        context.register(POINTED, new MoaFeatherShape(1.0, 1.0, 1.0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa.png")));
    }

    public static Registry<MoaFeatherShape> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIMoaFeatherShapes.MOA_FEATHER_SHAPE_REGISTRY_KEY);
    }
}