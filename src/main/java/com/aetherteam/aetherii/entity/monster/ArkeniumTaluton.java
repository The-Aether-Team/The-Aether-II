package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.goal.PreAnimationMeleeAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;

public class ArkeniumTaluton extends Taluton {
    public static int ATTACK_EVENT = 4;

    private int attackAnimationTick;

    public ArkeniumTaluton(EntityType<? extends ArkeniumTaluton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TalutonMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75F)
                .add(Attributes.ATTACK_DAMAGE, 8.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_EVENT) {
            this.attackAnimationTick = 10;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 260;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_ARKENIUM_TALUTON_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_ARKENIUM_TALUTON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_ARKENIUM_TALUTON_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_ARKENIUM_TALUTON_STEP.get(), 0.15F, 1.0F);
    }

    protected static class TalutonMeleeAttackGoal extends PreAnimationMeleeAttackGoal {

        private final ArkeniumTaluton taluton;

        public TalutonMeleeAttackGoal(ArkeniumTaluton taluton, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(taluton, speedModifier, followingTargetEvenIfNotSeen, 3, 40);
            this.taluton = taluton;
        }

        @Override
        public void attackAnimation() {
            this.taluton.attackAnimationTick = 10;
            this.mob.level().broadcastEntityEvent(this.mob, (byte) ATTACK_EVENT);
            this.taluton.playSound(AetherIISoundEvents.ENTITY_ARKENIUM_TALUTON_ATTACK.get(), 1.0F, 1.0F);

        }
    }
}
