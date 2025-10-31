package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.AlkahestPurifierRenderer;
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
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

public class AlkahestPurifierSpecialRenderer implements NoDataSpecialModelRenderer {
    private final AlkahestPurifierModel model;
    private final float openness;

    public AlkahestPurifierSpecialRenderer(AlkahestPurifierModel model, float openness) {
        this.model = model;
        this.openness = openness;
    }

    @Override
    public void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean partialTick) {
        VertexConsumer vertexconsumer = AlkahestPurifierRenderer.ALKAHEST_PURIFIER_0.buffer(buffer, RenderType::entitySolid);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.openness);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.openness);
        this.model.root().getExtentsForGui(poseStack, set);
    }

    public record Unbaked(float openness) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(AlkahestPurifierSpecialRenderer.Unbaked::openness)
        ).apply(instance, AlkahestPurifierSpecialRenderer.Unbaked::new));

        public Unbaked() {
            this(0.0F);
        }

        public Unbaked(float openness) {
            this.openness = openness;
        }

        @Override
        public MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            AlkahestPurifierModel model = new AlkahestPurifierModel(entityModelSet.bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
            return new AlkahestPurifierSpecialRenderer(model, this.openness);
        }
    }
}
