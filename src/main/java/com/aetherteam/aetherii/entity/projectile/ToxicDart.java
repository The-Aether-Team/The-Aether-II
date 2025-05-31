package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ToxicDart extends AbstractArrow {
    public ToxicDart(EntityType<? extends ToxicDart> entityType, Level level) {
        super(entityType, level);
    }

    public ToxicDart(double x, double y, double z, Level level) {
        super(AetherIIEntityTypes.TOXIC_DART.get(), x, y, z, level, new ItemStack(Items.ARROW), null);
        this.pickup = Pickup.DISALLOWED;
    }

    public ToxicDart(LivingEntity owner, Level level) {
        super(AetherIIEntityTypes.TOXIC_DART.get(), owner, level, new ItemStack(Items.ARROW), null);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void tickDespawn() {
        ((AbstractArrowAccessor) this).aether$setLife(((AbstractArrowAccessor) this).aether$getLife() + 1);
        if (((AbstractArrowAccessor) this).aether$getLife() >= 20) {
            this.discard();
        }
    }

    /**
     * Handles shield damaging when this projectile hits an entity.
     *
     * @param result The {@link HitResult} of the projectile.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
//        if (result.getType() == HitResult.Type.ENTITY) {
//            Entity entity = ((EntityHitResult) result).getEntity();
//            if (entity instanceof Player player && player.isBlocking()) {
//                PlayerAccessor playerAccessor = (PlayerAccessor) player;
//                playerAccessor.callHurtCurrentlyUsedShield(3.0F);
//            }
//        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

//        if (this.level() instanceof ServerLevel serverLevel) {
//            BlockState blockState = serverLevel.getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
//            Vec3 vec3 = result.getLocation();
//            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3.x, vec3.y, vec3.z, 3, 0.0F, 0.0F, 0.0F, 0.0F);
//        }
    }

    /**
     * Applies the Inebriation effect to an entity after being hurt.
     *
     * @param living The {@link LivingEntity} to affect.
     */
    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.TOXIN, 350);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
