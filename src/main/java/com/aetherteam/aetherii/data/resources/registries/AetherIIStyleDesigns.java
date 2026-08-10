package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class AetherIIStyleDesigns { //todo style names
    public static final ResourceKey<StyleDesign> GUARD = createKey("guard");
    public static final ResourceKey<StyleDesign> KNIGHT = createKey("knight");
    public static final ResourceKey<StyleDesign> RANGER = createKey("ranger");
    public static final ResourceKey<StyleDesign> SCOUT = createKey("scout");
    public static final ResourceKey<StyleDesign> WARRIOR = createKey("warrior");

    private static ResourceKey<StyleDesign> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.STYLE_DESIGN, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<StyleDesign> context) {
        register(context, GUARD);
        register(context, KNIGHT);
        register(context, RANGER);
        register(context, SCOUT);
        register(context, WARRIOR);
    }

    public static void register(BootstrapContext<StyleDesign> context, ResourceKey<StyleDesign> key) {
        StyleDesign design = new StyleDesign(key.identifier(), Component.empty());
        context.register(key, design);
    }

    public static Registry<StyleDesign> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.STYLE_DESIGN);
    }
}
