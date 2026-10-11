package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerGamePacketListenerImpl.class)
public interface ServerGamePacketListenerImplAccessor {
    @Accessor("aboveGroundTickCount")
    void aether_ii$setAboveGroundTickCount(int aboveGroundTickCount);

    @Accessor("aboveGroundVehicleTickCount")
    void aether_ii$setAboveGroundVehicleTickCount(int aboveGroundVehicleTickCount);

    @Invoker("tryPickItem")
    void aether_ii$tryPickItem(ItemStack stack);
}