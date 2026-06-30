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
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public static final StreamCodec<FriendlyByteBuf, GuidebookDiscoveryAttachment> STREAM_CODEC = StreamCodec.composite(
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
                AetherIIDataAttachments.sync(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            }
            this.shouldSetupAfterJoin = false;
        }
    }

    private void setupEntries(ServerPlayer serverPlayer) {
        RegistryAccess registryAccess = serverPlayer.server.registryAccess();
        if (this.bestiaryEntries.isEmpty()) {
            registryAccess.lookupOrThrow(AetherIIRegistries.BESTIARY_ENTRY).listElements().forEach(entry -> this.bestiaryEntries.add(new BestiaryEntry.Mutable(entry)));
        }
        if (this.effectsEntries.isEmpty()) {
            registryAccess.lookupOrThrow(AetherIIRegistries.EFFECTS_ENTRY).listElements().forEach(entry -> this.effectsEntries.add(new EffectsEntry.Mutable(entry)));
        }
        if (this.explorationEntries.isEmpty()) {
            registryAccess.lookupOrThrow(AetherIIRegistries.EXPLORATION_ENTRY).listElements().forEach(entry -> this.explorationEntries.add(new ExplorationEntry.Mutable(entry)));
        }
    }

    public void progressAdvancement(Player player, Advancement advancement) {
        this.trackDiscoveries(player, advancement);
    }

    public void trackDiscoveries(Player player, Advancement advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            RegistryAccess registryAccess = serverPlayer.server.registryAccess();
            this.trackBestiaryEntries(registryAccess, advancement, serverPlayer);
            this.trackEffectsEntries(registryAccess, advancement, serverPlayer);
            this.trackExplorationEntries(registryAccess, advancement, serverPlayer);
            AetherIIDataAttachments.sync(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
        }
    }

    private void trackBestiaryEntries(RegistryAccess registryAccess, Advancement advancement, ServerPlayer serverPlayer) {
        if (advancement.getId().getPath().startsWith("bestiary/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.bestiaryEntries);
        }
    }

    private void trackEffectsEntries(RegistryAccess registryAccess, Advancement advancement, ServerPlayer serverPlayer) {
        if (advancement.getId().getPath().startsWith("effects/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.effectsEntries);
        }
    }

    private void trackExplorationEntries(RegistryAccess registryAccess, Advancement advancement, ServerPlayer serverPlayer) {
        if (advancement.getId().getPath().startsWith("exploration/")) {
            this.revealEntries(registryAccess, advancement, serverPlayer, this.explorationEntries);
        }
    }

    private void revealEntries(RegistryAccess registryAccess, Advancement advancement, ServerPlayer serverPlayer, List<? extends MutableEntry> list) {
        GuidebookToast.Icons icon = null;
        Optional<RewardWrapper> rewardOptional = AetherIIRewardWrappers.getWrapperForAdvancement(registryAccess, advancement.getId());
        if (rewardOptional.isPresent()) {
            RewardWrapper reward = rewardOptional.get();
            for (MutableEntry entry : list) {
                if (entry.getEntry().is(reward.entryId())) {
                    boolean revealed = false;
                    List<String> values = reward.entryValues().isEmpty() ? List.copyOf(entry.getClientValues().keySet()) : reward.entryValues();
                    for (String name : values) {
                        GuidebookEntry.Info info = entry.getClientValues().get(name);
                        if (info != null && !info.isVisible()) {
                            info.reveal();
                            revealed = true;
                        }
                    }
                    if (revealed) {
                        icon = this.getIconForEntry(entry);
                        AetherIIDataAttachments.sync(serverPlayer, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
                    }
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
        if (entry instanceof BestiaryEntry.Mutable) {
            return GuidebookToast.Icons.BESTIARY;
        } else if (entry instanceof EffectsEntry.Mutable) {
            return GuidebookToast.Icons.EFFECTS;
        }
        return GuidebookToast.Icons.EXPLORATION;
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
