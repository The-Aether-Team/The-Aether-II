package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.blockentity.AlkahestPurifierBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AlkahestPurifierRenderer implements BlockEntityRenderer<AlkahestPurifierBlockEntity> {
    public static final Material ALKAHEST_PURIFIER_0 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/alkahest_purifier/alkahest_purifier_0"));
    public static final Material ALKAHEST_PURIFIER_1 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/alkahest_purifier/alkahest_purifier_1"));
    public static final Material ALKAHEST_PURIFIER_2 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/alkahest_purifier/alkahest_purifier_2"));
    public static final Material ALKAHEST_PURIFIER_3 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/alkahest_purifier/alkahest_purifier_3"));
    public static final Material ALKAHEST_PURIFIER_4 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/alkahest_purifier/alkahest_purifier_4"));
    private final AlkahestPurifierModel model;

    public AlkahestPurifierRenderer(BlockEntityRendererProvider.Context context) {
        this(context.getModelSet());
    }

    public AlkahestPurifierRenderer(EntityModelSet modelSet) {
        this.model = new AlkahestPurifierModel(modelSet.bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
    }

    @Override
    public void render(AlkahestPurifierBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        boolean levelExists = level != null;
        BlockState blockstate = levelExists ? blockEntity.getBlockState() : AetherIIBlocks.ALKAHEST_PURIFIER.get().defaultBlockState().setValue(AlkahestPurifierBlock.FACING, Direction.SOUTH);
        float yRot = blockstate.getValue(AlkahestPurifierBlock.FACING).toYRot();
        int alkahestLevel = blockstate.getValue(AlkahestPurifierBlock.LEVEL);
        float openNess = blockEntity.getOpenNess(partialTick);
        openNess = 1.0F - openNess;
        openNess = 1.0F - openNess * openNess * openNess;
        this.render(poseStack, buffer, packedLight, packedOverlay, yRot, alkahestLevel, openNess);
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, float yRot, int alkahestLevel, float openness) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        VertexConsumer consumer = this.getTextureForLevel(alkahestLevel).buffer(buffer, RenderType::entityCutout);
        this.model.setupAnim(openness);
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    public Material getTextureForLevel(int alkahestLevel) {
        return switch(alkahestLevel) {
            case 1 -> ALKAHEST_PURIFIER_1;
            case 2 -> ALKAHEST_PURIFIER_2;
            case 3 -> ALKAHEST_PURIFIER_3;
            case 4 -> ALKAHEST_PURIFIER_4;
            default -> ALKAHEST_PURIFIER_0;
        };
    }
}
