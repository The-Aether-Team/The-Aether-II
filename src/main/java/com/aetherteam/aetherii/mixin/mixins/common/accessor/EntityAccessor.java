package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    RandomSource aether_ii$getRandom();

//    @Accessor("portalEntrancePos")
//    BlockPos aether_ii$getPortalEntrancePos();
//
//    @Accessor("portalEntrancePos")
//    void aether_ii$setPortalEntrancePos(BlockPos portalEntrancePos);
//
//    @Invoker
//    Vec3 callGetRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle portal);
}