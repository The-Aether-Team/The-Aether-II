package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherIIGameEvents;
import com.aetherteam.aetherii.block.FreezingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

/**
 * Handles freezing blocks around Icestone when nearby game events indicate a possible state change.
 *
 * @see com.aetherteam.aether.event.hooks.RecipeHooks#preventBlockFreezing(LevelAccessor, BlockPos, BlockPos)
 */
public class IcestoneFreezingListener implements GameEventListener {
    private final IcestoneBlockEntity blockEntity;
    private final PositionSource listenerSource;
    private final int listenerRadius;

    public IcestoneFreezingListener(IcestoneBlockEntity blockEntity, PositionSource source, int radius) {
        this.blockEntity = blockEntity;
        this.listenerSource = source;
        this.listenerRadius = radius;
    }

    @Override
    public PositionSource getListenerSource() {
        return this.listenerSource;
    }

    @Override
    public int getListenerRadius() {
        return this.listenerRadius;
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context context, Vec3 pos) {
        if (event == AetherIIGameEvents.ICESTONE_FREEZABLE_UPDATE.get() || event == GameEvent.BLOCK_PLACE || event == GameEvent.FLUID_PLACE || event == GameEvent.ENTITY_PLACE) {
            this.blockEntity.freezeBlocks(level, this.blockEntity.getBlockPos(), this.blockEntity.getBlockState(), FreezingBlock.SQRT_8);
            return true;
        } else if (event == GameEvent.BLOCK_DESTROY) {
            BlockState state = context.affectedState();
            if (state != null && FreezingBlock.cachedResults.contains(state.getBlock())) {
                BlockPos blockPos = BlockPos.containing(pos);
                this.blockEntity.getLastBrokenPositions().put(blockPos, (int) (state.getBlock().defaultDestroyTime() * 200));
                return true;
            }
        }
        return false;
    }
}
