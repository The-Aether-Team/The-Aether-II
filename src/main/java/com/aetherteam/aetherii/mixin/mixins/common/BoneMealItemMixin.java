package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @WrapMethod(method = "growWaterPlant(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
    private static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, Direction clickedSide, Operation<Boolean> original) {
        if (level.dimension() == AetherIIDimensions.AETHER_HIGHLANDS_LEVEL) {
            if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
                if (level instanceof ServerLevel serverLevel) {
                    RandomSource random = level.getRandom();
                    BlockPos abovePos = pos.above();
                    Optional<Holder.Reference<PlacedFeature>> featureOptional = level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE).get(HighlandsPlacedFeatures.ARILUM_BONEMEAL);

                    start:
                    for (int i = 0; i < 128; ++i) {
                        BlockPos blockPos = abovePos;

                        for (int j = 0; j < i / 16; ++j) {
                            blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                            if (level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
                                continue start;
                            }
                        }

                        BlockState blockState = level.getBlockState(blockPos);
                        if (blockState.canSurvive(level, blockPos)) {
                            BlockState blockState1 = level.getBlockState(blockPos);
                            if (blockState1.is(Blocks.WATER) && level.getFluidState(blockPos).getAmount() == 8) {
                                if (featureOptional.isEmpty()) {
                                    continue;
                                }
                                featureOptional.get().value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, blockPos);
                            }
                        }
                    }
                    stack.shrink(1);
                }
                return true;
            } else {
                return false;
            }
        }
        return original.call(stack, level, pos, clickedSide);
    }
}
