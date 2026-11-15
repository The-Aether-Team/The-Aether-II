package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.mixin.wrappers.common.BlockBehaviourPropertiesWrapper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockBehaviour.Properties.class)
public class BlockBehaviourPropertiesMixin implements BlockBehaviourPropertiesWrapper {
    @Shadow
    public BlockBehaviour.@Nullable OffsetFunction offsetFunction;

    @Unique
    @Override
    public BlockBehaviour.Properties aether_ii$offsetFunction(BlockBehaviour.OffsetFunction offsetFunction) {
        this.offsetFunction = offsetFunction;
        return (BlockBehaviour.Properties) (Object) this;
    }
}
