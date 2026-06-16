package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIRenderPipelines;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.TextureTransform;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;

import java.util.function.BiFunction;

public class AetherIIRenderTypes {
    public static final Identifier IRRADIATED_GLINT_ITEM = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/misc/irradiated_glint_item.png");

    public static final BiFunction<Identifier, Boolean, RenderType> ENTITY_DITHER_NO_CULL = Util.memoize((location, outline) -> RenderType.create(
            "aether:entity_dither_no_cull",
            RenderSetup.builder(AetherIIRenderPipelines.getEntityDitherNoCull())
                    .withTexture("Sampler0", location)
                    .useLightmap()
                    .useOverlay()
                    .affectsCrumbling()
                    .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                    .createRenderSetup())
    );

    private static final RenderType CLOUD_COVER = RenderType.create(
            "aether:cloud_cover",
            RenderSetup.builder(AetherIIRenderPipelines.getCloudCoverShader())
                    .createRenderSetup());

    private static final RenderType IRRADIATED_GLINT = RenderType.create(
            "aether_ii:irradiated_glint",
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", IRRADIATED_GLINT_ITEM)
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .createRenderSetup());

    public static RenderType entityDitherNoCull(Identifier location) {
        return entityDitherNoCull(location, true);
    }

    public static RenderType entityDitherNoCull(Identifier location, boolean outline) {
        if (ShaderCompatibility.areShadersActive()) {
            return RenderTypes.entityTranslucent(location, outline);
        }
        return ENTITY_DITHER_NO_CULL.apply(location, outline);
    }

    public static RenderType cloudCover() {
        return CLOUD_COVER;
    }

    public static RenderType irradiatedGlint() {
        return IRRADIATED_GLINT;
    }

    public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
        event.registerRenderBuffer(cloudCover());
        event.registerRenderBuffer(irradiatedGlint());
    }
}