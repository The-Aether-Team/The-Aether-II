package com.aetherteam.aetherii.mixin.mixins.debug;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

//@Mixin(DebugPackets.class)
public class DebugPacketsMixin { //TODO
//    @Inject(at = @At(value = "HEAD"), method = "sendPathFindingPacket(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/level/pathfinder/Path;F)V")
//    private static void sendPathFindingPacket(Level level, Mob mob, @Nullable Path path, float maxDistanceToWaypoint, CallbackInfo ci) {
//        if (AetherII.DEBUG_MODE) {
//            if (!level.isClientSide() && path != null && path.debugData() != null) {
//                PathfindingDebugPayload packet = new PathfindingDebugPayload(mob.getId(), path, maxDistanceToWaypoint);
//                aether$sendPacketToAllPlayers((ServerLevel) level, packet);
//            }
//        }
//    }
//
//    @Unique
//    private static void aether$sendPacketToAllPlayers(ServerLevel level, CustomPacketPayload payload) {
//        Packet<?> packet = new ClientboundCustomPayloadPacket(payload);
//        for (ServerPlayer serverplayer : level.players()) {
//            serverplayer.connection.send(packet);
//        }
//    }
}
