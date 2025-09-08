package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;

public class AetherIIRenderTypes {
    public static final ResourceLocation IRRADIATED_GLINT_ITEM = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/misc/irradiated_glint_item.png");

    private static final RenderType CLOUD_COVER = RenderType.create(
            "aether:cloud_cover",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLE_FAN,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(AetherIIShaders.getCloudCoverShader()))
                    .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(RenderType.COLOR_WRITE)
                    .createCompositeState(false));

    private static final RenderType IRRADIATED_GLINT = RenderType.create(
            "aether_ii:irradiated_glint",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            1536,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_GLINT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(IRRADIATED_GLINT_ITEM, TriState.TRUE, false))
                    .setWriteMaskState(RenderType.COLOR_WRITE)
                    .setCullState(RenderType.NO_CULL)
                    .setDepthTestState(RenderType.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderType.GLINT_TEXTURING)
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