package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EnteredStructurePacket() implements CustomPacketPayload {
    public static final Type<EnteredStructurePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "entered_structure"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EnteredStructurePacket> STREAM_CODEC = StreamCodec.unit(new EnteredStructurePacket());

    @Override
    public Type<EnteredStructurePacket> type() {
        return TYPE;
    }

    public static void execute(EnteredStructurePacket payload, IPayloadContext context) {
        MusicManager music = Minecraft.getInstance().getMusicManager();

        if (context.player() instanceof ServerPlayer serverPlayer && !music.isPlayingMusic(AudioHooks.AETHER_MINESHAFT)) {
            if (serverPlayer.level().structureManager().getStructureAt(context.player().blockPosition(), serverPlayer.level().registryAccess().lookupOrThrow(Registries.STRUCTURE).getValueOrThrow(AetherIIStructures.UNDERCLOUD_MINESHAFT)).isValid()) {
                music.stopPlaying();
                music.startPlaying(AudioHooks.AETHER_MINESHAFT);
            }
        }
    }
}