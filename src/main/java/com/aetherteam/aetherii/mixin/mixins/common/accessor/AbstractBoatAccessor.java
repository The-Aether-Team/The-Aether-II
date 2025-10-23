package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.entity.vehicle.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractBoat.class)
public interface AbstractBoatAccessor {
    @Accessor("deltaRotation")
    float aether$getDeltaRotation();

    @Accessor("deltaRotation")
    void aether$setDeltaRotation(float deltaRotation);

    @Accessor("inputLeft")
    boolean aether$getInputLeft();

    @Accessor("inputRight")
    boolean aether$getInputRight();

    @Invoker
    AbstractBoat.Status callGetStatus();
}

