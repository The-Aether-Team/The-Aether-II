package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIISkyrootLizardVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class SkyrootLizard extends AetherAnimal {
    private static final EntityDataAccessor<Holder<SkyrootLizardVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(SkyrootLizard.class, AetherIIDataSerializers.SKYROOT_LIZARD_VARIANT.get());

    public SkyrootLizard(EntityType<? extends SkyrootLizard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 2.2, 2.2, Entity::isSprinting));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, this.registryAccess().lookupOrThrow(AetherIIRegistries.SKYROOT_LIZARD_VARIANT).getOrThrow(AetherIISkyrootLizardVariants.SKYROOT));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (reason != EntitySpawnReason.TRIGGERED) {
            this.setVariant(AetherIISkyrootLizardVariants.getRandomVariant(level.getRandom(), level.registryAccess()));
        }
        return spawnData;
    }

    @Override
    public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        if (itemStack.is(AetherIIItems.SKYROOT_STICK)) {
            playerEntity.playSound(AetherIISoundEvents.ENTITY_SKYROOT_LIZARD_HURT.get(), 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemStack, playerEntity, AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get().getDefaultInstance());
            playerEntity.setItemInHand(hand, result);
            this.discard();
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(playerEntity, hand);
        }
    }

    public Holder<SkyrootLizardVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<SkyrootLizardVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
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
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        SkyrootLizard lizard = AetherIIEntityTypes.SKYROOT_LIZARD.get().create(level, EntitySpawnReason.BREEDING);
        if (lizard != null) {
            lizard.setVariant(level.getRandom().nextBoolean() ? this.getVariant() : ((SkyrootLizard) otherParent).getVariant());
        };
        return AetherIIEntityTypes.SKYROOT_LIZARD.get().create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        VariantUtils.writeVariant(output, this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        VariantUtils.readVariant(input, AetherIIRegistries.SKYROOT_LIZARD_VARIANT).ifPresent(this::setVariant);
    }
}