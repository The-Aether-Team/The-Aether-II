package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIDyeableClientItemExtensions;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddleModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.util.ARGB;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MoaSaddleLayer extends RenderLayer<Moa, EntityModel<Moa>> {
    private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle.png");
    private static final ResourceLocation SADDLE_OVERLAY_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle_overlay.png");
    private final MoaSaddleModel saddle;

    public MoaSaddleLayer(RenderLayerParent<Moa, EntityModel<Moa>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddle = new MoaSaddleModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible() && moa.isSaddled()) {
            ItemStack saddle = moa.getInventory().getItem(0);
            int colorRaw = AetherIIDyeableClientItemExtensions.getDefaultDyeColor(saddle, 0xFF7D8BA3);
            this.saddle.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.renderSaddle(poseStack, bufferSource, packedLight, SADDLE_TEXTURE, ARGB.redFloat(colorRaw), ARGB.greenFloat(colorRaw), ARGB.blueFloat(colorRaw));
            this.renderSaddle(poseStack, bufferSource, packedLight, SADDLE_OVERLAY_TEXTURE, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderSaddle(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ResourceLocation texture, float red, float green, float blue) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(texture));
        this.saddle.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
