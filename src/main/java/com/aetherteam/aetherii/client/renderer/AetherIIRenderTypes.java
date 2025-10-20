package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIRenderPipelines;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;

public class AetherIIRenderTypes {
    public static final ResourceLocation IRRADIATED_GLINT_ITEM = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/misc/irradiated_glint_item.png");

    private static final RenderType CLOUD_COVER = RenderType.create(
            "aether:cloud_cover",
            1536,
            AetherIIRenderPipelines.getCloudCoverShader(),
            RenderType.CompositeState.builder()
                    .createCompositeState(false));

    private static final RenderType IRRADIATED_GLINT = RenderType.create(
            "aether_ii:irradiated_glint",
            1536,
            RenderPipelines.GLINT,
            RenderType.CompositeState.builder()
                    .setTextureState(new RenderStateShard.TextureStateShard(IRRADIATED_GLINT_ITEM, false))
                    .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                    .createCompositeState(false)
    );

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