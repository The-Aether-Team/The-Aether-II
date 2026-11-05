package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.entity.monster.BladeshroomHunter;
import com.aetherteam.aetherii.entity.projectile.Sawcap;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SawcapSlinger extends Monster implements RangedAttackMob {
    private float rotate;
    private float rotateO;
    private float aggressiveScale;

    public SawcapSlinger(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.rotateO = this.rotate;
            if (this.isAggressive()) {
                this.aggressiveScale = Mth.clamp(this.aggressiveScale + 0.01F, 0, 1F);
            } else {
                this.aggressiveScale = Mth.clamp(this.aggressiveScale - 0.01F, 0, 1F);
            }

            if (this.isAlive()) {
                this.setRotate(getRotate() + 2 + aggressiveScale * 30);
            }
        }
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

    public float getRotate() {
        return rotate;
    }

    public float getRotateAnimationScale(float a) {
        return Mth.lerp(a, this.rotateO, this.rotate);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1F, 60, 80, 10.0F) {

            @Override
            public void start() {
                super.start();
                setAggressive(true);
            }

            @Override
            public void stop() {
                super.stop();

                setAggressive(false);
            }
        });
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, BladeshroomHunter.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Sawcap dart = new Sawcap(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.05F;
        dart.setSoundEvent(SoundEvents.ARMOR_STAND_PLACE);
        dart.shoot(d1, d0 + d4, d3, 1.0F, 6.0F);
        this.playSound(SoundEvents.ARMOR_STAND_BREAK, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }
}
