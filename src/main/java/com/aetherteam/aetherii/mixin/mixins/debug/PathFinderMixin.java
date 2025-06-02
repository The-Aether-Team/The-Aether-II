package com.aetherteam.aetherii.mixin.mixins.debug;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.debug.accessor.PathAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(PathFinder.class)
public class PathFinderMixin {
    @Final
    @Shadow
    private BinaryHeap openSet;

    @Inject(method = "findPath(Lnet/minecraft/world/level/PathNavigationRegion;Lnet/minecraft/world/entity/Mob;Ljava/util/Set;FIF)Lnet/minecraft/world/level/pathfinder/Path;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/pathfinder/NodeEvaluator;done()V"))
    private void setPathDebugs(PathNavigationRegion region, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier, CallbackInfoReturnable<Path> info, @Local Node start, @Local Map<Target, BlockPos> map, @Local Path path) {
        if (AetherII.DEBUG_MODE) {
            if (path != null) {
                ((PathAccessor) path).aether$setDebug(this.openSet.getHeap(), new Node[0], map.keySet());
            }
        }
    }
}
