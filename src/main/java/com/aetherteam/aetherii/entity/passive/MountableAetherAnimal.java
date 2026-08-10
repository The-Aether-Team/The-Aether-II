package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.NotGrounded;
import com.aetherteam.aetherii.network.packet.serverbound.MountJumpedPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public abstract class MountableAetherAnimal extends AetherAnimal implements NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(MountableAetherAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_CROUCHED_ID = SynchedEntityData.defineId(MountableAetherAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_MOUNT_JUMPING_ID = SynchedEntityData.defineId(MountableAetherAnimal.class, EntityDataSerializers.BOOLEAN);

    protected MountableAetherAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ENTITY_ON_GROUND_ID, true);
        builder.define(DATA_PLAYER_CROUCHED_ID, false);
        builder.define(DATA_MOUNT_JUMPING_ID, false);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.onGround()) {
            this.setEntityOnGround(true);
        }
    }

    @Override
    protected void tickRidden(Player controller, Vec3 riddenInput) {
        super.tickRidden(controller, riddenInput);
        Vec2 rotation = new Vec2(controller.getXRot() * 0.5F, controller.getYRot());
        this.setRot(rotation.y, rotation.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.onGround()) {
            this.setMountJumping(false);
        }
    }

    @Override
    protected Vec3 getRiddenInput(Player controller, Vec3 selfInput) {
        float sideways = controller.xxa * 0.5F;
        float forward = controller.zza;
        if (forward <= 0.0F) {
            forward *= 0.25F;
        }
        return new Vec3(sideways, 0.0, forward);
    }

    @Override
    protected float getRiddenSpeed(Player controller) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    protected void doPlayerRide(Player player) {
        if (!this.level().isClientSide()) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
    }

    protected void executeRidersJump(float amount, Vec3 input) {
        double impulse = this.getMountedJumpPower(amount);
        Vec3 movement = this.getDeltaMovement();
        this.setDeltaMovement(movement.x, impulse, movement.z);
        this.needsSync = true;
        net.neoforged.neoforge.common.CommonHooks.onLivingJump(this);
        this.setMountJumping(true);
        this.setEntityOnGround(false);
        ClientPacketDistributor.sendToServer(new MountJumpedPacket(this.getId()));
        if (input.z > 0.0) {
            float sin = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
            float cos = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
            this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * sin * amount, 0.0, 0.4F * cos * amount));
        }
    }

    protected float getMountedJumpPower(float multiplier) {
        return this.getJumpPower(multiplier);
    }

    /**
     * @return Whether this entity has been set as on the ground, as a {@link Boolean} value.
     */
    @Override
    public boolean isEntityOnGround() {
        return this.getEntityData().get(DATA_ENTITY_ON_GROUND_ID);
    }

    /**
     * Sets whether this entity is on the ground.
     *
     * @param onGround The {@link Boolean} value.
     */
    @Override
    public void setEntityOnGround(boolean onGround) {
        this.getEntityData().set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    /**
     * @return Whether the passenger player tried to crouch.
     */
    public boolean playerTriedToCrouch() {
        return this.getEntityData().get(DATA_PLAYER_CROUCHED_ID);
    }

    /**
     * Sets whether the passenger player tried to crouch.
     *
     * @param playerTriedToCrouch The {@link Boolean} value.
     */
    public void setPlayerTriedToCrouch(boolean playerTriedToCrouch) {
        this.getEntityData().set(DATA_PLAYER_CROUCHED_ID, playerTriedToCrouch);
    }

    /**
     * @return Whether this mount is jumping, as a {@link Boolean}.
     */
    public boolean isMountJumping() {
        return this.getEntityData().get(DATA_MOUNT_JUMPING_ID);
    }

    /**
     * Sets whether the mount is jumping.
     *
     * @param isMountJumping The {@link Boolean} value.
     */
    public void setMountJumping(boolean isMountJumping) {
        this.getEntityData().set(DATA_MOUNT_JUMPING_ID, isMountJumping);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("MountJumping", this.isMountJumping());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setMountJumping(input.getBooleanOr("MountJumping", true));
    }
}
