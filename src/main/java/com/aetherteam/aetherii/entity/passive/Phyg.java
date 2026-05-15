package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.goal.FallingRandomStrollGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Phyg extends WingedAnimal {
    public Phyg(EntityType<? extends Phyg> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Phyg.PhygPanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, item -> item.is(AetherIITags.Items.PHYG_FOOD), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isHolding(itemstack -> itemstack.is(AetherIITags.Items.PHYG_CALM_ITEMS))) {
            if (!this.isVehicle() && !player.isSecondaryUseActive()) {
                this.doPlayerRide(player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity && livingEntity.isHolding(itemstack -> itemstack.is(AetherIITags.Items.PHYG_CALM_ITEMS))) {
            return livingEntity;
        }
        return null;
    }

    @Override
    protected float getRiddenSpeed(Player controller) {
        return super.getRiddenSpeed(controller) * 0.6F;
    }

    @Override
    protected float getMountedJumpPower(float multiplier) {
        return super.getJumpPower(multiplier) + (0.65F * multiplier);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.PHYG_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_PHYG_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_PHYG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_PHYG_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_PHYG_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return AetherIIEntityTypes.PHYG.get().create(level, EntitySpawnReason.BREEDING);
    }

    /**
     * [CODE COPY] - {@link Pig#getLeashOffset()}.
     */
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float multiplier = 4.0F;
        if (this.getControllingPassenger() != null) {
            multiplier = 1.75F;
            if (!this.onGround()) {
                multiplier = 1.25F;
            }
        }
        float f = Math.min(partialTick * multiplier, 1.0F);
        this.walkAnimation.update(f, 0.4F, this.isBaby() ? 3.0F : 1.0F);
    }

    protected static class PhygPanicGoal extends PanicGoal {
        public PhygPanicGoal(Phyg phyg, double speed) {
            super(phyg, speed);
        }

        @Override
        protected boolean shouldPanic() {
            return super.shouldPanic() || this.mob.getFirstPassenger() instanceof LivingEntity livingEntity && !livingEntity.isHolding(stack -> stack.is(AetherIITags.Items.PHYG_CALM_ITEMS));
        }
    }
}
