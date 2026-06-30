package com.aetherteam.aetherii.item.miscellaneous.glider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

public class GoldenAercloudGliderItem extends AercloudGliderItem {
    public GoldenAercloudGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public void calculateMovement(LivingEntity entity, Vec3 vec3) {
        float speed = 0.04F;
        entity.moveRelative(speed, vec3);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        entity.getDeltaMovement();
    }
}
