package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Invoker("renderQuadList")
    static void callRenderModelLists(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, int[] tintLayers, int packedLight, int packedOverlay) {
        throw new UnsupportedOperationException();
    }
}
