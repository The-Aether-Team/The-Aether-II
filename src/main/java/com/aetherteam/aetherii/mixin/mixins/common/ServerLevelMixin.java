package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.MixinHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(at = @At(value = "HEAD"), method = "tickPrecipitation(Lnet/minecraft/core/BlockPos;)V", cancellable = true)
    private void tickPrecipitation(BlockPos pos, CallbackInfo ci) {
        ServerLevel serverLevel = (ServerLevel) (Object) this;
        BlockPos heightmapPos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
        BlockPos belowHeightmapPos = heightmapPos.below();
        Holder<Biome> biomeHolder = serverLevel.getBiome(heightmapPos);

        if (biomeHolder.is(AetherIITags.Biomes.ARCTIC_ICE)) {
            MixinHooks.handleSnowfall(serverLevel, heightmapPos, belowHeightmapPos, biomeHolder);
            ci.cancel();
        }
    }
}
