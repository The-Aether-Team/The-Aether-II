package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.network.packet.clientbound.ReattachedEntityPacket;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

public class AttachedLivingAttachment {
    @Nullable
    private LivingEntity attachingTo;


    @Nullable
    private LivingEntity attachedEntity;
    private Optional<CompoundTag> attachedEntityTag = Optional.empty();

    public static final MapCodec<AttachedLivingAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CompoundTag.CODEC.optionalFieldOf("attached_entity").forGetter(AttachedLivingAttachment::getAttachedEntityTag)
    ).apply(instance, AttachedLivingAttachment::new));

    protected AttachedLivingAttachment(Optional<CompoundTag> attachedEntityTag) {
        this.attachedEntityTag = attachedEntityTag;
    }

    public AttachedLivingAttachment() {
    }

    public void login(LivingEntity living) {
        this.reattachEntity(living);
    }


    public void postTickUpdate(LivingEntity entity) {
        LivingEntity attached = this.getAttachedEntity();
        if (attached != null) {
            this.checkToRemoveAttach(entity, attached);
            this.tickAttached(entity, attached);
        }
    }

    private void tickAttached(LivingEntity entity, LivingEntity attached) {
        Vec3 viewVector = entity.getViewVector(1.0F);
        attached.setDeltaMovement(Vec3.ZERO);
        Vec3 vec3 = new Vec3(entity.getX() + entity.getBbWidth() * viewVector.x * 0.35F, entity.getY() + entity.getBbHeight() * 0.35F, entity.getZ() + entity.getBbWidth() * viewVector.z * 0.35F);
        attached.moveOrInterpolateTo(vec3);
        attached.resetFallDistance();
        if (attached instanceof Mob mob) {
            mob.lookAt(entity, 360F, 360F);
        }
    }

    /**
     * Removes an Attached Entity from the world and stores it to NBT for the capability. This is used when a target mob with an Attached Entity.
     */
    public void removeEntity() {
        if (this.getAttachedEntity() != null) {
            try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(this.getAttachedEntity().problemPath(), AetherII.LOGGER)) {
                TagValueOutput valueOutput = TagValueOutput.createWithContext(collector, this.getAttachedEntity().registryAccess());
                this.getAttachedEntity().saveAsPassenger(valueOutput);
                this.setAttachedEntityTag(Optional.of(valueOutput.buildResult()));
                this.getAttachedEntity().setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            }
        }
    }

    /**
     * Reattach an Entity to the Target Entity if there exists stored NBT when joining the world.
     */
    public void reattachEntity(LivingEntity living) {
        if (this.getAttachedEntity() != null) {
            if (!living.level().isClientSide()) {
                try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(living.problemPath(), AetherII.LOGGER)) {

                    ValueInput valueInput = TagValueInput.create(collector, living.registryAccess(), this.getAttachedEntityTag().get());
                    Optional<Entity> entity = EntityType.create(valueInput, living.level(), EntitySpawnReason.LOAD);

                    if (entity.isPresent()) {
                        living.level().addFreshEntity(entity.get());
                        if (entity.get() instanceof LivingEntity attached) {
                            this.setAttachedEntity(attached);
                            attached.getData(AetherIIDataAttachments.ATTACH_LIVING).setAttachingTo(living);
                        }
                        PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new ReattachedEntityPacket(living.getId(), entity.get().getId()));
                    }
                }
            }
            this.setAttachedEntityTag(Optional.empty());
        }
    }


    /**
     * Checks whether the capability should stop tracking a Attached Entity.
     */
    private void checkToRemoveAttach(LivingEntity entity, LivingEntity attached) {
        if ((attached == null || !attached.isAlive() || !entity.isAlive())) {
            this.setAttachedEntity(null);
        }
    }

    protected void setAttachedEntity(@Nullable LivingEntity attachedEntity) {
        this.attachedEntity = attachedEntity;
    }

    public void attachEntity(LivingEntity living, @Nullable LivingEntity attachedEntity) {
        this.setAttachedEntity(attachedEntity);
        if (!living.level().isClientSide() && attachedEntity != null) {
            attachedEntity.getData(AetherIIDataAttachments.ATTACH_LIVING).setAttachingTo(living);
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new ReattachedEntityPacket(living.getId(), attachedEntity.getId()));
        }
    }

    /**
     * @return The {@link LivingEntity} currently Attached Mob to the Entity
     */
    @Nullable
    public LivingEntity getAttachedEntity() {
        return this.attachedEntity;
    }


    public void setAttachingTo(@Nullable LivingEntity attachingEntity) {
        this.attachingTo = attachingEntity;
    }

    /**
     * @return The {@link LivingEntity} currently Attaching to
     */
    @Nullable
    public LivingEntity getAttachingTo() {
        return attachingTo;
    }

    public void setAttachedEntityTag(Optional<CompoundTag> attachedEntityTag) {
        this.attachedEntityTag = attachedEntityTag;
    }


    /**
     * @return The {@link CompoundTag} data for the LivingEntity currently attached to the Entity.
     */
    public Optional<CompoundTag> getAttachedEntityTag() {
        return this.attachedEntityTag;
    }
}