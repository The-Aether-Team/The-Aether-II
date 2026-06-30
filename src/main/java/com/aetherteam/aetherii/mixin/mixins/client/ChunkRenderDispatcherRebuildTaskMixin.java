package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Set;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.renderer.chunk.ChunkRenderDispatcher$RenderChunk$RebuildTask")
public class ChunkRenderDispatcherRebuildTaskMixin {
    @Inject(method = "compile(FFFLnet/minecraft/client/renderer/ChunkBufferBuilderPack;)Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask$CompileResults;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", shift = At.Shift.BEFORE, remap = false))
    private void renderSnowOnSnowyPlants(float cameraX, float cameraY, float cameraZ, ChunkBufferBuilderPack builders, CallbackInfoReturnable<?> cir, @Local(ordinal = 0) BlockState state, @Local(ordinal = 2) BlockPos pos, @Local RenderChunkRegion region, @Local PoseStack poseStack, @Local BlockRenderDispatcher dispatcher, @Local Set<RenderType> renderedLayers, @Local RandomSource random) {
        if (AetherGrassBlock.plantIsSnowed(state)) {
            BlockState snow = AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState();
            RenderType renderType = ItemBlockRenderTypes.getChunkRenderType(snow);
            BufferBuilder buffer = builders.builder(renderType);
            if (renderedLayers.add(renderType)) {
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
            }
            dispatcher.renderBatched(snow, pos, region, poseStack, buffer, true, random, ModelData.EMPTY, renderType);
        }
    }
}
