package com.aetherteam.aetherii.network;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.ClientNetworkUtil;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.packet.clientbound.AerbunnyMessagePacket;
import com.aetherteam.aetherii.network.packet.clientbound.AlkahestDamageBlockPacket;
import com.aetherteam.aetherii.network.packet.clientbound.AlkahestFizzPacket;
import com.aetherteam.aetherii.network.packet.clientbound.AlkahestItemSmokePacket;
import com.aetherteam.aetherii.network.packet.clientbound.AltarParticlesPacket;
import com.aetherteam.aetherii.network.packet.clientbound.AttackShockParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.AttackStabParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.BossInfoPacket;
import com.aetherteam.aetherii.network.packet.clientbound.BreakItemPacket;
import com.aetherteam.aetherii.network.packet.clientbound.ClientGrabItemPacket;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.DataAttachmentSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.FlushGuidebookDataPacket;
import com.aetherteam.aetherii.network.packet.clientbound.ForgeSoundPacket;
import com.aetherteam.aetherii.network.packet.clientbound.FreezingParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.GrassTintSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.GuidebookToastPacket;
import com.aetherteam.aetherii.network.packet.clientbound.HestveilExplosionEffectsPacket;
import com.aetherteam.aetherii.network.packet.clientbound.HourglassFinishParticlesPacket;
import com.aetherteam.aetherii.network.packet.clientbound.HourglassProcessParticlesPacket;
import com.aetherteam.aetherii.network.packet.clientbound.MusicBlockPlayPacket;
import com.aetherteam.aetherii.network.packet.clientbound.PortalTravelSoundPacket;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.aetherii.network.packet.clientbound.ResistanceKnockbackPacket;
import com.aetherteam.aetherii.network.packet.clientbound.SetAccessoriesPacket;
import com.aetherteam.aetherii.network.packet.clientbound.SetVehiclePacket;
import com.aetherteam.aetherii.network.packet.clientbound.SwetSyncPacket;
import com.aetherteam.aetherii.network.packet.serverbound.AerbunnyPuffPacket;
import com.aetherteam.aetherii.network.packet.serverbound.AlkahestBreakBlockPacket;
import com.aetherteam.aetherii.network.packet.serverbound.CheckBestiaryEntryPacket;
import com.aetherteam.aetherii.network.packet.serverbound.CheckEffectsEntryPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ClearAccessoriesPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aetherii.network.packet.serverbound.CurrencyAmountPacket;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardCompanionDeathPacket;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardCompanionPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeRenamePacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeSlotCharmsPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeTriggerSoundPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeUpgradePacket;
import com.aetherteam.aetherii.network.packet.serverbound.HeldCurrencyPacket;
import com.aetherteam.aetherii.network.packet.serverbound.MoaFlyModeChangePacket;
import com.aetherteam.aetherii.network.packet.serverbound.MountJumpedPacket;
import com.aetherteam.aetherii.network.packet.serverbound.MovementDataPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OutpostRespawnPacket;
import com.aetherteam.aetherii.network.packet.serverbound.SkiffParticlesPacket;
import com.aetherteam.aetherii.network.packet.serverbound.SkiffSteeringPacket;
import com.aetherteam.aetherii.network.packet.serverbound.StoreCompanionItemEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;

