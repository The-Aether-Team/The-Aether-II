package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Accessor("life")
    int aether$getLife();

    @Accessor("life")
    void aether$setLife(int life);
}
