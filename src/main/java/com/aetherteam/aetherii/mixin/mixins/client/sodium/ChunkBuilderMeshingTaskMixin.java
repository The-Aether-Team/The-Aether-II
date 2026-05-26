package com.aetherteam.aetherii.mixin.mixins.client.sodium;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask")
public class ChunkBuilderMeshingTaskMixin {
    @WrapOperation(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V",
            remap = false
        ),
        remap = false
    )
    private void aetherii$renderSnowOverlay(@Coerce Object instance, BlockStateModel model, BlockState state, BlockPos pos, BlockPos origin, Operation<Void> original, @Local BlockRenderCache cache) {
        original.call(instance, model, state, pos, origin);
        if (AetherGrassBlock.plantIsSnowed(state)) {
            BlockState snow = AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState();
            BlockStateModel snowModel = cache.getBlockModels().get(snow);
            original.call(instance, snowModel, snow, pos, origin);
        }
    }
}
