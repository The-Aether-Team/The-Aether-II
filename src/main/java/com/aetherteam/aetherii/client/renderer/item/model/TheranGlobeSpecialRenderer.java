package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.TheranGlobeModel;
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

public class TheranGlobeSpecialRenderer implements NoDataSpecialModelRenderer {
    private final TheranGlobeModel model;
    private final float globeRotation;

    public TheranGlobeSpecialRenderer(TheranGlobeModel model, float globeRotation) {
        this.model = model;
        this.globeRotation = globeRotation;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        Identifier location = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/theran_globe/theran_globe.png");
        RenderType renderType = RenderTypes.entityCutout(location);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.globeRotation);
        submitNodeCollector.submitModel(this.model, this.globeRotation, poseStack, renderType, i, i1, -1, null, i2, null);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(this.globeRotation);
        this.model.root().getExtentsForGui(poseStack, consumer);
    }

    public record Unbaked(float globeRotation) implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<TheranGlobeSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.FLOAT.optionalFieldOf("globe_rotation", 0.0F).forGetter(TheranGlobeSpecialRenderer.Unbaked::globeRotation)
        ).apply(instance, TheranGlobeSpecialRenderer.Unbaked::new));

        public Unbaked() {
            this(0.0F);
        }

        public Unbaked(float globeRotation) {
            this.globeRotation = globeRotation;
        }

        @Override
        public MapCodec<TheranGlobeSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public NoDataSpecialModelRenderer bake(BakingContext context) {
            TheranGlobeModel model = new TheranGlobeModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.THERAN_GLOBE));
            return new TheranGlobeSpecialRenderer(model, this.globeRotation);
        }
    }
}
