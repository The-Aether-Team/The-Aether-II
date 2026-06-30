package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import net.minecraft.core.particles.BlockParticleOption;
import com.aetherteam.aetherii.client.particle.options.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AmberDart extends AbstractArrow {
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(AmberDart.class, EntityDataSerializers.INT);
    private ItemStack pickupStack = new ItemStack(AetherIIItems.AMBER_DARTS.get());
    private ItemStack weaponStack = ItemStack.EMPTY;

    public AmberDart(EntityType<? extends AmberDart> entityType, Level level) {
        super(entityType, level);
    }

    public AmberDart(Level level, double x, double y, double z, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.AMBER_DART.get(), x, y, z, level);
        this.pickupStack = pickupStack.copy();
        this.weaponStack = weaponStack.copy();
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        if (AetherIIDataComponents.has(weaponStack, AetherIIDataComponents.BUILDUP_CONTENTS)) {
            this.entityData.set(ID_EFFECT_COLOR, this.getColor(weaponStack));
        }
        this.setBaseDamage(0);
    }

    public AmberDart(Level level, LivingEntity owner, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.AMBER_DART.get(), owner, level);
        this.pickupStack = pickupStack.copy();
        this.weaponStack = weaponStack.copy();
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        if (AetherIIDataComponents.has(weaponStack, AetherIIDataComponents.BUILDUP_CONTENTS)) {
            this.entityData.set(ID_EFFECT_COLOR, this.getColor(weaponStack));
        }
        this.setBaseDamage(0);
        Vec3 offset = new Vec3(0, 0, 0.5).xRot(-owner.getViewXRot(1.0F) * Mth.DEG_TO_RAD).yRot((-owner.getViewYRot(1.0F) * Mth.DEG_TO_RAD));
        double x = owner.getX();
        double y = owner.getEyeY() - (double) 0.1F;
        double z = owner.getZ();
        this.setPos(x + offset.x(), y + offset.y(), z + offset.z());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, -1);
    }

    @Override
    protected void tickDespawn() {
        ((AbstractArrowAccessor) this).aether$setLife(((AbstractArrowAccessor) this).aether$getLife() + 1);
        if (((AbstractArrowAccessor) this).aether$getLife() >= 1) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockState blockState = serverLevel.getBlockState(result.getBlockPos());
            Vec3 vec3 = result.getLocation();
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3.x, vec3.y, vec3.z, 4, result.getDirection().getStepX() * 0.1F, result.getDirection().getStepY() * 0.1F, result.getDirection().getStepZ() * 0.1F, 0.0F);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) { //todo needs another default break particle
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.isBlocking()) {
                AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.DAMAGE_SYSTEM).buildUpShieldStun(livingEntity, this.getOwner(), 1);
                if (entity instanceof Player player && player.isBlocking()) {
                    if (!player.getUseItem().isEmpty()) {
                        player.getUseItem().hurtAndBreak(3, player, entityPlayer -> entityPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                    }
                }
                return;
            }
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            BuildupContents buildupContents = AetherIIDataComponents.get(this.weaponStack, AetherIIDataComponents.BUILDUP_CONTENTS);
            if (buildupContents != null) {
                if (entity instanceof LivingEntity livingEntity) {
                    AetherIIDataAttachments.get(entity, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, this, this.getOwner(), buildupContents.preset(), buildupContents.amount());
                }
                Vec3 vec3 = result.getLocation();
                serverLevel.sendParticles(ColorParticleOption.create(AetherIIParticleTypes.EFFECT_BUILDUP.get(), buildupContents.getColor()), vec3.x, vec3.y, vec3.z, 1, 0.0F, this.random.nextDouble() / 3.0, 0.0F, 0.0F);
            }
        }
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.pickupStack.isEmpty() ? new ItemStack(AetherIIItems.AMBER_DARTS.get()) : this.pickupStack.copy();
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    public int getColor(ItemStack stack) {
        int defaultColor = BuildupContents.DEFAULT_COLOR;
        BuildupContents buildupContents = AetherIIDataComponents.get(stack, AetherIIDataComponents.BUILDUP_CONTENTS);
        if (buildupContents != null) {
            return buildupContents.getColor();
        }
        return defaultColor;
    }
}
