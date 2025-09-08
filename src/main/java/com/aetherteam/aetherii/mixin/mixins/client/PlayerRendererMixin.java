package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;", at = @At(value = "HEAD"), cancellable = true)
    private static void getArmPose(Player player, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        IClientItemExtensions extensions = IClientItemExtensions.of(stack);
        HumanoidModel.ArmPose armPose = extensions.getArmPose(player, hand, stack);
        if (armPose == null) {
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
