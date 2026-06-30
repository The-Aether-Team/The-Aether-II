package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.network.packet.clientbound.RemountAerbunnyPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.util.nbt.ValueInput;
import com.aetherteam.aetherii.util.nbt.ValueOutput;
import com.aetherteam.aetherii.util.nbt.ValueIOSerializable;
import com.aetherteam.aetherii.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

public class AerbunnyMountAttachment implements ValueIOSerializable {
    @Nullable
    private Aerbunny mountedAerbunny;
    private Optional<CompoundTag> mountedAerbunnyTag = Optional.empty();
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
            CompoundTag tag = new CompoundTag();
            Aerbunny aerbunny = this.getMountedAerbunny();
            aerbunny.saveAsPassenger(tag);
            this.setMountedAerbunnyTag(Optional.of(tag));
            aerbunny.stopRiding();
            aerbunny.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }
    }

    /**
     * Remounts an Aerbunny to the player if there exists stored NBT when joining the world.
     */
    public void remountAerbunny(Player player) {
        if (this.getMountedAerbunnyTag().isPresent()) {
            if (!player.level().isClientSide()) {
                Aerbunny aerbunny = new Aerbunny(AetherIIEntityTypes.AERBUNNY.get(), player.level());
                aerbunny.load(this.getMountedAerbunnyTag().get());
                player.level().addFreshEntity(aerbunny);
                aerbunny.startRiding(player, true);
                this.setMountedAerbunny(aerbunny);
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new RemountAerbunnyPacket(player.getId(), aerbunny.getId()));
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

    @Override
    public void serialize(ValueOutput valueOutput) {
        if (this.mountedAerbunnyTag.isPresent()) {
            valueOutput.store("mounted_aerbunny", CompoundTag.CODEC, this.mountedAerbunnyTag.get());
        }
    }

    @Override
    public void deserialize(ValueInput valueInput) {
        this.mountedAerbunnyTag = valueInput.read("mounted_aerbunny", CompoundTag.CODEC);
    }
}
