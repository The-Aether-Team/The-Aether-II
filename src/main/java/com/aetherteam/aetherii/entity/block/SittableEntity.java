package com.aetherteam.aetherii.entity.block;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class SittableEntity extends Entity {
    private BlockState blockState = Blocks.AIR.defaultBlockState();
    private boolean passengerUnseated = false;

    public SittableEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    public SittableEntity(Level level, Vec3 pos, float yRot, BlockState state) {
        this(AetherIIEntityTypes.SITTABLE.get(), level);
        this.setPos(pos);
        this.setYRot(yRot);
        this.blockState = state;
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (!this.level().getBlockState(this.blockPosition()).is(this.blockState.getBlock()) || this.blockState.is(Blocks.AIR) || this.passengerUnseated) {
                this.discard();
            }
        }
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        passenger.setYRot(this.getYRot());
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.passengerUnseated = true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        passenger.setYBodyRot(this.getYRot());
        float delta = Mth.wrapDegrees(passenger.getYRot() - this.getYRot());
        float targetDelta = Mth.clamp(delta, -105.0F, 105.0F);
        passenger.yRotO += targetDelta - delta;
        passenger.setYRot(passenger.getYRot() + targetDelta - delta);
        passenger.setYHeadRot(passenger.getYRot());
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        BlockPos pos = this.blockPosition();
        Direction facing = this.getDirection();
        Vec3 dismountLocation = DismountHelper.findSafeDismountLocation(passenger.getType(), this.level(), pos.relative(facing), false);
        if (dismountLocation != null) {
            return dismountLocation.add(0, 0.25, 0);
        } else {
            dismountLocation = DismountHelper.findSafeDismountLocation(passenger.getType(), this.level(), pos, false);
            if (dismountLocation != null) {
                return dismountLocation.add(0, 0.25, 0);
            } else {
                Direction[] offsets = { facing.getClockWise(), facing.getCounterClockWise(), facing.getOpposite() };
                for (Direction dir : offsets) {
                    dismountLocation = DismountHelper.findSafeDismountLocation(passenger.getType(), this.level(), pos.relative(dir), false);
                    if (dismountLocation != null) {
                        return dismountLocation.add(0, 0.25, 0);
                    }
                }
            }
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.store("BlockState", BlockState.CODEC, this.blockState);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        this.blockState = valueInput.read("BlockState", BlockState.CODEC).orElse(Blocks.SAND.defaultBlockState());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity, Block.getId(this.getBlockState()));
    }
}
