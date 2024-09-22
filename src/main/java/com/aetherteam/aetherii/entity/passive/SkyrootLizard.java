package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SkyrootLizard extends AetherAnimal {
    public SkyrootLizard(EntityType<? extends SkyrootLizard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 2.2, 2.2, Entity::isSprinting));
        this.goalSelector.addGoal(3, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        if (itemStack.is(AetherIIItems.SKYROOT_STICK)) {
            playerEntity.playSound(AetherIISoundEvents.ENTITY_AERBUNNY_HURT.get(), 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemStack, playerEntity, AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get().getDefaultInstance());
            playerEntity.setItemInHand(hand, result);
            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        } else {
            return super.mobInteract(playerEntity, hand);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_SKYROOT_LIZARD_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SKYROOT_LIZARD_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SKYROOT_LIZARD_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_SKYROOT_LIZARD_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return AetherIIEntityTypes.SKYROOT_LIZARD.get().create(level);
    }
}