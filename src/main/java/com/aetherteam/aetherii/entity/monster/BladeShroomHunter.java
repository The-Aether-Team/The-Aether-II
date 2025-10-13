package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.ai.controller.CellingMoveControl;
import com.aetherteam.aetherii.entity.ai.navigator.CellingPathNavigation;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.EnumSet;
import java.util.function.IntFunction;

public class BladeShroomHunter extends CellingMonster {
    public static int ATTACK_EVENT = 100;

    private int hideCooldownTime;
    private int burryTime;
    private int rustleTime;


    public static final EntityDataAccessor<State> DATA_BURY_ID = SynchedEntityData.defineId(BladeShroomHunter.class, AetherIIDataSerializers.BLADE_SHROOM_HUNTER_STATE.get());

    public AnimationState axeAttackAnimationState = new AnimationState();
    public AnimationState buryAnimationState = new AnimationState();
    public AnimationState unburyAnimationState = new AnimationState();
    public AnimationState rustleAnimationState = new AnimationState();

    private static final EntityDimensions BURRY_DIMENSIONS = EntityDimensions.scalable(0.9F, 0.9F).withEyeHeight(0.45F);


    public BladeShroomHunter(EntityType<? extends BladeShroomHunter> p_33002_, Level p_33003_) {
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_BURY_ID, State.IDLING);
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
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, BladeShroomHunter.class).setAlertOthers());
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
            if (this.getState() == State.HIDING && this.getTarget() != null) {
                this.setState(State.UNBURY);
                this.burryTime = 0;
                this.hideCooldownTime = this.pickNextHideCooldownTime();
            } else if (this.getState() == State.IDLING && this.getTarget() == null && this.hideCooldownTime <= 0) {
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
    public void knockback(double strength, double x, double z) {
        if (this.getState() != State.HIDING && this.getState() != State.UNBURY) {
            super.knockback(strength, x, z);
        }
    }

    @Override
    protected void customServerAiStep(ServerLevel p_376725_) {
        if (this.getState() == State.IDLING) {
            if (this.getAttachFacing() != Direction.DOWN) {
                this.stopCelling();
            }
        } else {
            super.customServerAiStep(p_376725_);
        }
    }

    private int pickNextHideCooldownTime() {
        return this.random.nextInt(20 * 30) + 20 * 30;
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
    public void addAdditionalSaveData(ValueOutput p_33443_) {
        super.addAdditionalSaveData(p_33443_);
        p_33443_.store("state", State.CODEC, this.getState());
        p_33443_.putInt("hide_cooldown_time", this.hideCooldownTime);
    }

    @Override
    public void readAdditionalSaveData(ValueInput p_33432_) {
        super.readAdditionalSaveData(p_33432_);
        p_33432_.read("state", State.CODEC).ifPresent(this::setState);
        p_33432_.getInt("hide_cooldown_time").ifPresent(integer -> {
            this.hideCooldownTime = integer;
        });
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
    public EntityDimensions getDefaultDimensions(Pose p_316426_) {
        return this.getState() == State.HIDING ? BURRY_DIMENSIONS : super.getDefaultDimensions(p_316426_);
    }

    protected static class ShroomHunterMeleeAttackGoal extends MeleeAttackGoal {
        private int ticksUntilNextAttack;
        private boolean attack;
        private final float attackThresholdSqr;

        public ShroomHunterMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
            this.attackThresholdSqr = attackThreshold * attackThreshold;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) < this.attackThresholdSqr;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) < this.attackThresholdSqr;
        }

        @Override
        public void start() {
            super.start();
            this.ticksUntilNextAttack = 0;
            this.attack = false;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.mob instanceof BladeShroomHunter bladeShroomHunter && bladeShroomHunter.getState() != State.IDLING) {
                this.attack = false;
            } else {

                if ((this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) && !this.attack) {
                    this.resetAttackCooldown();
                    this.attack = true;
                }

                if (this.attack && this.ticksUntilNextAttack == 38) {
                    this.mob.level().broadcastEntityEvent(this.mob, (byte) ATTACK_EVENT);
                }

                if (this.canPerformAttack(target)) {
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    this.mob.doHurtTarget(getServerLevel(this.mob.level()), target);
                    this.mob.setZza(0.3F);
                }

                if (this.attack) {
                    --this.ticksUntilNextAttack;
                }

                if (this.ticksUntilNextAttack <= 0) {
                    this.attack = false;
                }
            }
        }

        @Override
        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(38);
        }

        @Override
        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack == 18;
        }

        @Override
        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    protected static class ChangeStateGoal extends Goal {

        private final BladeShroomHunter bladeShroom;

        public ChangeStateGoal(BladeShroomHunter mob) {
            this.bladeShroom = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
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
        public static final IntFunction<BladeShroomHunter.State> BY_ID = ByIdMap.continuous(BladeShroomHunter.State::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, BladeShroomHunter.State> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, BladeShroomHunter.State::id);
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
