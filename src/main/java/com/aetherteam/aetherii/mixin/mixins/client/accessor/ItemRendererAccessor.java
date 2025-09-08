package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Invoker
    static void callRenderModelLists(BakedModel p_115190_, int[] p_387364_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
        throw new UnsupportedOperationException();
    }
}
