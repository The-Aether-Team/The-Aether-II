package com.aetherteam.aetherii.entity.vehicle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface RiderSitContext {
    boolean shouldRiderSit(Entity vehicle, LivingEntity passenger);
}
