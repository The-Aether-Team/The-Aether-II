package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class AetherIIStyleMaterials {
    public static final ResourceKey<StyleMaterial> BEAST_PELT = createKey("beast_pelt");
    public static final ResourceKey<StyleMaterial> BURRUKAI_PLATE = createKey("burrukai_plate");
    public static final ResourceKey<StyleMaterial> ZANITE = createKey("zanite");
    public static final ResourceKey<StyleMaterial> ARKENIUM = createKey("arkenium");
    public static final ResourceKey<StyleMaterial> GRAVITITE = createKey("gravitite");

    private static ResourceKey<StyleMaterial> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.STYLE_MATERIAL, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<StyleMaterial> context) {
        register(context, AetherIIItems.BEAST_PELT.get(), BEAST_PELT);
        register(context, AetherIIItems.BURRUKAI_PLATE.get(), BURRUKAI_PLATE);
        register(context, AetherIIItems.ZANITE_GEMSTONE.get(), ZANITE);
        register(context, AetherIIItems.ARKENIUM_PLATE.get(), ARKENIUM);
        register(context, AetherIIItems.GRAVITITE_PLATE.get(), GRAVITITE);
    }

    public static void register(BootstapContext<StyleMaterial> context, Item item, ResourceKey<StyleMaterial> key) {
        StyleMaterial design = new StyleMaterial(key.location(), BuiltInRegistries.ITEM.wrapAsHolder(item), Component.empty());
        context.register(key, design);
    }

    public static Registry<StyleMaterial> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.STYLE_MATERIAL);
    }
}
