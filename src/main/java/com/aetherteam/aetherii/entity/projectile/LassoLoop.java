package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class LassoLoop extends ThrowableProjectile implements Leashable {
    private Leashable.LeashData leashData;

    public LassoLoop(EntityType<? extends LassoLoop> entityType, Level level) {
        super(entityType, level);
    }

    public LassoLoop(Level level) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), level);
    }

    public LassoLoop(Level level, LivingEntity owner) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), level);
        this.setOwner(owner);
    }

    public LassoLoop(Level level, double x, double y, double z) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), x, y, z, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(AetherIIItems.BRETTL_LASSO.get()));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (this.getOwner() instanceof Player player && entity != player) {
            if (entity.isAlive() && entity instanceof Leashable leashable) {
                if (!(leashable.getLeashHolder() instanceof Player)) {
                    if (!this.level().isClientSide() && leashable.canHaveALeashAttachedTo(player)) {
                        if (leashable.isLeashed()) {
                            leashable.dropLeash();
                        }

                        leashable.setLeashedTo(player, true);
                        entity.setData(AetherIIDataAttachments.LASSO_CONNECTION, true);
                        this.playSound(SoundEvents.LEAD_TIED);
                    }
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public boolean checkElasticInteractions(Entity entity, LeashData leashData) {
        return false;
    }

    @Override
    public void leashTooFarBehaviour() {
        Leashable.super.leashTooFarBehaviour();
        this.discard();
    }

    @Nullable
    @Override
    public LeashData getLeashData() {
        return this.leashData;
    }

    @Override
    public void setLeashData(@Nullable Leashable.LeashData leashData) {
        this.leashData = leashData;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        this.writeLeashData(valueOutput, this.leashData);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        this.readLeashData(input);
    }
}
