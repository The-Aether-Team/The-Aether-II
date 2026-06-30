package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.network.syncher.SynchedEntityData;
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
    protected void defineSynchedData() {

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
                AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.DAMAGE_SYSTEM).buildUpShieldStun(livingEntity, this.getOwner(), 1);
                if (entity instanceof Player player && player.isBlocking()) {
                    if (!player.getUseItem().isEmpty()) {
                        player.getUseItem().hurtAndBreak(3, player, entityPlayer -> entityPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                    }
                }
            } else {
                AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, this, this.getOwner(), EffectBuildupPresets.WEBBED, 475);
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.SNOWBALL);
    }
}
