package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
    @Shadow
    @Final
    public ServerLevel level;

    @ModifyArg(method = "tickChunks(Lnet/minecraft/util/profiling/ProfilerFiller;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;getFilteredSpawningCategories(Lnet/minecraft/world/level/NaturalSpawner$SpawnState;ZZZ)Ljava/util/List;"), index = 3)
    private boolean injected(boolean value) {
        if (this.level.dimension().equals(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL)) {
            return true;
        }
        return value;
    }
}
