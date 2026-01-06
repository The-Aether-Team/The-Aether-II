package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.data.resources.registries.AetherIIShroudwingVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
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

public class Shroudwing extends Insect {
    private static final EntityDataAccessor<Holder<ShroudwingVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Shroudwing.class, AetherIIDataSerializers.SHROUDWING_VARIANT.get());

    public Shroudwing(EntityType<? extends Shroudwing> entityType, Level level) {
        super(entityType, level);
    }

    public Shroudwing(Level level) {
        super(AetherIIEntityTypes.SHROUDWING.get(), level);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIShroudwingVariants.BEETLE));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof BeetleGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<ShroudwingVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIShroudwingVariants.SHROUDWING_VARIANT_REGISTRY_KEY);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    public Holder<ShroudwingVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<ShroudwingVariant> variant) {
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
        VariantUtils.readVariant(input, AetherIIShroudwingVariants.SHROUDWING_VARIANT_REGISTRY_KEY).ifPresent(this::setVariant);
    }

    public static class BeetleGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<ShroudwingVariant> type;

        public BeetleGroupData(Holder<ShroudwingVariant> type) {
            super(false);
            this.type = type;
        }
    }
}
