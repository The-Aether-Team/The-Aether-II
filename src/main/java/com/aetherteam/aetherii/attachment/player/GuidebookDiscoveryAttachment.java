package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.network.packet.clientbound.GuidebookToastPacket;
import com.aetherteam.aetherii.network.packet.clientbound.UpdateGuidebookDiscoveryPacket;
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
    private List<Holder<BestiaryEntry>> uncheckedBestiaryEntries;
    private boolean shouldSyncAfterJoin = false;
    private boolean sync = false;

    public static final Codec<GuidebookDiscoveryAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BestiaryEntry.REFERENCE_CODEC.listOf().fieldOf("observed_bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getObservedBestiaryEntries),
            BestiaryEntry.REFERENCE_CODEC.listOf().fieldOf("understood_bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getUnderstoodBestiaryEntries),
            BestiaryEntry.REFERENCE_CODEC.listOf().fieldOf("unchecked_bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getUncheckedBestiaryEntries)
    ).apply(instance, GuidebookDiscoveryAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookDiscoveryAttachment> STREAM_CODEC = StreamCodec.composite(
            BestiaryEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GuidebookDiscoveryAttachment::getObservedBestiaryEntries,
            BestiaryEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GuidebookDiscoveryAttachment::getUnderstoodBestiaryEntries,
            BestiaryEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GuidebookDiscoveryAttachment::getUncheckedBestiaryEntries,
            GuidebookDiscoveryAttachment::new
    );

    public GuidebookDiscoveryAttachment(List<Holder<BestiaryEntry>> observedBestiaryEntries, List<Holder<BestiaryEntry>> understoodBestiaryEntries, List<Holder<BestiaryEntry>> uncheckedBestiaryEntries) {
        this.observedBestiaryEntries = new ArrayList<>(observedBestiaryEntries);
        this.understoodBestiaryEntries = new ArrayList<>(understoodBestiaryEntries);
        this.uncheckedBestiaryEntries = new ArrayList<>(uncheckedBestiaryEntries);
    }

    public GuidebookDiscoveryAttachment() {
        this.observedBestiaryEntries = new ArrayList<>();
        this.understoodBestiaryEntries = new ArrayList<>();
        this.uncheckedBestiaryEntries = new ArrayList<>();
    }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void clone(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void postTickUpdate(Player player) {
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

    public void progressAdvancement(Player player, AdvancementHolder advancement) {
        this.trackDiscoveries(player, advancement);
    }

    public void trackDiscoveries(Player player, AdvancementHolder advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            RegistryAccess registryAccess = serverPlayer.registryAccess();
            this.trackBestiaryEntries(serverPlayer, registryAccess, advancement);
            if (this.sync) {
                PacketDistributor.sendToPlayer(serverPlayer, new UpdateGuidebookDiscoveryPacket(this));
                this.sync = false;
            }
        }
    }

    private void trackBestiaryEntries(ServerPlayer serverPlayer, RegistryAccess registryAccess, AdvancementHolder advancement) {
        Registry<BestiaryEntry> bestiaryEntries = registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
        for (Holder.Reference<BestiaryEntry> entry : bestiaryEntries.holders().toList()) {
            if (advancement.id().equals(entry.value().observationAdvancement())) {
                boolean flag = false;
                if (!this.observedBestiaryEntries.contains(entry)) {
                    this.observedBestiaryEntries.add(entry);
                    flag = true;
                }
                if (!this.uncheckedBestiaryEntries.contains(entry)) {
                    this.uncheckedBestiaryEntries.add(entry);
                    flag = true;
                }
                if (flag) {
                    this.sync = true;
                }
            }
            if (advancement.id().equals(entry.value().understandingAdvancement())) {
                boolean flag = false;
                if (!this.observedBestiaryEntries.contains(entry)) {
                    this.observedBestiaryEntries.add(entry);
                    flag = true;
                }
                if (!this.understoodBestiaryEntries.contains(entry)) {
                    this.understoodBestiaryEntries.add(entry);
                    flag = true;
                }
                if (!this.uncheckedBestiaryEntries.contains(entry)) {
                    this.uncheckedBestiaryEntries.add(entry);
                    flag = true;
                }
                if (flag) {
                    this.sync = true;
                }
            }
        }
        if (this.sync) {
            PacketDistributor.sendToPlayer(serverPlayer, new GuidebookToastPacket(GuidebookToast.Type.DISCOVERY, GuidebookToast.Icons.BESTIARY));
        }
    }

    public List<Holder<BestiaryEntry>> getObservedBestiaryEntries() {
        return this.observedBestiaryEntries;
    }

    public List<Holder<BestiaryEntry>> getUnderstoodBestiaryEntries() {
        return this.understoodBestiaryEntries;
    }

    public List<Holder<BestiaryEntry>> getUncheckedBestiaryEntries() {
        return this.uncheckedBestiaryEntries;
    }

    public boolean hasAnyBestiaryEntryStatus(Holder<BestiaryEntry> bestiaryEntry) {
        return this.getObservedBestiaryEntries().contains(bestiaryEntry) || this.getUncheckedBestiaryEntries().contains(bestiaryEntry);
    }

    public void syncAttachment(GuidebookDiscoveryAttachment other) {
        this.observedBestiaryEntries = other.observedBestiaryEntries;
        this.understoodBestiaryEntries = other.understoodBestiaryEntries;
        this.uncheckedBestiaryEntries = other.uncheckedBestiaryEntries;
    }
}
