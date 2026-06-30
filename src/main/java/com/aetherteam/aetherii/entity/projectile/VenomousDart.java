package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class VenomousDart extends AbstractArrow {
    public VenomousDart(EntityType<? extends VenomousDart> entityType, Level level) {
        super(entityType, level);
    }

    public VenomousDart(double x, double y, double z, Level level) {
        super(AetherIIEntityTypes.VENOMOUS_DART.get(), x, y, z, level);
        this.pickup = Pickup.DISALLOWED;
        this.setBaseDamage(1.0F);
    }

    public VenomousDart(LivingEntity owner, Level level) {
        super(AetherIIEntityTypes.VENOMOUS_DART.get(), owner, level);
        this.pickup = Pickup.DISALLOWED;
        this.setBaseDamage(1.0F);
    }

    @Override
    protected void tickDespawn() {
        ((AbstractArrowAccessor) this).aether$setLife(((AbstractArrowAccessor) this).aether$getLife() + 1);
        if (((AbstractArrowAccessor) this).aether$getLife() >= 300) {
            this.discard();
        }
    }

    /**
     * Applies the Inebriation effect to an entity after being hurt.
     *
     * @param living The {@link LivingEntity} to affect.
     */
    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        AetherIIDataAttachments.get(living, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(living, this, this.getOwner(), EffectBuildupPresets.VENOM, 300);
        living.setArrowCount(living.getArrowCount() - 1);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
