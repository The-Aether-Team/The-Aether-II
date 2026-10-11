package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.PlayerRideableInstantJumping;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.aetherteam.aetherii.network.packet.serverbound.MoaFlyModeChangePacket;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow private boolean crouching;

    @Shadow
    public ClientInput input;

    @Shadow
    protected abstract void sendRidingJump();

    @Unique
    private int the_Aether_II$sprintCooldown;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/ClientInput;tick()V", shift = At.Shift.BEFORE))
    private void aiStep(CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;
        ItemStack useStack = localPlayer.getUseItem();
        if (useStack.getItem() instanceof TieredCrossbowItem && localPlayer.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            if (!localPlayer.getAbilities().flying && !localPlayer.isSwimming() && !localPlayer.isPassenger()) {
                this.crouching = true;
            }
        }
    }

    @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;jumpableVehicle()Lnet/minecraft/world/entity/PlayerRideableJumping;", shift = At.Shift.BEFORE))
    private void aiStepBeforeJumpVehicle(CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;

        boolean wasJumping = this.input.keyPresses.jump();
        @Nullable PlayerRideableInstantJumping jumpableVehicle = this.jumpableInstantVehicle();
        if (jumpableVehicle != null && jumpableVehicle.getJumpCooldown() == 0) {

            if (wasJumping) {
                jumpableVehicle.onPlayerJump();
                this.sendRidingJump();
            }
        }
    }


    public @Nullable PlayerRideableInstantJumping jumpableInstantVehicle() {
        Entity var2 = this.getControlledVehicle();
        PlayerRideableInstantJumping var10000;
        if (var2 instanceof PlayerRideableInstantJumping playerRideableInstantJumping) {
            if (playerRideableInstantJumping.canJump()) {
                var10000 = playerRideableInstantJumping;
                return var10000;
            }
        }

        var10000 = null;
        return var10000;
    }


    @Inject(method = "sendIsSprintingIfNeeded", at = @At(value = "HEAD"))
    private void sendIsSprintingIfNeeded(CallbackInfo ci) {
        if (this.getControlledVehicle() != null) {
            if (this.getControlledVehicle().onGround()) {
                this.the_Aether_II$sprintCooldown = 14;
            } else if (!this.getControlledVehicle().onGround()) {
                if (this.the_Aether_II$sprintCooldown <= 0 && this.input.keyPresses.sprint()) {
                    this.the_Aether_II$sprintCooldown = 14;
                    ClientPacketDistributor.sendToServer(new MoaFlyModeChangePacket(this.getControlledVehicle().getId()));
                } else if (this.the_Aether_II$sprintCooldown > 0 && !this.input.keyPresses.sprint()) {
                    --this.the_Aether_II$sprintCooldown;
                }
            }
        }
    }
}
