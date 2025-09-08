package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public class AetherIIShaders {
    private static final ShaderProgram CLOUD_COVER_SHADER = new ShaderProgram(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "core/cloud_cover"), DefaultVertexFormat.POSITION_COLOR, ShaderDefines.EMPTY);

    public static void registerShaders(RegisterShadersEvent event) {
        event.registerShader(CLOUD_COVER_SHADER);
    }

    public static ShaderProgram getCloudCoverShader() {
        return CLOUD_COVER_SHADER;
    }
}
