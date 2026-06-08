package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.FungalCacheModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class FungalCacheSpecialRenderer implements NoDataSpecialModelRenderer {
    private final FungalCacheModel model;
    private final float openness;

    public FungalCacheSpecialRenderer(FungalCacheModel model, float openness) {
        this.model = model;
        this.openness = openness;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        Identifier location = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/fungal_cache/fungal_cache.png");
        RenderType renderType = RenderTypes.entityCutout(location);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.openness);
        submitNodeCollector.submitModel(this.model, this.openness, poseStack, renderType, i, i1, -1, null, i2, null);
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
        public static final MapCodec<FungalCacheSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(FungalCacheSpecialRenderer.Unbaked::openness)
        ).apply(instance, FungalCacheSpecialRenderer.Unbaked::new));

        public Unbaked() {
            this(0.0F);
        }

        public Unbaked(float openness) {
            this.openness = openness;
        }

        @Override
        public MapCodec<FungalCacheSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public NoDataSpecialModelRenderer bake(BakingContext context) {
            FungalCacheModel model = new FungalCacheModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.FUNGAL_CACHE));
            return new FungalCacheSpecialRenderer(model, this.openness);
        }
    }
}
