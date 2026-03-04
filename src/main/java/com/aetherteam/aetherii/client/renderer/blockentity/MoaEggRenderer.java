package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.blockentity.MoaEggBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.state.MoaEggRenderState;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaEggModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class MoaEggRenderer implements BlockEntityRenderer<MoaEggBlockEntity, MoaEggRenderState> {
    private final MoaEggModel moaEggModel;
    private final TextureAtlas moaFeathersAtlas;
    private final TextureAtlas moaKeratinAtlas;
    private final TextureAtlas moaEyesAtlas;

    public MoaEggRenderer(BlockEntityRendererProvider.Context pContext) {
        this.moaEggModel = new MoaEggModel(pContext.entityModelSet().bakeLayer(AetherIIModelLayers.MOA_EGG));
        this.moaFeathersAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.MOA_FEATHER_SHEET);
        this.moaKeratinAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.MOA_KERATIN_SHEET);
        this.moaEyesAtlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.MOA_EYES_SHEET);
    }


    @Override
    public void submit(MoaEggRenderState moaEggRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(180F));

        if (moaEggRenderState.hatch > 0) {
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos((double) moaEggRenderState.tick * 3.25) * Math.PI * 0.4F * moaEggRenderState.hatch)));
        }

        TextureAtlasSprite feathersSprite = this.moaFeathersAtlas.getSprite(Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/moa_egg/moa_egg_feather_" + moaEggRenderState.featherShape.getSerializedName() + "_" + moaEggRenderState.featherColor.getSerializedName()));

        TextureAtlasSprite eyesSprite = this.moaEyesAtlas.getSprite(Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/moa_egg/moa_egg_eyes_" + moaEggRenderState.eyeColor.getSerializedName()));
        TextureAtlasSprite keratinSprite = this.moaKeratinAtlas.getSprite(Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/moa_egg/moa_egg_keratin_" + moaEggRenderState.keratinColor.getSerializedName()));

        submitNodeCollector.submitModel(
                this.moaEggModel, moaEggRenderState, poseStack, RenderTypes.entityCutoutNoCull(AetherIIAtlases.MOA_FEATHER_SHEET), -1, moaEggRenderState.lightCoords, -1, feathersSprite, 0, null
        );

        submitNodeCollector.submitModel(
                this.moaEggModel, moaEggRenderState, poseStack, RenderTypes.entityCutoutNoCull(AetherIIAtlases.MOA_EYES_SHEET), -1, moaEggRenderState.lightCoords, -1, eyesSprite, 0, null
        );

        submitNodeCollector.submitModel(
                this.moaEggModel, moaEggRenderState, poseStack, RenderTypes.entityCutoutNoCull(AetherIIAtlases.MOA_KERATIN_SHEET), -1, moaEggRenderState.lightCoords, -1, keratinSprite, 0, null
        );

    }


    @Override
    public MoaEggRenderState createRenderState() {
        return new MoaEggRenderState();
    }

    @Override
    public void extractRenderState(MoaEggBlockEntity blockEntity, MoaEggRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.MOA_EGG.get().defaultBlockState().setValue(AlkahestPurifierBlock.FACING, Direction.SOUTH);
        state.tick = blockEntity.tickCount;
        state.hatch = blockstate.getValue(MoaEggBlock.HATCH);
        state.featherShape = blockstate.getValue(MoaEggBlock.FEATHER_SHAPE);
        state.featherColor = blockstate.getValue(MoaEggBlock.FEATHERS);
        state.keratinColor = blockstate.getValue(MoaEggBlock.KERATIN);
        state.eyeColor = blockstate.getValue(MoaEggBlock.EYES);
    }

}
