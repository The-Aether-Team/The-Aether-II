package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    @WrapMethod(method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Ljava/util/List;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/function/Function;ZI)V")
    public void tesselateBlock(BlockAndTintGetter blockAndTintGetter, List<BlockModelPart> modelParts, BlockState state, BlockPos pos, PoseStack poseStack, Function<ChunkSectionLayer, VertexConsumer> bufferLookup, boolean checkSides, int packedOverlay, Operation<Void> original) {
        if (MixinHooks.RENDERING_BREAKING_TEXTURE) {
            List<BlockModelPart> editedModelParts = new ArrayList<>();
            Direction[] directions = Arrays.copyOfRange(Direction.values(), 0, 7);
            if (state.is(AetherIIBlocks.AETHER_GRASS_BLOCK)) {
                for (BlockModelPart modelPart : modelParts) {
                    if (modelPart instanceof SimpleModelWrapper wrapper) {
                        QuadCollection.Builder builder = new QuadCollection.Builder();
                        for (Direction side : directions) {
                            for (BakedQuad quad : wrapper.getQuads(side)) {
                                if (side == Direction.DOWN || quad.tintIndex() == 2) {
                                    if (side == null) {
                                        builder.addUnculledFace(quad);
                                    } else {
                                        builder.addCulledFace(side, quad);
                                    }
                                }
                            }
                        }
                        editedModelParts.add(new SimpleModelWrapper(builder.build(), wrapper.useAmbientOcclusion(), wrapper.particleIcon(), wrapper.renderType()));
                    }
                }
            }
            original.call(blockAndTintGetter, editedModelParts, state, pos, poseStack, bufferLookup, checkSides, packedOverlay);
        } else {
            original.call(blockAndTintGetter, modelParts, state, pos, poseStack, bufferLookup, checkSides, packedOverlay);
        }
    }
}
