package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.ai.controller.CellingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.ClosedAnimationMeleeAttackGoal;
import com.aetherteam.aetherii.entity.ai.navigator.CellingPathNavigation;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumSet;
import java.util.function.IntFunction;

public class BladeshroomHunter extends CellingMonster {
    public static int ATTACK_EVENT = 100;

    private int hideCooldownTime;
    private int burryTime;
    private int rustleTime;


    public static final EntityDataAccessor<State> DATA_BURY_ID = SynchedEntityData.defineId(BladeshroomHunter.class, AetherIIDataSerializers.BLADESHROOM_HUNTER_STATE.get());

    public AnimationState axeAttackAnimationState = new AnimationState();
    public AnimationState buryAnimationState = new AnimationState();
    public AnimationState unburyAnimationState = new AnimationState();
    public AnimationState rustleAnimationState = new AnimationState();

    private static final EntityDimensions BURRY_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.9F);


    public BladeshroomHunter(EntityType<? extends BladeshroomHunter> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.hideCooldownTime = this.pickNextHideCooldownTime();
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_316145_) {
        if (DATA_BURY_ID.equals(p_316145_)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_316145_);
    }


    private void normalPathSetup() {
        this.moveControl = new MoveControl(this);
        this.navigation = this.createNavigation(this.level());
    }

    private void cellingSetup() {
        this.moveControl = new CellingMoveControl(this);
        this.navigation = new CellingPathNavigation(this, level());
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BURY_ID, State.IDLING);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ChangeStateGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new ShroomHunterMeleeAttackGoal(this, 1.15F, true, 6.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, BladeshroomHunter.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {

            if (this.getState() == State.BURY_START || this.getState() == State.UNBURY) {
                ++this.burryTime;
            } else if (this.getState() == State.IDLING) {
                --this.hideCooldownTime;
            }

            boolean changeToUnburryStateFlag = this.getTarget() != null && this.onGround();
            boolean changeToBurryStateFlag = !this.isPassenger() && this.getTarget() == null && this.onGround();

            if (this.getState() == State.HIDING && changeToUnburryStateFlag) {
                this.setState(State.UNBURY);
                this.burryTime = 0;
                this.hideCooldownTime = this.pickNextHideCooldownTime();
            } else if (this.getState() == State.IDLING && changeToBurryStateFlag && this.hideCooldownTime <= 0) {
                this.setState(State.BURY_START);
                this.burryTime = 0;
                this.hideCooldownTime = this.pickNextHideCooldownTime();
            }


            if (this.getState() == State.BURY_START && this.burryTime >= 20 * 1.8F) {
                this.setState(State.HIDING);
            }
            if (this.getState() == State.UNBURY && this.burryTime >= 20 * 1.38F) {
                this.setState(State.IDLING);
            }
        }
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        boolean flag = super.startRiding(entity, force);
        if (flag) {
            this.setState(State.IDLING);
        }
        return flag;
    }

    @Override
    public void knockback(double strength, double x, double z) {
        if (this.getState() != State.HIDING && this.getState() != State.UNBURY) {
            super.knockback(strength, x, z);
        }
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel p_376725_ = (ServerLevel) this.level();
        if (this.getState() == State.IDLING) {
            if (this.getAttachFacing() != Direction.DOWN) {
                this.stopCelling();
            }
        } else {
            super.customServerAiStep();
        }
    }

    private int pickNextHideCooldownTime() {
        return this.random.nextInt(20 * 10) + 20 * 10;
    }

    private void setupAnimationStates() {
        switch (this.getState()) {
            case IDLING:
                this.rustleAnimationState.stop();
                this.buryAnimationState.stop();
                this.unburyAnimationState.stop();
                break;
            case BURY_START:
                this.rustleAnimationState.stop();
                this.unburyAnimationState.stop();
                this.buryAnimationState.startIfStopped(this.tickCount);
                break;
            case UNBURY:
                this.rustleAnimationState.stop();
                this.buryAnimationState.stop();
                this.unburyAnimationState.startIfStopped(this.tickCount);
                break;
            case HIDING:
                this.rustleAnimationState.startIfStopped(this.tickCount);
                this.buryAnimationState.stop();
                this.unburyAnimationState.stop();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_EVENT) {
            this.axeAttackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("state", this.getState().id());
        tag.putInt("hide_cooldown_time", this.hideCooldownTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("state")) {
            this.setState(State.BY_ID.apply(tag.getInt("state")));
        }
        if (tag.contains("hide_cooldown_time")) {
            this.hideCooldownTime = tag.getInt("hide_cooldown_time");
        }
    }

    public State getState() {
        return this.getEntityData().get(DATA_BURY_ID);
    }

    public void setState(State state) {
        if (state != this.getState()) {
            if (state == State.BURY_START || state == State.HIDING) {
                this.cellingSetup();
            } else {
                this.normalPathSetup();
            }
        }

        this.getEntityData().set(DATA_BURY_ID, state);
    }

    @Override
    public void stopCelling() {
        super.stopCelling();
        this.setState(State.UNBURY);
        this.burryTime = 0;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.getState() == State.HIDING ? BURRY_DIMENSIONS : super.getDimensions(pose);
    }

    protected static class ShroomHunterMeleeAttackGoal extends ClosedAnimationMeleeAttackGoal {
        public ShroomHunterMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen, 18, 38, attackThreshold);
        }

        @Override
        public void attackAnimation() {
            this.mob.level().broadcastEntityEvent(this.mob, (byte) ATTACK_EVENT);
        }

        @Override
        public void attackAction() {
            super.attackAction();
            this.mob.setZza(0.3F);
        }
    }

    protected static class ChangeStateGoal extends Goal {

        private final BladeshroomHunter bladeShroom;

        public ChangeStateGoal(BladeshroomHunter mob) {
            this.bladeShroom = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.bladeShroom.getState() == State.BURY_START || this.bladeShroom.getState() == State.UNBURY;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    public static enum State implements StringRepresentable {
        IDLING("idle", 0),
        BURY_START("bury", 1),
        UNBURY("unbury", 2),
        HIDING("hiding", 3);

        static final Codec<State> CODEC = StringRepresentable.fromEnum(State::values);
        public static final IntFunction<BladeshroomHunter.State> BY_ID = ByIdMap.continuous(BladeshroomHunter.State::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, BladeshroomHunter.State> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, BladeshroomHunter.State::id);
        private final String name;
        private final int id;

        private State(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public int id() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
