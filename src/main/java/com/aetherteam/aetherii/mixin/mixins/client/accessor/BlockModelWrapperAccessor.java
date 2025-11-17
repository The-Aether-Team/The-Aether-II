package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BlockModelWrapper.class)
public interface BlockModelWrapperAccessor {
    @Accessor("quads")
    List<BakedQuad> aether_ii$getQuads();

    @Mutable
    @Accessor("quads")
    void aether_ii$setQuads(List<BakedQuad> quads);
}
