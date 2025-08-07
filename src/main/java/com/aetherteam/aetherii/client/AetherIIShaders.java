package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public class AetherIIShaders {
    private static final RenderPipeline CLOUD_COVER_SHADER = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withLocation(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "pipeline/cloud_cover"))
            .withVertexShader("core/position_color")
            .withFragmentShader(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "core/cloud_cover"))
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthWrite(false)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_FAN)
            .build();

    public static void registerShaders(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(CLOUD_COVER_SHADER);
    }

    public static RenderPipeline getCloudCoverShader() {
        return CLOUD_COVER_SHADER;
    }
}
