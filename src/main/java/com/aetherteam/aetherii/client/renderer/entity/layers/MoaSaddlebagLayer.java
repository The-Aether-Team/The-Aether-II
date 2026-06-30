package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaLargeSaddlebagModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddlebagModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MoaSaddlebagLayer extends RenderLayer<Moa, EntityModel<Moa>> {
    private static final ResourceLocation SADDLEBAG_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag.png");
    private static final ResourceLocation LARGE_SADDLEBAG_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag_large.png");

    private final MoaSaddlebagModel saddlebag;
    private final MoaLargeSaddlebagModel largeSaddlebag;

    public MoaSaddlebagLayer(RenderLayerParent<Moa, EntityModel<Moa>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddlebag = new MoaSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLEBAG));
        this.largeSaddlebag = new MoaLargeSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_LARGE_SADDLEBAG));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible()) {
            if (moa.getSaddlebagStack().is(AetherIIItems.MOA_SADDLEBAG.get())) {
                this.saddlebag.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer vertexConsumer = bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(SADDLEBAG_TEXTURE));
                this.saddlebag.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            } else if (moa.getSaddlebagStack().is(AetherIIItems.LARGE_MOA_SADDLEBAG.get())) {
                this.largeSaddlebag.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer vertexConsumer = bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(LARGE_SADDLEBAG_TEXTURE));
                this.largeSaddlebag.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
