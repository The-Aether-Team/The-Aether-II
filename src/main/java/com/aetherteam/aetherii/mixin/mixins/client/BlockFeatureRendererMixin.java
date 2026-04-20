package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.renderer.block.model.blockstate.BreakingFixModel;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.feature.BlockFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BlockFeatureRenderer.class)
public class BlockFeatureRendererMixin {
    @WrapOperation(method = "renderBreakingBlockModelSubmits(Lnet/minecraft/client/renderer/SubmitNodeCollection;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;collectParts(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;Ljava/util/List;)V"))
    private static void renderBreakingTexture(BlockStateModel instance, BlockAndTintGetter blockAndTintGetter, BlockPos pos, BlockState state, RandomSource randomSource, List<BlockStateModelPart> list, Operation<Void> original) {
        if (instance instanceof BreakingFixModel breakingFixModel) {
            breakingFixModel.collectBreakingParts(blockAndTintGetter, pos, state, randomSource, list);
        } else {
            original.call(instance, blockAndTintGetter, pos, state, randomSource, list);
        }
    }
}
