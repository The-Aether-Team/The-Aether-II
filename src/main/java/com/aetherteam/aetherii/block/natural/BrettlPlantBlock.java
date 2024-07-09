package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;

public class BrettlPlantBlock extends Block {
    public static final EnumProperty<BrettlPlantBlock.BrettlType> TYPE = EnumProperty.create("brettl_type", BrettlPlantBlock.BrettlType.class);
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;


    public BrettlPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, BrettlType.BASE).setValue(GROWN, false));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        TriState soilDecision = stateBelow.canSustainPlant(level, pos.below(), Direction.UP, stateBelow);
        if (!soilDecision.isDefault()) return soilDecision.isTrue();
        return stateBelow.is(AetherIIBlocks.QUICKSOIL) && (stateBelow.getValue(TYPE) == BrettlType.BASE) && (stateBelow.getValue(TYPE) == BrettlType.MIDDLE);
    }

    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(TYPE) == BrettlType.TIP) {
            if (level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(5) == 0)) {
                BlockState brettlMiddle = state.setValue(TYPE, BrettlType.MIDDLE);
                BlockState brettlTip = state.setValue(TYPE, BrettlType.TIP);
                BlockState brettlFlower = AetherIIBlocks.BRETTL_FLOWER.get().defaultBlockState();

                if (level.getBlockState(pos.below()).getValue(TYPE) == BrettlType.BASE) {
                    if (level.getBlockState(pos.above()).isAir()) {
                        level.setBlock(pos, brettlMiddle, 2);
                        level.setBlock(pos.above(), brettlTip, 2);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(brettlMiddle));
                        CommonHooks.fireCropGrowPost(level, pos, state);
                    }
                } else {
                    level.setBlock(pos, brettlFlower, 2);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(brettlFlower));
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, GROWN);
    }

    public enum BrettlType implements StringRepresentable {
        BASE,
        MIDDLE,
        TIP;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}