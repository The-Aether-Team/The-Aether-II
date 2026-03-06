package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.AetherIIArmPoses;
import com.aetherteam.aetherii.client.renderer.accessory.FirstPersonRendering;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, AvatarRenderState, PlayerModel> {
    @Unique
    private static HumanoidArm currentArm = null;

    public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;ZLnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("HEAD"))
    private void firstPersonRightAccessories(PoseStack p_446962_, SubmitNodeCollector p_445938_, int p_445470_, Identifier p_445487_, boolean p_446672_, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.RIGHT;
    }

    @Inject(method = "renderLeftHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;ZLnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("HEAD"))
    private void firstPersonLeftAccessories(PoseStack p_445618_, SubmitNodeCollector p_447076_, int p_446116_, Identifier p_446675_, boolean p_446755_, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.LEFT;
    }

    @WrapMethod(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Lnet/minecraft/client/model/geom/ModelPart;Z)V")
    private void renderHand(PoseStack p_447067_, SubmitNodeCollector p_446294_, int p_447015_, Identifier p_467332_, ModelPart p_447044_, boolean p_447011_, Operation<Void> original) {
//        Player player = Minecraft.getInstance().player; //TODO
//        AvatarRenderer playerRenderer = (AvatarRenderer) (Object) this;
//        PlayerModel playerModel = playerRenderer.getModel();
//        if (!MixinHooks.RENDERING_ACCESSORY) {
//            original.call(poseStack, buffer, packedLight, skinTexture, arm, isSleeveVisible);
//        }
//        if (currentArm != null) {
//            AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
//                if (((AccessoryItem) stack.getItem()).rendersInFirstPerson(stack)) {
//                    for (RenderLayer<AvatarRenderState, PlayerModel> renderlayer : this.layers) {
//                        if (renderlayer instanceof FirstPersonRendering firstPersonRendering) {
//                            poseStack.pushPose();
//                            firstPersonRendering.renderOnFirstPerson(currentArm, stack, player, poseStack, playerModel, buffer, packedLight);
//                            poseStack.popPose();
//                        }
//                    }
//                }
//            });
//        }
        currentArm = null;
    }

    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;", at = @At(value = "HEAD"), cancellable = true)
    private static void getArmPose(Avatar player, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        IClientItemExtensions extensions = IClientItemExtensions.of(stack);
        HumanoidModel.ArmPose armPose = extensions.getArmPose(player, hand, stack);
        if (armPose == null) {
            if (player.getVehicle() instanceof CloudSkiff && !player.swinging && !(player.getUsedItemHand() == hand && player.getUseItemRemainingTicks() > 0)) {
                cir.setReturnValue(AetherIIArmPoses.SKIFF_SAILING);
            }
            if (!stack.isEmpty()) {
                if (player.getUsedItemHand() != hand || player.getUseItemRemainingTicks() <= 0) {
                    if (!player.swinging && stack.is(Tags.Items.TOOLS_CROSSBOW) && stack.getItem() instanceof TieredCrossbowItem && TieredCrossbowItem.isCharged(stack)) {
                        cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
                    }
                }
            }
        }
    }
}
