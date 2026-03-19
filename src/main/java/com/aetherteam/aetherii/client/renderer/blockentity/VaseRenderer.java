package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.VaseRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class VaseRenderer implements BlockEntityRenderer<VaseBlockEntity, VaseRenderState> {
    private final TextureAtlas vaseAtlas;
    private final ModelPart vaseModel;
    private static final float WOBBLE_AMPLITUDE = 0.125F;

    public VaseRenderer(BlockEntityRendererProvider.Context context) {
        this.vaseAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.VASE_ID);
        this.vaseModel = new VaseModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.VASE)).root();
    }

    public VaseRenderer(SpecialModelRenderer.BakingContext context) {
        this.vaseAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.VASE_ID);
        this.vaseModel = new VaseModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.VASE)).root();
    }

    public VaseRenderState createRenderState() {
        return new VaseRenderState();
    }

    public void extractRenderState(VaseBlockEntity blockEntity, VaseRenderState renderState, float p_447091_, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, p_447091_, vec3, crumblingOverlay);
        renderState.direction = blockEntity.getDirection();
        VaseBlockEntity.WobbleStyle wobblestyle = blockEntity.lastWobbleStyle;
        if (wobblestyle != null && blockEntity.getLevel() != null) {
            renderState.wobbleProgress = ((float)(blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + p_447091_)
                / wobblestyle.duration;
        } else {
            renderState.wobbleProgress = 0.0F;
        }
        renderState.vaseTexture = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/vases/veradexian_vase.png"); //todo
    }

    public void submit(VaseRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        Direction direction = renderState.direction;
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - direction.toYRot()));
        poseStack.translate(-0.5, 0.0, -0.5);
        if (renderState.wobbleProgress >= 0.0F && renderState.wobbleProgress <= 1.0F) {
            if (renderState.wobbleStyle == VaseBlockEntity.WobbleStyle.POSITIVE) {
                float f = 0.015625F;
                float f1 = renderState.wobbleProgress * (float) (Math.PI * 2);
                float f2 = -1.5F * (Mth.cos(f1) + 0.5F) * Mth.sin(f1 / 2.0F);
                poseStack.rotateAround(Axis.XP.rotation(f2 * 0.015625F), 0.5F, 0.0F, 0.5F);
                float f3 = Mth.sin(f1);
                poseStack.rotateAround(Axis.ZP.rotation(f3 * 0.015625F), 0.5F, 0.0F, 0.5F);
            } else {
                float f4 = Mth.sin(-renderState.wobbleProgress * 3.0F * (float) Math.PI) * 0.125F;
                float f5 = 1.0F - renderState.wobbleProgress;
                poseStack.rotateAround(Axis.YP.rotation(f4 * f5), 0.5F, 0.0F, 0.5F);
            }
        }
        TextureAtlasSprite vaseSprite = this.vaseAtlas.getSprite(renderState.vaseTexture);
        nodeCollector.submitModelPart(this.vaseModel, poseStack, RenderTypes.entityCutoutNoCull(AetherIIAtlases.VASE_SHEET), renderState.lightCoords, OverlayTexture.NO_OVERLAY, vaseSprite, false, false, -1, null, 0);
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(VaseBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.3, pos.getZ() + 1.0);
    }

    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.vaseModel.getExtentsForGui(poseStack, output);
    }
}