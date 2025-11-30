package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.variant.ButterflyVariant;
import com.aetherteam.aetherii.data.resources.registries.AetherIIButterflyVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
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

public class Butterfly extends Insect {
    private static final EntityDataAccessor<Holder<ButterflyVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Butterfly.class, AetherIIDataSerializers.BUTTERFLY_VARIANT.get());

    public Butterfly(EntityType<? extends Butterfly> entityType, Level level) {
        super(entityType, level);
    }

    public Butterfly(Level level) {
        super(AetherIIEntityTypes.BUTTERFLY.get(), level);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIButterflyVariants.GLITTERWING));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof ButterflyGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<ButterflyVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    public Holder<ButterflyVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<ButterflyVariant> variant) {
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
        VariantUtils.readVariant(input, AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY).ifPresent(this::setVariant);
    }

    public static class ButterflyGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<ButterflyVariant> type;

        public ButterflyGroupData(Holder<ButterflyVariant> type) {
            super(false);
            this.type = type;
        }
    }
}
