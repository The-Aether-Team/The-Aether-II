package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AlkahestPurifierSpecialRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/alkahest_purifier/alkahest_purifier_0.png");
    private final AlkahestPurifierModel model;
    private final float openness;

    public AlkahestPurifierSpecialRenderer(AlkahestPurifierModel model, float openness) {
        this.model = model;
        this.openness = openness;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean hasFoil) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.model.setupAnim(this.openness);
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutout(TEXTURE), false, hasFoil);
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public record Unbaked(float openness) {
        public static final MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(AlkahestPurifierSpecialRenderer.Unbaked::openness)
        ).apply(instance, AlkahestPurifierSpecialRenderer.Unbaked::new));

        public Unbaked() {
            this(0.0F);
        }

        public MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public AlkahestPurifierSpecialRenderer bake(EntityModelSet modelSet) {
            return new AlkahestPurifierSpecialRenderer(new AlkahestPurifierModel(modelSet.bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER)), this.openness);
        }
    }
}
