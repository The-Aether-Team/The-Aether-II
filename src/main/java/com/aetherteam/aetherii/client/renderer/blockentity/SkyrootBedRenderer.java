package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.SkyrootBedBlock;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.state.SkyrootBedRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.blockentity.BedRenderer}.<br><br>
 * Stripped down to only use what is necessary.
 */
public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedBlockEntity, SkyrootBedRenderState> {
    private static final Identifier BED_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/undyed.png");
    public static final Identifier[] DYED_BED_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) -> Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/" + dyeColor.getName() + ".png")).toArray(Identifier[]::new);
    private final Model<Unit> headModel;
    private final Model<Unit> footModel;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet());
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
    public SkyrootBedRenderState createRenderState() {
        return new SkyrootBedRenderState();
    }

    @Override
    public void extractRenderState(SkyrootBedBlockEntity blockEntity, SkyrootBedRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.SKYROOT_BED.get().defaultBlockState().setValue(SkyrootBedBlock.FACING, Direction.SOUTH);
        state.angle = blockstate.getValue(SkyrootBedBlock.FACING);
        state.bedTexture = blockstate.getBlock() != AetherIIBlocks.SKYROOT_BED.get() ? DYED_BED_TEXTURES[blockEntity.getColor().getId()] : BED_LOCATION;
        DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedBlockEntity> combiner = DoubleBlockCombiner.combineWithNeigbour(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, SkyrootBedBlock.FACING, blockstate, blockEntity.getLevel(), blockEntity.getBlockPos(), (levelAccessor, blockPos) -> false);
        int i = combiner.apply(new BrightnessCombiner<>()).get(state.lightCoords);
        state.lightCoords = i;
        state.bedPart = blockstate.getValue(SkyrootBedBlock.PART);
    }

    @Override
    public void submit(SkyrootBedRenderState skyRootBedRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        this.renderPiece(poseStack, submitNodeCollector, skyRootBedRenderState.bedPart == BedPart.HEAD ? this.headModel : this.footModel, skyRootBedRenderState.angle, skyRootBedRenderState.bedTexture, skyRootBedRenderState.lightCoords, OverlayTexture.NO_OVERLAY, false, skyRootBedRenderState.breakProgress, 0);
    }

    public void renderInHand(PoseStack poseStack, SubmitNodeCollector bufferSource, int packedLight, int packedOverlay, Identifier location) {
        this.renderPiece(poseStack, bufferSource, this.headModel, Direction.SOUTH, location, packedLight, packedOverlay, false, null, 0);
        this.renderPiece(poseStack, bufferSource, this.footModel, Direction.SOUTH, location, packedLight, packedOverlay, true, null, 0);
    }

    private void renderPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Model model, Direction direction, Identifier location, int packedLight, int packedOverlay, boolean isFeet,
                             ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay,
                             int p_451666_) {
        poseStack.pushPose();
        preparePose(poseStack, isFeet, direction);
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, RenderTypes.entityCutout(location), packedLight, packedOverlay, -1, null, p_451666_, crumblingOverlay);
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

    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack posestack = new PoseStack();
        preparePose(posestack, false, Direction.SOUTH);
        this.headModel.root().getExtentsForGui(posestack, output);
        posestack.setIdentity();
        preparePose(posestack, true, Direction.SOUTH);
        this.footModel.root().getExtentsForGui(posestack, output);
    }
}

