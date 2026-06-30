package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.MoaRenderer;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class MoaEyesLayer extends RenderLayer<Moa, EntityModel<Moa>> {
    public MoaEyesLayer(RenderLayerParent<Moa, EntityModel<Moa>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible() && !MoaRenderer.hasSpecialTexture(moa)) {
            String name = moa.isBaby() ? "moa_baby_eyes" : "moa_eyes";
            ResourceLocation texture = new ResourceLocation(AetherII.MODID, "entity/mobs/moa/" + name + "_" + moa.getEyeColor().getSerializedName());
            TextureAtlasSprite sprite = AetherIIAtlases.getMoaEyesSprite(texture);
            VertexConsumer vertexConsumer = sprite.wrap(bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(AetherIIAtlases.MOA_EYES_SHEET)));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
