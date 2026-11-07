package com.aetherteam.aetherii.attachment.level;

import com.aetherteam.aetherii.network.packet.clientbound.AlkahestFizzPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlkahestBreakageAttachment {
    private final HashMap<BlockPos, BreakageData> blockBreakage = new HashMap<>();

    public void postTickUpdate(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            if (!this.blockBreakage.isEmpty()) {
                for (Iterator<Map.Entry<BlockPos, BreakageData>> iterator = this.blockBreakage.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<BlockPos, BreakageData> entry = iterator.next();
                    BlockPos key = entry.getKey();
                    BreakageData value = entry.getValue();
                    StaticData staticData = entry.getValue().staticData();
                    if (!level.getFluidState(staticData.sourcePos()).isEmpty()) {
                        if (server.getTickCount() % staticData.timeDelay() == 0 && server.getTickCount() >= staticData.initialTime() + staticData.timeDelay()) {
                            int destroyProgress = value.destroyProgress() + staticData.destroySpeed();
                            if (destroyProgress >= 9) {
                                if (!staticData.drop()) {
                                    serverLevel.setBlock(staticData.sourcePos(), Blocks.AIR.defaultBlockState(), 3);
                                }
                                serverLevel.destroyBlock(key, staticData.drop());
                                iterator.remove();
                                for (ServerPlayer player : serverLevel.players()) {
                                    player.connection.send(new ClientboundBlockDestructionPacket(key.hashCode(), key, -1));
                                }
                                PacketDistributor.sendToPlayersInDimension(serverLevel, new AlkahestFizzPacket(key, Direction.DOWN, new Vec3(0, 0.5, 0)));
                            } else {
                                this.blockBreakage.put(key, new BreakageData(destroyProgress, staticData));
                                for (ServerPlayer player : serverLevel.players()) {
                                    player.connection.send(new ClientboundBlockDestructionPacket(key.hashCode(), key, destroyProgress));
                                }
                            }
                        }
                    } else {
                        for (ServerPlayer player : serverLevel.players()) {
                            player.connection.send(new ClientboundBlockDestructionPacket(key.hashCode(), key, -1));
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }

    public HashMap<BlockPos, BreakageData> getBlockBreakage() {
        return this.blockBreakage;
    }

    public record BreakageData(int destroyProgress, StaticData staticData) {

    }

    public record StaticData(BlockPos sourcePos, int destroySpeed, boolean drop, int initialTime, int timeDelay) {

    }
}
