package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.CrystalAppleBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class CrystalApplesDecorator extends TreeDecorator {
    public static final MapCodec<CrystalApplesDecorator> CODEC = MapCodec.unit(CrystalApplesDecorator::new);

    @Override
    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        List<BlockPos> branches = new ArrayList<>();

        for (BlockPos leaf : context.leaves()) {
            if (level.getBlockState(leaf).is(BlockTags.LOGS)) {
                branches.add(leaf);
            }
        }
        for (int i = 0; i < branches.size(); i++) {
            BlockPos branch = branches.get(i);
            if (i == 0
                    || (i == 1 && random.nextInt(3) == 0)
                    || (i == 2 && random.nextInt(5) == 0)
                    || (i == 3 && random.nextInt(8) == 0)) {
                level.setBlock(branch, AetherIIBlocks.BUDDING_CRYSTALROOT.get().defaultBlockState(), 1 | 2);
                level.setBlock(branch.below(), AetherIIBlocks.CRYSTAL_APPLE.get().defaultBlockState().setValue(CrystalAppleBlock.AGE, 2), 1 | 2);
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.CRYSTAL_APPLES.get();
    }
}
