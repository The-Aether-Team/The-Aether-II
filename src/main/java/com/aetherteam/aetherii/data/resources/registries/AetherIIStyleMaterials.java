package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class AetherIIStyleMaterials {
    public static final ResourceKey<Registry<StyleMaterial>> STYLE_MATERIAL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "style_material"));

    public static final ResourceKey<StyleMaterial> TAEGORE_HIDE = createKey("taegore_hide");
    public static final ResourceKey<StyleMaterial> BURRUKAI_PELT = createKey("burrukai_pelt");
    public static final ResourceKey<StyleMaterial> ZANITE = createKey("zanite");
    public static final ResourceKey<StyleMaterial> ARKENIUM = createKey("arkenium");
    public static final ResourceKey<StyleMaterial> GRAVITITE = createKey("gravitite");

    private static ResourceKey<StyleMaterial> createKey(String name) {
        return ResourceKey.create(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<StyleMaterial> context) {
        register(context, AetherIIItems.TAEGORE_HIDE.get(), TAEGORE_HIDE);
        register(context, AetherIIItems.BURRUKAI_PELT.get(), BURRUKAI_PELT);
        register(context, AetherIIItems.ZANITE_GEMSTONE.get(), ZANITE);
        register(context, AetherIIItems.ARKENIUM_PLATES.get(), ARKENIUM);
        register(context, AetherIIItems.GRAVITITE_PLATE.get(), GRAVITITE);
    }

    public static void register(BootstrapContext<StyleMaterial> context, Item item, ResourceKey<StyleMaterial> key) {
        StyleMaterial design = new StyleMaterial(key.location(), BuiltInRegistries.ITEM.wrapAsHolder(item), Component.empty());
        context.register(key, design);
    }

    public static Registry<StyleMaterial> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY);
    }
}
