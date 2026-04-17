package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.blockentity.AlkahestPurifierBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.AlkahestPurifierRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class AlkahestPurifierRenderer implements BlockEntityRenderer<AlkahestPurifierBlockEntity, AlkahestPurifierRenderState> {
    public static final SpriteId ALKAHEST_PURIFIER_0 = AetherIIAtlases.ALKAHEST_PURIFIER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier_0"));
    public static final SpriteId ALKAHEST_PURIFIER_1 = AetherIIAtlases.ALKAHEST_PURIFIER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier_1"));
    public static final SpriteId ALKAHEST_PURIFIER_2 = AetherIIAtlases.ALKAHEST_PURIFIER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier_2"));
    public static final SpriteId ALKAHEST_PURIFIER_3 = AetherIIAtlases.ALKAHEST_PURIFIER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier_3"));
    public static final SpriteId ALKAHEST_PURIFIER_4 = AetherIIAtlases.ALKAHEST_PURIFIER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier_4"));
    private final AlkahestPurifierModel model;
    private final SpriteGetter sprites;

    public AlkahestPurifierRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.sprites());
    }

    public AlkahestPurifierRenderer(EntityModelSet modelSet, SpriteGetter sprites) {
        this.sprites = sprites;
        this.model = new AlkahestPurifierModel(modelSet.bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
    }


    @Override
    public void submit(AlkahestPurifierRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        float yRot = state.angle;
        int alkahestLevel = state.level;
        float openNess = state.open;
        openNess = 1.0F - openNess;
        openNess = 1.0F - openNess * openNess * openNess;
        this.render(poseStack, submitNodeCollector, state, state.lightCoords, yRot, alkahestLevel, openNess);
    }


    public void render(PoseStack poseStack, SubmitNodeCollector collector, AlkahestPurifierRenderState state, int packedLight, float yRot, int alkahestLevel, float openness) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        SpriteId spriteId = getTextureForLevel(state.level);
        this.model.setupAnim(openness);
        collector.submitModel(
                this.model, openness, poseStack, spriteId.renderType(this.model::renderType), packedLight, OverlayTexture.NO_OVERLAY, -1, this.materialSet.get(material), 0, state.breakProgress
        );

        poseStack.popPose();
    }

    public SpriteId getTextureForLevel(int alkahestLevel) {
        return switch(alkahestLevel) {
            case 1 -> ALKAHEST_PURIFIER_1;
            case 2 -> ALKAHEST_PURIFIER_2;
            case 3 -> ALKAHEST_PURIFIER_3;
            case 4 -> ALKAHEST_PURIFIER_4;
            default -> ALKAHEST_PURIFIER_0;
        };
    }

    @Override
    public AlkahestPurifierRenderState createRenderState() {
        return new AlkahestPurifierRenderState();
    }

    @Override
    public void extractRenderState(AlkahestPurifierBlockEntity blockEntity, AlkahestPurifierRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.ALKAHEST_PURIFIER.get().defaultBlockState().setValue(AlkahestPurifierBlock.FACING, Direction.SOUTH);
        state.angle = blockstate.getValue(AlkahestPurifierBlock.FACING).toYRot();
        state.level = blockstate.getValue(AlkahestPurifierBlock.LEVEL);
        state.open = blockEntity.getOpenNess(p_446851_);

    }

}
