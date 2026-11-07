package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {
    @WrapOperation(method = "updateLeaves(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)Lnet/minecraft/world/phys/shapes/DiscreteVoxelShape;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LeavesBlock;getOptionalDistanceAt(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/OptionalInt;"))
    private static OptionalInt updateLeaves(BlockState state, Operation<OptionalInt> original, @Local(name = "toCheck") List<Set<BlockPos>> toCheck, @Local(name = "neighborPos") BlockPos.MutableBlockPos neighborPos, @Local(name = "smallestDistance") int smallestDistance) {
        OptionalInt distance = original.call(state);
        if (state.is(AetherIITags.Blocks.LEAVES)) {
            if (distance.isEmpty() || distance.getAsInt() == 7) {
                if (toCheck.get(smallestDistance).contains(neighborPos)) {
                    return OptionalInt.of(smallestDistance);
                }
            }
        }
        return distance;
    }
}
