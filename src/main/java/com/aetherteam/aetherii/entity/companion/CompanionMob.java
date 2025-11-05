package com.aetherteam.aetherii.entity.companion;

import com.aetherteam.aetherii.entity.ai.goal.CompanionFollowGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class CompanionMob extends PathfinderMob implements Companion<CompanionMob> {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_OWNER_ID = SynchedEntityData.defineId(CompanionMob.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_ID = SynchedEntityData.defineId(CompanionMob.class, EntityDataSerializers.ITEM_STACK);

    private final Supplier<ItemStack> summoningItem;
    private final boolean isFloating;

    protected CompanionMob(EntityType<? extends PathfinderMob> entityType, Level level, Supplier<ItemStack> summoningItem, boolean isFloating) {
        super(entityType, level);
        this.summoningItem = summoningItem;
        this.isFloating = isFloating;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new CompanionFollowGoal<>(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_OWNER_ID, Optional.empty());
        builder.define(DATA_ITEM_ID, ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        return spawnGroupData;
    }

    @Override
    public void tick() {
        super.tick();
        this.tickCompanion(this);
    }

//    /** //TODO
//     * Adjusts the friction of companions, mostly depending on if they're a hovering companion or not.
//     *
//     * @param deltaMovement The entity's {@link Vec3} of motion.
//     * @param friction The original friction {@link Float} value.
//     * @return The new entity motion {@link Vec3}.
//     */
//    @Override
//    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 deltaMovement, float friction) {
//        float newFriction = this.getFrictionModifier(); // The new friction that is dependent on the companion type.
//        Vec3 motion = super.handleRelativeFrictionAndCalculateMovement(deltaMovement, newFriction); // The new friction is passed into this friction method.
//        if (this.onGround() && !this.shouldDiscardFriction()) {
//            double adjustedFriction = (newFriction * 0.91F) / (friction * 0.91F); // Used to multiply the new friction (the numerator) to the friction-adjusted motion again, and the denominator will cancel the original friction in the vanilla travel method.
//            motion = motion.multiply(adjustedFriction, 1.0, adjustedFriction);
//        }
//        return motion;
//    }

    @Override
    public void onEquip(ItemStack itemStack) {
        this.setItem(itemStack); // Stores the summoning item.
    }

    @Override
    public void onUnequip(ItemStack itemStack) {
        this.setItem(ItemStack.EMPTY); // Stops storing the summoning item.
    }

    /**
     * @return The {@link UUID} of this companion's owner.
     */
    @Override
    public Optional<EntityReference<LivingEntity>> getOwner() {
        return this.getEntityData().get(DATA_OWNER_ID);
    }

    /**
     * Sets the owner of this companion using the {@link UUID}.
     *
     * @param owner The owner's {@link UUID}.
     */
    @Override
    public void setOwner(Optional<EntityReference<LivingEntity>> owner) {
        this.getEntityData().set(DATA_OWNER_ID, owner);
    }

    /**
     * @return The {@link ItemStack} that summoned this companion.
     */
    @Override
    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_ID);
    }

    /**
     * Sets the {@link ItemStack} that summoned this companion.
     *
     * @param stack The {@link ItemStack}.
     */
    @Override
    public void setItem(ItemStack stack) {
        this.getEntityData().set(DATA_ITEM_ID, stack);
    }

    /**
     * @return The summoning {@link ItemStack} for the companion.
     */
    @Override
    public ItemStack getSummonItem() {
        return this.summoningItem.get();
    }

    /**
     * @return A {@link Boolean} for whether this companion appears to hover off of the ground.
     */
    public boolean isFloating() {
        return this.isFloating;
    }

    /**
     * @return A {@link Float} for the companion's movement friction. It applies more slippery motion to floating entities when slowing down, to simulate midair stopping physics.
     */
    public float getFrictionModifier() {
        if (this.isFloating() && Math.abs(this.getDeltaMovement().x() * this.getDeltaMovement().z()) < 0.00075) {
            return 0.95F;
        } else if (this.isFloating()) {
            return 0.6F;
        } else {
            return this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFriction(level(), this.getBlockPosBelowThatAffectsMyMovement(), this);
        }
    }

    /**
     * Makes companions immune to all natural damage types.
     *
     * @param source The {@link DamageSource}.
     * @return Whether the companion is invulnerable to the damage, as a {@link Boolean}.
     */
    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        return !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    /**
     * @return True to always show the companion's name above its head.
     */
    @Override
    public boolean shouldShowName() {
        return true;
    }

    /**
     * @return The summoning {@link ItemStack} for the companion.
     */
    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getSummonItem();
    }

    @Override
    public boolean canUsePortal(boolean allowPassengers) {
        return false;
    }
}
