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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
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
import java.util.EnumSet;

public class Edward extends MerchantEntity {
    private static final EntityDataAccessor<Boolean> DATA_SITTING_ID = SynchedEntityData.defineId(Edward.class, EntityDataSerializers.BOOLEAN);
    private BlockPos sittingPosition;

    public Edward(EntityType<? extends NpcEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SITTING_ID, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(1, new StrollExceptWhenSitting(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new SitGoal(this, 1.0));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setSittingPosition(this.blockPosition());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
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
            this.clampHeadRotationToBody();
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
    protected float tickHeadTurn(float yRot, float animStep) {
        if (!this.isSitting()) {
            return super.tickHeadTurn(yRot, animStep);
        }
        return this.yBodyRot;
    }

    public boolean isSitting() {
        return this.getEntityData().get(DATA_SITTING_ID);
    }

    public void setSitting(boolean sitting) {
        this.getEntityData().set(DATA_SITTING_ID, sitting);
    }

    public void setSittingPosition(BlockPos sittingPosition) {
        this.sittingPosition = sittingPosition;
    }

    public BlockPos getSittingPosition() {
        return this.sittingPosition;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putIntArray("SittingPosition", new int[]{this.getSittingPosition().getX(), this.getSittingPosition().getY(), this.getSittingPosition().getZ()});
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("SittingPosition")) {
            int[] positions = tag.getIntArray("SittingPosition");
            this.setSittingPosition(new BlockPos(positions[0], positions[1], positions[2]));
        }
    }

    public static class StrollExceptWhenSitting extends WaterAvoidingRandomStrollGoal {
        protected final Edward edward;

        public StrollExceptWhenSitting(Edward edward, double speedModifier) {
            super(edward, speedModifier, 80);
            this.edward = edward;
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            if (this.edward.isSitting()) {
                return null;
            }
            return super.getPosition();
        }
    }

    public static class SitGoal extends Goal {
        public static final int DEFAULT_INTERVAL = 120;
        protected final Edward edward;
        protected double wantedX;
        protected double wantedY;
        protected double wantedZ;
        protected final double speedModifier;
        protected int interval;
        protected boolean forceTrigger;
        private final boolean checkNoActionTime;

        public SitGoal(Edward edward, double speedModifier) {
            this(edward, speedModifier, 750);
        }

        public SitGoal(Edward edward, double speedModifier, int interval) {
            this(edward, speedModifier, interval, true);
        }

        public SitGoal(Edward edward, double speedModifier, int interval, boolean checkNoActionTime) {
            this.edward = edward;
            this.speedModifier = speedModifier;
            this.interval = interval;
            this.checkNoActionTime = checkNoActionTime;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.edward.hasControllingPassenger()) {
                return false;
            } else {
                if (!this.forceTrigger) {
                    if (!this.edward.getNavigation().isDone()) {
                        return false;
                    }

                    if (this.checkNoActionTime && this.edward.getNoActionTime() >= 100) {
                        return false;
                    }

                    if (this.edward.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                        return false;
                    }
                }

                Vec3 vec3 = this.getPosition();
                if (vec3 == null) {
                    return false;
                } else {
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                    this.forceTrigger = false;
                    return true;
                }
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            if (this.edward.isSitting()) {
                return null;
            }
            return this.edward.getSittingPosition().getBottomCenter();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.edward.getNavigation().isDone() && !this.edward.hasControllingPassenger();
        }

        @Override
        public void start() {
            this.edward.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        @Override
        public void stop() {
            this.edward.setSitting(true);
            this.edward.getNavigation().stop();
            super.stop();
        }

        public void trigger() {
            this.forceTrigger = true;
        }

        public void setInterval(int newchance) {
            this.interval = newchance;
        }
    }
}
