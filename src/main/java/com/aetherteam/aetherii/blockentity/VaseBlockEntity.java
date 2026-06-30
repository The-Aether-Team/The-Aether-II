package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class VaseBlockEntity extends AetherIIRandomizableSingleItemBlockEntity {
    public static final int EVENT_POT_WOBBLES = 1;
    public long wobbleStartedAtTick;
    public WobbleStyle lastWobbleStyle;

    public VaseBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.VASE.get(), pos, state);
    }

    public Direction getDirection() {
        return this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public void wobble(WobbleStyle wobbleStyle) {
        if (this.level != null && !this.level.isClientSide()) {
            this.level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), EVENT_POT_WOBBLES, wobbleStyle.ordinal());
        }
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (this.level != null && id == EVENT_POT_WOBBLES && type >= 0 && type < WobbleStyle.values().length) {
            this.wobbleStartedAtTick = this.level.getGameTime();
            this.lastWobbleStyle = WobbleStyle.values()[type];
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public enum WobbleStyle {
        POSITIVE(7),
        NEGATIVE(10);

        public final int duration;

        WobbleStyle(int duration) {
            this.duration = duration;
        }
    }
}
