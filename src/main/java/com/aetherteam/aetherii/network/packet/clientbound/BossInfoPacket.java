package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

/**
 * Packets to help sync the server's Aether boss bars with the client's.
 */
public abstract class BossInfoPacket implements CustomPacketPayload {
    protected final UUID bossEvent;
    protected final int entityID;

    public BossInfoPacket(UUID bossEvent, int entityID) {
        this.bossEvent = bossEvent;
        this.entityID = entityID;
    }

    /**
     * Adds a boss bar for the client.
     */
    public static class Display extends BossInfoPacket {
        public static final Type<Display> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "add_custom_bossbar"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Display> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            Display::getBossEvent,
            ByteBufCodecs.INT,
            Display::getEntityID,
            Display::new);

        public Display(UUID bossEvent, int entityID) {
            super(bossEvent, entityID);
        }

        @Override
        public Type<Display> type() {
            return TYPE;
        }

        public static void execute(Display payload, IPayloadContext context) {
            RenderHooks.BOSS_EVENTS.put(payload.bossEvent, payload.entityID);
        }
    }

    /**
     * Removes a boss bar for the client.
     */
    public static class Remove extends BossInfoPacket {
        public static final Type<Remove> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "remove_custom_bossbar"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            Remove::getBossEvent,
            ByteBufCodecs.INT,
            Remove::getEntityID,
            Remove::new);

        public Remove(UUID bossEvent, int entityID) {
            super(bossEvent, entityID);
        }

        @Override
        public Type<Remove> type() {
            return TYPE;
        }

        public static void execute(Remove payload, IPayloadContext context) {
            RenderHooks.BOSS_EVENTS.remove(payload.bossEvent);
        }
    }

    public UUID getBossEvent() {
        return this.bossEvent;
    }

    public int getEntityID() {
        return this.entityID;
    }
}
