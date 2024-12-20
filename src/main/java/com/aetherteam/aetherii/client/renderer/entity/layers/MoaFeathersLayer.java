package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.MoaRenderer;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;

public class MoaFeathersLayer extends RenderLayer<Moa, EntityModel<Moa>> {
    private final TextureAtlas moaFeathersAtlas;

    public MoaFeathersLayer(RenderLayerParent<Moa, EntityModel<Moa>> renderer, ModelManager modelManager) {
        super(renderer);
        this.moaFeathersAtlas = modelManager.getAtlas(MoaRenderer.MOA_FEATHER_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible()) {
            String name = moa.isBaby() ? "moa_baby_feather" : "moa_feather";
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/mobs/moa/" + name + "_" + moa.getFeatherShape() + "_" + moa.getFeatherColor());
            TextureAtlasSprite sprite = this.moaFeathersAtlas.getSprite(texture);
            VertexConsumer vertexConsumer = sprite.wrap(bufferSource.getBuffer(RenderType.entityCutoutNoCull(MoaRenderer.MOA_FEATHER_SHEET)));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F));
        }
    }
}
