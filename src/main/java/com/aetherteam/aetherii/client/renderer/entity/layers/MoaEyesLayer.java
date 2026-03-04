package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class MoaEyesLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private final TextureAtlas moaEyesAtlas;

    public MoaEyesLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, ModelManager modelManager) {
        super(renderer);
        this.moaEyesAtlas = modelManager.getAtlas(AetherIIAtlases.MOA_EYES_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, MoaRenderState moa, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible && !moa.hasSpecialTexture()) {
            String name = moa.isBaby ? "moa_baby_eyes" : "moa_eyes";
             texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/mobs/moa/" + name + "_" + moa.eyeColor);
            TextureAtlasSprite sprite = this.moaEyesAtlas.getSprite(texture);
            VertexConsumer vertexConsumer = sprite.wrap(bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(AetherIIAtlases.MOA_EYES_SHEET)));
            float opacity = moa.opacity;

            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F));
        }
    }
}
