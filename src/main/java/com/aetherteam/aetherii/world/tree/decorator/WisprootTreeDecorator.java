package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WisprootTreeDecorator extends TreeDecorator {
    public static final Codec< WisprootTreeDecorator> CODEC =
            BlockState.CODEC.fieldOf("block").xmap(WisprootTreeDecorator::new, (instance) -> instance.state).codec();

    private final BlockState state;

    public  WisprootTreeDecorator(BlockState state) {
        this.state = state;
    }

    public void place(Context context) {
        List<BlockPos> trunk = context.logs();
        trunk.forEach((pos) -> context.setBlock(new BlockPos(pos.getX(), trunk.get(1).getY(), pos.getZ()), state));
    }

    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.WISPROOT_DECORATOR.get();
    }
}