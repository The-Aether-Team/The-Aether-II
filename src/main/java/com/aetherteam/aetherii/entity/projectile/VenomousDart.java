package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class VenomousDart extends AbstractArrow {
    public VenomousDart(EntityType<? extends VenomousDart> entityType, Level level) {
        super(entityType, level);
    }

    public VenomousDart(double x, double y, double z, Level level) {
        super(AetherIIEntityTypes.VENOMOUS_DART.get(), x, y, z, level, new ItemStack(Items.ARROW), null);
        this.pickup = Pickup.DISALLOWED;
    }

    public VenomousDart(LivingEntity owner, Level level) {
        super(AetherIIEntityTypes.VENOMOUS_DART.get(), owner, level, new ItemStack(Items.ARROW), null);
        this.pickup = Pickup.DISALLOWED;
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

    /**
     * Applies the Inebriation effect to an entity after being hurt.
     *
     * @param living The {@link LivingEntity} to affect.
     */
    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.VENOM, 350);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
