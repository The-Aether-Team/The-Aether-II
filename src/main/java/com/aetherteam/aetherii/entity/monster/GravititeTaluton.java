package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GravititeTaluton extends Taluton implements RangedAttackMob {
    private float legRotO;
    private float legRot;
    private float debrisRot0;
    private float debrisRot;

    public GravititeTaluton(EntityType<? extends GravititeTaluton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 60, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.STEP_HEIGHT, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
            }
        }
        if (this.level().isClientSide()) {
            this.legRotO = this.legRot;
            this.legRot += 0.1F;
            this.debrisRot0 = this.debrisRot;
            this.debrisRot -= 0.05F;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        GravititeDebrisShot debrisShot = new GravititeDebrisShot(this, this.level());
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.5) - debrisShot.getY();
        double d2 = target.getZ() - this.getZ();
        if (this.level() instanceof ServerLevel serverLevel) {
            debrisShot.shoot(d0, d1, d2, 0.5F, 0.0F);
            serverLevel.addFreshEntity(debrisShot);
        }
    }

    @Override
    public void jumpFromGround() { }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    public float getLegRotO() {
        return this.legRotO;
    }

    public float getLegRot() {
        return this.legRot;
    }

    public float getDebrisRot0() {
        return this.debrisRot0;
    }

    public float getDebrisRot() {
        return this.debrisRot;
    }
}
