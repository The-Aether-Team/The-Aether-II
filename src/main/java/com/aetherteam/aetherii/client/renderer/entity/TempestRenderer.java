package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.TempestEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.TempestModel;
import com.aetherteam.aetherii.entity.monster.Tempest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * [CODE COPY] - {@link com.aetherteam.aether.client.renderer.entity.ZephyrRenderer}.
 */
public class TempestRenderer extends MobRenderer<Tempest, TempestModel> {
    private static final ResourceLocation TEMPEST_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/tempest/tempest.png");

    public TempestRenderer(EntityRendererProvider.Context context) {
        super(context, new TempestModel(context.bakeLayer(AetherIIModelLayers.TEMPEST)), 0.5F);
        this.addLayer(new TempestEmissiveLayer(this));
    }

    @Override
    protected void scale(Tempest tempest, PoseStack poseStack, float partialTickTime) {
        poseStack.translate(0.0, 0.27, 0.0);
        poseStack.translate(0.0, -0.1, 0.0);
        float sin = Mth.sin((tempest.tickCount + partialTickTime) / 6);
        poseStack.translate(0.0, sin / 15, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(Tempest tempest) {
        return TEMPEST_TEXTURE;
    }
}
