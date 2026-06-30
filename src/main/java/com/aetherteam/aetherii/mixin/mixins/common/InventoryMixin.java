package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Shadow
    @Final
    public Player player;

    @Inject(method = "clearOrCountMatchingItems(Ljava/util/function/Predicate;ILnet/minecraft/world/Container;)I", at = @At(value = "RETURN"), cancellable = true)
    public void clearOrCountMatchingItems(Predicate<ItemStack> predicate, int amountToRemove, Container craftSlots, CallbackInfoReturnable<Integer> ci) {
        boolean countingOnly = amountToRemove == 0;
        Container container = AetherIIDataAttachments.get(this.player, AetherIIDataAttachments.ACCESSORIES);
        int count = ContainerHelper.clearOrCountMatchingItems(container, predicate, amountToRemove - ci.getReturnValue(), countingOnly);
        ci.setReturnValue(ci.getReturnValue() + count);
    }
}
