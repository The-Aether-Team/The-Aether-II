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
    private void firstPersonRightAccessories(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, boolean hasSleeve, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.RIGHT;
    }

    @Inject(method = "renderLeftHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;ZLnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("HEAD"))
    private void firstPersonLeftAccessories(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, boolean hasSleeve, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.LEFT;
    }

    @WrapMethod(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Lnet/minecraft/client/model/geom/ModelPart;Z)V")
    private void renderHand(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, ModelPart arm, boolean hasSleeve, Operation<Void> original) {
        Player player = Minecraft.getInstance().player;
        AvatarRenderer playerRenderer = (AvatarRenderer) (Object) this;
        if (playerRenderer.getModel() instanceof PlayerModel playerModel) {
            if (!MixinHooks.RENDERING_ACCESSORY) {
                original.call(poseStack, submitNodeCollector, lightCoords, skinTexture, arm, hasSleeve);
            }
            if (currentArm != null) {
                AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                    if (((AccessoryItem) stack.getItem()).rendersInFirstPerson(stack)) {
                        for (RenderLayer<AvatarRenderState, PlayerModel> renderlayer : this.layers) {
                            if (renderlayer instanceof FirstPersonRendering firstPersonRendering) {
                                poseStack.pushPose();
                                firstPersonRendering.submitFirstPerson(currentArm, stack, player, poseStack, playerModel, submitNodeCollector, lightCoords);
                                poseStack.popPose();
                            }
                        }
                    }
                });
            }
        }
        currentArm = null;
    }

    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;", at = @At(value = "HEAD"), cancellable = true)
    private static void getArmPose(Avatar avatar, ItemStack itemInHand, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        IClientItemExtensions extensions = IClientItemExtensions.of(itemInHand);
        HumanoidModel.ArmPose armPose = extensions.getArmPose(avatar, hand, itemInHand);
        if (armPose == null) {
            if (avatar.getVehicle() instanceof CloudSkiff && !avatar.swinging && !(avatar.getUsedItemHand() == hand && avatar.getUseItemRemainingTicks() > 0)) {
                cir.setReturnValue(AetherIIArmPoses.SKIFF_SAILING);
            }
            if (!itemInHand.isEmpty()) {
                if (avatar.getUsedItemHand() != hand || avatar.getUseItemRemainingTicks() <= 0) {
                    if (!avatar.swinging && itemInHand.is(Tags.Items.TOOLS_CROSSBOW) && itemInHand.getItem() instanceof TieredCrossbowItem && TieredCrossbowItem.isCharged(itemInHand)) {
                        cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
                    }
                }
            }
        }
    }
}
