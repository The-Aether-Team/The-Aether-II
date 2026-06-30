package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class AetherFlowerBlock extends FlowerBlock implements Snowable {
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    public AetherFlowerBlock(Properties properties) {
        super(MobEffects.MOVEMENT_SPEED, 0, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, Boolean.FALSE));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

    @Override
    public boolean isSnowy(BlockState blockState) {
        return blockState.getValue(SNOWY);
    }
}
