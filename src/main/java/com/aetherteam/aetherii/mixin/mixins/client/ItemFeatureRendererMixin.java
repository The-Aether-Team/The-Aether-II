package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemFeatureRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"))
    private void renderIrradiatedGlint(ItemRenderer instance, BakedModel model, ItemStack stack, int combinedLight, int combinedOverlay, PoseStack poseStack, VertexConsumer vertexConsumer, Operation<Void> original, ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack originalPoseStack, MultiBufferSource buffer, int originalCombinedLight, int originalCombinedOverlay, BakedModel originalModel) {
        model = this.aether_ii$displayModel(model, stack, displayContext);
        original.call(instance, model, stack, combinedLight, combinedOverlay, poseStack, vertexConsumer);
        if (!stack.hasFoil() && stack.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            original.call(instance, model, stack, combinedLight, combinedOverlay, poseStack, buffer.getBuffer(AetherIIRenderTypes.irradiatedGlint()));
        }
    }

    private BakedModel aether_ii$displayModel(BakedModel model, ItemStack stack, ItemDisplayContext displayContext) {
        if (stack.getItem() instanceof AercloudGliderItem
                && (displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.GROUND || displayContext == ItemDisplayContext.FIXED)) {
            return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack.getItem());
        }
        return model;
    }
}
