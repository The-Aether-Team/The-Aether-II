package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.FreezingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IcestoneBlockEntity extends BlockEntity implements FreezingBlock {
    private final IcestoneFreezingListener listener;
    private final Map<BlockPos, Integer> lastBrokenPositions = new HashMap<>();

    public IcestoneBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.ICESTONE.get(), pos, state);
        PositionSource positionSource = new BlockPositionSource(this.getBlockPos());
        this.listener = new IcestoneFreezingListener(this, positionSource, 4);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, IcestoneBlockEntity blockEntity) {
        if (!blockEntity.lastBrokenPositions.isEmpty()) {
            for (Iterator<Map.Entry<BlockPos, Integer>> it = blockEntity.lastBrokenPositions.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<BlockPos, Integer> entry = it.next();
                int nextDelay = entry.setValue(entry.getValue() - 1);
                if (nextDelay == 0) {
                    blockEntity.freezeBlocks(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), FreezingBlock.SQRT_8);
                    it.remove();
                }
            }
        }
    }

    public GameEventListener getListener() {
        return this.listener;
    }

    public Map<BlockPos, Integer> getLastBrokenPositions() {
        return this.lastBrokenPositions;
    }

}
