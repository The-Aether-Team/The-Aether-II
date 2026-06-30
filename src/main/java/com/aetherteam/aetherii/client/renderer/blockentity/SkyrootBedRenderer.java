package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.SkyrootBedBlock;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import java.util.Arrays;
import java.util.Comparator;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.blockentity.BedRenderer}.<br><br>
 * Stripped down to only use what is necessary.
 */
public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedBlockEntity> {
    private static final ResourceLocation BED_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/bed/skyroot/undyed.png");
    private static final ResourceLocation[] DYED_BED_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) -> new ResourceLocation(AetherII.MODID, "textures/entity/bed/skyroot/" + dyeColor.getName() + ".png")).toArray(ResourceLocation[]::new);
    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(AetherIIModelLayers.SKYROOT_BED_HEAD);
        this.footRoot = context.bakeLayer(AetherIIModelLayers.SKYROOT_BED_FOOT);
    }

    public SkyrootBedRenderer(EntityModelSet modelSet) {
        this.headRoot = modelSet.bakeLayer(AetherIIModelLayers.SKYROOT_BED_HEAD);
        this.footRoot = modelSet.bakeLayer(AetherIIModelLayers.SKYROOT_BED_FOOT);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 58).addBox(8.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 58).addBox(22.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 58).addBox(10.0F, -3.0F, -8.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 12).addBox(8.0F, -9.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(46, 4).addBox(8.0F, -13.0F, 6.0F, 16.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(48, 0).addBox(9.0F, -15.0F, 6.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(8.0F, 16.0F, 9.0F, 1.5708F, 0.0F, -3.1416F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    public static LayerDefinition createFootLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("foot", CubeListBuilder.create()
                        .texOffs(0, 77).addBox(-8.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 77).addBox(6.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 77).addBox(-6.0F, -3.0F, -8.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 34).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, 16.0F, 9.0F, 1.5708F, 0.0F, -3.1416F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void render(SkyrootBedBlockEntity bed, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level level = bed.getLevel();
        if (level != null) {
            BlockState blockstate = bed.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedBlockEntity> combineResult = DoubleBlockCombiner.combineWithNeigbour(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, SkyrootBedBlock.FACING, blockstate, level, bed.getBlockPos(), (levelAccessor, pos) -> false);
            int i = combineResult.apply(new BrightnessCombiner<>()).get(combinedLight);
            ResourceLocation bedTexture = texture(blockstate.getBlock(), bed.getColor());
            this.renderPiece(poseStack, buffer, blockstate.getValue(SkyrootBedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, blockstate.getValue(SkyrootBedBlock.FACING), bedTexture, i, combinedOverlay, false);
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, BED_LOCATION, combinedLight, combinedOverlay, false);
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, BED_LOCATION, combinedLight, combinedOverlay, true);
        }
    }

    public void renderInHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ResourceLocation location) {
        this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, location, packedLight, packedOverlay, false);
        this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, location, packedLight, packedOverlay, true);
    }

    public static ResourceLocation texture(Block block, DyeColor color) {
        return block == AetherIIBlocks.SKYROOT_BED.get() ? BED_LOCATION : DYED_BED_TEXTURES[color.getId()];
    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource buffer, ModelPart model, Direction direction, ResourceLocation location, int packedLight, int packedOverlay, boolean foot) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5625, foot ? -1.0 : 0.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.ZP.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(1.0, -0.5, 0.0);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(location));
        model.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
