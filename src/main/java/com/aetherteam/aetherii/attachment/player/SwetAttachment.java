package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.network.packet.clientbound.SwetSyncPacket;
import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Iterator;
import java.util.List;

public class SwetAttachment implements INBTSerializable<CompoundTag> { //todo merge into player atttachment
    public static final int MAX_SWET_COUNT = 3;

    private final List<Swet> swets = Lists.newArrayList();


    private final Player entity;
    private boolean loadingSync = false;

    public SwetAttachment(Player entity) {
        this.entity = entity;
    }

    public List<Swet> getLatchedSwets() {
        return this.swets;
    }

    public void detachSwets() {
        if (!this.entity.level().isClientSide()) {
            for (final Swet swet : this.swets) {
                swet.setFoodSaturation(0);
                this.spawnSwet(swet);
            }
        }

        this.swets.clear();
        this.loadingSync = true;
    }

    public void detachSwet(final Swet swet) {
        this.swets.remove(swet);
        this.spawnSwet(swet);
        this.loadingSync = true;
    }

    public void spawnSwet(final Swet swet) {
        if (this.entity.level() instanceof ServerLevel serverLevel) {
            swet.setPos(this.entity.position());

            //When the server loads the swet from nbt with read() it is created in dimension 0, because the this.entity has not loaded yet
            if (swet.level() != serverLevel) {
                swet.changeDimension(new DimensionTransition(serverLevel, this.entity.position(), this.entity.getDeltaMovement(), this.entity.getYRot(), this.entity.getXRot(), DimensionTransition.DO_NOTHING));
            } else {
                this.entity.level().addFreshEntity(swet);
            }
        }
    }

    public boolean canLatchOn() {
        return this.swets.size() < MAX_SWET_COUNT;
    }

    public void latchSwet(final Swet swet) {
        if (!this.canLatchOn()) {
            return;
        }

        this.swets.add(EntityUtil.clone(swet));
        swet.discard();
        this.loadingSync = true;
    }

    public void handleSwetTick() {
        if (this.entity.isInWater()) {
            this.detachSwets();
        }

        final Iterator<Swet> it = this.swets.iterator();

        while (it.hasNext()) {
            final Swet swet = it.next();

            if (swet.processSucking(this.entity)) {
                it.remove();
                this.spawnSwet(swet);
            }
        }
    }

    public void postTickUpdate(Player player) {
        if (this.loadingSync) {
            if (!this.entity.level().isClientSide()) {
                PacketDistributor.sendToAllPlayers(new SwetSyncPacket(this.entity.getId(), this.serializeNBT(this.entity.registryAccess())));
            }
            this.loadingSync = false;
        }
        this.handleSwetTick();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        final ListTag list = new ListTag();
        CompoundTag compoundTag = new CompoundTag();

        for (final Swet swet : this.swets) {
            final CompoundTag tag = new CompoundTag();

            swet.addAdditionalSaveData(tag);

            list.add(tag);
        }


        compoundTag.put("swets", list);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        final ListTag list = tag.getList("swets", 10);

        this.swets.clear();
        for (int i = 0; i < list.size(); i++) {
            final CompoundTag compound = list.getCompound(i);
            compound.remove("Dimension");

            final Swet swet = AetherIIEntityTypes.SWET.get().create(this.entity.level());
            swet.readAdditionalSaveData(compound);

            this.loadingSync = true;
            this.swets.add(swet);
        }
    }
}
