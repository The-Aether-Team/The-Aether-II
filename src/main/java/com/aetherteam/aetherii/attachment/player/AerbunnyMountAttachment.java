package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

public class AerbunnyMountAttachment {
    @Nullable
    private Aerbunny mountedAerbunny;
    private Optional<CompoundTag> mountedAerbunnyTag = Optional.empty();

    public static final MapCodec<AerbunnyMountAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CompoundTag.CODEC.optionalFieldOf("mounted_aerbunny").forGetter(AerbunnyMountAttachment::getMountedAerbunnyTag)
    ).apply(instance, AerbunnyMountAttachment::new));

    protected AerbunnyMountAttachment(Optional<CompoundTag> mountedAerbunnyTag) {
        this.mountedAerbunnyTag = mountedAerbunnyTag;
    }

    public AerbunnyMountAttachment() { }

    public void login(Player player) {
        this.remountAerbunny(player);
    }

    public void postTickUpdate(Player player) {
        this.checkToRemoveAerbunny(player);
    }

    /**
     * Removes an Aerbunny from the world and stores it to NBT for the capability. This is used when a player logs out with an Aerbunny.
     */
    public void removeAerbunny() {
        if (this.getMountedAerbunny() != null) {
            try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(this.getMountedAerbunny().problemPath(), AetherII.LOGGER)) {
                TagValueOutput valueOutput = TagValueOutput.createWithContext(collector, this.getMountedAerbunny().registryAccess());
                this.getMountedAerbunny().saveAsPassenger(valueOutput);
                this.setMountedAerbunnyTag(Optional.of(valueOutput.buildResult()));
                this.getMountedAerbunny().stopRiding();
                this.getMountedAerbunny().setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            }
        }
    }

    /**
     * Remounts an Aerbunny to the player if there exists stored NBT when joining the world.
     */
    public void remountAerbunny(Player player) {
        if (this.getMountedAerbunnyTag().isPresent()) {
            if (!player.level().isClientSide()) {
                try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(player.problemPath(), AetherII.LOGGER)) {
                    Aerbunny aerbunny = new Aerbunny(AetherIIEntityTypes.AERBUNNY.get(), player.level());
                    ValueInput valueInput = TagValueInput.create(collector, player.registryAccess(), this.getMountedAerbunnyTag().get());
                    aerbunny.load(valueInput);
                    player.level().addFreshEntity(aerbunny);
                    aerbunny.startRiding(player, true, false);
                    this.setMountedAerbunny(aerbunny);
                    if (player instanceof ServerPlayer serverPlayer) {
                        PacketDistributor.sendToPlayer(serverPlayer, new RemountAerbunnyPacket(player.getId(), aerbunny.getId()));
                    }
                }
            }
            this.setMountedAerbunnyTag(Optional.empty());
        }
    }

    /**
     * Checks whether the capability should stop tracking a mounted Aerbunny.
     */
    private void checkToRemoveAerbunny(Player player) {
        if (this.getMountedAerbunny() != null && (!this.getMountedAerbunny().isAlive() || !player.isAlive())) {
            this.setMountedAerbunny(null);
        }
    }

    public void setMountedAerbunny(@Nullable Aerbunny mountedAerbunny) {
        this.mountedAerbunny = mountedAerbunny;
    }

    /**
     * @return The {@link Aerbunny} currently mounted to the player
     */
    @Nullable
    public Aerbunny getMountedAerbunny() {
        return this.mountedAerbunny;
    }

    public void setMountedAerbunnyTag(Optional<CompoundTag> mountedAerbunnyTag) {
        this.mountedAerbunnyTag = mountedAerbunnyTag;
    }

    /**
     * @return The {@link CompoundTag} data for the Aerbunny currently mounted to the player.
     */
    public Optional<CompoundTag> getMountedAerbunnyTag() {
        return this.mountedAerbunnyTag;
    }
}
