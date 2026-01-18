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
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
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

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {
    @Unique
    private static HumanoidArm currentArm = null;

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;ZLnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("HEAD"))
    private void firstPersonRightAccessories(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ResourceLocation skinTexture, boolean isSleeveVisible, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.RIGHT;
    }

    @Inject(method = "renderLeftHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;ZLnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("HEAD"))
    private void firstPersonLeftAccessories(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ResourceLocation skinTexture, boolean isSleeveVisible, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.LEFT;
    }

    @WrapMethod(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/model/geom/ModelPart;Z)V")
    private void renderHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation skinTexture, ModelPart arm, boolean isSleeveVisible, Operation<Void> original) {
        Player player = Minecraft.getInstance().player;
        PlayerRenderer playerRenderer = (PlayerRenderer) (Object) this;
        PlayerModel playerModel = playerRenderer.getModel();
        if (!MixinHooks.RENDERING_ACCESSORY) {
            original.call(poseStack, buffer, packedLight, skinTexture, arm, isSleeveVisible);
        }
        if (currentArm != null) {
            AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                if (((AccessoryItem) stack.getItem()).rendersInFirstPerson(stack)) {
                    for (RenderLayer<PlayerRenderState, PlayerModel> renderlayer : this.layers) {
                        if (renderlayer instanceof FirstPersonRendering firstPersonRendering) {
                            poseStack.pushPose();
                            firstPersonRendering.renderOnFirstPerson(currentArm, stack, player, poseStack, playerModel, buffer, packedLight);
                            poseStack.popPose();
                        }
                    }
                }
            });
        }
        currentArm = null;
    }

    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;", at = @At(value = "HEAD"), cancellable = true)
    private static void getArmPose(Player player, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
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
