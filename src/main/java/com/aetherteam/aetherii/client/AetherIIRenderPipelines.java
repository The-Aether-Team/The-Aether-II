package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public class AetherIIRenderPipelines {
    public static final RenderPipeline.Snippet ENTITY_DITHER_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_LIGHT_DIR_SNIPPET)
            .withVertexShader(Identifier.withDefaultNamespace("core/entity"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(AetherII.MODID, "core/entity_dither"))
            .withSampler("Sampler0")
            .withSampler("Sampler2")
            .withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .buildSnippet();

    public static final RenderPipeline ENTITY_DITHER_NO_CULL = RenderPipeline.builder(ENTITY_DITHER_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(AetherII.MODID, "pipeline/entity_dither_no_cull"))
            .withShaderDefine("ALPHA_CUTOUT", 0.1F)
            .withShaderDefine("PER_FACE_LIGHTING")
            .withSampler("Sampler1")
            .withCull(false)
            .build();

    public static final RenderPipeline BASE_SKY_SHADER = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(AetherII.MODID, "pipeline/base_sky"))
            .withVertexShader("core/position")
            .withFragmentShader("core/position")
            .withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.TRIANGLE_FAN)
            .build();

    public static final RenderPipeline TOP_SKY_GRADIENT_SHADER = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(AetherII.MODID, "pipeline/top_sky_gradient"))
            .withVertexShader("core/position_color")
            .withFragmentShader("core/position_color")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_FAN)
            .build();

    public static final RenderPipeline CLOUD_COVER_SHADER = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(AetherII.MODID, "pipeline/cloud_cover"))
            .withVertexShader("core/position_color")
            .withFragmentShader(Identifier.fromNamespaceAndPath(AetherII.MODID, "core/cloud_cover"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_FAN)
            .build();

    public static void registerShaders(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(ENTITY_DITHER_NO_CULL);
        event.registerPipeline(BASE_SKY_SHADER);
        event.registerPipeline(TOP_SKY_GRADIENT_SHADER);
        event.registerPipeline(CLOUD_COVER_SHADER);
    }
}
