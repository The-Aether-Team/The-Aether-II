package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.PlayerAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SkephidWebbingBall extends ThrowableProjectile implements ItemSupplier {
    public SkephidWebbingBall(EntityType<? extends SkephidWebbingBall> type, Level level) {
        super(type, level);
    }

    public SkephidWebbingBall(double x, double y, double z, Level level) {
        super(AetherIIEntityTypes.SKEPHID_WEBBING_BALL.get(), level);
        this.setPos(x, y, z);
    }

    public SkephidWebbingBall(LivingEntity shooter, Level level) {
        super(AetherIIEntityTypes.SKEPHID_WEBBING_BALL.get(), shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), level);
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    /**
     * Handles shield damaging and knockback when this projectile hits an entity.
     *
     * @param result The {@link EntityHitResult} of the projectile.
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.isBlocking()) {
                if (entity instanceof Player player && player.isBlocking()) {
                    PlayerAccessor playerAccessor = (PlayerAccessor) player;
                    playerAccessor.callHurtCurrentlyUsedShield(3.0F);
                }
            } else {
                livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.WEBBED, 350);
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.SNOWBALL);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }
}