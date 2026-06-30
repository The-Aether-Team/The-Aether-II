package com.aetherteam.aetherii.entity.passive;

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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FlyingCow extends WingedAnimal {
    private static final EntityDimensions BABY_DIMENSIONS = EntityType.COW.getDimensions().scale(0.5F);

    public FlyingCow(EntityType<? extends FlyingCow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(AetherIITags.Items.FLYING_COW_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.Cow#mobInteract(Player, InteractionHand)}.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.isHolding(itemstack -> itemstack.is(AetherIITags.Items.FLYING_COW_CALM_ITEMS))) {
            if (!this.isVehicle() && !player.isSecondaryUseActive()) {
                this.doPlayerRide(player);
                return InteractionResult.SUCCESS;
            }
        } else if (itemStack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(AetherIISoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
            ItemStack itemStack1 = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, itemStack1);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity && livingEntity.isHolding(itemstack -> itemstack.is(AetherIITags.Items.FLYING_COW_CALM_ITEMS))) {
            return livingEntity;
        }
        return null;
    }

    @Override
    protected float getRiddenSpeed(Player controller) {
        return super.getRiddenSpeed(controller) * 0.7F;
    }

    @Override
    protected float getMountedJumpPower(float multiplier) {
        return super.getMountedJumpPower(multiplier) + (0.75F * multiplier);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.FLYING_COW_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_FLYING_COW_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_FLYING_COW_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_FLYING_COW_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_FLYING_COW_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return AetherIIEntityTypes.FLYING_COW.get().create(level);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return this.isBaby() ? size.height * 0.95F : 1.3F;
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
        this.walkAnimation.update(f * (this.isBaby() ? 3.0F : 1.0F), 0.4F);
    }
}
