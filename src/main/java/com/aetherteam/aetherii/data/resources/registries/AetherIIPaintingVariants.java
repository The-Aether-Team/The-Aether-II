package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;

public class AetherIIPaintingVariants {
    public static final ResourceKey<PaintingVariant> FAR = create("far");

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, FAR, 4, 4);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> id, int width, int height) {
        register(context, id, width, height, true);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> id, int width, int height, boolean hasAuthor) {
        context.register(
            id,
            new PaintingVariant(
                width,
                height,
                id.identifier(),
                Optional.of(Component.translatable(id.identifier().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW)),
                hasAuthor
                    ? Optional.of(Component.translatable(id.identifier().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY))
                    : Optional.empty()
            )
        );
    }

    private static ResourceKey<PaintingVariant> create(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }
}
