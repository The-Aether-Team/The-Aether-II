package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("attackStrengthTicker")
    int aether$getAttackStrengthTicker();

    @Accessor("jumping")
    boolean aether$isJumping();

    @Accessor("noJumpDelay")
    int aether$getNoJumpDelay();

    @Accessor("noJumpDelay")
    void aether$setNoJumpDelay(int noJumpDelay);

    @Invoker
    float callGetJumpPower();

    @Invoker
    float callGetWaterSlowDown();

    @Invoker("createItemStackToDrop")
    ItemEntity callCreateItemStackToDrop(ItemStack droppedItem, boolean dropAround, boolean traceItem);
}
