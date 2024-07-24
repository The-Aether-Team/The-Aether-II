package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aetherii.entity.ai.goal.PhygPanicGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class Phyg extends WingedAnimal {
    public Phyg(EntityType<? extends Phyg> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PhygPanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(AetherIITags.Items.PHYG_FOOD), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity && livingEntity.isHolding(itemstack -> itemstack.is(AetherIITags.Items.PHYG_CALM_ITEMS))) {
            return super.getControllingPassenger();
        }
        return null;
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

    @Nullable
    @Override
    protected SoundEvent getSaddledSound() {
        return AetherIISoundEvents.ENTITY_PHYG_SADDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_PHYG_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return AetherIIEntityTypes.PHYG.get().create(level);
    }

    @Override
    public boolean isSaddleable() {
        return false;
    }

    /**
     * [CODE COPY] - {@link Pig#getLeashOffset()}.
     */
    @OnlyIn(Dist.CLIENT)
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
