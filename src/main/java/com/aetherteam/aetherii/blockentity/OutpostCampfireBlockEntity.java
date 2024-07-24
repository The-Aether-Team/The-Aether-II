package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OutpostCampfireBlockEntity extends MultiBlockEntity {
    public OutpostCampfireBlockEntity() {
        this(BlockPos.ZERO, AetherIIBlocks.OUTPOST_CAMPFIRE.get().defaultBlockState());
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof MultiBlockEntity multiBlockEntity) {
            if (multiBlockEntity.getLevelOriginPos() == null) {
                if (state.getValue(OutpostCampfireBlock.PART_FACING) == Direction.SOUTH) {
                    Direction direction = state.getValue(OutpostCampfireBlock.PART_FACING);
                    BlockPos relativePos = pos;
                    for (int i = 0; i < 4; i++) {
                        if (level.getBlockEntity(relativePos) instanceof MultiBlockEntity multiblock) {
                            multiblock.setLevelOriginPos(pos);
                        }
                        relativePos = relativePos.relative(direction);
                        direction = direction.getCounterClockWise();
                    }
                }
            }
        }
    }
}
