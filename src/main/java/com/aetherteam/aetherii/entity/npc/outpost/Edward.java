package com.aetherteam.aetherii.entity.npc.outpost;

import com.aetherteam.aetherii.entity.npc.MerchantEntity;
import com.aetherteam.aetherii.entity.npc.MerchantTrades;
import com.aetherteam.aetherii.entity.npc.NpcEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Edward extends MerchantEntity {
    private static final EntityDataAccessor<Boolean> DATA_SITTING_ID = SynchedEntityData.defineId(Edward.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> DATA_HOME_POSITION_ID = SynchedEntityData.defineId(Edward.class, EntityDataSerializers.BLOCK_POS);

    public Edward(EntityType<? extends NpcEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SITTING_ID, false);
        this.entityData.define(DATA_HOME_POSITION_ID, BlockPos.ZERO);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(1, new StrollExceptWhenSitting(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag dataTag) {
        this.setHomePosition(this.blockPosition());
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData, dataTag);
    }

    @Override
    protected void updateTrades() {
        MerchantTrades.ItemListing[] listings = MerchantTrades.EDWARD_TRADES.get(1);
        if (listings != null) {
            MerchantOffers merchantOffers = this.getOffers();
            this.addOffersFromItemListings(merchantOffers, listings, 5);
            int i = this.random.nextInt(listings.length);
            MerchantTrades.ItemListing randomListing = listings[i];
            MerchantOffer merchantOffer = randomListing.getOffer(this, this.random);
            if (merchantOffer != null) {
                merchantOffers.add(merchantOffer);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSitting()) {
            this.yBodyRot = this.getYRot();
        }
    }

    @Override
    public void aiStep() {
        if (this.isSitting()) {
            if (this.getRandom().nextInt(5000) == 0) {
                this.setSitting(false);
            }
        }
        super.aiStep();
    }

    @Override
    protected float tickHeadTurn(float yRot, float yBodyRot) {
        if (!this.isSitting()) {
            return super.tickHeadTurn(yRot, yBodyRot);
        } else {
            this.yBodyRotO = this.yRotO;
            this.yBodyRot = this.getYRot();
            return yRot;
        }
    }

    public boolean isSitting() {
        return this.getEntityData().get(DATA_SITTING_ID);
    }

    public void setSitting(boolean sitting) {
        this.getEntityData().set(DATA_SITTING_ID, sitting);
    }

    public BlockPos getHomePosition() {
        return this.getEntityData().get(DATA_HOME_POSITION_ID);
    }

    public void setHomePosition(BlockPos homePosition) {
        this.getEntityData().set(DATA_HOME_POSITION_ID, homePosition);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putIntArray("HomePosition", new int[]{this.getHomePosition().getX(), this.getHomePosition().getY(), this.getHomePosition().getZ()});
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("HomePosition")) {
            int[] positions = tag.getIntArray("HomePosition");
            this.setHomePosition(new BlockPos(positions[0], positions[1], positions[2]));
        }
    }

    public static class StrollExceptWhenSitting extends WaterAvoidingRandomStrollGoal {
        protected final Edward edward;

        public StrollExceptWhenSitting(Edward edward, double speedModifier) {
            super(edward, speedModifier, 80);
            this.edward = edward;
        }

        @Override
        public boolean canUse() {
            return !this.edward.isSitting() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.edward.isSitting() && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            if (this.edward.isSitting()) {
                return null;
            } else {
                return this.edward.getRandom().nextInt(4) == 0 ? Vec3.atBottomCenterOf(this.edward.getHomePosition()) : super.getPosition();
            }
        }

        @Override
        public void stop() {
            super.stop();
            if (this.edward.getBoundingBox().inflate(2.0).contains(Vec3.atBottomCenterOf(this.edward.getHomePosition()))) {
                this.edward.setSitting(true);
            }
        }
    }
}
