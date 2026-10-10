package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFullBushBlock extends FullAetherBushBlock {
    public AetherFullBushBlock(Properties properties) {
        super(properties, AetherIIEnvironmentAttributes.AETHER_BUSH_COLOR);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected int getLightDampening(BlockState state) {
        return 1;
    }
}