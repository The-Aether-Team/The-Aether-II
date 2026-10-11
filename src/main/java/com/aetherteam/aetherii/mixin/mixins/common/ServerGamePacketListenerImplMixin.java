package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.api.entity.CustomPickItemEntity;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.network.protocol.game.ServerboundPickItemFromEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Inject(
        method = "handlePickItemFromEntity(Lnet/minecraft/network/protocol/game/ServerboundPickItemFromEntityPacket;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPickResult()Lnet/minecraft/world/item/ItemStack;"),
        cancellable = true,
        require = 1)
    protected void handlePickItemFromEntity(ServerboundPickItemFromEntityPacket packet, CallbackInfo callbackInfo) {
        ServerPlayer player = ((ServerGamePacketListenerImpl)(Object)this).player;
        ServerLevel serverlevel = player.level();
        @SuppressWarnings("deprecation")
        Entity entity = serverlevel.getEntityOrPart(packet.id());
        if (entity instanceof CustomPickItemEntity customPickItemEntity) {
            ItemStack itemstack = customPickItemEntity.getPickResult(player, packet.includeData());
            if (itemstack != null) {
                ((ServerGamePacketListenerImplAccessor)(Object)this).aether_ii$tryPickItem(itemstack);
                callbackInfo.cancel();
            }
        }
    }
}
