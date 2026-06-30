package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.network.packet.clientbound.SwetSyncPacket;
import com.google.common.collect.Lists;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.util.nbt.ValueInput;
import com.aetherteam.aetherii.util.nbt.ValueOutput;
import com.aetherteam.aetherii.util.nbt.ValueIOSerializable;
import com.aetherteam.aetherii.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

public class SwetLatchAttachment implements ValueIOSerializable {
    public static final ResourceLocation DEBUFFED_MOVEMENT_SPEED = new ResourceLocation(AetherII.MODID, "player.debuff.swet_movement_speed");
    private static final UUID DEBUFFED_MOVEMENT_SPEED_UUID = AetherIIStats.uuid(DEBUFFED_MOVEMENT_SPEED);
    public static final int MAX_SWET_COUNT = 3;


    @Override
    public void serialize(ValueOutput valueOutput) {
        ListTag list = new ListTag();
        try {
            for (Swet swet : this.getLatchedSwets()) {
                String id = swet.getEncodeId();
                if (id != null) {
                    CompoundTag element = new CompoundTag();
                    element.putString("id", id);
                    swet.saveWithoutId(element);
                    list.add(element);
                }
            }
            valueOutput.buildResult().put("swets", list);
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Saving entity NBT");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being saved");
            this.player.fillCrashReportCategory(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public void deserialize(ValueInput valueInput) {
        ValueInput.ValueInputList list = valueInput.childrenListOrEmpty("swets");

        this.getLatchedSwets().clear();
        list.stream().forEach(element -> EntityType.create(element.compoundTag(), this.player.level()).ifPresent((entity) -> {
            if (entity instanceof Swet swet) {
                this.getLatchedSwets().add(swet);
                this.syncToClient = !this.player.level().isClientSide();
            }
        }));
    }
    private final Player player;
    private final List<Swet> swets = Lists.newArrayList();
    private boolean syncToClient = false;

    public SwetLatchAttachment(Player player) {
        this.player = player;
    }

    public void postTickUpdate() {
        if (!this.player.level().isClientSide()) {
            this.handleSwetTick();
            if (this.syncToClient) {
                ValueOutput valueOutput = new ValueOutput();
                this.serialize(valueOutput);
                PacketDistributor.sendToAllPlayers(new SwetSyncPacket(this.player.getId(), valueOutput.buildResult()));
                this.syncToClient = false;
            }
        }
    }

    public void handleSwetTick() {
        AttributeInstance movementSpeedAttribute = this.player.getAttribute(Attributes.MOVEMENT_SPEED);
        double value = -0.15 * this.swets.size();
        if (movementSpeedAttribute != null) {
            AttributeModifier currentModifier = movementSpeedAttribute.getModifier(DEBUFFED_MOVEMENT_SPEED_UUID);
            if (currentModifier == null || currentModifier.getAmount() != value) {
                movementSpeedAttribute.removeModifier(DEBUFFED_MOVEMENT_SPEED_UUID);
                if (value != 0.0F) {
                    movementSpeedAttribute.addTransientModifier(new AttributeModifier(DEBUFFED_MOVEMENT_SPEED_UUID, DEBUFFED_MOVEMENT_SPEED.toString(), value, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }

        if (!this.getLatchedSwets().isEmpty()) {
            if (this.player.tickCount % 20 == 0) {
                this.player.level().playSound(null, this.player.getX(), this.player.getY(), this.player.getZ(), AetherIISoundEvents.ENTITY_SWET_LEECH.get(), SoundSource.HOSTILE, 1.0F, ((this.player.getRandom().nextFloat() - this.player.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }
        }

        if (this.player.isInWater()) {
            this.detachSwets();
            return;
        }
        for (Swet swet : List.copyOf(this.getLatchedSwets())) {
            if (!this.getLatchedSwets().contains(swet)) {
                continue;
            }
            if (swet.processSucking(this.player)) {
                this.getLatchedSwets().remove(swet);
                this.spawnSwet(swet);
                this.syncToClient = true;
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
                if (swet.changeDimension(serverLevel) instanceof Swet movedSwet) {
                    movedSwet.moveTo(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.getYRot(), this.player.getXRot());
                    movedSwet.setDeltaMovement(this.player.getDeltaMovement());
                }
            } else {
                swet.setPos(this.player.position());
                this.player.level().addFreshEntity(swet);
            }
        }
    }

    public void latchSwet(final Swet swet) {
        if (!this.player.level().isClientSide() && this.canLatchOn()) {
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
