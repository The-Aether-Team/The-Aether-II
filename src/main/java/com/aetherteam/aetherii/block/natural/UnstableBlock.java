package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class UnstableBlock extends Block {
    public UnstableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        level.destroyBlock(pos, false);
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            if (level.getBlockState(offsetPos).is(this)) {
                level.scheduleTick(offsetPos, this, 1);
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof Player player && !player.isCrouching()) {
            level.scheduleTick(pos, this, 10);
        }
        super.stepOn(level, pos, state, entity);
    }
}
