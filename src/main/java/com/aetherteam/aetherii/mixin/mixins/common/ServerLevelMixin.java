package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @WrapOperation(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean aether_ii$freezeArcticIce(Biome biome, LevelReader levelReader, BlockPos pos, Operation<Boolean> original) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (level.getBiome(pos.above()).is(AetherIITags.Biomes.ARCTIC_ICE)) {
            boolean shouldFreeze = original.call(biome, levelReader, pos);
            if (shouldFreeze) {
                level.setBlockAndUpdate(pos, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
            }
            return false;
        }
        return original.call(biome, levelReader, pos);
    }

    @WrapOperation(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean aether_ii$snowArcticPlants(Biome biome, LevelReader levelReader, BlockPos pos, Operation<Boolean> original) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (level.getBiome(pos).is(AetherIITags.Biomes.ARCTIC_ICE)) {
            int maxSnowLayers = level.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
            if (maxSnowLayers > 0 && AetherGrassBlock.shouldSnow(biome, level, pos)) {
                BlockState state = level.getBlockState(pos);
                if (state.is(AetherIIBlocks.ARCTIC_SNOW.get())) {
                    int layers = state.getValue(SnowLayerBlock.LAYERS);
                    if (layers < Math.min(maxSnowLayers, 8)) {
                        BlockState newState = state.setValue(SnowLayerBlock.LAYERS, layers + 1);
                        Block.pushEntitiesUp(state, newState, level, pos);
                        level.setBlockAndUpdate(pos, newState);
                    }
                } else if (AetherGrassBlock.plantNotSnowed(state) && state.getBlock() instanceof Snowable snowable) {
                    level.setBlockAndUpdate(pos, snowable.setSnowy(state));
                } else {
                    level.setBlockAndUpdate(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState());
                }
            }
            return false;
        }
        return original.call(biome, levelReader, pos);
    }
}
