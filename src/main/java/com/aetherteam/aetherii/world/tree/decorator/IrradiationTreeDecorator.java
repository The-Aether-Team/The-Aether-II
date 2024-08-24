package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.IrradiatedLeavesBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class IrradiationTreeDecorator extends TreeDecorator {
    public static final MapCodec<IrradiationTreeDecorator> CODEC = MapCodec.unit(IrradiationTreeDecorator::new);

    @Override
    public void place(Context context) {
        Integer lastY = null;

        int stateValue = 0;
        for (BlockPos pos : context.leaves()) {
            int currentY = pos.getY();
            int finalStateValue = Math.min(stateValue, 7);
            context.level().isStateAtPosition(pos, (blockState) -> {
                if (blockState.getBlock() instanceof IrradiatedLeavesBlock) {
                    context.setBlock(pos, blockState.setValue(IrradiatedLeavesBlock.SHADE, finalStateValue));
                    return true;
                }
                return false;
            });
            if (lastY == null || currentY != lastY) {
                lastY = currentY;
                stateValue++;
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.IRRADIATION.get();
    }
}
