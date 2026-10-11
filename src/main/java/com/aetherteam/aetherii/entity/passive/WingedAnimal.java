package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class WingedAnimal extends MountableAetherAnimal implements PlayerRideableJumping {
    private double slowFall = 0;
    /**
     * Used for wing animations.
     */
    private float wingFold;
    private float wingAngle;
    protected float playerJumpPendingScale;

    public WingedAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /**
     * Navigation for falling entities.
     *
     * @param level The {@link Level}.
     * @return The {@link PathNavigation} class.
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    /**
     * Makes this entity fall slowly.
     */
    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double fallSpeed = Math.min(gravity.getValue() * -2.0, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
                if (this.slowFall == 0 || this.needsSync) {
                    this.slowFall = this.getDeltaMovement().y();
                    this.needsSync = false;
                }
                this.slowFall = Mth.lerp(0.1, this.slowFall, fallSpeed);

                this.setDeltaMovement(this.getDeltaMovement().x(), this.slowFall, this.getDeltaMovement().z());
                this.setEntityOnGround(false);
            } else if (this.getDeltaMovement().y() == fallSpeed) {
                this.slowFall = 0;
            }
        }
        if (this.level().isClientSide()) {
            float aimingForFold;
            if (this.isEntityOnGround()) {
                aimingForFold = 0.0F;
            } else {
                aimingForFold = 1.0F;
            }
            this.setWingFold(this.getWingFold() + ((aimingForFold - this.getWingFold()) / 37.5F));
        }
    }

    @Override
    protected void tickRidden(Player controller, Vec3 riddenInput) {
        super.tickRidden(controller, riddenInput);
        if (this.isLocalInstanceAuthoritative()) {
            if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                this.executeRidersJump(this.playerJumpPendingScale, riddenInput);
            }
            this.playerJumpPendingScale = 0.0F;
            this.checkFallDistanceAccumulation();
        }
    }

    @Override
    public void onPlayerJump(int jumpAmount) {
        if (jumpAmount < 0) {
            jumpAmount = 0;
        }
        this.playerJumpPendingScale = this.getPlayerJumpPendingScale(jumpAmount);
    }

    @Override
    public boolean canJump() {
        return !this.isMountJumping();
    }

    @Override
    public void handleStartJump(int jumpScale) {

    }

    @Override
    public void handleStopJump() {

    }

    /**
     * @return The {@link Float} for the wings' fold amount.
     */
    public float getWingFold() {
        return this.wingFold;
    }

    /**
     * Sets the wings' fold amount.
     *
     * @param wingFold The {@link Float} amount.
     */
    public void setWingFold(float wingFold) {
        this.wingFold = wingFold;
    }

    /**
     * @return The {@link Float} for the wings' angle.
     */
    public float getWingAngle() {
        return this.wingAngle;
    }

    /**
     * Sets the wings' angle
     *
     * @param wingAngle The {@link Float} amount.
     */
    public void setWingAngle(float wingAngle) {
        this.wingAngle = wingAngle;
    }

    /**
     * @return A {@link Float} for the calculated movement speed, both when mounted and not mounted.
     */
    @Override
    public float getFlyingSpeed() {
        if (this.isEffectiveAi() && !this.onGround() && this.getPassengers().isEmpty()) {
            return this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
        } else {
            return super.getFlyingSpeed();
        }
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() || this.fallDistance < 4 ? 3 : 14;
    }
}
