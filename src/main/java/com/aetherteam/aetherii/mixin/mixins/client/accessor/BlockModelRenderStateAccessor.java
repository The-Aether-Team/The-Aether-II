package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BlockModelRenderState.class)
public interface BlockModelRenderStateAccessor {
    @Accessor("modelParts")
    List<BlockStateModelPart> aether_ii$getModelParts();

    @Accessor("modelParts")
    void aether_ii$setModelParts(List<BlockStateModelPart> modelParts);
}