public final class AetherIINetwork {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AetherII.MODID, "main"),
            () -> PROTOCOL,
            NetworkRegistry.acceptMissingOr(PROTOCOL),
            NetworkRegistry.acceptMissingOr(PROTOCOL));
    private static int id;
    private static boolean registered;

    private AetherIINetwork() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }

        registerClientbound(AerbunnyMessagePacket.class, AerbunnyMessagePacket.STREAM_CODEC, AerbunnyMessagePacket::execute);
        registerClientbound(AlkahestDamageBlockPacket.class, AlkahestDamageBlockPacket.STREAM_CODEC, AlkahestDamageBlockPacket::execute);
        registerClientbound(AlkahestFizzPacket.class, AlkahestFizzPacket.STREAM_CODEC, AlkahestFizzPacket::execute);
        registerClientbound(AlkahestItemSmokePacket.class, AlkahestItemSmokePacket.STREAM_CODEC, AlkahestItemSmokePacket::execute);
        registerClientbound(AltarParticlesPacket.class, AltarParticlesPacket.STREAM_CODEC, AltarParticlesPacket::execute);
        registerClientbound(AttackShockParticlePacket.class, AttackShockParticlePacket.STREAM_CODEC, AttackShockParticlePacket::execute);
        registerClientbound(AttackStabParticlePacket.class, AttackStabParticlePacket.STREAM_CODEC, AttackStabParticlePacket::execute);
        registerClientbound(BossInfoPacket.Display.class, BossInfoPacket.Display.STREAM_CODEC, BossInfoPacket.Display::execute);
        registerClientbound(BossInfoPacket.Remove.class, BossInfoPacket.Remove.STREAM_CODEC, BossInfoPacket.Remove::execute);
        registerClientbound(BreakItemPacket.class, BreakItemPacket.STREAM_CODEC, BreakItemPacket::execute);
        registerClientbound(ClientGrabItemPacket.class, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registerClientbound(DataAttachmentSyncPacket.class, DataAttachmentSyncPacket.STREAM_CODEC, DataAttachmentSyncPacket::execute);
        registerClientbound(FlushGuidebookDataPacket.class, FlushGuidebookDataPacket.STREAM_CODEC, FlushGuidebookDataPacket::execute);
        registerClientbound(ForgeSoundPacket.class, ForgeSoundPacket.STREAM_CODEC, ForgeSoundPacket::execute);
        registerClientbound(FreezingParticlePacket.class, FreezingParticlePacket.STREAM_CODEC, FreezingParticlePacket::execute);
        registerClientbound(HestveilExplosionEffectsPacket.class, HestveilExplosionEffectsPacket.STREAM_CODEC, HestveilExplosionEffectsPacket::execute);
        registerClientbound(GuidebookToastPacket.class, GuidebookToastPacket.STREAM_CODEC, GuidebookToastPacket::execute);
        registerClientbound(DamageTypeParticlePacket.class, DamageTypeParticlePacket.STREAM_CODEC, DamageTypeParticlePacket::execute);
        registerClientbound(PortalTravelSoundPacket.class, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registerClientbound(HourglassFinishParticlesPacket.class, HourglassFinishParticlesPacket.STREAM_CODEC, HourglassFinishParticlesPacket::execute);
        registerClientbound(HourglassProcessParticlesPacket.class, HourglassProcessParticlesPacket.STREAM_CODEC, HourglassProcessParticlesPacket::execute);
        registerClientbound(MusicBlockPlayPacket.class, MusicBlockPlayPacket.STREAM_CODEC, MusicBlockPlayPacket::execute);
        registerClientbound(RemountAerbunnyPacket.class, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registerClientbound(ResistanceKnockbackPacket.class, ResistanceKnockbackPacket.STREAM_CODEC, ResistanceKnockbackPacket::execute);
        registerClientbound(SetAccessoriesPacket.class, SetAccessoriesPacket.STREAM_CODEC, SetAccessoriesPacket::execute);
        registerClientbound(SetVehiclePacket.class, SetVehiclePacket.STREAM_CODEC, SetVehiclePacket::execute);
        registerClientbound(SwetSyncPacket.class, SwetSyncPacket.STREAM_CODEC, SwetSyncPacket::execute);
        registerClientbound(GrassTintSyncPacket.class, GrassTintSyncPacket.STREAM_CODEC, GrassTintSyncPacket::execute);

        registerServerbound(AlkahestBreakBlockPacket.class, AlkahestBreakBlockPacket.STREAM_CODEC, AlkahestBreakBlockPacket::execute);
        registerServerbound(AerbunnyPuffPacket.class, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registerServerbound(CheckBestiaryEntryPacket.class, CheckBestiaryEntryPacket.STREAM_CODEC, CheckBestiaryEntryPacket::execute);
        registerServerbound(CheckEffectsEntryPacket.class, CheckEffectsEntryPacket.STREAM_CODEC, CheckEffectsEntryPacket::execute);
        registerServerbound(ClearAccessoriesPacket.class, ClearAccessoriesPacket.STREAM_CODEC, ClearAccessoriesPacket::execute);
        registerServerbound(ClearItemPacket.class, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registerServerbound(CurrencyAmountPacket.class, CurrencyAmountPacket.STREAM_CODEC, CurrencyAmountPacket::execute);
        registerServerbound(DiscardCompanionDeathPacket.class, DiscardCompanionDeathPacket.STREAM_CODEC, DiscardCompanionDeathPacket::execute);
        registerServerbound(DiscardCompanionPacket.class, DiscardCompanionPacket.STREAM_CODEC, DiscardCompanionPacket::execute);
        registerServerbound(ForgeRenamePacket.class, ForgeRenamePacket.STREAM_CODEC, ForgeRenamePacket::execute);
        registerServerbound(ForgeSlotCharmsPacket.class, ForgeSlotCharmsPacket.STREAM_CODEC, ForgeSlotCharmsPacket::execute);
        registerServerbound(ForgeTriggerSoundPacket.class, ForgeTriggerSoundPacket.STREAM_CODEC, ForgeTriggerSoundPacket::execute);
        registerServerbound(ForgeUpgradePacket.class, ForgeUpgradePacket.STREAM_CODEC, ForgeUpgradePacket::execute);
        registerServerbound(HeldCurrencyPacket.class, HeldCurrencyPacket.STREAM_CODEC, HeldCurrencyPacket::execute);
        registerServerbound(MoaFlyModeChangePacket.class, MoaFlyModeChangePacket.STREAM_CODEC, MoaFlyModeChangePacket::execute);
        registerServerbound(MountJumpedPacket.class, MountJumpedPacket.STREAM_CODEC, MountJumpedPacket::execute);
        registerServerbound(MovementDataPacket.class, MovementDataPacket.STREAM_CODEC, MovementDataPacket::execute);
        registerServerbound(OpenGuidebookPacket.class, OpenGuidebookPacket.STREAM_CODEC, OpenGuidebookPacket::execute);
        registerServerbound(OpenInventoryPacket.class, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registerServerbound(OutpostRespawnPacket.class, OutpostRespawnPacket.STREAM_CODEC, OutpostRespawnPacket::execute);
        registerServerbound(SkiffParticlesPacket.class, SkiffParticlesPacket.STREAM_CODEC, SkiffParticlesPacket::execute);
        registerServerbound(SkiffSteeringPacket.class, SkiffSteeringPacket.STREAM_CODEC, SkiffSteeringPacket::execute);
        registerServerbound(StoreCompanionItemEntityPacket.class, StoreCompanionItemEntityPacket.STREAM_CODEC, StoreCompanionItemEntityPacket::execute);

        registered = true;
    }

    public static void sendToServer(Object payload) {
        CHANNEL.sendToServer(asPayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, Object payload) {
        CHANNEL.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player), asPayload(payload));
    }

    public static void sendToAllPlayers(Object payload) {
        CHANNEL.send(net.minecraftforge.network.PacketDistributor.ALL.noArg(), asPayload(payload));
    }

    public static void sendToPlayersInDimension(ServerLevel level, Object payload) {
        CHANNEL.send(net.minecraftforge.network.PacketDistributor.DIMENSION.with(level::dimension), asPayload(payload));
    }

    public static void sendToPlayersNear(ServerLevel level, ServerPlayer excluded, double x, double y, double z, double radius, Object payload) {
        CHANNEL.send(net.minecraftforge.network.PacketDistributor.NEAR.with(() -> new net.minecraftforge.network.PacketDistributor.TargetPoint(excluded, x, y, z, radius, level.dimension())), asPayload(payload));
    }

    private static <T extends AetherPacketPayload> void registerClientbound(Class<T> type, StreamCodec<FriendlyByteBuf, T> codec, BiConsumer<T, AetherPayloadContext> handler) {
        register(type, codec, handler, NetworkDirection.PLAY_TO_CLIENT);
    }

    private static <T extends AetherPacketPayload> void registerServerbound(Class<T> type, StreamCodec<FriendlyByteBuf, T> codec, BiConsumer<T, AetherPayloadContext> handler) {
        register(type, codec, handler, NetworkDirection.PLAY_TO_SERVER);
    }

    private static <T extends AetherPacketPayload> void register(Class<T> type, StreamCodec<FriendlyByteBuf, T> codec, BiConsumer<T, AetherPayloadContext> handler, NetworkDirection direction) {
        CHANNEL.messageBuilder(type, id++, direction)
                .encoder((payload, buffer) -> codec.encode(buffer, payload))
                .decoder(codec::decode)
                .consumerMainThread((payload, contextSupplier) -> {
                    NetworkEvent.Context context = contextSupplier.get();
                    handler.accept(payload, new ForgePayloadContext(context));
                    context.setPacketHandled(true);
                })
                .add();
    }

    private static AetherPacketPayload asPayload(Object payload) {
        if (payload instanceof AetherPacketPayload aetherPacketPayload) {
            return aetherPacketPayload;
        }
        throw new IllegalArgumentException("Expected Aether II packet payload, got " + payload);
    }

    private record ForgePayloadContext(NetworkEvent.Context context) implements AetherPayloadContext {
        @Override
        public Player player() {
            Player sender = this.context.getSender();
            return sender != null ? sender : ClientNetworkUtil.getPlayer();
        }

        @Override
        public void enqueueWork(Runnable runnable) {
            this.context.enqueueWork(runnable);
        }
    }
}
