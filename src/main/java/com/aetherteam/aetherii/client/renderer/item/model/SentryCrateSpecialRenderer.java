package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Set;
import java.util.function.Consumer;

public class SentryCrateSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SentryCrateModel model;
    private final Material material;

    public SentryCrateSpecialRenderer(SentryCrateModel model, Material material) {
        this.model = model;
        this.material = material;
    }

    @Override
    public void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean partialTick) {
        VertexConsumer vertexConsumer = this.material.buffer(buffer, RenderTypes::entitySolid);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack posestack = new PoseStack();
        this.model.root().getExtentsForGui(posestack, consumer);
    }

    public record Unbaked(Identifier texture) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<SentryCrateSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(SentryCrateSpecialRenderer.Unbaked::texture)
        ).apply(instance, SentryCrateSpecialRenderer.Unbaked::new));

        public MapCodec<SentryCrateSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(BakingContext context) {
            SentryCrateModel model = new SentryCrateModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
            Material material = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(this.texture);
            return new SentryCrateSpecialRenderer(model, material);
        }
    }
}
