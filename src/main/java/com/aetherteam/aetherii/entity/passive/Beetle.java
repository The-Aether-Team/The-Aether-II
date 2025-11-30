package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBeetleVariants;
import com.aetherteam.aetherii.data.resources.registries.AetherIIButterflyVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.BeetleVariant;
import com.aetherteam.aetherii.entity.variant.ButterflyVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Beetle extends Insect {
    private static final EntityDataAccessor<Holder<BeetleVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Beetle.class, AetherIIDataSerializers.BEETLE_VARIANT.get());

    public Beetle(EntityType<? extends Beetle> entityType, Level level) {
        super(entityType, level);
    }

    public Beetle(Level level) {
        super(AetherIIEntityTypes.BEETLE.get(), level);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIBeetleVariants.BEETLE));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof BeetleGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<BeetleVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIBeetleVariants.BEETLE_VARIANT_REGISTRY_KEY);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    public Holder<BeetleVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<BeetleVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        VariantUtils.writeVariant(output, this.getVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        VariantUtils.readVariant(input, AetherIIBeetleVariants.BEETLE_VARIANT_REGISTRY_KEY).ifPresent(this::setVariant);
    }

    public static class BeetleGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<BeetleVariant> type;

        public BeetleGroupData(Holder<BeetleVariant> type) {
            super(false);
            this.type = type;
        }
    }
}
