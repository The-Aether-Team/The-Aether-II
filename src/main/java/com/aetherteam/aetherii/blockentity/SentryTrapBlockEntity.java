package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SentryTrapBlockEntity extends GroundTrapBlockEntity {
    public SentryTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_TRAP.get(), pos, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, GroundTrapBlockEntity blockEntity) {
        GroundTrapBlockEntity.clientTick(level, pos, state, blockEntity);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GroundTrapBlockEntity blockEntity) {
        if (blockEntity.firstTick) {
            blockEntity.getSpawner().setEntityId(AetherIIEntityTypes.DETONATION_SENTRY.get(), level, level.getRandom(), pos);
            blockEntity.firstTick = false;
        }
        GroundTrapBlockEntity.serverTick(level, pos, state, blockEntity);
    }
}
