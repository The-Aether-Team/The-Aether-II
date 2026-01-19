package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(BlockRenderDispatcher.class)
public class BlockRenderDispatcherMixin {
    @Inject(at = @At(value = "HEAD"), method = "renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V")
    private void renderBreakingTextureHead(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, CallbackInfo ci) {
        MixinHooks.RENDERING_BREAKING_TEXTURE = true;
    }

    @Inject(at = @At(value = "RETURN"), method = "renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V")
    private void renderBreakingTextureTail(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, CallbackInfo ci) {
        MixinHooks.RENDERING_BREAKING_TEXTURE = false;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Ljava/util/List;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/function/Function;ZI)V", shift = At.Shift.BEFORE), method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/function/Function;ZLjava/util/List;)V")
    private void tesselateWithAO(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, Function<ChunkSectionLayer, VertexConsumer> vertexConsumer, boolean checkSides, List<BlockModelPart> parts, CallbackInfo ci) {
        BlockRenderDispatcher renderer = (BlockRenderDispatcher) (Object) this;
        if (AetherGrassBlock.plantIsSnowed(state)) {
            BlockState snow = AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState();
            List<BlockModelPart> snowParts = renderer.getBlockModel(snow).collectParts(level, pos, state, RandomSource.create(state.getSeed(pos)));
            renderer.getModelRenderer().tesselateBlock(level, snowParts, snow, pos, poseStack, vertexConsumer, checkSides, OverlayTexture.NO_OVERLAY);
        }
    }
}
