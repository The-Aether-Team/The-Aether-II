package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.BlockUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("vehicle")
    void aether_ii$setVehicle(Entity entity);

    @Accessor("random")
    RandomSource aether_ii$getRandom();

    @Accessor("firstTick")
    boolean aether_ii$getFirstTick();

    @Accessor("portalEntrancePos")
    BlockPos aether_ii$getPortalEntrancePos();

    @Accessor("portalEntrancePos")
    void aether_ii$setPortalEntrancePos(BlockPos pos);

    @Accessor("isInsidePortal")
    boolean aether_ii$isInsidePortal();

    @Accessor("isInsidePortal")
    void aether_ii$setInsidePortal(boolean insidePortal);

    @Invoker
    boolean callCouldAcceptPassenger();

    @Invoker
    boolean callCanAddPassenger(Entity passenger);

    @Invoker
    Stream<Entity> callGetIndirectPassengersStream();

    @Invoker
    void callAddPassenger(Entity passenger);

    @Invoker("getRelativePortalPosition")
    Vec3 callGetRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle portal);
}
