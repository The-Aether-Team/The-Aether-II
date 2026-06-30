package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import com.aetherteam.aetherii.network.packet.serverbound.MoaFlyModeChangePacket;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin extends AbstractClientPlayer {
    @Unique
    private int the_Aether_II$sprintCooldown;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/Input;tick(ZF)V", shift = At.Shift.BEFORE))
    private void aiStep(CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;
        ItemStack useStack = localPlayer.getUseItem();
        if (useStack.getItem() instanceof TieredCrossbowItem && AetherIIDataAttachments.get(localPlayer, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            if (!localPlayer.getAbilities().flying && !localPlayer.isSwimming() && !localPlayer.isPassenger()) {
                localPlayer.crouching = true;
            }
        }
    }

    @Inject(method = "sendIsSprintingIfNeeded", at = @At("HEAD"))
    private void sendIsSprintingIfNeeded(CallbackInfo ci) {
        if (this.getControlledVehicle() != null) {
            if (this.getControlledVehicle().onGround()) {
                this.the_Aether_II$sprintCooldown = 14;
            } else {
                boolean sprintKeyDown = Minecraft.getInstance().options.keySprint.isDown();
                if (this.the_Aether_II$sprintCooldown <= 0 && sprintKeyDown) {
                    this.the_Aether_II$sprintCooldown = 14;
                    ClientPacketDistributor.sendToServer(new MoaFlyModeChangePacket(this.getControlledVehicle().getId()));
                } else if (this.the_Aether_II$sprintCooldown > 0 && !sprintKeyDown) {
                    --this.the_Aether_II$sprintCooldown;
                }
            }
        }
    }
}
