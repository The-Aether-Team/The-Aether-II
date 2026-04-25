package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.block.model.blockstate.CopyBlockModel;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.BlockModelRenderStateAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CopyBlockSpecialRenderer implements SpecialModelRenderer<BlockState> {
    private final SpriteGetter spriteGetter;
    private final Holder<Block> block;
    private final Identifier overlay;

    public CopyBlockSpecialRenderer(SpriteGetter spriteGetter, Holder<Block> block, Identifier overlay) {
        this.spriteGetter = spriteGetter;
        this.block = block;
        this.overlay = overlay;
    }

    @Override
    public @Nullable BlockState extractArgument(ItemStack itemStack) {
        return itemStack.get(AetherIIDataComponents.BLOCK_STATE);
    }

    @Override
    public void submit(@Nullable BlockState blockState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        if (blockState != null) {
            BlockModelRenderState copyRenderState = new BlockModelRenderState();
            Minecraft.getInstance().getBlockModelResolver().update(copyRenderState, blockState, BlockDisplayContext.create());

            List<BlockStateModelPart> newParts = new ArrayList<>();
            if (Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(this.block.value().defaultBlockState()) instanceof CopyBlockModel copyBlockModel) {
                copyBlockModel.collectCopyParts(((BlockModelRenderStateAccessor) copyRenderState).aether_ii$getModelParts(), newParts);
                ((BlockModelRenderStateAccessor) copyRenderState).aether_ii$setModelParts(newParts);
            }
            copyRenderState.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);

            submitNodeCollector.submitCustomGeometry(poseStack, Sheets.cutoutBlockSheet(), this.submitSurfaces(-0.001F, -0.001F, 1.001F, 1.001F, -0.001F, 1.001F));
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
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
            consumer.accept(vector3f);
        }
    }

    private SubmitNodeCollector.CustomGeometryRenderer submitSurfaces(float startX, float startZ, float endX, float endZ, float botY, float topY) {
        return new SubmitNodeCollector.CustomGeometryRenderer() {
            @Override
            public void render(PoseStack.Pose pose, VertexConsumer vertexConsumer) {
                TextureAtlasSprite sprite = CopyBlockSpecialRenderer.this.spriteGetter.get(Sheets.BLOCKS_MAPPER.apply(CopyBlockSpecialRenderer.this.overlay));

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
        };
    }

    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.addVertex(pose, x, y, z).setColor(0xFF, 0xFF, 0xFF, 0xAA).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, normalX, normalY, normalZ);
    }

    public record Unbaked(Holder<Block> block, Identifier overlay) implements SpecialModelRenderer.Unbaked<BlockState> {
        public static final MapCodec<CopyBlockSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(CopyBlockSpecialRenderer.Unbaked::block),
                Identifier.CODEC.fieldOf("overlay").forGetter(CopyBlockSpecialRenderer.Unbaked::overlay)
        ).apply(instance, CopyBlockSpecialRenderer.Unbaked::new));

        @Override
        public MapCodec<CopyBlockSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public CopyBlockSpecialRenderer bake(BakingContext bakingContext) {
            return new CopyBlockSpecialRenderer(bakingContext.sprites(), this.block(), this.overlay());
        }
    }
}
