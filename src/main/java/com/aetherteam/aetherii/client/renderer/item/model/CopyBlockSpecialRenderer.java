package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Set;
import java.util.function.Consumer;

//public class CopyBlockSpecialRenderer implements SpecialModelRenderer<BlockState> {
//    private final Holder<Block> block;
//    private final Identifier overlay;
//
//    public CopyBlockSpecialRenderer(Holder<Block> block, Identifier overlay) {
//        this.block = block;
//        this.overlay = overlay;
//    }
//
//    @Override
//    public void submit(@Nullable BlockState blockState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, int packedOverlay, boolean b, int i2) {
////        BlockAndTintGetter world = Minecraft.getInstance().level; //TODO
////        if (blockState != null && world != null) {
////            poseStack.pushPose();
////            ModelBlockRenderer.renderModel(poseStack.last(), submitNodeCollector, Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState), 1.0F, 1.0F, 1.0F, packedLight, packedOverlay, EmptyBlockAndTintGetter.INSTANCE, BlockPos.ZERO, this.block.value().defaultBlockState());
////            this.drawSurfaces(submitNodeCollector, poseStack.last(), -0.001F, -0.001F, 1.001F, 1.001F, -0.001F, 1.001F);
////            poseStack.popPose();
////        }
//    }
//
//    @Override
//    public void getExtents(Consumer<Vector3fc> consumer) {
//        PoseStack posestack = new PoseStack();
//        Vector3f[] cubeVectors = {
//                new Vector3f(16, 0, 16),
//                new Vector3f(16, 0, 0),
//                new Vector3f(16, 16, 0),
//                new Vector3f(16, 16, 16),
//                new Vector3f(0, 0, 16),
//                new Vector3f(16, 0, 16),
//                new Vector3f(16, 16, 16),
//                new Vector3f(0, 16, 16)
//        };
//        for (int i = 0; i < 8; i++) {
//            Vector3f vertex = cubeVectors[i];
//            float f = vertex.x() / 16.0F;
//            float f1 = vertex.y() / 16.0F;
//            float f2 = vertex.z() / 16.0F;
//            Vector3f vector3f = posestack.last().pose().transformPosition(f, f1, f2, new Vector3f());
//            consumer.accept(vector3f);
//        }
//    }
//
////    private void drawSurfaces(MultiBufferSource buffer, PoseStack.Pose pose, float startX, float startZ, float endX, float endZ, float botY, float topY) { //TODO
////        VertexConsumer builder = buffer.getBuffer(RenderType.cutout());
////        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(this.overlay);
////
////        if (sprite != null) {
////            float minU = sprite.getU1();
////            float maxU = sprite.getU0();
////            float minV = sprite.getV1();
////            float maxV = sprite.getV0();
////
////            buildVertex(builder, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
////            buildVertex(builder, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
////            buildVertex(builder, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
////            buildVertex(builder, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);
////
////            buildVertex(builder, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
////            buildVertex(builder, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
////            buildVertex(builder, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
////            buildVertex(builder, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);
////
////            buildVertex(builder, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
////            buildVertex(builder, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
////            buildVertex(builder, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
////            buildVertex(builder, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);
////
////            buildVertex(builder, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
////            buildVertex(builder, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
////            buildVertex(builder, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
////            buildVertex(builder, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);
////
////            buildVertex(builder, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
////            buildVertex(builder, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
////            buildVertex(builder, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
////            buildVertex(builder, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);
////
////            buildVertex(builder, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
////            buildVertex(builder, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
////            buildVertex(builder, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
////            buildVertex(builder, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
////        }
////    }
//
//    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
//        builder.addVertex(pose, x, y, z).setColor(0xFF, 0xFF, 0xFF, 0xAA).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, normalX, normalY, normalZ);
//    }
//
//    @Override
//    public @Nullable BlockState extractArgument(ItemStack itemStack) {
//        return itemStack.get(AetherIIDataComponents.BLOCK_STATE);
//    }
//
//    public record Unbaked(Holder<Block> block, Identifier overlay) implements NoDataSpecialModelRenderer.Unbaked {
//        public static final MapCodec<CopyBlockSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
//                BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(CopyBlockSpecialRenderer.Unbaked::block),
//                Identifier.CODEC.fieldOf("overlay").forGetter(CopyBlockSpecialRenderer.Unbaked::overlay)
//        ).apply(instance, CopyBlockSpecialRenderer.Unbaked::new));
//
//        @Override
//        public MapCodec<CopyBlockSpecialRenderer.Unbaked> type() {
//            return MAP_CODEC;
//        }
//
//        @Override
//        public CopyBlockSpecialRenderer bake(BakingContext context) {
//            return new CopyBlockSpecialRenderer(this.block(), this.overlay());
//        }
//    }
//}
