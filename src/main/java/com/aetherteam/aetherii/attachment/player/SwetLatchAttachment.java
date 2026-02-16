package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.network.packet.clientbound.SwetSyncPacket;
import com.google.common.collect.Lists;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Iterator;
import java.util.List;

public class SwetLatchAttachment implements ValueIOSerializable {
    public static final ResourceLocation DEBUFFED_MOVEMENT_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "player.debuff.swet_movement_speed");
    public static final int MAX_SWET_COUNT = 3;


    @Override
    public void serialize(ValueOutput valueOutput) {
        ValueOutput.ValueOutputList output = valueOutput.childrenList("swets");
        try {
            for (Swet swet : this.getLatchedSwets()) {
                String id = swet.getEncodeId();
                if (id != null) {
                    ValueOutput element = output.addChild();
                    element.putString("id", id);
                    swet.saveWithoutId(element);
                }
            }
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
        list.stream().forEach(element -> EntityType.create(element, this.player.level(), EntitySpawnReason.TRIGGERED).ifPresent((entity) -> {
            if (entity instanceof Swet swet) {
                this.getLatchedSwets().add(swet);
                this.syncToClient = true;
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
        if (this.syncToClient) {
            if (!this.player.level().isClientSide()) {
                try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(this.player.problemPath(), AetherII.LOGGER)) {
                    TagValueOutput tagvalueoutput = TagValueOutput.createWithContext(problemreporter$scopedcollector, this.player.registryAccess());
                    this.serialize(tagvalueoutput);
                    PacketDistributor.sendToAllPlayers(new SwetSyncPacket(this.player.getId(), tagvalueoutput.buildResult()));
                }


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

        if (!this.getLatchedSwets().isEmpty()) {
            if (this.player.tickCount % 20 == 0) {
                this.player.level().playLocalSound(this.player, AetherIISoundEvents.ENTITY_SWET_LEECH.get(), SoundSource.HOSTILE, 1.0F, ((this.player.getRandom().nextFloat() - this.player.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
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
