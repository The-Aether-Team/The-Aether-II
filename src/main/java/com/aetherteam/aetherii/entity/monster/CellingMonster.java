package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.entity.ai.controller.CellingMoveControl;
import com.aetherteam.aetherii.entity.ai.navigator.CellingPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import static net.minecraftforge.common.util.TransformationHelper.quatFromXYZ;

public class CellingMonster extends Monster {
    public static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(CellingMonster.class, EntityDataSerializers.DIRECTION);
    public static final EntityDataAccessor<Quaternionf> CELL_ROTATION = SynchedEntityData.defineId(CellingMonster.class, EntityDataSerializers.QUATERNION);


    public Quaternionfc prevRotation = new Quaternionf();
    public float prevAttachChangeProgress;

    public float attachChangeProgress;

    protected CellingMonster(EntityType<? extends CellingMonster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.moveControl = new CellingMoveControl(this);
        this.navigation = new CellingPathNavigation(this, level());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACHED_FACE, Direction.DOWN);
        this.entityData.define(CELL_ROTATION, new Quaternionf());
    }

    @Override
    public void travel(Vec3 p_32394_) {
        if (this.getAttachFacing() != Direction.DOWN) {
            this.moveRelative(0.1F, p_32394_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.6));
            this.calculateEntityAnimation(false);
        } else {
            super.travel(p_32394_);
        }
    }

    @Override
    public void tick() {
        this.prevAttachChangeProgress = this.attachChangeProgress;

        if (!this.prevRotation.equals(this.getCellRotation())) {
            attachChangeProgress = 1F;

            this.prevRotation = this.getCellRotation();
        }

        super.tick();

        if (attachChangeProgress > 0F) {
            attachChangeProgress -= 0.1F;
        }
    }

    public float getAttachAmount(float p_20999_) {
        return Mth.lerp(p_20999_, this.prevAttachChangeProgress, this.attachChangeProgress);
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel p_376725_ = (ServerLevel) this.level();
        super.customServerAiStep();
        ProfilerFiller profilerfiller = this.level().getProfiler();
        profilerfiller.push("cellingAI");
        this.cellingTick();
        profilerfiller.pop();
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        this.stopCelling();
    }

    protected void cellingTick() {

        boolean flag = this.moveControl instanceof CellingMoveControl && ((CellingMoveControl) this.moveControl).isWalkableUpper();
        boolean flag2 = this.moveControl.hasWanted() && this.moveControl.getWantedY() - this.getY() > 0;


        if (!flag && !flag2 && (this.onGround() || this.isInWater() || this.isInLava() /*|| this.isInFluidType()*/)) {
            this.entityData.set(ATTACHED_FACE, Direction.DOWN);
            this.setCellRotation(new Quaternionf());
        } else {
            Direction closestDirection = null;
            Quaternionf closestRotation = new Quaternionf();
            double closestDistance = 2.5D;
            BlockPos pos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY() + (this.getBbHeight() / 2)), Mth.floor(this.getZ()));

            //first celling check in bb height's center
            for (BlockPos offsetPos : BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1)
                    .filter(p_341357_ -> Math.abs(p_341357_.getX() - p_341357_.getY() - p_341357_.getZ()) != 0)
                    .map(BlockPos::immutable)
                    .toList()) {
                BlockPos pos1 = pos.offset(offsetPos);
                Direction dir = Direction.getNearest(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ());
                Vec3 offset = Vec3.atCenterOf(pos1);
                if (dir != Direction.DOWN) {
                    if (closestDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(pos1, this, dir.getOpposite())) {
                        closestDistance = this.position().distanceTo(offset);
                        closestDirection = dir;
                        closestRotation = quatFromXYZ(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ(), false);
                    }
                }
            }

            if (closestDirection == null) {
                //second celling check
                pos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
                for (BlockPos offsetPos : BlockPos.betweenClosedStream(-1, -1, -1, 1, 0, 1)
                        .filter(p_341357_ -> Math.abs(p_341357_.getX() - p_341357_.getY() - p_341357_.getZ()) != 0)
                        .map(BlockPos::immutable)
                        .toList()) {
                    BlockPos pos1 = pos.offset(offsetPos);
                    Direction dir = Direction.getNearest(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ());
                    Vec3 offset = Vec3.atCenterOf(pos1);
                    if (dir != Direction.DOWN) {
                        if (closestDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(pos1, this, dir.getOpposite())) {
                            closestDistance = this.position().distanceTo(offset);
                            closestDirection = dir;
                            closestRotation = quatFromXYZ(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ(), false);
                        }
                    }
                }
            }

            if (closestDirection == null) {
                //third celling check with full bb height
                pos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY() + this.getBbHeight()), Mth.floor(this.getZ()));
                for (BlockPos offsetPos : BlockPos.betweenClosedStream(-1, 0, -1, 1, 1, 1)
                        .filter(p_341357_ -> Math.abs(p_341357_.getX() - p_341357_.getY() - p_341357_.getZ()) != 0)
                        .map(BlockPos::immutable)
                        .toList()) {
                    BlockPos pos1 = pos.offset(offsetPos);
                    Direction dir = Direction.getNearest(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ());
                    Vec3 offset = Vec3.atCenterOf(pos1);
                    if (dir != Direction.DOWN) {
                        if (closestDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(pos1, this, dir.getOpposite())) {
                            closestDistance = this.position().distanceTo(offset);
                            closestDirection = dir;
                            closestRotation = quatFromXYZ(pos1.getX() - pos.getX(), pos1.getY() - pos.getY(), pos1.getZ() - pos.getZ(), false);
                        }
                    }
                }
            }

            if (closestDirection != null && closestDirection != this.getDirection()) {
                this.entityData.set(ATTACHED_FACE, closestDirection);
                this.setCellRotation(closestRotation);
            } else if (Direction.DOWN != this.getDirection() && closestDirection == null) {
                this.entityData.set(ATTACHED_FACE, Direction.DOWN);
                this.setCellRotation(new Quaternionf());
            }
        }

    }

    public void stopCelling() {
        this.entityData.set(ATTACHED_FACE, Direction.DOWN);
        this.setCellRotation(new Quaternionf());
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
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAttachFace(Direction.from3DDataValue(tag.contains("AttachFace") ? tag.getByte("AttachFace") : 0));
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("AttachFace", (byte) this.getAttachFacing().get3DDataValue());
    }

    protected void setAttachFace(Direction attachFace) {
        this.entityData.set(ATTACHED_FACE, attachFace);
    }

    public Direction getAttachFacing() {
        return this.entityData.get(ATTACHED_FACE);
    }

    protected void setCellRotation(Quaternionf quaternionf) {
        this.entityData.set(CELL_ROTATION, quaternionf);
    }

    public Quaternionfc getCellRotation() {
        return this.entityData.get(CELL_ROTATION);
    }
}
