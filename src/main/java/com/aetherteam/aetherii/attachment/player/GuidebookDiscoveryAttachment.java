package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.network.packet.clientbound.UpdateGuidebookDiscoveryPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class GuidebookDiscoveryAttachment {
    private List<Holder<BestiaryEntry>> observedBestiaryEntries;
    private List<Holder<BestiaryEntry>> understoodBestiaryEntries;
    private boolean shouldSyncAfterJoin = false;
    private boolean sync = false;

    public static final Codec<GuidebookDiscoveryAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BestiaryEntry.REFERENCE_CODEC.listOf().fieldOf("observed_bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getObservedBestiaryEntries),
            BestiaryEntry.REFERENCE_CODEC.listOf().fieldOf("understood_bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getUnderstoodBestiaryEntries)
    ).apply(instance, GuidebookDiscoveryAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookDiscoveryAttachment> STREAM_CODEC = StreamCodec.composite(
            BestiaryEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GuidebookDiscoveryAttachment::getObservedBestiaryEntries,
            BestiaryEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GuidebookDiscoveryAttachment::getUnderstoodBestiaryEntries,
            GuidebookDiscoveryAttachment::new
    );

    public GuidebookDiscoveryAttachment(List<Holder<BestiaryEntry>> observedBestiaryEntries, List<Holder<BestiaryEntry>> understoodBestiaryEntries) {
        this.observedBestiaryEntries = new ArrayList<>(observedBestiaryEntries);
        this.understoodBestiaryEntries = new ArrayList<>(understoodBestiaryEntries);
    }

    public GuidebookDiscoveryAttachment() {
        this.observedBestiaryEntries = new ArrayList<>();
        this.understoodBestiaryEntries = new ArrayList<>();
    }

    public void onLogin(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void onUpdate(Player player) {
        this.syncAfterJoin(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new UpdateGuidebookDiscoveryPacket(this));
            }
            this.shouldSyncAfterJoin = false;
        }
    }

    public void trackDiscoveries(Player player, AdvancementHolder advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            RegistryAccess registryAccess = serverPlayer.registryAccess();
            this.trackBestiaryEntries(registryAccess, advancement);
            if (this.sync) {
                PacketDistributor.sendToPlayer(serverPlayer, new UpdateGuidebookDiscoveryPacket(this));
                this.sync = false;
            }
        }
    }

    private void trackBestiaryEntries(RegistryAccess registryAccess, AdvancementHolder advancement) { //todo toasts
        Registry<BestiaryEntry> bestiaryEntries = registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
        for (Holder.Reference<BestiaryEntry> entry : bestiaryEntries.holders().toList()) {
            if (advancement.id().equals(entry.value().observationAdvancement())) {
                this.observedBestiaryEntries.add(entry);
                this.sync = true;
            }
            if (advancement.id().equals(entry.value().understandingAdvancement())) {
                this.understoodBestiaryEntries.add(entry);
                this.sync = true;
            }
        }
    }

    public List<Holder<BestiaryEntry>> getObservedBestiaryEntries() {
        return this.observedBestiaryEntries;
    }

    public List<Holder<BestiaryEntry>> getUnderstoodBestiaryEntries() {
        return this.understoodBestiaryEntries;
    }

    public void syncAttachment(GuidebookDiscoveryAttachment other) {
        this.observedBestiaryEntries = other.observedBestiaryEntries;
        this.understoodBestiaryEntries = other.understoodBestiaryEntries;
    }
}
