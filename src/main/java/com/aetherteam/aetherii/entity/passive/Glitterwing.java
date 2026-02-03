package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIGlitterwingVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Glitterwing extends Insect {
    private static final EntityDataAccessor<Holder<GlitterwingVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Glitterwing.class, AetherIIDataSerializers.GLITTERWING_VARIANT.get());
    public static int LAND_EVENT = 101;
    public static int TAKE_OFF_EVENT = 102;
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState takeOffAnimationState = new AnimationState();

    public Glitterwing(EntityType<? extends Glitterwing> entityType, Level level) {
        super(entityType, level);
    }

    public Glitterwing(Level level) {
        super(AetherIIEntityTypes.GLITTERWING.get(), level);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIGlitterwingVariants.INDIGO));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof GlitterwingGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<GlitterwingVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIRegistries.GLITTERWING_VARIANT);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    public Holder<GlitterwingVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<GlitterwingVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        VariantUtils.writeVariant(valueOutput, this.getVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        VariantUtils.readVariant(valueInput, AetherIIRegistries.GLITTERWING_VARIANT).ifPresent(this::setVariant);
    }


    @Override
    public void setRestWithAnimation(boolean rest) {
        super.setRestWithAnimation(rest);
        if (rest) {
            this.level().broadcastEntityEvent(this, (byte) LAND_EVENT);
        } else {
            this.level().broadcastEntityEvent(this, (byte) TAKE_OFF_EVENT);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == LAND_EVENT) {
            this.takeOffAnimationState.stop();
            this.landAnimationState.start(this.tickCount);
        } else if (id == TAKE_OFF_EVENT) {
            this.landAnimationState.stop();
            this.takeOffAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public static class GlitterwingGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<GlitterwingVariant> type;

        public GlitterwingGroupData(Holder<GlitterwingVariant> type) {
            super(false);
            this.type = type;
        }
    }
}
