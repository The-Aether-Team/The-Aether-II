package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.AetherIIArmPoses;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin<S extends AvatarRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> {
    @Inject(method = "submitArmWithItem(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmWithItem(S renderState, ItemStackRenderState p_434802_, ItemStack p_455869_, HumanoidArm arm, PoseStack p_435466_, SubmitNodeCollector p_433358_, int p_436055_, CallbackInfo ci) {
        HumanoidArm posedArm = null;
        if (renderState.rightArmPose == AetherIIArmPoses.GLIDING) {
            posedArm = HumanoidArm.RIGHT;
        } else if (renderState.leftArmPose == AetherIIArmPoses.GLIDING) {
            posedArm = HumanoidArm.LEFT;
        }
        if (posedArm != null && posedArm != arm) {
            ci.cancel();
        }
    }
}
