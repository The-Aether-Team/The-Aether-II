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

public class MoaKeratinLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private final TextureAtlas moaKeratinAtlas;

    public MoaKeratinLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, ModelManager modelManager) {
        super(renderer);
        this.moaKeratinAtlas = modelManager.getAtlas(AetherIIAtlases.MOA_KERATIN_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, MoaRenderState moa, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible && !moa.hasSpecialTexture()) {
            String name = moa.isBaby ? "moa_baby_keratin" : "moa_keratin";
             texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/mobs/moa/" + name + "_" + moa.keratinColor);
            TextureAtlasSprite sprite = this.moaKeratinAtlas.getSprite(texture);
            VertexConsumer vertexConsumer = sprite.wrap(bufferSource.getBuffer(AetherIIRenderTypes.entityDitherNoCull(AetherIIAtlases.MOA_KERATIN_SHEET)));
            float opacity = moa.opacity;

            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F));
        }
    }
}
