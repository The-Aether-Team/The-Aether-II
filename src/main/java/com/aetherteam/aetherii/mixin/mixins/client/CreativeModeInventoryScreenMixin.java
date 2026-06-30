package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.network.ClientPacketDistributor;
import com.aetherteam.aetherii.network.packet.serverbound.ClearAccessoriesPacket;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
    @Inject(method = "slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;getItems()Lnet/minecraft/core/NonNullList;"))
    private void slotClicked(Slot slot, int slotId, int buttonNum, ClickType clickType, CallbackInfo ci) {
        ClientPacketDistributor.sendToServer(new ClearAccessoriesPacket());
    }
}
