package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
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
            Biome biome = biomeHolder.value();

            if (serverLevel.isAreaLoaded(belowHeightmapPos, 1)) {
                if (biome.shouldFreeze(serverLevel, belowHeightmapPos)) {
                    serverLevel.setBlockAndUpdate(belowHeightmapPos, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
                }
            }

            if (serverLevel.isRaining()) {
                int i = serverLevel.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
                if (i > 0 && AetherGrassBlock.shouldSnow(biome, serverLevel, heightmapPos)) {
                    BlockState blockState = serverLevel.getBlockState(heightmapPos);
                    if (blockState.is(AetherIIBlocks.ARCTIC_SNOW.get())) {
                        int layers = blockState.getValue(SnowLayerBlock.LAYERS);
                        if (layers < Math.min(i, 8)) {
                            BlockState blockstate1 = blockState.setValue(SnowLayerBlock.LAYERS, layers + 1);
                            Block.pushEntitiesUp(blockState, blockstate1, serverLevel, heightmapPos);
                            serverLevel.setBlockAndUpdate(heightmapPos, blockstate1);
                        }
                    } else if (AetherGrassBlock.plantNotSnowed(blockState) && blockState.getBlock() instanceof Snowable snowable) {
                        serverLevel.setBlockAndUpdate(heightmapPos, snowable.setSnowy(blockState));
                    } else {
                        serverLevel.setBlockAndUpdate(heightmapPos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState());
                    }
                }

                Biome.Precipitation precipitation = biome.getPrecipitationAt(belowHeightmapPos);
                if (precipitation != Biome.Precipitation.NONE) {
                    BlockState blockState = serverLevel.getBlockState(belowHeightmapPos);
                    blockState.getBlock().handlePrecipitation(blockState, serverLevel, belowHeightmapPos, precipitation);
                }
            }
            ci.cancel();
        }
    }
}
