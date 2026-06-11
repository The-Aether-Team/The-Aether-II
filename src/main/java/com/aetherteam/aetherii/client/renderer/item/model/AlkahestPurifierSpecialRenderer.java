package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.AlkahestPurifierRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class AlkahestPurifierSpecialRenderer implements NoDataSpecialModelRenderer {
    private final AlkahestPurifierModel model;
    private final float openness;
    private final SpriteGetter sprites;

    public AlkahestPurifierSpecialRenderer(SpriteGetter context, AlkahestPurifierModel model, float openness) {
        this.sprites = context;
        this.model = model;
        this.openness = openness;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        RenderType renderType = AlkahestPurifierRenderer.ALKAHEST_PURIFIER_0.renderType(RenderTypes::entitySolid);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.openness);
        submitNodeCollector.submitModel(this.model, this.openness, poseStack, renderType, i, i1, -1, this.sprites.get(AlkahestPurifierRenderer.ALKAHEST_PURIFIER_0), i2, null);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.openness);
        this.model.root().getExtentsForGui(poseStack, consumer);
    }

    public record Unbaked(float openness) implements NoDataSpecialModelRenderer.Unbaked {
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
        public NoDataSpecialModelRenderer bake(BakingContext context) {
            AlkahestPurifierModel model = new AlkahestPurifierModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
            return new AlkahestPurifierSpecialRenderer(context.sprites(), model, this.openness);
        }
    }
}
