package com.aetherteam.aetherii.entity.vehicle;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CloudSkiff extends VehicleEntity {
    private float deltaRotation;
    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private float landFriction;
    private Status status;
    private Status oldStatus;
    private double lastYd;

    public CloudSkiff(Level level) {
        this(AetherIIEntityTypes.CLOUD_SKIFF.get(), level);
    }

    public CloudSkiff(EntityType<CloudSkiff> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }


    public void tick() {
        this.oldStatus = this.status;
        this.status = this.getStatus();

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        super.tick();
        this.interpolation.interpolate();
        if (this.isLocalInstanceAuthoritative()) {
//            if (!(this.getFirstPassenger() instanceof Player)) {
//                this.setPaddleState(false, false);
//            }

//            this.floatBoat();
            if (this.level().isClientSide) {
                this.controlBoat();
//                this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }

        this.applyEffectsFromBlocks();
        this.applyEffectsFromBlocks();

//        for(int i = 0; i <= 1; ++i) {
//            if (this.getPaddleState(i)) {
//                if (!this.isSilent() && this.paddlePositions[i] % ((float)Math.PI * 2F) <= ((float)Math.PI / 4F) && (this.paddlePositions[i] + ((float)Math.PI / 8F)) % ((float)Math.PI * 2F) >= ((float)Math.PI / 4F)) {
//                    SoundEvent soundevent = this.getPaddleSound();
//                    if (soundevent != null) {
//                        Vec3 vec3 = this.getViewVector(1.0F);
//                        double d0 = i == 1 ? -vec3.z : vec3.z;
//                        double d1 = i == 1 ? vec3.x : -vec3.x;
//                        this.level().playSound((Entity)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
//                    }
//                }
//
//                this.paddlePositions[i] += ((float)Math.PI / 8F);
//            } else {
//                this.paddlePositions[i] = 0.0F;
//            }
//        }

//        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelector.pushableBy(this));
//        if (!list.isEmpty()) {
//            boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);
//
//            for(Entity entity : list) {
//                if (!entity.hasPassenger(this)) {
//                    if (flag && this.getPassengers().size() < this.getMaxPassengers() && !entity.isPassenger() && this.hasEnoughSpaceFor(entity) && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player) && !(entity instanceof Creaking)) {
//                        entity.startRiding(this);
//                    } else {
//                        this.push(entity);
//                    }
//                }
//            }
//        }

    }

    private void controlBoat() {
        if (this.isVehicle()) {
            float f = 0.0F;
            if (this.inputLeft) {
                --this.deltaRotation;
            }

            if (this.inputRight) {
                ++this.deltaRotation;
            }

            if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                f += 0.005F;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);
            if (this.inputUp) {
                f += 0.04F;
            }

            if (this.inputDown) {
                f -= 0.005F;
            }

            this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * Mth.DEG_TO_RAD) * f, 0.0F, Mth.cos(this.getYRot() * Mth.DEG_TO_RAD) * f));
//            this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
        }

    }

    private Status getStatus() { //todo
        BlockState belowState = this.level().getBlockState(this.getOnPos());
        if (belowState.is(AetherIITags.Blocks.AERCLOUDS)) {
            return Status.ON_CLOUDS;
        } else {
            float f = belowState.getFriction(this.level(), this.getOnPos(), this);
            if (f > 0.0F) {
                this.landFriction = f;
                return Status.ON_LAND;
            } else {
                return Status.IN_AIR;
            }
        }
    }

    public void setInput(boolean left, boolean right, boolean up, boolean down) { //todo
        this.inputLeft = left;
        this.inputRight = right;
        this.inputUp = up;
        this.inputDown = down;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult result = super.interact(player, hand);
        return result != InteractionResult.PASS ? result : (!player.isSecondaryUseActive() && (this.level().isClientSide() || player.startRiding(this)) ? InteractionResult.SUCCESS : InteractionResult.PASS);
    }

    @Override
    public void animateHurt(float p_376617_) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float p_376713_) {
        float f = 0.0F;
        if (this.getPassengers().size() > 1) {
            int i = this.getPassengers().indexOf(entity);
            if (i == 0) {
                f = 0.2F;
            } else {
                f = -0.6F;
            }

            if (entity instanceof Animal) {
                f += 0.2F;
            }
        }

        return new Vec3(0.0F, dimensions.height() / 3.0F, f).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
    }

    @Override
    public void push(Entity entity) {
        if (entity instanceof CloudSkiff) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(entity);
        }
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return canVehicleCollide(this, entity);
    }

    public static boolean canVehicleCollide(Entity first, Entity second) {
        return (second.canBeCollidedWith(first) || second.isPushable()) && !first.isPassengerOfSameVehicle(second);
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    protected Item getDropItem() {
        return Items.AIR; //todo
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public Vec3 getRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle rectangle) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(axis, rectangle));
    }

    @Override
    public InterpolationHandler getInterpolation() {
        return this.interpolation;
    }

    @Override
    protected void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
        super.positionRider(entity, moveFunction);
        if (!entity.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)) {
            entity.setYRot(entity.getYRot() + this.deltaRotation);
            entity.setYHeadRot(entity.getYHeadRot() + this.deltaRotation);
            this.clampRotation(entity);
//            if (entity instanceof Animal && this.getPassengers().size() == this.getMaxPassengers()) {
//                int i = entity.getId() % 2 == 0 ? 90 : 270;
//                entity.setYBodyRot(((Animal)entity).yBodyRot + (float)i);
//                entity.setYHeadRot(entity.getYHeadRot() + (float)i);
//            }
        }

    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, passenger.getBbWidth(), passenger.getYRot());
        double x = this.getX() + vec3.x;
        double z = this.getZ() + vec3.z;
        BlockPos blockPos = BlockPos.containing(x, this.getBoundingBox().maxY, z);
        BlockPos belowPos = blockPos.below();
        if (!this.level().isWaterAt(belowPos)) {
            List<Vec3> list = Lists.newArrayList();
            double y = this.level().getBlockFloorHeight(blockPos);
            if (DismountHelper.isBlockFloorValid(y)) {
                list.add(new Vec3(x, (double)blockPos.getY() + y, z));
            }

            double belowY = this.level().getBlockFloorHeight(belowPos);
            if (DismountHelper.isBlockFloorValid(belowY)) {
                list.add(new Vec3(x, (double)belowPos.getY() + belowY, z));
            }

            for (Pose pose : passenger.getDismountPoses()) {
                for (Vec3 pos : list) {
                    if (DismountHelper.canDismountTo(this.level(), pos, passenger, pose)) {
                        passenger.setPose(pose);
                        return pos;
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(passenger);
    }

    protected void clampRotation(Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += f1 - f;
        entity.setYRot(entity.getYRot() + f1 - f);
        entity.setYHeadRot(entity.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity entity) {
        this.clampRotation(entity);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        LivingEntity controller;
        if (entity instanceof LivingEntity livingentity) {
            controller = livingentity;
        } else {
            controller = super.getControllingPassenger();
        }
        return controller;
    }

    @Override
    protected void checkFallDamage(double dist, boolean p_376924_, BlockState state, BlockPos pos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (p_376924_) {
                this.resetFallDistance();
            }
        }

    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {

    }

    public enum Status {
        ON_CLOUDS,
        ON_LAND,
        IN_AIR;
    }
}
