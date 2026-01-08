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
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.blockentity.BedRenderer}.<br><br>
 * Stripped down to only use what is necessary.
 */
public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedBlockEntity> {
    private static final ResourceLocation BED_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/undyed.png");
    public static final ResourceLocation[] DYED_BED_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) -> ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/" + dyeColor.getName() + ".png")).toArray(ResourceLocation[]::new);;
    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(AetherIIModelLayers.SKYROOT_BED_HEAD);
        this.footRoot = context.bakeLayer(AetherIIModelLayers.SKYROOT_BED_FOOT);
    }

    @Override
    public void render(SkyrootBedBlockEntity bed, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, Vec3 cameraPos) {
        Level level = bed.getLevel();
        BlockState state = bed.getBlockState();
        if (level != null) {
            DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedBlockEntity> combineResult = DoubleBlockCombiner.combineWithNeigbour(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, ChestBlock.FACING, state, level, bed.getBlockPos(), (levelAccessor, pos) -> false);
            int i = combineResult.apply(new BrightnessCombiner<>()).get(combinedLight);
            this.renderPiece(poseStack, buffer, state.getValue(SkyrootBedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, state.getValue(SkyrootBedBlock.FACING), i, combinedOverlay, false, bed.getColor(), isDyed(state));
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, combinedLight, combinedOverlay, false, bed.getColor(), isDyed(state));
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, combinedLight, combinedOverlay, true, bed.getColor(), isDyed(state));
        }
    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource buffer, ModelPart model, Direction direction, int packedLight, int packedOverlay, boolean foot, DyeColor color, boolean dyed) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5625, foot ? -1.0 : 0.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
        poseStack.translate(-0.5, -0.5, -0.5);
        VertexConsumer vertexconsumer = dyed ? buffer.getBuffer(RenderType.entitySolid(DYED_BED_TEXTURES[color.getId()])) : buffer.getBuffer(RenderType.entitySolid(BED_LOCATION));
        model.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private boolean isDyed(BlockState state) {
        return state.getBlock() != AetherIIBlocks.SKYROOT_BED.get();
    }
}

