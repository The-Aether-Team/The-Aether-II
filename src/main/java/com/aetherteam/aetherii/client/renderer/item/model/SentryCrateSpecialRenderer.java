package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentryCrateSpecialRenderer {
    private final SentryCrateModel model;
    private final ResourceLocation texture;

    public SentryCrateSpecialRenderer(SentryCrateModel model, ResourceLocation texture) {
        this.model = model;
        this.texture = texture;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean hasFoil) {
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutout(this.texture), false, hasFoil);
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public record Unbaked(ResourceLocation texture) {
        public static final MapCodec<SentryCrateSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(SentryCrateSpecialRenderer.Unbaked::texture)
        ).apply(instance, SentryCrateSpecialRenderer.Unbaked::new));

        public MapCodec<SentryCrateSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SentryCrateSpecialRenderer bake(EntityModelSet modelSet) {
            ResourceLocation fullTexture = new ResourceLocation(AetherII.MODID, "textures/entity/sentry_crate/" + this.texture.getPath() + ".png");
            return new SentryCrateSpecialRenderer(new SentryCrateModel(modelSet.bakeLayer(AetherIIModelLayers.SENTRY_CRATE)), fullTexture);
        }
    }
}
