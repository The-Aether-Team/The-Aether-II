package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.api.guidebook.*;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import com.aetherteam.aetherii.data.resources.registries.AetherIIRewardWrappers;
import com.aetherteam.aetherii.network.packet.clientbound.FlushGuidebookDataPacket;
import com.aetherteam.aetherii.network.packet.clientbound.GuidebookToastPacket;
import com.mojang.serialization.MapCodec;
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
    private boolean shouldSetupAfterJoin = false;
    private List<BestiaryEntry.Mutable> bestiaryEntries;
    private List<EffectsEntry.Mutable> effectsEntries;
    private List<ExplorationEntry.Mutable> explorationEntries;

    public static final MapCodec<GuidebookDiscoveryAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BestiaryEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("bestiary_entries").forGetter(GuidebookDiscoveryAttachment::getBestiaryEntries),
            EffectsEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("effects_entries").forGetter(GuidebookDiscoveryAttachment::getEffectsEntries),
            ExplorationEntry.Mutable.DIRECT_CODEC.listOf().fieldOf("exploration_entries").forGetter(GuidebookDiscoveryAttachment::getExplorationEntries)
    ).apply(instance, GuidebookDiscoveryAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookDiscoveryAttachment> STREAM_CODEC = StreamCodec.composite(
            BestiaryEntry.Mutable.STREAM_CODEC.apply(ByteBufCodecs.list()), GuidebookDiscoveryAttachment::getBestiaryEntries,
            EffectsEntry.Mutable.STREAM_CODEC.apply(ByteBufCodecs.list()), GuidebookDiscoveryAttachment::getEffectsEntries,
            ExplorationEntry.Mutable.STREAM_CODEC.apply(ByteBufCodecs.list()), GuidebookDiscoveryAttachment::getExplorationEntries,
            GuidebookDiscoveryAttachment::new);

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

    public void postTickUpdate(Player player) {
        this.setupAfterJoin(player);
    }

    public void login(Player player) {
        this.shouldSetupAfterJoin = true;
    }

    public void clone(Player player) {
        this.shouldSetupAfterJoin = true;
    }

    private void setupAfterJoin(Player player) {
        if (this.shouldSetupAfterJoin) {
            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new FlushGuidebookDataPacket());
                this.setupEntries(serverPlayer);
                player.syncData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            }
            this.shouldSetupAfterJoin = false;
        }
    }

    private void setupEntries(ServerPlayer serverPlayer) {
        RegistryAccess registryAccess = serverPlayer.registryAccess();
        if (this.bestiaryEntries.isEmpty()) {
            Registry<BestiaryEntry> bestiaryEntries = registryAccess.lookupOrThrow(AetherIIRegistries.BESTIARY_ENTRY);
            for (Holder<BestiaryEntry> entry : bestiaryEntries.asHolderIdMap()) {
                this.bestiaryEntries.add(new BestiaryEntry.Mutable(entry));
            }
        }
        if (this.effectsEntries.isEmpty()) {
            Registry<EffectsEntry> effectsEntries = registryAccess.lookupOrThrow(AetherIIRegistries.EFFECTS_ENTRY);
            for (Holder<EffectsEntry> entry : effectsEntries.asHolderIdMap()) {
                this.effectsEntries.add(new EffectsEntry.Mutable(entry));
            }
        }
        if (this.explorationEntries.isEmpty()) {
            Registry<ExplorationEntry> explorationEntries = registryAccess.lookupOrThrow(AetherIIRegistries.EXPLORATION_ENTRY);
            for (Holder<ExplorationEntry> entry : explorationEntries.asHolderIdMap()) {
                this.explorationEntries.add(new ExplorationEntry.Mutable(entry));
            }
        }
    }

    public void progressAdvancement(Player player, AdvancementHolder advancement) {
        this.trackDiscoveries(player, advancement);
    }

    public void trackDiscoveries(Player player, AdvancementHolder advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            RegistryAccess registryAccess = serverPlayer.registryAccess();
            this.trackBestiaryEntries(registryAccess, advancement, serverPlayer);
            this.trackEffectsEntries(registryAccess, advancement, serverPlayer);
            this.trackExplorationEntries(registryAccess, advancement, serverPlayer);
            player.syncData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
        }
    }

    private void trackBestiaryEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (advancement.id().getPath().startsWith("bestiary/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.bestiaryEntries);
        }
    }

    private void trackEffectsEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (advancement.id().getPath().startsWith("effects/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.effectsEntries);
        }
    }

    private void trackExplorationEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        if (advancement.id().getPath().startsWith("exploration/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.explorationEntries);
        }
    }

    private void revealEntries(RegistryAccess registryAccess, AdvancementHolder advancement, ServerPlayer serverPlayer, List<? extends MutableEntry> list) {
        GuidebookToast.Icons icon = null;
        Optional<RewardWrapper> rewardOptional = AetherIIRewardWrappers.getWrapperForAdvancement(registryAccess, advancement.id());
        if (rewardOptional.isPresent()) {
            RewardWrapper reward = rewardOptional.get();
            for (MutableEntry entry : list) {
                if (entry.getEntry().is(reward.entryId())) {
                    entry.getClientValues().keySet().forEach(name -> {
                        if (entry.getClientValues().containsKey(name)) {
                            entry.getClientValues().get(name).reveal();
                        }
                    });
                    icon = this.getIconForEntry(entry);
                    serverPlayer.syncData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
                }
            }
        }
        if (icon != null) {
            PacketDistributor.sendToPlayer(serverPlayer, new GuidebookToastPacket(GuidebookToast.Type.DISCOVERY, icon));
        }
    }

    public void clearEntries() {
        this.getBestiaryEntries().clear();
        this.getEffectsEntries().clear();
        this.getExplorationEntries().clear();
    }

    public GuidebookToast.Icons getIconForEntry(MutableEntry entry) {
        switch(entry) {
            case BestiaryEntry.Mutable e -> {
                return GuidebookToast.Icons.BESTIARY;
            }
            case EffectsEntry.Mutable e -> {
                return GuidebookToast.Icons.EFFECTS;
            }
            default -> {
                return GuidebookToast.Icons.EXPLORATION;
            }
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
}
