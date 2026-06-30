package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class VaseSpecialRenderer {
    private final VaseModel vaseModel;
    private final ResourceLocation location;

    public VaseSpecialRenderer(VaseModel vaseModel, ResourceLocation location) {
        this.vaseModel = vaseModel;
        this.location = location;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean hasFoil) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutout(this.location), false, hasFoil);
        this.vaseModel.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public record Unbaked(ResourceLocation texture) {
        public static final MapCodec<VaseSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(VaseSpecialRenderer.Unbaked::texture)
        ).apply(instance, VaseSpecialRenderer.Unbaked::new));

        public MapCodec<VaseSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public VaseSpecialRenderer bake(EntityModelSet modelSet) {
            return new VaseSpecialRenderer(new VaseModel(modelSet.bakeLayer(AetherIIModelLayers.VASE)), this.texture);
        }
    }
}
