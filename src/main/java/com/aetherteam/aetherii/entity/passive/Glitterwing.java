package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIGlitterwingVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import com.aetherteam.aetherii.entity.variant.SpawnContext;
import com.aetherteam.aetherii.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, this.level().registryAccess().registryOrThrow(AetherIIRegistries.GLITTERWING_VARIANT).getHolder(AetherIIGlitterwingVariants.INDIGO).orElseThrow());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnData instanceof GlitterwingGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<GlitterwingVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIRegistries.GLITTERWING_VARIANT);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public Holder<GlitterwingVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<GlitterwingVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.getVariant().unwrapKey().ifPresent(key -> tag.putString("variant", key.location().toString()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("variant")) {
            ResourceKey<GlitterwingVariant> key = ResourceKey.create(AetherIIRegistries.GLITTERWING_VARIANT, new ResourceLocation(tag.getString("variant")));
            this.level().registryAccess().registryOrThrow(AetherIIRegistries.GLITTERWING_VARIANT).getHolder(key).ifPresent(this::setVariant);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_218498_) {
        if (DATA_REST.equals(p_218498_)) {
            if (this.isRest()) {
                this.takeOffAnimationState.stop();
                this.landAnimationState.start(this.tickCount);
            } else {
                this.landAnimationState.stop();
                this.takeOffAnimationState.start(this.tickCount);
            }
        }

        super.onSyncedDataUpdated(p_218498_);
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
