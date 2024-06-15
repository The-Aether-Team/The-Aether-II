package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class AetherIIShaders {
    private static ShaderInstance cloudCoverShader;

    public static void registerShaders(RegisterShadersEvent event) {
        ResourceProvider resourceProvider = event.getResourceProvider();
        try {
            event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(AetherII.MODID, "cloud_cover"), DefaultVertexFormat.POSITION_COLOR), instance -> cloudCoverShader = instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ShaderInstance getCloudCoverShader() {
        return cloudCoverShader;
    }
}
