package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.entity.ai.controller.CellingMoveControl;
import com.aetherteam.aetherii.entity.ai.navigator.CellingPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CellingMonster extends Monster {
    private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(CellingMonster.class, EntityDataSerializers.DIRECTION);

    private boolean isUpsideDownNavigator;
    public float attachChangeProgress;
    public float prevAttachChangeProgress;
    public Direction prevAttachDir = Direction.DOWN;

    protected CellingMonster(EntityType<? extends CellingMonster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        switchNavigator(true);
        this.moveControl = new CellingMoveControl(this);
        this.navigation = new CellingPathNavigation(this, level());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACHED_FACE, Direction.DOWN);
    }

    @Override
    public void travel(Vec3 p_32394_) {
        if (this.isControlledByLocalInstance() && this.getAttachFacing() != Direction.DOWN) {
            this.moveRelative(0.1F, p_32394_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.6));
            this.calculateEntityAnimation(true);
        } else {
            super.travel(p_32394_);
        }
    }

    public void tick() {
        this.prevAttachChangeProgress = this.attachChangeProgress;
        super.tick();
        if (attachChangeProgress > 0F) {
            attachChangeProgress -= 0.1F;
        }
        Vec3 vector3d = this.getDeltaMovement();
        if (!this.level().isClientSide) {
            boolean flag = this.moveControl instanceof CellingMoveControl && ((CellingMoveControl) this.moveControl).isWalkableUpper();
            boolean flag2 = this.moveControl.hasWanted() && this.moveControl.getWantedY() - this.getY() > 0;

            if (!flag && !flag2 && (this.onGround() || this.isInWaterOrBubble() || this.isInLava() || this.isInFluidType())) {
                this.entityData.set(ATTACHED_FACE, Direction.DOWN);
            } else if (this.verticalCollision && !flag && !flag2) {
                this.entityData.set(ATTACHED_FACE, Direction.UP);
            } else {
                Direction closestDirection = null;
                double closestDistance = 100D;
                for (Direction dir : Direction.values()) {
                    if (dir != Direction.DOWN) {
                        BlockPos antPos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
                        BlockPos offsetPos = antPos.relative(dir);
                        Vec3 offset = Vec3.atCenterOf(offsetPos);
                        if (closestDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(offsetPos, this, dir.getOpposite())) {
                            closestDistance = this.position().distanceTo(offset);
                            closestDirection = dir;
                        }
                    }
                }
                if (closestDirection != null && closestDirection != this.getDirection()) {
                    this.entityData.set(ATTACHED_FACE, closestDirection);
                } else if (Direction.DOWN != this.getDirection() && closestDirection == null) {
                    this.entityData.set(ATTACHED_FACE, Direction.DOWN);
                }
            }
        }
        final Direction attachmentFacing = this.getAttachFacing();
        if (attachmentFacing != Direction.DOWN) {
            if (attachmentFacing == Direction.UP && this.yya >= 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 1, 0));
            }
        }
        if (prevAttachDir != attachmentFacing) {
            attachChangeProgress = 1F;
        }
        this.prevAttachDir = attachmentFacing;
        if (!this.level().isClientSide) {
            if (attachmentFacing != Direction.UP && !this.isUpsideDownNavigator) {
                switchNavigator(false);
            }
            if (attachmentFacing == Direction.DOWN && this.isUpsideDownNavigator) {
                switchNavigator(true);
            }
        }
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
        super.checkFallDamage(p_20990_, p_20991_, p_20992_, p_20993_);
        if (this.getAttachFacing() != Direction.DOWN) {
            this.resetFallDistance();
        }
    }

    public boolean onClimbable() {
        return false;
    }

    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * 0.2F;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_33432_) {
        super.readAdditionalSaveData(p_33432_);
        this.setAttachFace(Direction.from3DDataValue(p_33432_.getByte("AttachFace")));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag p_33443_) {
        super.addAdditionalSaveData(p_33443_);
        p_33443_.putByte("AttachFace", (byte) this.getAttachFacing().get3DDataValue());
    }

    private void setAttachFace(Direction attachFace) {
        this.entityData.set(ATTACHED_FACE, attachFace);
    }

    public Direction getAttachFacing() {
        return this.entityData.get(ATTACHED_FACE);
    }


    public void switchNavigator(boolean rightsideUp) {

        if (rightsideUp) {
            this.isUpsideDownNavigator = false;
        } else {
            this.isUpsideDownNavigator = true;
        }
    }
}
