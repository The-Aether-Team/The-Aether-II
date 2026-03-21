package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class VaseSpecialRenderer implements NoDataSpecialModelRenderer {
    private final VaseModel vaseModel;

    public VaseSpecialRenderer(VaseModel vaseModel) {
        this.vaseModel = vaseModel;
    }

    @Override
    public void submit(ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        Identifier location = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/vases/veradexian_vase.png");
        RenderType renderType = RenderTypes.entityCutout(location);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        submitNodeCollector.submitModel(this.vaseModel, Unit.INSTANCE, poseStack, renderType, i, i1, -1, null, i2, null);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.vaseModel.root().getExtentsForGui(poseStack, consumer);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<VaseSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new VaseSpecialRenderer.Unbaked());

        public Unbaked() {
        }

        @Override
        public MapCodec<VaseSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(BakingContext context) {
            VaseModel model = new VaseModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.VASE));
            return new VaseSpecialRenderer(model);
        }
    }
}
