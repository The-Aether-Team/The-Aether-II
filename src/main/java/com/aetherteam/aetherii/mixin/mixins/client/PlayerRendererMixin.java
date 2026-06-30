package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.renderer.accessory.FirstPersonRendering;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Unique
    private static HumanoidArm aether_ii$currentArm;

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderRightHand", at = @At("HEAD"))
    private void aether_ii$setRightHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, CallbackInfo ci) {
        aether_ii$currentArm = HumanoidArm.RIGHT;
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"))
    private void aether_ii$setLeftHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, CallbackInfo ci) {
        aether_ii$currentArm = HumanoidArm.LEFT;
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void aether_ii$renderOnlyFirstPersonAccessories(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        if (MixinHooks.RENDERING_ACCESSORY) {
            this.aether_ii$renderFirstPersonAccessories(poseStack, buffer, packedLight, player);
            aether_ii$currentArm = null;
            ci.cancel();
        }
    }

    @Inject(method = "renderHand", at = @At("TAIL"))
    private void aether_ii$renderFirstPersonAccessories(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        this.aether_ii$renderFirstPersonAccessories(poseStack, buffer, packedLight, player);
        aether_ii$currentArm = null;
    }

    @Unique
    private void aether_ii$renderFirstPersonAccessories(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player) {
        HumanoidArm arm = aether_ii$currentArm;
        if (arm == null) {
            return;
        }
        ItemStack stack = AccessoryUtil.getFirst(player, AccessoryContainer.SlotType.HANDWEAR).orElse(ItemStack.EMPTY);
        if (!(stack.getItem() instanceof AccessoryItem accessoryItem) || !accessoryItem.rendersInFirstPerson(stack)) {
            return;
        }
        PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
        for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layer : this.layers) {
            if (layer instanceof FirstPersonRendering firstPersonRendering) {
                poseStack.pushPose();
                firstPersonRendering.renderOnFirstPerson(arm, stack, player, poseStack, playerModel, buffer, packedLight);
                poseStack.popPose();
            }
        }
    }
}
