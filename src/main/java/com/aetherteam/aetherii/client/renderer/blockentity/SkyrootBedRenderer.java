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
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.blockentity.BedRenderer}.<br><br>
 * Stripped down to only use what is necessary.
 */
public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedBlockEntity> {
    private static final Identifier BED_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/undyed.png");
    public static final Identifier[] DYED_BED_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) -> Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/" + dyeColor.getName() + ".png")).toArray(Identifier[]::new);
    private final Model headModel;
    private final Model footModel;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        this(context.getModelSet());
    }

    public SkyrootBedRenderer(EntityModelSet modelSet) {
        this.headModel = new Model.Simple(modelSet.bakeLayer(AetherIIModelLayers.SKYROOT_BED_HEAD), RenderTypes::entityCutout);
        this.footModel = new Model.Simple(modelSet.bakeLayer(AetherIIModelLayers.SKYROOT_BED_FOOT), RenderTypes::entityCutout);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 58).addBox(8.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(92, 58).addBox(22.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(36, 58).addBox(10.0F, -3.0F, -8.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(32, 12).addBox(8.0F, -9.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(46, 4).addBox(8.0F, -13.0F, 6.0F, 16.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(9.0F, -15.0F, 6.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 16.0F, 9.0F, 1.5708F, 0.0F, -3.1416F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    public static LayerDefinition createFootLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("foot", CubeListBuilder.create().texOffs(0, 77).addBox(-8.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(92, 77).addBox(6.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(36, 77).addBox(-6.0F, -3.0F, -8.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(32, 34).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 16.0F, 9.0F, 1.5708F, 0.0F, -3.1416F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void render(SkyrootBedBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, Vec3 pos) {
        Level level = blockEntity.getLevel();
        BlockState state = blockEntity.getBlockState();
        if (level != null) {
            Identifier location = state.getBlock() != AetherIIBlocks.SKYROOT_BED.get() ? DYED_BED_TEXTURES[blockEntity.getColor().getId()] : BED_LOCATION;
            BlockState blockstate = blockEntity.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedBlockEntity> combiner = DoubleBlockCombiner.combineWithNeigbour(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, SkyrootBedBlock.FACING, blockstate, level, blockEntity.getBlockPos(), (levelAccessor, blockPos) -> false);
            int i = combiner.apply(new BrightnessCombiner<>()).get(packedLight);
            this.renderPiece(poseStack, multiBufferSource, blockstate.getValue(SkyrootBedBlock.PART) == BedPart.HEAD ? this.headModel : this.footModel, blockstate.getValue(SkyrootBedBlock.FACING), location, i, packedOverlay, false);
        }
    }

    public void renderInHand(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Identifier location) {
        this.renderPiece(poseStack, bufferSource, this.headModel, Direction.SOUTH, location, packedLight, packedOverlay, false);
        this.renderPiece(poseStack, bufferSource, this.footModel, Direction.SOUTH, location, packedLight, packedOverlay, true);
    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource bufferSource, Model model, Direction direction, Identifier location, int packedLight, int packedOverlay, boolean isFeet) {
        poseStack.pushPose();
        preparePose(poseStack, isFeet, direction);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderTypes.entityCutout(location));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private static void preparePose(PoseStack poseStack, boolean isFeet, Direction direction) {
        poseStack.translate(0.0F, 0.5625F, isFeet ? -1.0F : 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        poseStack.translate(1.0, -0.5F, 0.0F);
    }

    public void getExtents(Set<Vector3f> output) {
        PoseStack posestack = new PoseStack();
        preparePose(posestack, false, Direction.SOUTH);
        this.headModel.root().getExtentsForGui(posestack, output);
        posestack.setIdentity();
        preparePose(posestack, true, Direction.SOUTH);
        this.footModel.root().getExtentsForGui(posestack, output);
    }
}

