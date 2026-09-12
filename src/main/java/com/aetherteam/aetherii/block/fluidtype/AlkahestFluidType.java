package com.aetherteam.aetherii.block.fluidtype;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

public class AlkahestFluidType extends FluidType {
    public AlkahestFluidType(FluidType.Properties properties) {
        super(properties);
    }

    @Override
    public boolean move(LivingEntity entity, Vec3 movementVector, double gravity) {
        double oldY = entity.getY();
        boolean isFalling = entity.getDeltaMovement().y <= 0.0;
        float slowDown = entity.isSprinting() ? 0.9F : ((LivingEntityAccessor) entity).callGetWaterSlowDown();
        float speed = 0.02F;
        float waterWalker = (float) entity.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (!entity.onGround()) {
            waterWalker *= 0.5F;
        }

        if (waterWalker > 0.0F) {
            slowDown += (0.546F - slowDown) * waterWalker;
            speed += (entity.getSpeed() - speed) * waterWalker;
        }

        if (entity.hasEffect(MobEffects.DOLPHINS_GRACE)) {
            slowDown = 0.96F;
        }

        speed *= (float) entity.getAttributeValue(NeoForgeMod.SWIM_SPEED);
        entity.moveRelative(speed, movementVector);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        Vec3 ladderMovement = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.onClimbable()) {
            ladderMovement = new Vec3(ladderMovement.x, 0.2, ladderMovement.z);
        }

        ladderMovement = ladderMovement.multiply(slowDown, 0.8F, slowDown);
        entity.setDeltaMovement(entity.getFluidFallingAdjustedMovement(gravity, isFalling, ladderMovement));
        this.jumpOutOfFluid(entity, oldY);
        return true;
    }

    private void jumpOutOfFluid(LivingEntity entity, double oldY) {
        Vec3 movement = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.isFree(movement.x, movement.y + 0.6F - entity.getY() + oldY, movement.z)) {
            entity.setDeltaMovement(movement.x, 0.3F, movement.z);
        }
    }
}
