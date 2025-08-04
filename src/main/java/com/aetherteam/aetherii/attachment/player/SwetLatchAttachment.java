package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.network.packet.clientbound.SwetSyncPacket;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.TeleportTransition;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Iterator;
import java.util.List;

public class SwetLatchAttachment {
    public static final ResourceLocation DEBUFFED_MOVEMENT_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "player.debuff.swet_movement_speed");
    public static final int MAX_SWET_COUNT = 3;

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        ListTag listTag = new ListTag();
        CompoundTag compoundTag = new CompoundTag();

        for (Swet swet : this.getLatchedSwets()) {
            CompoundTag swetTag = new CompoundTag();
            swet.addAdditionalSaveData(swetTag);
            listTag.add(swetTag);
        }
        compoundTag.put("swets", listTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        ListTag list = tag.getList("swets", 10);

        this.getLatchedSwets().clear();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag compound = list.getCompound(i);
            compound.remove("Dimension");

            Swet swet = AetherIIEntityTypes.SWET.get().create(this.player.level(), EntitySpawnReason.TRIGGERED);
            if (swet != null) {
                swet.readAdditionalSaveData(compound);
                this.getLatchedSwets().add(swet);
                this.syncToClient = true;
            }
        }
    }
    //todo CODECS

    public static final Codec<SwetLatchAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(

    ).apply(instance, SwetLatchAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SwetLatchAttachment> STREAM_CODEC = StreamCodec.composite(

            SwetLatchAttachment::new);

    private final Player player;
    private final List<Swet> swets = Lists.newArrayList();
    private boolean syncToClient = false;

    public SwetLatchAttachment(Player player) {
        this.player = player;
    }

    public void postTickUpdate() {
        if (this.syncToClient) {
            if (!this.player.level().isClientSide()) {
                PacketDistributor.sendToAllPlayers(new SwetSyncPacket(this.player.getId(), this.serializeNBT(this.player.registryAccess())));
            }
            this.syncToClient = false;
        }
        this.handleSwetTick();
    }

    public void handleSwetTick() {
        AttributeInstance movementSpeedAttribute = this.player.getAttribute(Attributes.MOVEMENT_SPEED);
        double value = -0.15 * this.swets.size();
        if (movementSpeedAttribute != null) {
            if (!movementSpeedAttribute.hasModifier(DEBUFFED_MOVEMENT_SPEED) || movementSpeedAttribute.getModifier(DEBUFFED_MOVEMENT_SPEED).amount() != value) {
                if (value != 0.0F) {
                    movementSpeedAttribute.addOrUpdateTransientModifier(new AttributeModifier(DEBUFFED_MOVEMENT_SPEED, value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                } else if (movementSpeedAttribute.hasModifier(DEBUFFED_MOVEMENT_SPEED)) {
                    movementSpeedAttribute.removeModifier(DEBUFFED_MOVEMENT_SPEED);
                }
            }
        }
        if (this.player.isInWater()) {
            this.detachSwets();
        }
        for (Iterator<Swet> iterator = this.getLatchedSwets().iterator(); iterator.hasNext(); ) {
            Swet swet = iterator.next();
            if (swet.processSucking(this.player)) {
                iterator.remove();
                this.spawnSwet(swet);
            }
        }
    }

    public void detachSwets() {
        if (!this.player.level().isClientSide()) {
            for (final Swet swet : this.getLatchedSwets()) {
                swet.setFoodSaturation(0);
                this.spawnSwet(swet);
            }
        }
        this.getLatchedSwets().clear();
        this.syncToClient = true;
    }

    public void detachSwet(final Swet swet) {
        this.getLatchedSwets().remove(swet);
        this.spawnSwet(swet);
        this.syncToClient = true;
    }

    public void spawnSwet(final Swet swet) {
        if (this.player.level() instanceof ServerLevel serverLevel) {
            // When the server loads the Swet from NBT with read() it is created in dimension 0, because this.player has not loaded yet.
            if (swet.level() != serverLevel) {
                swet.teleport(new TeleportTransition(serverLevel, this.player.position(), this.player.getDeltaMovement(), this.player.getYRot(), this.player.getXRot(), TeleportTransition.DO_NOTHING));
            } else {
                swet.setPos(this.player.position());
                this.player.level().addFreshEntity(swet);
            }
        }
    }

    public void latchSwet(final Swet swet) {
        if (this.canLatchOn()) {
            this.getLatchedSwets().add(EntityUtil.clone(swet));
            swet.discard();
            this.syncToClient = true;
        }
    }

    public boolean canLatchOn() {
        return this.getLatchedSwets().size() < MAX_SWET_COUNT;
    }

    public List<Swet> getLatchedSwets() {
        return this.swets;
    }
}
