package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIStyleDesigns { //todo style names
    public static final ResourceKey<Registry<StyleDesign>> STYLE_DESIGN_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "style_design"));

    public static final ResourceKey<StyleDesign> GUARD = createKey("guard");
    public static final ResourceKey<StyleDesign> KNIGHT = createKey("knight");
    public static final ResourceKey<StyleDesign> RANGER = createKey("ranger");
    public static final ResourceKey<StyleDesign> SCOUT = createKey("scout");
    public static final ResourceKey<StyleDesign> WARRIOR = createKey("warrior");

    private static ResourceKey<StyleDesign> createKey(String name) {
        return ResourceKey.create(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<StyleDesign> context) {
        register(context, GUARD);
        register(context, KNIGHT);
        register(context, RANGER);
        register(context, SCOUT);
        register(context, WARRIOR);
    }

    public static void register(BootstrapContext<StyleDesign> context, ResourceKey<StyleDesign> key) {
        StyleDesign design = new StyleDesign(key.location(), Component.empty());
        context.register(key, design);
    }

    public static Registry<StyleDesign> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY);
    }
}
