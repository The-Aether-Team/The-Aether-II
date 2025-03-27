package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.api.guidebook.*;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIExplorationEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIRewardWrappers;
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
import java.util.Optional;

public class GuidebookDiscoveryAttachment {
    private List<BestiaryEntry.Mutable> bestiaryEntries;
    private List<EffectsEntry.Mutable> effectsEntries;
    private List<ExplorationEntry.Mutable> explorationEntries;
    private boolean shouldSyncAfterJoin = false;
    private boolean sync = false;

    public static final Codec<GuidebookDiscoveryAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BestiaryEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getBestiaryEntries),
            EffectsEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("effects_entries").forGetter(GuidebookDiscoveryAttachment::getEffectsEntries),
            ExplorationEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("exploration_entries").forGetter(GuidebookDiscoveryAttachment::getExplorationEntries)
    ).apply(instance, GuidebookDiscoveryAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookDiscoveryAttachment> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

    protected GuidebookDiscoveryAttachment(List<BestiaryEntry.Mutable> bestiaryEntries, List<EffectsEntry.Mutable> effectsEntries, List<ExplorationEntry.Mutable> explorationEntries) {
        this.bestiaryEntries = new ArrayList<>(bestiaryEntries);
        this.effectsEntries = new ArrayList<>(effectsEntries);
        this.explorationEntries = new ArrayList<>(explorationEntries);
    }

    public GuidebookDiscoveryAttachment() {
        this.bestiaryEntries = new ArrayList<>();
        this.effectsEntries = new ArrayList<>();
        this.explorationEntries = new ArrayList<>();
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
            this.trackBestiaryEntries(registryAccess, advancement, serverPlayer);
            this.trackEffectsEntries(registryAccess, advancement, serverPlayer); //todo need to check that the reward wrapper registry actually matches first. but also theres no registry in the reward wrapper now.
            this.trackExplorationEntries(registryAccess, advancement, serverPlayer);
            if (this.sync) {
                PacketDistributor.sendToPlayer(serverPlayer, new UpdateGuidebookDiscoveryPacket(this));
                this.sync = false;
            }
        }
    }

    private void trackBestiaryEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (this.bestiaryEntries.isEmpty()) {
            Registry<BestiaryEntry> bestiaryEntries = registryAccess.lookupOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
            for (Holder<BestiaryEntry> entry : bestiaryEntries.asHolderIdMap()) {
                this.bestiaryEntries.add(new BestiaryEntry.Mutable(entry));
            }
        }
        if (advancement.id().getPath().startsWith("bestiary/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.bestiaryEntries, GuidebookToast.Icons.BESTIARY);
        }
    }

    private void trackEffectsEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (this.effectsEntries.isEmpty()) {
            Registry<EffectsEntry> effectsEntries = registryAccess.lookupOrThrow(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);
            for (Holder<EffectsEntry> entry : effectsEntries.asHolderIdMap()) {
                this.effectsEntries.add(new EffectsEntry.Mutable(entry));
            }
        }
        if (advancement.id().getPath().startsWith("effects/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.effectsEntries, GuidebookToast.Icons.EFFECTS);
        }
    }

    private void trackExplorationEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (this.explorationEntries.isEmpty()) {
            Registry<ExplorationEntry> explorationEntries = registryAccess.lookupOrThrow(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY);
            for (Holder<ExplorationEntry> entry : explorationEntries.asHolderIdMap()) {
                this.explorationEntries.add(new ExplorationEntry.Mutable(entry));
            }
        }
        if (advancement.id().getPath().startsWith("exploration/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.explorationEntries, GuidebookToast.Icons.EXPLORATION);
        }
    }

    private void revealEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer, List<? extends MutableEntry> list, GuidebookToast.Icons icon) {
        Optional<RewardWrapper> rewardOptional = AetherIIRewardWrappers.getWrapperForAdvancement(registryAccess, advancement);
        if (rewardOptional.isPresent()) {
            RewardWrapper reward = rewardOptional.get();
            for (MutableEntry entry : list) {
                if (entry.getEntry().is(reward.entryId())) {
                    entry.getClientValues().keySet().forEach(name -> {
                        if (entry.getClientValues().containsKey(name)) {
                            entry.getClientValues().get(name).reveal();
                        }
                    });
                    this.sync = true;
                }
            }
        }
        if (this.sync) {
            PacketDistributor.sendToPlayer(serverPlayer, new GuidebookToastPacket(GuidebookToast.Type.DISCOVERY, icon));
        }
    }

    public List<BestiaryEntry.Mutable> getBestiaryEntries() {
        return this.bestiaryEntries;
    }

    public List<EffectsEntry.Mutable> getEffectsEntries() {
        return this.effectsEntries;
    }

    public List<ExplorationEntry.Mutable> getExplorationEntries() {
        return this.explorationEntries;
    }

    public void syncAttachment(GuidebookDiscoveryAttachment other) {
        this.bestiaryEntries = other.bestiaryEntries;
        this.effectsEntries = other.effectsEntries;
        this.explorationEntries = other.explorationEntries;
    }
}
