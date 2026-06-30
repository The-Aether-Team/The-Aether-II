package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CopyBlockSpecialRenderer {
    public static void render(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ResourceLocation overlay) {
        BlockState copiedState = AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE);
        if (copiedState == null) {
            return;
        }

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(copiedState, poseStack, buffer, packedLight, packedOverlay);
        renderOverlay(poseStack, buffer, overlay);
    }

    public static void renderOverlay(PoseStack poseStack, MultiBufferSource buffer, ResourceLocation overlay) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(overlay.getNamespace(), "block/" + overlay.getPath()));
        VertexConsumer vertexConsumer = buffer.getBuffer(Sheets.cutoutBlockSheet());
        submitSurfaces(vertexConsumer, poseStack.last(), sprite, -0.001F, -0.001F, 1.001F, 1.001F, -0.001F, 1.001F);
    }

    private static void submitSurfaces(VertexConsumer vertexConsumer, PoseStack.Pose pose, TextureAtlasSprite sprite, float startX, float startZ, float endX, float endZ, float botY, float topY) {
        float minU = sprite.getU1();
        float maxU = sprite.getU0();
        float minV = sprite.getV1();
        float maxV = sprite.getV0();

        buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
        buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
        buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
        buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);

        buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
        buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
        buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
        buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);

        buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
        buildVertex(vertexConsumer, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
        buildVertex(vertexConsumer, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
        buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);

        buildVertex(vertexConsumer, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
        buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
        buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
        buildVertex(vertexConsumer, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);

        buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
        buildVertex(vertexConsumer, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
        buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
        buildVertex(vertexConsumer, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);

        buildVertex(vertexConsumer, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
        buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
        buildVertex(vertexConsumer, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
        buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
    }

    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.vertex(pose.pose(), x, y, z)
                .color(0xFF, 0xFF, 0xFF, 0xAA)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(pose.normal(), normalX, normalY, normalZ)
                .endVertex();
    }

    public record Unbaked(Holder<Block> block, ResourceLocation overlay) {
    }
}
