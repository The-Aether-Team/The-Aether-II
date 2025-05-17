package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
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
        GravititeDebrisShot debris = new GravititeDebrisShot(this, this.level());
        debris.setPos(this.getX(), this.getEyeY() - 0.7, this.getZ());
        debris.forceSetRotation(this.getXRot(), this.getYRot());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.1F;
        debris.shoot(d1, d0 + d4, d3, 0.01F, 0.0F);
        this.playSound(AetherIISoundEvents.COCKATRICE_SHOOT.value(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(debris);
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
