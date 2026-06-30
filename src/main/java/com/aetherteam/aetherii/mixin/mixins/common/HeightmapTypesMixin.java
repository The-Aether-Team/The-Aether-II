package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Predicate;

@Mixin(Heightmap.Types.class)
public class HeightmapTypesMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Predicate<BlockState> injected(Predicate<BlockState> isOpaque, @Local(ordinal = 0, argsOnly = true) String name) {
        if (name.equals("MOTION_BLOCKING_NO_LEAVES")) {
            return isOpaque.or((state) -> state.is(AetherIITags.Blocks.LEAVES));
        }
        return isOpaque;
    }
}
