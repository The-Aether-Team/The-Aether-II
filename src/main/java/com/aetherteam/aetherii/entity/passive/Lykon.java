package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.event.EventHooks;
import org.jspecify.annotations.Nullable;

public class Lykon extends AetherTamableAnimal implements NeutralMob {
    private static final EntityDataAccessor<Long> DATA_ANGER_END_TIME = SynchedEntityData.defineId(Lykon.class, EntityDataSerializers.LONG);

    public static final TargetingConditions.Selector PREY_SELECTOR = (target, level) -> true;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private @Nullable EntityReference<LivingEntity> persistentAngerTarget;

    public Lykon(EntityType<? extends Lykon> type, Level level) {
        super(type, level);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.ON_TOP_OF_POWDER_SNOW, -1.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_ANGER_END_TIME, -1L);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AetherTamableAnimal.TamableAnimalPanicGoal(1.5F, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, (double) 1.1F, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, (double) 1.1F, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, (double) 1.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, (double) 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Cockatrice.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 4.0);
    }


    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        this.addPersistentAngerSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.readPersistentAngerSaveData(this.level(), input);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.feed(player, hand, itemStack, 2.0F, 2.0F);
                return InteractionResult.SUCCESS;
            }

            if (!itemStack.is(ItemTags.WOLF_COLLAR_DYES) || !this.isOwnedBy(player)) {
                if (this.isEquippableInSlot(itemStack, EquipmentSlot.BODY) && !this.isWearingBodyArmor() && this.isOwnedBy(player) && !this.isBaby()) {
                    this.setBodyArmorItem(itemStack.copyWithCount(1));
                    itemStack.consume(1, player);
                    return InteractionResult.SUCCESS;
                }

                if (this.isInSittingPose() && this.isWearingBodyArmor() && this.isOwnedBy(player) && this.getBodyArmorItem().isDamaged() && this.getBodyArmorItem().isValidRepairItem(itemStack)) {
                    itemStack.shrink(1);
                    this.playSound(SoundEvents.WOLF_ARMOR_REPAIR);
                    ItemStack armor = this.getBodyArmorItem();
                    int repairUnit = (int) ((float) armor.getMaxDamage() * 0.125F);
                    armor.setDamageValue(Math.max(0, armor.getDamageValue() - repairUnit));
                    return InteractionResult.SUCCESS;
                }

                InteractionResult interactionResult = super.mobInteract(player, hand);
                if (!interactionResult.consumesAction() && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    return InteractionResult.SUCCESS.withoutItem();
                }

                return interactionResult;
            }

            /*DyeColor color = (DyeColor)itemStack.get(DataComponents.DYE);
            if (color != null && color != this.getCollarColor()) {
                this.setCollarColor(color);
                itemStack.consume(1, player);
                return InteractionResult.SUCCESS;
            }*/
        } else if (!this.level().isClientSide() && itemStack.is(AetherIIItems.COCKATRICE_FEATHER) && !this.isAngry()) {
            itemStack.consume(1, player);
            this.tryToTame(player);
            return InteractionResult.SUCCESS_SERVER;
        }

        return super.mobInteract(player, hand);
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, player)) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity) null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }

    }

    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
        if (super.doHurtTarget(serverLevel, entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, this, this, EffectBuildupPresets.WOUND, 250);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(AetherIITags.Items.LYKON_FOOD);
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0);
            this.setHealth(40.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (this.isInvulnerableTo(level, source)) {
            return false;
        } else {
            this.setOrderedToSit(false);
            return super.hurtServer(level, source, damage);
        }
    }


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return AetherIIEntityTypes.LYKON.get().create(serverLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    public long getPersistentAngerEndTime() {
        return this.entityData.get(DATA_ANGER_END_TIME);
    }

    @Override
    public void setPersistentAngerEndTime(long endTime) {
        this.entityData.set(DATA_ANGER_END_TIME, endTime);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }
}
