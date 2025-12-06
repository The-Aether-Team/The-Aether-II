package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.EmptyBlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;

public class LockedBlockSpecialRenderer implements SpecialModelRenderer<BlockState> {
    @Override
    public void render(@Nullable BlockState blockState, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, boolean partialTick) {
        BlockAndTintGetter world = Minecraft.getInstance().level;
        if (blockState != null && world != null) {
            poseStack.pushPose();
            ModelBlockRenderer.renderModel(poseStack.last(), multiBufferSource, Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState), 1.0F, 1.0F, 1.0F, packedLight, packedOverlay, EmptyBlockAndTintGetter.INSTANCE, BlockPos.ZERO, Blocks.AIR.defaultBlockState());
            drawSurfaces(multiBufferSource, poseStack.last(), -0.001F, -0.001F, 1.001F, 1.001F, -0.001F, 1.001F);
            poseStack.popPose();
        }
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
        PoseStack posestack = new PoseStack();
        Vector3f[] cubeVectors = {
                new Vector3f(16, 0, 16),
                new Vector3f(16, 0, 0),
                new Vector3f(16, 16, 0),
                new Vector3f(16, 16, 16),
                new Vector3f(0, 0, 16),
                new Vector3f(16, 0, 16),
                new Vector3f(16, 16, 16),
                new Vector3f(0, 16, 16)
        };
        for (int i = 0; i < 8; i++) {
            Vector3f vertex = cubeVectors[i];
            float f = vertex.x() / 16.0F;
            float f1 = vertex.y() / 16.0F;
            float f2 = vertex.z() / 16.0F;
            Vector3f vector3f = posestack.last().pose().transformPosition(f, f1, f2, new Vector3f());
            set.add(vector3f);
        }
    }

    private static void drawSurfaces(MultiBufferSource buffer, PoseStack.Pose pose, float startX, float startZ, float endX, float endZ, float botY, float topY) {
        VertexConsumer builder = buffer.getBuffer(RenderType.cutout());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/dungeon_lock"));

        if (sprite != null) {
            float minU = sprite.getU1();
            float maxU = sprite.getU0();
            float minV = sprite.getV1();
            float maxV = sprite.getV0();

            buildVertex(builder, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
            buildVertex(builder, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
            buildVertex(builder, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
            buildVertex(builder, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);

            buildVertex(builder, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
            buildVertex(builder, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
            buildVertex(builder, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
            buildVertex(builder, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);

            buildVertex(builder, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
            buildVertex(builder, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
            buildVertex(builder, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
            buildVertex(builder, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);

            buildVertex(builder, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
            buildVertex(builder, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
            buildVertex(builder, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
            buildVertex(builder, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);

            buildVertex(builder, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
            buildVertex(builder, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
            buildVertex(builder, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
            buildVertex(builder, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);

            buildVertex(builder, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
            buildVertex(builder, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
            buildVertex(builder, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
            buildVertex(builder, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
        }
    }

    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.addVertex(pose, x, y, z).setColor(0xFF, 0xFF, 0xFF, 0xAA).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, normalX, normalY, normalZ);
    }

    @Override
    public @Nullable BlockState extractArgument(ItemStack itemStack) {
        return itemStack.get(AetherIIDataComponents.BLOCK_STATE);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<LockedBlockSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public MapCodec<LockedBlockSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            return new LockedBlockSpecialRenderer();
        }
    }
}
